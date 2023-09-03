package com.ziyao.harbor.gateway.core;

import com.ziyao.harbor.core.Extractor;
import com.ziyao.harbor.core.token.TokenType;
import com.ziyao.harbor.gateway.core.token.AccessToken;
import com.ziyao.harbor.gateway.core.token.DefaultAccessToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
public abstract class AccessTokenExtractor implements Extractor<ServerHttpRequest, AccessToken> {


    private static final AccessTokenExtractor EXTRACTOR;

    static {
        EXTRACTOR = new AccessTokenExtractor() {
            @Override
            public AccessToken extract(ServerHttpRequest request) {
                return super.extract(request);
            }
        };
    }

    @Override
    public AccessToken extract(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String Token = headers.getFirst(RequestAttributes.AUTHORIZATION);

        // @formatter:off
        return DefaultAccessToken.builder()
                .token(TokenType.extract(Token))
                .refreshToken(headers.getFirst(RequestAttributes.REFRESH_TOKEN))
                .timestamp(headers.getFirst(RequestAttributes.TIMESTAMP))
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
    public static AccessToken extractForHeaders(ServerWebExchange exchange) {
        return EXTRACTOR.extract(exchange.getRequest());
    }
}
