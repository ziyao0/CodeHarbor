package com.ziyao.cfx.gateway.support;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.net.InetSocketAddress;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
public abstract class IPUtils {


    private static final String X_FORWARDED_FOR = "X-Forwarded-For";
    private static final String X_REAL_IP = "X-Real-IP";
    private static final String UNKNOWN = "unknown";

    public static String getIP(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();

        String ip = headers.getFirst(X_FORWARDED_FOR);

        if (StringUtils.hasLength(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = headers.getFirst(X_REAL_IP);
        if (StringUtils.hasLength(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        if (ObjectUtils.isEmpty(remoteAddress)) {
            return UNKNOWN;
        }
        return remoteAddress.getAddress().getHostAddress();
    }
}
