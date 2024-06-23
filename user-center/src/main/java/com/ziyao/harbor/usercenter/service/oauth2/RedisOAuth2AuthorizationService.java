//package com.ziyao.harbor.usercenter.service.oauth2;
//
//import com.ziyao.harbor.core.utils.Assert;
//import com.ziyao.harbor.usercenter.repository.redis.OAuth2AuthorizationRepository;
//import com.ziyao.security.oauth2.core.OAuth2Authorization;
//import com.ziyao.security.oauth2.core.OAuth2TokenType;
//import org.springframework.data.redis.core.HashOperations;
//
///**
// * @author ziyao
// * @since 2024/06/08 17:29:08
// */
//public class RedisOAuth2AuthorizationService extends AbstractOAuth2AuthorizationService {
//
//    private final OAuth2AuthorizationRepository authorizationRepository;
//
//    public RedisOAuth2AuthorizationService(OAuth2AuthorizationRepository authorizationRepository) {
//        this.authorizationRepository = authorizationRepository;
//    }
//
//    @Override
//    public void save(OAuth2Authorization authorization) {
//        Assert.notNull(authorization, "authorization must not be null");
//        this.opsForHash().put(authorization.getId(), authorization);
//    }
//
//    @Override
//    public void remove(OAuth2Authorization authorization) {
//        this.opsForHash().delete(authorization.getId());
//    }
//
//    @Override
//    public OAuth2Authorization findById(Long id) {
//        return this.opsForHash().get(id);
//    }
//
//    @Override
//    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
//        Assert.notNull(token, "token must not be null");
//        Assert.notNull(tokenType, "tokenType must not be null");
//
//        for (OAuth2Authorization authorization : opsForHash().values()) {
//            if (hasToken(authorization, token, tokenType)) {
//                return authorization;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public Model model() {
//        return Model.redis;
//    }
//
//    private HashOperations<Long, OAuth2Authorization> opsForHash() {
//        return authorizationRepository.opsForHash();
//    }
//}
