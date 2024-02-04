package com.ziyao.harbor.cache.redis.support;

import com.ziyao.harbor.cache.core.CacheEntityInformation;
import com.ziyao.harbor.cache.redis.serializer.ObjectRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
public class RedisEntityInformation<K, V, HK, HV> implements CacheEntityInformation<K, V, HK, HV> {

    private final Class<K> keyJavaType;
    private final Class<V> valueJavaType;
    private final Class<HK> hkJavaType;
    private final Class<HV> hvJavaType;

    public RedisEntityInformation(Class<K> keyJavaType,
                                  Class<V> valueJavaType,
                                  Class<HK> hkJavaType,
                                  Class<HV> hvJavaType) {
        this.keyJavaType = keyJavaType;
        this.valueJavaType = valueJavaType;
        this.hkJavaType = hkJavaType;
        this.hvJavaType = hvJavaType;
    }

    @Override
    public Class<K> getKeyJavaType() {
        return this.keyJavaType;
    }

    @Override
    public Class<V> getValueJavaType() {
        return this.valueJavaType;
    }

    @Override
    public Class<HK> getHashKeyJavaType() {
        return this.hkJavaType;
    }

    @Override
    public Class<HV> getHashValueJavaType() {
        return this.hvJavaType;
    }

    @Override
    public RedisSerializer<K> getKeySerializer() {
        return new ObjectRedisSerializer<>(this.keyJavaType);
    }

    @Override
    public RedisSerializer<V> getValueSerializer() {
        return new ObjectRedisSerializer<>(this.valueJavaType);
    }

    @Override
    public RedisSerializer<HK> getHashKeySerializer() {
        return new ObjectRedisSerializer<>(this.hkJavaType);
    }

    @Override
    public RedisSerializer<HV> getHashValueSerializer() {
        return new ObjectRedisSerializer<>(this.hvJavaType);
    }
}
