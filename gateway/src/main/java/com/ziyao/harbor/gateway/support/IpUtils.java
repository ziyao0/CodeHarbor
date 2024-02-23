package com.ziyao.harbor.gateway.support;

import com.ziyao.harbor.core.utils.Strings;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;

import java.net.InetSocketAddress;
import java.net.URL;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
public abstract class IpUtils {

    private static final String IP2REGION_PATH = "META-INF/ip2region.xdb";
    private static final String X_FORWARDED_FOR = "X-Forwarded-For";
    private static final String X_REAL_IP = "X-Real-IP";
    private static final String UNKNOWN = "unknown";

    public static String getIP(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();

        String ip = headers.getFirst(X_FORWARDED_FOR);

        if (Strings.hasLength(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = headers.getFirst(X_REAL_IP);
        if (Strings.hasLength(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        if (ObjectUtils.isEmpty(remoteAddress)) {
            return UNKNOWN;
        }
        return remoteAddress.getAddress().getHostAddress();
    }

    public static String getAddr(String ip) {
        try {
            URL resource = IpUtils.class.getClassLoader().getResource(IP2REGION_PATH);
            String path = null;
            if (resource != null) {
                path = resource.getPath();
            }
            byte[] bytes = Searcher.loadContentFromFile(path);
            Searcher searcher = Searcher.newWithBuffer(bytes);
            return searcher.search(ip);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(getAddr("61.135.215.24"));
    }
}
