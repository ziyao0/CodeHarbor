package com.ziyao.harbor;

import com.ziyao.harbor.elastic.entity.User;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformationCreatorImpl;
import org.springframework.data.util.TypeInformation;
import org.springframework.stereotype.Service;

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


    public static void main(String[] args) {

        SimpleElasticsearchMappingContext simpleElasticsearchMappingContext = new SimpleElasticsearchMappingContext();
        ElasticsearchEntityInformationCreatorImpl creator = new ElasticsearchEntityInformationCreatorImpl(simpleElasticsearchMappingContext);

        User user = new User();
        user.setId("1");
        user.setAddr("海淀");
        user.setName("李四");

        ElasticsearchEntityInformation<User, Object> information = creator.getEntityInformation(User.class);
        Object id = information.getId(user);
        System.out.println(information);
    }
}
