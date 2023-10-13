package com.ziyao.harbor.gateway.core.token;

import com.auth0.jwt.interfaces.Claim;
import lombok.Data;

import java.util.Map;

/**
 * @author ziyao zhang
 * @since 2023/5/16
 */
@Data
public class SuccessAuthorization implements Authorization {

    private static final long serialVersionUID = 1906018666555325676L;
    Map<String, Claim> claims;
    private String token;

    public SuccessAuthorization(String token) {
        this.token = token;
    }

    public SuccessAuthorization(Map<String, Claim> claims, String token) {
        this.claims = claims;
        this.token = token;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public boolean isAuthorized() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
