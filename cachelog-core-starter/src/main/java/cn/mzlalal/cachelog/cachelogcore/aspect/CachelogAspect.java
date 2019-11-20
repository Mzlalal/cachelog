package cn.mzlalal.cachelog.cachelogcore.aspect;

import cn.mzlalal.cachelog.cachelogcore.annotaion.Cachelog;
import cn.mzlalal.cachelog.cachelogcore.config.properties.CachelogProperties;
import cn.mzlalal.cachelog.cachelogcore.entity.CacheLog;
import cn.mzlalal.cachelog.cachelogcore.entity.enums.MethodHead;
import cn.mzlalal.cachelog.cachelogcore.interfaces.CacheLogFormatTypeInterface;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * @description: 默认切面
 * 可以更换切面 查看cachelog-demo中的 baseRedisConfig 以及CachelogAspect_Test
 * @author: Mzlalal
 * @date: 2019/11/19 16:52
 * @version: 1.0
 */
@Slf4j
@Aspect
public class CachelogAspect {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    CachelogProperties cachelogProperties;

    /**
     * 定义切入点 切入点为带有@Cachelog的注解
     */
    @Pointcut(value = "@within(cn.mzlalal.cachelog.cachelogcore.annotaion.Cachelog)")
    public void pointCut() {
    }

    /**
     * 环绕通知
     *
     * @param pjp        切入点
     * @param annotation 注解内容
     * @return
     */
    @Around("pointCut() && @within(annotation)")
    public Object doAround(ProceedingJoinPoint pjp, Cachelog annotation) {
        // 获取请求对象
        HttpServletRequest request = this.getRequest();
        // 实例化对象
        CacheLog cacheLog = new CacheLog();
        // 设置方法开始时间
        cacheLog.setStartTimestamp(new Date());

        // 获取调用方IP
        if (request != null) {
            cacheLog.setRemoteIP(request.getRemoteAddr());
        }

        // 获取类名
        cacheLog.setClassName(pjp.getTarget().getClass().toString());
        // 获取签名方法名称
        cacheLog.setMethodName(((MethodSignature) (pjp.getSignature())).getMethod().getName());
        // 获取方法操作类型
        cacheLog.setOperationType(this.getOperationType(cacheLog.getMethodName()));
        // 获取请求参数
        cacheLog.setRequestParameter(JSON.toJSONString(pjp.getArgs()));

        // 返回值
        Object returnValue = null;
        try {
            returnValue = pjp.proceed();
        } catch (Throwable throwable) {
            log.error("", throwable);
            return returnValue;
        } finally {
            // 结束时间
            cacheLog.setEndTimestamp(new Date());
            // 总耗时
            cacheLog.setTotalTime(cacheLog.getEndTimestamp().getTime() - cacheLog.getStartTimestamp().getTime());
            // 返回结果
            cacheLog.setReturnValue(JSON.toJSONString(returnValue));
            if (log.isDebugEnabled()) {
                log.debug("方法:{} 总耗时:{}", cacheLog.getMethodName(), cacheLog.getTotalTime());
            }
        }

        // 判断是否存储到redis 使用hash  类名分组 方法名称为key 返回值为value
        if (annotation.isRedis()) {
            redisTemplate.opsForHash().put(cacheLog.getClassName(),
                    cacheLog.getMethodName(),
                    returnValue);
        }
        // 判断是否记录日志
        if (annotation.isLog()) {
            this.printLog(cacheLog);
        }
        return returnValue;
    }

    /**
     * 获取 request 对象
     *
     * @return
     */
    private HttpServletRequest getRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra != null) {
            return sra.getRequest();
        }
        return null;
    }

    /**
     * 打印日志
     * @param cacheLog
     */
    private void printLog(CacheLog cacheLog) {
        switch (cachelogProperties.getFormatType()) {
            case FASTJSON:
                log.info(JSON.toJSONStringWithDateFormat(cacheLog, "yyyy-MM-dd hh:mm:ss"));
                break;
            case STRINGBUILDER:
                log.info(org.apache.commons.lang3.builder.ReflectionToStringBuilder.toString(cacheLog));
                break;
            case CUSTOMIZATION:
                // 参考demo中的cn.mzlalal.cachelog.cachelogdemo.config.DemoCacheLogFormatTypeImpl实现自定义
                Assert.notNull(cachelogProperties.getClassPath(), "自定义类路径不能为空！");
                Assert.notNull(cachelogProperties.getMethodName(), "自定义方法名称不能为空！");
                try {
                    // 根据指定的类路径查询
                    Class clazz = Class.forName(cachelogProperties.getClassPath());
                    Object instanceObj = clazz.newInstance();
                    Assert.isInstanceOf(CacheLogFormatTypeInterface.class, instanceObj, "该类未实现CacheLogFormatTypeInterface接口!");
                    Method[] methods = clazz.getDeclaredMethods();
                    for (Method temp : methods) {
                        if (temp.getName().equals(cachelogProperties.getMethodName())) {
                            Object returnValue = temp.invoke(instanceObj, cacheLog);
                            log.info(returnValue.toString());
                            // 退出执行
                            return;
                        }
                    }
                } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    log.error("自定义格式化日志出现了问题!", e);
                }
                break;
            default:
                log.error("未知的日志格式化方式! 请确认cn.mzlalal.cachelog.format-type值是否有误!");
                break;
        }
    }

    /**
     * 根据方法名称返回操作类型 参考遵守 alibaba 命名规范
     * 若不存在以下命名 则返回操作类型为 未知
     * --1） 获取单个对象的方法用 get 做前缀。
     * --2） 获取多个对象的方法用 list 做前缀。
     * --3） 获取统计值的方法用 count 做前缀。
     * --4） 插入的方法用 save（推荐）或 insert 做前缀。
     * --5） 删除的方法用 remove（推荐）或 delete 做前缀。
     * --6） 修改的方法用 update 做前缀。
     *
     * @param methodName 方法名称
     * @return string 若未查询到返回 未知
     */
    private String getOperationType(String methodName) {
        for (MethodHead temp : MethodHead.values()) {
            if (methodName.startsWith(temp.getHead())) {
                return temp.getType();
            }
        }
        return "未知";
    }
}
