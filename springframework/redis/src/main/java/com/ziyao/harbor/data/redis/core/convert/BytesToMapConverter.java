package com.ziyao.harbor.data.redis.core.convert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;
import java.util.Map;

/**
 * @author ziyao
 * @see <a href="https://blog.zziyao.cn">https://blog.zziyao.cn</a>
 * @since 2024/07/02 16:00:58
 */
public class BytesToMapConverter implements Converter<byte[], Map<byte[], byte[]>> {


    public final TypeReference<Map<byte[], byte[]>> mapTypeReference = new TypeReference<>() {
    };

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Map<byte[], byte[]> convert(byte[] source) {

        if (source.length == 0) {
            return Map.of();
        }
        try {
            return mapper.readValue(source, mapTypeReference);
        } catch (IOException e) {
            throw new SerializationException("bytes to map error " + e.getMessage(), e);
        }
    }
}
