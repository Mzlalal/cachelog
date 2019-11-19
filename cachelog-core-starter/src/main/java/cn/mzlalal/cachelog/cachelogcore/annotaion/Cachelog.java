package cn.mzlalal.cachelog.cachelogcore.annotaion;

import java.lang.annotation.*;

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
     * 是否保存到redis
     * @return
     */
    boolean isRedis() default false;

    /**
     * 是否记录日志
     * @return
     */
    boolean isLog() default true;
}
