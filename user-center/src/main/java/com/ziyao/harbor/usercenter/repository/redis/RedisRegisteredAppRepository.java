package com.ziyao.harbor.usercenter.repository.redis;

import com.ziyao.harbor.data.redis.core.Expired;
import com.ziyao.harbor.data.redis.repository.HashRepository;
import com.ziyao.security.oauth2.core.RegisteredApp;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyao
 * @since 2024/06/08 21:51:02
 */
@Repository
@Expired(timeout = 90, unit = TimeUnit.DAYS)
public interface RedisRegisteredAppRepository extends HashRepository<Long, RegisteredApp> {
}
