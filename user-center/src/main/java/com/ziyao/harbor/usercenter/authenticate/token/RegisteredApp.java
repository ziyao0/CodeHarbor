package com.ziyao.harbor.usercenter.authenticate.token;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.core.utils.Collections;
import com.ziyao.security.oauth2.core.AuthorizationGrantType;
import com.ziyao.security.oauth2.settings.TokenSettings;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
@Getter
public class RegisteredApp implements Serializable {

    @Serial
    private static final long serialVersionUID = -5766646592402572432L;

    private Long appId;
    private Integer appType;
    private Set<AuthorizationGrantType> authorizationGrantTypes;
    private Integer state;
    private Instant issuedAt;
    private String appSecret;
    private Instant appSecretExpiresAt;
    private String appName;
    private Set<String> scopes;
    private String redirectUri;
    private String postLogoutRedirectUri;
    private TokenSettings tokenSettings;

    protected RegisteredApp() {
    }

    public static Builder withAppId(Long appid) {
        Assert.notNull(appid, "id cannot be empty");
        return new Builder(appid);
    }

    public static class Builder implements Serializable {

        @Serial
        private static final long serialVersionUID = 6495205880660410126L;
        private Long appId;
        private Integer appType;
        private Set<AuthorizationGrantType> authorizationGrantTypes;
        private Integer state;
        private Instant issuedAt;
        private String appSecret;
        private Instant appSecretExpiresAt;
        private String appName;
        private Set<String> scopes;
        private String redirectUri;
        private String postLogoutRedirectUri;
        private TokenSettings tokenSettings;

        protected Builder(Long appId) {
            this.appId = appId;
        }

        protected Builder(RegisteredApp registeredApp) {

            this.appId = registeredApp.getAppId();
            this.appType = registeredApp.getAppType();
            if (!Collections.isEmpty(registeredApp.getAuthorizationGrantTypes())) {
                this.authorizationGrantTypes = registeredApp.getAuthorizationGrantTypes();
            }
            this.state = registeredApp.getState();
            this.issuedAt = registeredApp.getIssuedAt();
            this.appSecret = registeredApp.getAppSecret();
            this.appSecretExpiresAt = registeredApp.getAppSecretExpiresAt();
            this.appName = registeredApp.getAppName();
            if (!Collections.isEmpty(registeredApp.getScopes())) {
                this.scopes = registeredApp.getScopes();
            }
            this.redirectUri = registeredApp.getRedirectUri();
            this.postLogoutRedirectUri = registeredApp.getPostLogoutRedirectUri();

            this.tokenSettings = TokenSettings.withSettings(registeredApp.getTokenSettings().getSettings()).build();
        }

        public Builder appId(Long appId) {
            this.appId = appId;
            return this;
        }

        public Builder appType(Integer appType) {
            this.appType = appType;
            return this;
        }

        public Builder authorizationGrantTypes(Set<AuthorizationGrantType> authorizationGrantTypes) {
            this.authorizationGrantTypes = authorizationGrantTypes;
            return this;
        }

        public Builder authorizationGrantTypes(Consumer<Set<AuthorizationGrantType>> authorizationGrantTypesConsumer) {
            authorizationGrantTypesConsumer.accept(this.authorizationGrantTypes);
            return this;
        }

        public Builder state(Integer state) {
            this.state = state;
            return this;
        }

        public Builder issuedAt(Instant issuedAt) {
            this.issuedAt = issuedAt;
            return this;
        }

        public Builder appSecret(String appSecret) {
            this.appSecret = appSecret;
            return this;
        }

        public Builder appSecretExpiresAt(Instant appSecretExpiresAt) {
            this.appSecretExpiresAt = appSecretExpiresAt;
            return this;
        }

        public Builder appName(String appName) {
            this.appName = appName;
            return this;
        }

        public Builder scopes(Set<String> scopes) {
            this.scopes = scopes;
            return this;
        }

        public Builder scopes(Consumer<Set<String>> scopesConsumer) {
            scopesConsumer.accept(this.scopes);
            return this;
        }


        public Builder redirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public Builder postLogoutRedirectUris(String postLogoutRedirectUri) {
            this.postLogoutRedirectUri = postLogoutRedirectUri;
            return this;
        }

        public Builder tokenSettings(TokenSettings tokenSettings) {
            this.tokenSettings = tokenSettings;
            return this;
        }

        public RegisteredApp build() {
            Assert.notNull(this.appId, "clientId cannot be empty");
            Assert.notNull(this.authorizationGrantTypes, "authorizationGrantTypes cannot be empty");
            if (this.authorizationGrantTypes.contains(AuthorizationGrantType.AUTHORIZATION_CODE)) {
                Assert.notNull(this.redirectUri, "redirectUris cannot be empty");
            }
            if (this.tokenSettings == null) {
                this.tokenSettings = TokenSettings.builder().build();
            }
            return create();
        }

        private @NotNull RegisteredApp create() {
            RegisteredApp registeredApp = new RegisteredApp();
            registeredApp.appId = this.appId;
            registeredApp.appType = this.appType;
            registeredApp.authorizationGrantTypes = Set.copyOf(this.authorizationGrantTypes);
            registeredApp.state = this.state;
            registeredApp.issuedAt = this.issuedAt;
            registeredApp.appSecret = this.appSecret;
            registeredApp.appSecretExpiresAt = this.appSecretExpiresAt;
            registeredApp.appName = this.appName;
            registeredApp.scopes = Set.copyOf(this.scopes);
            registeredApp.redirectUri = this.redirectUri;
            registeredApp.postLogoutRedirectUri = this.postLogoutRedirectUri;
            registeredApp.tokenSettings = this.tokenSettings;
            return registeredApp;
        }
    }
}
