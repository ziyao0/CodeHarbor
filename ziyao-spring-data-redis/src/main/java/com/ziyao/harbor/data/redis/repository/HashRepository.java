package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.Repository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 基于redis对于hash的存储库
 *
 * @author ziyao
 * @since 2023/4/23
 */
@NoRepositoryBean
public interface HashRepository<K, HK, HV> extends Repository<K> {

    HashOperations<K, HK, HV> opsForHash();
}
