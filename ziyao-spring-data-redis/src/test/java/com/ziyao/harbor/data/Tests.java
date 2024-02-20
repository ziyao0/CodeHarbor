package com.ziyao.harbor.data;

import com.ziyao.harbor.data.redis.CatRepository;
import com.ziyao.harbor.data.redis.core.Repository;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
public class Tests {


    public static void main(String[] args) {

        RedisRepositoryFactory factory = new RedisRepositoryFactory(new RedisTemplate<>());

        System.out.println(RedisRepository.class.isAssignableFrom(CatRepository.class));
        System.out.println(Repository.class.isAssignableFrom(CatRepository.class));

        CatRepository repository = factory.getRepository(CatRepository.class);
        System.out.println(repository);
    }


}
