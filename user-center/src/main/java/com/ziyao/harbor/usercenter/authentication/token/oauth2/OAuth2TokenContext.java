package com.ziyao.harbor.usercenter.authentication.token.oauth2;

import com.ziyao.harbor.usercenter.entity.User;
import com.ziyao.security.oauth2.core.AuthorizationGrantType;
import com.ziyao.security.oauth2.core.OAuth2Authorization;
import com.ziyao.security.oauth2.core.OAuth2TokenType;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
public interface OAuth2TokenContext {

    <V> V get(String key);

    boolean hasKey(String key);

    default <V> V get(Class<V> key) {
        Assert.notNull(key, "key cannot be null");
        return get(key.getSimpleName());
    }

    /**
     * 返回应用信息
     *
     * @return the {@link RegisteredApp}
     */
    default RegisteredApp getRegisteredApp() {
        return get(RegisteredApp.class);
    }


    default OAuth2TokenType getTokenType() {
        return get(OAuth2TokenType.class);
    }

    default Set<String> getAuthorizedScopes() {
        return hasKey(AbstractBuilder.AUTHORIZED_SCOPE_KEY) ?
                get(AbstractBuilder.AUTHORIZED_SCOPE_KEY) :
                Collections.emptySet();
    }

    default AuthorizationGrantType getAuthorizationGrantType() {
        return get(AuthorizationGrantType.class);
    }

    abstract class AbstractBuilder<T extends OAuth2TokenContext, B extends AbstractBuilder<T, B>> {
        /**
         * 授权范围
         */
        private static final String AUTHORIZED_SCOPE_KEY = OAuth2Authorization.class.getName().concat(".AUTHORIZED_SCOPE");
        /**
         * 用户主体信息
         */
        private static final String PRINCIPAL_AUTHENTICATION_KEY = OAuth2Authorization.class.getName().concat(".PRINCIPAL");
        /**
         * 授权上下文信息
         */
        private final Map<String, Object> context = new HashMap<>();


        /**
         * 设置 {@link OAuth2Authorization authorization}.
         */
        public B authorization(OAuth2Authorization authorization) {
            return put(OAuth2Authorization.class, authorization);
        }

        /**
         * 设置授权范围
         *
         * @param authorizedScopes 授权范围-->角色编码
         */
        public B authorizedScopes(Set<String> authorizedScopes) {
            return put(AUTHORIZED_SCOPE_KEY, authorizedScopes);
        }

        /**
         * 设置token类型
         */
        public B tokenType(OAuth2TokenType tokenType) {
            return put(OAuth2TokenType.class, tokenType);
        }

        /**
         * 设置授权类型
         */
        public B authorizationGrantType(AuthorizationGrantType authorizationGrantType) {
            return put(AuthorizationGrantType.class, authorizationGrantType);
        }

        /**
         * 设置应用信息
         */
        public B registeredApp(RegisteredApp registeredApp) {
            return put(RegisteredApp.class, registeredApp);
        }

        /**
         * sets 用户信息
         */
        public B principal(User principal) {
            return put(PRINCIPAL_AUTHENTICATION_KEY, principal);
        }

        /**
         * 关联属性
         *
         * @param key   属性键
         * @param value 属性值
         * @return {@link AbstractBuilder}
         */
        public B put(String key, Object value) {
            Assert.notNull(key, "key cannot be null");
            Assert.notNull(value, "value cannot be null");
            this.context.put(key, value);
            return getThis();
        }

        public B put(Class<?> key, Object value) {
            return this.put(key.getSimpleName(), value);
        }

        /**
         * 用于操作属性
         */
        public B context(Consumer<Map<String, Object>> contextConsumer) {
            contextConsumer.accept(this.context);
            return getThis();
        }

        /**
         * 获取属性
         */
        @SuppressWarnings("unchecked")
        protected <V> V get(Object key) {
            return (V) this.context.get(key);
        }

        protected Map<String, Object> getContext() {
            return this.context;
        }

        @SuppressWarnings("unchecked")
        protected final B getThis() {
            return (B) this;
        }

        public abstract T build();

    }
}
