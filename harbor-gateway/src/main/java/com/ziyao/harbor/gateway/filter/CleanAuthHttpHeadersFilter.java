package com.ziyao.harbor.gateway.filter;

import com.ziyao.harbor.gateway.core.support.RequestAttributes;
import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author ziyao zhang
 * @since 2023/9/26
 */
public class CleanAuthHttpHeadersFilter implements HttpHeadersFilter, Ordered {
    @Override
    public HttpHeaders filter(HttpHeaders input, ServerWebExchange exchange) {

        HttpHeaders httpHeaders = new HttpHeaders();

        input.entrySet().stream()
                .filter(entry -> !RequestAttributes.AUTHORIZATION_HEADERS.contains(entry.getKey()))
                .forEach(entry -> httpHeaders.addAll(entry.getKey(), entry.getValue()));

        return httpHeaders;
    }

    @Override
    public boolean supports(Type type) {
        return HttpHeadersFilter.super.supports(type);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
