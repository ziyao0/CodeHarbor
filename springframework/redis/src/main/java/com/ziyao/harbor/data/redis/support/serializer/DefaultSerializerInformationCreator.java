package com.ziyao.harbor.data.redis.support.serializer;

import com.ziyao.harbor.data.redis.core.RepositoryInformation;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
public class DefaultSerializerInformationCreator implements SerializerInformationCreator {


    @Override
    public SerializerInformation<?, ?, ?, ?> getInformation(RepositoryInformation repositoryInformation) {
        // 创建默认实体信息
        return new DefaultSerializerInformation<>(repositoryInformation.getKeyType(),
                repositoryInformation.getValueType(),
                repositoryInformation.getHashKeyType(),
                repositoryInformation.getHashValueType());
    }
}
