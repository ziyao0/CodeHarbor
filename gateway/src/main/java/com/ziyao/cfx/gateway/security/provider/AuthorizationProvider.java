package com.ziyao.cfx.gateway.security.provider;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.ziyao.cfx.common.token.Tokens;
import com.ziyao.cfx.common.utils.SecurityUtils;
import com.ziyao.cfx.gateway.security.api.Authorization;
import com.ziyao.cfx.gateway.security.api.Provider;
import com.ziyao.cfx.gateway.security.core.FailureAuthorization;
import com.ziyao.cfx.gateway.security.core.SuccessAuthorization;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ziyao zhang
 * @since 2023/5/16
 */
@Component
public class AuthorizationProvider implements Provider {

    @Override
    public Authorization authorize(Authorization authorization) {

        String token = authorization.getToken();
        try {
            Map<String, Claim> claims = Tokens.getClaims(token, SecurityUtils.loadJwtTokenSecret());
            return new SuccessAuthorization(claims, token);
        } catch (JWTVerificationException e) {
            return new FailureAuthorization(e.getMessage());
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
