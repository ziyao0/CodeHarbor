package com.ziyao.harbor.core.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ziyao zhang
 * @since 2023/9/4
 */
public abstract class BeanMaps {

    private BeanMaps() {
    }

    public static Map<String, Object> tomap(Object object) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (object != null) {
                Class<?> clazz = object.getClass();
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    Object value = field.get(object);
                    map.put(fieldName, value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
