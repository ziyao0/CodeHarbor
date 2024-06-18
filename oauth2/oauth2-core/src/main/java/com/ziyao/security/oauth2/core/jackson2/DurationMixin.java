package com.ziyao.security.oauth2.core.jackson2;

import com.fasterxml.jackson.annotation.*;

/**
 * @author ziyao zhang
 * @time 2024/6/12
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE,
        creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class DurationMixin {

    @JsonCreator
    static void ofSeconds(@JsonProperty("seconds") long seconds, @JsonProperty("nano") long nanoAdjustment) {
    }

    @JsonGetter("seconds")
    abstract long getSeconds();

    @JsonGetter("nano")
    abstract int getNano();

}