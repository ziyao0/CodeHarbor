package com.ziyao.harbor.data.redis;

import com.ziyao.harbor.data.redis.repository.RedisRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
@Repository
public interface CatCacheRepository extends RedisRepository<String, Cat, String, List<Cat>> {
}
