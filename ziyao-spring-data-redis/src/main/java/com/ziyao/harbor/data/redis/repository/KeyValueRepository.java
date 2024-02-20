package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.Repository;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@NoRepositoryBean
public interface KeyValueRepository<K, V> extends Repository<K> {
    /**
     * Returns the operations performed on simple values (or Strings in Redis terminology).
     *
     * @return value operations
     */
    ValueOperations<K, V> opsForValue();

    /**
     * Returns the operations performed on list values.
     *
     * @return list operations
     */
    ListOperations<K, V> opsForList();



    /**
     * Returns the operations performed on set values.
     *
     * @return set operations
     */
    SetOperations<K, V> opsForSet();

    /**
     * Returns the operations performed on ZSet values (also known as sorted sets).
     *
     * @return ZSet operations
     */
    ZSetOperations<K, V> opsForZSet();
}
