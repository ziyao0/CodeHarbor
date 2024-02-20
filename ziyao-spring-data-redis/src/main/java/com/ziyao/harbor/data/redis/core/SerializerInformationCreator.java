package com.ziyao.harbor.data.redis.core;

import com.ziyao.harbor.data.redis.serializer.SerializerInformation;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
public interface SerializerInformationCreator {

    <K, V, HK, HV> SerializerInformation<K, V, HK, HV>
    getInformation(Class<K> keyClass, Class<V> valueClass, Class<HK> hkeyClass, Class<HV> hvalueClass);
}
