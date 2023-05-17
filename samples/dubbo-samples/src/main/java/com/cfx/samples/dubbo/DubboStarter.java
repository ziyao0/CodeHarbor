package com.cfx.samples.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ziyao zhang
 * @since 2023/5/11
 */
@EnableDubbo
@SpringBootApplication
public class DubboStarter {


    public static void main(String[] args) {

        SpringApplication.run(DubboStarter.class, args);
    }
}
