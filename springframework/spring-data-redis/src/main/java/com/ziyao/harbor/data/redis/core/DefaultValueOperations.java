package com.ziyao.harbor.data.redis.core;

import com.ziyao.harbor.data.redis.support.serializer.SerializerInformation;
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


    public DefaultValueOperations(RedisTemplate<String, V> operations, long timeout, SerializerInformation metadata) {
        super(operations, timeout, metadata);
    }

    @Override
    public void set(V value) {
        operations.opsForValue().set(key, value);
        expire();
    }

    @Override
    public void set(V value, long timeout, TimeUnit unit) {
        operations.opsForValue().set(key, value, timeout, unit);
    }

    @Override
    public Boolean setIfAbsent(V value) {
        Boolean b = operations.opsForValue().setIfAbsent(key, value);
        expire();
        return b;
    }

    @Override
    public Boolean setIfAbsent(V value, long timeout, TimeUnit unit) {
        return operations.opsForValue().setIfAbsent(key, value, timeout, unit);
    }

    @Override
    public Boolean setIfPresent(V value) {
        Boolean b = operations.opsForValue().setIfAbsent(key, value);
        expire();
        return b;
    }

    @Override
    public Boolean setIfPresent(V value, long timeout, TimeUnit unit) {
        return operations.opsForValue().setIfPresent(key, value, timeout, unit);
    }

    @Override
    public void multiSet(Map<? extends String, ? extends V> map) {
        operations.opsForValue().multiSet(map);
        expire();
    }

    @Override
    public Boolean multiSetIfAbsent(Map<? extends String, ? extends V> map) {
        Boolean b = operations.opsForValue().multiSetIfAbsent(map);
        expire();
        return b;
    }

    @Override
    public V get() {
        return operations.opsForValue().get(key);
    }

    @Override
    public V getAndDelete() {
        return operations.opsForValue().getAndDelete(key);
    }

    @Override
    public V getAndExpire(long timeout, TimeUnit unit) {
        return operations.opsForValue().getAndExpire(key, timeout, unit);
    }

    @Override
    public V getAndExpire(Duration timeout) {
        return operations.opsForValue().getAndExpire(key, timeout);
    }

    @Override
    public V getAndPersist() {
        return operations.opsForValue().getAndPersist(key);
    }

    @Override
    public V getAndSet(V value) {
        V v = operations.opsForValue().getAndSet(key, value);
        expire();
        return v;
    }

    @Override
    public List<V> multiGet(Collection<String> keys) {
        return operations.opsForValue().multiGet(keys);
    }

    @Override
    public Long increment() {
        return operations.opsForValue().increment(key);
    }

    @Override
    public Long increment(long delta) {
        return operations.opsForValue().increment(key, delta);
    }

    @Override
    public Double increment(double delta) {
        return operations.opsForValue().increment(key, delta);
    }

    @Override
    public Long decrement() {
        return operations.opsForValue().decrement(key);
    }

    @Override
    public Long decrement(long delta) {
        return operations.opsForValue().decrement(key, delta);
    }

    @Override
    public Integer append(String value) {
        return operations.opsForValue().append(key, value);
    }

    @Override
    public String get(long start, long end) {
        return operations.opsForValue().get(key, start, end);
    }

    @Override
    public void set(V value, long offset) {
        operations.opsForValue().set(key, value, offset);
        expire();
    }

    @Override
    public Long size() {
        return operations.opsForValue().size(key);
    }

    @Override
    public Boolean setBit(long offset, boolean value) {
        return operations.opsForValue().setBit(key, offset, value);
    }

    @Override
    public Boolean getBit(long offset) {
        return operations.opsForValue().getBit(key, offset);
    }

    @Override
    public List<Long> bitField(BitFieldSubCommands subCommands) {
        return operations.opsForValue().bitField(key, subCommands);
    }

}
