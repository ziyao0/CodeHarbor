package com.ziyao.security.oauth2.core.converter;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Collections;
import java.util.Set;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
public class ObjectToStringConverter implements GenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Object.class, String.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return (source != null) ? source.toString() : null;
    }

}
