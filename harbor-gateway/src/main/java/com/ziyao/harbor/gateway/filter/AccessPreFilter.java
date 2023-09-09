package com.ziyao.harbor.gateway.filter;

import com.ziyao.harbor.core.error.Errors;
import com.ziyao.harbor.gateway.core.AccessTokenExtractor;
import com.ziyao.harbor.gateway.core.DataBuffers;
import com.ziyao.harbor.gateway.core.factory.AccessChainFactory;
import com.ziyao.harbor.gateway.core.token.AccessControl;
import jakarta.annotation.Resource;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
@Component
@Order(Integer.MIN_VALUE)
public class AccessPreFilter implements GlobalFilter {

    @Resource
    private AccessChainFactory accessChainFactory;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //处理请求api
        // TODO: 2023/9/9 从请求头提取请求路径，请求ip等相关信息，进行前置校验   快速失败
        // 从请求头提取认证token
        AccessControl accessControl = AccessTokenExtractor.extractForHeaders(exchange);

        try {
            accessChainFactory.handle(accessControl);
        } catch (Exception e) {
            return DataBuffers.writeWith(exchange.getResponse(), Errors.FORBIDDEN, HttpStatus.FORBIDDEN);
        }

        return chain.filter(exchange);


    }


}
