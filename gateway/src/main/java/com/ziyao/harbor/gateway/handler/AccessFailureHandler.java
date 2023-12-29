package com.ziyao.harbor.gateway.handler;

import com.ziyao.harbor.core.error.StatusMessage;
import com.ziyao.harbor.gateway.core.FailureHandler;
import com.ziyao.harbor.gateway.core.support.DataBuffers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serial;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Slf4j
@Component
public class AccessFailureHandler implements FailureHandler {

    @Override
    public Mono<Void> onFailureResume(ServerWebExchange exchange, Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
        StatusMessage statusMessage = new StatusMessage() {
            @Serial
            private static final long serialVersionUID = -2545416176368395645L;

            @Override
            public Integer getStatus() {
                return 403;
            }

            @Override
            public String getMessage() {
                return "越权访问拦截";
            }
        };
        // TODO: 2023/9/14 异常处理
        return DataBuffers.writeWith(exchange.getResponse(), statusMessage);

    }
}
