package com.ziyao.harbor.usercenter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Lists;
import com.ziyao.harbor.usercenter.authentication.AuthenticationManager;
import com.ziyao.harbor.usercenter.authentication.PrimaryAuthenticationManager;
import com.ziyao.harbor.usercenter.authentication.converter.*;
import com.ziyao.harbor.usercenter.authentication.provider.AuthenticationProvider;
import com.ziyao.harbor.usercenter.authentication.token.*;
import com.ziyao.harbor.usercenter.repository.jpa.ApplicationRepository;
import com.ziyao.harbor.usercenter.repository.jpa.AuthorizationRepository;
import com.ziyao.harbor.usercenter.repository.redis.OAuth2AuthorizationRepository;
import com.ziyao.harbor.usercenter.repository.redis.RedisRegisteredAppRepository;
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
            ApplicationRepository applicationRepository, RedisRegisteredAppRepository redisRegisteredAppRepository) {

        return new DelegatingRegisteredAppService(
                Lists.newArrayList(new JpaRegisteredAppService(applicationRepository),
                        new RedisRegisteredAppService(redisRegisteredAppRepository),
                        new CaffeineRegisteredAppService())
        );
    }

    @Bean
    public OAuth2AuthorizationService delegatingOAuth2AuthorizationService(RegisteredAppService delegatingRegisteredAppService,
                                                                           AuthorizationRepository authorizationRepository,
                                                                           OAuth2AuthorizationRepository oAuth2AuthorizationRepository) {
        return new DelegatingOAuth2AuthorizationService(
                Lists.newArrayList(
                        new JpaOAuth2AuthorizationService(delegatingRegisteredAppService, authorizationRepository),
                        new RedisOAuth2AuthorizationService(oAuth2AuthorizationRepository),
                        new CaffeineOAuth2AuthorizationService()
                )
        );
    }

    @Bean
    public AuthenticationConverter authenticationConverter() {
        return new DelegatingAuthenticationConverter(
                Lists.newArrayList(
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
