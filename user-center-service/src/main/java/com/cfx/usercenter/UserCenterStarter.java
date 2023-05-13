package com.cfx.usercenter;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
@Slf4j
@MapperScan("com.cfx.usercenter.mapper")
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
public class UserCenterStarter implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterStarter.class, args);
    }

    @Override
    public void run(String... args) {
        log.debug("user-center-service is started！");
    }
}
