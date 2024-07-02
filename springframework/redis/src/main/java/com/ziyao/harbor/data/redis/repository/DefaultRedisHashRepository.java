package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RedisHashEntity;
import com.ziyao.harbor.data.redis.core.RedisOpsAdapter;
import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import org.springframework.data.redis.core.RedisOperations;

import java.util.Map;
import java.util.Optional;

/**
 * @author ziyao
 * @since 2024/06/25 09:30:32
 */
public class DefaultRedisHashRepository<T extends RedisHashEntity, ID>
        extends AbstractRepository<T, ID> implements RedisHashRepository<T, ID> {

    public DefaultRedisHashRepository(RepositoryInformation repositoryInformation, RedisOperations<byte[], byte[]> redisOps) {
        super(new RedisOpsAdapter(redisOps), repositoryInformation);
    }

    @Override
    public Optional<Map<Object, Object>> findById(ID id) {
        return Optional.empty();
    }

    @Override
    public Optional<Object> findByHashKey(ID id, Object hashKey) {
        return Optional.empty();
    }

    @Override
    public Long deleteByHashKey(String id, Object... hashKey) {
        return 0L;
    }

    @Override
    public void save(Object hashKey, Object hashValue) {

    }

    @Override
    public void saveAll(Map<Object, Object> map) {

    }
}
