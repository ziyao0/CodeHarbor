package com.ziyao.harbor.elasticsearch.config;

import com.ziyao.harbor.elasticsearch.ElasticsearchRepositoryConfigExtension;
import org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;

/**
 * @author ziyao zhang
 * @since 2023/11/16
 */
public class ElasticsearchRepositoriesRegistrar extends RepositoryBeanDefinitionRegistrarSupport {

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport#getAnnotation()
     */
    @NonNull
    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableElasticsearchRepositories.class;
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
