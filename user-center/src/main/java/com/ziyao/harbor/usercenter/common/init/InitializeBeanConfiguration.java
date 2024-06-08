package com.ziyao.harbor.usercenter.common.init;

import com.ziyao.harbor.usercenter.authenticate.token.generator.*;
import com.ziyao.security.oauth2.core.OAuth2Token;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ziyao zhang
 * @since 2023/4/26
 */
@Configuration
public class InitializeBeanConfiguration {


    @Bean
    public OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator() {
        return new DelegatingOAuth2TokenGenerator(
                new OAuth2AuthorizationCodeGenerator(),
                new OAuth2AccessTokenGenerator(),
                new OAuth2RefreshTokenGenerator()
        );
    }

}
