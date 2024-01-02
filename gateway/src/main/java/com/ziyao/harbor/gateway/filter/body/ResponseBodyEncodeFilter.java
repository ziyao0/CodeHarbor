package com.ziyao.harbor.gateway.filter.body;

import com.ziyao.harbor.gateway.filter.AbstractAfterAuthenticationFilter;
import com.ziyao.harbor.gateway.rewrite.EncodeRewriteFunction;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ziyao zhang
 * @since 2024/1/2
 */
public class ResponseBodyEncodeFilter extends AbstractAfterAuthenticationFilter {

    private final ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory;

    public ResponseBodyEncodeFilter(ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory) {
        this.modifyRequestBodyGatewayFilterFactory = modifyRequestBodyGatewayFilterFactory;
    }

    @Override
    protected Mono<Void> onSuccess(ServerWebExchange exchange, GatewayFilterChain chain) {
        EncodeRewriteFunction rewriteFunction = new EncodeRewriteFunction();
        ModifyRequestBodyGatewayFilterFactory.Config config = new ModifyRequestBodyGatewayFilterFactory.Config()
                .setRewriteFunction(rewriteFunction);
        return modifyRequestBodyGatewayFilterFactory.apply(config).filter(exchange, chain);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
