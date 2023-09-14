package com.ziyao.harbor.gateway.core.token;

import lombok.Data;

import java.io.Serial;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Data
public class AccessControl implements AccessToken {
    @Serial
    private static final long serialVersionUID = -3447048378569999827L;
    private String ip;
    private String resource;
    private String api;
    private String timestamp;
    private String digest;
    private String token;
    private String refreshToken;
    private String name;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String ip;
        private String resource;
        private String api;
        private String timestamp;
        private String digest;
        private String token;
        private String refreshToken;
        private String name;

        public Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder api(String api) {
            this.api = api;
            return this;
        }

        public Builder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder digest(String digest) {
            this.digest = digest;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder resource(String resource) {
            this.resource = resource;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public AccessControl build() {
            AccessControl accessControl = new AccessControl();
            accessControl.setToken(token);
            accessControl.setApi(api);
            accessControl.setIp(ip);
            accessControl.setRefreshToken(refreshToken);
            accessControl.setTimestamp(timestamp);
            accessControl.setDigest(digest);
            accessControl.setResource(resource);
            accessControl.setName(name);
            return accessControl;
        }
    }
}
