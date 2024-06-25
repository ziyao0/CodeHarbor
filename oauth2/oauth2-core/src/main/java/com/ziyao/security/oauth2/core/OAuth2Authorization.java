package com.ziyao.security.oauth2.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ziyao.security.oauth2.core.jackson2.OAuth2AuthorizationDeserializer;
import com.ziyao.security.oauth2.core.jackson2.OAuth2AuthorizationSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
@Getter
@EqualsAndHashCode
@JsonSerialize(using = OAuth2AuthorizationSerializer.class)
@JsonDeserialize(using = OAuth2AuthorizationDeserializer.class)
public class OAuth2Authorization implements Serializable {
    private static final long serialVersionUID = 161440620113264850L;
    private Long id;
    private Long appId;
    private Long userId;
    /**
     * 授权类型
     */
    private AuthorizationGrantType authorizationGrantType;
    /**
     * 授权范围
     */
    private Set<String> authorizedScopes;
    /**
     * oauth2 token
     */
    private Map<Class<? extends OAuth2Token>, Token<?>> tokens;
    /**
     * 额外属性
     */
    private Map<String, Object> attributes;

    public Token<OAuth2AccessToken> getAccessToken() {
        return getToken(OAuth2AccessToken.class);
    }

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
        Assert.hasText(name, "Get name cannot be empty");
        return (T) this.attributes.get(name);
    }

    /**
     * Returns a new {@link Builder}, initialized with the provided
     */
    public static Builder withAppId(Long appid) {
        Assert.notNull(appid, "application cannot be null");
        return new Builder(appid);
    }

    /**
     * Returns a new {@link Builder}, initialized with the provided
     */
    public static Builder withId(Long id) {
        Assert.notNull(id, "application cannot be null");
        return new Builder(id);
    }

    /**
     * 返回一个新的 {@link Builder}，该 {@code OAuth2Authorization} 中的值进行初始化。
     */
    public static Builder from(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        return new Builder(authorization.getAppId())
                .id(authorization.getId())
                .userId(authorization.getUserId())
                .authorizationGrantType(authorization.getAuthorizationGrantType())
                .authorizedScopes(authorization.getAuthorizedScopes())
                .tokens(authorization.tokens)
                .attributes(attrs -> attrs.putAll(authorization.getAttributes()));
    }

    @Getter
    @EqualsAndHashCode
    public static class Token<T extends OAuth2Token> implements Serializable {

        private static final long serialVersionUID = 5452476839192606325L;

        protected static final String TOKEN_METADATA_NAMESPACE = "metadata.token.";

        /**
         * 令牌是否失效元数据名称
         */
        public static final String INVALIDATED_METADATA_NAME = TOKEN_METADATA_NAMESPACE.concat("invalidated");

        /**
         * 用于令牌声明的元数据的名称
         */
        public static final String CLAIMS_METADATA_NAME = TOKEN_METADATA_NAMESPACE.concat("claims");

        private final T token;
        /**
         * 返回与令牌关联的元数据。
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
         * 如果令牌已失效（例如，已撤销），则返回 {@code true}。默认值为 {@code false}。
         */
        public boolean isInvalidated() {
            return Boolean.TRUE.equals(getMetadata(INVALIDATED_METADATA_NAME));
        }

        /**
         * 如果令牌已过期，则返回 {@code true}
         */
        public boolean isExpired() {
            return getToken().getExpiresAt() != null && Instant.now().isAfter(getToken().getExpiresAt());
        }

        /**
         * 如果令牌早于可以使用的时间，则返回 {@code true}。
         */
        public boolean isBeforeUse() {
            Instant notBefore = null;
            if (!CollectionUtils.isEmpty(getClaims())) {
                notBefore = (Instant) getClaims().get("nbf");
            }
            return notBefore != null && Instant.now().isBefore(notBefore);
        }

        /**
         * 如果令牌当前处于活跃状态，则返回 {@code true}。
         */
        public boolean isActive() {
            return !isInvalidated() && !isExpired() && !isBeforeUse();
        }

        /**
         * 返回与令牌关联的声明
         */
        @Nullable
        public Map<String, Object> getClaims() {
            return getMetadata(CLAIMS_METADATA_NAME);
        }

        /**
         * 返回与令牌关联的元数据的值。
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

    }

    public static class Builder implements Serializable {

        private static final long serialVersionUID = -5658976968224374315L;
        //标识符
        private Long id;
        // 用户标识
        private Long userId;
        // 注册客户端ID
        private final Long appId;
        // 授权类型
        private AuthorizationGrantType authorizationGrantType;
        // 授权范围
        private Set<String> authorizedScopes = new HashSet<>();
        // tokens
        private Map<Class<? extends OAuth2Token>, Token<?>> tokens = new HashMap<>();
        // 授权相关属性
        private final Map<String, Object> attributes = new HashMap<>();

        protected Builder(Long appId) {
            this.appId = appId;
        }

        /**
         * 设置授权标识符
         */
        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * 设置授权标识符
         */
        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        /**
         * 设置授权类型
         */
        public Builder authorizationGrantType(AuthorizationGrantType authorizationGrantType) {
            this.authorizationGrantType = authorizationGrantType;
            return this;
        }

        /**
         * 设置授权范围
         */
        public Builder authorizedScopes(Set<String> authorizedScopes) {
            this.authorizedScopes = authorizedScopes;
            return this;
        }

        /**
         * 设置认证token
         *
         * @param accessToken the {@link OAuth2AccessToken}
         * @return the {@link Builder}
         */
        public Builder accessToken(OAuth2AccessToken accessToken) {
            return token(accessToken);
        }

        /**
         * 设置刷新token
         *
         * @param refreshToken the {@link OAuth2RefreshToken}
         * @return the {@link Builder}
         */
        public Builder refreshToken(OAuth2RefreshToken refreshToken) {
            return token(refreshToken);
        }

        /**
         * 设置token
         */
        public <T extends OAuth2Token> Builder token(T token) {
            return token(token, metadata -> {

            });
        }

        /**
         * 设置 {@link OAuth2Token token} 并设置元数据信息
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
         * 添加授权相关属性
         */
        public Builder attribute(String name, Object value) {
            Assert.hasText(name, "key cannot be empty");
            Assert.notNull(value, "value cannot be null");
            this.attributes.put(name, value);
            return this;
        }

        /**
         * 添加授权相关属性
         */
        public Builder attributes(Consumer<Map<String, Object>> attributesConsumer) {
            attributesConsumer.accept(this.attributes);
            return this;
        }

        /**
         * 构建 {@link OAuth2Authorization}.
         */
        public OAuth2Authorization build() {
            Assert.notNull(this.authorizationGrantType, "authorizationGrantType cannot be null");
            OAuth2Authorization authorization = new OAuth2Authorization();
            if (null == this.id) {
                Random random = new Random();

                // Generate a random number between 1000 and 9999 (inclusive)
                int randomNumber = 1000 + random.nextInt(9000);
                this.id = (long) randomNumber;
            }
            authorization.id = this.id;
            authorization.userId = this.userId;
            authorization.appId = this.appId;
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

