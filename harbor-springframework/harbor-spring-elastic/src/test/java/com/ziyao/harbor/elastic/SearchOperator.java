package com.ziyao.harbor.elastic;

import com.ziyao.harbor.elastic.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/11/14
 */
@Repository
public interface SearchOperator extends ElasticsearchRepository<User, Long> {

    List<User> findByName(String name);

    List<User> findByAddr(String name);


}
