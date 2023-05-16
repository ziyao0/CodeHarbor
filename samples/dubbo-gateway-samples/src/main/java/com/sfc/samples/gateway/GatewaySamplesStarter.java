package com.sfc.samples.gateway;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Eason
 * @since 2023/5/11
 */
@EnableDubbo
@SpringBootApplication
public class GatewaySamplesStarter {

    public static void main(String[] args) {
        SpringApplication.run(GatewaySamplesStarter.class, args);
    }
}