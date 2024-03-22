package com.ziyao.harbor.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
@Component
public class AccessPostFilter extends AbstractGlobalFilter {


    @Override
    protected Mono<Void> doFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // @formatter:off
        return chain.filter(exchange)
                .doFinally(signalType -> {

                    switch (signalType) {
                        case ON_ERROR:
                            // TODO: 2023/5/22 发送异常日志
                            break;
                        case ON_COMPLETE:
                            // TODO: 2023/5/22 成功时调用
                            break;
                        default:
                            // TODO: 2023/5/22 默认调用
                    }
                });
        // @formatter:on
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
