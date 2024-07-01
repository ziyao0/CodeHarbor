package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RedisAdapter;
import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import com.ziyao.harbor.data.redis.core.convert.RedisMetadata;
import org.springframework.data.redis.core.RedisOperations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author ziyao
 * @since 2024/06/25 15:36:54
 */
public class DefaultRedisSetRepository<T, ID> extends AbstractRepository<T, ID> implements RedisSetRepository<T, ID> {


    public DefaultRedisSetRepository(RepositoryInformation repositoryInformation, RedisOperations<byte[], byte[]> redisOps) {
        super(new RedisAdapter(redisOps), repositoryInformation);

    }

    @Override
    public Optional<Set<T>> findById(ID id) {
        byte[] redisKey = this.redisAdapter.createKey(id, getEntityInformation().getKeySpace(), getEntityInformation().getJavaType());

        Set<byte[]> raws = this.redisAdapter.getRedisOps().opsForSet().members(redisKey);
        RedisMetadata redisMetadata = RedisMetadata.createRedisEntity();
        redisMetadata.setRaws(raws);
        List<T> ts = this.redisAdapter.getConverter().readList(getEntityInformation().getJavaType(), redisMetadata);

        return Optional.of(Set.copyOf(ts));
    }

    @Override
    public void save(T entity) {
        byte[] redisKey = this.redisAdapter.createKey(
                getEntityInformation().getId(entity), getEntityInformation().getKeySpace(), getEntityInformation().getJavaType());

        RedisMetadata rdo = RedisMetadata.createRedisEntity();

        this.redisAdapter.getConverter().write(entity, rdo);

        this.redisAdapter.getRedisOps().opsForSet().add(redisKey, rdo.getRaw());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveAll(T... entities) {

        Optional<T> first = Arrays.stream(entities).findFirst();

        if (first.isPresent()) {

            T entity = first.get();

            byte[] redisKey = this.redisAdapter.createKey(
                    getEntityInformation().getId(entity), getEntityInformation().getKeySpace(), getEntityInformation().getJavaType());

            RedisMetadata rdo = RedisMetadata.createRedisEntity();

            this.redisAdapter.getConverter().write(entity, rdo);

            this.redisAdapter.getRedisOps().opsForSet().add(redisKey, rdo.getRaw());
        }
    }
}
