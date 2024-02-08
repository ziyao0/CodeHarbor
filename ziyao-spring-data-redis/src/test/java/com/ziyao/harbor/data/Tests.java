package com.ziyao.harbor.data;

import com.ziyao.harbor.data.redis.CatCacheRepository;
import com.ziyao.harbor.data.redis.core.Repository;
import com.ziyao.harbor.data.redis.repository.RedisRepository;
import com.ziyao.harbor.data.redis.support.RedisRepositoryFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
public class Tests {


    public static void main(String[] args) {

        RedisRepositoryFactory factory = new RedisRepositoryFactory(new RedisTemplate<>());

        System.out.println(RedisRepository.class.isAssignableFrom(CatCacheRepository.class));
        System.out.println(Repository.class.isAssignableFrom(CatCacheRepository.class));

        CatCacheRepository repository = factory.getRepository(CatCacheRepository.class);
        System.out.println(repository);
    }


}
