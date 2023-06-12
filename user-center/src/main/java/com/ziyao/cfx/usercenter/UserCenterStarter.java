package com.ziyao.cfx.usercenter;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
@MapperScan("com.cfx.usercenter.mapper")
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
public class UserCenterStarter {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterStarter.class, args);
    }

}
