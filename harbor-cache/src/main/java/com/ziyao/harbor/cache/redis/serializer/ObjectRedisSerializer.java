package com.ziyao.harbor.cache.redis.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
public class ObjectRedisSerializer<T> implements RedisSerializer<T> {

    static final byte[] EMPTY_ARRAY = new byte[0];

    private final ObjectMapper mapper;
    private final Class<T> entityClass;

    public ObjectRedisSerializer(Class<T> entityClass) {
        this.mapper = new ObjectMapper();
        this.entityClass = entityClass;
    }

    public ObjectRedisSerializer(ObjectMapper mapper, Class<T> entityClass) {
        this.mapper = mapper;
        this.entityClass = entityClass;
    }

    @Override
    public byte[] serialize(T source) throws SerializationException {
        if (source == null) {
            return EMPTY_ARRAY;
        }

        try {
            return mapper.writeValueAsBytes(source);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Could not write JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public T deserialize(byte[] source) throws SerializationException {
        return deserialize(source, entityClass);
    }

    @Nullable
    public T deserialize(@Nullable byte[] source, Class<T> type) throws SerializationException {

        Assert.notNull(type,
                "Deserialization type must not be null! Please provide Object.class to make use of Jackson2 default typing.");

        if (isEmpty(source)) {
            return null;
        }

        try {
            return mapper.readValue(source, type);
        } catch (Exception ex) {
            throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    static boolean isEmpty(@Nullable byte[] data) {
        return (data == null || data.length == 0);
    }


}
