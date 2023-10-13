package com.ziyao.harbor.gateway.core.support;

import com.ziyao.harbor.core.error.StatusMessage;
import com.ziyao.harbor.core.utils.Strings;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoOperator;

import java.util.Objects;

/**
 * @author ziyao zhang
 * @since 2023/9/7
 */
public abstract class DataBuffers {

    private DataBuffers() {
    }


    /**
     * 组装响应对象
     */
    public static Mono<Void> writeWith(ServerWebExchange exchange, StatusMessage statusMessage) {
        return writeWith(exchange.getResponse(), statusMessage);
    }


    /**
     * 组装响应对象
     */
    public static Mono<Void> writeWith(ServerHttpResponse response, StatusMessage statusMessage) {
        return writeWith(response, statusMessage.getStatus(), statusMessage.getMessage());
    }

    /**
     * 组装响应对象
     */
    public static Mono<Void> writeWith(ServerHttpResponse response, int status, String message) {
        // 设置响应状态码
        HttpStatus httpStatus = HttpStatus.resolve(status);
        if (Objects.nonNull(httpStatus)) {
            response.setStatusCode(HttpStatus.valueOf(status));
        }
        // 填充响应体
        DataBuffer dataBuffer = response.bufferFactory()
                .wrap(Strings.toBytes(StatusMessage.getInstance(status, message)));
        // 填充响应类型
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(MonoOperator.just(dataBuffer));
    }

}
