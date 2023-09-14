package com.ziyao.harbor.gateway.core;

import com.ziyao.harbor.gateway.core.token.AccessToken;
import com.ziyao.harbor.gateway.core.token.Authorization;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

/**
 * @author ziyao zhang
 * @since 2023/5/16
 */
public interface ProviderManager {

    /**
     * 授权处理
     *
     * @param accessToken {@link AccessToken}访问令牌
     * @return 返回认证结果
     */
    Mono<Authorization> authorize(@NonNull AccessToken accessToken);
}
