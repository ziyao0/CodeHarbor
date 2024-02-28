package com.ziyao.harbor.data.redis.core;

import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
public class DefaultValueOperations<V> extends AbstractOperations<V> implements ValueOperations<V> {


    public DefaultValueOperations(RedisTemplate<String, V> template, String key) {
        super(template, key);
    }

    @Override
    public void set(V value) {
        template.opsForValue().set(key, value);
    }

    @Override
    public void set(V value, long timeout, TimeUnit unit) {
        template.opsForValue().set(key, value, timeout, unit);
    }

    @Override
    public Boolean setIfAbsent(V value) {
        return template.opsForValue().setIfAbsent(key, value);
    }

    @Override
    public Boolean setIfAbsent(V value, long timeout, TimeUnit unit) {
        return template.opsForValue().setIfAbsent(key, value, timeout, unit);
    }

    @Override
    public Boolean setIfPresent(V value) {
        return template.opsForValue().setIfAbsent(key, value);
    }

    @Override
    public Boolean setIfPresent(V value, long timeout, TimeUnit unit) {
        return template.opsForValue().setIfPresent(key, value, timeout, unit);
    }

    @Override
    public void multiSet(Map<? extends String, ? extends V> map) {
        template.opsForValue().multiSet(map);
    }

    @Override
    public Boolean multiSetIfAbsent(Map<? extends String, ? extends V> map) {
        return template.opsForValue().multiSetIfAbsent(map);
    }

    @Override
    public V get() {
        return template.opsForValue().get(key);
    }

    @Override
    public V getAndDelete() {
        return template.opsForValue().getAndDelete(key);
    }

    @Override
    public V getAndExpire(long timeout, TimeUnit unit) {
        return template.opsForValue().getAndExpire(key, timeout, unit);
    }

    @Override
    public V getAndExpire(Duration timeout) {
        return template.opsForValue().getAndExpire(key, timeout);
    }

    @Override
    public V getAndPersist() {
        return template.opsForValue().getAndPersist(key);
    }

    @Override
    public V getAndSet(V value) {
        return template.opsForValue().getAndSet(key, value);
    }

    @Override
    public List<V> multiGet(Collection<String> keys) {
        return template.opsForValue().multiGet(keys);
    }

    @Override
    public Long increment() {
        return template.opsForValue().increment(key);
    }

    @Override
    public Long increment(long delta) {
        return template.opsForValue().increment(key, delta);
    }

    @Override
    public Double increment(double delta) {
        return template.opsForValue().increment(key, delta);
    }

    @Override
    public Long decrement() {
        return template.opsForValue().decrement(key);
    }

    @Override
    public Long decrement(long delta) {
        return template.opsForValue().decrement(key, delta);
    }

    @Override
    public Integer append(String value) {
        return template.opsForValue().append(key, value);
    }

    @Override
    public String get(long start, long end) {
        return template.opsForValue().get(key, start, end);
    }

    @Override
    public void set(V value, long offset) {
        template.opsForValue().set(key, value, offset);
    }

    @Override
    public Long size() {
        return template.opsForValue().size(key);
    }

    @Override
    public Boolean setBit(long offset, boolean value) {
        return template.opsForValue().setBit(key, offset, value);
    }

    @Override
    public Boolean getBit(long offset) {
        return template.opsForValue().getBit(key, offset);
    }

    @Override
    public List<Long> bitField(BitFieldSubCommands subCommands) {
        return template.opsForValue().bitField(key, subCommands);
    }

}
