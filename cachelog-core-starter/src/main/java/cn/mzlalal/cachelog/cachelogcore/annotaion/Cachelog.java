package cn.mzlalal.cachelog.cachelogcore.annotaion;

import cn.mzlalal.cachelog.cachelogcore.entity.enums.ExpiredPolicyEnums;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @description: cache log注解
 * @author: Mzlalal
 * @date: 2019/11/19 16:42
 * @version: 1.0
 */
// 记录到javadoc中
@Documented
// 不抛弃该注解 一直有效
@Retention(RetentionPolicy.RUNTIME)
// 可使用在方法 类上面
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Cachelog {
    /**
     * 是否记录日志
     */
    boolean isLog() default true;
    /**
     * 是否保存到redis
     */
    boolean isRedis() default false;
    /**
     * 过期策略
     * @return ExpiredTime
     */
    ExpiredPolicyEnums policy() default ExpiredPolicyEnums.DEFAULT;
    /**
     * 过期时间
     */
    long time() default 300;
    /**
     * 时间单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;
    /**
     * 返回错误的实体, 如果使用该实体 则不会保存到redis
     */
    Class exceptionEntity() default Throwable.class;

    /**
     * 当前类模块名称 例如用户服务
     */
    String moduleName() default "";
    /**
     * 当前方法为当前模块哪个功能
     */
    String functionName() default "";
}
