package com.ziyao.harbor.data.redis.serializer;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
public class IntegerRedisSerializer extends ObjectRedisSerializer<Integer> {

    public IntegerRedisSerializer() {
        super(Integer.class);
    }
}
