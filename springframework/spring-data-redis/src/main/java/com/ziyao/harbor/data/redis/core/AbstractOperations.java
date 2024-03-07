package com.ziyao.harbor.data.redis.core;

import com.ziyao.harbor.core.utils.Assert;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
@Getter
@Setter
public abstract class AbstractOperations<V> {

    protected final RedisTemplate<String, V> operations;
    protected final long timeout;
    protected String key;

    public AbstractOperations(RedisTemplate<String, V> operations, long timeout) {
        this.operations = operations;
        this.timeout = timeout;
        // 设置redis序列化
    }

    protected void expire() {
        if (timeout > 0) {
            operations.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    public void refresh() {
        Assert.isTrue(this.timeout >= 0, "没有设置正确的过期时间，timeout:" + timeout);
        operations.expire(key, this.timeout, TimeUnit.SECONDS);
    }

    public void refresh(long timeout, TimeUnit unit) {
        operations.expire(key, timeout, unit);
    }

    public boolean hasKey() {
        return Boolean.TRUE.equals(operations.hasKey(key));
    }


    public boolean deleteKey() {
        return Boolean.TRUE.equals(operations.delete(key));
    }

    public void setKeySerializer(RedisSerializer<?> serializer) {
        operations.setKeySerializer(serializer);
    }

    public void setValueSerializer(RedisSerializer<?> serializer) {
        operations.setValueSerializer(serializer);
    }

    public void setHashKeySerializer(RedisSerializer<?> serializer) {
        operations.setHashKeySerializer(serializer);
    }

    public void setHashValueSerializer(RedisSerializer<?> serializer) {
        operations.setHashValueSerializer(serializer);
    }
}
