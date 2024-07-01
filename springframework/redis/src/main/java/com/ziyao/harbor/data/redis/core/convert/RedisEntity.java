package com.ziyao.harbor.data.redis.core.convert;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * @author ziyao zhang
 * @time 2024/6/29
 */
@Setter
public class RedisEntity {

    @Getter
    private byte[] raw;
    @Getter
    private Collection<byte[]> raws;
    @Getter
    private Map<byte[], byte[]> rawMap;

    private String keyspace;
    private String id;
    private Long timeToLive;

    public RedisEntity() {
        this(null);
    }

    public RedisEntity(byte[] raw) {
        this.raw = raw;
    }

    public Optional<String> getKeyspace() {
        return Optional.ofNullable(keyspace);
    }

    public Optional<String> getId() {
        return Optional.ofNullable(id);
    }

    public Optional<Long> getTimeToLive() {
        return Optional.ofNullable(timeToLive);
    }

    public static RedisEntity createRedisEntity(byte[] raw) {
        return new RedisEntity(raw);
    }

    public static RedisEntity createRedisEntity() {
        return createRedisEntity(null);
    }
}
