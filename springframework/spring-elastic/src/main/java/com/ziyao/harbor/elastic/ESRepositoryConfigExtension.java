package com.ziyao.harbor.elastic;

import com.ziyao.harbor.elastic.repository.ESRepository;
import org.springframework.data.elasticsearch.repository.config.ElasticsearchRepositoryConfigExtension;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author ziyao zhang
 * @since 2023/11/17
 */
public class ESRepositoryConfigExtension extends ElasticsearchRepositoryConfigExtension {

    @NonNull
    @Override
    public String getRepositoryFactoryBeanClassName() {
        return ESRepositoryFactoryBean.class.getName();
    }

    @NonNull
    @Override
    protected Collection<Class<?>> getIdentifyingTypes() {
        return Arrays.asList(ESRepository.class, ESRepository.class);
    }
}
