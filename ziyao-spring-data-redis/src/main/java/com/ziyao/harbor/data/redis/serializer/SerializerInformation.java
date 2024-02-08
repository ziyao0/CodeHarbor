package com.ziyao.harbor.data.redis.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
public interface SerializerInformation<K, V, HK, HV> {

    /**
     * @return the key {@link RedisSerializer}.
     */
    RedisSerializer<K> getKeySerializer();

    /**
     * @return the value {@link RedisSerializer}.
     */
    RedisSerializer<V> getValueSerializer();

    /**
     * @return the hash key {@link RedisSerializer}.
     */
    RedisSerializer<HK> getHashKeySerializer();

    /**
     * @return the hash value {@link RedisSerializer}.
     */
    RedisSerializer<HV> getHashValueSerializer();
}
