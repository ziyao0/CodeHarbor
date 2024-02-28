package com.ziyao.harbor.data.redis.support.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
public class DefaultSerializerInformation<K, V, HK, HV> implements SerializerInformation<K, V, HK, HV> {

    private final Class<K> keyJavaType;
    private final Class<V> valueJavaType;
    private final Class<HK> hkJavaType;
    private final Class<HV> hvJavaType;

    public DefaultSerializerInformation(Class<K> keyJavaType,
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

    @SuppressWarnings("unchecked")
    @Override
    public RedisSerializer<K> getKeySerializer() {
        return (RedisSerializer<K>) getRedisSerializer(this.keyJavaType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public RedisSerializer<V> getValueSerializer() {
        return (RedisSerializer<V>) getRedisSerializer(this.valueJavaType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public RedisSerializer<HK> getHashKeySerializer() {
        return (RedisSerializer<HK>) getRedisSerializer(this.hkJavaType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public RedisSerializer<HV> getHashValueSerializer() {
        return (RedisSerializer<HV>) getRedisSerializer(this.hvJavaType);
    }

    private RedisSerializer<?> getRedisSerializer(Class<?> javaType) {
        if (String.class.isAssignableFrom(javaType)) {
            return RedisSerializer.string();
        } else {
            return new ObjectRedisSerializer<>(javaType);
        }
    }
}
