package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RedisAdapter;
import com.ziyao.harbor.data.redis.core.RedisEntityInformation;
import com.ziyao.harbor.data.redis.core.RedisRepository;
import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import lombok.Getter;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.core.mapping.RedisPersistentEntity;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao
 * @since 2024/06/25 09:30:49
 */
@Getter
public abstract class AbstractRepository<T, ID> implements RedisRepository<T> {

    protected final RedisAdapter redisAdapter;
    private final RedisEntityInformation<T, ID> entityInformation;

    @SuppressWarnings("unchecked")
    public AbstractRepository(RedisAdapter redisAdapter, RepositoryInformation repositoryInformation) {
        this.redisAdapter = redisAdapter;

        RedisPersistentEntity<?> persistentEntity = new RedisMappingContext().getPersistentEntity(repositoryInformation.getValueType());
        this.entityInformation = (RedisEntityInformation<T, ID>) new RedisEntityInformation<>(persistentEntity);
    }

    @Override
    public boolean hasKey(Object id, String keyspace, Class<T> type) {
        return Optional.of(redisAdapter.hasKey(id, keyspace, type)).orElse(false);
    }

    @Override
    public void delete(Object id, String keyspace, Class<T> type) {
        redisAdapter.delete(id, keyspace, type);
    }

    @Override
    public void timeToLive(Object id, String keyspace, Class<T> type, long timeout, TimeUnit timeUnit) {
        redisAdapter.timeToLive(id, keyspace, type, timeout, timeUnit);
    }

}
