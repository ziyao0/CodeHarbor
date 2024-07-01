package com.ziyao.security.oauth2.core.jackson2;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.log.LogMessage;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author ziyao zhang
 * @time 2024/6/13
 */
public final class Jackson2Modules {


    private static final Log logger = LogFactory.getLog(Jackson2Modules.class);

    private static final String javaTimeJackson2ModuleClass = "com.fasterxml.jackson.datatype.jsr310.JavaTimeModule";

    private static final boolean javaTimeJacksonPresent;

    static {

        ClassLoader classLoader = Jackson2Modules.class.getClassLoader();

        javaTimeJacksonPresent = ClassUtils.isPresent(javaTimeJackson2ModuleClass, classLoader);
    }


    public Jackson2Modules() {
    }

    public static void enableDefaultTyping(ObjectMapper mapper) {
        if (mapper != null) {
            TypeResolverBuilder<?> typeBuilder = mapper.getDeserializationConfig().getDefaultTyper(null);
            if (typeBuilder == null) {
                mapper.setDefaultTyping(createAllowlistedDefaultTyping());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static Module loadAndGetInstance(String className, ClassLoader loader) {
        try {
            Class<? extends Module> securityModule = (Class<? extends Module>) ClassUtils.forName(className, loader);
            logger.debug(LogMessage.format("Loaded module %s, now registering", className));
            return securityModule.getConstructor().newInstance();
        } catch (Exception ex) {
            logger.debug(LogMessage.format("Cannot load module %s", className), ex);
        }
        return null;
    }

    public static List<Module> getModules(ClassLoader loader) {
        List<Module> modules = new ArrayList<>();

        if (javaTimeJacksonPresent) {
            addToModulesList(loader, modules, javaTimeJackson2ModuleClass);
        }
        return modules;
    }

    private static void addToModulesList(ClassLoader loader, List<Module> modules, String className) {
        Module module = loadAndGetInstance(className, loader);
        if (module != null) {
            modules.add(module);
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

        @Serial
        private static final long serialVersionUID = 3135761660950949610L;

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
            ALLOWLIST_CLASS_NAMES = Set.of(
                    "java.util.ArrayList",
                    "java.util.Collections$EmptyList",
                    "java.util.Collections$EmptyMap",
                    "java.util.Collections$UnmodifiableRandomAccessList",
                    "java.util.Collections$SingletonList",
                    "java.util.Date",
                    "java.time.Instant",
                    "java.net.URL",
                    "java.util.TreeMap",
                    "java.util.HashMap",
                    "java.util.LinkedHashMap",
                    "org.springframework.security.core.context.SecurityContextImpl",
                    "java.util.Arrays$ArrayList");
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
