package com.ziyao.harbor.gateway.core;

import com.alibaba.fastjson.JSON;
import com.ziyao.harbor.core.error.IMessage;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoOperator;

import java.nio.charset.StandardCharsets;

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
    public static Mono<Void> writeWith(ServerHttpResponse response, int status, String message) {
        DataBuffer dataBuffer = response.bufferFactory().wrap(
                JSON.toJSONString(IMessage.getInstance(status, message)).getBytes(StandardCharsets.UTF_8));
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(MonoOperator.just(dataBuffer));
    }

    /**
     * 组装响应对象
     */
    public static Mono<Void> writeWith(ServerHttpResponse response, IMessage iMessage) {
        DataBuffer dataBuffer = response.bufferFactory().wrap(
                JSON.toJSONString(iMessage).getBytes(StandardCharsets.UTF_8));
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(MonoOperator.just(dataBuffer));
    }

    /**
     * 组装响应对象
     */
    public static Mono<Void> writeWith(ServerHttpResponse response, IMessage iMessage, HttpStatusCode statusCode) {
        response.setStatusCode(statusCode);
        DataBuffer dataBuffer = response.bufferFactory().wrap(
                JSON.toJSONString(iMessage).getBytes(StandardCharsets.UTF_8));
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(MonoOperator.just(dataBuffer));
    }    /**
     * 组装响应对象
     */
    public static Mono<Void> writeWith(ServerHttpResponse response, HttpStatusCode statusCode) {
        response.setStatusCode(statusCode);
        DataBuffer dataBuffer = response.bufferFactory().wrap(
                JSON.toJSONString(statusCode).getBytes(StandardCharsets.UTF_8));
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(MonoOperator.just(dataBuffer));
    }
}
