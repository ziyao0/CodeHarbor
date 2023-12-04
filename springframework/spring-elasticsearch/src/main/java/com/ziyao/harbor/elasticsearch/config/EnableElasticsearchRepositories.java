package com.ziyao.harbor.elasticsearch.config;

import com.ziyao.harbor.elasticsearch.ElasticsearchRepositoryFactoryBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.repository.config.DefaultRepositoryBaseClass;
import org.springframework.data.repository.query.QueryLookupStrategy;

import java.lang.annotation.*;

/**
 * @author ziyao zhang
 * @since 2023/11/16
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(ElasticsearchRepositoriesRegistrar.class)
public @interface EnableElasticsearchRepositories {

    /**
     * Alias for the {@link #basePackages()} attribute. Allows for more concise annotation declarations e.g.:
     * {@code @EnableElasticsearchRepositories("org.my.pkg")} instead of
     * {@code @EnableElasticsearchRepositories(basePackages="org.my.pkg")}.
     */
    String[] value() default {};

    /**
     * Base packages to scan for annotated components. {@link #value()} is an alias for (and mutually exclusive with) this
     * attribute. Use {@link #basePackageClasses()} for a type-safe alternative to text-based package names.
     */
    String[] basePackages() default {};

    /**
     * Type-safe alternative to {@link #basePackages()} for specifying the packages to scan for annotated components. The
     * package of each class specified will be scanned. Consider creating a special no-op marker class or interface in
     * each package that serves no purpose other than being referenced by this attribute.
     */
    Class<?>[] basePackageClasses() default {};

    /**
     * Specifies which types are eligible for component scanning. Further narrows the set of candidate components from
     * everything in {@link #basePackages()} to everything in the base packages that matches the given filter or filters.
     */
    ComponentScan.Filter[] includeFilters() default {};

    /**
     * Specifies which types are not eligible for component scanning.
     */
    ComponentScan.Filter[] excludeFilters() default {};

    /**
     * Returns the postfix to be used when looking up custom repository implementations. Defaults to {@literal Impl}. So
     * for a repository named {@code PersonRepository} the corresponding implementation class will be looked up scanning
     * for {@code PersonRepositoryImpl}.
     */
    String repositoryImplementationPostfix() default "Impl";

    /**
     * Configures the location of where to find the Spring Data named queries properties file. Will default to
     * {@code META-INFO/elasticsearch-named-queries.properties}.
     */
    String namedQueriesLocation() default "";

    /**
     * Returns the key of the {@link org.springframework.data.repository.query.QueryLookupStrategy} to be used for lookup
     * queries for query methods. Defaults to
     * {@link org.springframework.data.repository.query.QueryLookupStrategy.Key#CREATE_IF_NOT_FOUND}.
     */
    QueryLookupStrategy.Key queryLookupStrategy() default QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND;

    /**
     * Returns the {@link org.springframework.beans.factory.FactoryBean} class to be used for each repository instance.
     * Defaults to {@link org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactoryBean}.
     */
    Class<?> repositoryFactoryBeanClass() default ElasticsearchRepositoryFactoryBean.class;

    /**
     * Configure the repository base class to be used to create repository proxies for this particular configuration.
     */
    Class<?> repositoryBaseClass() default DefaultRepositoryBaseClass.class;

    // Elasticsearch specific configuration

    /**
     * Configures the name of the {@link org.springframework.data.elasticsearch.core.ElasticsearchOperations} bean
     * definition to be used to create repositories discovered through this annotation. Defaults to
     * {@code elasticsearchTemplate}.
     */
    String elasticsearchTemplateRef() default "elasticsearchTemplate";

    /**
     * Configures whether nested repository-interfaces (e.g. defined as inner classes) should be discovered by the
     * repositories infrastructure.
     */
    boolean considerNestedRepositories() default false;
}
