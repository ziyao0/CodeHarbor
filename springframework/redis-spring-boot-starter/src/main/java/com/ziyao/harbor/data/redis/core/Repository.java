package com.ziyao.harbor.data.redis.core;

import org.springframework.stereotype.Indexed;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
@Indexed
public interface Repository {

    /**
     * 判断缓存中有没有这个key
     */
    boolean hasKey(String... arguments);

    /**
     * 删除key
     */
    boolean delete(String... arguments);


    String getKey(String... arguments);

    default String version() {
        return "v1.0";
    }
}
