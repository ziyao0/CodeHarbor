package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.Repository;
import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import com.ziyao.harbor.data.redis.core.TimeToLive;
import com.ziyao.harbor.data.redis.support.TimeoutUtils;
import lombok.Getter;
import org.springframework.data.redis.core.RedisOperations;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao
 * @since 2024/06/25 09:30:49
 */
public abstract class AbstractRepository<T> implements Repository {

    private final RedisOperations<String, T> operations;
    @Getter
    private final long ttl;

    public AbstractRepository(RedisOperations<String, T> operations, RepositoryInformation repositoryInformation) {
        this.operations = operations;
        TimeToLive timeToLive = repositoryInformation.getRepositoryInterface().getAnnotation(TimeToLive.class);

        this.ttl = timeToLive != null && timeToLive.ttl() > 0
                ? TimeoutUtils.toSeconds(timeToLive.ttl(), timeToLive.unit()) : -1;
    }

    @Override
    public boolean hasKey(String key) {
        return Optional.ofNullable(operations.hasKey(key)).orElse(false);
    }

    @Override
    public boolean delete(String key) {
        return Optional.ofNullable(operations.delete(key)).orElse(false);
    }

    @Override
    public void expire(String key) {
        this.expire(key, ttl, TimeUnit.SECONDS);
    }

    @Override
    public void expire(String key, long timeout, TimeUnit timeUnit) {
        operations.expire(key, timeout, timeUnit);
    }
}
