package com.ziyao.security.oauth2.core;

import com.ziyao.security.oauth2.core.oidc.IdTokenClaimAccessor;
import com.ziyao.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author ziyao
 * @since 2024/06/19 14:42:55
 */
public class OidcIdToken extends AbstractOAuth2Token implements IdTokenClaimAccessor {


    private final Map<String, Object> claims;

    public OidcIdToken(String tokenValue, Instant issuedAt, Instant expiresAt, Map<String, Object> claims) {
        super(tokenValue, issuedAt, expiresAt);
        this.claims = claims;
    }


    public static Builder withTokenValue(String tokenValue) {
        return new Builder(tokenValue);
    }


    @Override
    public Map<String, Object> getClaims() {
        return this.claims;
    }


    public static final class Builder {

        private String tokenValue;

        private final Map<String, Object> claims = new LinkedHashMap<>();

        private Builder(String tokenValue) {
            this.tokenValue = tokenValue;
        }


        public Builder tokenValue(String tokenValue) {
            this.tokenValue = tokenValue;
            return this;
        }


        public Builder claim(String name, Object value) {
            this.claims.put(name, value);
            return this;
        }


        public Builder claims(Consumer<Map<String, Object>> claimsConsumer) {
            claimsConsumer.accept(this.claims);
            return this;
        }


        public Builder accessTokenHash(String accessTokenHash) {
            return claim(IdTokenClaimNames.AT_HASH, accessTokenHash);
        }


        public Builder audience(Collection<String> audience) {
            return claim(IdTokenClaimNames.AUD, audience);
        }


        public Builder authTime(Instant authenticatedAt) {
            return claim(IdTokenClaimNames.AUTH_TIME, authenticatedAt);
        }


        public Builder authenticationContextClass(String authenticationContextClass) {
            return claim(IdTokenClaimNames.ACR, authenticationContextClass);
        }


        public Builder authenticationMethods(List<String> authenticationMethods) {
            return claim(IdTokenClaimNames.AMR, authenticationMethods);
        }


        public Builder authorizationCodeHash(String authorizationCodeHash) {
            return claim(IdTokenClaimNames.C_HASH, authorizationCodeHash);
        }


        public Builder authorizedParty(String authorizedParty) {
            return claim(IdTokenClaimNames.AZP, authorizedParty);
        }


        public Builder expiresAt(Instant expiresAt) {
            return this.claim(IdTokenClaimNames.EXP, expiresAt);
        }


        public Builder issuedAt(Instant issuedAt) {
            return this.claim(IdTokenClaimNames.IAT, issuedAt);
        }


        public Builder issuer(String issuer) {
            return this.claim(IdTokenClaimNames.ISS, issuer);
        }


        public Builder nonce(String nonce) {
            return this.claim(IdTokenClaimNames.NONCE, nonce);
        }


        public Builder subject(String subject) {
            return this.claim(IdTokenClaimNames.SUB, subject);
        }

        /**
         * Build the {@link OidcIdToken}
         *
         * @return The constructed {@link OidcIdToken}
         */
        public OidcIdToken build() {
            Instant iat = toInstant(this.claims.get(IdTokenClaimNames.IAT));
            Instant exp = toInstant(this.claims.get(IdTokenClaimNames.EXP));
            return new OidcIdToken(this.tokenValue, iat, exp, this.claims);
        }

        private Instant toInstant(Object timestamp) {
            if (timestamp != null) {
                Assert.isInstanceOf(Instant.class, timestamp, "timestamps must be of type Instant");
            }
            return (Instant) timestamp;
        }

    }
}
