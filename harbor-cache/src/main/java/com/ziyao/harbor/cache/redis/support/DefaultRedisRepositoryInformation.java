package com.ziyao.harbor.cache.redis.support;

import com.ziyao.harbor.cache.core.CacheRepositoryMetadata;
import com.ziyao.harbor.cache.core.CacheRepositoryInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.Assert;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
public class DefaultRedisRepositoryInformation implements CacheRepositoryInformation {

    private final CacheRepositoryMetadata metadata;
    private final Class<?> repositoryBaseClass;

    public DefaultRedisRepositoryInformation(CacheRepositoryMetadata metadata,
                                             Class<?> repositoryBaseClass) {
        Assert.notNull(metadata, "Repository metadata must not be null");
        Assert.notNull(repositoryBaseClass, "Repository base class must not be null");

        this.metadata = metadata;
        this.repositoryBaseClass = repositoryBaseClass;
    }

    @Override
    public TypeInformation<?> getKeyTypeInformation() {
        return metadata.getKeyTypeInformation();
    }

    @Override
    public TypeInformation<?> getValueTypeInformation() {
        return metadata.getValueTypeInformation();
    }

    @Override
    public TypeInformation<?> getHashKeyTypeInformation() {
        return metadata.getHashKeyTypeInformation();
    }

    @Override
    public TypeInformation<?> getHashValueTypeInformation() {
        return metadata.getValueTypeInformation();
    }

    @Override
    public Class<?> getRepositoryInterface() {
        return metadata.getRepositoryInterface();
    }

    @Override
    public Class<?> getRepositoryBaseClass() {
        return this.repositoryBaseClass;
    }
}
