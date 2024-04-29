package com.ziyao.harbor.gateway.core;

import com.auth0.jwt.interfaces.Claim;
import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.gateway.core.token.SuccessAuthorization;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public abstract class HeadersInjector {

    public static void inject(ServerWebExchange exchange, SuccessAuthorization authorization) {
        Assert.notNull(exchange, "缺少注入对象(ServerWebExchange)");
        Assert.notNull(authorization, "缺少要注入的信息(Authorization)");
        exchange.getRequest().mutate()
                .headers(httpHeaders -> {
                    MultiValueMap<String, String> headers = new HttpHeaders();
                    for (Map.Entry<String, Claim> entry : authorization.getClaims().entrySet()) {
                        String value = Strings.encodeURLUTF8(entry.getValue().as(Object.class).toString());
                        headers.add(entry.getKey(), value);
                    }
                    httpHeaders.addAll(headers);
                })
                .build();
    }

    private HeadersInjector() {
    }
}
