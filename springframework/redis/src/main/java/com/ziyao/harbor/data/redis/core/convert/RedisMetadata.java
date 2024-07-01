package com.ziyao.harbor.data.redis.core.convert;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Optional;

/**
 * @author ziyao zhang
 * @time 2024/6/29
 */
@Setter
public class RedisMetadata {

    @Getter
    private byte[] raw;
    @Getter
    private Collection<byte[]> raws;

    private String keyspace;
    private String id;
    private Long timeToLive;

    public RedisMetadata() {
        this(null);
    }

    public RedisMetadata(byte[] raw) {
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

    public static RedisMetadata createRedisEntity(byte[] raw) {
        return new RedisMetadata(raw);
    }

    public static RedisMetadata createRedisEntity() {
        return createRedisEntity(null);
    }
}
