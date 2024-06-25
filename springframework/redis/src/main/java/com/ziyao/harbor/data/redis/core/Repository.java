package com.ziyao.harbor.data.redis.core;

import org.springframework.stereotype.Indexed;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
@Indexed
public interface Repository {

    /**
     * 判断缓存中有没有这个key
     */
    boolean hasKey(String key);

    /**
     * 删除key
     */
    boolean delete(String key);

    /**
     * 刷新过期时间
     */
    void expire(String key, long timeout, TimeUnit timeUnit);

    /**
     * 设置过期时间
     */
    void expire(String key);

    default String version() {
        return "v1.0";
    }
}
