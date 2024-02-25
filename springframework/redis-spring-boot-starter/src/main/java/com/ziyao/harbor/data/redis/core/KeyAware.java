package com.ziyao.harbor.data.redis.core;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public interface KeyAware {

    void setKey(String key);

    String getKey();
}
