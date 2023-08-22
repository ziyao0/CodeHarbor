package com.ziyao.harbor.samples.seata;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ziyao zhang
 * @since 2023/5/11
 */
@EnableDubbo
@SpringBootApplication
public class SeataStarter {

    public static void main(String[] args) {
        SpringApplication.run(SeataStarter.class, args);
    }
}
