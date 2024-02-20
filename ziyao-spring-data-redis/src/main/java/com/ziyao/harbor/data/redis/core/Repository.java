package com.ziyao.harbor.data.redis.core;

import org.springframework.stereotype.Indexed;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
@Indexed
public interface Repository<K> {

    /**
     * 判断缓存中有没有这个key
     */
    boolean hasKey();

    /**
     * 删除key
     */
    boolean delete();


    String getKey();

    default String version() {
        return "v1.0";
    }
}
