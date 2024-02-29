package com.harbor.boot.autoconfigure.redis.config;

import lombok.Getter;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.TypeFilterUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.data.config.ConfigurationUtils;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.data.repository.config.DefaultRepositoryBaseClass;
import org.springframework.data.util.Streamable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author ziyao zhang
 * @since 2024/2/29
 */
public class AnnotationRepositoryConfigurationSource extends RepositoryConfigurationSourceSupport {

    private static final String REPOSITORY_IMPLEMENTATION_POSTFIX = "repositoryImplementationPostfix";
    private static final String BASE_PACKAGES = "basePackages";
    private static final String BASE_PACKAGE_CLASSES = "basePackageClasses";
    private static final String NAMED_QUERIES_LOCATION = "namedQueriesLocation";
    private static final String QUERY_LOOKUP_STRATEGY = "queryLookupStrategy";
    private static final String REPOSITORY_FACTORY_BEAN_CLASS = "repositoryFactoryBeanClass";
    private static final String REPOSITORY_BASE_CLASS = "repositoryBaseClass";
    private static final String CONSIDER_NESTED_REPOSITORIES = "considerNestedRepositories";
    private static final String BOOTSTRAP_MODE = "bootstrapMode";

    private final AnnotationMetadata configMetadata;
    /**
     * -- GETTER --
     * Returns the
     * for the
     * annotation that triggered the configuration.
     */
    @Getter
    private final AnnotationMetadata enableAnnotationMetadata;
    /**
     * -- GETTER --
     * Returns the
     * of the annotation configured.
     */
    @Getter
    private final AnnotationAttributes attributes;
    private final ResourceLoader resourceLoader;
    private final Environment environment;
    private final BeanDefinitionRegistry registry;
    private final boolean hasExplicitFilters;

    /**
     * Creates a new {@link org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource} from the given {@link AnnotationMetadata} and
     * annotation.
     *
     * @param metadata       must not be {@literal null}.
     * @param annotation     must not be {@literal null}.
     * @param resourceLoader must not be {@literal null}.
     * @param environment    must not be {@literal null}.
     * @param registry       must not be {@literal null}.
     * @deprecated since 2.2. Prefer to use overload taking a {@link BeanNameGenerator} additionally.
     */
    @Deprecated
    public AnnotationRepositoryConfigurationSource(AnnotationMetadata metadata, Class<? extends Annotation> annotation,
                                                   ResourceLoader resourceLoader, Environment environment, BeanDefinitionRegistry registry) {
        this(metadata, annotation, resourceLoader, environment, registry, null);
    }

    /**
     * Creates a new {@link org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource} from the given {@link AnnotationMetadata} and
     * annotation.
     *
     * @param metadata       must not be {@literal null}.
     * @param annotation     must not be {@literal null}.
     * @param resourceLoader must not be {@literal null}.
     * @param environment    must not be {@literal null}.
     * @param registry       must not be {@literal null}.
     * @param generator      can be {@literal null}.
     */
    public AnnotationRepositoryConfigurationSource(AnnotationMetadata metadata, Class<? extends Annotation> annotation,
                                                   ResourceLoader resourceLoader, Environment environment, BeanDefinitionRegistry registry,
                                                   @Nullable BeanNameGenerator generator) {

        super(environment, ConfigurationUtils.getRequiredClassLoader(resourceLoader), registry,
                defaultBeanNameGenerator(generator));

        Assert.notNull(metadata, "Metadata must not be null");
        Assert.notNull(annotation, "Annotation must not be null");
        Assert.notNull(resourceLoader, "ResourceLoader must not be null");

        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(annotation.getName());

        if (annotationAttributes == null) {
            throw new IllegalStateException(String.format("Unable to obtain annotation attributes for %s", annotation));
        }

        this.attributes = new AnnotationAttributes(annotationAttributes);
        this.enableAnnotationMetadata = AnnotationMetadata.introspect(annotation);
        this.configMetadata = metadata;
        this.resourceLoader = resourceLoader;
        this.environment = environment;
        this.registry = registry;
        this.hasExplicitFilters = hasExplicitFilters(attributes);
    }

    public  @NonNull Streamable<String> getBasePackages() {

        String[] value = attributes.getStringArray("value");
        String[] basePackages = attributes.getStringArray(BASE_PACKAGES);
        Class<?>[] basePackageClasses = attributes.getClassArray(BASE_PACKAGE_CLASSES);

        // Default configuration - return package of annotated class
        if (value.length == 0 && basePackages.length == 0 && basePackageClasses.length == 0) {

            String className = configMetadata.getClassName();
            return Streamable.of(ClassUtils.getPackageName(className));
        }

        Set<String> packages = new HashSet<>();
        packages.addAll(Arrays.asList(value));
        packages.addAll(Arrays.asList(basePackages));

        for (Class<?> c : basePackageClasses) {
            packages.add(ClassUtils.getPackageName(c));
        }

        return Streamable.of(packages);
    }

    public @NonNull  Optional<Object> getQueryLookupStrategyKey() {
        return Optional.ofNullable(attributes.get(QUERY_LOOKUP_STRATEGY));
    }

    public  @NonNull Optional<String> getNamedQueryLocation() {
        return getNullDefaultedAttribute(NAMED_QUERIES_LOCATION);
    }

    public @NonNull  Optional<String> getRepositoryImplementationPostfix() {
        return getNullDefaultedAttribute(REPOSITORY_IMPLEMENTATION_POSTFIX);
    }

    @NonNull
    public Object getSource() {
        return configMetadata;
    }

    @Override
    protected @NonNull Iterable<TypeFilter> getIncludeFilters() {
        return parseFilters("includeFilters");
    }

    @Override
    public  @NonNull Streamable<TypeFilter> getExcludeFilters() {
        return parseFilters("excludeFilters");
    }

    @Override
    public @NonNull  Optional<String> getRepositoryFactoryBeanClassName() {
        return Optional.of(attributes.getClass(REPOSITORY_FACTORY_BEAN_CLASS).getName());
    }

    @Override
    public @NonNull  Optional<String> getRepositoryBaseClassName() {

        if (!attributes.containsKey(REPOSITORY_BASE_CLASS)) {
            return Optional.empty();
        }

        Class<?> repositoryBaseClass = attributes.getClass(REPOSITORY_BASE_CLASS);
        return DefaultRepositoryBaseClass.class.equals(repositoryBaseClass) ? Optional.empty()
                : Optional.of(repositoryBaseClass.getName());
    }

    @Override
    public boolean shouldConsiderNestedRepositories() {
        return attributes.containsKey(CONSIDER_NESTED_REPOSITORIES) && attributes.getBoolean(CONSIDER_NESTED_REPOSITORIES);
    }

    @Override
    public @NonNull  Optional<String> getAttribute( @NonNull String name) {
        return getAttribute(name, String.class);
    }

    @Override
    public <T>  @NonNull Optional<T> getAttribute( @NonNull String name,  @NonNull Class<T> type) {

        if (!attributes.containsKey(name)) {
            throw new IllegalArgumentException(String.format("No attribute named %s found", name));
        }

        Object value = attributes.get(name);

        if (value == null) {
            return Optional.empty();
        }

        Assert.isInstanceOf(type, value,
                () -> String.format("Attribute value for %s is of type %s but was expected to be of type %s", name,
                        value.getClass(), type));

        Object result = value instanceof String //
                ? StringUtils.hasText((String) value) ? value : null //
                : value;

        return Optional.ofNullable(type.cast(result));
    }

    @Override
    public boolean usesExplicitFilters() {
        return hasExplicitFilters;
    }

    @Override
    public @NonNull BootstrapMode getBootstrapMode() {

        try {
            return attributes.getEnum(BOOTSTRAP_MODE);
        } catch (IllegalArgumentException o_O) {
            return BootstrapMode.DEFAULT;
        }
    }

    @Override
    public String getResourceDescription() {

        String simpleClassName = ClassUtils.getShortName(configMetadata.getClassName());
        String annotationClassName = ClassUtils.getShortName(enableAnnotationMetadata.getClassName());

        return String.format("@%s declared on %s", annotationClassName, simpleClassName);
    }

    private Streamable<TypeFilter> parseFilters(String attributeName) {

        AnnotationAttributes[] filters = attributes.getAnnotationArray(attributeName);

        return Streamable.of(() -> Arrays.stream(filters) //
                .flatMap(it -> TypeFilterUtils.createTypeFiltersFor(it, this.environment, this.resourceLoader, this.registry)
                        .stream()));
    }

    private Optional<String> getNullDefaultedAttribute(String attributeName) {

        String attribute = attributes.getString(attributeName);

        return StringUtils.hasText(attribute) ? Optional.of(attribute) : Optional.empty();
    }

    private static boolean hasExplicitFilters(AnnotationAttributes attributes) {

        return Stream.of("includeFilters", "excludeFilters") //
                .anyMatch(it -> attributes.getAnnotationArray(it).length > 0);
    }

    private static BeanNameGenerator defaultBeanNameGenerator(@Nullable BeanNameGenerator generator) {

        return generator == null || ConfigurationClassPostProcessor.IMPORT_BEAN_NAME_GENERATOR.equals(generator) //
                ? new AnnotationBeanNameGenerator() //
                : generator;
    }
}
