package com.ziyao.harbor.usercenter;

import com.ziyao.harbor.data.redis.core.RedisAdapter;
import com.ziyao.harbor.data.redis.core.convert.RedisUpdate;
import com.ziyao.harbor.usercenter.entity.Application;
import com.ziyao.security.oauth2.core.Authentication;
import com.ziyao.security.oauth2.core.context.SecurityContextHolder;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public static void main(String[] args) {
        System.setProperty(SecurityContextHolder.SYSTEM_PROPERTY, SecurityContextHolder.MODE_DEBUG);
        SpringApplication.run(UserCenter.class, args);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
    }

    @Bean
    public RedisTemplate<byte[], byte[]> bytesRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(RedisSerializer.byteArray());
        redisTemplate.setValueSerializer(RedisSerializer.byteArray());
        redisTemplate.setHashKeySerializer(RedisSerializer.byteArray());
        redisTemplate.setHashValueSerializer(RedisSerializer.byteArray());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public RedisAdapter redisAdapter(RedisConnectionFactory redisConnectionFactory) {
        return new RedisAdapter(bytesRedisTemplate(redisConnectionFactory));
    }

    @Autowired
    private RedisAdapter redisAdapter;

    @Override
    public void run(String... args) throws Exception {
        Application application = new Application();
        application.setAppId(1111L);
        application.setIssuedAt(LocalDateTime.now());

        List<Application> applications = new ArrayList<>();
        applications.add(application);
        Application application1 = new Application();
        application1.setAppId(2222L);
        application1.setIssuedAt(LocalDateTime.now());
        applications.add(application1);
        RedisUpdate<List<Application>> update = new RedisUpdate<>(application.getAppId(), applications);
        redisAdapter.update(update);

    }
}
