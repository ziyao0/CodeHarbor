package com.ziyao.harbor.usercenter.repository.redis;

import com.ziyao.harbor.data.redis.repository.RedisHashRepository;
import com.ziyao.security.oauth2.core.RegisteredApp;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Repository;

/**
 * @author ziyao
 * @since 2024/06/08 21:51:02
 */
@RedisHash
@Repository
public interface RedisRegisteredAppRepository extends RedisHashRepository<Long, RegisteredApp> {
}
