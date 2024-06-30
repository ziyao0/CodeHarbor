package com.ziyao.harbor.usercenter.repository.redis;

import com.ziyao.harbor.data.redis.repository.RedisHashRepository;
import com.ziyao.security.oauth2.core.OAuth2Authorization;
import org.springframework.stereotype.Repository;

/**
 * @author ziyao
 * @since 2024/06/08 17:30:01
 */
@Repository
public interface OAuth2AuthorizationRepositoryRedis extends RedisHashRepository<Long, OAuth2Authorization> {
}
