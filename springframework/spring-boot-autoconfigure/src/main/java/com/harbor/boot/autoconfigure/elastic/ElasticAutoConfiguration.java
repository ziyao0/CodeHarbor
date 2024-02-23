package com.harbor.boot.autoconfigure.elastic;

import com.ziyao.harbor.elasticsearch.config.EnableElasticsearchRepositories;
import org.springframework.context.annotation.Configuration;

/**
 * org.elasticsearch.bootstrap.Bootstrap
 *
 * @author ziyao
 * @since 2023/4/23
 */
@Configuration
@EnableElasticsearchRepositories
public class ElasticAutoConfiguration {
}
