package com.ziyao.harbor.usercenter.service.oauth2;

import com.ziyao.security.oauth2.core.OAuth2Authorization;
import com.ziyao.security.oauth2.core.OAuth2TokenType;

import java.util.List;

/**
 * @author ziyao
 * @since 2024/06/08 11:27:10
 */
public class DelegatingOAuth2AuthorizationService implements OAuth2AuthorizationService {


    private OAuth2AuthorizationService jpaOAuth2AuthorizationService;
    private OAuth2AuthorizationService caffeineOAuth2AuthorizationService;
    private OAuth2AuthorizationService redisOAuth2AuthorizationService;

    public DelegatingOAuth2AuthorizationService(List<OAuth2AuthorizationService> authorizationServices) {
        for (OAuth2AuthorizationService authorizationService : authorizationServices) {
            switch (authorizationService.model()) {
                case jpa:
                    this.jpaOAuth2AuthorizationService = authorizationService;
                    break;
                case caffeine:
                    this.caffeineOAuth2AuthorizationService = authorizationService;
                    break;
                case redis:
                    this.redisOAuth2AuthorizationService = authorizationService;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + authorizationService);
            }
        }
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        // 先存储数据库
        jpaOAuth2AuthorizationService.save(authorization);
        authorization = jpaOAuth2AuthorizationService.findById(authorization.getId());
        // 保存redis
        redisOAuth2AuthorizationService.save(authorization);
        // 在内存中保存数据
        caffeineOAuth2AuthorizationService.save(authorization);
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        caffeineOAuth2AuthorizationService.remove(authorization);

        redisOAuth2AuthorizationService.remove(authorization);

        jpaOAuth2AuthorizationService.remove(authorization);
    }

    @Override
    public OAuth2Authorization findById(Long id) {
        // 先从内存中查询
        OAuth2Authorization authorization = caffeineOAuth2AuthorizationService.findById(id);
        if (authorization != null) {
            // 从redis查询
            authorization = redisOAuth2AuthorizationService.findById(id);
            if (authorization == null) {
                // 从数据库查
                authorization = jpaOAuth2AuthorizationService.findById(id);
                if (authorization != null) {
                    // 如果数据库查询出来的值不为空则分别给redis和内存中都存储一份
                    redisOAuth2AuthorizationService.save(authorization);
                    caffeineOAuth2AuthorizationService.save(authorization);
                }
            } else {
                // 如果redis查询到的结果不为空 则向内存中存储一份
                caffeineOAuth2AuthorizationService.save(authorization);
            }
        }
        return authorization;
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        // 先从内存中查询
        OAuth2Authorization authorization = caffeineOAuth2AuthorizationService.findByToken(token, tokenType);
        if (authorization == null) {
            // 从redis查询
            authorization = redisOAuth2AuthorizationService.findByToken(token, tokenType);
            if (authorization == null) {
                // 从数据库查
                authorization = jpaOAuth2AuthorizationService.findByToken(token, tokenType);
                if (authorization != null) {
                    // 如果数据库查询出来的值不为空则分别给redis和内存中都存储一份
                    redisOAuth2AuthorizationService.save(authorization);
                    caffeineOAuth2AuthorizationService.save(authorization);
                }
            } else {
                // 如果redis查询到的结果不为空 则向内存中存储一份
                caffeineOAuth2AuthorizationService.save(authorization);
            }
        }
        return authorization;
    }
}
