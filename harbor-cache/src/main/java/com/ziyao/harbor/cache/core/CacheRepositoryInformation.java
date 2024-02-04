package com.ziyao.harbor.cache.core;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
public interface CacheRepositoryInformation extends CacheRepositoryMetadata{

    /**
     * Returns the base class to be used to create the proxy backing instance.
     */
    Class<?> getRepositoryBaseClass();
}
