package com.ziyao.harbor.usercenter.authentication.jackson2;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.ziyao.security.oauth2.core.OAuth2Authorization;

import java.io.IOException;

/**
 * @author ziyao
 * @since 2024/06/14 12:16:33
 */
public class OAuth2AuthorizationDeserializer extends JsonDeserializer<OAuth2Authorization> {


    @Override
    public OAuth2Authorization deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return null;
    }
}
