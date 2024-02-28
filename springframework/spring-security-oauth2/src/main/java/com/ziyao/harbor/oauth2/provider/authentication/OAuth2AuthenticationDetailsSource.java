package com.ziyao.harbor.oauth2.provider.authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ziyao zhang
 * @since 2024/2/27
 */
public class OAuth2AuthenticationDetailsSource implements
        AuthenticationDetailsSource<HttpServletRequest, OAuth2AuthenticationDetails> {

    public OAuth2AuthenticationDetails buildDetails(HttpServletRequest context) {
        return new OAuth2AuthenticationDetails(context);
    }

}
