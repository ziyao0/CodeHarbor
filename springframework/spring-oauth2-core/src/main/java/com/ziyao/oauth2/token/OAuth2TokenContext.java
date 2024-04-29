package com.ziyao.oauth2.token;

import com.ziyao.oauth2.core.AuthorizationGrantType;
import com.ziyao.oauth2.core.OAuth2Authorization;
import com.ziyao.oauth2.core.OAuth2TokenType;
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


    default OAuth2TokenType getTokenType() {
        return get(OAuth2TokenType.class);
    }

    default Set<String> getAuthorizedScopes() {
//        return hasKey(AbstractBuilder.AUTHORIZED_SCOPE_KEY) ?
//                get(AbstractBuilder.AUTHORIZED_SCOPE_KEY) :
//                Collections.emptySet();
        return Collections.emptySet();
    }

    default AuthorizationGrantType getAuthorizationGrantType() {
        return get(AuthorizationGrantType.class);
    }

    abstract class AbstractBuilder<T extends OAuth2TokenContext, B extends AbstractBuilder<T, B>> {
        private static final String AUTHORIZED_SCOPE_KEY =
                OAuth2Authorization.class.getName().concat(".AUTHORIZED_SCOPE");
        private final Map<String, Object> context = new HashMap<>();


        /**
         * Sets the {@link OAuth2Authorization authorization}.
         *
         * @param authorization the {@link OAuth2Authorization}
         * @return the {@link AbstractBuilder} for further configuration
         */
        public B authorization(OAuth2Authorization authorization) {
            return put(OAuth2Authorization.class, authorization);
        }

        /**
         * Sets the authorized scope(s).
         *
         * @param authorizedScopes the authorized scope(s)
         * @return the {@link AbstractBuilder} for further configuration
         */
        public B authorizedScopes(Set<String> authorizedScopes) {
            return put(AUTHORIZED_SCOPE_KEY, authorizedScopes);
        }

        /**
         * Sets the {@link OAuth2TokenType token type}.
         *
         * @param tokenType the {@link OAuth2TokenType}
         * @return the {@link AbstractBuilder} for further configuration
         */
        public B tokenType(OAuth2TokenType tokenType) {
            return put(OAuth2TokenType.class, tokenType);
        }

        /**
         * Sets the {@link AuthorizationGrantType authorization grant type}.
         *
         * @param authorizationGrantType the {@link AuthorizationGrantType}
         * @return the {@link AbstractBuilder} for further configuration
         */
        public B authorizationGrantType(AuthorizationGrantType authorizationGrantType) {
            return put(AuthorizationGrantType.class, authorizationGrantType);
        }

        /**
         * Associates an attribute.
         *
         * @param key   the key for the attribute
         * @param value the value of the attribute
         * @return the {@link AbstractBuilder} for further configuration
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
         * A {@code Consumer} of the attributes {@code Map}
         * allowing the ability to add, replace, or remove.
         *
         * @param contextConsumer a {@link Consumer} of the attributes {@code Map}
         * @return the {@link AbstractBuilder} for further configuration
         */
        public B context(Consumer<Map<String, Object>> contextConsumer) {
            contextConsumer.accept(this.context);
            return getThis();
        }

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

        /**
         * Builds a new {@link OAuth2TokenContext}.
         *
         * @return the {@link OAuth2TokenContext}
         */
        public abstract T build();

    }
}
