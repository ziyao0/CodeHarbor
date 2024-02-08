package com.ziyao.harbor.data.redis.support;

import com.ziyao.harbor.data.redis.core.RedisRepositoryInformation;
import com.ziyao.harbor.data.redis.core.RedisRepositoryMetadata;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.Assert;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
public class DefaultRedisRepositoryInformation implements RedisRepositoryInformation {

    private final RedisRepositoryMetadata metadata;
    private final Class<?> repositoryBaseClass;

    public DefaultRedisRepositoryInformation(RedisRepositoryMetadata metadata,
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
