package com.ziyao.harbor.gateway.core.support;

import com.google.common.collect.ImmutableSet;
import com.ziyao.harbor.gateway.support.ParameterNames;
import com.ziyao.harbor.gateway.core.AuthorizerContext;
import org.springframework.web.server.ServerWebExchange;

import java.util.Set;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public abstract class RequestAttributes {

    private RequestAttributes() {
    }

    public static final String AUTHORIZATION = ParameterNames.AUTHORIZATION;
    public static final String TIMESTAMP = ParameterNames.TIMESTAMP;
    public static final String REFRESH_TOKEN = ParameterNames.REFRESH_TOKEN;
    public static final String RESOURCE = ParameterNames.RESOURCE;
    public static final String DIGEST = ParameterNames.DIGEST;
    public static final String AUTHORIZER_CONTEXT = "authorizer_context";

    /**
     * 认证相关请求头key
     */
    public static final Set<String> AUTHORIZATION_HEADERS =
            Set.of(AUTHORIZATION, TIMESTAMP, REFRESH_TOKEN, DIGEST);

    /**
     * 判断是否已经通过认证
     *
     * @param exchange HTTP 请求-响应交互的协定。提供对 HTTP 请求和响应的访问，并公开其他与服务器端处理相关的属性和功能，例如请求属性。
     * @return <code>true</code> 已经通过认证
     */
    public static boolean isAuthenticated(ServerWebExchange exchange) {
        return loadAuthorizerContext(exchange) != null;
    }

    /**
     * 获取认证上下文
     *
     * @param exchange exchange
     * @return {@link AuthorizerContext} 认证上下文
     */
    public static AuthorizerContext loadAuthorizerContext(ServerWebExchange exchange) {
        return exchange.getAttribute(AUTHORIZER_CONTEXT);
    }

    /**
     * 存储认证上下文信息
     *
     * @param exchange          exchange
     * @param authorizerContext {@link AuthorizerContext} 认证上下文
     */
    public static void storeAuthorizerContext(ServerWebExchange exchange, AuthorizerContext authorizerContext) {
        exchange.getAttributes().put(AUTHORIZER_CONTEXT, authorizerContext);
    }
}
