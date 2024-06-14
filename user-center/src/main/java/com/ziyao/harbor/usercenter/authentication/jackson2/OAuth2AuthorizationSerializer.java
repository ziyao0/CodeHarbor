package com.ziyao.harbor.usercenter.authentication.jackson2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.ziyao.security.oauth2.core.OAuth2Authorization;
import com.ziyao.security.oauth2.core.OAuth2AuthorizationCode;
import com.ziyao.security.oauth2.core.OAuth2Token;

import java.io.IOException;
import java.time.Instant;
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

        if (authorization.getRegisteredAppId() != null)
            jsonGenerator.writeNumberField("registeredAppId", authorization.getRegisteredAppId());

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
    }

    private Map<String, Object> writeMap(Map<Class<? extends OAuth2Token>, OAuth2Authorization.Token<?>> tokens) {
        Map<String, Object> map = new HashMap<>();

        for (Map.Entry<Class<? extends OAuth2Token>, OAuth2Authorization.Token<?>> entry : tokens.entrySet()) {

            map.put(entry.getKey().getSimpleName(), entry.getValue().getToken());
        }
        return map;
    }

    public static void main(String[] args) {
        OAuth2AuthorizationCode code = new OAuth2AuthorizationCode("1111", Instant.now(), Instant.now());

    }
}
