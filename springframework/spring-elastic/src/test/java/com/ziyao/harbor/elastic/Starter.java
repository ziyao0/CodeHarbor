package com.ziyao.harbor.elastic;

import com.ziyao.harbor.elastic.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;

/**
 * @author ziyao zhang
 * @since 2023/11/14
 */
@EnableESRepositories(basePackages = "com.ziyao.harbor.elastic")
@SpringBootApplication
public class Starter implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }


    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private UserESRepository userESRepository;


    @Override
    public void run(String... args) throws Exception {

        User user = new User();

        Sort sort = Sort.by(Sort.Direction.ASC, "age");

        Page<User> users1 = userESRepository.searchMatcher(user, PageRequest.of(0, 10, sort));
        for (User user1 : users1) {
            System.out.println(user1);
        }
//        // 创建索引
//        IndexOperations indexOperations = elasticsearchTemplate.indexOps(IndexCoordinates.of("cat"));
//        if (indexOperations.exists()) {
//            System.out.println("索引存在");
//        }
//        else {
//            Document mapping = indexOperations.createMapping(Cat.class);
////            Settings settings = indexOperations.createSettings(null);
////            boolean b = indexOperations.create(null, mapping);
//            boolean b = indexOperations.create();
//            System.out.println(b);
//        }


//        NativeQueryBuilder builder = new NativeQueryBuilder();

//        Query query = QueryBuilders.matchQueryAsQuery("addr", "南瑞", Operator.And, 1.5f);
//        Query addrQuery = QueryBuilders.match().field("addr")
//                .query("北京中关村")
//                .analyzer("ik_max_word")
//                .operator(Operator.And)
//                .boost(1f).build()._toQuery();


//        NativeQueryBuilder nativeQueryBuilder = builder.withQuery(addrQuery).withPageable(Pageable.ofSize(100));


//        Criteria criteria = new Criteria("addr").is("北京中关村").or("name").is("李四").boost(2f);
//
//        CriteriaQueryBuilder queryBuilder = CriteriaQuery.builder(criteria).withPageable(PageRequest.of(0, 10)).withSort(Sort.by(Sort.Direction.ASC, "age"));
//
//
//        SearchHits<User> search = elasticsearchTemplate.search(queryBuilder.build(), User.class);
//
//
//        List<SearchHit<User>> searchHits = search.getSearchHits();
//        for (SearchHit<User> searchHit : searchHits) {
//            User content = searchHit.getContent();
//            System.out.println(content);
//        }
    }
}
