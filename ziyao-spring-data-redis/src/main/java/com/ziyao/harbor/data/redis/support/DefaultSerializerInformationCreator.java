package com.ziyao.harbor.data.redis.support;

import com.ziyao.harbor.data.redis.core.SerializerInformationCreator;
import com.ziyao.harbor.data.redis.serializer.SerializerInformation;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
public class DefaultSerializerInformationCreator implements SerializerInformationCreator {


    @Override
    public <K, V, HK, HV> SerializerInformation<K, V, HK, HV>
    getInformation(Class<K> keyClass, Class<V> valueClass, Class<HK> hkeyClass, Class<HV> hvalueClass) {
        // 创建默认实体信息
        return new DefaultSerializerInformation<>(keyClass, valueClass, hkeyClass, hvalueClass);
    }
}
