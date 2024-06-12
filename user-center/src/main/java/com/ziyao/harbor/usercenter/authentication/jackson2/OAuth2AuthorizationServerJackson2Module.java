package com.ziyao.harbor.usercenter.authentication.jackson2;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.IOException;
import java.io.Serial;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
        enableDefaultTyping(context.getOwner());
        context.setMixInAnnotations(Duration.class, DurationMixin.class);

    }

    public static void enableDefaultTyping(ObjectMapper mapper) {
        if (mapper != null) {
            TypeResolverBuilder<?> typeBuilder = mapper.getDeserializationConfig().getDefaultTyper(null);
            if (typeBuilder == null) {
                mapper.setDefaultTyping(createAllowlistedDefaultTyping());
            }
        }
    }

    private static TypeResolverBuilder<? extends TypeResolverBuilder> createAllowlistedDefaultTyping() {
        TypeResolverBuilder<? extends TypeResolverBuilder> result = new AllowlistTypeResolverBuilder(
                ObjectMapper.DefaultTyping.NON_FINAL);
        result = result.init(JsonTypeInfo.Id.CLASS, null);
        result = result.inclusion(JsonTypeInfo.As.PROPERTY);
        return result;
    }

    static class AllowlistTypeResolverBuilder extends ObjectMapper.DefaultTypeResolverBuilder {

        AllowlistTypeResolverBuilder(ObjectMapper.DefaultTyping defaultTyping) {
            super(defaultTyping,
                    // we do explicit validation in the TypeIdResolver
                    BasicPolymorphicTypeValidator.builder().allowIfSubType(Object.class).build());
        }

        @Override
        protected TypeIdResolver idResolver(MapperConfig<?> config, JavaType baseType,
                                            PolymorphicTypeValidator subtypeValidator, Collection<NamedType> subtypes, boolean forSer,
                                            boolean forDeser) {
            TypeIdResolver result = super.idResolver(config, baseType, subtypeValidator, subtypes, forSer, forDeser);
            return new AllowlistTypeIdResolver(result);
        }

    }

    static class AllowlistTypeIdResolver implements TypeIdResolver {

        private static final Set<String> ALLOWLIST_CLASS_NAMES;

        static {
            Set<String> names = new HashSet<>();
            names.add("java.util.ArrayList");
            names.add("java.util.Collections$EmptyList");
            names.add("java.util.Collections$EmptyMap");
            names.add("java.util.Collections$UnmodifiableRandomAccessList");
            names.add("java.util.Collections$SingletonList");
            names.add("java.util.Date");
            names.add("java.time.Instant");
            names.add("java.net.URL");
            names.add("java.util.TreeMap");
            names.add("java.util.HashMap");
            names.add("java.util.LinkedHashMap");
            names.add("org.springframework.security.core.context.SecurityContextImpl");
            names.add("java.util.Arrays$ArrayList");
            ALLOWLIST_CLASS_NAMES = Collections.unmodifiableSet(names);
        }

        private final TypeIdResolver delegate;

        AllowlistTypeIdResolver(TypeIdResolver delegate) {
            this.delegate = delegate;
        }

        @Override
        public void init(JavaType baseType) {
            this.delegate.init(baseType);
        }

        @Override
        public String idFromValue(Object value) {
            return this.delegate.idFromValue(value);
        }

        @Override
        public String idFromValueAndType(Object value, Class<?> suggestedType) {
            return this.delegate.idFromValueAndType(value, suggestedType);
        }

        @Override
        public String idFromBaseType() {
            return this.delegate.idFromBaseType();
        }

        @Override
        public JavaType typeFromId(DatabindContext context, String id) throws IOException {
            DeserializationConfig config = (DeserializationConfig) context.getConfig();
            JavaType result = this.delegate.typeFromId(context, id);
            String className = result.getRawClass().getName();
            if (isInAllowlist(className)) {
                return result;
            }
            boolean isExplicitMixin = config.findMixInClassFor(result.getRawClass()) != null;
            if (isExplicitMixin) {
                return result;
            }
            JacksonAnnotation jacksonAnnotation = AnnotationUtils.findAnnotation(result.getRawClass(),
                    JacksonAnnotation.class);
            if (jacksonAnnotation != null) {
                return result;
            }
            throw new IllegalArgumentException("The class with " + id + " and name of " + className
                    + " is not in the allowlist. "
                    + "If you believe this class is safe to deserialize, please provide an explicit mapping using Jackson annotations or by providing a Mixin. "
                    + "If the serialization is only done by a trusted source, you can also enable default typing. "
                    + "See https://github.com/spring-projects/spring-security/issues/4370 for details");
        }

        private boolean isInAllowlist(String id) {
            return ALLOWLIST_CLASS_NAMES.contains(id);
        }

        @Override
        public String getDescForKnownTypeIds() {
            return this.delegate.getDescForKnownTypeIds();
        }

        @Override
        public JsonTypeInfo.Id getMechanism() {
            return this.delegate.getMechanism();
        }

    }

}
