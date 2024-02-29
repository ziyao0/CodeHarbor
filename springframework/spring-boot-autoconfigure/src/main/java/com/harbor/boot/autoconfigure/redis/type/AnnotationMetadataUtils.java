package com.harbor.boot.autoconfigure.redis.type;

import org.springframework.core.type.StandardAnnotationMetadata;

/**
 * @author ziyao zhang
 * @since 2024/2/29
 */
public abstract class AnnotationMetadataUtils {

    @SuppressWarnings("deprecation")
    public static org.springframework.core.type.AnnotationMetadata introspect(Class<?> type) {
        return new StandardAnnotationMetadata(type, true);
    }
}
