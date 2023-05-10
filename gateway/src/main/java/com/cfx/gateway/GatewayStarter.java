package com.cfx.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayStarter {

    public static void main(String[] args) {
        SpringApplication.run(GatewayStarter.class, args);
    }
}
