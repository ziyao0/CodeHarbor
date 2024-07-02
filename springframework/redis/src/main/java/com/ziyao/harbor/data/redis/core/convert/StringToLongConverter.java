package com.ziyao.harbor.data.redis.core.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

/**
 * @author ziyao zhang
 * @time 2024/7/2
 */
public class StringToLongConverter implements Converter<String, Long> {
    @Nullable
    @Override
    public Long convert(String source) {
        return Long.parseLong(source);
    }
}
