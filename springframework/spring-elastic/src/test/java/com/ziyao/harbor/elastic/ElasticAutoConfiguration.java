package com.ziyao.harbor.elastic;

import com.ziyao.harbor.elastic.entity.Cat;
import org.elasticsearch.client.Client;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * org.elasticsearch.bootstrap.Bootstrap
 *
 * @author ziyao
 * @since 2023/4/23
 */
@AutoConfiguration
@ConditionalOnClass({Client.class, ElasticsearchRepository.class})
@ConditionalOnProperty(prefix = "spring.elasticsearch.repositories", name = "enabled", havingValue = "true",
        matchIfMissing = true)
@Import(ESRepositoriesRegistrar.class)
public class ElasticAutoConfiguration {

    @Bean
    public Cat cat() {
        return new Cat();
    }
}
