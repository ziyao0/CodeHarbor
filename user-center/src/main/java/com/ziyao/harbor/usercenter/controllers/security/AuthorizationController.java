package com.ziyao.harbor.usercenter.controllers.security;

import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.authentication.context.SecurityContextHolder;
import com.ziyao.harbor.usercenter.authentication.converter.AuthenticationConverter;
import com.ziyao.harbor.usercenter.authentication.core.Authentication;
import com.ziyao.harbor.usercenter.response.AccessTokenResponse;
import com.ziyao.harbor.usercenter.response.OAuth2AuthorizationCodeResponse;
import com.ziyao.harbor.usercenter.service.security.AuthorizationServer;
import com.ziyao.security.oauth2.token.OAuth2ParameterNames;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-13
 */
@RestController
public class AuthorizationController {

    private final AuthenticationConverter authenticationConverter;
    private final AuthorizationServer authorizationServer;

    public AuthorizationController(AuthenticationConverter authenticationConverter, AuthorizationServer authorizationServer) {
        this.authenticationConverter = authenticationConverter;
        this.authorizationServer = authorizationServer;
    }

    /**
     * 授权
     */
    @GetMapping("/authorize")
    public OAuth2AuthorizationCodeResponse authorize(HttpServletRequest request) {

        if (!SecurityContextHolder.isUnauthorized()) {
            // 如果未授权则跳转发到认证服务器
        }

        Long appId = Optional.ofNullable(request.getParameter(OAuth2ParameterNames.APP_ID)).map(Long::parseLong).orElse(null);
        String state = Optional.ofNullable(request.getParameter(OAuth2ParameterNames.STATE)).orElse(Strings.EMPTY);
        String grantType = Optional.ofNullable(request.getParameter(OAuth2ParameterNames.GRANT_TYPE)).orElse(Strings.EMPTY);

        return authorizationServer.authorize(appId, state, grantType);
    }

    @GetMapping("/token")
    public AccessTokenResponse token(HttpServletRequest request) {

        Authentication authentication = authenticationConverter.convert(request);

        return authorizationServer.token(authentication);
    }
}
