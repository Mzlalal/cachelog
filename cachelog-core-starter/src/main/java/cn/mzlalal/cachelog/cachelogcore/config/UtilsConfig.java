package cn.mzlalal.cachelog.cachelogcore.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @description: 工具类实例化
 * @author: Mzlalal
 * @date: 2019/11/19 17:26
 * @version: 1.0
 */
@Configuration
public class UtilsConfig {

    /**
     * 初始化redis template 操作模板
     * @return RedisTemplate
     */
    @Bean
    @ConditionalOnMissingBean(RedisTemplate.class)
    public RedisTemplate createRedisTemplate(){
        return new RedisTemplate<String, Object>();
    }
}
