package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.Repository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@NoRepositoryBean
public interface RedisValueRepository<T> extends Repository {


    /**
     * 通过key获取对象
     */
    Optional<T> findById(String id);

    /**
     * 保存
     */
    void save(String id, T value);

    /**
     * 保存
     */
    void save(String id, T value, long timeout, TimeUnit timeUnit);

}
