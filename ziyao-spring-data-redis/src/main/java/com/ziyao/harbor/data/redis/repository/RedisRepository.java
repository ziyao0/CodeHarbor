package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.BasicRepository;
import org.springframework.data.redis.core.*;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
@NoRepositoryBean
public interface RedisRepository<K, V, HK, HV> extends BasicRepository<K, V, HK, HV> {
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

    HashOperations<K, HK, HV> opsForHash();

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
