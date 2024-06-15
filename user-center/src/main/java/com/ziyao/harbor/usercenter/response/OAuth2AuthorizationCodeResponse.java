package com.ziyao.harbor.usercenter.response;

/**
 * @author ziyao zhang
 * @time 2024/6/15
 */
public record OAuth2AuthorizationCodeResponse(String code, String state) {

    public static OAuth2AuthorizationCodeResponse create(String code, String state) {
        return new OAuth2AuthorizationCodeResponse(code, state);
    }
}
