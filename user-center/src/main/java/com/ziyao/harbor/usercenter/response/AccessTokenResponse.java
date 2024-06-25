package com.ziyao.harbor.usercenter.response;

import lombok.Getter;

/**
 * @author ziyao zhang
 * @time 2024/6/15
 */
@Getter
public class AccessTokenResponse {

    private final String tokenType;
    private final String accessToken;
    private final String refreshToken;

    public AccessTokenResponse(String tokenType, String accessToken, String refreshToken) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }


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
