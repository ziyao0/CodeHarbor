package com.ziyao.harbor.data.redis.support.serializer;

import com.ziyao.harbor.data.redis.core.RepositoryInformation;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
public interface SerializerInformationCreator {

    SerializerInformation<?, ?, ?, ?> getInformation(RepositoryInformation repositoryInformation);
}
