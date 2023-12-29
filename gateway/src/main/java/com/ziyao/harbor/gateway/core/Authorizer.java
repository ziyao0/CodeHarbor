package com.ziyao.harbor.gateway.core;

import com.ziyao.harbor.core.Named;
import com.ziyao.harbor.gateway.core.token.AccessToken;
import com.ziyao.harbor.gateway.core.token.Authorization;
import reactor.core.publisher.Mono;

/**
 * @author ziyao zhang
 * @since 2023/5/16
 */
public interface Authorizer extends Named {

    /**
     * 授权处理
     *
     * @param accessToken {@link AccessToken}授权核心参数
     * @return 返回认证结果
     */
    Mono<Authorization> authorize(AccessToken accessToken);
}
