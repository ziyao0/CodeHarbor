package com.ziyao.harbor.usercenter.repository.redis;

import com.ziyao.harbor.data.redis.core.Expired;
import com.ziyao.harbor.data.redis.core.RedisKey;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyao
 * @since 2024/06/08 17:30:01
 */
@Repository
@Expired(timeout = 90, unit = TimeUnit.DAYS)
@RedisKey(format = "ziyao:harbor:user_center:oauth2:authorization")
public interface OAuth2AuthorizationRepository /*extends HashRepository<Long, OAuth2Authorization> */ {
}
