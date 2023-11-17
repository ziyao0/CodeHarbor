package com.ziyao.harbor.elastic;


import org.springframework.data.elasticsearch.repository.config.ElasticsearchRepositoryConfigExtension;
import org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;

/**
 * @author ziyao zhang
 * @since 2023/11/16
 */
public class ESRepositoriesRegistrar extends RepositoryBeanDefinitionRegistrarSupport {

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport#getAnnotation()
     */
    @NonNull
    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableESRepositories.class;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport#getExtension()
     */
    @NonNull
    @Override
    protected RepositoryConfigurationExtension getExtension() {
        return new ElasticsearchRepositoryConfigExtension();
    }
}
