package com.ziyao.harbor.gateway.core.token;

import lombok.Data;

import java.util.Set;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Data
public class DefaultAccessToken implements AccessToken {


    private String token;

    private String refreshToken;

    private String timestamp;

    private Set<String> scope;

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private String token;

        private String refreshToken;

        private String timestamp;

        private Set<String> scope;
        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public DefaultAccessToken build() {
            DefaultAccessToken accessToken = new DefaultAccessToken();
            accessToken.setToken(token);
            accessToken.setRefreshToken(refreshToken);
            accessToken.setTimestamp(timestamp);
            return accessToken;
        }
    }
}
