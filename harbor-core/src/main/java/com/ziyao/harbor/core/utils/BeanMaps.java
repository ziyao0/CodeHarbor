package com.ziyao.harbor.core.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author ziyao zhang
 * @since 2023/9/4
 */
public abstract class BeanMaps {

    private BeanMaps() {
    }

    public static Map<String, Object> tomap(Object object) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();

        if (Objects.nonNull(object)) {
            Class<?> clazz = object.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(object);
                map.put(fieldName, value);
            }
        }
        return map;
    }
}
