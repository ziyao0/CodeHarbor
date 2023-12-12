package com.ziyao.harbor.elasticsearch.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import com.ziyao.harbor.elasticsearch.query.BetweenQueries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/11/15
 */
@NoRepositoryBean
public interface ElasticsearchRepository<T, ID> extends PagingAndSortingRepository<T, ID>, CrudRepository<T, ID> {

    /**
     * 分页搜索数据
     * <p>searchSimilar
     * entity中如果屬性{@code null}则不会被当做查询条件进行查询.
     * 如果全部为空则进行全量查询
     *
     * @param entity   被搜索实体类
     * @param pageable 分页信息
     * @return 返回搜到到的结果，分页展示
     */
    Page<T> searchSimilar(T entity, Pageable pageable);

    /**
     * 分页搜索数据
     * <p>searchSimilar
     * entity中如果屬性{@code null}则不会被当做查询条件进行查询.
     * 如果全部为空则进行全量查询
     *
     * @param entity         被搜索实体类
     * @param betweenQueries 范围搜索条件
     * @param pageable       分页信息
     * @return 返回搜到到的结果，分页展示
     */
    Page<T> searchSimilar(T entity, BetweenQueries betweenQueries, Pageable pageable);

    /**
     * 分页搜索数据
     * <p>
     * 会通过 fields中的字段进行搜索,如果为空则进行全量查询
     *
     * @param entity         被搜索实体类
     * @param fields         搜索条件
     * @param betweenQueries 范围搜索条件
     * @param pageable       分页信息
     * @return 返回搜到到的结果，分页展示
     */
    Page<T> searchSimilar(T entity, @Nullable String[] fields, BetweenQueries betweenQueries, Pageable pageable);

    /**
     * 分页搜索数据
     *
     * @param entity         被搜索实体类
     * @param fields         搜索条件
     * @param betweenQueries 范围搜索条件
     * @param pageable       分页信息
     * @param operator       关联类型。默认{@link Operator#And}
     * @return 返回搜到到的结果，分页展示
     */
    Page<T> searchSimilar(T entity, @Nullable String[] fields, BetweenQueries betweenQueries, Pageable pageable, Operator operator);

    /**
     * 分页搜索数据
     *
     * @param criteria 条件
     * @param pageable 分页信息
     * @return 返回搜到到的结果，分页展示
     */
    Page<T> searchSimilar(Criteria criteria, Pageable pageable);

    /**
     * 分页搜索数据
     *
     * @param query 搜索条件
     * @return 返回搜到到的结果，分页展示
     */
    Page<T> search(Query query);

    /**
     * Execute the multi search query against elasticsearch and return result as {@link List} of {@link SearchHits}.
     *
     * @param queries condition
     * @return list of SearchHits
     */
    List<SearchHits<T>> multiSearch(List<? extends Query> queries);

    /**
     * return number of elements found by given query
     *
     * @param query condition
     * @return count
     */
    long count(Query query);
}
