package com.ziyao.harbor.data.redis.core;

import org.springframework.data.redis.connection.zset.Aggregate;
import org.springframework.data.redis.connection.zset.Weights;
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

    public DefaultZSetOperations(RedisTemplate<String, V> template, String redisKey) {
        super(template, redisKey);
    }

    @Override
    public Boolean add(V value, double score) {
        return template.opsForZSet().add(redisKey, value, score);
    }

    @Override
    public Long add(Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> typedTuples) {
        return template.opsForZSet().add(redisKey, typedTuples);
    }

    @Override
    public Long remove(Object... values) {
        return template.opsForZSet().remove(redisKey, values);
    }

    @Override
    public Double incrementScore(V value, double delta) {
        return template.opsForZSet().incrementScore(redisKey, value, delta);
    }

    @Override
    public Long rank(Object o) {
        return template.opsForZSet().rank(redisKey, o);
    }

    @Override
    public Long reverseRank(Object o) {
        return template.opsForZSet().reverseRank(redisKey, o);
    }

    @Override
    public Set<V> range(long start, long end) {
        return template.opsForZSet().range(redisKey, start, end);
    }

    @Override
    public Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> rangeWithScores(long start, long end) {
        return template.opsForZSet().rangeWithScores(redisKey, start, end);
    }

    @Override
    public Set<V> rangeByScore(double min, double max) {
        return template.opsForZSet().rangeByScore(redisKey, min, max);
    }

    @Override
    public Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> rangeByScoreWithScores(double min, double max) {
        return template.opsForZSet().rangeByScoreWithScores(redisKey, min, max);
    }

    @Override
    public Set<V> rangeByScore(double min, double max, long offset, long count) {
        return template.opsForZSet().rangeByScore(redisKey, min, max, offset, count);
    }

    @Override
    public Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> rangeByScoreWithScores(double min, double max, long offset, long count) {
        return template.opsForZSet().rangeByScoreWithScores(redisKey, min, max, offset, count);
    }

    @Override
    public Set<V> reverseRange(long start, long end) {
        return template.opsForZSet().reverseRange(redisKey, start, end);
    }

    @Override
    public Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> reverseRangeWithScores(long start, long end) {
        return template.opsForZSet().reverseRangeWithScores(redisKey, start, end);
    }

    @Override
    public Set<V> reverseRangeByScore(double min, double max) {
        return template.opsForZSet().reverseRangeByScore(redisKey, min, max);
    }

    @Override
    public Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> reverseRangeByScoreWithScores(double min, double max) {
        return template.opsForZSet().reverseRangeByScoreWithScores(redisKey, min, max);
    }

    @Override
    public Set<V> reverseRangeByScore(double min, double max, long offset, long count) {
        return template.opsForZSet().reverseRangeByScore(redisKey, min, max, offset, count);
    }

    @Override
    public Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> reverseRangeByScoreWithScores(double min, double max, long offset, long count) {
        return template.opsForZSet().reverseRangeByScoreWithScores(redisKey, min, max, offset, count);
    }

    @Override
    public Long count(double min, double max) {
        return template.opsForZSet().count(redisKey, min, max);
    }

    @Override
    public Long size() {
        return template.opsForZSet().size(redisKey);
    }

    @Override
    public Long zCard() {
        return template.opsForZSet().zCard(redisKey);
    }

    @Override
    public Double score(Object o) {
        return template.opsForZSet().score(redisKey, o);
    }

    @Override
    public Long removeRange(long start, long end) {
        return template.opsForZSet().removeRange(redisKey, start, end);
    }

    @Override
    public Long removeRangeByScore(double min, double max) {
        return template.opsForZSet().removeRangeByScore(redisKey, min, max);
    }

    @Override
    public Long unionAndStore(String otherKey, String destKey) {
        return template.opsForZSet().unionAndStore(redisKey, otherKey, destKey);
    }

    @Override
    public Long unionAndStore(Collection<String> otherKeys, String destKey) {
        return template.opsForZSet().unionAndStore(redisKey, otherKeys, destKey);
    }

    @Override
    public Long unionAndStore(Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights) {
        return template.opsForZSet().unionAndStore(redisKey, otherKeys, destKey, aggregate, weights);
    }

    @Override
    public Long intersectAndStore(String otherKey, String destKey) {
        return template.opsForZSet().intersectAndStore(redisKey, otherKey, destKey);
    }

    @Override
    public Long intersectAndStore(Collection<String> otherKeys, String destKey) {
        return template.opsForZSet().intersectAndStore(redisKey, otherKeys, destKey);
    }

    @Override
    public Long intersectAndStore(Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights) {
        return template.opsForZSet().intersectAndStore(redisKey, otherKeys, destKey, aggregate, weights);
    }

    @Override
    public Cursor<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> scan(ScanOptions options) {
        return template.opsForZSet().scan(redisKey, options);
    }
}
