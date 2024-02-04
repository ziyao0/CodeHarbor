package com.ziyao.harbor.cache.core;

import com.ziyao.harbor.cache.redis.serializer.SerializerInformation;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
public interface CacheEntityInformation<K, V, HK, HV> extends SerializerInformation<K, V, HK, HV> {

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
}
