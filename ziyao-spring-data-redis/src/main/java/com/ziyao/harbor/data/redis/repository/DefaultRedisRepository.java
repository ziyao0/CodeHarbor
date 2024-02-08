package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RedisEntityInformation;
import org.springframework.data.redis.core.*;

import java.util.Collection;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
public class DefaultRedisRepository<K, V, HK, HV> implements RedisRepository<K, V, HK, HV> {

    private final RedisTemplate<K, V> operations;

    public DefaultRedisRepository(RedisEntityInformation<K, V, HK, HV> metadata, RedisTemplate<K, V> operations) {
        this.operations = operations;
        // 设置序列化
        operations.setKeySerializer(metadata.getKeySerializer());
        operations.setValueSerializer(metadata.getValueSerializer());
        operations.setHashKeySerializer(metadata.getHashKeySerializer());
        operations.setHashValueSerializer(metadata.getHashValueSerializer());
    }


    @Override
    public boolean hasKey(K k) {
        return Boolean.TRUE.equals(operations.hasKey(k));
    }

    @Override
    public boolean delete(K k) {
        return Boolean.TRUE.equals(operations.delete(k));
    }

    @Override
    public long delete(Collection<K> ks) {
        Long count = operations.delete(ks);
        if (null == count) return 0;
        else return count;
    }

    @Override
    public HashOperations<K, HK, HV> opsForHash() {
        return operations.opsForHash();
    }

    @Override
    public ListOperations<K, V> opsForList() {
        return operations.opsForList();
    }

    @Override
    public SetOperations<K, V> opsForSet() {
        return operations.opsForSet();
    }

    @Override
    public ZSetOperations<K, V> opsForZSet() {
        return operations.opsForZSet();
    }

    @Override
    public ValueOperations<K, V> opsForValue() {
        return operations.opsForValue();
    }
}
