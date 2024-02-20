package com.ziyao.harbor.data.redis;

import com.ziyao.harbor.data.redis.core.Key;
import com.ziyao.harbor.data.redis.repository.HashRepository;
import com.ziyao.harbor.data.redis.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
@Key(index = "redis:key:cat")
@Repository
public interface CatRepository extends KeyValueRepository<String, Cat>, HashRepository<String, String, List<Cat>> {
}
