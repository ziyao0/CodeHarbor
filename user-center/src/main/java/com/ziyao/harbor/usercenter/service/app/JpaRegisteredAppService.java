package com.ziyao.harbor.usercenter.service.app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.authentication.jackson2.OAuth2AuthorizationServerJackson2Module;
import com.ziyao.harbor.usercenter.authentication.token.oauth2.RegisteredApp;
import com.ziyao.harbor.usercenter.entity.Application;
import com.ziyao.harbor.usercenter.repository.jpa.ApplicationRepository;
import com.ziyao.security.oauth2.settings.TokenSettings;
import com.ziyao.security.oauth2.support.AuthorizationGrantTypes;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

/**
 * @author ziyao
 * @since 2024/06/08 17:27:56
 */
public class JpaRegisteredAppService implements RegisteredAppService {

    private final ApplicationRepository applicationRepository;

    private final ObjectMapper objectMapper;

    public JpaRegisteredAppService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        this.objectMapper = objectMapper;
    }

    @Override
    public void save(RegisteredApp registeredApp) {
        applicationRepository.save(toEntity(registeredApp));
    }

    @Override
    public RegisteredApp findById(Long appId) {
        return applicationRepository.findById(appId).map(this::toObject).orElse(null);
    }

    @Override
    public Model model() {
        return Model.jpa;
    }

    private RegisteredApp toObject(Application application) {

        Set<String> authorizationGrantTypes = Strings.commaDelimitedListToSet(
                application.getAuthorizationGrantTypes());

        Set<String> appScopes = Strings.commaDelimitedListToSet(
                application.getScopes()
        );

        Map<String, Object> tokenSettingsMap = parseMap(application.getTokenSettings());

        RegisteredApp.Builder builder = RegisteredApp.withAppId(application.getAppId())
                .appName(application.getAppName())
                .appType(application.getAppType())
                .issuedAt(application.getIssuedAt())
                .appSecret(application.getAppSecret())
                .appSecretExpiresAt(application.getAppSecretExpiresAt())
                .state(application.getState())
                .redirectUri(application.getRedirectUri())
                .postLogoutRedirectUris(application.getPostLogoutRedirectUri())
                .authorizationGrantTypes(grantTypes -> {
                    authorizationGrantTypes.forEach(authorizationGrantType -> {
                        grantTypes.add(AuthorizationGrantTypes.resolve(authorizationGrantType));
                    });
                })
                .scopes((scopes) -> {
                    scopes.addAll(appScopes);
                });

        TokenSettings tokenSettings = TokenSettings.withSettings(tokenSettingsMap).reuseRefreshTokens(true).build();

        return builder.tokenSettings(tokenSettings).build();
    }

    private Application toEntity(RegisteredApp registeredApp) {
        Application application = new Application();
        application.setAppId(registeredApp.getAppId());
        application.setAppName(registeredApp.getAppName());
        application.setState(registeredApp.getState());
        application.setAuthorizationGrantTypes(StringUtils.collectionToCommaDelimitedString(registeredApp.getAuthorizationGrantTypes()));
        application.setScopes(StringUtils.collectionToCommaDelimitedString(registeredApp.getScopes()));
        application.setAppType(registeredApp.getAppType());
        application.setAppSecret(registeredApp.getAppSecret());
        application.setIssuedAt(registeredApp.getIssuedAt());
        application.setAppSecretExpiresAt(registeredApp.getAppSecretExpiresAt());
        application.setRedirectUri(registeredApp.getRedirectUri());
        application.setPostLogoutRedirectUri(registeredApp.getPostLogoutRedirectUri());
        application.setTokenSettings(writeMap(registeredApp.getTokenSettings().getSettings()));
        return application;
    }


    private Map<String, Object> parseMap(String data) {
        try {
            return this.objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    private String writeMap(Map<String, Object> data) {
        try {
            return this.objectMapper.writeValueAsString(data);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
}
