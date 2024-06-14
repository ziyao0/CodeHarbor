package com.ziyao.security.oauth2.jackson2;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ziyao.security.oauth2.settings.OAuth2TokenFormat;

import java.io.Serial;
import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * @author ziyao zhang
 * @time 2024/6/12
 */
public class OAuth2AuthorizationServerJackson2Module extends SimpleModule {
    @Serial
    private static final long serialVersionUID = -3992287885902751251L;

    public OAuth2AuthorizationServerJackson2Module() {
        super(OAuth2AuthorizationServerJackson2Module.class.getName(), new Version(1, 0, 0, null, null, null));
    }

    @Override
    public void setupModule(SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
        context.setMixInAnnotations(Collections.emptyMap().getClass(),
                UnmodifiableMapMixin.class);
        context.setMixInAnnotations(HashSet.class, HashSetMixin.class);
        context.setMixInAnnotations(LinkedHashSet.class, HashSetMixin.class);
        context.setMixInAnnotations(Duration.class, DurationMixin.class);
        context.setMixInAnnotations(OAuth2TokenFormat.class, OAuth2TokenFormatMixin.class);

    }



}
