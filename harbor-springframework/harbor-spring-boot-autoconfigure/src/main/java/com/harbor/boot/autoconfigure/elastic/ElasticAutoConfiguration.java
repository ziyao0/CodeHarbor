package com.harbor.boot.autoconfigure.elastic;

import com.ziyao.harbor.elastic.EnableESRepositories;
import org.springframework.context.annotation.Configuration;

/**
 * org.elasticsearch.bootstrap.Bootstrap
 *
 * @author ziyao
 * @since 2023/4/23
 */
@Configuration
<<<<<<<< HEAD:harbor-springframework/harbor-spring-elastic/src/main/java/com/ziyao/harbor/elastic/ElasticsearchAutoConfiguration.java
public class ElasticsearchAutoConfiguration {
========
@EnableESRepositories
public class ElasticAutoConfiguration {
>>>>>>>> main:harbor-springframework/harbor-spring-boot-autoconfigure/src/main/java/com/harbor/boot/autoconfigure/elastic/ElasticAutoConfiguration.java
}
