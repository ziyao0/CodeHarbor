package com.ziyao.harbor.usercenter.authentication.jackson2;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.ziyao.harbor.usercenter.authentication.token.oauth2.RegisteredApp;

import java.io.IOException;

/**
 * @author ziyao zhang
 * @time 2024/6/13
 */
public class RegisteredAppDeserializer extends JsonDeserializer<RegisteredApp> {

    @Override
    public RegisteredApp deserialize(JsonParser p, DeserializationContext context) throws IOException, JacksonException {
        return null;
    }
}
