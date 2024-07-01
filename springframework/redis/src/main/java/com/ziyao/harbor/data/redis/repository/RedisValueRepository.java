package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RedisRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@NoRepositoryBean
public interface RedisValueRepository<T, ID> extends RedisRepository<T> {

    /**
     * 通过key获取对象
     */
    Optional<T> findById(Object id);

    /**
     * 保存
     */
    void save(T entity);

    /**
     * @return 如果返回 {@code true} 则表示不存在并保存成功，反则证明存在该key
     */
    boolean saveIfAbsent(T entity);
}
