package com.ziyao.harbor.gateway.handler;

import com.ziyao.harbor.gateway.core.HeadersInjector;
import com.ziyao.harbor.gateway.core.SuccessfulHandler;
import com.ziyao.harbor.gateway.core.token.Authorization;
import com.ziyao.harbor.gateway.core.token.SuccessAuthorization;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Component
public class AccessSuccessfulHandler implements SuccessfulHandler<Authorization> {
    @Override
    public ServerWebExchange onSuccessful(ServerWebExchange exchange, Authorization authorization) {
        SuccessAuthorization success = (SuccessAuthorization) authorization;
        // 注入请求头
        HeadersInjector.injectHeaders(exchange, success);

        // TODO: 2023/9/4 判断是否需要刷新token，如果需要则刷新并写入cookie中
//        exchange.getResponse().getHeaders().add(Tokens.AUTHORIZATION,  );
        return exchange;
    }
}
