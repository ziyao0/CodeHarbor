package com.ziyao.harbor.usercenter.repository.redis;

import com.ziyao.harbor.data.redis.core.Expired;
import com.ziyao.harbor.data.redis.core.RedisKey;
import com.ziyao.harbor.data.redis.repository.KeyValueRepository;
import com.ziyao.harbor.usercenter.entity.MenuTree;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Repository
@Expired(timeout = 7, unit = TimeUnit.DAYS)
@RedisKey(format = "ziyao:harbor:user_center:menu_tree:{}:{}")
public interface MenuTreeRepository extends KeyValueRepository<List<MenuTree>> {
}
