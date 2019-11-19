package cn.mzlalal.cachelog.cachelogcore.config;

import cn.mzlalal.cachelog.cachelogcore.aspect.CachelogAspect;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.Charset;

/**
 * @description: redis 初始化配置
 * @author: Mzlalal
 */
@Slf4j
@Configuration
public class BaseRedisConfig {


    /**
     * 实例化 CachelogAspect 对象
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(CachelogAspect.class)
    public CachelogAspect createCachelogAspect() {
        return new CachelogAspect();
    }

    /**
     * 实例化 RedisTemplate 对象
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RedisTemplate.class)
    public RedisTemplate<String, Object> createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        // 设置数据存入 redis 的序列化方式,并开启事务
        // 如果不配置Serializer，那么存储的时候缺省使用String
        // 如果用User类型存储，那么会提示错误User can't cast to String！
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer(Charset.forName("GBK"));
        FastJson2JsonRedisSerializer fastJson2JsonRedisSerializer = new FastJson2JsonRedisSerializer(Object.class);

        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        // 设置对象映射
        fastJson2JsonRedisSerializer.setObjectMapper(om);

        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJson2JsonRedisSerializer);

        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}