package com.ziyao.harbor.usercenter.response;

import lombok.Getter;

/**
 * @author ziyao zhang
 * @time 2024/6/15
 */
@Getter
public class OAuth2AuthorizationCodeResponse {

    private final String code;
    private final String state;

    protected OAuth2AuthorizationCodeResponse(String code, String state) {
        this.code = code;
        this.state = state;
    }

    public static OAuth2AuthorizationCodeResponse create(String code, String state) {
        return new OAuth2AuthorizationCodeResponse(code, state);
    }
}
