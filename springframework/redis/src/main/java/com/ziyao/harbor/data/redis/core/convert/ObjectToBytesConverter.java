package com.ziyao.harbor.data.redis.core.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

/**
 * @author ziyao zhang
 * @time 2024/6/30
 */
public class ObjectToBytesConverter implements Converter<Object, byte[]> {

    private final ObjectMapper mapper = new ObjectMapper();
    static final byte[] EMPTY_ARRAY = new byte[0];

    public ObjectToBytesConverter() {
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public byte[] convert(@Nullable Object source) {

        if (source == null) {
            return EMPTY_ARRAY;
        }

        try {
            return mapper.writeValueAsBytes(source);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Could not write JSON: " + e.getMessage(), e);
        }
    }
}
