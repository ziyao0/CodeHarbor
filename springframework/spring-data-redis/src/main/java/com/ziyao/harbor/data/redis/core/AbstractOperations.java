package com.ziyao.harbor.data.redis.core;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
public abstract class AbstractOperations<V> implements KeyAware {
    protected final RedisTemplate<String, V> template;
    protected String key;
    protected long timeout;

    public AbstractOperations(RedisTemplate<String, V> template, long timeout) {
        this.template = template;
        this.timeout = timeout;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    protected void expire() {
        if (timeout > 0) {
            template.expire(key, timeout, TimeUnit.SECONDS);
        }
    }
}
