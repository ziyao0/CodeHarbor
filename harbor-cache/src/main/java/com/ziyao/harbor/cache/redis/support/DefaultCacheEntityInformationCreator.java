package com.ziyao.harbor.cache.redis.support;

import com.ziyao.harbor.cache.core.CacheEntityInformation;
import com.ziyao.harbor.cache.core.CacheEntityInformationCreator;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
public class DefaultCacheEntityInformationCreator implements CacheEntityInformationCreator {


    @Override
    public <K, V, HK, HV> CacheEntityInformation<K, V, HK, HV> getInformation(Class<K> keyClass,
                                                                              Class<V> valueClass,
                                                                              Class<HK> hkeyClass,
                                                                              Class<HV> hvalueClass) {
        // 创建默认实体信息
        return new RedisEntityInformation<>(keyClass, valueClass, hkeyClass, hvalueClass);
    }
}
