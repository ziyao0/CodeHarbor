package com.ziyao.harbor.gateway.cache;

import com.ziyao.harbor.core.CacheBeanNames;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author ziyao zhang
 * @since 2023/9/4
 */
@Configuration
@ConditionalOnClass({ReactiveRedisConnectionFactory.class, ReactiveRedisTemplate.class})
public class ReactiveRedisAutoConfiguration {


    @Bean(CacheBeanNames.REACTIVE_REDIS_TEMPLATE)
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        RedisSerializer<String> keySerializer = new StringRedisSerializer();
        RedisSerializer<Object> valueSerializer = new GenericJackson2JsonRedisSerializer();
        RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder = RedisSerializationContext
                .newSerializationContext();
        // @formatter:off
        RedisSerializationContext<String, Object> context = builder.key(keySerializer)
                .hashKey(keySerializer)
                .value(valueSerializer)
                .hashValue(valueSerializer)
                .build();
        // @formatter:on
        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, context);
    }
}
