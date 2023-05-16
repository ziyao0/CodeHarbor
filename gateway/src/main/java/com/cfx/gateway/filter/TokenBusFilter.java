package com.cfx.gateway.filter;

import com.cfx.common.support.Tokens;
import com.cfx.gateway.common.config.GatewayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
@Component
public class TokenBusFilter implements GlobalFilter {


    @Autowired
    private GatewayConfig gatewayConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (StringUtils.hasLength(authorization) && authorization.startsWith("Bearer ")) {
            String jwtToken = authorization.substring(7);
            // 检查JWT Token是否有效
            Tokens.refresh(jwtToken, gatewayConfig.getOauth2Security(), false);
            exchange.getResponse().getHeaders().add("Authorization", "Bearer " + "66543452453");
        }

        return chain.filter(exchange);
    }
}
