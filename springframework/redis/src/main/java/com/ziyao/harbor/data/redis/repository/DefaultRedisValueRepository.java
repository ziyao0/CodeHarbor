package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RedisAdapter;
import com.ziyao.harbor.data.redis.core.RedisEntityInformation;
import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import com.ziyao.harbor.data.redis.core.convert.RedisUpdate;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
public class DefaultRedisValueRepository<T, ID> extends AbstractRepository<T, ID> implements RedisValueRepository<T, ID> {


    public DefaultRedisValueRepository(RepositoryInformation repositoryInformation, RedisOperations<byte[], byte[]> redisOps) {
        super(new RedisAdapter(redisOps), repositoryInformation);

    }

    @Override
    public Optional<T> findById(@NonNull Object id) {
        String keySpace = getEntityInformation().getKeySpace();
        Class<T> javaType = getEntityInformation().getJavaType();
        return Optional.ofNullable(redisAdapter.findById(id, keySpace, javaType));
    }

    @Override
    public void save(T entity) {

        RedisEntityInformation<T, ID> entityInformation = getEntityInformation();
        ID id = entityInformation.getId(entity);

        if (null == id) {
            throw new IllegalArgumentException("未在实体类中获取存在id属性的字段或类");
        }
        Class<T> javaType = entityInformation.getJavaType();

        RedisUpdate<T> update = new RedisUpdate<>(id, javaType, entity, false);

        if (entityInformation.hasExplicitTimeToLiveProperty()) {
            Long timeToLive = entityInformation.getTimeToLive(entity);

            if (timeToLive != null && timeToLive > 0) {
                update.setRefresh(true);
            }

        }
        redisAdapter.update(update);
    }

    @Override
    public boolean saveIfAbsent(T entity) {
        RedisEntityInformation<T, ID> entityInformation = getEntityInformation();

        ID id = entityInformation.getId(entity);

        if (null == id) {
            throw new IllegalArgumentException("未在实体类中获取存在id属性的字段或类");
        }

        RedisUpdate<T> update = new RedisUpdate<>(id, entityInformation.getJavaType(), entity, false);

        if (entityInformation.hasExplicitTimeToLiveProperty()) {
            Long timeToLive = entityInformation.getTimeToLive(entity);

            if (timeToLive != null && timeToLive > 0) {
                update.setRefresh(true);
            }
        }
        return redisAdapter.updateIfAbsent(update);
    }
}
