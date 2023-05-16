package com.cfx.gateway.security.provider;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.cfx.common.support.Tokens;
import com.cfx.gateway.common.config.GatewayConfig;
import com.cfx.gateway.security.api.Authorization;
import com.cfx.gateway.security.api.Provider;
import com.cfx.gateway.security.core.FailureAuthorization;
import com.cfx.gateway.security.core.SuccessAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Eason
 * @since 2023/5/16
 */
@Component
public class AuthorizationProvider implements Provider {

    @Autowired
    private GatewayConfig gatewayConfig;

    @Override
    public Authorization authorize(Authorization authorization) {

        String token = authorization.getToken();
        try {
            Map<String, Claim> claims = Tokens.getClaims(token, gatewayConfig.getOauth2Security());
            // 获取token数据
            Long appid = claims.get(Tokens.APP_ID).asLong();
            Long userId = claims.get(Tokens.USER_ID).asLong();
            String username = claims.get(Tokens.USERNAME).asString();
            return new SuccessAuthorization(appid, userId, username, token);
        } catch (JWTVerificationException e) {
            return new FailureAuthorization(e.getMessage());
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
