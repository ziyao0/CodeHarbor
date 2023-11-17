package com.ziyao.harbor.elastic;

import com.ziyao.harbor.elastic.entity.User;
import com.ziyao.harbor.elastic.repository.ESRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/11/16
 */
@Repository
public interface UserESRepository extends ESRepository<User, Long> {

    List<User> findByName(String name);

    List<User> findByAddr(String addr);
}
