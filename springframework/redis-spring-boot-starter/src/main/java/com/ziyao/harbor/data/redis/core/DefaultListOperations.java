package com.ziyao.harbor.data.redis.core;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
public class DefaultListOperations<V> extends AbstractOperations<V> implements ListOperations<V> {
    public DefaultListOperations(RedisTemplate<String, V> template, String key) {
        super(template, key);
    }

    @Override
    public List<V> range(long start, long end) {
        return template.opsForList().range(key, start, end);
    }

    @Override
    public void trim(long start, long end) {
        template.opsForList().trim(key, start, end);
    }

    @Override
    public Long size() {
        return template.opsForList().size(key);
    }

    @Override
    public Long leftPush(V value) {
        return template.opsForList().leftPush(key, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Long leftPushAll(V... values) {
        return template.opsForList().leftPushAll(key, values);
    }

    @Override
    public Long leftPushAll(Collection<V> values) {
        return template.opsForList().leftPushAll(key, values);
    }

    @Override
    public Long leftPushIfPresent(V value) {
        return template.opsForList().leftPushIfPresent(key, value);
    }

    @Override
    public Long leftPush(V pivot, V value) {
        return template.opsForList().leftPush(key, pivot, value);
    }

    @Override
    public Long rightPush(V value) {
        return template.opsForList().rightPush(key, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Long rightPushAll(V... values) {
        return template.opsForList().rightPushAll(key, values);
    }

    @Override
    public Long rightPushAll(Collection<V> values) {
        return template.opsForList().rightPushAll(key, values);
    }

    @Override
    public Long rightPushIfPresent(V value) {
        return template.opsForList().rightPushIfPresent(key, value);
    }

    @Override
    public Long rightPush(V pivot, V value) {
        return template.opsForList().rightPush(key, pivot, value);
    }

    @Override
    public void set(long index, V value) {
        template.opsForList().set(key, index, value);
    }

    @Override
    public Long remove(long count, Object value) {
        return template.opsForList().remove(key, count, value);
    }

    @Override
    public V index(long index) {
        return template.opsForList().index(key, index);
    }

    @Override
    public Long indexOf(V value) {
        return template.opsForList().indexOf(key, value);
    }

    @Override
    public Long lastIndexOf(V value) {
        return template.opsForList().lastIndexOf(key, value);
    }

    @Override
    public V leftPop() {
        return template.opsForList().leftPop(key);
    }

    @Override
    public List<V> leftPop(long count) {
        return template.opsForList().leftPop(key, count);
    }

    @Override
    public V leftPop(long timeout, TimeUnit unit) {
        return template.opsForList().leftPop(key, timeout, unit);
    }

    @Override
    public V rightPop() {
        return template.opsForList().rightPop(key);
    }

    @Override
    public List<V> rightPop(long count) {
        return template.opsForList().rightPop(key, count);
    }

    @Override
    public V rightPop(long timeout, TimeUnit unit) {
        return template.opsForList().rightPop(key, timeout, unit);
    }

    @Override
    public V rightPopAndLeftPush(String sourceKey, String destinationKey) {
        return template.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    @Override
    public V rightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) {
        return template.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
    }
}
