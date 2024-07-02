package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RedisEntityInformation;
import com.ziyao.harbor.data.redis.core.RedisOpsAdapter;
import com.ziyao.harbor.data.redis.core.RedisRepository;
import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import lombok.Getter;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.core.mapping.RedisPersistentEntity;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyao
 * @since 2024/06/25 09:30:49
 */
@Getter
public abstract class AbstractRepository<T, ID> implements RedisRepository<T, ID> {

    private static final String SCAN_ = ":*";

    protected final RedisOpsAdapter redisOpsAdapter;
    private final RedisEntityInformation<T, ID> entityInformation;

    @SuppressWarnings("unchecked")
    public AbstractRepository(RedisOpsAdapter redisOpsAdapter, RepositoryInformation repositoryInformation) {
        this.redisOpsAdapter = redisOpsAdapter;

        RedisPersistentEntity<?> persistentEntity = new RedisMappingContext().getPersistentEntity(repositoryInformation.getJavaType());
        this.entityInformation = (RedisEntityInformation<T, ID>) new RedisEntityInformation<>(persistentEntity);
    }

    @Override
    public boolean hasKey(ID id) {
        return redisOpsAdapter.contains(id, this.entityInformation.getKeySpace());
    }

    @Override
    public void delete(ID id) {
        redisOpsAdapter.delete(id, this.entityInformation.getKeySpace(), this.entityInformation.getJavaType());
    }

    @Override
    public boolean expire(ID id, long timeout, TimeUnit timeUnit) {
        return redisOpsAdapter.expire(id, this.entityInformation.getKeySpace(), timeout);
    }

}
