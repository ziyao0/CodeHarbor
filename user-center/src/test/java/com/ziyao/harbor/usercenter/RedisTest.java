package com.ziyao.harbor.usercenter;

import com.ziyao.harbor.data.redis.core.RedisOpsAdapter;
import com.ziyao.harbor.data.redis.core.convert.RedisRawData;
import com.ziyao.harbor.usercenter.entity.Application;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;

/**
 * @author ziyao
 * @see <a href="https://blog.zziyao.cn">https://blog.zziyao.cn</a>
 * @since 2024/07/02 15:23:07
 */
public class RedisTest {

    public static void main(String[] args) {
        RedisOpsAdapter redisOpsAdapter = new RedisOpsAdapter(new RedisTemplate<>());

        Application application = new Application();

        application.setAppId(1111L);

        application.setAppName("appName");

        application.setState(1);
        application.setIssuedAt(LocalDateTime.now());

        RedisRawData redisData = new RedisRawData();
        redisOpsAdapter.getConverter().write(application, redisData);

        System.out.println(redisData);




    }

}
