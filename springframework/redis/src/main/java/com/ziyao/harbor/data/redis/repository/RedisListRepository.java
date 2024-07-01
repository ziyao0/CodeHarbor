package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RedisRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author ziyao
 * @since 2024/06/25 15:14:54
 */
public interface RedisListRepository<T, ID> extends RedisRepository<T> {


    Optional<List<T>> findById(ID id);

    void save(T value);

    void saveAll(List<T> values);

    Optional<T> leftPop(ID id);

    Optional<T> rightPop(ID id);

    void leftPush(T value);

    void rightPush(T value);
}
