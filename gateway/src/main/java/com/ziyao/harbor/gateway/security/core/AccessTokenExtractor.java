package com.ziyao.harbor.gateway.security.core;

import com.ziyao.harbor.core.Extractor;
import com.ziyao.harbor.gateway.security.api.AccessToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author zhangziyao
 * @since  2023/4/23
 */
public class AccessTokenExtractor implements Extractor<ServerHttpRequest, AccessToken> {

    @Override
    public AccessToken extract(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        return null;
    }


    private String getFirst(HttpHeaders headers, String key) {
        List<String> values = headers.get(key);
        if (!CollectionUtils.isEmpty(values)) {
            return values.get(0);
        }
        return null;
    }
}
