package com.ziyao.harbor.usercenter.authentication.converter;

import com.ziyao.harbor.usercenter.request.AuthenticationRequest;
import com.ziyao.security.oauth2.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author ziyao zhang
 * @time 2024/6/15
 */
public class DelegatingAuthenticationConverter implements AuthenticationConverter {


    private final List<AuthenticationConverter> authenticationConverters;

    public DelegatingAuthenticationConverter(List<AuthenticationConverter> authenticationConverters) {
        this.authenticationConverters = authenticationConverters;
    }

    @Override
    public Authentication convert(AuthenticationRequest request) {
        for (AuthenticationConverter authenticationConverter : this.authenticationConverters) {
            Authentication authentication = authenticationConverter.convert(request);
            if (authentication != null) {
                return authentication;
            }
        }
        return null;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        for (AuthenticationConverter authenticationConverter : this.authenticationConverters) {
            Authentication authentication = authenticationConverter.convert(request);
            if (authentication != null) {
                return authentication;
            }
        }
        return null;
    }
}
