package com.ziyao.harbor.data.redis.autoconfigure;

import com.ziyao.harbor.data.redis.support.RepositoryFactoryBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisOperations;

import java.lang.annotation.*;

/**
 * @author ziyao zhang
 * @since 2024/2/5
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(RedisRepositoriesRegistrar.class)
public @interface EnableRedisRepositories {
    /**
     * Alias for the {@link #basePackages()} attribute. Allows for more concise annotation declarations e.g.:
     * {@code @EnableRedisRepositories("org.my.pkg")} instead of
     * {@code @EnableRedisRepositories(basePackages="org.my.pkg")}.
     */
    String[] value() default {};

    /**
     * Base packages to scan for annotated components. {@link #value()} is an alias for (and mutually exclusive with) this
     * attribute. Use {@link #basePackageClasses()} for a type-safe alternative to String-based package names.
     */
    String[] basePackages() default {};

    /**
     * Type-safe alternative to {@link #basePackages()} for specifying the packages to scan for annotated components. The
     * package of each class specified will be scanned. Consider creating a special no-op marker class or interface in
     * each package that serves no purpose other than being referenced by this attribute.
     */
    Class<?>[] basePackageClasses() default {};

    /**
     * Specifies which types are not eligible for component scanning.
     */
    ComponentScan.Filter[] excludeFilters() default {};

    /**
     * Specifies which types are eligible for component scanning. Further narrows the set of candidate components from
     * everything in {@link #basePackages()} to everything in the base packages that matches the given filter or filters.
     */
    ComponentScan.Filter[] includeFilters() default {};

    /**
     * Returns the postfix to be used when looking up custom repository implementations. Defaults to {@literal Impl}. So
     * for a repository named {@code PersonRepository} the corresponding implementation class will be looked up scanning
     * for {@code PersonRepositoryImpl}.
     */
    String repositoryImplementationPostfix() default "Impl";


    /**
     * Returns the {@link FactoryBean} class to be used for each repository instance. Defaults to
     * {@link RepositoryFactoryBean}.
     */
    Class<?> repositoryFactoryBeanClass() default RepositoryFactoryBean.class;

    /**
     * Configures whether nested repository-interfaces (e.g. defined as inner classes) should be discovered by the
     * repositories infrastructure.
     */
    boolean considerNestedRepositories() default false;

    /**
     * Configures the bean name of the {@link RedisOperations} to be used. Defaulted to {@literal redisTemplate}.
     */
    String redisTemplateRef() default "redisTemplate";


    /**
     * Configure the {@literal notify-keyspace-events} property if not already set. <br />
     * Use an empty {@link String} to keep (<b>not</b> alter) existing server configuration.
     *
     * @return {@literal Ex} by default.
     * @since 1.8
     */
    String keyspaceNotificationsConfigParameter() default "Ex";
}
