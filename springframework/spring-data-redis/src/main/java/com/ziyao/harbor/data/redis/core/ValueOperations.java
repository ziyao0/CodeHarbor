package com.ziyao.harbor.data.redis.core;

import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
public interface ValueOperations<V> extends KeyAware  {
    /**
     * Set {@code value} for {@code key}.
     *
     * @param value must not be {@literal null}.
     * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
     */
    void set(V value);

    /**
     * Set the {@code value} and expiration {@code timeout} for {@code key}.
     *
     * @param value   must not be {@literal null}.
     * @param timeout the key expiration timeout.
     * @param unit    must not be {@literal null}.
     * @see <a href="https://redis.io/commands/setex">Redis Documentation: SETEX</a>
     */
    void set(V value, long timeout, TimeUnit unit);

    /**
     * Set the {@code value} and expiration {@code timeout} for {@code key}.
     *
     * @param value   must not be {@literal null}.
     * @param timeout must not be {@literal null}.
     * @throws IllegalArgumentException if either {@code key}, {@code value} or {@code timeout} is not present.
     * @see <a href="https://redis.io/commands/setex">Redis Documentation: SETEX</a>
     * @since 2.1
     */
    default void set(V value, Duration timeout) {

        Assert.notNull(timeout, "Timeout must not be null");

        if (TimeoutUtils.hasMillis(timeout)) {
            set(value, timeout.toMillis(), TimeUnit.MILLISECONDS);
        } else {
            set(value, timeout.getSeconds(), TimeUnit.SECONDS);
        }
    }

    /**
     * Set {@code key} to hold the string {@code value} if {@code key} is absent.
     *
     * @param value must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/setnx">Redis Documentation: SETNX</a>
     */
    @Nullable
    Boolean setIfAbsent(V value);

    /**
     * Set {@code key} to hold the string {@code value} and expiration {@code timeout} if {@code key} is absent.
     *
     * @param value   must not be {@literal null}.
     * @param timeout the key expiration timeout.
     * @param unit    must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
     * @since 2.1
     */
    @Nullable
    Boolean setIfAbsent(V value, long timeout, TimeUnit unit);

    /**
     * Set {@code key} to hold the string {@code value} and expiration {@code timeout} if {@code key} is absent.
     *
     * @param value   must not be {@literal null}.
     * @param timeout must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @throws IllegalArgumentException if either {@code key}, {@code value} or {@code timeout} is not present.
     * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
     * @since 2.1
     */
    @Nullable
    default Boolean setIfAbsent(V value, Duration timeout) {

        Assert.notNull(timeout, "Timeout must not be null");

        if (TimeoutUtils.hasMillis(timeout)) {
            return setIfAbsent(value, timeout.toMillis(), TimeUnit.MILLISECONDS);
        }

        return setIfAbsent(value, timeout.getSeconds(), TimeUnit.SECONDS);
    }

    /**
     * Set {@code key} to hold the string {@code value} if {@code key} is present.
     *
     * @param value must not be {@literal null}.
     * @return command result indicating if the key has been set.
     * @throws IllegalArgumentException if either {@code key} or {@code value} is not present.
     * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
     * @since 2.1
     */
    @Nullable
    Boolean setIfPresent(V value);

    /**
     * Set {@code key} to hold the string {@code value} and expiration {@code timeout} if {@code key} is present.
     *
     * @param value   must not be {@literal null}.
     * @param timeout the key expiration timeout.
     * @param unit    must not be {@literal null}.
     * @return command result indicating if the key has been set.
     * @throws IllegalArgumentException if either {@code key}, {@code value} or {@code timeout} is not present.
     * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
     * @since 2.1
     */
    @Nullable
    Boolean setIfPresent(V value, long timeout, TimeUnit unit);

    /**
     * Set {@code key} to hold the string {@code value} and expiration {@code timeout} if {@code key} is present.
     *
     * @param value   must not be {@literal null}.
     * @param timeout must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @throws IllegalArgumentException if either {@code key}, {@code value} or {@code timeout} is not present.
     * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
     * @since 2.1
     */
    @Nullable
    default Boolean setIfPresent(V value, Duration timeout) {

        Assert.notNull(timeout, "Timeout must not be null");

        if (TimeoutUtils.hasMillis(timeout)) {
            return setIfPresent(value, timeout.toMillis(), TimeUnit.MILLISECONDS);
        }

        return setIfPresent(value, timeout.getSeconds(), TimeUnit.SECONDS);
    }

    /**
     * Set multiple keys to multiple values using key-value pairs provided in {@code tuple}.
     *
     * @param map must not be {@literal null}.
     * @see <a href="https://redis.io/commands/mset">Redis Documentation: MSET</a>
     */
    void multiSet(Map<? extends String, ? extends V> map);

    /**
     * Set multiple keys to multiple values using key-value pairs provided in {@code tuple} only if the provided key does
     * not exist.
     *
     * @param map must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/msetnx">Redis Documentation: MSETNX</a>
     */
    @Nullable
    Boolean multiSetIfAbsent(Map<? extends String, ? extends V> map);

    /**
     * Get the value of {@code key}.
     *
     * @return {@literal null} when key does not exist or used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/get">Redis Documentation: GET</a>
     */
    @Nullable
    V get();

    /**
     * Return the value at {@code key} and delete the key.
     *
     * @return {@literal null} when key does not exist or used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/getdel">Redis Documentation: GETDEL</a>
     * @since 2.6
     */
    @Nullable
    V getAndDelete();

    /**
     * Return the value at {@code key} and expire the key by applying {@code timeout}.
     *
     * @param timeout
     * @param unit    must not be {@literal null}.
     * @return {@literal null} when key does not exist or used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/getex">Redis Documentation: GETEX</a>
     * @since 2.6
     */
    @Nullable
    V getAndExpire(long timeout, TimeUnit unit);

    /**
     * Return the value at {@code key} and expire the key by applying {@code timeout}.
     *
     * @param timeout must not be {@literal null}.
     * @return {@literal null} when key does not exist or used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/getex">Redis Documentation: GETEX</a>
     * @since 2.6
     */
    @Nullable
    V getAndExpire(Duration timeout);

    /**
     * Return the value at {@code key} and persist the key. This operation removes any TTL that is associated with
     * {@code key}.
     *
     * @return {@literal null} when key does not exist or used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/getex">Redis Documentation: GETEX</a>
     * @since 2.6
     */
    @Nullable
    V getAndPersist();

    /**
     * Set {@code value} of {@code key} and return its old value.
     *
     * @return {@literal null} when key does not exist or used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/getset">Redis Documentation: GETSET</a>
     */
    @Nullable
    V getAndSet(V value);

    /**
     * Get multiple {@code keys}. Values are in the order of the requested keys Absent field values are represented using
     * {@code null} in the resulting {@link List}.
     *
     * @param keys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/mget">Redis Documentation: MGET</a>
     */
    @Nullable
    List<V> multiGet(Collection<String> keys);

    /**
     * Increment an integer value stored as string value under {@code key} by one.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/incr">Redis Documentation: INCR</a>
     * @since 2.1
     */
    @Nullable
    Long increment();

    /**
     * Increment an integer value stored as string value under {@code key} by {@code delta}.
     *
     * @param delta
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/incrby">Redis Documentation: INCRBY</a>
     */
    @Nullable
    Long increment(long delta);

    /**
     * Increment a floating point number value stored as string value under {@code key} by {@code delta}.
     *
     * @param delta
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/incrbyfloat">Redis Documentation: INCRBYFLOAT</a>
     */
    @Nullable
    Double increment(double delta);

    /**
     * Decrement an integer value stored as string value under {@code key} by one.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/decr">Redis Documentation: DECR</a>
     * @since 2.1
     */
    @Nullable
    Long decrement();

    /**
     * Decrement an integer value stored as string value under {@code key} by {@code delta}.
     *
     * @param delta
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/decrby">Redis Documentation: DECRBY</a>
     * @since 2.1
     */
    @Nullable
    Long decrement(long delta);

    /**
     * Append a {@code value} to {@code key}.
     *
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/append">Redis Documentation: APPEND</a>
     */
    @Nullable
    Integer append(String value);

    /**
     * Get a substring of value of {@code key} between {@code begin} and {@code end}.
     *
     * @param start
     * @param end
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/getrange">Redis Documentation: GETRANGE</a>
     */
    @Nullable
    String get(long start, long end);

    /**
     * Overwrite parts of {@code key} starting at the specified {@code offset} with given {@code value}.
     *
     * @param value
     * @param offset
     * @see <a href="https://redis.io/commands/setrange">Redis Documentation: SETRANGE</a>
     */
    void set(V value, long offset);

    /**
     * Get the length of the value stored at {@code key}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/strlen">Redis Documentation: STRLEN</a>
     */
    @Nullable
    Long size();

    /**
     * Sets the bit at {@code offset} in value stored at {@code key}.
     *
     * @param offset
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/setbit">Redis Documentation: SETBIT</a>
     * @since 1.5
     */
    @Nullable
    Boolean setBit(long offset, boolean value);

    /**
     * Get the bit value at {@code offset} of value at {@code key}.
     *
     * @param offset
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/getbit">Redis Documentation: GETBIT</a>
     * @since 1.5
     */
    @Nullable
    Boolean getBit(long offset);

    /**
     * Get / Manipulate specific integer fields of varying bit widths and arbitrary non (necessary) aligned offset stored
     * at a given {@code key}.
     *
     * @param subCommands must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/bitfield">Redis Documentation: BITFIELD</a>
     * @since 2.1
     */
    @Nullable
    List<Long> bitField(BitFieldSubCommands subCommands);
}
