package com.cfx.openapi;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
public class OpenApiStarter {

    public static void main(String[] args) {
        SpringApplication.run(OpenApiStarter.class, args);
    }
}
