package com.ziyao.harbor.usercenter;

import com.ziyao.harbor.usercenter.authentication.context.SecurityContextHolder;
import com.ziyao.harbor.usercenter.authentication.core.Authentication;
import com.ziyao.harbor.usercenter.authentication.token.oauth2.RegisteredApp;
import com.ziyao.harbor.usercenter.repository.redis.RedisRegisteredAppRepository;
import com.ziyao.security.oauth2.core.AuthorizationGrantType;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Set;

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
public class UserCenter implements CommandLineRunner {

    @Resource
    private RedisRegisteredAppRepository redisRegisteredAppRepository;

    public static void main(String[] args) {
        System.setProperty(SecurityContextHolder.SYSTEM_PROPERTY, SecurityContextHolder.MODE_DEBUG);
        SpringApplication.run(UserCenter.class, args);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
    }

    @Override
    public void run(String... args) throws Exception {

        RegisteredApp registeredApp = RegisteredApp.withAppId(110L).
                authorizationGrantTypes(Set.of(AuthorizationGrantType.AUTHORIZATION_CODE))
                .redirectUri("111").issuedAt(Instant.now().plusSeconds(10000)).build();

        redisRegisteredAppRepository.opsForHash().put(1111L, registeredApp);

        RegisteredApp registeredApp1 = redisRegisteredAppRepository.opsForHash().get(1111L);
        System.out.println(registeredApp1);
    }
}
