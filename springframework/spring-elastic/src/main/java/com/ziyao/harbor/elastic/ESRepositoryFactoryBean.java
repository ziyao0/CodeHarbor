package com.ziyao.harbor.elastic;

import com.ziyao.harbor.core.lang.NonNull;
import com.ziyao.harbor.core.lang.Nullable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * @author ziyao zhang
 * @since 2023/11/16
 */
public class ESRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable>
        extends ElasticsearchRepositoryFactoryBean<T, S, ID> {

    @Nullable
    private ElasticsearchOperations operations;

    /**
     * Creates a new {@link ElasticsearchRepositoryFactoryBean} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    public ESRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    /**
     * Configures the {@link ElasticsearchOperations} to be used to create Elasticsearch repositories.
     *
     * @param operations the operations to set
     */
    public void setElasticsearchOperations(@Nullable ElasticsearchOperations operations) {

        Assert.notNull(operations, "ElasticsearchOperations must not be null!");

        setMappingContext(operations.getElasticsearchConverter().getMappingContext());
        super.setElasticsearchOperations(operations);
        this.operations = operations;
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        Assert.notNull(operations, "ElasticsearchOperations must be configured!");
    }

    @NonNull
    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {

        Assert.notNull(operations, "operations are not initialized");

        return new ESRepositoryFactory(operations);
    }
}
