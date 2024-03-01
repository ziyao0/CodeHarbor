package com.ziyao.harbor.data.redis.core;

import com.ziyao.harbor.data.redis.support.serializer.SerializerInformation;
import org.springframework.data.redis.connection.convert.Converters;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
public class DefaultHashOperations<V, HK, HV> extends AbstractOperations<V> implements HashOperations<HK, HV> {

    public DefaultHashOperations(RedisTemplate<String, V> operations, long timeout, SerializerInformation metadata) {
        super(operations, timeout, metadata);
    }

    @Override
    public final Long delete(Object... hashKeys) {
        return operations.opsForHash().delete(key, hashKeys);
    }

    @Override
    public Boolean hasKey(HK hashKey) {
        return operations.opsForHash().hasKey(key, hashKey);
    }

    @SuppressWarnings("unchecked")
    @Override
    public HV get(HK hashKey) {
        return (HV) operations.opsForHash().get(key, hashKey);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HV> multiGet(Collection<HK> hashKeys) {
        return (List<HV>) operations.opsForHash().multiGet(key, Collections.singleton(hashKeys));
    }

    @Override
    public Long increment(HK hashKey, long delta) {
        return operations.opsForHash().increment(key, hashKey, delta);
    }

    @Override
    public Double increment(HK hashKey, double delta) {
        return operations.opsForHash().increment(key, hashKey, delta);
    }

    @SuppressWarnings("unchecked")
    @Override
    public HK randomKey() {
        return (HK) operations.opsForHash().randomKey(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map.Entry<HK, HV> randomEntry() {
        return (Map.Entry<HK, HV>) operations.opsForHash().randomEntry(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HK> randomKeys(long count) {
        return (List<HK>) operations.opsForHash().randomKeys(key, count);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<HK, HV> randomEntries(long count) {
        return (Map<HK, HV>) operations.opsForHash().randomEntries(key, count);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<HK> keys() {
        return (Set<HK>) operations.opsForHash().keys(key);
    }

    @Override
    public Long lengthOfValue(HK hashKey) {
        return operations.opsForHash().lengthOfValue(key, hashKey);
    }

    @Override
    public Long size() {
        return operations.opsForHash().size(key);
    }

    @Override
    public void put(HK hashKey, HV value) {
        if (timeout > 0)
            this.put(hashKey, value, timeout, TimeUnit.SECONDS);
        else
            operations.opsForHash().put(key, hashKey, value);
    }

    @Override
    public void put(HK hashKey, HV value, long timeout, TimeUnit unit) {
        operations.opsForHash().put(key, hashKey, value);
        operations.expire(key, timeout, unit);
    }

    @Override
    public Boolean putIfAbsent(HK hashKey, HV value) {
        Boolean b = operations.opsForHash().putIfAbsent(key, hashKey, value);
        expire();
        return b;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HV> values() {
        return (List<HV>) operations.opsForHash().values(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<HK, HV> entries() {
        return (Map<HK, HV>) operations.opsForHash().entries(key);
    }

    @SuppressWarnings("deprecation")
    @Override
    public Cursor<Map.Entry<HK, HV>> scan(ScanOptions options) {
        byte[] rawKey = rawKey(key);
        return operations.executeWithStickyConnection(
                (RedisCallback<Cursor<Map.Entry<HK, HV>>>) connection -> new ConvertingCursor<>(connection.hScan(rawKey, options),
                        source -> Converters.entryOf(deserializeHashKey(source.getKey()), deserializeHashValue(source.getValue()))));
    }

    byte[] rawKey(String key) {

        Assert.notNull(key, "non null key required");


        return RedisSerializer.string().serialize(key);
    }

    RedisSerializer hashKeySerializer() {
        return operations.getHashKeySerializer();
    }

    RedisSerializer hashValueSerializer() {
        return operations.getHashValueSerializer();
    }

    @SuppressWarnings({"unchecked"})
    HK deserializeHashKey(byte[] value) {
        if (hashKeySerializer() == null) {
            return (HK) value;
        }
        return (HK) hashKeySerializer().deserialize(value);
    }

    @SuppressWarnings("unchecked")
    HV deserializeHashValue(byte[] value) {
        if (hashValueSerializer() == null) {
            return (HV) value;
        }
        return (HV) hashValueSerializer().deserialize(value);
    }

}
