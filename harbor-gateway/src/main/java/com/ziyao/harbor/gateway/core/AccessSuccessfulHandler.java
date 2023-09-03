package com.ziyao.harbor.gateway.core;

import com.ziyao.harbor.core.token.Tokens;
import com.ziyao.harbor.core.utils.SecurityUtils;
import com.ziyao.harbor.gateway.core.token.SuccessAuthorization;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Component
public class AccessSuccessfulHandler implements SuccessfulHandler<SuccessAuthorization> {
    @Override
    public void onSuccessful(ServerWebExchange exchange, SuccessAuthorization information) {
        // 注入请求头
        HeadersInjector.injectHeaders(exchange, information);
        String refreshToken = Tokens.refresh(information.getToken(), SecurityUtils.loadJwtTokenSecret(), false);
        exchange.getResponse().getHeaders().add(Tokens.AUTHORIZATION, Tokens.getBearerToken(refreshToken));
    }
}
