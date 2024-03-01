package com.ziyao.harbor.data.redis.core;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
public interface HashOperations<HK, HV>  extends KeyAware {
    /**
     * Delete given hash {@code hashKeys}.
     *
     * @param hashKeys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    Long delete(Object... hashKeys);

    /**
     * Determine if given hash {@code hashKey} exists.
     *
     * @param hashKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    Boolean hasKey(HK hashKey);

    /**
     * Get value for given {@code hashKey} from hash at {@code key}.
     *
     * @param hashKey must not be {@literal null}.
     * @return {@literal null} when key or hashKey does not exist or used in pipeline / transaction.
     */
    @Nullable
    HV get(HK hashKey);

    /**
     * Get values for given {@code hashKeys} from hash at {@code key}. Values are in the order of the requested keys
     * Absent field values are represented using {@code null} in the resulting {@link List}.
     *
     * @param hashKeys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    List<HV> multiGet(Collection<HK> hashKeys);

    /**
     * Increment {@code value} of a hash {@code hashKey} by the given {@code delta}.
     *
     * @param hashKey must not be {@literal null}.
     * @param delta   delta
     * @return {@literal null} when used in pipeline / transaction.
     */
    Long increment(HK hashKey, long delta);

    /**
     * Increment {@code value} of a hash {@code hashKey} by the given {@code delta}.
     *
     * @param hashKey must not be {@literal null}.
     * @param delta   delta
     * @return {@literal null} when used in pipeline / transaction.
     */
    Double increment(HK hashKey, double delta);

    /**
     * Return a random hash key (aka field) from the hash stored at {@code key}.
     *
     * @return {@literal null} if key does not exist or when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/hrandfield">Redis Documentation: HRANDFIELD</a>
     * @since 2.6
     */
    @Nullable
    HK randomKey();

    /**
     * Return a random entry from the hash stored at {@code key}.
     *
     * @return {@literal null} if key does not exist or when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/hrandfield">Redis Documentation: HRANDFIELD</a>
     * @since 2.6
     */
    @Nullable
    Map.Entry<HK, HV> randomEntry();

    /**
     * Return random hash keys (aka fields) from the hash stored at {@code key}. If the provided {@code count} argument is
     * positive, return a list of distinct hash keys, capped either at {@code count} or the hash size. If {@code count} is
     * negative, the behavior changes and the command is allowed to return the same hash key multiple times. In this case,
     * the number of returned fields is the absolute value of the specified count.
     *
     * @param count number of fields to return.
     * @return {@literal null} if key does not exist or when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/hrandfield">Redis Documentation: HRANDFIELD</a>
     * @since 2.6
     */
    @Nullable
    List<HK> randomKeys(long count);

    /**
     * Return a random entries from the hash stored at {@code key}.
     *
     * @param count number of fields to return. Must be positive.
     * @return {@literal null} if key does not exist or when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/hrandfield">Redis Documentation: HRANDFIELD</a>
     * @since 2.6
     */
    @Nullable
    Map<HK, HV> randomEntries(long count);

    /**
     * Get key set (fields) of hash at {@code key}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     */
    Set<HK> keys();

    /**
     * Returns the length of the value associated with {@code hashKey}. If either the {@code key} or the {@code hashKey}
     * do not exist, {@code 0} is returned.
     *
     * @param hashKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @since 2.1
     */
    @Nullable
    Long lengthOfValue(HK hashKey);

    /**
     * Get size of hash at {@code key}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     */
    Long size();

    /**
     * Set multiple hash fields to multiple values using data provided in {@code m}.
     *
     * @param m must not be {@literal null}.
     */
    default void putAll(Map<? extends HK, ? extends HV> m) {

    }

    /**
     * Set the {@code value} of a hash {@code hashKey}.
     *
     * @param hashKey must not be {@literal null}.
     * @param value   v
     */
    void put(HK hashKey, HV value);

    /**
     * Set the {@code value} of a hash {@code hashKey}.
     *
     * @param hashKey must not be {@literal null}.
     * @param value   v
     */
    void put(HK hashKey, HV value, long timeout, TimeUnit unit);

    /**
     * Set the {@code value} of a hash {@code hashKey} only if {@code hashKey} does not exist.
     *
     * @param hashKey must not be {@literal null}.
     * @param value   value
     * @return {@literal null} when used in pipeline / transaction.
     */
    Boolean putIfAbsent(HK hashKey, HV value);

    /**
     * Get entry set (values) of hash at {@code key}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     */
    List<HV> values();

    /**
     * Get entire hash stored at {@code key}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     */
    Map<HK, HV> entries();

    /**
     * Use a {@link Cursor} to iterate over entries in hash at {@code key}. <br />
     * <strong>Important:</strong> Call {@link Cursor#close()} when done to avoid resource leaks.
     *
     * @param options must not be {@literal null}.
     * @return the result cursor providing access to the scan result. Must be closed once fully processed (e.g. through a
     * try-with-resources clause).
     * @since 1.4
     */
    Cursor<Map.Entry<HK, HV>> scan(ScanOptions options);

}
