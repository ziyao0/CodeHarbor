package com.ziyao.harbor.gateway.service.impl;

import com.ziyao.harbor.gateway.service.SecurityService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Slf4j
@Service
public class SecurityServiceImpl implements SecurityService {

    @Resource
    private ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    @Override
    public void offline(String token) {
        reactiveStringRedisTemplate.delete(token).subscribe(
                size -> log.debug("退出成功！")
        );
    }
}
