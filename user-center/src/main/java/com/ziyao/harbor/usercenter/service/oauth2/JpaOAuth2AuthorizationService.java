package com.ziyao.harbor.usercenter.service.oauth2;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.usercenter.entity.Authorization;
import com.ziyao.harbor.usercenter.repository.jpa.AuthorizationRepository;
import com.ziyao.security.oauth2.core.OAuth2Authorization;
import com.ziyao.security.oauth2.core.OAuth2TokenType;
import com.ziyao.security.oauth2.core.jackson2.Jackson2Modules;
import com.ziyao.security.oauth2.core.jackson2.OAuth2AuthorizationServerJackson2Module;
import com.ziyao.security.oauth2.core.token.OAuth2ParameterNames;

import java.util.List;
import java.util.Optional;

/**
 * 默认实现---数据库操作
 *
 * @author ziyao
 * @since 2024/06/08 11:34:05
 */
public class JpaOAuth2AuthorizationService extends AbstractOAuth2AuthorizationService {


    private final AuthorizationRepository authorizationRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JpaOAuth2AuthorizationService(AuthorizationRepository authorizationRepository) {

        this.authorizationRepository = authorizationRepository;

        ClassLoader classLoader = JpaOAuth2AuthorizationService.class.getClassLoader();
        List<Module> securityModules = Jackson2Modules.getModules(classLoader);
        this.objectMapper.registerModules(securityModules);
        this.objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
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


}
