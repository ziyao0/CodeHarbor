package com.ziyao.harbor.gateway.core;

import com.ziyao.harbor.core.error.StatusMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Slf4j
@Component
public class AccessFailureHandler implements FailureHandler {

    @Override
    public Mono<Void> onFailureResume(ServerWebExchange exchange, Throwable throwable) {
        StatusMessage statusMessage = null;
        // TODO: 2023/9/14 异常处理
        return DataBuffers.writeWith(exchange.getResponse(), statusMessage);

    }
}
