package com.ziyao.harbor.data.redis.core.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

/**
 * @author ziyao zhang
 * @time 2024/7/2
 */
public class ClassToStringConverter implements Converter<Class<?>, String> {
    @Nullable
    @Override
    public String convert(Class<?> source) {
        return source.getName();
    }
}
