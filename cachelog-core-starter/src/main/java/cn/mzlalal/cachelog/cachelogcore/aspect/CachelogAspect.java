package cn.mzlalal.cachelog.cachelogcore.aspect;

import cn.mzlalal.cachelog.cachelogcore.annotaion.Cachelog;
import cn.mzlalal.cachelog.cachelogcore.config.properties.CachelogProperties;
import cn.mzlalal.cachelog.cachelogcore.entity.CacheLog;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
    public void pointCut() {}

    /**
     * 环绕通知
     * @param pjp 切入点
     * @param annotation 注解内容
     * @return
     */
    @Around("pointCut() && @within(annotation)")
    public Object doAround(ProceedingJoinPoint pjp, Cachelog annotation) {
        // 获取请求对象
        HttpServletRequest request = getRequest();
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
            printLog(cacheLog);
        }
        return returnValue;
    }

    /**
     * 获取 request 对象
     * @return
     */
    private HttpServletRequest getRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
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
            case REFLECTIONTOSTRINGBUILDER:
                log.info(org.apache.commons.lang3.builder.ReflectionToStringBuilder.toString(cacheLog));
                break;
            case CUSTOMIZATION:
                // todo 待完善自定义类路径以及方法名输出日志对象
                break;
        }

    }
}
