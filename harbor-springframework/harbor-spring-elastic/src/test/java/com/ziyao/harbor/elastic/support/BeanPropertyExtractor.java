package com.ziyao.harbor.elastic.support;

import com.ziyao.harbor.core.Extractor;
import com.ziyao.harbor.elastic.entity.User;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchPersistentProperty;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformationCreatorImpl;
import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.TypeInformation;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class BeanPropertyExtractor<T> implements Extractor<T, Object> {

    private final Class<T> beanClass;
    private final String propertyName;

    public BeanPropertyExtractor(Class<T> beanClass, String propertyName) {
        this.beanClass = beanClass;
        this.propertyName = propertyName;
    }

    @Override
    public Object extract(T bean) {
        SimpleElasticsearchMappingContext simpleElasticsearchMappingContext = new SimpleElasticsearchMappingContext();
        ElasticsearchEntityInformationCreatorImpl creator = new ElasticsearchEntityInformationCreatorImpl(simpleElasticsearchMappingContext);


        SimpleElasticsearchPersistentEntity<?> persistentEntity = simpleElasticsearchMappingContext
                .getRequiredPersistentEntity(User.class);

        PersistentPropertyAccessor<T> propertyAccessor = persistentEntity.getPropertyAccessor(bean);

        PropertyDescriptor propertyDescriptor;
        try {
            propertyDescriptor = new PropertyDescriptor(propertyName, beanClass);
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
        Property property2 = Property.of(TypeInformation.of(beanClass), propertyDescriptor);

        SimpleElasticsearchPersistentProperty property = new SimpleElasticsearchPersistentProperty(property2,
                persistentEntity, SimpleTypeHolder.DEFAULT);

        return propertyAccessor.getProperty(property);

    }


    public static <T> Object extractProperty(T bean, Class<T> beanClass, String propertyName) {
        BeanPropertyExtractor<T> extractor = new BeanPropertyExtractor<>(beanClass, propertyName);
        return extractor.extract(bean);
    }
}
