package com.ziyao.harbor.usercenter.repository.redis;

import com.ziyao.harbor.data.redis.core.TimeToLive;
import com.ziyao.harbor.data.redis.repository.RedisValueRepository;
import com.ziyao.harbor.usercenter.entity.MenuTree;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Repository
@TimeToLive(ttl = 7, unit = TimeUnit.DAYS)
public interface MenuTreeRepository extends RedisValueRepository<List<MenuTree>> {
}
