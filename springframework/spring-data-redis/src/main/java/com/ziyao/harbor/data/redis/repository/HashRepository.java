package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.HashOperations;
import com.ziyao.harbor.data.redis.core.Repository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 基于redis对于hash的存储库
 *
 * @author ziyao
 * @since 2023/4/23
 */
@NoRepositoryBean
public interface HashRepository<HK, HV> extends Repository {

    HashOperations<HK, HV> opsForHash(String... arguments);
}
