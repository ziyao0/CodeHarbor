package com.ziyao.harbor.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @author ziyao
 * @since 2024/05/28 10:59:33
 */
@Component
public class RedirectFilter extends AbstractGlobalFilter {


    @Override
    protected Mono<Void> doFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 判断是否需要重定向
        exchange.getResponse().setStatusCode(HttpStatus.FOUND);
        exchange.getResponse().getHeaders().setLocation(URI.create(extractBaseUrl(exchange)));
        return exchange.getResponse().setComplete();
    }

    /**
     * 获取重定向路径
     */
    private String extractBaseUrl(ServerWebExchange exchange) {
        String scheme = exchange.getRequest().getURI().getScheme();
        String host = exchange.getRequest().getURI().getHost();
        int port = exchange.getRequest().getURI().getPort();

        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append(scheme).append("://").append(host);
        if (port != -1 && port != 80 && port != 443) {
            baseUrl.append(":").append(port);
        }
        return baseUrl.toString();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
