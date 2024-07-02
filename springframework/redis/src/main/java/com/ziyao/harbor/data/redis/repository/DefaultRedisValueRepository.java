package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RedisEntityInformation;
import com.ziyao.harbor.data.redis.core.RedisOpsAdapter;
import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import com.ziyao.harbor.data.redis.core.convert.RedisUpdate;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
public class DefaultRedisValueRepository<T, ID> extends AbstractRepository<T, ID> implements RedisValueRepository<T, ID> {


    public DefaultRedisValueRepository(RepositoryInformation repositoryInformation, RedisOperations<byte[], byte[]> redisOps) {
        super(new RedisOpsAdapter(redisOps), repositoryInformation);

    }

    @Override
    public Optional<T> findById(@NonNull Object id) {
        String keySpace = getEntityInformation().getKeySpace();
        Class<T> javaType = getEntityInformation().getJavaType();
        return Optional.ofNullable(redisOpsAdapter.findById(id, keySpace, javaType));
    }

    @Override
    public Optional<List<T>> findAll() {
        final String keySpace = getEntityInformation().getKeySpace();
        Class<T> javaType = getEntityInformation().getJavaType();
        List<T> entities = this.redisOpsAdapter.findAllOf(keySpace, javaType, 0, -1);
        return Optional.ofNullable(entities);
    }


    @Override
    public void save(T entity) {
        redisOpsAdapter.insert(entity);
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
//        return redisOpsAdapter.updateIfAbsent(update);
        return false;
    }
}
