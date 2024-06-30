package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RedisEntityInformation;
import com.ziyao.harbor.data.redis.core.RedisRepository;
import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import lombok.Getter;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.core.mapping.RedisPersistentEntity;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao
 * @since 2024/06/25 09:30:49
 */
@Getter
public abstract class AbstractRepository<T, ID> implements RedisRepository<ID> {

    private final RedisOperations<ID, T> operations;
    private final RedisEntityInformation<T, ID> entityInformation;

    @SuppressWarnings("unchecked")
    public AbstractRepository(RedisOperations<ID, T> operations, RepositoryInformation repositoryInformation) {
        this.operations = operations;

        RedisPersistentEntity<?> persistentEntity = new RedisMappingContext().getPersistentEntity(repositoryInformation.getValueType());
        this.entityInformation = (RedisEntityInformation<T, ID>) new RedisEntityInformation<>(persistentEntity);
    }

    @Override
    public boolean hasKey(ID key) {
        return Optional.ofNullable(operations.hasKey(key)).orElse(false);
    }

    @Override
    public boolean delete(ID key) {
        return Optional.ofNullable(operations.delete(key)).orElse(false);
    }

    @Override
    public void timeToLive(ID key, long timeout, TimeUnit timeUnit) {
        operations.expire(key, timeout, timeUnit);
    }

}
