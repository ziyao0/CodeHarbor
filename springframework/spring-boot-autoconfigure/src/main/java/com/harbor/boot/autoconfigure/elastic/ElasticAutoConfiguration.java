package com.harbor.boot.autoconfigure.elastic;

import com.ziyao.harbor.elastic.ESRepositoriesRegistrar;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;

/**
 * org.elasticsearch.bootstrap.Bootstrap
 *
 * @author ziyao
 * @since 2023/4/23
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "spring.data.elasticsearch.repositories", name = "enabled", havingValue = "true",
        matchIfMissing = true)
@Import(ESRepositoriesRegistrar.class)
public class ElasticAutoConfiguration {

}
