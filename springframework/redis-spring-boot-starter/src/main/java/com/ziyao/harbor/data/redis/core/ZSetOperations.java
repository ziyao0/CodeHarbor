package com.ziyao.harbor.data.redis.core;

import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
public interface ZSetOperations<V> {

    /**
     * Add {@code value} to a sorted set at {@code key}, or update its {@code score} if it already exists.
     *
     * @param score the score.
     * @param value the value.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zadd">Redis Documentation: ZADD</a>
     */
    @Nullable
    Boolean add(V value, double score);

    /**
     * Add {@code tuples} to a sorted set at {@code key}, or update its {@code score} if it already exists.
     *
     * @param tuples must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zadd">Redis Documentation: ZADD</a>
     */
    @Nullable
    Long add(Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> tuples);

    /**
     * Remove {@code values} from sorted set. Return number of removed elements.
     *
     * @param values must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrem">Redis Documentation: ZREM</a>
     */
    @Nullable
    Long remove(Object... values);

    /**
     * Increment the score of element with {@code value} in sorted set by {@code increment}.
     *
     * @param value the value.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zincrby">Redis Documentation: ZINCRBY</a>
     */
    @Nullable
    Double incrementScore(V value, double delta);

    /**
     * Determine the index of element with {@code value} in a sorted set.
     *
     * @param o the value.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrank">Redis Documentation: ZRANK</a>
     */
    @Nullable
    Long rank(Object o);

    /**
     * Determine the index of element with {@code value} in a sorted set when scored high to low.
     *
     * @param o the value.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrank">Redis Documentation: ZREVRANK</a>
     */
    @Nullable
    Long reverseRank(Object o);

    /**
     * Get elements between {@code start} and {@code end} from sorted set.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrange">Redis Documentation: ZRANGE</a>
     */
    @Nullable
    Set<V> range(long start, long end);

    /**
     * Get set of {@code Tuple}s between {@code start} and {@code end} from sorted set.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrange">Redis Documentation: ZRANGE</a>
     */
    @Nullable
    Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> rangeWithScores(long start, long end);

    /**
     * Get elements where score is between {@code min} and {@code max} from sorted set.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrangebyscore">Redis Documentation: ZRANGEBYSCORE</a>
     */
    @Nullable
    Set<V> rangeByScore(double min, double max);

    /**
     * Get set of {@code Tuple}s where score is between {@code min} and {@code max} from sorted set.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrangebyscore">Redis Documentation: ZRANGEBYSCORE</a>
     */
    @Nullable
    Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> rangeByScoreWithScores(double min, double max);

    /**
     * Get elements in range from {@code start} to {@code end} where score is between {@code min} and {@code max} from
     * sorted set.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrangebyscore">Redis Documentation: ZRANGEBYSCORE</a>
     */
    @Nullable
    Set<V> rangeByScore(double min, double max, long offset, long count);

    /**
     * Get set of {@code Tuple}s in range from {@code start} to {@code end} where score is between {@code min} and
     * {@code max} from sorted set.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrangebyscore">Redis Documentation: ZRANGEBYSCORE</a>
     */
    @Nullable
    Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> rangeByScoreWithScores(double min, double max, long offset, long count);

    /**
     * Get elements in range from {@code start} to {@code end} from sorted set ordered from high to low.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrange">Redis Documentation: ZREVRANGE</a>
     */
    @Nullable
    Set<V> reverseRange(long start, long end);

    /**
     * Get set of {@code Tuple}s in range from {@code start} to {@code end} from sorted set ordered from high to low.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrange">Redis Documentation: ZREVRANGE</a>
     */
    @Nullable
    Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> reverseRangeWithScores(long start, long end);

    /**
     * Get elements where score is between {@code min} and {@code max} from sorted set ordered from high to low.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrangebyscore">Redis Documentation: ZREVRANGEBYSCORE</a>
     */
    @Nullable
    Set<V> reverseRangeByScore(double min, double max);

    /**
     * Get set of {@code Tuple} where score is between {@code min} and {@code max} from sorted set ordered from high to
     * low.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrangebyscore">Redis Documentation: ZREVRANGEBYSCORE</a>
     */
    @Nullable
    Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> reverseRangeByScoreWithScores(double min, double max);

    /**
     * Get elements in range from {@code start} to {@code end} where score is between {@code min} and {@code max} from
     * sorted set ordered high -> low.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrangebyscore">Redis Documentation: ZREVRANGEBYSCORE</a>
     */
    @Nullable
    Set<V> reverseRangeByScore(double min, double max, long offset, long count);

    /**
     * Get set of {@code Tuple} in range from {@code start} to {@code end} where score is between {@code min} and
     * {@code max} from sorted set ordered high -> low.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrangebyscore">Redis Documentation: ZREVRANGEBYSCORE</a>
     */
    @Nullable
    Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> reverseRangeByScoreWithScores(double min, double max, long offset, long count);

    /**
     * Count number of elements within sorted set with scores between {@code min} and {@code max}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zcount">Redis Documentation: ZCOUNT</a>
     */
    @Nullable
    Long count(double min, double max);

    /**
     * Returns the number of elements of the sorted set stored with given {@code key}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see #zCard()
     * @see <a href="https://redis.io/commands/zcard">Redis Documentation: ZCARD</a>
     */
    @Nullable
    Long size();

    /**
     * Get the size of sorted set with {@code key}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zcard">Redis Documentation: ZCARD</a>
     * @since 1.3
     */
    @Nullable
    Long zCard();

    /**
     * Get the score of element with {@code value} from sorted set with key {@code key}.
     *
     * @param o the value.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zscore">Redis Documentation: ZSCORE</a>
     */
    @Nullable
    Double score(Object o);

    /**
     * Remove elements in range between {@code start} and {@code end} from sorted set with {@code key}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zremrangebyrank">Redis Documentation: ZREMRANGEBYRANK</a>
     */
    @Nullable
    Long removeRange(long start, long end);

    /**
     * Remove elements with scores between {@code min} and {@code max} from sorted set with {@code key}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zremrangebyscore">Redis Documentation: ZREMRANGEBYSCORE</a>
     */
    @Nullable
    Long removeRangeByScore(double min, double max);

    /**
     * Union sorted sets at {@code key} and {@code otherKeys} and store result in destination {@code destKey}.
     *
     * @param otherKey must not be {@literal null}.
     * @param destKey  must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zunionstore">Redis Documentation: ZUNIONSTORE</a>
     */
    @Nullable
    Long unionAndStore(String otherKey, String destKey);

    /**
     * Union sorted sets at {@code key} and {@code otherKeys} and store result in destination {@code destKey}.
     *
     * @param otherKeys must not be {@literal null}.
     * @param destKey   must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zunionstore">Redis Documentation: ZUNIONSTORE</a>
     */
    @Nullable
    Long unionAndStore(Collection<String> otherKeys, String destKey);

    /**
     * Union sorted sets at {@code key} and {@code otherKeys} and store result in destination {@code destKey}.
     *
     * @param otherKeys must not be {@literal null}.
     * @param destKey   must not be {@literal null}.
     * @param aggregate must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zunionstore">Redis Documentation: ZUNIONSTORE</a>
     * @since 2.1
     */
    @Nullable
    default Long unionAndStore(Collection<String> otherKeys, String destKey, RedisZSetCommands.Aggregate aggregate) {
        return unionAndStore(otherKeys, destKey, aggregate, RedisZSetCommands.Weights.fromSetCount(1 + otherKeys.size()));
    }

    /**
     * Union sorted sets at {@code key} and {@code otherKeys} and store result in destination {@code destKey}.
     *
     * @param otherKeys must not be {@literal null}.
     * @param destKey   must not be {@literal null}.
     * @param aggregate must not be {@literal null}.
     * @param weights   must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zunionstore">Redis Documentation: ZUNIONSTORE</a>
     * @since 2.1
     */
    @Nullable
    Long unionAndStore(Collection<String> otherKeys, String destKey, RedisZSetCommands.Aggregate aggregate, RedisZSetCommands.Weights weights);

    /**
     * Intersect sorted sets at {@code key} and {@code otherKey} and store result in destination {@code destKey}.
     *
     * @param otherKey must not be {@literal null}.
     * @param destKey  must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zinterstore">Redis Documentation: ZINTERSTORE</a>
     */
    @Nullable
    Long intersectAndStore(String otherKey, String destKey);

    /**
     * Intersect sorted sets at {@code key} and {@code otherKeys} and store result in destination {@code destKey}.
     *
     * @param otherKeys must not be {@literal null}.
     * @param destKey   must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zinterstore">Redis Documentation: ZINTERSTORE</a>
     */
    @Nullable
    Long intersectAndStore(Collection<String> otherKeys, String destKey);

    /**
     * Intersect sorted sets at {@code key} and {@code otherKeys} and store result in destination {@code destKey}.
     *
     * @param otherKeys must not be {@literal null}.
     * @param destKey   must not be {@literal null}.
     * @param aggregate must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zinterstore">Redis Documentation: ZINTERSTORE</a>
     * @since 2.1
     */
    @Nullable
    default Long intersectAndStore(Collection<String> otherKeys, String destKey, RedisZSetCommands.Aggregate aggregate) {
        return intersectAndStore(otherKeys, destKey, aggregate, RedisZSetCommands.Weights.fromSetCount(1 + otherKeys.size()));
    }

    /**
     * Intersect sorted sets at {@code key} and {@code otherKeys} and store result in destination {@code destKey}.
     *
     * @param otherKeys must not be {@literal null}.
     * @param destKey   must not be {@literal null}.
     * @param aggregate must not be {@literal null}.
     * @param weights   must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zinterstore">Redis Documentation: ZINTERSTORE</a>
     * @since 2.1
     */
    @Nullable
    Long intersectAndStore(Collection<String> otherKeys, String destKey, RedisZSetCommands.Aggregate aggregate, RedisZSetCommands.Weights weights);

    /**
     * Iterate over elements in zset at {@code key}. <br />
     * <strong>Important:</strong> Call {@link Cursor#close()} when done to avoid resource leak.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zscan">Redis Documentation: ZSCAN</a>
     * @since 1.4
     */
    Cursor<org.springframework.data.redis.core.ZSetOperations.TypedTuple<V>> scan(ScanOptions options);

}
