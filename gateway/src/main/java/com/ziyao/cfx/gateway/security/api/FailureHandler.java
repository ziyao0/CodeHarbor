package com.ziyao.cfx.gateway.security.api;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author ziyao zhang
 * @since 2023/5/16
 */
public interface FailureHandler {

    /**
     * 失败时调用
     * <p>
     * {@link Throwable}              异常信息
     * T 返回类型
     */
    DataBuffer onFailureResume(ServerWebExchange exchange, Throwable throwable);

}
