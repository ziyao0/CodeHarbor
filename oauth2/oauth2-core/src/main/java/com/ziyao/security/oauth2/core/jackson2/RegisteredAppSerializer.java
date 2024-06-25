package com.ziyao.security.oauth2.core.jackson2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.ziyao.security.oauth2.core.AuthorizationGrantType;
import com.ziyao.security.oauth2.core.RegisteredApp;
import com.ziyao.security.oauth2.core.settings.TokenSettings;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ziyao zhang
 * @time 2024/6/13
 */
public class RegisteredAppSerializer extends JsonSerializer<RegisteredApp> {

    @Override
    public void serialize(RegisteredApp registeredApp, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("appId", registeredApp.getAppId());
        jsonGenerator.writeStringField("appName", registeredApp.getAppName());

        if (registeredApp.getAppType() != null)
            jsonGenerator.writeNumberField("appType", registeredApp.getAppType());

        jsonGenerator.writeArrayFieldStart("authorizationGrantTypes");
        for (AuthorizationGrantType grantType : registeredApp.getAuthorizationGrantTypes()) {
            jsonGenerator.writeString(grantType.value());
        }
        jsonGenerator.writeEndArray();
        if (registeredApp.getState() != null)
            jsonGenerator.writeNumberField("state", registeredApp.getState());

        if (registeredApp.getIssuedAt() != null)
            jsonGenerator.writeStringField("issuedAt", registeredApp.getIssuedAt().toString());
        jsonGenerator.writeStringField("appSecret", registeredApp.getAppSecret());

        if (registeredApp.getAppSecretExpiresAt() != null)
            jsonGenerator.writeStringField("appSecretExpiresAt", registeredApp.getAppSecretExpiresAt().toString());
        jsonGenerator.writeArrayFieldStart("scopes");
        for (String scope : registeredApp.getScopes()) {
            jsonGenerator.writeString(scope);
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeStringField("redirectUri", registeredApp.getRedirectUri());
        jsonGenerator.writeStringField("postLogoutRedirectUri", registeredApp.getPostLogoutRedirectUri());
        // 写入settings
        Map<String, Object> settings = createSettingsMap(registeredApp.getTokenSettings());
        jsonGenerator.writeObjectField("tokenSettings", settings);
        jsonGenerator.writeEndObject();

    }

    private Map<String, Object> createSettingsMap(TokenSettings tokenSettings) {

        Map<String, Object> settings = tokenSettings.getSettings();

        Map<String, Object> settingsMap = new HashMap<>();

        for (Map.Entry<String, Object> entry : settings.entrySet()) {
            // 删除空值
            if (null == entry.getValue()) {
                settings.remove(entry.getKey());
            }

            if (entry.getValue() instanceof Duration duration) {
                settingsMap.put(entry.getKey(), duration.toString());
            } else
                settingsMap.put(entry.getKey(), entry.getValue());
        }
        return settingsMap;
    }
}
