package com.ziyao.harbor.gateway.route;

import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2023/9/7
 */
public class CacheRouteDefinitionLocator implements RouteDefinitionLocator {
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        Set<String> values = new HashSet<>();
        return Flux.fromIterable(values)
                .map(s -> createRouteDefinition());
    }

    private RouteDefinition createRouteDefinition() {
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId("id");
        URI uri = URI.create("uri");
        // 设置过滤器和谓词（根据需要添加）
        routeDefinition.setPredicates(getPredicate());
        routeDefinition.setUri(uri);
        // 设置过滤器链
        routeDefinition.setFilters(getFilterDefinitions());
        return routeDefinition;
    }

    private List<FilterDefinition> getFilterDefinitions() {
        List<FilterDefinition> filterDefinitions = new ArrayList<>();
        filterDefinitions.add(new FilterDefinition(getRequestHeaderText("TIMESTAMP", String.valueOf(System.currentTimeMillis()))));
        return filterDefinitions;
    }

    /**
     * 创建谓词列表
     */
    private List<PredicateDefinition> getPredicate() {
        // 创建谓词列表，并根据 authApp 进行配置
        List<PredicateDefinition> predicates = new ArrayList<>();
        // 添加 Header 谓词
        PredicateDefinition predicateDefinition = new PredicateDefinition("Header=key,value");
        predicates.add(predicateDefinition);
        return predicates;
    }

    /**
     * FilterDefinition value
     */
    private String getRequestHeaderText(String name, String value) {
        return NameValueGatewayFilterFactory.class.getSimpleName() + "=" + name + "," + value;
    }
}
