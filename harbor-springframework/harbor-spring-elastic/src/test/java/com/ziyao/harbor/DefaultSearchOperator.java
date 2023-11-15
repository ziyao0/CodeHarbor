package com.ziyao.harbor;

import com.ziyao.harbor.elastic.entity.User;
import com.ziyao.harbor.elastic.support.BeanPropertyExtractor;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentProperty;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchPersistentProperty;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformationCreatorImpl;
import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.TypeInformation;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

/**
 * @author ziyao zhang
 * @since 2023/11/15
 */
@Service
public class DefaultSearchOperator extends SimpleElasticsearchPersistentEntity<User> {


    public DefaultSearchOperator(TypeInformation<User> typeInformation,
                                 ContextConfiguration contextConfiguration) {
        super(typeInformation, contextConfiguration);
    }


//    @Override
//    public Page<User> searchSimilar1(User entity, String[] fields, Pageable pageable, Sort sort) {
//        Assert.notNull(entity, "Cannot search similar records for 'null'.");
//        Assert.notNull(pageable, "'pageable' cannot be 'null'");
//
//        MoreLikeThisQuery query = new MoreLikeThisQuery();
//        query.setPageable(pageable);
//
//        if (fields != null) {
//            query.addFields(fields);
//        }
//        Criteria criteria = new Criteria();
//        for (String field : fields) {
//            criteria.and(field).is("");
//        }
//
//
//        CriteriaQueryBuilder queryBuilder = CriteriaQuery.builder(criteria).withPageable(pageable).withSort(Sort.by(Sort.Direction.ASC, "age"));
//
//
////        elasticsearchTemplate.search()
//        return null;
//    }

    private Object extractFromBean(Object bean) {
        return null;
    }


    public static void main(String[] args) throws IntrospectionException, ClassNotFoundException {

        SimpleElasticsearchMappingContext simpleElasticsearchMappingContext = new SimpleElasticsearchMappingContext();
        ElasticsearchEntityInformationCreatorImpl creator = new ElasticsearchEntityInformationCreatorImpl(simpleElasticsearchMappingContext);

        User user = new User();
        user.setId("1");
        user.setAddr("海淀");
        user.setName("李四");
        user.setAge(1L);


        Object addr = BeanPropertyExtractor.extractProperty(user, User.class, "addr");

        System.out.println(addr);

        SimpleElasticsearchPersistentEntity<?> persistentEntity = simpleElasticsearchMappingContext
                .getRequiredPersistentEntity(user.getClass());

        PersistentPropertyAccessor<User> propertyAccessor = persistentEntity.getPropertyAccessor(user);

        PropertyDescriptor descriptor1 = new PropertyDescriptor("age", user.getClass());
        Class<?> propertyType = descriptor1.getPropertyType();


        System.out.println(propertyType.getName());

        Property property2 = Property.of(TypeInformation.of(user.getClass()), descriptor1);

        SimpleElasticsearchPersistentProperty property = new SimpleElasticsearchPersistentProperty(property2,
                persistentEntity, SimpleTypeHolder.DEFAULT);

        Object property1 = propertyAccessor.getProperty(property);
        Object cast = propertyType.cast(property1);
        System.out.println(cast);

    }

}
