package com.ziyao.harbor.cache.core;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
public interface CacheEntityInformationCreator {

    <K, V, HK, HV> CacheEntityInformation<K, V, HK, HV> getInformation(Class<K> keyClass,
                                                                       Class<V> valueClass,
                                                                       Class<HK> hkeyClass,
                                                                       Class<HV> hvalueClass);
}
