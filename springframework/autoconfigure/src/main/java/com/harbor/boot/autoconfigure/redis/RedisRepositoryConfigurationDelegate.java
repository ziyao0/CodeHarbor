package com.harbor.boot.autoconfigure.redis;

import com.harbor.boot.autoconfigure.redis.config.AnnotationRepositoryConfigurationSource;
import com.harbor.boot.autoconfigure.redis.config.RepositoryConfiguration;
import com.harbor.boot.autoconfigure.redis.config.RepositoryConfigurationExtension;
import com.harbor.boot.autoconfigure.redis.config.RepositoryConfigurationSource;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

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

            RootBeanDefinition beanDefinition = (RootBeanDefinition) definitionBuilder.getBeanDefinition();

            beanDefinition.setTargetType(getRepositoryInterface(configuration));

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

        if (resourceLoader instanceof EnvironmentCapable environmentCapable) {
            return environmentCapable.getEnvironment();
        } else return new StandardEnvironment();
    }

    /**
     * Returns the repository interface of the given {@link org.springframework.data.repository.config.RepositoryConfiguration} as loaded {@link Class}.
     *
     * @param configuration must not be {@literal null}.
     * @return can be {@literal null}.
     */
    @Nullable
    private ResolvableType getRepositoryInterface(RepositoryConfiguration<?> configuration) {

        String interfaceName = configuration.getRepositoryInterface();
        ClassLoader classLoader = resourceLoader.getClassLoader() == null
                ? ClassUtils.getDefaultClassLoader()
                : resourceLoader.getClassLoader();

        classLoader = classLoader != null ? classLoader : getClass().getClassLoader();

        Class<?> repositoryInterface = ReflectionUtils.loadIfPresent(interfaceName, classLoader);
        Class<?> factoryBean = ReflectionUtils.loadIfPresent(configuration.getRepositoryFactoryBeanClassName(),
                classLoader);
        if (factoryBean == null) {
            return null;
        }
        int numberOfGenerics = factoryBean.getTypeParameters().length;

        Class<?>[] generics = new Class<?>[numberOfGenerics];
        generics[0] = repositoryInterface;

        if (numberOfGenerics > 1) {
            for (int i = 1; i < numberOfGenerics; i++) {
                generics[i] = Object.class;
            }
        }

        return ResolvableType.forClassWithGenerics(factoryBean, generics);
    }
}
