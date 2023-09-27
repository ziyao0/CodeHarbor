package com.ziyao.harbor.gateway.filter;

import com.ziyao.harbor.core.Ordered;
import com.ziyao.harbor.core.StopWatch;
import com.ziyao.harbor.gateway.core.GatewayStopWatches;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 秒表信息打印
 *
 * @author ziyao zhang
 * @since 2023/9/27
 */
@Slf4j
@Component
public class StopWatchFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // @formatter:off
        return chain.filter(exchange).doFinally(signalType -> {
            StopWatch stopWatch = GatewayStopWatches.getStopWatch(exchange);
            if (Objects.nonNull(stopWatch)){
                log.debug(stopWatch.prettyPrint());
            }
        });
        // @formatter:on
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
