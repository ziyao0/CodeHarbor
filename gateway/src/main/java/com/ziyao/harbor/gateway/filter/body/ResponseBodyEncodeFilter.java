package com.ziyao.harbor.gateway.filter.body;

import com.ziyao.harbor.gateway.filter.AbstractAfterAuthenticationFilter;
import com.ziyao.harbor.gateway.rewrite.EncodeRewriteFunction;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ziyao zhang
 * @since 2024/1/2
 */
public class ResponseBodyEncodeFilter extends AbstractAfterAuthenticationFilter {

    private final ModifyResponseBodyGatewayFilterFactory modifyResponseBodyGatewayFilterFactory;

    public ResponseBodyEncodeFilter(ModifyResponseBodyGatewayFilterFactory modifyResponseBodyGatewayFilterFactory) {
        this.modifyResponseBodyGatewayFilterFactory = modifyResponseBodyGatewayFilterFactory;
    }


    @Override
    protected Mono<Void> onSuccess(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        EncodeRewriteFunction rewriteFunction = new EncodeRewriteFunction();
        ModifyResponseBodyGatewayFilterFactory.Config config = new ModifyResponseBodyGatewayFilterFactory.Config()
                .setRewriteFunction(byte[].class, byte[].class, rewriteFunction);
        return modifyResponseBodyGatewayFilterFactory
                .apply(config)
                .filter(exchange, chain);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
