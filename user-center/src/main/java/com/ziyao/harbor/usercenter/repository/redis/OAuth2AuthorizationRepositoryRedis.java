package com.ziyao.harbor.usercenter.repository.redis;

import com.ziyao.harbor.data.redis.core.TimeToLive;
import com.ziyao.harbor.data.redis.repository.RedisHashRepository;
import com.ziyao.security.oauth2.core.OAuth2Authorization;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyao
 * @since 2024/06/08 17:30:01
 */
@Repository
@TimeToLive(ttl = 90, unit = TimeUnit.DAYS)
public interface OAuth2AuthorizationRepositoryRedis extends RedisHashRepository<Long, OAuth2Authorization> {
}
