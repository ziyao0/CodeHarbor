package com.ziyao.security.oauth2.core.jackson2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziyao.harbor.core.utils.Collections;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.security.oauth2.core.AuthorizationGrantType;
import com.ziyao.security.oauth2.core.RegisteredApp;
import com.ziyao.security.oauth2.core.settings.TokenSettings;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * @author ziyao zhang
 * @time 2024/6/13
 */
public class RegisteredAppDeserializer extends JsonDeserializer<RegisteredApp> {

    @Override
    public RegisteredApp deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        ObjectMapper mapper = (ObjectMapper) parser.getCodec();
        JsonNode root = mapper.readTree(parser);
        return deserialize(mapper, root);
    }

    private RegisteredApp deserialize(ObjectMapper mapper, JsonNode jsonNode) {


        return RegisteredApp.withAppId(JsonNodeUtils.findLongValue(jsonNode, "appId"))
                .appName(JsonNodeUtils.findStringValue(jsonNode, "appName"))
                .appType(JsonNodeUtils.findIntegerValue(jsonNode, "appType"))
                .authorizationGrantTypes(authorizationGrantTypes -> {

                    List<String> grantTypes = JsonNodeUtils.findArrayValue(jsonNode, "authorizationGrantTypes");
                    if (Collections.nonNull(grantTypes)) {
                        for (String grantType : grantTypes) {
                            authorizationGrantTypes.add(new AuthorizationGrantType(grantType));
                        }
                    }
                })
                .state(JsonNodeUtils.findIntegerValue(jsonNode, "state"))
                .issuedAt(parseTimestamp(jsonNode, "issuedAt"))
                .appSecret(JsonNodeUtils.findStringValue(jsonNode, "appSecret"))
                .appSecretExpiresAt(parseTimestamp(jsonNode, "appSecretExpiresAt"))
                .scopes(JsonNodeUtils.findValue(jsonNode, "scopes", JsonNodeUtils.STRING_SET, mapper))
                .redirectUri(JsonNodeUtils.findStringValue(jsonNode, "redirectUri"))
                .postLogoutRedirectUris(JsonNodeUtils.findStringValue(jsonNode, "postLogoutRedirectUris"))
                .tokenSettings(
                        createSettings(
                                JsonNodeUtils.findValue(jsonNode, "tokenSettings", JsonNodeUtils.STRING_OBJECT_MAP, mapper)))
                .build();

    }

    private TokenSettings createSettings(Map<String, Object> settings) {

        if (Collections.isEmpty(settings)) {
            return null;
        }

        for (Map.Entry<String, Object> entry : settings.entrySet()) {
            if (entry.getValue() instanceof String value) {
                settings.put(entry.getKey(), Duration.parse(value));
            }
        }

        return TokenSettings.withSettings(settings).build();
    }

    private Instant parseTimestamp(JsonNode node, String fieldName) {

        String value = JsonNodeUtils.findStringValue(node, fieldName);

        return Strings.hasText(value) ? Instant.parse(value) : null;
    }
}
