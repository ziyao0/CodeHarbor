package com.ziyao.harbor.data.redis.support;

import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.core.mapping.RedisPersistentEntity;
import org.springframework.data.redis.core.mapping.RedisPersistentProperty;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.Assert;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public final class EntityPropertyExtractor<T> {

    private final Class<T> entityClass;

    private final RedisPersistentEntity<?> persistentEntity;

    public EntityPropertyExtractor(Class<T> entityClass) {
        this.entityClass = entityClass;

        this.persistentEntity = new RedisMappingContext().getRequiredPersistentEntity(entityClass);
    }

    public Object extract(T entity, String fieldName) {
        Assert.notNull(entity, "entity 不能为空.");
        Assert.notNull(fieldName, "'fieldName' cannot be 'null'");

        return doExtract(entity, fieldName);
    }

    public Map<String, Object> extract(T entity) {
        Assert.notNull(entity, "entity 不能为空.");

        Map<String, Object> properties = new java.util.HashMap<>();

        for (RedisPersistentProperty redisPersistentProperty : persistentEntity) {
            String fieldName = redisPersistentProperty.getField().getName();
            Object value = doExtract(entity, fieldName);
            properties.put(fieldName, value);
        }
        return properties;
    }


    private Object doExtract(T entity, String fieldName) {
        PersistentPropertyAccessor<T> propertyAccessor = persistentEntity.getPropertyAccessor(entity);

        RedisPersistentProperty persistentProperty = createPersistentProperty(fieldName);
        return propertyAccessor.getProperty(persistentProperty);
    }

    /**
     * 创建属性描述符
     *
     * @param fieldName 字段名称
     * @return 返回属性描述符
     */
    private PropertyDescriptor createPropertyDescriptor(String fieldName) {
        try {
            return new PropertyDescriptor(fieldName, entityClass);
        } catch (IntrospectionException e) {
            // FIXME: 2023/11/16 处理异常抛出
            throw new RuntimeException(e);
        }
    }

    private RedisPersistentProperty createPersistentProperty(String fieldName) {
        PropertyDescriptor propertyDescriptor = createPropertyDescriptor(fieldName);

        return new RedisPersistentProperty(
                Property.of(TypeInformation.of(entityClass), propertyDescriptor),
                persistentEntity, SimpleTypeHolder.DEFAULT);
    }
}
