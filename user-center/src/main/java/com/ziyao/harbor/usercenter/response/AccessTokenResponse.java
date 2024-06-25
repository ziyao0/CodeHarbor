package com.ziyao.harbor.usercenter.response;

/**
 * @author ziyao zhang
 * @time 2024/6/15
 */
public record AccessTokenResponse(String tokenType, String accessToken, String refreshToken) {

    public static AccessTokenResponse create(String tokenType, String accessToken, String refreshToken) {
        return new AccessTokenResponse(tokenType, accessToken, refreshToken);
    }

    public static Builder witchTokenType(String tokenType) {
        return new Builder(tokenType);
    }

    public static class Builder {

        private final String tokenType;

        private String accessToken;

        private String refreshToken;

        public Builder(String tokenType) {
            this.tokenType = tokenType;
        }

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public AccessTokenResponse build() {
            return AccessTokenResponse.create(this.tokenType, this.accessToken, this.refreshToken);
        }
    }
}
