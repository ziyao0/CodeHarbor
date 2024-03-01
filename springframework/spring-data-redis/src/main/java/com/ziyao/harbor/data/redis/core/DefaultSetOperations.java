package com.ziyao.harbor.data.redis.core;

import com.ziyao.harbor.data.redis.support.serializer.SerializerInformation;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
public class DefaultSetOperations<V> extends AbstractOperations<V> implements SetOperations<V> {

    public DefaultSetOperations(RedisTemplate<String, V> operations, long timeout, SerializerInformation metadata) {
        super(operations, timeout, metadata);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Long add(V... values) {
        Long add = operations.opsForSet().add(key, values);
        expire();
        return add;
    }

    @Override
    public Long remove(Object... values) {
        return operations.opsForSet().remove(key, values);
    }

    @Override
    public V pop() {
        return operations.opsForSet().pop(key);
    }

    @Override
    public List<V> pop(long count) {
        return operations.opsForSet().pop(key, count);
    }

    @Override
    public Boolean move(V value, String destKey) {
        return operations.opsForSet().move(key, value, destKey);
    }

    @Override
    public Long size() {
        return operations.opsForSet().size(key);
    }

    @Override
    public Boolean isMember(Object o) {
        return operations.opsForSet().isMember(key, o);
    }

    @Override
    public Map<Object, Boolean> isMember(Object... objects) {
        return operations.opsForSet().isMember(key, objects);
    }

    @Override
    public Set<V> intersect(String otherKey) {
        return operations.opsForSet().intersect(key, otherKey);
    }

    @Override
    public Set<V> intersect(String key, Collection<String> otherKeys) {
        return operations.opsForSet().intersect(key, otherKeys);
    }

    @Override
    public Set<V> intersect(Collection<String> keys) {
        return operations.opsForSet().intersect(keys);
    }

    @Override
    public Long intersectAndStore(String otherKey, String destKey) {
        return operations.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    @Override
    public Long intersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return operations.opsForSet().intersectAndStore(key, otherKeys, destKey);
    }

    @Override
    public Long intersectAndStore(Collection<String> keys, String destKey) {
        return operations.opsForSet().intersectAndStore(keys, destKey);
    }

    @Override
    public Set<V> union(String otherKey) {
        return operations.opsForSet().union(key, otherKey);
    }

    @Override
    public Set<V> union(String key, Collection<String> otherKeys) {
        return operations.opsForSet().union(key, otherKeys);
    }

    @Override
    public Set<V> union(Collection<String> keys) {
        return operations.opsForSet().union(keys);
    }

    @Override
    public Long unionAndStore(String otherKey, String destKey) {
        return operations.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    @Override
    public Long unionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return operations.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    @Override
    public Long unionAndStore(Collection<String> keys, String destKey) {
        return operations.opsForSet().unionAndStore(keys, destKey);
    }

    @Override
    public Set<V> difference(String otherKey) {
        return operations.opsForSet().difference(key, otherKey);
    }

    @Override
    public Set<V> difference(String key, Collection<String> otherKeys) {
        return operations.opsForSet().difference(key, otherKeys);
    }

    @Override
    public Set<V> difference(Collection<String> keys) {
        return operations.opsForSet().difference(keys);
    }

    @Override
    public Long differenceAndStore(String otherKey, String destKey) {
        return operations.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    @Override
    public Long differenceAndStore(String key, Collection<String> otherKeys, String destKey) {
        return operations.opsForSet().differenceAndStore(key, otherKeys, destKey);
    }

    @Override
    public Long differenceAndStore(Collection<String> keys, String destKey) {
        return operations.opsForSet().differenceAndStore(keys, destKey);
    }

    @Override
    public Set<V> members() {
        return operations.opsForSet().members(key);
    }

    @Override
    public V randomMember() {
        return operations.opsForSet().randomMember(key);
    }

    @Override
    public Set<V> distinctRandomMembers(long count) {
        return operations.opsForSet().distinctRandomMembers(key, count);
    }

    @Override
    public List<V> randomMembers(long count) {
        return operations.opsForSet().randomMembers(key, count);
    }

    @Override
    public Cursor<V> scan(ScanOptions options) {
        return operations.opsForSet().scan(key, options);
    }
}
