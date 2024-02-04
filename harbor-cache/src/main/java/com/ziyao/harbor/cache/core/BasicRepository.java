package com.ziyao.harbor.cache.core;

import java.util.Collection;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
public interface BasicRepository<K, V, HK, HV> extends CacheRepository<K, V, HK, HV> {

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
