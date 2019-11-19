package cn.mzlalal.cachelog.cachelogcore.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import cn.mzlalal.cachelog.cachelogcore.annotaion.Cachelog;

/**
 * @description: 默认切面
 * @author: Mzlalal
 * @date: 2019/11/19 16:52
 * @version: 1.0
 */
@Slf4j
public class CachelogAspect {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 定义切入点 切入点为带有@Cachelog的注解
     */
    @Pointcut(value = "@annotation(cn.mzlalal.cachelog.cachelogcore.annotaion.Cachelog)")
    public void pointCut() {}

    @Around("pointCut() && @annotation(cachelog)")
    public Object doAround(ProceedingJoinPoint pjp, Cachelog cachelog) {
        // 开始时间
        long startTime = System.currentTimeMillis();
        // 获取签名方法名称
        String methodName = ((MethodSignature) (pjp.getSignature())).getMethod().getName();

        // 返回值
        Object returnValue = null;
        try {
            returnValue = pjp.proceed();
        } catch (Throwable throwable) {
            log.error("", throwable);
        }
        // 结束时间
        long endTime = System.currentTimeMillis();
        // 判断是否存储到redis
        if (cachelog.isRedis()) {
            redisTemplate.opsForHash().put(pjp.getTarget().getClass().toString(), methodName, returnValue);
        }
        // 判断是否记录日志
        if (cachelog.isLog()) {
            log.info("方法:{}执行结束!结果为:{}", methodName, returnValue);
        }
        if (log.isDebugEnabled()) {
            log.debug("方法:{} 总耗时:{}", methodName, (endTime - startTime));
        }
        return null;
    }
}
