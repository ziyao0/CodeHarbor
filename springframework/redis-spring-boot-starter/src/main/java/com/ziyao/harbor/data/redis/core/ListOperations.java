package com.ziyao.harbor.data.redis.core;

import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
public interface ListOperations<V> {
    /**
     * Get elements between {@code begin} and {@code end} from list at {@code key}.
     *
     * @param start start
     * @param end   end
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/lrange">Redis Documentation: LRANGE</a>
     */
    @Nullable
    List<V> range(long start, long end);

    /**
     * Trim list at {@code key} to elements between {@code start} and {@code end}.
     *
     * @param start start
     * @param end   end
     * @see <a href="https://redis.io/commands/ltrim">Redis Documentation: LTRIM</a>
     */
    void trim(long start, long end);

    /**
     * Get the size of list stored at {@code key}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/llen">Redis Documentation: LLEN</a>
     */
    @Nullable
    Long size();

    /**
     * Prepend {@code value} to {@code key}.
     *
     * @param value v
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/lpush">Redis Documentation: LPUSH</a>
     */
    @Nullable
    Long leftPush(V value);

    /**
     * Prepend {@code values} to {@code key}.
     *
     * @param values vs
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/lpush">Redis Documentation: LPUSH</a>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    Long leftPushAll(V... values);

    /**
     * Prepend {@code values} to {@code key}.
     *
     * @param values must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/lpush">Redis Documentation: LPUSH</a>
     * @since 1.5
     */
    @Nullable
    Long leftPushAll(Collection<V> values);

    /**
     * Prepend {@code values} to {@code key} only if the list exists.
     *
     * @param value v
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/lpushx">Redis Documentation: LPUSHX</a>
     */
    @Nullable
    Long leftPushIfPresent(V value);

    /**
     * Insert {@code value} to {@code key} before {@code pivot}.
     *
     * @param pivot must not be {@literal null}.
     * @param value v
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/linsert">Redis Documentation: LINSERT</a>
     */
    @Nullable
    Long leftPush(V pivot, V value);

    /**
     * Append {@code value} to {@code key}.
     *
     * @param value v
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/rpush">Redis Documentation: RPUSH</a>
     */
    @Nullable
    Long rightPush(V value);

    /**
     * Append {@code values} to {@code key}.
     *
     * @param values vs
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/rpush">Redis Documentation: RPUSH</a>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    Long rightPushAll(V... values);

    /**
     * Append {@code values} to {@code key}.
     *
     * @param values v
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/rpush">Redis Documentation: RPUSH</a>
     * @since 1.5
     */
    @Nullable
    Long rightPushAll(Collection<V> values);

    /**
     * Append {@code values} to {@code key} only if the list exists.
     *
     * @param value v
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/rpushx">Redis Documentation: RPUSHX</a>
     */
    @Nullable
    Long rightPushIfPresent(V value);

    /**
     * Insert {@code value} to {@code key} after {@code pivot}.
     *
     * @param pivot must not be {@literal null}.
     * @param value v
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/linsert">Redis Documentation: LINSERT</a>
     */
    @Nullable
    Long rightPush(V pivot, V value);


    /**
     * Set the {@code value} list element at {@code index}.
     *
     * @param index index
     * @param value v
     * @see <a href="https://redis.io/commands/lset">Redis Documentation: LSET</a>
     */
    void set(long index, V value);

    /**
     * Removes the first {@code count} occurrences of {@code value} from the list stored at {@code key}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/lrem">Redis Documentation: LREM</a>
     */
    @Nullable
    Long remove(long count, Object value);

    /**
     * Get element at {@code index} form list at {@code key}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/lindex">Redis Documentation: LINDEX</a>
     */
    @Nullable
    V index(long index);

    /**
     * Returns the index of the first occurrence of the specified value in the list at at {@code key}. <br />
     * Requires Redis 6.0.6 or newer.
     *
     * @param value must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction or when not contained in list.
     * @see <a href="https://redis.io/commands/lpos">Redis Documentation: LPOS</a>
     * @since 2.4
     */
    @Nullable
    Long indexOf(V value);

    /**
     * Returns the index of the last occurrence of the specified value in the list at at {@code key}. <br />
     * Requires Redis 6.0.6 or newer.
     *
     * @param value must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction or when not contained in list.
     * @see <a href="https://redis.io/commands/lpos">Redis Documentation: LPOS</a>
     * @since 2.4
     */
    @Nullable
    Long lastIndexOf(V value);

    /**
     * Removes and returns first element in list stored at {@code key}.
     *
     * @return can be {@literal null}.
     * @see <a href="https://redis.io/commands/lpop">Redis Documentation: LPOP</a>
     */
    @Nullable
    V leftPop();

    /**
     * Removes and returns first {@code} elements in list stored at {@code key}.
     *
     * @return can be {@literal null}.
     * @see <a href="https://redis.io/commands/lpop">Redis Documentation: LPOP</a>
     * @since 2.6
     */
    @Nullable
    List<V> leftPop(long count);

    /**
     * Removes and returns first element from lists stored at {@code key} . <br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     *
     * @param unit must not be {@literal null}.
     * @return can be {@literal null}.
     * @see <a href="https://redis.io/commands/blpop">Redis Documentation: BLPOP</a>
     */
    @Nullable
    V leftPop(long timeout, TimeUnit unit);

    /**
     * Removes and returns first element from lists stored at {@code key} . <br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     *
     * @param timeout must not be {@literal null}.
     * @return can be {@literal null}.
     * @throws IllegalArgumentException if the timeout is {@literal null} or negative.
     * @see <a href="https://redis.io/commands/blpop">Redis Documentation: BLPOP</a>
     * @since 2.3
     */
    @Nullable
    default V leftPop(Duration timeout) {

        Assert.notNull(timeout, "Timeout must not be null");
        Assert.isTrue(!timeout.isNegative(), "Timeout must not be negative");

        return leftPop(TimeoutUtils.toSeconds(timeout), TimeUnit.SECONDS);
    }

    /**
     * Removes and returns last element in list stored at {@code key}.
     *
     * @return can be {@literal null}.
     * @see <a href="https://redis.io/commands/rpop">Redis Documentation: RPOP</a>
     */
    @Nullable
    V rightPop();

    /**
     * Removes and returns last {@code} elements in list stored at {@code key}.
     *
     * @return can be {@literal null}.
     * @see <a href="https://redis.io/commands/rpop">Redis Documentation: RPOP</a>
     * @since 2.6
     */
    @Nullable
    List<V> rightPop(long count);

    /**
     * Removes and returns last element from lists stored at {@code key}. <br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     *
     * @param unit must not be {@literal null}.
     * @return can be {@literal null}.
     * @see <a href="https://redis.io/commands/brpop">Redis Documentation: BRPOP</a>
     */
    @Nullable
    V rightPop(long timeout, TimeUnit unit);

    /**
     * Removes and returns last element from lists stored at {@code key}. <br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     *
     * @param timeout must not be {@literal null}.
     * @return can be {@literal null}.
     * @see <a href="https://redis.io/commands/brpop">Redis Documentation: BRPOP</a>
     * @since 2.3
     */
    @Nullable
    default V rightPop(Duration timeout) {

        Assert.notNull(timeout, "Timeout must not be null");
        Assert.isTrue(!timeout.isNegative(), "Timeout must not be negative");

        return rightPop(TimeoutUtils.toSeconds(timeout), TimeUnit.SECONDS);
    }

    /**
     * Remove the last element from list at {@code sourceKey}, append it to {@code destinationKey} and return its value.
     *
     * @param sourceKey      must not be {@literal null}.
     * @param destinationKey must not be {@literal null}.
     * @return can be {@literal null}.
     * @see <a href="https://redis.io/commands/rpoplpush">Redis Documentation: RPOPLPUSH</a>
     */
    @Nullable
    V rightPopAndLeftPush(String sourceKey, String destinationKey);

    /**
     * Remove the last element from list at {@code srcKey}, append it to {@code dstKey} and return its value.<br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     *
     * @param sourceKey      must not be {@literal null}.
     * @param destinationKey must not be {@literal null}.
     * @param unit           must not be {@literal null}.
     * @return can be {@literal null}.
     * @see <a href="https://redis.io/commands/brpoplpush">Redis Documentation: BRPOPLPUSH</a>
     */
    @Nullable
    V rightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit);

    /**
     * Remove the last element from list at {@code srcKey}, append it to {@code dstKey} and return its value.<br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     *
     * @param sourceKey      must not be {@literal null}.
     * @param destinationKey must not be {@literal null}.
     * @param timeout        must not be {@literal null}.
     * @return can be {@literal null}.
     * @throws IllegalArgumentException if the timeout is {@literal null} or negative.
     * @see <a href="https://redis.io/commands/brpoplpush">Redis Documentation: BRPOPLPUSH</a>
     * @since 2.3
     */
    @Nullable
    default V rightPopAndLeftPush(String sourceKey, String destinationKey, Duration timeout) {

        Assert.notNull(timeout, "Timeout must not be null");
        Assert.isTrue(!timeout.isNegative(), "Timeout must not be negative");

        return rightPopAndLeftPush(sourceKey, destinationKey, TimeoutUtils.toSeconds(timeout), TimeUnit.SECONDS);
    }
}
