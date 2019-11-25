本项目使用aop和自定义注解将日志打印，redis缓存返回结果进行统一
***
主要使用用途如下:
1. 添加在类上，访问该类的所有方法时可以根据方法名称判断方法操作类型（参考阿里巴巴命名规范）。
2. 添加在方法上，可单独在方法上添加注解自定义该方法操作。
***
### 自定义注解为@Cachelog
```

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
```
***
### 过期策略详解
过期策略主要如下：
```

    /**
     * 从不过期
     */
    NERVER,
    /**
     * 在300-3000秒内随机过期
     */
    RANDOM,
    /**
     * 自定义 默认300秒
     */
    DEFAULT
```
### 日志打印策略
日志打印主要有FASTJSON, STRINGBUILDER, 以及CUSTOMIZATION自定义
***
通过自定义需要实现CacheLogFormatTypeInterface接口
```

    /**
     * 通过 alibaba fastjson 将实体转换成string
     */
    FASTJSON,

    /**
     * 通过 org.apache.commons.lang3.builder.ReflectionToStringBuilder 将实体转换成string
     */
    STRINGBUILDER,

    /**
     * 自定义 通过类名和方法名进行反射调用
     */
    CUSTOMIZATION
```
### 自定义切面
在切面内需要自己定制化实现内容的时候，可以进行如下操作：
 1. 继承 CachelogAspect
 2. 覆盖 doAround 方法
 3. 在 config 中继承 CachelogRedisConfig
 4. 在 config 中实例化 CachelogAspect_Test 切面