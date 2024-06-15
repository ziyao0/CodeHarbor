package com.ziyao.harbor.usercenter.authentication.converter;

import com.ziyao.harbor.usercenter.authentication.core.Authentication;
import com.ziyao.harbor.usercenter.request.AuthenticationRequest;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author ziyao
 * @since 2024/06/12 09:47:11
 */
public interface AuthenticationConverter {


    Authentication convert(AuthenticationRequest request);

    Authentication convert(HttpServletRequest request);
}
