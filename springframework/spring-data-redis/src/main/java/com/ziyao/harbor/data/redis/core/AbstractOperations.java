package com.ziyao.harbor.data.redis.core;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.data.redis.support.serializer.SerializerInformation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
public abstract class AbstractOperations<V> {
    protected final RedisTemplate<String, V> operations;
    protected final long timeout;
    @Getter
    @Setter
    protected String key;

    public AbstractOperations(RedisTemplate<String, V> operations, long timeout, SerializerInformation metadata) {
        this.operations = operations;
        this.timeout = timeout;
        // 设置redis序列化
        operations.setKeySerializer(metadata.getKeySerializer());
        operations.setValueSerializer(metadata.getValueSerializer());
        operations.setHashKeySerializer(metadata.getHashKeySerializer());
        operations.setHashValueSerializer(metadata.getHashValueSerializer());
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

    public boolean deleteKey() {
        return Boolean.TRUE.equals(operations.delete(key));
    }
}
