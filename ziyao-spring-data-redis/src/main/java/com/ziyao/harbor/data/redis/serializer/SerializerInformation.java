package com.ziyao.harbor.data.redis.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
public interface SerializerInformation<K, V, HK, HV> {
    /**
     * Returns the actual key class type.
     */
    Class<K> getKeyJavaType();

    /**
     * Returns the actual value class type.
     */
    Class<V> getValueJavaType();

    /**
     * Returns the actual hash key class type.
     */
    Class<HK> getHashKeyJavaType();

    /**
     * Returns the actual hash value class type.
     */
    Class<HV> getHashValueJavaType();
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
