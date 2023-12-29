package com.ziyao.harbor.gateway.core;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ziyao zhang
 * @since 2023/5/16
 */
@FunctionalInterface
public interface FailureHandler {

    /**
     * 失败时调用
     * <p>
     * {@link Throwable}异常信息
     * T 返回类型
     */
    Mono<Void> onFailureResume(ServerWebExchange exchange, Throwable throwable);

}
