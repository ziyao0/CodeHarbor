package com.ziyao.harbor.usercenter.service.oauth2;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.usercenter.repository.redis.OAuth2AuthorizationRepositoryRedis;
import com.ziyao.security.oauth2.core.OAuth2Authorization;
import com.ziyao.security.oauth2.core.OAuth2TokenType;

import java.util.List;
import java.util.Optional;

/**
 * @author ziyao
 * @since 2024/06/08 17:29:08
 */
public class RedisOAuth2AuthorizationService extends AbstractOAuth2AuthorizationService {

    private final OAuth2AuthorizationRepositoryRedis oauth2AuthorizationRepositoryRedis;

    public RedisOAuth2AuthorizationService(OAuth2AuthorizationRepositoryRedis authorizationRepository) {
        this.oauth2AuthorizationRepositoryRedis = authorizationRepository;
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization must not be null");
        oauth2AuthorizationRepositoryRedis.save(authorization);
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        oauth2AuthorizationRepositoryRedis.delete(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(Long id) {
        return oauth2AuthorizationRepositoryRedis.findById(id).orElse(null);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.notNull(token, "token must not be null");
        Assert.notNull(tokenType, "tokenType must not be null");

        Optional<List<OAuth2Authorization>> optionalOAuth2Authorizations = oauth2AuthorizationRepositoryRedis.findAll();

        if (optionalOAuth2Authorizations.isPresent()) {
            for (OAuth2Authorization authorization : optionalOAuth2Authorizations.get()) {
                if (hasToken(authorization, token, tokenType)) {
                    return authorization;
                }
            }
        }
        return null;
    }

    @Override
    public Model model() {
        return Model.redis;
    }
}
