package com.ziyao.harbor.usercenter.authentication.token;

import com.ziyao.harbor.usercenter.authentication.provider.OAuth2AuthorizationCodeAuthenticationProvider;
import com.ziyao.security.oauth2.core.Authentication;
import com.ziyao.security.oauth2.core.AuthorizationGrantType;
import lombok.Getter;
import org.springframework.util.Assert;



/**
 * @author ziyao zhang
 * @time 2024/6/11
 * @see com.ziyao.harbor.usercenter.authentication.token.OAuth2AuthorizationGrantAuthenticationToken
 * @see OAuth2AuthorizationCodeAuthenticationProvider
 */
@Getter
public class OAuth2AuthorizationCodeAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    private static final long serialVersionUID = -4774441664615838808L;

    private final String code;

    private final String redirectUri;

    public OAuth2AuthorizationCodeAuthenticationToken(Authentication appPrincipal, String code, String redirectUri) {
        super(AuthorizationGrantType.AUTHORIZATION_CODE, appPrincipal);
        Assert.hasText(code, "code cannot be empty");
        this.code = code;
        this.redirectUri = redirectUri;
    }
}
