package com.ziyao.security.oauth2.core;//package com.ziyao.oauth2.core;
//
//import lombok.Setter;
//
//import java.io.Serial;
//import java.io.Serializable;
//import java.util.*;
//
///**
// * @author ziyao zhang
// * @since 2024/2/27
// */
//public class DefaultOAuth2AccessToken implements Serializable, OAuth2AccessToken {
//    @Serial
//    private static final long serialVersionUID = -8765695009206119151L;
//
//    @Setter
//    private String value;
//
//    /**
//     * -- SETTER --
//     * The instant the token expires.
//     * <p>
//     * expiration The instant the token expires.
//     */
//    @Setter
//    private Date expiration;
//
//    /**
//     * -- SETTER --
//     * The token type, as introduced in draft 11 of the OAuth 2 spec.
//     * <p>
//     * tokenType The token type, as introduced in draft 11 of the OAuth 2 spec.
//     */
//    @Setter
//    private String tokenType = BEARER_TYPE.toLowerCase();
//
//    /**
//     * -- SETTER --
//     * The refresh token associated with the access token, if any.
//     * <p>
//     * refreshToken The refresh token associated with the access token, if any.
//     */
//    @Setter
//    private OAuth2RefreshToken refreshToken;
//
//    /**
//     * -- SETTER --
//     * The scope of the token.
//     * <p>
//     * scope The scope of the token.
//     */
//    @Setter
//    private Set<String> scope;
//
//    private Map<String, Object> additionalInformation = Collections.emptyMap();
//
//    /**
//     * Create an access token from the value provided.
//     */
//    public DefaultOAuth2AccessToken(String value) {
//        this.value = value;
//    }
//
//    /**
//     * Private constructor for JPA and other serialization tools.
//     */
//    @SuppressWarnings("unused")
//    private DefaultOAuth2AccessToken() {
//        this((String) null);
//    }
//
//    /**
//     * Copy constructor for access token.
//     *
//     * @param accessToken 访问令牌
//     */
//    public DefaultOAuth2AccessToken(OAuth2AccessToken accessToken) {
//        this(accessToken.getValue());
//        setAdditionalInformation(accessToken.getAdditionalInformation());
//        setRefreshToken(accessToken.getRefreshToken());
//        setExpiration(accessToken.getExpiration());
//        setScope(accessToken.getScope());
//        setTokenType(accessToken.getTokenType());
//    }
//
//    @Override
//    public Map<String, Object> getAdditionalInformation() {
//        return this.additionalInformation;
//    }
//
//    /**
//     * Additional information that token granters would like to add to the token, e.g. to support new token types. If
//     * the values in the map are primitive then remote communication is going to always work. It should also be safe to
//     * use maps (nested if desired), or something that is explicitly serializable by Jackson.
//     *
//     * @param additionalInformation the additional information to set
//     */
//    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
//        this.additionalInformation = new LinkedHashMap<>(additionalInformation);
//    }
//
//    @Override
//    public Set<String> getScope() {
//        return scope;
//    }
//
//    @Override
//    public OAuth2RefreshToken getRefreshToken() {
//        return refreshToken;
//    }
//
//    @Override
//    public String getTokenType() {
//        return tokenType;
//    }
//
//
//    @Override
//    public boolean isExpired() {
//        return expiration != null && expiration.before(new Date());
//    }
//
//    @Override
//    public Date getExpiration() {
//        return expiration;
//    }
//
//    @Override
//    public int getExpiresIn() {
//        return expiration != null
//                ? Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L).intValue()
//                : 0;
//    }
//
//    @Override
//    public String getValue() {
//        return value;
//    }
//
//    public static OAuth2AccessToken valueOf(Map<String, String> tokenParams) {
//        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(tokenParams.get(ACCESS_TOKEN));
//
//        if (tokenParams.containsKey(EXPIRES_IN)) {
//            long expiration = 0;
//            try {
//                expiration = Long.parseLong(String.valueOf(tokenParams.get(EXPIRES_IN)));
//            } catch (NumberFormatException e) {
//                // fall through...
//            }
//            token.setExpiration(new Date(System.currentTimeMillis() + (expiration * 1000L)));
//        }
//
//        if (tokenParams.containsKey(REFRESH_TOKEN)) {
//            String refresh = tokenParams.get(REFRESH_TOKEN);
//            DefaultOAuth2RefreshToken refreshToken = new DefaultOAuth2RefreshToken(refresh);
//            token.setRefreshToken(refreshToken);
//        }
//
//        if (tokenParams.containsKey(SCOPE)) {
//            Set<String> scope = new TreeSet<>();
//            for (StringTokenizer tokenizer = new StringTokenizer(tokenParams.get(SCOPE), " ,"); tokenizer
//                    .hasMoreTokens(); ) {
//                scope.add(tokenizer.nextToken());
//            }
//            token.setScope(scope);
//        }
//
//        if (tokenParams.containsKey(TOKEN_TYPE)) {
//            token.setTokenType(tokenParams.get(TOKEN_TYPE));
//        }
//
//        return token;
//    }
//}
