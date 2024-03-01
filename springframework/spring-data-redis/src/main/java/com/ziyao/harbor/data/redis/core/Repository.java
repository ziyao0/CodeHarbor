package com.ziyao.harbor.data.redis.core;

import com.ziyao.harbor.data.redis.support.serializer.SerializerInformation;
import org.springframework.stereotype.Indexed;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
@Indexed
public interface Repository {

    /**
     * 判断缓存中有没有这个key
     */
    boolean hasKey(String... arguments);

    /**
     * 删除key
     */
    boolean delete(String... arguments);


    /**
     * 获取key
     */
    String getKey(String... arguments);

    /**
     * 刷新过期时间
     */
    void refresh(String... arguments);

    /**
     * 刷新过期时间
     */
    void refresh(long timeout, TimeUnit unit, String... arguments);


    SerializerInformation getInformation();

    default String version() {
        return "v1.0";
    }
}
