package com.ziyao.harbor.data.redis.core;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.Map;

/**
 * @author ziyao
 * @see <a href="https://blog.zziyao.cn">https://blog.zziyao.cn</a>
 * @since 2024/07/01 16:48:49
 */
@Setter
@Getter
public abstract class RedisHashEntity extends RedisEntity {

    @Serial
    private static final long serialVersionUID = 776687427957260133L;

    private Map<Object, Object> hashData;

}
