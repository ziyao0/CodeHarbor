package com.ziyao.security.oauth2.token;

import com.ziyao.security.oauth2.converter.ClaimConversionService;
import lombok.Getter;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.Assert;

import java.net.URL;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
@Getter
public class OAuth2TokenClaimsSet {


    private final Map<String, Object> claims;

    private OAuth2TokenClaimsSet(Map<String, Object> claims) {
        this.claims = Map.copyOf(claims);
    }


    public URL getIssuer() {
        return getClaimAsURL(OAuth2TokenClaimNames.ISS);
    }


    public String getSubject() {
        return getClaimAsString(OAuth2TokenClaimNames.SUB);
    }


    public List<String> getAudience() {
        return getClaimAsStringList(OAuth2TokenClaimNames.AUD);
    }


    public Instant getExpiresAt() {
        return getClaimAsInstant(OAuth2TokenClaimNames.EXP);
    }


    public Instant getNotBefore() {
        return getClaimAsInstant(OAuth2TokenClaimNames.NBF);
    }

    public Instant getIssuedAt() {
        return getClaimAsInstant(OAuth2TokenClaimNames.IAT);
    }

    public String getId() {
        return getClaimAsString(OAuth2TokenClaimNames.JTI);
    }


    @SuppressWarnings("unchecked")
    public <T> T getClaim(String claim) {
        return hasClaim(claim) ? null : (T) getClaims().get(claim);
    }


    public boolean hasClaim(String claim) {
        Assert.notNull(claim, "claim cannot be null");
        return !getClaims().containsKey(claim);
    }


    public String getClaimAsString(String claim) {
        return hasClaim(claim) ? null
                : ClaimConversionService.getSharedInstance().convert(getClaims().get(claim), String.class);
    }


    public Boolean getClaimAsBoolean(String claim) {
        if (hasClaim(claim)) {
            return null;
        }
        Object claimValue = getClaims().get(claim);
        Boolean convertedValue = ClaimConversionService.getSharedInstance().convert(claimValue, Boolean.class);
        Assert.notNull(convertedValue,
                () -> "Unable to convert claim '" + claim + "' of type '" + claimValue.getClass() + "' to Boolean.");
        return convertedValue;
    }

    public Instant getClaimAsInstant(String claim) {
        if (hasClaim(claim)) {
            return null;
        }
        Object claimValue = getClaims().get(claim);
        Instant convertedValue = ClaimConversionService.getSharedInstance().convert(claimValue, Instant.class);
        Assert.notNull(convertedValue,
                () -> "Unable to convert claim '" + claim + "' of type '" + claimValue.getClass() + "' to Instant.");
        return convertedValue;
    }

    public URL getClaimAsURL(String claim) {
        if (hasClaim(claim)) {
            return null;
        }
        Object claimValue = getClaims().get(claim);
        URL convertedValue = ClaimConversionService.getSharedInstance().convert(claimValue, URL.class);
        Assert.notNull(convertedValue,
                () -> "Unable to convert claim '" + claim + "' of type '" + claimValue.getClass() + "' to URL.");
        return convertedValue;
    }

    @SuppressWarnings("unchecked")
    public List<String> getClaimAsStringList(String claim) {
        if (hasClaim(claim)) {
            return null;
        }
        final TypeDescriptor sourceDescriptor = TypeDescriptor.valueOf(Object.class);
        final TypeDescriptor targetDescriptor = TypeDescriptor.collection(List.class,
                TypeDescriptor.valueOf(String.class));
        Object claimValue = getClaims().get(claim);
        List<String> convertedValue = (List<String>) ClaimConversionService.getSharedInstance()
                .convert(claimValue, sourceDescriptor, targetDescriptor);
        Assert.notNull(convertedValue,
                () -> "Unable to convert claim '" + claim + "' of type '" + claimValue.getClass() + "' to List.");
        return convertedValue;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final Map<String, Object> claims = new HashMap<>();

        private Builder() {
        }


        public Builder issuer(String issuer) {
            return claim(OAuth2TokenClaimNames.ISS, issuer);
        }

        /**
         * 设置主题 {@code （sub）} 声明，该声明标识作为 OAuth 2.0 令牌主题的主体
         */
        public Builder subject(String subject) {
            return claim(OAuth2TokenClaimNames.SUB, subject);
        }

        /**
         * 设置受众 {@code （aud）} 声明，用于标识 OAuth 2.0 令牌的目标收件人
         */
        public Builder audience(List<String> audience) {
            return claim(OAuth2TokenClaimNames.AUD, audience);
        }

        /**
         * 设置过期时间 {@code （exp）} 声明，该声明标识不得接受 OAuth 2.0 令牌进行处理的时间或之后的时间。
         */
        public Builder expiresAt(Instant expiresAt) {
            return claim(OAuth2TokenClaimNames.EXP, expiresAt);
        }

        /**
         * 设置 not before {@code （nbf）} 声明，该声明标识不得接受 OAuth 2.0 令牌进行处理的时间。
         *
         * @param notBefore 不得接受 OAuth 2.0 令牌进行处理的时间
         * @return the {@link Builder}
         */
        public Builder notBefore(Instant notBefore) {
            return claim(OAuth2TokenClaimNames.NBF, notBefore);
        }

        /**
         * 设置颁发于 {@code （iat）} 声明，该声明标识颁发 OAuth 2.0 令牌的时间
         *
         * @param issuedAt OAuth 2.0 令牌的颁发时间
         * @return the {@link Builder}
         */
        public Builder issuedAt(Instant issuedAt) {
            return claim(OAuth2TokenClaimNames.IAT, issuedAt);
        }

        /**
         * 设置 ID {@code （jti）} 声明，该声明为 OAuth 2.0 令牌提供唯一标识符。
         *
         * @param jti OAuth 2.0 令牌的唯一标识符
         * @return the {@link Builder}
         */
        public Builder id(String jti) {
            return claim(OAuth2TokenClaimNames.JTI, jti);
        }

        /**
         * 设置声明。
         *
         * @param name  name
         * @param value value
         * @return the {@link Builder}
         */
        public Builder claim(String name, Object value) {
            Assert.hasText(name, "name cannot be empty");
            Assert.notNull(value, "value cannot be null");
            this.claims.put(name, value);
            return this;
        }

        /**
         * 向 {@code Consumer} 提供对声明的访问权限
         *
         * @param claimsConsumer a {@code Consumer} of the claims
         * @return the {@link Builder}
         */
        public Builder claims(Consumer<Map<String, Object>> claimsConsumer) {
            claimsConsumer.accept(this.claims);
            return this;
        }

        /**
         * Builds a new {@link OAuth2TokenClaimsSet}.
         *
         * @return a {@link OAuth2TokenClaimsSet}
         */
        public OAuth2TokenClaimsSet build() {
            Assert.notEmpty(this.claims, "claims cannot be empty");

            // The value of the 'iss' claim is a String or URL (StringOrURI).
            // Attempt to convert to URL.
            Object issuer = this.claims.get(OAuth2TokenClaimNames.ISS);
            if (issuer != null) {
                URL convertedValue = ClaimConversionService.getSharedInstance().convert(issuer, URL.class);
                if (convertedValue != null) {
                    this.claims.put(OAuth2TokenClaimNames.ISS, convertedValue);
                }
            }

            return new OAuth2TokenClaimsSet(this.claims);
        }

    }
}
