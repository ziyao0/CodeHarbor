package com.ziyao.oauth2.core;

import com.ziyao.harbor.core.utils.Strings;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
public class OAuth2Authorization implements Serializable {
    @Serial
    private static final long serialVersionUID = 161440620113264850L;
    @Getter
    private String id;
    @Getter
    private String registeredClientId;
    /**
     * -- GETTER --
     * Returns the
     * used for the authorization.
     */
    @Getter
    private AuthorizationGrantType authorizationGrantType;
    /**
     * -- GETTER --
     * Returns the authorized scope(s).
     */
    @Getter
    private Set<String> authorizedScopes;
    private Map<Class<? extends OAuth2Token>, Token<?>> tokens;
    @Getter
    private Map<String, Object> attributes;

    /**
     * Returns the {@link Token} of type {@link OAuth2AccessToken}.
     *
     * @return the {@link Token} of type {@link OAuth2AccessToken}
     */
    public Token<OAuth2AccessToken> getAccessToken() {
        return getToken(OAuth2AccessToken.class);
    }

    /**
     * Returns the {@link Token} of type {@link OAuth2RefreshToken}.
     *
     * @return the {@link Token} of type {@link OAuth2RefreshToken}, or {@code null} if not available
     */
    @Nullable
    public Token<OAuth2RefreshToken> getRefreshToken() {
        return getToken(OAuth2RefreshToken.class);
    }


    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends OAuth2Token> Token<T> getToken(Class<T> tokenType) {
        Assert.notNull(tokenType, "tokenType cannot be null");
        Token<?> token = this.tokens.get(tokenType);
        return token != null ? (Token<T>) token : null;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String name) {
        Assert.hasText(name, "name cannot be empty");
        return (T) this.attributes.get(name);
    }


    /**
     * Returns a new {@link Builder}, initialized with the values from the provided {@code OAuth2Authorization}.
     *
     * @param authorization the {@code OAuth2Authorization} used for initializing the {@link Builder}
     * @return the {@link Builder}
     */
    public static Builder from(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        return new Builder(authorization.getRegisteredClientId())
                .id(authorization.getId())
                .authorizationGrantType(authorization.getAuthorizationGrantType())
                .authorizedScopes(authorization.getAuthorizedScopes())
                .tokens(authorization.tokens)
                .attributes(attrs -> attrs.putAll(authorization.getAttributes()));
    }


    @Getter
    public static class Token<T extends OAuth2Token> implements Serializable {
        @Serial
        private static final long serialVersionUID = 5452476839192606325L;
        protected static final String TOKEN_METADATA_NAMESPACE = "metadata.token.";

        /**
         * The name of the metadata that indicates if the token has been invalidated.
         */
        public static final String INVALIDATED_METADATA_NAME = TOKEN_METADATA_NAMESPACE.concat("invalidated");

        /**
         * The name of the metadata used for the claims of the token.
         */
        public static final String CLAIMS_METADATA_NAME = TOKEN_METADATA_NAMESPACE.concat("claims");

        /**
         * -- GETTER --
         * Returns the token of type
         * .
         */
        private final T token;
        /**
         * -- GETTER --
         * Returns the metadata associated to the token.
         */
        private final Map<String, Object> metadata;

        protected Token(T token) {
            this(token, defaultMetadata());
        }

        protected Token(T token, Map<String, Object> metadata) {
            this.token = token;
            this.metadata = Collections.unmodifiableMap(metadata);
        }

        /**
         * Returns {@code true} if the token has been invalidated (e.g. revoked).
         * The default is {@code false}.
         *
         * @return {@code true} if the token has been invalidated, {@code false} otherwise
         */
        public boolean isInvalidated() {
            return Boolean.TRUE.equals(getMetadata(INVALIDATED_METADATA_NAME));
        }

        /**
         * Returns {@code true} if the token has expired.
         *
         * @return {@code true} if the token has expired, {@code false} otherwise
         */
        public boolean isExpired() {
            return getToken().getExpiresAt() != null && Instant.now().isAfter(getToken().getExpiresAt());
        }

        /**
         * Returns {@code true} if the token is before the time it can be used.
         *
         * @return {@code true} if the token is before the time it can be used, {@code false} otherwise
         */
        public boolean isBeforeUse() {
            Instant notBefore = null;
            if (!CollectionUtils.isEmpty(getClaims())) {
                notBefore = (Instant) getClaims().get("nbf");
            }
            return notBefore != null && Instant.now().isBefore(notBefore);
        }

        /**
         * Returns {@code true} if the token is currently active.
         *
         * @return {@code true} if the token is currently active, {@code false} otherwise
         */
        public boolean isActive() {
            return !isInvalidated() && !isExpired() && !isBeforeUse();
        }

        /**
         * Returns the claims associated to the token.
         *
         * @return a {@code Map} of the claims, or {@code null} if not available
         */
        @Nullable
        public Map<String, Object> getClaims() {
            return getMetadata(CLAIMS_METADATA_NAME);
        }

        /**
         * Returns the value of the metadata associated to the token.
         *
         * @param name the name of the metadata
         * @param <V>  the value type of the metadata
         * @return the value of the metadata, or {@code null} if not available
         */
        @Nullable
        @SuppressWarnings("unchecked")
        public <V> V getMetadata(String name) {
            Assert.hasText(name, "name cannot be empty");
            return (V) this.metadata.get(name);
        }

        protected static Map<String, Object> defaultMetadata() {
            Map<String, Object> metadata = new HashMap<>();
            metadata.put(INVALIDATED_METADATA_NAME, false);
            return metadata;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Token<?> token1 = (Token<?>) o;
            return Objects.equals(token, token1.token) && Objects.equals(metadata, token1.metadata);
        }

        @Override
        public int hashCode() {
            return Objects.hash(token, metadata);
        }
    }

    /**
     * A builder for {@link OAuth2Authorization}.
     */
    public static class Builder implements Serializable {
        @Serial
        private static final long serialVersionUID = -5658976968224374315L;

        private String id;
        private final String registeredClientId;
        private AuthorizationGrantType authorizationGrantType;
        private Set<String> authorizedScopes;
        private Map<Class<? extends OAuth2Token>, Token<?>> tokens = new HashMap<>();
        private final Map<String, Object> attributes = new HashMap<>();

        protected Builder(String registeredClientId) {
            this.registeredClientId = registeredClientId;
        }

        /**
         * Sets the identifier for the authorization.
         *
         * @param id the identifier for the authorization
         * @return the {@link Builder}
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the {@link AuthorizationGrantType authorization grant type} used for the authorization.
         *
         * @param authorizationGrantType the {@link AuthorizationGrantType}
         * @return the {@link Builder}
         */
        public Builder authorizationGrantType(AuthorizationGrantType authorizationGrantType) {
            this.authorizationGrantType = authorizationGrantType;
            return this;
        }

        /**
         * Sets the authorized scope(s).
         *
         * @param authorizedScopes the {@code Set} of authorized scope(s)
         * @return the {@link Builder}
         * @since 0.4.0
         */
        public Builder authorizedScopes(Set<String> authorizedScopes) {
            this.authorizedScopes = authorizedScopes;
            return this;
        }

        /**
         * Sets the {@link OAuth2AccessToken access token}.
         *
         * @param accessToken the {@link OAuth2AccessToken}
         * @return the {@link Builder}
         */
        public Builder accessToken(OAuth2AccessToken accessToken) {
            return token(accessToken);
        }

        /**
         * Sets the {@link OAuth2RefreshToken refresh token}.
         *
         * @param refreshToken the {@link OAuth2RefreshToken}
         * @return the {@link Builder}
         */
        public Builder refreshToken(OAuth2RefreshToken refreshToken) {
            return token(refreshToken);
        }

        /**
         * Sets the {@link OAuth2Token token}.
         *
         * @param token the token
         * @param <T>   the type of the token
         * @return the {@link Builder}
         */
        public <T extends OAuth2Token> Builder token(T token) {
            return token(token, (metadata) -> {
            });
        }

        /**
         * Sets the {@link OAuth2Token token} and associated metadata.
         *
         * @param token            the token
         * @param metadataConsumer a {@code Consumer} of the metadata {@code Map}
         * @param <T>              the type of the token
         * @return the {@link Builder}
         */
        public <T extends OAuth2Token> Builder token(T token,
                                                     Consumer<Map<String, Object>> metadataConsumer) {

            Assert.notNull(token, "token cannot be null");
            Map<String, Object> metadata = Token.defaultMetadata();
            Token<?> existingToken = this.tokens.get(token.getClass());
            if (existingToken != null) {
                metadata.putAll(existingToken.getMetadata());
            }
            metadataConsumer.accept(metadata);
            Class<? extends OAuth2Token> tokenClass = token.getClass();
            this.tokens.put(tokenClass, new Token<>(token, metadata));
            return this;
        }

        protected final Builder tokens(Map<Class<? extends OAuth2Token>, Token<?>> tokens) {
            this.tokens = new HashMap<>(tokens);
            return this;
        }

        /**
         * Adds an attribute associated to the authorization.
         *
         * @param name  the name of the attribute
         * @param value the value of the attribute
         * @return the {@link Builder}
         */
        public Builder attribute(String name, Object value) {
            Assert.hasText(name, "name cannot be empty");
            Assert.notNull(value, "value cannot be null");
            this.attributes.put(name, value);
            return this;
        }

        /**
         * A {@code Consumer} of the attributes {@code Map}
         * allowing the ability to add, replace, or remove.
         *
         * @param attributesConsumer a {@link Consumer} of the attributes {@code Map}
         * @return the {@link Builder}
         */
        public Builder attributes(Consumer<Map<String, Object>> attributesConsumer) {
            attributesConsumer.accept(this.attributes);
            return this;
        }

        /**
         * Builds a new {@link OAuth2Authorization}.
         *
         * @return the {@link OAuth2Authorization}
         */
        public OAuth2Authorization build() {
            Assert.notNull(this.authorizationGrantType, "authorizationGrantType cannot be null");
            OAuth2Authorization authorization = new OAuth2Authorization();
            if (!Strings.hasText(this.id)) {
                this.id = UUID.randomUUID().toString();
            }
            authorization.id = this.id;
            authorization.registeredClientId = this.registeredClientId;
            authorization.authorizationGrantType = this.authorizationGrantType;
            authorization.authorizedScopes =
                    Collections.unmodifiableSet(
                            !com.ziyao.harbor.core.utils.Collections.isEmpty(this.authorizedScopes) ?
                                    new HashSet<>(this.authorizedScopes) :
                                    new HashSet<>()
                    );
            authorization.tokens = Collections.unmodifiableMap(this.tokens);
            authorization.attributes = Collections.unmodifiableMap(this.attributes);
            return authorization;
        }

    }
}

