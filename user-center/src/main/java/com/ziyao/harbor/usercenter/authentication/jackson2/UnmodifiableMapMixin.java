package com.ziyao.harbor.usercenter.authentication.jackson2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Map;

/**
 * @author ziyao zhang
 * @time 2024/6/13
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonDeserialize(using = UnmodifiableMapDeserializer.class)
abstract class UnmodifiableMapMixin {

    @JsonCreator
    UnmodifiableMapMixin(Map<?, ?> map) {
    }

}
