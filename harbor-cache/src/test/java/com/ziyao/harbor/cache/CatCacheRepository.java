package com.ziyao.harbor.cache;

import com.ziyao.harbor.cache.redis.repository.RedisRepository;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
public interface CatCacheRepository extends RedisRepository<String, String, String, String> {
}
