package com.ziyao.security.oauth2.core.jackson2;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.security.oauth2.core.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author ziyao
 * @since 2024/06/14 12:16:33
 */
public class OAuth2AuthorizationDeserializer extends JsonDeserializer<OAuth2Authorization> {

    private static final Log log = LogFactory.getLog(OAuth2AuthorizationDeserializer.class);

    @Override
    public OAuth2Authorization deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode root = mapper.readTree(jsonParser);

        return deserialize(mapper, root);
    }

    @SuppressWarnings("unchecked")
    private OAuth2Authorization deserialize(ObjectMapper mapper, JsonNode jsonNode) {
        // @formatter:off
        OAuth2Authorization.Builder builder = OAuth2Authorization.withAppId(
                JsonNodeUtils.findLongValue(jsonNode, "appId"))
                .id(JsonNodeUtils.findLongValue(jsonNode, "id"))
                .userId(JsonNodeUtils.findLongValue(jsonNode, "userId"))
                .authorizationGrantType(
                        Optional.ofNullable(JsonNodeUtils.findStringValue(jsonNode, "authorizationGrantType"))
                                .map(AuthorizationGrantType::new)
                                .orElse(null)
                )
                .authorizedScopes(
                        JsonNodeUtils.findValue(jsonNode, "authorizedScopes", JsonNodeUtils.STRING_SET, mapper)
                )
                .attributes(
                        att -> att.putAll(
                                Optional.ofNullable(JsonNodeUtils.findValue(jsonNode, "attributes", JsonNodeUtils.STRING_OBJECT_MAP, mapper))
                                        .orElse(Map.of()))
                );

        Map<String, Object> tokens = JsonNodeUtils.findValue(jsonNode, "tokens", JsonNodeUtils.STRING_OBJECT_MAP, mapper);

        for (Map.Entry<String, Object> entry : tokens.entrySet()) {
            String tokenClassName = entry.getKey();

            Map<String, String> tokenMap = (Map<String, String>) entry.getValue();

            String tokenValue = tokenMap.get("tokenValue");
            Instant issuedAt = Optional.ofNullable(tokenMap.get("issuedAt")).map(Instant::parse).orElse(null);
            Instant expiresAt = Optional.ofNullable(tokenMap.get("expiresAt")).map(Instant::parse).orElse(null);
            Map<String,Object> metadata = Optional.ofNullable(tokenMap.get("metadata")).map(m->mapper.convertValue(m, JsonNodeUtils.STRING_OBJECT_MAP)).orElse(Map.of());

            if (OAuth2AuthorizationCode.class.getSimpleName().equals(tokenClassName)) {
                // 创建授权码
                builder.token(new OAuth2AuthorizationCode(tokenValue, issuedAt, expiresAt),md-> md.putAll(metadata));
            } else if (OAuth2RefreshToken.class.getSimpleName().equals(tokenClassName)) {
                // 创建刷新token
                builder.token(new OAuth2RefreshToken(tokenValue, issuedAt, expiresAt),md-> md.putAll(metadata));
            } else if (OAuth2AccessToken.class.getSimpleName().equals(tokenClassName)) {
                // 创建认证token
                OAuth2AccessToken.TokenType tokenType = Optional.ofNullable(tokenMap.get("tokenType"))
                        .map(OAuth2AccessToken.TokenType::valueOf)
                        .orElse(null);

                Set<String> scopes = Optional.ofNullable(tokenMap.get("scopes"))
                        .map(Strings::commaDelimitedListToSet)
                        .orElse(Set.of());
                builder.token(new OAuth2AccessToken(tokenType, tokenValue, issuedAt, expiresAt, scopes),md-> md.putAll(metadata));
            }
        }
        return builder.build();
        // @formatter:on
    }
}
