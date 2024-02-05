package com.ziyao.harbor.data.redis.core;

import org.springframework.stereotype.Indexed;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
@Indexed
public interface Repository<K, V, HK, HV> {
    default String version() {
        return "v1.0";
    }
}
