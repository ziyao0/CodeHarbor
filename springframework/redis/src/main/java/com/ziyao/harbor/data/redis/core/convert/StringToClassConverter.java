package com.ziyao.harbor.data.redis.core.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author ziyao zhang
 * @time 2024/7/2
 */
public class StringToClassConverter implements Converter<String, Class<?>> {
    @Nullable
    @Override
    public Class<?> convert(@NonNull String source) {
        try {
            return Class.forName(source);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Class not found: " + source, e);
        }
    }
}
