package com.harbor.boot.autoconfigure.redis;

import com.ziyao.harbor.data.redis.support.CacheBeanName;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author ziyao zhang
 * @since 2024/2/5
 */
@Configuration
@ConditionalOnClass({RedisTemplate.class})
@Import(RedisRepositoriesRegistrar.class)
public class DataRedisAutoConfiguration {


    @Bean(name = CacheBeanName.cache_name)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(RedisSerializer.byteArray());
        redisTemplate.setHashKeySerializer(RedisSerializer.byteArray());
        redisTemplate.setValueSerializer(RedisSerializer.byteArray());
        redisTemplate.setHashValueSerializer(RedisSerializer.byteArray());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
