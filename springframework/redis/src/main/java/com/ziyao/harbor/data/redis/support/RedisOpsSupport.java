package com.ziyao.harbor.data.redis.support;

import com.ziyao.harbor.data.redis.support.serializer.ObjectRedisSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.*;

/**
 * @author ziyao zhang
 * @since 2024/2/23
 */
public abstract class RedisOpsSupport {

    private static final Logger log = LoggerFactory.getLogger(RedisOpsSupport.class);

    private static RedisTemplate operations;

    @SuppressWarnings("unchecked")
    public static <V> RedisTemplate<String, V> getOperations() {
        return RedisOpsSupport.operations;
    }

    @SuppressWarnings("unchecked")
    public static <V> ValueOperations<String, V> opsForValue(Class<V> valueJavaType) {
        RedisOpsSupport.operations.setValueSerializer(new ObjectRedisSerializer<V>(valueJavaType));
        return RedisOpsSupport.operations.opsForValue();
    }

    @SuppressWarnings("unchecked")
    public static <V> ListOperations<String, V> opsForList(Class<V> valueJavaType) {
        RedisOpsSupport.operations.setValueSerializer(new ObjectRedisSerializer<V>(valueJavaType));
        return RedisOpsSupport.operations.opsForList();
    }

    @SuppressWarnings("unchecked")
    public static <V> SetOperations<String, V> opsForSet(Class<V> valueJavaType) {
        RedisOpsSupport.operations.setValueSerializer(new ObjectRedisSerializer<V>(valueJavaType));
        return RedisOpsSupport.operations.opsForSet();
    }

    @SuppressWarnings("unchecked")
    public static <V> ZSetOperations<String, V> opsForZSet(Class<V> valueJavaType) {
        RedisOpsSupport.operations.setValueSerializer(new ObjectRedisSerializer<V>(valueJavaType));
        return RedisOpsSupport.operations.opsForZSet();
    }

    @SuppressWarnings("unchecked")
    public static <HK, HV> HashOperations<String, HK, HV> opsForHash(Class<HK> hashKeyJavaType, Class<HV> hashValueJavaType) {
        RedisOpsSupport.operations.setHashKeySerializer(new ObjectRedisSerializer<HK>(hashKeyJavaType));
        RedisOpsSupport.operations.setHashValueSerializer(new ObjectRedisSerializer<HV>(hashValueJavaType));
        return RedisOpsSupport.operations.opsForHash();
    }

    public static void setOperations(RedisTemplate<?, ?> operations) {
        RedisOpsSupport.operations = operations;
        log.info("redis operations init {}", operations);
    }
}
