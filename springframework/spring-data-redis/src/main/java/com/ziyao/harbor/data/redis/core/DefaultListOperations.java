package com.ziyao.harbor.data.redis.core;

import com.ziyao.harbor.data.redis.support.serializer.SerializerInformation;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
public class DefaultListOperations<V> extends AbstractOperations<V> implements ListOperations<V> {
    public DefaultListOperations(RedisTemplate<String, V> operations, long timeout, SerializerInformation metadata) {
        super(operations, timeout, metadata);
    }

    @Override
    public List<V> range(long start, long end) {
        return operations.opsForList().range(key, start, end);
    }

    @Override
    public void trim(long start, long end) {
        operations.opsForList().trim(key, start, end);
    }

    @Override
    public Long size() {
        return operations.opsForList().size(key);
    }

    @Override
    public Long leftPush(V value) {
        Long l = operations.opsForList().leftPush(key, value);
        expire();
        return l;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Long leftPushAll(V... values) {
        Long l = operations.opsForList().leftPushAll(key, values);
        expire();
        return l;
    }

    @Override
    public Long leftPushAll(Collection<V> values) {
        Long l = operations.opsForList().leftPushAll(key, values);
        expire();
        return l;
    }

    @Override
    public Long leftPushIfPresent(V value) {
        Long l = operations.opsForList().leftPushIfPresent(key, value);
        expire();
        return l;
    }

    @Override
    public Long leftPush(V pivot, V value) {
        Long l = operations.opsForList().leftPush(key, pivot, value);
        expire();
        return l;
    }

    @Override
    public Long rightPush(V value) {
        Long l = operations.opsForList().rightPush(key, value);
        expire();
        return l;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Long rightPushAll(V... values) {
        Long l = operations.opsForList().rightPushAll(key, values);
        expire();
        return l;
    }

    @Override
    public Long rightPushAll(Collection<V> values) {
        Long l = operations.opsForList().rightPushAll(key, values);
        expire();
        return l;
    }

    @Override
    public Long rightPushIfPresent(V value) {
        Long l = operations.opsForList().rightPushIfPresent(key, value);
        expire();
        return l;
    }

    @Override
    public Long rightPush(V pivot, V value) {
        Long l = operations.opsForList().rightPush(key, pivot, value);
        expire();
        return l;
    }

    @Override
    public void set(long index, V value) {
        operations.opsForList().set(key, index, value);
        expire();
    }

    @Override
    public Long remove(long count, Object value) {
        return operations.opsForList().remove(key, count, value);
    }

    @Override
    public V index(long index) {
        return operations.opsForList().index(key, index);
    }

    @Override
    public Long indexOf(V value) {
        return operations.opsForList().indexOf(key, value);
    }

    @Override
    public Long lastIndexOf(V value) {
        return operations.opsForList().lastIndexOf(key, value);
    }

    @Override
    public V leftPop() {
        return operations.opsForList().leftPop(key);
    }

    @Override
    public List<V> leftPop(long count) {
        return operations.opsForList().leftPop(key, count);
    }

    @Override
    public V leftPop(long timeout, TimeUnit unit) {
        return operations.opsForList().leftPop(key, timeout, unit);
    }

    @Override
    public V rightPop() {
        return operations.opsForList().rightPop(key);
    }

    @Override
    public List<V> rightPop(long count) {
        return operations.opsForList().rightPop(key, count);
    }

    @Override
    public V rightPop(long timeout, TimeUnit unit) {
        return operations.opsForList().rightPop(key, timeout, unit);
    }

    @Override
    public V rightPopAndLeftPush(String sourceKey, String destinationKey) {
        return operations.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    @Override
    public V rightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) {
        return operations.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
    }
}
