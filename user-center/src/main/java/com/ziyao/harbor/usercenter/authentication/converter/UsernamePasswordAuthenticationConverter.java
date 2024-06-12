package com.ziyao.harbor.usercenter.authentication.converter;

import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.authentication.core.Authentication;
import com.ziyao.harbor.usercenter.authentication.token.UsernamePasswordAuthenticationToken;
import com.ziyao.harbor.usercenter.request.AuthenticationRequest;
import com.ziyao.security.oauth2.core.AuthorizationGrantType;

/**
 * @author ziyao
 * @since 2024/06/12 09:48:48
 */
public class UsernamePasswordAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(AuthenticationRequest request) {

        if (!AuthorizationGrantType.PASSWORD.isMatches(request.getGrantType())) {
            return null;
        }
        // 获取用户输入的用户名和密码
        String username = Strings.hasText(request.getUsername()) ? request.getUsername() : Strings.EMPTY;
        String password = Strings.hasText(request.getPassword()) ? request.getPassword() : Strings.EMPTY;

        return UsernamePasswordAuthenticationToken.unauthenticated(username, password);
    }
}
