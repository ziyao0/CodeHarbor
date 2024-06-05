package com.ziyao.security.oauth2.converter;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.util.ClassUtils;

import java.util.*;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
public class ObjectToListStringConverter implements ConditionalGenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> convertibleTypes = new LinkedHashSet<>();
        convertibleTypes.add(new ConvertiblePair(Object.class, List.class));
        convertibleTypes.add(new ConvertiblePair(Object.class, Collection.class));
        return convertibleTypes;
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (targetType.getElementTypeDescriptor() == null
                || targetType.getElementTypeDescriptor().getType().equals(String.class) || sourceType == null
                || ClassUtils.isAssignable(sourceType.getType(), targetType.getElementTypeDescriptor().getType())) {
            return true;
        }
        return false;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        if (source instanceof Collection) {
            Collection<String> results = new ArrayList<>();
            for (Object object : ((Collection<?>) source)) {
                if (object != null) {
                    results.add(object.toString());
                }
            }
            return results;
        }
        return Collections.singletonList(source.toString());
    }

}
