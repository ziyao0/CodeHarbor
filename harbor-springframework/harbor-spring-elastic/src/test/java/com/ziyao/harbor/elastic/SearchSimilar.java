package com.ziyao.harbor.elastic;

import com.ziyao.harbor.elastic.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;

/**
 * @author ziyao zhang
 * @since 2023/11/15
 */
@org.springframework.stereotype.Repository
public interface SearchSimilar extends Repository<User, Long> {


//    Page<User> searchSimilar1(User entity, String[] fields, Pageable pageable, Sort sort);
}
