package com.ziyao.security.oauth2.core.converter;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
public class ObjectToMapStringObjectConverter implements ConditionalGenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Object.class, Map.class));
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (targetType.getElementTypeDescriptor() == null
                || targetType.getMapKeyTypeDescriptor().getType().equals(String.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        if (!(source instanceof Map)) {
            return null;
        }
        Map<?, ?> sourceMap = (Map<?, ?>) source;
        Map<String, Object> result = new HashMap<>();
        sourceMap.forEach((k, v) -> result.put(k.toString(), v));
        return result;
    }

}
