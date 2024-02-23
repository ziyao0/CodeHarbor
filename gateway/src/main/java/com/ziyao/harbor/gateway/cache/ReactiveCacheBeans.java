package com.ziyao.harbor.gateway.cache;

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

/**
 * @author ziyao zhang
 * @since 2023/9/15
 */
public abstract class ReactiveCacheBeans {

    public static ReactiveRedisTemplate<String, Object> reactiveRedis;
    public static ReactiveStringRedisTemplate reactiveStringRedis;

    public static void setRedis(ReactiveRedisTemplate<String, Object> reactiveRedis) {
        ReactiveCacheBeans.reactiveRedis = reactiveRedis;
    }

    public static void setStringRedis(ReactiveStringRedisTemplate reactiveStringRedis) {
        ReactiveCacheBeans.reactiveStringRedis = reactiveStringRedis;
    }
}
