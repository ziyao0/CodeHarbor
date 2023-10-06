package com.ziyao.harbor.gateway.filter;

import com.ziyao.harbor.core.error.StatusMessage;
import com.ziyao.harbor.gateway.core.AccessTokenExtractor;
import com.ziyao.harbor.gateway.core.support.DataBuffers;
import com.ziyao.harbor.gateway.core.token.DefaultAccessToken;
import com.ziyao.harbor.gateway.factory.AccessChainFactory;
import jakarta.annotation.Resource;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoOperator;
import reactor.core.scheduler.Schedulers;

/**
 * 前置访问控制过滤器
 *
 * @author zhangziyao
 * @since 2023/4/23
 */
@Component
public class AccessPreFilter extends AbstractGlobalFilter {

    @Resource
    private AccessChainFactory accessChainFactory;

    protected AccessPreFilter() {
        super(AccessPreFilter.class.getSimpleName());
    }

    @Override
    protected Mono<Void> doFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 2023/9/9 从请求头提取请求路径，请求ip等相关信息，进行前置校验   快速失败
        DefaultAccessToken defaultAccessToken = AccessTokenExtractor.extractForHeaders(exchange);
        return MonoOperator.just(defaultAccessToken)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(access -> {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    accessChainFactory.filter(access);
                    return chain.filter(exchange);
                    // TODO: 2023/9/24 403 禁止访问
                })
                .onErrorResume(t -> DataBuffers.writeWith(exchange, StatusMessage.getInstance(403, "禁止访问")));
    }


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
