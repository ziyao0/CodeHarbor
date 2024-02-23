package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.*;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@NoRepositoryBean
public interface KeyValueRepository<V> extends Repository {
    /**
     * Returns the operations performed on simple values (or Strings in Redis terminology).
     *
     * @return value operations
     */
    ValueOperations<V> opsForValue();

    /**
     * Returns the operations performed on list values.
     *
     * @return list operations
     */
    ListOperations<V> opsForList();


    /**
     * Returns the operations performed on set values.
     *
     * @return set operations
     */
    SetOperations<V> opsForSet();

    /**
     * Returns the operations performed on ZSet values (also known as sorted sets).
     *
     * @return ZSet operations
     */
    ZSetOperations<V> opsForZSet();
}
