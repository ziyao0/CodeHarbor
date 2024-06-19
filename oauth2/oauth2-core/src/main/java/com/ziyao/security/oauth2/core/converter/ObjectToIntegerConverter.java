package com.ziyao.security.oauth2.core.converter;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Collections;
import java.util.Set;

/**
 * @author ziyao
 * @since 2024/06/19 11:18:21
 */
public class ObjectToIntegerConverter implements GenericConverter {

    @Override
    public Set<GenericConverter.ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new GenericConverter.ConvertiblePair(Object.class, Boolean.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        if (source instanceof Integer) {
            return source;
        }
        if (source instanceof String) {
            return Integer.valueOf((String) source);
        }
        return null;
    }
}
