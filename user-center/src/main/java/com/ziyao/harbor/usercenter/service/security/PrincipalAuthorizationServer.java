package com.ziyao.harbor.usercenter.service.security;

import com.ziyao.harbor.usercenter.authentication.AuthenticationManager;
import com.ziyao.harbor.usercenter.authentication.support.SecurityUtils;
import com.ziyao.harbor.usercenter.authentication.token.OAuth2AccessTokenAuthenticationToken;
import com.ziyao.harbor.usercenter.authentication.token.OAuth2TokenGenerator;
import com.ziyao.harbor.usercenter.entity.Application;
import com.ziyao.harbor.usercenter.repository.jpa.ApplicationRepository;
import com.ziyao.harbor.usercenter.repository.redis2.ApplicationRepositoryRedis;
import com.ziyao.harbor.usercenter.response.AccessTokenResponse;
import com.ziyao.harbor.usercenter.response.OAuth2AuthorizationCodeResponse;
import com.ziyao.harbor.usercenter.service.app.RegisteredAppService;
import com.ziyao.harbor.usercenter.service.oauth2.OAuth2AuthorizationService;
import com.ziyao.security.oauth2.core.*;
import com.ziyao.security.oauth2.core.context.SecurityContextHolder;
import com.ziyao.security.oauth2.core.token.DefaultOAuth2TokenContext;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author ziyao zhang
 * @time 2024/6/15
 */
@Service
@RequiredArgsConstructor
public class PrincipalAuthorizationServer implements AuthorizationServer {


    private final AuthenticationManager authenticationManager;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    private final RegisteredAppService registeredAppService;
    private final OAuth2AuthorizationService authorizationService;
    private final ApplicationRepositoryRedis applicationRepositoryRedis;
    private final ApplicationRepository applicationRepository;

//    public PrincipalAuthorizationServer(AuthenticationManager authenticationManager,
//                                        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
//                                        RegisteredAppService registeredAppService,
//                                        OAuth2AuthorizationService authorizationService) {
//        this.authenticationManager = authenticationManager;
//        this.tokenGenerator = tokenGenerator;
//        this.registeredAppService = registeredAppService;
//        this.authorizationService = authorizationService;
//    }

    @Override
    public OAuth2AuthorizationCodeResponse authorize(Long appId, String state, String grantType) {

        RegisteredApp registeredApp = registeredAppService.findById(appId);
        Application application = applicationRepository.findById(appId).get();
        Application save = applicationRepositoryRedis.save(application);

        List<Application> all = applicationRepositoryRedis.findAll();
        Optional<Application> byId = applicationRepositoryRedis.findById(appId);

        if (registeredApp == null) {
            throw new DataRetrievalFailureException(
                    "The RegisteredClient with id '" + appId + "' was not found in the RegisteredClientRepository.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        DefaultOAuth2TokenContext context = DefaultOAuth2TokenContext.builder()
                .tokenType(new OAuth2TokenType(grantType))
                .registeredApp(registeredApp)
                // 填充用户信息
                .principal(authentication)
                .build();

        OAuth2Token auth2Token = this.tokenGenerator.generate(context);
        if (null == auth2Token) {
            return null;
        }
        OAuth2AuthorizationCode authorizationCode = (OAuth2AuthorizationCode) auth2Token;

        UserDetails principal = (UserDetails) authentication.getPrincipal();

        OAuth2Authorization authorization = OAuth2Authorization.withAppId(registeredApp.getAppId())
//                .userId(principal.getId())
                .userId(10001L)
                .token(authorizationCode)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .build();

        authorizationService.save(authorization);
        return OAuth2AuthorizationCodeResponse.create(authorizationCode.getTokenValue(), "");
    }

    @Override
    public AccessTokenResponse token(Authentication authentication) {
        OAuth2AccessTokenAuthenticationToken authenticationToken = (OAuth2AccessTokenAuthenticationToken) this.authenticationManager.authenticate(authentication);
        if (SecurityUtils.isUnauthorized(authentication)) {
            // TODO
        }

        return AccessTokenResponse.witchTokenType(authenticationToken.getAccessToken().getTokenType().getValue())
                .accessToken(authenticationToken.getAccessToken().getTokenValue())
                .refreshToken(authenticationToken.getRefreshToken().getTokenValue())
                .build();
    }
}
