package com.ziyao.harbor.data.redis.support;

import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import com.ziyao.harbor.data.redis.core.RepositoryMetadata;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.Assert;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
public class DefaultRepositoryInformation implements RepositoryInformation {

    private final RepositoryMetadata metadata;
    private final Class<?> repositoryBaseClass;

    public DefaultRepositoryInformation(RepositoryMetadata metadata,
                                        Class<?> repositoryBaseClass) {
        Assert.notNull(metadata, "Repository metadata must not be null");
        Assert.notNull(repositoryBaseClass, "Repository base class must not be null");

        this.metadata = metadata;
        this.repositoryBaseClass = repositoryBaseClass;
    }


    @Override
    public Class<?> getRepositoryInterface() {
        return metadata.getRepositoryInterface();
    }

    @Override
    public TypeInformation<?> getIdTypeInformation() {
        return metadata.getIdTypeInformation();
    }

    @Override
    public TypeInformation<?> getJavaTypeInformation() {
        return metadata.getJavaTypeInformation();
    }

    @Override
    public Class<?> getRepositoryBaseClass() {
        return this.repositoryBaseClass;
    }
}
