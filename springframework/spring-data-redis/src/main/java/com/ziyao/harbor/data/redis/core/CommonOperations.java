package com.ziyao.harbor.data.redis.core;

import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/3/1
 */
public interface CommonOperations {
    /**
     * 刷新过期时间
     */
    void refresh();

    /**
     * 刷新过期时间
     */
    void refresh(long timeout, TimeUnit unit);

    /**
     * 刪除key
     */
    boolean deleteKey();

    /**
     * 判斷是否存在这个key
     */
    boolean hasKey();
   
    boolean deleteKey();

    void setKeySerializer(RedisSerializer<?> serializer);

    void setValueSerializer(RedisSerializer<?> serializer);

    void setHashKeySerializer(RedisSerializer<?> serializer);

    void setHashValueSerializer(RedisSerializer<?> serializer);
}
