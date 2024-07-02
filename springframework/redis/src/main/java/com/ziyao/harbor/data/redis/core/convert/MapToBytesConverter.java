package com.ziyao.harbor.data.redis.core.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziyao.harbor.core.utils.Collections;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

import java.util.Map;

/**
 * @author ziyao
 * @see <a href="https://blog.zziyao.cn">https://blog.zziyao.cn</a>
 * @since 2024/07/02 15:54:38
 */
public class MapToBytesConverter implements Converter<Map<byte[], byte[]>, byte[]> {


    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] convert(@Nullable Map<byte[], byte[]> source) {

        if (Collections.isEmpty(source)) {
            return new byte[0];
        }

        try {
            return mapper.writeValueAsBytes(source);
        } catch (JsonProcessingException e) {
            throw new SerializationException("map to bytes error " + e.getMessage(), e);
        }

    }
}
