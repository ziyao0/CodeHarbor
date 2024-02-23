package com.ziyao.harbor.data.redis.core;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.connection.convert.Converters;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
public class DefaultHashOperations<V, HK, HV> extends AbstractOperations<V> implements HashOperations<HK, HV> {

    public DefaultHashOperations(RedisTemplate<String, V> template, String redisKey) {
        super(template, redisKey);
    }

    @Override
    public final Long delete(Object... hashKeys) {
        return template.opsForHash().delete(redisKey, hashKeys);
    }

    @Override
    public Boolean hasKey(HK hashKey) {
        return template.opsForHash().hasKey(redisKey, hashKey);
    }

    @SuppressWarnings("unchecked")
    @Override
    public HV get(HK hashKey) {
        return (HV) template.opsForHash().get(redisKey, hashKey);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HV> multiGet(Collection<HK> hashKeys) {
        return (List<HV>) template.opsForHash().multiGet(redisKey, Collections.singleton(hashKeys));
    }

    @Override
    public Long increment(HK hashKey, long delta) {
        return template.opsForHash().increment(redisKey, hashKey, delta);
    }

    @Override
    public Double increment(HK hashKey, double delta) {
        return template.opsForHash().increment(redisKey, hashKey, delta);
    }

    @SuppressWarnings("unchecked")
    @Override
    public HK randomKey() {
        return (HK) template.opsForHash().randomKey(redisKey);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map.Entry<HK, HV> randomEntry() {
        return (Map.Entry<HK, HV>) template.opsForHash().randomEntry(redisKey);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HK> randomKeys(long count) {
        return (List<HK>) template.opsForHash().randomKeys(redisKey, count);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<HK, HV> randomEntries(long count) {
        return (Map<HK, HV>) template.opsForHash().randomEntries(redisKey, count);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<HK> keys() {
        return (Set<HK>) template.opsForHash().keys(redisKey);
    }

    @Override
    public Long lengthOfValue(HK hashKey) {
        return template.opsForHash().lengthOfValue(redisKey, hashKey);
    }

    @Override
    public Long size() {
        return template.opsForHash().size(redisKey);
    }

    @Override
    public void put(HK hashKey, HV value) {
        template.opsForHash().put(redisKey, hashKey, value);
    }

    @Override
    public Boolean putIfAbsent(HK hashKey, HV value) {
        return template.opsForHash().putIfAbsent(redisKey, hashKey, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HV> values() {
        return (List<HV>) template.opsForHash().values(redisKey);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<HK, HV> entries() {
        return (Map<HK, HV>) template.opsForHash().entries(redisKey);
    }

    @Override
    public Cursor<Map.Entry<HK, HV>> scan(ScanOptions options) {
        byte[] rawKey = rawKey(redisKey);
        return template.executeWithStickyConnection(
                (RedisCallback<Cursor<Map.Entry<HK, HV>>>) connection -> new ConvertingCursor<>(connection.hScan(rawKey, options),
                        new Converter<Map.Entry<byte[], byte[]>, Map.Entry<HK, HV>>() {

                            @Override
                            public Map.Entry<HK, HV> convert(final Map.Entry<byte[], byte[]> source) {
                                return Converters.entryOf(deserializeHashKey(source.getKey()), deserializeHashValue(source.getValue()));
                            }
                        }));
    }

    byte[] rawKey(String key) {

        Assert.notNull(key, "non null key required");


        return RedisSerializer.string().serialize(key);
    }

    RedisSerializer hashKeySerializer() {
        return template.getHashKeySerializer();
    }

    RedisSerializer hashValueSerializer() {
        return template.getHashValueSerializer();
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
