package com.ziyao.harbor.gateway.rewrite;

import org.reactivestreams.Publisher;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author ziyao zhang
 * @since 2024/1/2
 */
public class DecodeRewriteFunction extends AbstractRewriteFunction {
    @Override
    public Publisher<byte[]> apply(ServerWebExchange serverWebExchange, byte[] bytes) {
        return null;
    }
}
