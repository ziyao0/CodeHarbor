package com.ziyao.harbor.elasticsearch.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import com.ziyao.harbor.elasticsearch.query.BetweenQueries;
import com.ziyao.harbor.elasticsearch.support.EntityPropertyExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.BaseQuery;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository;
import org.springframework.data.util.StreamUtils;
import org.springframework.data.util.Streamable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ziyao zhang
 * @since 2023/11/15
 */
public class EnhanceElasticsearchRepository<T, ID> implements ElasticsearchRepository<T, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnhanceElasticsearchRepository.class);
    private final EntityPropertyExtractor<T> entityPropertyExtractor;
    protected ElasticsearchOperations operations;
    protected IndexOperations indexOperations;
    protected Class<T> entityClass;
    protected ElasticsearchEntityInformation<T, ID> entityInformation;


    public EnhanceElasticsearchRepository(ElasticsearchEntityInformation<T, ID> metadata,
                                          ElasticsearchOperations operations) {
        this.operations = operations;

        Assert.notNull(metadata, "ElasticsearchEntityInformation must not be null!");

        this.entityInformation = metadata;
        this.entityClass = this.entityInformation.getJavaType();
        this.indexOperations = operations.indexOps(this.entityClass);

        if (shouldCreateIndexAndMapping() && !indexOperations.exists()) {
            indexOperations.createWithMapping();
        }
        entityPropertyExtractor = new EntityPropertyExtractor<>(metadata.getJavaType());
    }


    @Override
    public Page<T> searchSimilar(T entity, Pageable pageable) {
        return searchSimilar(entity, null, BetweenQueries.of(), pageable);
    }

    @Override
    public Page<T> searchSimilar(T entity, BetweenQueries betweenQueries, Pageable pageable) {
        return searchSimilar(entity, null, betweenQueries, pageable);
    }

    @Override
    public Page<T> searchSimilar(@NonNull T entity, @Nullable String[] fields, BetweenQueries betweenQueries, @NonNull Pageable pageable) {
        return searchSimilar(entity, fields, betweenQueries, pageable, Operator.And);
    }


    @Override
    public Page<T> searchSimilar(T entity, @Nullable String[] fields, BetweenQueries betweenQueries, Pageable pageable, Operator operator) {
        Assert.notNull(entity, "Cannot search similar records for 'null'.");
        Assert.notNull(pageable, "'pageable' cannot be 'null'");

        return search(
                CriteriaQuery.builder(
                                createCriteria(entity, fields, betweenQueries, operator))
                        .withPageable(pageable).build());
    }

    @Override
    public Page<T> searchSimilar(Criteria criteria, Pageable pageable) {
        Assert.notNull(criteria, "Query 不能为空");
        Assert.notNull(pageable, "Query 不能为空");
        return search(
                CriteriaQuery.builder(criteria).withPageable(pageable).build());
    }

    @Override
    public Page<T> search(Query query) {
        Assert.notNull(query, "Query 不能为空");
        return doSearch(query);
    }

    @Override
    public List<SearchHits<T>> multiSearch(List<? extends Query> queries) {
        Assert.notNull(queries, "查询条件不能为空");
        return operations.multiSearch(queries, entityClass);
    }

    @Override
    public long count(Query query) {
        Assert.notNull(query, "查询条件不能为空");
        return operations.count(query, entityClass);
    }

    private boolean shouldCreateIndexAndMapping() {

        final ElasticsearchPersistentEntity<?> entity = operations.getElasticsearchConverter().getMappingContext()
                .getRequiredPersistentEntity(entityClass);
        return entity.isCreateIndexAndMapping();
    }

    @Override
    public @NonNull Optional<T> findById(@NonNull ID id) {
        String idRepresentation = stringIdRepresentation(id);
        if (idRepresentation == null) {
            return Optional.empty();
        } else
            return Optional.ofNullable(
                    execute(operations -> operations.get(idRepresentation, entityClass, getIndexCoordinates())));
    }


    @Override
    public @NonNull Iterable<T> findAll() {
        int itemCount = (int) this.count();

        if (itemCount == 0) {
            return new PageImpl<>(Collections.emptyList());
        }
        return this.findAll(PageRequest.of(0, Math.max(1, itemCount)));
    }


    @SuppressWarnings("unchecked")
    @Override
    public @NonNull Page<T> findAll(@NonNull Pageable pageable) {

        Assert.notNull(pageable, "pageable must not be null");

        Query query = Query.findAll();
        query.setPageable(pageable);
        SearchHits<T> searchHits = execute(operations -> operations.search(query, entityClass, getIndexCoordinates()));
        if (searchHits != null) {
            SearchPage<T> page = SearchHitSupport.searchPageFor(searchHits, query.getPageable());
            // noinspection ConstantConditions
            return (Page<T>) SearchHitSupport.unwrapSearchHits(page);
        } else return Page.empty();
    }


    @SuppressWarnings("unchecked")
    @Override
    public @NonNull Iterable<T> findAll(@NonNull Sort sort) {

        Assert.notNull(sort, "sort must not be null");

        int itemCount = (int) this.count();

        if (itemCount == 0) {
            return new PageImpl<>(Collections.emptyList());
        }
        Pageable pageable = PageRequest.of(0, itemCount, sort);
        Query query = Query.findAll();
        query.setPageable(pageable);
        List<SearchHit<T>> searchHitList = execute(
                operations -> operations.search(query, entityClass, getIndexCoordinates()).getSearchHits());
        // noinspection ConstantConditions
        return (List<T>) SearchHitSupport.unwrapSearchHits(searchHitList);
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NonNull Iterable<T> findAllById(@NonNull Iterable<ID> ids) {

        Assert.notNull(ids, "ids can't be null.");

        List<String> stringIds = stringIdsRepresentation(ids);
        Query query = getIdQuery(stringIds);
        if (!stringIds.isEmpty()) {
            query.setPageable(PageRequest.of(0, stringIds.size()));
        }
        List<SearchHit<T>> searchHitList = execute(
                operations -> operations.search(query, entityClass, getIndexCoordinates()).getSearchHits());
        // noinspection ConstantConditions
        Object unwrappedSearchHits = SearchHitSupport.unwrapSearchHits(searchHitList);
        if (unwrappedSearchHits != null) {
            return (List<T>) unwrappedSearchHits;
        } else {
            return List.of();
        }
    }

    @Override
    public long count() {
        Query query = Query.findAll();
        ((BaseQuery) query).setMaxResults(0);
        Long count = execute(operations -> operations.count(query, entityClass, getIndexCoordinates()));
        return count == null ? 0L : count;
    }

    @NonNull
    @Override
    public <S extends T> S save(@NonNull S entity) {

        Assert.notNull(entity, "Cannot save 'null' entity.");

        // noinspection ConstantConditions
        return executeAndRefresh(operations -> operations.save(entity, getIndexCoordinates()));
    }

    public <S extends T> List<S> save(List<S> entities) {

        Assert.notNull(entities, "Cannot insert 'null' as a List.");

        return Streamable.of(saveAll(entities)).stream().collect(Collectors.toList());
    }

    @NonNull
    @Override
    public <S extends T> Iterable<S> saveAll(@NonNull Iterable<S> entities) {

        Assert.notNull(entities, "Cannot insert 'null' as a List.");

        IndexCoordinates indexCoordinates = getIndexCoordinates();
        executeAndRefresh(operations -> operations.save(entities, indexCoordinates));

        return entities;
    }

    @Override
    public boolean existsById(@NonNull ID id) {
        // noinspection ConstantConditions
        return execute(operations -> operations.exists(stringIdRepresentation(id), getIndexCoordinates()));
    }

    @Override
    public void deleteById(@NonNull ID id) {

        Assert.notNull(id, "Cannot delete entity with id 'null'.");

        doDelete(id, getIndexCoordinates());
    }

    @Override
    public void delete(@NonNull T entity) {

        Assert.notNull(entity, "Cannot delete 'null' entity.");

        doDelete(extractIdFromBean(entity), getIndexCoordinates());
    }

    @Override
    public void deleteAllById(@NonNull Iterable<? extends ID> ids) {

        Assert.notNull(ids, "Cannot delete 'null' list.");

        List<String> idStrings = new ArrayList<>();
        for (ID id : ids) {
            idStrings.add(stringIdRepresentation(id));
        }

        if (idStrings.isEmpty()) {
            return;
        }

        Query query = operations.idsQuery(idStrings);
        executeAndRefresh((SimpleElasticsearchRepository.OperationsCallback<Void>) operations -> {
            operations.delete(query, entityClass, getIndexCoordinates());
            return null;
        });
    }

    @Override
    public void deleteAll(@NonNull Iterable<? extends T> entities) {

        Assert.notNull(entities, "Cannot delete 'null' list.");

        List<ID> ids = new ArrayList<>();
        for (T entity : entities) {
            ID id = extractIdFromBean(entity);
            if (id != null) {
                ids.add(id);
            }
        }

        deleteAllById(ids);
    }

    private void doDelete(@Nullable ID id, IndexCoordinates indexCoordinates) {

        if (id != null) {
            String idRepresentation = stringIdRepresentation(id);
            if (idRepresentation != null) {
                executeAndRefresh(operations -> operations.delete(idRepresentation, indexCoordinates));
            }
        }
    }

    @Override
    public void deleteAll() {
        IndexCoordinates indexCoordinates = getIndexCoordinates();

        executeAndRefresh((SimpleElasticsearchRepository.OperationsCallback<Void>) operations -> {
            operations.delete(Query.findAll(), entityClass, indexCoordinates);
            return null;
        });
    }

    private void doRefresh() {
        RefreshPolicy refreshPolicy = null;

        if (operations instanceof AbstractElasticsearchTemplate) {
            refreshPolicy = ((AbstractElasticsearchTemplate) operations).getRefreshPolicy();
        }

        if (refreshPolicy == null) {
            indexOperations.refresh();
        }
    }

    // region helper functions
    @Nullable
    protected ID extractIdFromBean(T entity) {
        return entityInformation.getId(entity);
    }

    private List<String> stringIdsRepresentation(Iterable<? extends ID> ids) {

        Assert.notNull(ids, "ids can't be null.");

        return StreamUtils.createStreamFromIterator(ids.iterator()).map(this::stringIdRepresentation)
                .collect(Collectors.toList());
    }

    protected @Nullable String stringIdRepresentation(@Nullable ID id) {
        return operations.convertId(id);
    }

    private IndexCoordinates getIndexCoordinates() {
        return operations.getIndexCoordinatesFor(entityClass);
    }

    private Query getIdQuery(List<String> stringIds) {
        return operations.idsQuery(stringIds);
    }
    // endregion

    // region operations callback
    @FunctionalInterface
    public interface OperationsCallback<R> {
        @Nullable
        R doWithOperations(ElasticsearchOperations operations);
    }

    @Nullable
    public <R> R execute(SimpleElasticsearchRepository.OperationsCallback<R> callback) {
        return callback.doWithOperations(operations);
    }

    @Nullable
    public <R> R executeAndRefresh(SimpleElasticsearchRepository.OperationsCallback<R> callback) {
        R result = callback.doWithOperations(operations);
        doRefresh();
        return result;
    }
    // endregion

    /**
     * 查询核心方法
     */
    @SuppressWarnings("unchecked")
    private Page<T> doSearch(Query query) {

        SearchHits<T> searchHits = operations.search(query, entityClass);
        SearchPage<T> searchPage = SearchHitSupport.searchPageFor(searchHits, query.getPageable());
        return (Page<T>) SearchHitSupport.unwrapSearchHits(searchPage);
    }

    /**
     * 创建并组装查询条件
     */
    private Criteria createCriteria(T entity, @Nullable String[] fields, BetweenQueries betweenQueries, Operator operator) {
        Criteria criteria = new Criteria();

        Map<String, Object> properties = extractPropertyFromEntity(entity, fields);
        doCreateCriteria(properties, betweenQueries).forEach(condition -> {
            switch (operator) {
                case And -> criteria.and(condition);
                case Or -> criteria.or(condition);
                default -> LOGGER.error("未知的操作类型:{}", operator.jsonValue());
            }
        });
        return criteria;
    }

    /**
     * 创建查询条件
     */
    private List<Criteria> doCreateCriteria(Map<String, Object> properties, BetweenQueries betweenQueries) {
        List<Criteria> criteriaList = new ArrayList<>();
        for (Map.Entry<String, Object> property : properties.entrySet()) {

            Object value = property.getValue();
            //  skip value is null
            if (!ObjectUtils.isEmpty(value)) {
                criteriaList.add(new Criteria(property.getKey()).is(property.getValue()));
            }
        }
        List<BetweenQueries.BetweenQuery> queries = betweenQueries.getQueries();
        // Query conditions for assembly range
        queries.forEach(query -> criteriaList.add(new Criteria(query.field()).between(query.lowerBound(), query.upperBound())));
        return criteriaList;
    }

    /**
     * 从给定的实体类中提取请求参数和值，返回key-value格式
     *
     * @param entity 实体类
     * @param fields 给定字段数组，如果不为空则从实体类中提取该数组中字段的值
     * @return 返回key-value格式的集合
     */
    private Map<String, Object> extractPropertyFromEntity(T entity, @Nullable String[] fields) {
        if (fields == null) {
            return entityPropertyExtractor.extract(entity);
        }
        Map<String, Object> properties = new HashMap<>();
        for (String field : fields) {
            properties.put(field, entityPropertyExtractor.extract(entity, field));
        }
        return properties;
    }
}
