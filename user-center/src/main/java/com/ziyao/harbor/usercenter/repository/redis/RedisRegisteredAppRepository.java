package com.ziyao.harbor.usercenter.repository.redis;

import com.ziyao.harbor.data.redis.core.TimeToLive;
import com.ziyao.harbor.data.redis.repository.RedisHashRepository;
import com.ziyao.security.oauth2.core.RegisteredApp;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyao
 * @since 2024/06/08 21:51:02
 */
@RedisHash
@Repository
@TimeToLive(ttl = 90, unit = TimeUnit.DAYS)
public interface RedisRegisteredAppRepository extends RedisHashRepository<Long, RegisteredApp> {
}
