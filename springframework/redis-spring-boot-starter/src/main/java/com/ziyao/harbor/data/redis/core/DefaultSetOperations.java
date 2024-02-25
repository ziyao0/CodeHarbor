package com.ziyao.harbor.data.redis.core;

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

    public DefaultSetOperations(RedisTemplate<String, V> template, String key) {
        super(template, key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Long add(V... values) {
        return template.opsForSet().add(key, values);
    }

    @Override
    public Long remove(Object... values) {
        return template.opsForSet().remove(key, values);
    }

    @Override
    public V pop() {
        return template.opsForSet().pop(key);
    }

    @Override
    public List<V> pop(long count) {
        return template.opsForSet().pop(key, count);
    }

    @Override
    public Boolean move(V value, String destKey) {
        return template.opsForSet().move(key, value, destKey);
    }

    @Override
    public Long size() {
        return template.opsForSet().size(key);
    }

    @Override
    public Boolean isMember(Object o) {
        return template.opsForSet().isMember(key, o);
    }

    @Override
    public Map<Object, Boolean> isMember(Object... objects) {
        return template.opsForSet().isMember(key, objects);
    }

    @Override
    public Set<V> intersect(String otherKey) {
        return template.opsForSet().intersect(key, otherKey);
    }

    @Override
    public Set<V> intersect(String key, Collection<String> otherKeys) {
        return template.opsForSet().intersect(key, otherKeys);
    }

    @Override
    public Set<V> intersect(Collection<String> keys) {
        return template.opsForSet().intersect(keys);
    }

    @Override
    public Long intersectAndStore(String otherKey, String destKey) {
        return template.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    @Override
    public Long intersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return template.opsForSet().intersectAndStore(key, otherKeys, destKey);
    }

    @Override
    public Long intersectAndStore(Collection<String> keys, String destKey) {
        return template.opsForSet().intersectAndStore(keys, destKey);
    }

    @Override
    public Set<V> union(String otherKey) {
        return template.opsForSet().union(key, otherKey);
    }

    @Override
    public Set<V> union(String key, Collection<String> otherKeys) {
        return template.opsForSet().union(key, otherKeys);
    }

    @Override
    public Set<V> union(Collection<String> keys) {
        return template.opsForSet().union(keys);
    }

    @Override
    public Long unionAndStore(String otherKey, String destKey) {
        return template.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    @Override
    public Long unionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return template.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    @Override
    public Long unionAndStore(Collection<String> keys, String destKey) {
        return template.opsForSet().unionAndStore(keys, destKey);
    }

    @Override
    public Set<V> difference(String otherKey) {
        return template.opsForSet().difference(key, otherKey);
    }

    @Override
    public Set<V> difference(String key, Collection<String> otherKeys) {
        return template.opsForSet().difference(key, otherKeys);
    }

    @Override
    public Set<V> difference(Collection<String> keys) {
        return template.opsForSet().difference(keys);
    }

    @Override
    public Long differenceAndStore(String otherKey, String destKey) {
        return template.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    @Override
    public Long differenceAndStore(String key, Collection<String> otherKeys, String destKey) {
        return template.opsForSet().differenceAndStore(key, otherKeys, destKey);
    }

    @Override
    public Long differenceAndStore(Collection<String> keys, String destKey) {
        return template.opsForSet().differenceAndStore(keys, destKey);
    }

    @Override
    public Set<V> members() {
        return template.opsForSet().members(key);
    }

    @Override
    public V randomMember() {
        return template.opsForSet().randomMember(key);
    }

    @Override
    public Set<V> distinctRandomMembers(long count) {
        return template.opsForSet().distinctRandomMembers(key, count);
    }

    @Override
    public List<V> randomMembers(long count) {
        return template.opsForSet().randomMembers(key, count);
    }

    @Override
    public Cursor<V> scan(ScanOptions options) {
        return template.opsForSet().scan(key, options);
    }
}
