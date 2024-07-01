package com.ziyao.harbor.data.redis.core;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ziyao
 * @see <a href="https://blog.zziyao.cn">https://blog.zziyao.cn</a>
 * @since 2024/07/01 16:57:38
 */
public abstract class RedisEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -7685540435416968373L;
    @Id
    private String id;

    @TimeToLive
    private long ttl;
}
