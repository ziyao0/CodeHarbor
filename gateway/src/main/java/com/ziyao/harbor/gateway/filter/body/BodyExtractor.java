package com.ziyao.harbor.gateway.filter.body;

import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Flux;

/**
 * @author ziyao zhang
 * @since 2024/1/10
 */
public abstract class BodyExtractor {


    public static Flux<byte[]> extractRequestBody(ServerHttpRequest request) {
        return request.getBody()
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    // 注意：这里简化了字符编码处理，请根据实际情况进行适配
                    return bytes;
                });
    }

}
