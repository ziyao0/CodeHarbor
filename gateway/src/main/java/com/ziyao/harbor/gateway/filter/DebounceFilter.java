package com.ziyao.harbor.gateway.filter;

import com.ziyao.harbor.crypto.digest.DigestUtils;
import com.ziyao.harbor.gateway.config.GatewayConfig;
import com.ziyao.harbor.gateway.support.RedisKeys;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao
 * @since 2024/05/28 09:37:54
 */
@Component
public class DebounceFilter extends AbstractAfterAuthenticationFilter {


    private static final String DEBOUNCE_VALUE = "1";

    private final ReactiveStringRedisTemplate operations;
    private final GatewayConfig gatewayConfig;

    public DebounceFilter(ReactiveStringRedisTemplate reactiveStringRedisTemplate, GatewayConfig gatewayConfig) {
        this.operations = reactiveStringRedisTemplate;
        this.gatewayConfig = gatewayConfig;
    }

    @Override
    protected Mono<Void> onSuccess(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 判断是否开启防抖动功能
        if (!gatewayConfig.isEnableDebounced()) {
            return chain.filter(exchange);
        }

        return operations.opsForValue()
                .setIfAbsent(
                        RedisKeys.getDebounceKeyByValue(md5Hex(exchange)),
                        DEBOUNCE_VALUE,
                        Duration.ofMillis(gatewayConfig.getDebounceTimes()))
                .flatMap(res -> {
                    if (Boolean.TRUE.equals(res)) {
                        return chain.filter(exchange);
                    } else {
                        // 防抖动异常返回，可以自定义
                        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                        return exchange.getResponse().setComplete();
                    }
                });
    }

    /**
     * 对接口和请求参数进行md5
     *
     * @param exchange 服务请求上下文信息
     * @return 返回md5后的请求结果
     */
    private String md5Hex(ServerWebExchange exchange) {
        //是不是应该还需要解析出当前用户信息，并把用户唯一标识作为key的一部分
        String path = exchange.getRequest().getURI().getPath();
        String queryParams = exchange.getRequest().getQueryParams().toString();
        return DigestUtils.md5Hex(path + "?" + queryParams);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
