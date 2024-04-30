package com.harbor.boot.autoconfigure.redis;

import com.harbor.boot.autoconfigure.redis.config.AnnotationRepositoryConfigurationSource;
import com.harbor.boot.autoconfigure.redis.config.RepositoryConfiguration;
import com.harbor.boot.autoconfigure.redis.config.RepositoryConfigurationExtension;
import com.harbor.boot.autoconfigure.redis.config.RepositoryConfigurationSource;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author ziyao zhang
 * @since 2024/2/5
 */
public class RedisRepositoryConfigurationDelegate {

    private final RepositoryConfigurationSource configurationSource;
    private final ResourceLoader resourceLoader;
    private final Environment environment;
    private static final String FACTORY_BEAN_OBJECT_TYPE = "factoryBeanObjectType";

    /**
     * Creates a new {@link org.springframework.data.repository.config.RepositoryConfigurationDelegate} for the given {@link RepositoryConfigurationSource} and
     * {@link ResourceLoader} and {@link Environment}.
     *
     * @param configurationSource must not be {@literal null}.
     * @param resourceLoader      must not be {@literal null}.
     * @param environment         must not be {@literal null}.
     */
    public RedisRepositoryConfigurationDelegate(RepositoryConfigurationSource configurationSource, ResourceLoader resourceLoader, Environment environment) {
        this.configurationSource = configurationSource;
        this.resourceLoader = resourceLoader;
        this.environment = defaultEnvironment(environment, resourceLoader);
    }

    public List<BeanComponentDefinition> registerRepositoriesIn(BeanDefinitionRegistry registry, RepositoryConfigurationExtension extension) {


        extension.registerBeansForRoot(registry, configurationSource);

        RedisRepositoryBeanDefinitionBuilder builder = new RedisRepositoryBeanDefinitionBuilder(registry, extension,
                configurationSource, resourceLoader, environment);


        Collection<RepositoryConfiguration<RepositoryConfigurationSource>> configurations = extension
                .getRepositoryConfigurations(configurationSource, resourceLoader, false);

        List<BeanComponentDefinition> definitions = new ArrayList<>();


        for (RepositoryConfiguration<? extends RepositoryConfigurationSource> configuration : configurations) {

            BeanDefinitionBuilder definitionBuilder = builder.build(configuration);

            extension.postProcess(definitionBuilder, configurationSource);

            extension.postProcess(definitionBuilder, (AnnotationRepositoryConfigurationSource) configurationSource);

            AbstractBeanDefinition beanDefinition = definitionBuilder.getBeanDefinition();

            beanDefinition.setAttribute(FACTORY_BEAN_OBJECT_TYPE, configuration.getRepositoryInterface());
            beanDefinition.setResourceDescription(configuration.getResourceDescription());

            String beanName = configurationSource.generateBeanName(beanDefinition);


            registry.registerBeanDefinition(beanName, beanDefinition);
            definitions.add(new BeanComponentDefinition(beanDefinition, beanName));
        }


        return definitions;
    }

    private static Environment defaultEnvironment(@Nullable Environment environment,
                                                  @Nullable ResourceLoader resourceLoader) {

        if (environment != null) {
            return environment;
        }

        return resourceLoader instanceof EnvironmentCapable ? ((EnvironmentCapable) resourceLoader).getEnvironment()
                : new StandardEnvironment();
    }
}
