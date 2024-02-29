package com.ziyao.harbor.redis.autoconfigure;

import com.ziyao.harbor.data.redis.support.RepositoryFactoryBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

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

    String[] value() default {};


    String[] basePackages() default {};


    Class<?>[] basePackageClasses() default {};


    ComponentScan.Filter[] excludeFilters() default {};


    ComponentScan.Filter[] includeFilters() default {};


    String repositoryImplementationPostfix() default "Impl";


    Class<?> repositoryFactoryBeanClass() default RepositoryFactoryBean.class;


    boolean considerNestedRepositories() default false;


    String redisTemplateRef() default "redisTemplate";


    String keyspaceNotificationsConfigParameter() default "Ex";
}
