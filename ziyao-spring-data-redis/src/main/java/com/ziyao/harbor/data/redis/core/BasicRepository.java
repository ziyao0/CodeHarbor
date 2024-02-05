package com.ziyao.harbor.data.redis.core;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
@NoRepositoryBean
public interface BasicRepository<K, V, HK, HV> extends Repository<K, V, HK, HV> {

    /**
     * 判断缓存中有没有这个key
     */
    boolean hasKey(K k);

    /**
     * 删除key
     */
    boolean delete(K k);

    /**
     * 批量删除
     */
    long delete(Collection<K> ks);
}
