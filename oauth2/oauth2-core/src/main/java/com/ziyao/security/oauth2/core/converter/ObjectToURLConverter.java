package com.ziyao.security.oauth2.core.converter;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Set;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
public class ObjectToURLConverter implements GenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Object.class, URL.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        if (source instanceof URL) {
            return source;
        }
        try {
            return new URI(source.toString()).toURL();
        } catch (Exception ex) {
            // Ignore
        }
        return null;
    }

}
