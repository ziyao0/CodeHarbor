package com.ziyao.harbor.elastic.core;

import com.ziyao.harbor.elastic.search.Searcher;

/**
 * @author ziyao zhang
 * @since 2023/10/31
 */
public interface ElasticContext {

    Searcher getSearcher();
}
