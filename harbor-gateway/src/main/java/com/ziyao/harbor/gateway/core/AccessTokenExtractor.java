package com.ziyao.harbor.gateway.core;

import com.ziyao.harbor.core.Extractor;
import com.ziyao.harbor.core.token.TokenType;
import com.ziyao.harbor.gateway.core.token.AccessControl;
import com.ziyao.harbor.gateway.core.token.AccessToken;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
public abstract class AccessTokenExtractor implements Extractor<ServerWebExchange, AccessControl> {


    private static final AccessTokenExtractor EXTRACTOR;

    static {
        EXTRACTOR = new AccessTokenExtractor() {
            @Override
            public AccessControl extract(ServerWebExchange request) {
                return super.extract(request);
            }
        };
    }

    @Override
    public AccessControl extract(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String Token = headers.getFirst(RequestAttributes.AUTHORIZATION);

        // @formatter:off
        return AccessControl.builder()
                .token(TokenType.extract(Token))
                .refreshToken(headers.getFirst(RequestAttributes.REFRESH_TOKEN))
                .timestamp(headers.getFirst(RequestAttributes.TIMESTAMP))
                .digest(RequestAttributes.DIGEST)
                .resource(RequestAttributes.RESOURCE)
                .api(exchange.getAttributes().get(ServerWebExchangeUtils.GATEWAY_PREDICATE_PATH_CONTAINER_ATTR).toString())
                .build();
        // @formatter:on
    }

    /**
     * 从请求头提取认证token
     *
     * @param exchange {@link ServerWebExchange}
     * @return 返回认证token
     * @see AccessToken
     */
    public static AccessControl extractForHeaders(ServerWebExchange exchange) {
        return EXTRACTOR.extract(exchange);
    }
}
