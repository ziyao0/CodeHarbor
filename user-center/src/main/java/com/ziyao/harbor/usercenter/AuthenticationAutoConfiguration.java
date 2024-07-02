package com.ziyao.harbor.usercenter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ziyao.harbor.usercenter.authentication.AuthenticationManager;
import com.ziyao.harbor.usercenter.authentication.PrimaryAuthenticationManager;
import com.ziyao.harbor.usercenter.authentication.converter.*;
import com.ziyao.harbor.usercenter.authentication.provider.AuthenticationProvider;
import com.ziyao.harbor.usercenter.authentication.token.*;
import com.ziyao.harbor.usercenter.repository.jpa.ApplicationRepository;
import com.ziyao.harbor.usercenter.repository.jpa.AuthorizationRepository;
import com.ziyao.harbor.usercenter.repository.redis.OAuth2AuthorizationRepositoryRedis;
import com.ziyao.harbor.usercenter.repository.redis.RegisteredAppRepositoryRedis;
import com.ziyao.harbor.usercenter.service.app.*;
import com.ziyao.harbor.usercenter.service.oauth2.*;
import com.ziyao.harbor.web.ApplicationContextUtils;
import com.ziyao.security.oauth2.core.OAuth2Token;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
@Configuration
public class AuthenticationAutoConfiguration implements ApplicationContextAware {

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.setApplicationContext(applicationContext);
    }

    @Bean
    public OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator() {
        return new DelegatingOAuth2TokenGenerator(
                new OAuth2AuthorizationCodeGenerator(),
                new OAuth2AccessTokenGenerator(),
                new OAuth2RefreshTokenGenerator()
        );
    }

    @Bean
    public RegisteredAppService delegatingRegisteredAppService(
            ApplicationRepository applicationRepository, RegisteredAppRepositoryRedis registeredAppRepositoryRedis) {

        return new DelegatingRegisteredAppService(
                List.of(new JpaRegisteredAppService(applicationRepository),
                        new RedisRegisteredAppService(registeredAppRepositoryRedis),
                        new CaffeineRegisteredAppService())
        );
    }

    @Bean
    public OAuth2AuthorizationService delegatingOAuth2AuthorizationService(AuthorizationRepository authorizationRepository,
                                                                           OAuth2AuthorizationRepositoryRedis oAuth2AuthorizationRepository) {
        return new DelegatingOAuth2AuthorizationService(
                List.of(
                        new JpaOAuth2AuthorizationService(authorizationRepository),
                        new RedisOAuth2AuthorizationService(oAuth2AuthorizationRepository),
                        new CaffeineOAuth2AuthorizationService()
                )
        );
    }

    @Bean
    public AuthenticationConverter authenticationConverter() {
        return new DelegatingAuthenticationConverter(
                List.of(
                        new UsernamePasswordAuthenticationConverter(),
                        new OAuth2AuthorizationCodeAuthenticationConverter(),
                        new OAuth2RefreshTokenAuthenticationConverter()
                )
        );
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> authenticators = ApplicationContextUtils.getBeansOfType(AuthenticationProvider.class);
        return new PrimaryAuthenticationManager(authenticators);
    }
}
