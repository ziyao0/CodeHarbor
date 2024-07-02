package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RedisOpsAdapter;
import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import com.ziyao.harbor.data.redis.core.convert.RedisUpdate;
import org.springframework.data.redis.core.RedisOperations;

import java.util.List;
import java.util.Optional;

/**
 * @author ziyao
 * @since 2024/06/25 15:17:24
 */
public class DefaultRedisListRepository<T, ID> extends AbstractRepository<T, ID> implements RedisListRepository<T, ID> {

    public DefaultRedisListRepository(RepositoryInformation repositoryInformation, RedisOperations<byte[], byte[]> redisOps) {
        super(new RedisOpsAdapter(redisOps), repositoryInformation);
    }

    @Override
    public Optional<List<T>> findById(ID id) {
//        return Optional.ofNullable(redisOpsAdapter.findByIdForList(id, getEntityInformation().getKeySpace(), getEntityInformation().getJavaType()));
        return Optional.empty();
    }

    @Override
    public void save(T entity) {

        ID id = getEntityInformation().getRequiredId(entity);
        RedisUpdate<T> update = new RedisUpdate<>(id, getEntityInformation().getJavaType(), entity, false);

        if (getEntityInformation().hasExplicitTimeToLiveProperty()) {
            Long timeToLive = getEntityInformation().getTimeToLive(entity);
            if (timeToLive != null && timeToLive > 0) {
                update.setRefresh(true);
            }
        }
//        redisOpsAdapter.update(update);
    }

    @Override
    public void saveAll(List<T> values) {
//
//        T entity = values.get(0);
//
//        RedisMetadata rdo = RedisMetadata.createRedisEntity();
//
//        redisOpsAdapter.getConverter().write(values, rdo);
//
//        byte[] redisKey = redisOpsAdapter.createKey(getEntityInformation().getId(entity), getEntityInformation().getKeySpace(), getEntityInformation().getJavaType());
//
//        redisOpsAdapter.getRedisOps().opsForList().leftPushAll(redisKey, rdo.getRaws());

    }

    @Override
    public Optional<T> leftPop(ID id) {

//        byte[] redisKey = redisOpsAdapter.createKey(id, getEntityInformation().getKeySpace(), getEntityInformation().getJavaType());
//
//        byte[] raw = redisOpsAdapter.getRedisOps().opsForList().leftPop(redisKey);
//
//        T entity = this.redisOpsAdapter.getConverter().read(getEntityInformation().getJavaType(), RedisMetadata.createRedisEntity(raw));
//        return Optional.ofNullable(entity);
        return Optional.empty();
    }

    @Override
    public Optional<T> rightPop(ID id) {
//        byte[] redisKey = redisOpsAdapter.createKey(id, getEntityInformation().getKeySpace(), getEntityInformation().getJavaType());
//        byte[] raw = redisOpsAdapter.getRedisOps().opsForList().rightPop(redisKey);
//        T entity = this.redisOpsAdapter.getConverter().read(getEntityInformation().getJavaType(), RedisMetadata.createRedisEntity(raw));
        return Optional.empty();
    }

    @Override
    public void leftPush(T entity) {
//        byte[] redisKey = redisOpsAdapter.createKey(getEntityInformation().getId(entity),
//                getEntityInformation().getKeySpace(), getEntityInformation().getJavaType());
//
//        RedisMetadata rdo = RedisMetadata.createRedisEntity();
//
//        redisOpsAdapter.getConverter().write(entity, rdo);
//
//        redisOpsAdapter.getRedisOps().opsForList().leftPush(redisKey, rdo.getRaw());
    }

    @Override
    public void rightPush(T entity) {
//        byte[] redisKey = redisOpsAdapter.createKey(getEntityInformation().getId(entity),
//                getEntityInformation().getKeySpace(), getEntityInformation().getJavaType());
//
//        RedisMetadata rdo = RedisMetadata.createRedisEntity();
//
//        redisOpsAdapter.getConverter().write(entity, rdo);
//
//        redisOpsAdapter.getRedisOps().opsForList().rightPush(redisKey, rdo.getRaw());
    }
}
