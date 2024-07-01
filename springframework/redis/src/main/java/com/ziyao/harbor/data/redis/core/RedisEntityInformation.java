package com.ziyao.harbor.data.redis.core;

import lombok.Getter;
import org.springframework.data.redis.core.mapping.RedisPersistentEntity;
import org.springframework.data.repository.core.support.PersistentEntityInformation;

/**
 * @author ziyao zhang
 * @time 2024/6/29
 */
@Getter
public class RedisEntityInformation<T, ID> extends PersistentEntityInformation<T, ID> {

    private final RedisPersistentEntity<T> persistentEntity;

    public RedisEntityInformation(RedisPersistentEntity<T> persistentEntity) {
        super(persistentEntity);
        this.persistentEntity = persistentEntity;
    }

    public Long getTimeToLive(T entity) {
        return this.persistentEntity.getTimeToLiveAccessor().getTimeToLive(entity);
    }

    public String getKeySpace() {
        return this.persistentEntity.getKeySpace();
    }

    public boolean hasExplicitTimeToLiveProperty() {
        return this.persistentEntity.hasExplictTimeToLiveProperty();
    }

    public boolean isExpiringEntity() {
        return this.persistentEntity.isExpiring();
    }
}
