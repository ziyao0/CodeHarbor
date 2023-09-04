package com.ziyao.harbor.core.token;

import java.util.Date;
import java.util.Map;

/**
 * @author ziyao zhang
 * @since 2023/8/29
 */
public class JwtInfo implements TokenDetails {

    private TokenType tokenType;
    private JwtHeader jwtHeader;
    private Map<String, Object> payload;
    private Date expiresAt;
    private String secret;

    public static Builder builder() {
        return new Builder();
    }

    public static class JwtHeader implements Header {
        public JwtHeader() {
            this.issuer = "ziyao";
            this.subject = "harbor";
        }

        public JwtHeader(String issuer, String subject, String... audience) {
            this.issuer = issuer;
            this.subject = subject;
            this.audience = audience;
        }

        private String issuer;

        private String subject;

        private String[] audience;

        @Override
        public String getIssuer() {
            return this.issuer;
        }

        @Override
        public String getSubject() {
            return this.subject;
        }

        @Override
        public String[] getAudience() {
            return this.audience;
        }
    }

    @Override
    public TokenType getTokenType() {
        return this.tokenType;
    }

    @Override
    public Header getHeader() {
        return this.jwtHeader;
    }

    @Override
    public Map<String, Object> getPayload() {
        return this.payload;
    }

    @Override
    public Date getExpiresAt() {
        return this.expiresAt;
    }

    @Override
    public String getSecret() {
        return this.secret;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public void setJwtHeader(JwtHeader jwtHeader) {
        this.jwtHeader = jwtHeader;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public static class Builder {
        private TokenType tokenType;
        private JwtHeader jwtHeader;
        private Map<String, Object> payload;
        private Date expiresAt;
        private String secret;

        public Builder tokenType(TokenType tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public Builder jwtHeader(JwtHeader jwtHeader) {
            this.jwtHeader = jwtHeader;
            return this;
        }

        public Builder payload(Map<String, Object> payload) {
            this.payload = payload;
            return this;
        }

        public Builder expiresAt(Date expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public Builder secret(String secret) {
            this.secret = secret;
            return this;
        }

        public JwtInfo build() {
            JwtInfo jwtInfo = new JwtInfo();
            jwtInfo.setJwtHeader(this.jwtHeader);
            jwtInfo.setTokenType(this.tokenType);
            jwtInfo.setPayload(this.payload);
            jwtInfo.setSecret(this.secret);
            jwtInfo.setExpiresAt(this.expiresAt);
            return jwtInfo;
        }
    }
}
