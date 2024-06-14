package com.ziyao.harbor.usercenter.service.oauth2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.authentication.token.oauth2.RegisteredApp;
import com.ziyao.harbor.usercenter.entity.Authorization;
import com.ziyao.harbor.usercenter.repository.jpa.AuthorizationRepository;
import com.ziyao.harbor.usercenter.service.app.RegisteredAppService;
import com.ziyao.security.oauth2.core.*;
import com.ziyao.security.oauth2.support.AuthorizationGrantTypes;
import com.ziyao.security.oauth2.token.OAuth2ParameterNames;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 默认实现---数据库操作
 *
 * @author ziyao
 * @since 2024/06/08 11:34:05
 */
public class JpaOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final RegisteredAppService registeredAppService;
    private final AuthorizationRepository authorizationRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JpaOAuth2AuthorizationService(RegisteredAppService registeredAppService, AuthorizationRepository authorizationRepository) {
        this.registeredAppService = registeredAppService;
        this.authorizationRepository = authorizationRepository;

//        ClassLoader classLoader = JpaOAuth2AuthorizationService.class.getClassLoader();
//        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
//        this.objectMapper.registerModules(securityModules);
//        this.objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
    }


    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization must not be null");
        this.authorizationRepository.save(toEntity(authorization));
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization must not be null");
        this.authorizationRepository.deleteById(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(Long id) {
        Assert.notNull(id, "id must not be null");
        return this.authorizationRepository.findById(id).map(this::toObject).orElse(null);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.notNull(token, "token must not be null");
        Assert.notNull(tokenType, "tokenType must not be null");


        Optional<Authorization> result;
        if (OAuth2ParameterNames.CODE.equals(tokenType.value())) {
            result = authorizationRepository.findByAuthorizationCodeValue(token);
        } else if (OAuth2ParameterNames.REFRESH_TOKEN.equals(tokenType.value())) {
            result = authorizationRepository.findByRefreshTokenValue(token);
        } else if (OAuth2ParameterNames.ACCESS_TOKEN.equals(tokenType.value())) {
            result = authorizationRepository.findByAccessTokenValue(token);
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.value())) {
            result = authorizationRepository.findByState(Integer.parseInt(token));
        } else {
            result = Optional.empty();
        }

        return result.map(this::toObject).orElse(null);
    }

    @Override
    public Model model() {
        return Model.jpa;
    }

    private OAuth2Authorization toObject(Authorization entity) {
        RegisteredApp registeredApp = registeredAppService.findById(entity.getAppid());
        if (registeredApp == null) {
            throw new DataRetrievalFailureException(
                    "The RegisteredClient with id '" + entity.getAppid() + "' was not found in the RegisteredClientRepository.");
        }
        OAuth2Authorization.Builder builder = OAuth2Authorization.withAppId(registeredApp.getAppId())
                .id(entity.getId())
                //.principalName(entity.getPrincipalName())
                .authorizationGrantType(AuthorizationGrantTypes.resolve(entity.getAuthorizationGrantType()))
                .authorizedScopes(StringUtils.commaDelimitedListToSet(entity.getAuthorizedScopes()))
                .attributes(attributes -> attributes.putAll(parseMap(entity.getAttributes())));

        if (entity.getState() != null) {
            builder.attribute(OAuth2ParameterNames.STATE, entity.getState());
        }

        if (entity.getAuthorizationCodeValue() != null) {
            OAuth2AuthorizationCode authorizationCode = new OAuth2AuthorizationCode(
                    entity.getAuthorizationCodeValue(),
                    entity.getAuthorizationCodeIssuedAt(),
                    entity.getAuthorizationCodeExpiresAt());
            builder.token(authorizationCode, metadata -> metadata.putAll(parseMap(entity.getAuthorizationCodeMetadata())));
        }
        if (entity.getAccessTokenValue() != null) {
            OAuth2AccessToken accessToken = new OAuth2AccessToken(
                    OAuth2AccessToken.TokenType.Bearer,
                    entity.getAccessTokenValue(),
                    entity.getAccessTokenIssuedAt(),
                    entity.getAccessTokenExpiresAt(),
                    StringUtils.commaDelimitedListToSet(entity.getAccessTokenScopes()));
            builder.token(accessToken, metadata -> metadata.putAll(parseMap(entity.getAccessTokenMetadata())));
        }

        if (entity.getRefreshTokenValue() != null) {
            OAuth2RefreshToken refreshToken = new OAuth2RefreshToken(
                    entity.getRefreshTokenValue(),
                    entity.getRefreshTokenIssuedAt(),
                    entity.getRefreshTokenExpiresAt());
            builder.token(refreshToken, metadata -> metadata.putAll(parseMap(entity.getRefreshTokenMetadata())));
        }
        return builder.build();
    }

    private Authorization toEntity(OAuth2Authorization authorization) {
        Authorization entity = new Authorization();
        entity.setId(authorization.getId());
        entity.setAppid(authorization.getAppId());
        entity.setUserId(authorization.getUserId());
        entity.setAuthorizationGrantType(authorization.getAuthorizationGrantType().value());
        entity.setAuthorizedScopes(Strings.collectionToDelimitedString(authorization.getAuthorizedScopes(), ","));
        entity.setAttributes(writeMap(authorization.getAttributes()));
        entity.setState(authorization.getAttribute(OAuth2ParameterNames.STATE));
        //设置状态
        entity.setState(1);

        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode =
                authorization.getToken(OAuth2AuthorizationCode.class);
        setTokenValues(
                authorizationCode,
                entity::setAuthorizationCodeValue,
                entity::setAuthorizationCodeIssuedAt,
                entity::setAuthorizationCodeExpiresAt,
                entity::setAuthorizationCodeMetadata
        );

        OAuth2Authorization.Token<OAuth2AccessToken> accessToken =
                authorization.getToken(OAuth2AccessToken.class);
        setTokenValues(
                accessToken,
                entity::setAccessTokenValue,
                entity::setAccessTokenIssuedAt,
                entity::setAccessTokenExpiresAt,
                entity::setAccessTokenMetadata
        );
        if (accessToken != null && accessToken.getToken().getScopes() != null) {
            entity.setAccessTokenScopes(StringUtils.collectionToDelimitedString(accessToken.getToken().getScopes(), ","));
        }

        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken =
                authorization.getToken(OAuth2RefreshToken.class);
        setTokenValues(
                refreshToken,
                entity::setRefreshTokenValue,
                entity::setRefreshTokenIssuedAt,
                entity::setRefreshTokenExpiresAt,
                entity::setRefreshTokenMetadata
        );

        return entity;
    }

    private void setTokenValues(
            OAuth2Authorization.Token<?> token,
            Consumer<String> tokenValueConsumer,
            Consumer<Instant> issuedAtConsumer,
            Consumer<Instant> expiresAtConsumer,
            Consumer<String> metadataConsumer) {
        if (token != null) {
            OAuth2Token oAuth2Token = token.getToken();
            tokenValueConsumer.accept(oAuth2Token.getTokenValue());
            issuedAtConsumer.accept(oAuth2Token.getIssuedAt());
            expiresAtConsumer.accept(oAuth2Token.getExpiresAt());
            metadataConsumer.accept(writeMap(token.getMetadata()));
        }
    }


    private Map<String, Object> parseMap(String data) {
        try {
            return this.objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    private String writeMap(Map<String, Object> metadata) {
        try {
            return this.objectMapper.writeValueAsString(metadata);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

}
