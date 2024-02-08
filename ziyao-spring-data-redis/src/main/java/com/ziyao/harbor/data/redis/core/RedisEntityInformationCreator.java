package com.ziyao.harbor.data.redis.core;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
public interface RedisEntityInformationCreator {

    <K, V, HK, HV> RedisEntityInformation<K, V, HK, HV> getInformation(Class<K> keyClass,
                                                                       Class<V> valueClass,
                                                                       Class<HK> hkeyClass,
                                                                       Class<HV> hvalueClass);
}
