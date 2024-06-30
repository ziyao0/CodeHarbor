package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.Repository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Deprecated
@NoRepositoryBean
public interface RedisStringKeyValueRepository<T> extends Repository {


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

    /**
     * @return 如果返回 {@code true} 则表示不存在并保存成功，反则证明存在该key
     */
    boolean saveIfAbsent(String id, T value);
}
