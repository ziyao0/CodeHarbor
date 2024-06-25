package com.ziyao.harbor.web;

import com.google.common.collect.Lists;
import com.ziyao.harbor.core.error.StatusMessage;
import com.ziyao.harbor.web.context.ContextInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;
import java.util.Map;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
@RestControllerAdvice
@ConditionalOnClass({ContextInfo.class})
public class GlobalResponseHandlerAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(@Nullable MethodParameter returnType, @Nullable Class converterType) {
        if (returnType != null) {
            Class<?> type = returnType.getParameterType();
            if (Exception.class.isAssignableFrom(type)) {
                return false;
            }
            return Object.class.isAssignableFrom(type);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object beforeBodyWrite(Object body, @Nullable MethodParameter parameter, @Nullable MediaType mediaType,
                                  @Nullable Class<? extends HttpMessageConverter<?>> converter, @Nullable ServerHttpRequest request, @Nullable ServerHttpResponse response) {
        if (body == null) {
            return ResponseBuilder.ok(null);
        }
        if (body instanceof StatusMessage) {
            return body;
        }
        // 如果 body 是 Map 类型并且包含异常信息的字段
        if (body instanceof Map) {
            Map map = (Map) body;
            if (map.keySet().containsAll(paramNames)) {
                return ResponseBuilder.of(
                        Integer.parseInt(String.valueOf(map.get("status"))),
                        map.get("error").toString(),
                        map.get("path"));
            }
        }


        return ResponseBuilder.ok(body);

    }

    private static final List<String> paramNames = Lists.newArrayList("timestamp", "status", "error", "path");
}
