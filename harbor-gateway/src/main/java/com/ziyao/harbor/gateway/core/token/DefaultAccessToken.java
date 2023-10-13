package com.ziyao.harbor.gateway.core.token;

import lombok.Data;


/**
 * @author ziyao
 * @since 2023/4/23
 */
@Data
public class DefaultAccessToken implements AccessToken {
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

        public DefaultAccessToken build() {
            DefaultAccessToken defaultAccessToken = new DefaultAccessToken();
            defaultAccessToken.setToken(token);
            defaultAccessToken.setApi(api);
            defaultAccessToken.setIp(ip);
            defaultAccessToken.setRefreshToken(refreshToken);
            defaultAccessToken.setTimestamp(timestamp);
            defaultAccessToken.setDigest(digest);
            defaultAccessToken.setResource(resource);
            defaultAccessToken.setName(name);
            return defaultAccessToken;
        }
    }
}
