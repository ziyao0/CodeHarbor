package com.ziyao.harbor.gateway.rewrite;

import com.ziyao.harbor.gateway.filter.body.BodyExtractor;
import org.reactivestreams.Publisher;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author ziyao zhang
 * @since 2024/1/2
 */
public class EncodeRewriteFunction extends AbstractRewriteFunction {


    @Override
    public Publisher<byte[]> apply(ServerWebExchange serverWebExchange, byte[] bytes) {
        return BodyExtractor.extractRequestBody(serverWebExchange.getRequest());
    }
}
