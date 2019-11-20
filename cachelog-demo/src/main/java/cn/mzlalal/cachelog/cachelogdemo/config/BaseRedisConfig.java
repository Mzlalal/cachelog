package cn.mzlalal.cachelog.cachelogdemo.config;

import cn.mzlalal.cachelog.cachelogcore.config.CachelogBaseRedisConfig;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: Mzlalal
 * @date: 2019/11/20 9:36
 * @version: 1.0
 */
@Configuration
public class BaseRedisConfig extends CachelogBaseRedisConfig {

    /**
     * 实例化 CachelogAspect 对象
     * 如果不实例化 则切面无效
     * @return
     */
//    @Override
//    @Bean
//    @ConditionalOnMissingBean(CachelogAspect.class)
//    public CachelogAspect createCachelogAspect() {
//        return new CachelogAspect_Test();
//    }
}
