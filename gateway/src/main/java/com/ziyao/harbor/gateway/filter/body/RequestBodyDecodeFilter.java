package com.ziyao.harbor.gateway.filter.body;

import com.ziyao.harbor.gateway.filter.AbstractAfterAuthenticationFilter;
import com.ziyao.harbor.gateway.rewrite.DecodeRewriteFunction;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ziyao zhang
 * @since 2024/1/2
 */
@Component
public class RequestBodyDecodeFilter extends AbstractAfterAuthenticationFilter {


    private final ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory;

    public RequestBodyDecodeFilter(ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory) {
        this.modifyRequestBodyGatewayFilterFactory = modifyRequestBodyGatewayFilterFactory;
    }

    @Override
    protected Mono<Void> onSuccess(ServerWebExchange exchange, GatewayFilterChain chain) {
        DecodeRewriteFunction rewriteFunction = new DecodeRewriteFunction();
        ModifyRequestBodyGatewayFilterFactory.Config config = new ModifyRequestBodyGatewayFilterFactory.Config()
                .setRewriteFunction(rewriteFunction);
        return modifyRequestBodyGatewayFilterFactory.apply(config).filter(exchange, chain);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
