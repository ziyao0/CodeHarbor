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
    public DefaultListOperations(RedisTemplate<String, V> template, String redisKey) {
        super(template, redisKey);
    }

    @Override
    public List<V> range(long start, long end) {
        return template.opsForList().range(redisKey, start, end);
    }

    @Override
    public void trim(long start, long end) {
        template.opsForList().trim(redisKey, start, end);
    }

    @Override
    public Long size() {
        return template.opsForList().size(redisKey);
    }

    @Override
    public Long leftPush(V value) {
        return template.opsForList().leftPush(redisKey, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Long leftPushAll(V... values) {
        return template.opsForList().leftPushAll(redisKey, values);
    }

    @Override
    public Long leftPushAll(Collection<V> values) {
        return template.opsForList().leftPushAll(redisKey, values);
    }

    @Override
    public Long leftPushIfPresent(V value) {
        return template.opsForList().leftPushIfPresent(redisKey, value);
    }

    @Override
    public Long leftPush(V pivot, V value) {
        return template.opsForList().leftPush(redisKey, pivot, value);
    }

    @Override
    public Long rightPush(V value) {
        return template.opsForList().rightPush(redisKey, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Long rightPushAll(V... values) {
        return template.opsForList().rightPushAll(redisKey, values);
    }

    @Override
    public Long rightPushAll(Collection<V> values) {
        return template.opsForList().rightPushAll(redisKey, values);
    }

    @Override
    public Long rightPushIfPresent(V value) {
        return template.opsForList().rightPushIfPresent(redisKey, value);
    }

    @Override
    public Long rightPush(V pivot, V value) {
        return template.opsForList().rightPush(redisKey, pivot, value);
    }

    @Override
    public void set(long index, V value) {
        template.opsForList().set(redisKey, index, value);
    }

    @Override
    public Long remove(long count, Object value) {
        return template.opsForList().remove(redisKey, count, value);
    }

    @Override
    public V index(long index) {
        return template.opsForList().index(redisKey, index);
    }

    @Override
    public Long indexOf(V value) {
        return template.opsForList().indexOf(redisKey, value);
    }

    @Override
    public Long lastIndexOf(V value) {
        return template.opsForList().lastIndexOf(redisKey, value);
    }

    @Override
    public V leftPop() {
        return template.opsForList().leftPop(redisKey);
    }

    @Override
    public List<V> leftPop(long count) {
        return template.opsForList().leftPop(redisKey, count);
    }

    @Override
    public V leftPop(long timeout, TimeUnit unit) {
        return template.opsForList().leftPop(redisKey, timeout, unit);
    }

    @Override
    public V rightPop() {
        return template.opsForList().rightPop(redisKey);
    }

    @Override
    public List<V> rightPop(long count) {
        return template.opsForList().rightPop(redisKey, count);
    }

    @Override
    public V rightPop(long timeout, TimeUnit unit) {
        return template.opsForList().rightPop(redisKey, timeout, unit);
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
