package com.ziyao.harbor.usercenter.service.oauth2;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.ziyao.security.oauth2.core.OAuth2Authorization;
import com.ziyao.security.oauth2.core.OAuth2TokenType;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao
 * @since 2024/06/08 11:08:11
 */
public class CaffeineOAuth2AuthorizationService extends AbstractOAuth2AuthorizationService {

    private final int maxInitializedAuthorizations = 100;

    /*
     * 存储尚未颁发访问令牌的授权码认证对象
     */
    private final Cache<Long, OAuth2Authorization> initializedAuthorizations = Caffeine.newBuilder()
            .maximumSize(maxInitializedAuthorizations)
            .expireAfterWrite(7, TimeUnit.DAYS)
            .build();

    /*
     * 存储已经颁发了访问令牌的认证对象
     */
    private final Cache<Long, OAuth2Authorization> authorizations =
            Caffeine.newBuilder().maximumSize(1000).build();

    public CaffeineOAuth2AuthorizationService() {
        this(Collections.emptyList());
    }

    public CaffeineOAuth2AuthorizationService(OAuth2Authorization... authorizations) {
        this(Arrays.asList(authorizations));
    }

    public CaffeineOAuth2AuthorizationService(List<OAuth2Authorization> authorizations) {

        if (!CollectionUtils.isEmpty(authorizations)) {
            for (OAuth2Authorization authorization : authorizations) {
                this.authorizations.put(authorization.getId(), authorization);
            }
        }
    }


    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        if (isComplete(authorization)) this.authorizations.put(authorization.getId(), authorization);
        else this.initializedAuthorizations.put(authorization.getId(), authorization);
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        if (isComplete(authorization)) this.authorizations.invalidate(authorization.getId());
        else this.initializedAuthorizations.invalidate(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(Long id) {
        Assert.notNull(id, "id cannot be empty");
        OAuth2Authorization authorization = this.authorizations.getIfPresent(id);
        return authorization != null ? authorization : this.initializedAuthorizations.getIfPresent(id);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");
        for (OAuth2Authorization authorization : this.authorizations.asMap().values()) {
            if (hasToken(authorization, token, tokenType)) return authorization;
        }
        for (OAuth2Authorization authorization : this.initializedAuthorizations.asMap().values()) {
            if (hasToken(authorization, token, tokenType)) return authorization;
        }
        return null;
    }

    @Override
    public Model model() {
        return Model.caffeine;
    }
}
