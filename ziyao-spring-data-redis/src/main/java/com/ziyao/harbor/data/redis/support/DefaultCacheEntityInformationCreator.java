package com.ziyao.harbor.data.redis.support;

import com.ziyao.harbor.data.redis.core.RedisEntityInformation;
import com.ziyao.harbor.data.redis.core.RedisEntityInformationCreator;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
public class DefaultCacheEntityInformationCreator implements RedisEntityInformationCreator {


    @Override
    public <K, V, HK, HV> RedisEntityInformation<K, V, HK, HV> getInformation(Class<K> keyClass,
                                                                              Class<V> valueClass,
                                                                              Class<HK> hkeyClass,
                                                                              Class<HV> hvalueClass) {
        // 创建默认实体信息
        return new com.ziyao.harbor.data.redis.support.RedisEntityInformation<>(keyClass, valueClass, hkeyClass, hvalueClass);
    }
}
