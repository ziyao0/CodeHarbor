package com.ziyao.harbor.data.redis.core;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
public interface SetOperations<V>  extends KeyAware {

    /**
     * Add given {@code values} to set at {@code key}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sadd">Redis Documentation: SADD</a>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    Long add(V... values);

    /**
     * Remove given {@code values} from set at {@code key} and return the number of removed elements.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/srem">Redis Documentation: SREM</a>
     */
    @Nullable
    Long remove(Object... values);

    /**
     * Remove and return a random member from set at {@code key}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/spop">Redis Documentation: SPOP</a>
     */
    @Nullable
    V pop();

    /**
     * Remove and return {@code count} random members from set at {@code key}.
     *
     * @param count number of random members to pop from the set.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/spop">Redis Documentation: SPOP</a>
     */
    @Nullable
    List<V> pop(long count);

    /**
     * Move {@code value} from {@code key} to {@code destKey}
     *
     * @param destKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/smove">Redis Documentation: SMOVE</a>
     */
    @Nullable
    Boolean move(V value, String destKey);

    /**
     * Get size of set at {@code key}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/scard">Redis Documentation: SCARD</a>
     */
    @Nullable
    Long size();

    /**
     * Check if set at {@code key} contains {@code value}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sismember">Redis Documentation: SISMEMBER</a>
     */
    @Nullable
    Boolean isMember(Object o);

    /**
     * Check if set at {@code key} contains one or more {@code values}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/smismember">Redis Documentation: SMISMEMBER</a>
     * @since 2.6
     */
    @Nullable
    Map<Object, Boolean> isMember(Object... objects);

    /**
     * Returns the members intersecting all given sets at {@code key} and {@code otherKey}.
     *
     * @param otherKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sinter">Redis Documentation: SINTER</a>
     */
    @Nullable
    Set<V> intersect(String otherKey);

    /**
     * Returns the members intersecting all given sets at {@code key} and {@code otherKeys}.
     *
     * @param otherKeys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sinter">Redis Documentation: SINTER</a>
     */
    @Nullable
    Set<V> intersect(String key, Collection<String> otherKeys);

    /**
     * Returns the members intersecting all given sets at {@code keys}.
     *
     * @param keys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sinter">Redis Documentation: SINTER</a>
     * @since 2.2
     */
    @Nullable
    Set<V> intersect(Collection<String> keys);

    /**
     * Intersect all given sets at {@code key} and {@code otherKey} and store result in {@code destKey}.
     *
     * @param otherKey must not be {@literal null}.
     * @param destKey  must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sinterstore">Redis Documentation: SINTERSTORE</a>
     */
    @Nullable
    Long intersectAndStore(String otherKey, String destKey);

    /**
     * Intersect all given sets at {@code key} and {@code otherKeys} and store result in {@code destKey}.
     *
     * @param otherKeys must not be {@literal null}.
     * @param destKey   must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sinterstore">Redis Documentation: SINTERSTORE</a>
     */
    @Nullable
    Long intersectAndStore(String key, Collection<String> otherKeys, String destKey);

    /**
     * Intersect all given sets at {@code keys} and store result in {@code destKey}.
     *
     * @param keys    must not be {@literal null}.
     * @param destKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sinterstore">Redis Documentation: SINTERSTORE</a>
     * @since 2.2
     */
    @Nullable
    Long intersectAndStore(Collection<String> keys, String destKey);

    /**
     * Union all sets at given {@code keys} and {@code otherKey}.
     *
     * @param otherKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sunion">Redis Documentation: SUNION</a>
     */
    @Nullable
    Set<V> union(String otherKey);

    /**
     * Union all sets at given {@code keys} and {@code otherKeys}.
     *
     * @param otherKeys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sunion">Redis Documentation: SUNION</a>
     */
    @Nullable
    Set<V> union(String key, Collection<String> otherKeys);

    /**
     * Union all sets at given {@code keys}.
     *
     * @param keys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sunion">Redis Documentation: SUNION</a>
     * @since 2.2
     */
    @Nullable
    Set<V> union(Collection<String> keys);

    /**
     * Union all sets at given {@code key} and {@code otherKey} and store result in {@code destKey}.
     *
     * @param otherKey must not be {@literal null}.
     * @param destKey  must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sunionstore">Redis Documentation: SUNIONSTORE</a>
     */
    @Nullable
    Long unionAndStore(String otherKey, String destKey);

    /**
     * Union all sets at given {@code key} and {@code otherKeys} and store result in {@code destKey}.
     *
     * @param otherKeys must not be {@literal null}.
     * @param destKey   must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sunionstore">Redis Documentation: SUNIONSTORE</a>
     */
    @Nullable
    Long unionAndStore(String key, Collection<String> otherKeys, String destKey);

    /**
     * Union all sets at given {@code keys} and store result in {@code destKey}.
     *
     * @param keys    must not be {@literal null}.
     * @param destKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sunionstore">Redis Documentation: SUNIONSTORE</a>
     * @since 2.2
     */
    @Nullable
    Long unionAndStore(Collection<String> keys, String destKey);

    /**
     * Diff all sets for given {@code key} and {@code otherKey}.
     *
     * @param otherKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sdiff">Redis Documentation: SDIFF</a>
     */
    @Nullable
    Set<V> difference(String otherKey);

    /**
     * Diff all sets for given {@code key} and {@code otherKeys}.
     *
     * @param otherKeys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sdiff">Redis Documentation: SDIFF</a>
     */
    @Nullable
    Set<V> difference(String key, Collection<String> otherKeys);

    /**
     * Diff all sets for given {@code keys}.
     *
     * @param keys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sdiff">Redis Documentation: SDIFF</a>
     * @since 2.2
     */
    @Nullable
    Set<V> difference(Collection<String> keys);

    /**
     * Diff all sets for given {@code key} and {@code otherKey} and store result in {@code destKey}.
     *
     * @param otherKey must not be {@literal null}.
     * @param destKey  must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sdiffstore">Redis Documentation: SDIFFSTORE</a>
     */
    @Nullable
    Long differenceAndStore(String otherKey, String destKey);

    /**
     * Diff all sets for given {@code key} and {@code otherKeys} and store result in {@code destKey}.
     *
     * @param otherKeys must not be {@literal null}.
     * @param destKey   must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sdiffstore">Redis Documentation: SDIFFSTORE</a>
     */
    @Nullable
    Long differenceAndStore(String key, Collection<String> otherKeys, String destKey);

    /**
     * Diff all sets for given {@code keys} and store result in {@code destKey}.
     *
     * @param keys    must not be {@literal null}.
     * @param destKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sdiffstore">Redis Documentation: SDIFFSTORE</a>
     * @since 2.2
     */
    @Nullable
    Long differenceAndStore(Collection<String> keys, String destKey);

    /**
     * Get all elements of set at {@code key}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/smembers">Redis Documentation: SMEMBERS</a>
     */
    @Nullable
    Set<V> members();

    /**
     * Get random element from set at {@code key}.
     *
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/srandmember">Redis Documentation: SRANDMEMBER</a>
     */
    V randomMember();

    /**
     * Get {@code count} distinct random elements from set at {@code key}.
     *
     * @param count nr of members to return
     * @return empty {@link Set} if {@code key} does not exist.
     * @throws IllegalArgumentException if count is negative.
     * @see <a href="https://redis.io/commands/srandmember">Redis Documentation: SRANDMEMBER</a>
     */
    @Nullable
    Set<V> distinctRandomMembers(long count);

    /**
     * Get {@code count} random elements from set at {@code key}.
     *
     * @param count nr of members to return.
     * @return empty {@link List} if {@code key} does not exist or {@literal null} when used in pipeline / transaction.
     * @throws IllegalArgumentException if count is negative.
     * @see <a href="https://redis.io/commands/srandmember">Redis Documentation: SRANDMEMBER</a>
     */
    @Nullable
    List<V> randomMembers(long count);

    /**
     * Use a {@link Cursor} to iterate over entries set at {@code key}. <br />
     * <strong>Important:</strong> Call {@link Cursor#close()} when done to avoid resource leaks.
     *
     * @param options must not be {@literal null}.
     * @return the result cursor providing access to the scan result. Must be closed once fully processed (e.g. through a
     * try-with-resources clause).
     * @since 1.4
     */
    Cursor<V> scan(ScanOptions options);

}
