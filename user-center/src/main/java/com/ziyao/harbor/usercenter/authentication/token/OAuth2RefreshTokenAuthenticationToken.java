package com.ziyao.harbor.usercenter.authentication.token;

import com.ziyao.harbor.usercenter.authentication.core.Authentication;
import com.ziyao.security.oauth2.core.AuthorizationGrantType;
import lombok.Getter;

import java.io.Serial;
import java.util.Set;

/**
 * @author ziyao
 * @since 2024/06/12 10:33:41
 */
@Getter
public class OAuth2RefreshTokenAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    @Serial
    private static final long serialVersionUID = 2954034450231107021L;

    private final String refreshToken;

    private final Set<String> scopes;

    public OAuth2RefreshTokenAuthenticationToken(String refreshToken, Authentication appPrincipal, Set<String> scopes) {
        super(AuthorizationGrantType.REFRESH_TOKEN, appPrincipal);
        this.refreshToken = refreshToken;
        this.scopes = scopes;
    }
}
