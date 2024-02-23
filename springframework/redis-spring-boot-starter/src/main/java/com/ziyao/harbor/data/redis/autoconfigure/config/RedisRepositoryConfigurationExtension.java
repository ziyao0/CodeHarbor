package com.ziyao.harbor.data.redis.autoconfigure.config;

import com.ziyao.harbor.data.redis.support.RepositoryFactoryBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.repository.config.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author ziyao zhang
 * @since 2024/2/5
 */
public class RedisRepositoryConfigurationExtension implements RepositoryConfigurationExtension {
    private static final Log logger = LogFactory.getLog(RedisRepositoryConfigurationExtension.class);
    private static final String CLASS_LOADING_ERROR = "%s - Could not load type %s using class loader %s";
    protected static final String REDIS_TEMPLATE_BEAN_REF_ATTRIBUTE = "redisTemplateRef";

    @Override
    public @NonNull String getModuleName() {
        return "Redis";
    }

    @Override
    public @NonNull <T extends RepositoryConfigurationSource> Collection<RepositoryConfiguration<T>>
    getRepositoryConfigurations(@NonNull T configSource, @NonNull ResourceLoader loader, @NonNull boolean strictMatchesOnly) {
        Assert.notNull(configSource, "ConfigSource must not be null");
        Assert.notNull(loader, "Loader must not be null");

        Set<RepositoryConfiguration<T>> result = new HashSet<>();

        for (BeanDefinition candidate : configSource.getCandidates(loader)) {

            RepositoryConfiguration<T> configuration = getRepositoryConfiguration(candidate, configSource);
//            Class<?> repositoryInterface = loadRepositoryInterface(configuration,
//                    getConfigurationInspectionClassLoader(loader));

            result.add(configuration);
        }

        return result;
    }

    @Override
    public @NonNull String getDefaultNamedQueryLocation() {
//        return String.format("classpath*:META-INF/%s-named-queries.properties", getModuleIdentifier());
        return "";
    }

    @Override
    public @NonNull String getRepositoryFactoryBeanClassName() {
        return RepositoryFactoryBean.class.getName();
    }


    @Override
    public void registerBeansForRoot(@NonNull BeanDefinitionRegistry registry, @NonNull RepositoryConfigurationSource configurationSource) {
        Optional<String> templateRef = configurationSource.getAttribute("redisTemplateRef");
        if (templateRef.isPresent()) {
            String redisTemplateRef = templateRef.get();
            if (!StringUtils.hasText(redisTemplateRef)) {
                throw new IllegalStateException(
                        "@EnableRedisRepositories(redisTemplateRef = … ) must be configured to a non empty value");
            }
        }


    }

    @Override
    public void postProcess(@NonNull BeanDefinitionBuilder builder, @NonNull RepositoryConfigurationSource config) {

    }

    public static void registerIfNotAlreadyRegistered(Supplier<AbstractBeanDefinition> supplier,
                                                      BeanDefinitionRegistry registry, String beanName, Object source) {

        if (registry.containsBeanDefinition(beanName)) {
            return;
        }

        AbstractBeanDefinition bean = supplier.get();

        bean.setSource(source);
        registry.registerBeanDefinition(beanName, bean);
    }


    protected <T extends RepositoryConfigurationSource> RepositoryConfiguration<T> getRepositoryConfiguration(
            BeanDefinition definition, T configSource) {
        return new DefaultRepositoryConfiguration<>(configSource, definition, this);
    }

    @Nullable
    private Class<?> loadRepositoryInterface(RepositoryConfiguration<?> configuration,
                                             @Nullable ClassLoader classLoader) {

        String repositoryInterface = configuration.getRepositoryInterface();

        try {
            return org.springframework.util.ClassUtils.forName(repositoryInterface, classLoader);
        } catch (ClassNotFoundException | LinkageError e) {
            logger.warn(String.format(CLASS_LOADING_ERROR, getModuleName(), repositoryInterface, classLoader), e);
        }

        return null;
    }

    @Nullable
    protected ClassLoader getConfigurationInspectionClassLoader(ResourceLoader loader) {
        return loader.getClassLoader();
    }


    @Override
    public void postProcess(@NonNull BeanDefinitionBuilder builder, @NonNull AnnotationRepositoryConfigurationSource config) {
        AnnotationAttributes attributes = config.getAttributes();

        builder.addPropertyReference("redisOperations", attributes.getString(REDIS_TEMPLATE_BEAN_REF_ATTRIBUTE));
    }

    @Override
    public void postProcess(@NonNull BeanDefinitionBuilder builder, @NonNull XmlRepositoryConfigurationSource config) {

    }
}
