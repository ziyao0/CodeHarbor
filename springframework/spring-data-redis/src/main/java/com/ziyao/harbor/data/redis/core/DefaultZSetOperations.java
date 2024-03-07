package com.ziyao.harbor.data.redis.core;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.Collection;
import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
public class DefaultZSetOperations<V> extends AbstractOperations<V> implements ZSetOperations<V> {

    public DefaultZSetOperations(RedisTemplate<String, V> operations, long timeout) {
        super(operations, timeout);
    }

    @Override
    public Boolean add(V value, double score) {
        return operations.opsForZSet().add(key, value, score);
    }

    @Override
    public Long add(Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> typedTuples) {
        return operations.opsForZSet().add(key, typedTuples);
    }

    @Override
    public Long remove(Object... values) {
        return operations.opsForZSet().remove(key, values);
    }

    @Override
    public Double incrementScore(V value, double delta) {
        return operations.opsForZSet().incrementScore(key, value, delta);
    }

    @Override
    public Long rank(Object o) {
        return operations.opsForZSet().rank(key, o);
    }

    @Override
    public Long reverseRank(Object o) {
        return operations.opsForZSet().reverseRank(key, o);
    }

    @Override
    public Set<V> range(long start, long end) {
        return operations.opsForZSet().range(key, start, end);
    }

    @Override
    public Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> rangeWithScores(long start, long end) {
        return operations.opsForZSet().rangeWithScores(key, start, end);
    }

    @Override
    public Set<V> rangeByScore(double min, double max) {
        return operations.opsForZSet().rangeByScore(key, min, max);
    }

    @Override
    public Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> rangeByScoreWithScores(double min, double max) {
        return operations.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    @Override
    public Set<V> rangeByScore(double min, double max, long offset, long count) {
        return operations.opsForZSet().rangeByScore(key, min, max, offset, count);
    }

    @Override
    public Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> rangeByScoreWithScores(double min, double max, long offset, long count) {
        return operations.opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);
    }

    @Override
    public Set<V> reverseRange(long start, long end) {
        return operations.opsForZSet().reverseRange(key, start, end);
    }

    @Override
    public Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> reverseRangeWithScores(long start, long end) {
        return operations.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    @Override
    public Set<V> reverseRangeByScore(double min, double max) {
        return operations.opsForZSet().reverseRangeByScore(key, min, max);
    }

    @Override
    public Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> reverseRangeByScoreWithScores(double min, double max) {
        return operations.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    @Override
    public Set<V> reverseRangeByScore(double min, double max, long offset, long count) {
        return operations.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
    }

    @Override
    public Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> reverseRangeByScoreWithScores(double min, double max, long offset, long count) {
        return operations.opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
    }

    @Override
    public Long count(double min, double max) {
        return operations.opsForZSet().count(key, min, max);
    }

    @Override
    public Long size() {
        return operations.opsForZSet().size(key);
    }

    @Override
    public Long zCard() {
        return operations.opsForZSet().zCard(key);
    }

    @Override
    public Double score(Object o) {
        return operations.opsForZSet().score(key, o);
    }

    @Override
    public Long removeRange(long start, long end) {
        return operations.opsForZSet().removeRange(key, start, end);
    }

    @Override
    public Long removeRangeByScore(double min, double max) {
        return operations.opsForZSet().removeRangeByScore(key, min, max);
    }

    @Override
    public Long unionAndStore(String otherKey, String destKey) {
        return operations.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    @Override
    public Long unionAndStore(Collection<String> otherKeys, String destKey) {
        return operations.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    @Override
    public Long intersectAndStore(String otherKey, String destKey) {
        return operations.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    @Override
    public Long intersectAndStore(Collection<String> otherKeys, String destKey) {
        return operations.opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }


    @Override
    public Cursor<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> scan(ScanOptions options) {
        return operations.opsForZSet().scan(key, options);
    }
}
