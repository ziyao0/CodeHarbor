package com.ziyao.harbor.usercenter.authentication.converter;

import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.authentication.token.UsernamePasswordAuthenticationToken;
import com.ziyao.harbor.usercenter.request.AuthenticationRequest;
import com.ziyao.security.oauth2.core.Authentication;
import com.ziyao.security.oauth2.core.AuthorizationGrantType;
import com.ziyao.security.oauth2.token.OAuth2ParameterNames;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

/**
 * @author ziyao
 * @since 2024/06/12 09:48:48
 */
public class UsernamePasswordAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(AuthenticationRequest request) {

        if (!AuthorizationGrantType.PASSWORD.matches(request.getGrantType())) {
            return null;
        }
        // 获取用户输入的用户名和密码
        String username = Optional.ofNullable(request.getUsername()).orElse(Strings.EMPTY);
        String password = Optional.ofNullable(request.getPassword()).orElse(Strings.EMPTY);

        return UsernamePasswordAuthenticationToken.unauthenticated(username, password);
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        if (!AuthorizationGrantType.PASSWORD.matches(request.getParameter(OAuth2ParameterNames.PASSWORD))) {
            return null;
        }

        // 获取用户输入的用户名和密码
        String username = Optional.ofNullable(request.getParameter(OAuth2ParameterNames.USERNAME)).orElse(Strings.EMPTY);
        String password = Optional.ofNullable(request.getParameter(OAuth2ParameterNames.PASSWORD)).orElse(Strings.EMPTY);

        return UsernamePasswordAuthenticationToken.unauthenticated(username, password);

    }
}
