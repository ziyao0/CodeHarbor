package com.ziyao.harbor.usercenter;

import com.ziyao.harbor.data.redis.core.convert.BoostMappingRedisConverter;
import com.ziyao.harbor.data.redis.core.convert.BytesToMapConverter;
import com.ziyao.harbor.data.redis.core.convert.MapToBytesConverter;
import com.ziyao.harbor.data.redis.core.convert.RedisData2;
import com.ziyao.harbor.usercenter.entity.Application;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.PathIndexResolver;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.core.convert.ReferenceResolverImpl;
import org.springframework.data.redis.core.mapping.RedisMappingContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author ziyao
 * @see <a href="https://blog.zziyao.cn">https://blog.zziyao.cn</a>
 * @since 2024/07/02 15:23:07
 */
public class RedisTest {

    public static void main(String[] args) {
        RedisMappingContext mappingContext = new RedisMappingContext();
        BoostMappingRedisConverter converter = new BoostMappingRedisConverter(mappingContext,
                new PathIndexResolver(mappingContext), new ReferenceResolverImpl(new RedisTemplate<>()));

        converter.setCustomConversions(new RedisCustomConversions(List.of(new BytesToMapConverter(), new MapToBytesConverter())));
        converter.afterPropertiesSet();

        Application application = new Application();

        application.setAppId(1111L);

        application.setAppName("appName");

        application.setState(1);
        application.setIssuedAt(LocalDateTime.now());

        RedisData2 redisData = new RedisData2();
        converter.write(application, redisData);

        System.out.println(redisData);

        Map<byte[], byte[]> map = redisData.getBucket().rawMap();

        byte[] convert = converter.getConversionService().convert(map, byte[].class);

        Map<byte[], byte[]> convert1 = converter.getConversionService().convert(convert, Map.class);

        RedisData2 data = new RedisData2(convert1);


        data.setId(redisData.getId());
        data.setKeyspace(redisData.getKeyspace());

        Application read = converter.read(Application.class, redisData);

        System.out.println(read);

    }

}
