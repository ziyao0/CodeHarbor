package com.ziyao.security.oauth2.jackson2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.security.oauth2.core.OAuth2AccessToken;
import com.ziyao.security.oauth2.core.OAuth2Authorization;
import com.ziyao.security.oauth2.core.OAuth2Token;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ziyao
 * @since 2024/06/14 12:15:53
 */
public class OAuth2AuthorizationSerializer extends JsonSerializer<OAuth2Authorization> {

    @Override
    public void serialize(OAuth2Authorization authorization, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

        if (authorization.getId() != null)
            jsonGenerator.writeNumberField("id", authorization.getId());

        if (authorization.getAppId() != null)
            jsonGenerator.writeNumberField("appId", authorization.getAppId());

        if (authorization.getUserId() != null)
            jsonGenerator.writeNumberField("userId", authorization.getUserId());

        jsonGenerator.writeStringField("authorizationGrantType", authorization.getAuthorizationGrantType().value());

        jsonGenerator.writeArrayFieldStart("authorizedScopes");
        for (String authorizedScope : authorization.getAuthorizedScopes()) {
            jsonGenerator.writeString(authorizedScope);
        }
        jsonGenerator.writeEndArray();


        jsonGenerator.writeObjectField("tokens", writeMap(authorization.getTokens()));

        jsonGenerator.writeObjectField("attributes", authorization.getAttributes());

        jsonGenerator.writeEndObject();
    }

    private Map<String, Object> writeMap(Map<Class<? extends OAuth2Token>, OAuth2Authorization.Token<?>> tokens) {
        Map<String, Object> map = new HashMap<>();

        for (Map.Entry<Class<? extends OAuth2Token>, OAuth2Authorization.Token<?>> entry : tokens.entrySet()) {

            Class<? extends OAuth2Token> key = entry.getKey();

            OAuth2Authorization.Token<?> authorizationToken = entry.getValue();
            OAuth2Token token = authorizationToken.getToken();

            Map<String, Object> metadata = authorizationToken.getMetadata();

            Map<String, String> writeMap = new HashMap<>(Map.of("tokenValue", token.getTokenValue(),
                    "issuedAt", token.getIssuedAt().toString(),
                    "expiresAt", token.getExpiresAt().toString()));

            if (OAuth2AccessToken.class.isAssignableFrom(key)) {
                OAuth2AccessToken accessToken = (OAuth2AccessToken) token;
                writeMap.put("tokenType", accessToken.getTokenType().getValue());
                writeMap.put("scopes", Strings.collectionToCommaDelimitedString(accessToken.getScopes()));
            }
            map.put(key.getSimpleName(), writeMap);
            map.put("metadata", metadata);
        }
        return map;
    }
}
