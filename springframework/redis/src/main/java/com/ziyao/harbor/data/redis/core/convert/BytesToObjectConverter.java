package com.ziyao.harbor.data.redis.core.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author ziyao zhang
 * @time 2024/6/30
 */
public class BytesToObjectConverter implements Converter<byte[], Object> {

    private final ObjectMapper mapper = new ObjectMapper();

    public BytesToObjectConverter() {
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public Object convert(@NonNull byte[] source) {

        if (isEmpty(source)) {
            return null;
        }

        try {
            return mapper.readValue(source, Object.class);
        } catch (Exception ex) {
            throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    static boolean isEmpty(@Nullable byte[] data) {
        return (data == null || data.length == 0);
    }

}
