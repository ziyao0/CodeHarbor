package com.ziyao.harbor.data.redis.core;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
public abstract class AbstractOperations<V> implements KeyAware {
    protected final RedisTemplate<String, V> template;
    protected String key;

    public AbstractOperations(RedisTemplate<String, V> template, String key) {
        this.template = template;
        this.key = key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }

}
