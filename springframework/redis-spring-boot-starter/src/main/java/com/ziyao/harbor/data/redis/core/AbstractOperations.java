package com.ziyao.harbor.data.redis.core;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
public abstract class AbstractOperations<V> {
    final RedisTemplate<String, V> template;
    final String redisKey;

    public AbstractOperations(RedisTemplate<String, V> template, String redisKey) {
        this.template = template;
        this.redisKey = redisKey;
    }
}
