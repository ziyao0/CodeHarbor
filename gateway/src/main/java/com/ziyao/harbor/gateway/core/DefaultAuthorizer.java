package com.ziyao.harbor.gateway.core;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.ziyao.harbor.core.jwt.Jwts;
import com.ziyao.harbor.core.utils.SecurityUtils;
import com.ziyao.harbor.gateway.core.token.AccessToken;
import com.ziyao.harbor.gateway.core.token.Authorization;
import com.ziyao.harbor.gateway.core.token.FailureAuthorization;
import com.ziyao.harbor.gateway.core.token.SuccessAuthorization;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoOperator;

import java.util.Map;

/**
 * 默认授权方
 *
 * @author ziyao zhang
 * @since 2023/5/16
 */
@Component
public class DefaultAuthorizer implements Authorizer {

    @Override
    public Mono<Authorization> authorize(AccessToken accessToken) {

        String token = accessToken.getToken();
        try {
            Map<String, Claim> claims = Jwts.getClaims(token, SecurityUtils.loadJwtTokenSecret());
            return MonoOperator.just(new SuccessAuthorization(claims, token));
        } catch (JWTVerificationException e) {
            return MonoOperator.just(new FailureAuthorization(e.getMessage()));
        }
    }

    @Override
    public String getName() {
        return "accessControl";
    }

}
