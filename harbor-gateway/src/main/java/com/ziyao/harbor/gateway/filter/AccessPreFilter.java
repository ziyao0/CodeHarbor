package com.ziyao.harbor.gateway.filter;

import com.ziyao.harbor.core.error.Errors;
import com.ziyao.harbor.gateway.core.AccessTokenExtractor;
import com.ziyao.harbor.gateway.core.DataBuffers;
import com.ziyao.harbor.gateway.core.factory.AccessChainFactory;
import com.ziyao.harbor.gateway.core.token.AccessControl;
import jakarta.annotation.Resource;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoOperator;

/**
 * 前置访问控制过滤器
 *
 * @author zhangziyao
 * @since 2023/4/23
 */
@Component
@Order(Integer.MIN_VALUE)
public class AccessPreFilter implements GlobalFilter {

    @Resource
    private AccessChainFactory accessChainFactory;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 2023/9/9 从请求头提取请求路径，请求ip等相关信息，进行前置校验   快速失败
        AccessControl accessControl = AccessTokenExtractor.extractForHeaders(exchange);
        return MonoOperator.just(accessControl)
                .flatMap(access -> {
                    accessChainFactory.filter(access);
                    return chain.filter(exchange);
                }).onErrorResume(t -> DataBuffers.writeWith(exchange, Errors.FORBIDDEN));
    }


}
