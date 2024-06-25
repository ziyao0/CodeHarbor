package com.ziyao.harbor.gateway.core;

import com.ziyao.harbor.gateway.core.support.RequestAttributes;
import com.ziyao.harbor.gateway.core.token.AccessToken;
import com.ziyao.harbor.gateway.core.token.DefaultAccessToken;
import com.ziyao.harbor.gateway.support.IpUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
public abstract class AccessTokenExtractor {


    /**
     * 从请求头提取认证token
     *
     * @param exchange {@link ServerWebExchange}
     * @return 返回认证token
     * @see AccessToken
     */
    public static DefaultAccessToken extractForHeaders(ServerWebExchange exchange) {
        return doExtracted(exchange);
    }

    public static DefaultAccessToken doExtracted(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String token = headers.getFirst(RequestAttributes.AUTHORIZATION);

        // @formatter:off
        return DefaultAccessToken.builder()
                .token(token)
                .refreshToken(headers.getFirst(RequestAttributes.REFRESH_TOKEN))
                .timestamp(headers.getFirst(RequestAttributes.TIMESTAMP))
                .digest(RequestAttributes.DIGEST)
                .resource(RequestAttributes.RESOURCE)
                // TODO: 2023/10/13  
                .api(exchange.getAttributes().get("接口地址").toString())
                .ip(IpUtils.getIP(exchange))
                .name("accessControl")
                .build();
        // @formatter:on
    }

    private AccessTokenExtractor() {
    }
}
