package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author ziyao
 * @since 2024/06/25 15:14:54
 */
public interface RedisListRepository<T> extends Repository {


    Optional<List<T>> findById(String id);

    void save(String id, T value);

    void saveAll(String id, List<T> values);

    Optional<T> leftPop(String id);

    Optional<T> rightPop(String id);

    void leftPush(String id, T value);

    void rightPush(String id, T value);
}
