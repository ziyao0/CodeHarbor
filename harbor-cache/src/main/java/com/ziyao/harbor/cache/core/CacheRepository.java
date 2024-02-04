package com.ziyao.harbor.cache.core;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
public interface CacheRepository<K, V, HK, HV> {
    default String version() {
        return "v1.0";
    }
}
