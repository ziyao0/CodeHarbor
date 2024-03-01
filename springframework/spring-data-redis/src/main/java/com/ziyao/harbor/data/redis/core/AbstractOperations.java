package com.ziyao.harbor.data.redis.core;

import com.ziyao.harbor.core.utils.Assert;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
public abstract class AbstractOperations<V> {
    protected final RedisTemplate<String, V> template;
    protected final long timeout;
    @Getter
    @Setter
    protected String key;

    public AbstractOperations(RedisTemplate<String, V> template, long timeout) {
        this.template = template;
        this.timeout = timeout;
    }


    protected void expire() {
        if (timeout > 0) {
            template.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    public void refresh() {
        Assert.isTrue(this.timeout >= 0, "没有设置正确的过期时间，timeout:" + timeout);
        template.expire(key, timeout, TimeUnit.SECONDS);
    }
}
