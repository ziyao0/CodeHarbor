package com.ziyao.oauth2.converter;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
public class ObjectToInstantConverter implements GenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Object.class, Instant.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        if (source instanceof Instant) {
            return source;
        }
        if (source instanceof Date) {
            return ((Date) source).toInstant();
        }
        if (source instanceof Number) {
            return Instant.ofEpochSecond(((Number) source).longValue());
        }
        try {
            return Instant.ofEpochSecond(Long.parseLong(source.toString()));
        } catch (Exception ex) {
            // Ignore
        }
        try {
            return Instant.parse(source.toString());
        } catch (Exception ex) {
            // Ignore
        }
        return null;
    }

}
