package com.ziyao.harbor.data.redis;

import com.ziyao.harbor.data.redis.autoconfigure.EnableRedisRepositories;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;
import java.util.Map;

/**
 * @author ziyao zhang
 * @since 2024/2/5
 */
@SpringBootApplication
@EnableRedisRepositories(basePackages = "com.ziyao.harbor.data.redis")
public class RedisStarter implements CommandLineRunner {


    @Bean
    public RedisTemplate<String, Object> authRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        RedisSerializer<String> keySerializer = new StringRedisSerializer();
        RedisSerializer<Object> valueSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(RedisStarter.class, args);
    }


    @Resource
    private CatRepository catRepository;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(String... args) throws Exception {

        catRepository.opsForValue().set("ZIYAO", new Cat("黑猫警长", "12"));
        Cat cat = catRepository.opsForValue().get("ZIYAO");
        System.out.println(cat);

        catRepository.opsForHash().put("ZIYAO2", "z1", List.of(new Cat("黑猫警长", "12")));
        Map<String, List<Cat>> ziyao2 = catRepository.opsForHash().entries("ZIYAO2");
        System.out.println(ziyao2);
        System.out.println(catRepository.getKey());
    }
}
