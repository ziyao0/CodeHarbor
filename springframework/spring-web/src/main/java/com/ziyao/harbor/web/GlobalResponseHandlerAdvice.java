package com.ziyao.harbor.web;

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

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
@RestControllerAdvice
@ConditionalOnClass({ContextInfo.class})
public class GlobalResponseHandlerAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(@Nullable MethodParameter returnType, @Nullable Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, @Nullable MethodParameter parameter, @Nullable MediaType mediaType,
                                  @Nullable Class<? extends HttpMessageConverter<?>> converter, @Nullable ServerHttpRequest request, @Nullable ServerHttpResponse response) {
        if (body instanceof StatusMessage)
            return body;

        return ResponseBuilder.ok(body);
    }
}
