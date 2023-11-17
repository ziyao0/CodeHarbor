package com.ziyao.harbor.elastic;

import com.ziyao.harbor.elastic.repository.DefaultESRepository;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactory;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.lang.NonNull;

import static org.springframework.data.querydsl.QuerydslUtils.QUERY_DSL_PRESENT;

/**
 * @author ziyao zhang
 * @since 2023/11/16
 */
public class ESRepositoryFactory extends ElasticsearchRepositoryFactory {
    public ESRepositoryFactory(ElasticsearchOperations elasticsearchOperations) {
        super(elasticsearchOperations);
    }

    @NonNull
    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        if (isQueryDslRepository(metadata.getRepositoryInterface())) {
            throw new IllegalArgumentException("QueryDsl Support has not been implemented yet.");
        }

        return DefaultESRepository.class;
    }


    private static boolean isQueryDslRepository(Class<?> repositoryInterface) {
        return QUERY_DSL_PRESENT && QuerydslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
    }
}
