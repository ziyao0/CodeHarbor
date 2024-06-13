package com.ziyao.harbor.usercenter.authentication.jackson2;

import com.alibaba.nacos.shaded.com.google.gson.JsonElement;
import com.alibaba.nacos.shaded.com.google.gson.JsonSerializationContext;
import com.alibaba.nacos.shaded.com.google.gson.JsonSerializer;
import com.ziyao.harbor.usercenter.authentication.token.oauth2.RegisteredApp;

import java.lang.reflect.Type;

/**
 * @author ziyao zhang
 * @time 2024/6/13
 */
public class RegisteredAppSerializer implements JsonSerializer<RegisteredApp> {


    @Override
    public JsonElement serialize(RegisteredApp registeredApp, Type type, JsonSerializationContext jsonSerializationContext) {
        return null;
    }
}
