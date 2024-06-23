package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.Repository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@NoRepositoryBean
public interface KeyValueRepository<T> extends Repository {

    /**
     * 通过key获取对象
     */
    Optional<T> findById(String id);

    /**
     * 保存
     */
    void save(T value);

    /**
     * 删除
     */
    void delete(T value);

    /**
     * 通过key删除
     */
    void deleteById(String id);


}
