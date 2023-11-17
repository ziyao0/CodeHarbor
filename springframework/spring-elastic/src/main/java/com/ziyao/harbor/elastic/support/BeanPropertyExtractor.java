package com.ziyao.harbor.elastic.support;

import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentProperty;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchPersistentProperty;
import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.util.Assert;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public final class BeanPropertyExtractor<T> {

    private final Class<T> entityClass;

    private final SimpleElasticsearchPersistentEntity<?> persistentEntity;

    public BeanPropertyExtractor(Class<T> entityClass) {
        this.entityClass = entityClass;

        this.persistentEntity = new SimpleElasticsearchMappingContext()
                .getRequiredPersistentEntity(entityClass);
    }

    public Object extract(T entity, String fieldName) {
        Assert.notNull(entity, "entity 不能为空.");
        Assert.notNull(fieldName, "'fieldName' cannot be 'null'");

        return doExtract(entity, fieldName);
    }

    public Map<String, Object> extract(T entity) {
        Assert.notNull(entity, "entity 不能为空.");

        Map<String, Object> properties = new java.util.HashMap<>();

        for (ElasticsearchPersistentProperty elasticsearchPersistentProperty : persistentEntity) {
            String fieldName = elasticsearchPersistentProperty.getFieldName();
            Object value = doExtract(entity, fieldName);
            properties.put(fieldName, value);
        }
        return properties;
    }


    private Object doExtract(T entity, String fieldName) {
        PersistentPropertyAccessor<T> propertyAccessor = persistentEntity.getPropertyAccessor(entity);

        SimpleElasticsearchPersistentProperty persistentProperty = createPersistentProperty(fieldName);
        return propertyAccessor.getProperty(persistentProperty);
    }

    private PropertyDescriptor createPropertyDescriptor(String fieldName) {
        try {
            return new PropertyDescriptor(fieldName, entityClass);
        } catch (IntrospectionException e) {
            // FIXME: 2023/11/16 处理异常抛出
            throw new RuntimeException(e);
        }
    }

    private SimpleElasticsearchPersistentProperty createPersistentProperty(String fieldName) {
        PropertyDescriptor propertyDescriptor = createPropertyDescriptor(fieldName);

        return new SimpleElasticsearchPersistentProperty(
                Property.of(ClassTypeInformation.from(entityClass), propertyDescriptor),
                persistentEntity, SimpleTypeHolder.DEFAULT);
    }
}
