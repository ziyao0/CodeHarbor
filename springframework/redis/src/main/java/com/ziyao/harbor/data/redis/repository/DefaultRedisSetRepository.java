package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RedisOpsAdapter;
import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import org.springframework.data.redis.core.RedisOperations;

import java.util.Optional;
import java.util.Set;

/**
 * @author ziyao
 * @since 2024/06/25 15:36:54
 */
public class DefaultRedisSetRepository<T, ID> extends AbstractRepository<T, ID> implements RedisSetRepository<T, ID> {


    public DefaultRedisSetRepository(RepositoryInformation repositoryInformation, RedisOperations<byte[], byte[]> redisOps) {
        super(new RedisOpsAdapter(redisOps), repositoryInformation);

    }

    @Override
    public Optional<Set<T>> findById(ID id) {
//        byte[] redisKey = this.redisOpsAdapter.createKey(id, getEntityInformation().getKeySpace(), getEntityInformation().getJavaType());
//
//        Set<byte[]> raws = this.redisOpsAdapter.getRedisOps().opsForSet().members(redisKey);
//        RedisMetadata redisMetadata = RedisMetadata.createRedisEntity();
//        redisMetadata.setRaws(raws);
//        List<T> ts = this.redisOpsAdapter.getConverter().readList(getEntityInformation().getJavaType(), redisMetadata);
//
//        return Optional.of(Set.copyOf(ts));
        return Optional.empty();
    }

    @Override
    public void save(T entity) {
//        byte[] redisKey = this.redisOpsAdapter.createKey(
//                getEntityInformation().getId(entity), getEntityInformation().getKeySpace(), getEntityInformation().getJavaType());
//
//        RedisMetadata rdo = RedisMetadata.createRedisEntity();
//
//        this.redisOpsAdapter.getConverter().write(entity, rdo);
//
//        this.redisOpsAdapter.getRedisOps().opsForSet().add(redisKey, rdo.getRaw());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveAll(T... entities) {

//        Optional<T> first = Arrays.stream(entities).findFirst();
//
//        if (first.isPresent()) {
//
//            T entity = first.get();
//
//            byte[] redisKey = this.redisOpsAdapter.createKey(
//                    getEntityInformation().getId(entity), getEntityInformation().getKeySpace(), getEntityInformation().getJavaType());
//
//            RedisMetadata rdo = RedisMetadata.createRedisEntity();
//
//            this.redisOpsAdapter.getConverter().write(entity, rdo);
//
//            this.redisOpsAdapter.getRedisOps().opsForSet().add(redisKey, rdo.getRaw());
//        }
    }
}
