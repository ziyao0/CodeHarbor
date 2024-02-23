package com.ziyao.harbor.elasticsearch;

import com.ziyao.harbor.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author ziyao zhang
 * @since 2023/11/17
 */
public class ElasticsearchRepositoryConfigExtension extends org.springframework.data.elasticsearch.repository.config.ElasticsearchRepositoryConfigExtension {

    @NonNull
    @Override
    public String getRepositoryFactoryBeanClassName() {
        return ElasticsearchRepositoryFactoryBean.class.getName();
    }

    @NonNull
    @Override
    protected Collection<Class<?>> getIdentifyingTypes() {
        return Arrays.asList(ElasticsearchRepository.class, ElasticsearchRepository.class);
    }
}
