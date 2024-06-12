package com.ziyao.harbor.usercenter.authentication.provider;

import com.ziyao.harbor.usercenter.authentication.converter.AuthenticationConverter;
import com.ziyao.harbor.usercenter.authentication.core.Authentication;
import com.ziyao.harbor.usercenter.request.AuthenticationRequest;

import java.util.List;

/**
 * @author ziyao
 * @since 2024/06/12 14:19:37
 */
public class DelegatingAuthenticationConverter implements AuthenticationConverter {


    private final List<AuthenticationConverter> converters;

    public DelegatingAuthenticationConverter(List<AuthenticationConverter> converters) {
        this.converters = converters;
    }

    @Override
    public Authentication convert(AuthenticationRequest request) {

        // 身份验证转化器
        for (AuthenticationConverter converter : this.converters) {
            Authentication authentication = converter.convert(request);
            if (authentication != null) {
                return authentication;
            }
        }
        return null;
    }
}
