package com.ziyao.harbor.data.redis.core;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
public interface RedisRepositoryInformation extends RedisRepositoryMetadata {

    /**
     * Returns the base class to be used to create the proxy backing instance.
     */
    Class<?> getRepositoryBaseClass();
}
