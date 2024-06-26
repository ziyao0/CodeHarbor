package com.ziyao.harbor.usercenter;

import com.ziyao.security.oauth2.core.Authentication;
import com.ziyao.security.oauth2.core.context.SecurityContextHolder;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.ziyao.harbor.usercenter.repository.mapper")
@EntityScan(basePackages = "com.ziyao.harbor.usercenter.entity")
@EnableJpaRepositories(basePackages = "com.ziyao.harbor.usercenter.repository.jpa")
@EnableRedisRepositories(basePackages = "com.ziyao.harbor.usercenter.repository.redis2")
public class UserCenter {

    public static void main(String[] args) {
        System.setProperty(SecurityContextHolder.SYSTEM_PROPERTY, SecurityContextHolder.MODE_DEBUG);
        SpringApplication.run(UserCenter.class, args);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
    }


}
