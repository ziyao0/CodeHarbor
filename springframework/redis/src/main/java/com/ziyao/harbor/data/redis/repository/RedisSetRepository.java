package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * @author ziyao
 * @since 2024/06/25 15:36:24
 */
public interface RedisSetRepository<T> extends Repository {


    Optional<Set<T>> findById(String id);

    void save(String id, T value);

    @SuppressWarnings("unchecked")
    void saveAll(String id, T... values);
}
