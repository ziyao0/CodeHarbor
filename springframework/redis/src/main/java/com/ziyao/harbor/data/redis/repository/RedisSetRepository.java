package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RedisRepository;

import java.util.Optional;
import java.util.Set;

/**
 * @author ziyao
 * @since 2024/06/25 15:36:24
 */
public interface RedisSetRepository<T, ID> extends RedisRepository<T, ID> {


    Optional<Set<T>> findById(ID id);

    void save(T entity);

    @SuppressWarnings("unchecked")
    void saveAll(T... entities);
}
