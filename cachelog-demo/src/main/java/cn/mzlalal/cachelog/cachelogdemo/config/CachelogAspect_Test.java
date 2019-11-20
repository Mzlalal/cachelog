package cn.mzlalal.cachelog.cachelogdemo.config;

import cn.mzlalal.cachelog.cachelogcore.aspect.CachelogAspect;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;

/**
 * @description: 如果需要自定义切面内环绕通知的实现方法 主要修改如下
 * 1. 继承 CachelogAspect
 * 2. 覆盖 doAround 方法
 * 3. 在 config 中继承 CachelogRedisConfig
 * 4. 在 config 中实例化 CachelogAspect_Test 切面
 * @author: Mzlalal
 * @date: 2019/11/19 16:52
 * @version: 1.0
 */
@Slf4j
@Aspect
public class CachelogAspect_Test extends CachelogAspect {
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Override
//    @Around("pointCut() && @annotation(cachelog)")
//    public Object doAround(ProceedingJoinPoint pjp, Cachelog cachelog) {
//        System.out.println("进入CachelogAspect_Test");
//        // 开始时间
//        long startTime = System.currentTimeMillis();
//        // 获取签名方法名称
//        String methodName = ((MethodSignature) (pjp.getSignature())).getMethod().getName();
//
//        // 返回值
//        Object returnValue = null;
//        try {
//            returnValue = pjp.proceed();
//        } catch (Throwable throwable) {
//            log.error("", throwable);
//        }
//        // 结束时间
//        long endTime = System.currentTimeMillis();
//        // 判断是否存储到redis
//        if (cachelog.isRedis()) {
//            redisTemplate.opsForHash().put(pjp.getTarget().getClass().toString(), methodName, returnValue);
//        }
//        // 判断是否记录日志
//        if (cachelog.isLog()) {
//            log.info("方法:{}执行结束!结果为:{}", methodName, returnValue);
//        }
//        if (log.isDebugEnabled()) {
//            log.debug("方法:{} 总耗时:{}", methodName, (endTime - startTime));
//        }
//        return returnValue;
//    }
}
