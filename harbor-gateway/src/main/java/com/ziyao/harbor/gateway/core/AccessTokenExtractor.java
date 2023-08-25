package com.ziyao.harbor.gateway.core;

import com.ziyao.harbor.core.Extractor;
import com.ziyao.harbor.core.token.TokenType;
import com.ziyao.harbor.gateway.core.token.AccessToken;
import com.ziyao.harbor.gateway.core.token.DefaultAccessToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
public class AccessTokenExtractor implements Extractor<ServerHttpRequest, AccessToken> {

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
}
