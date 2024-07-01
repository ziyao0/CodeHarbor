package com.ziyao.harbor.data.redis.core.convert;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ClassUtils;

/**
 * @author ziyao zhang
 * @time 2024/6/29
 */
@Getter
public class RedisUpdate<T> {

    private final Object id;
    private final Class<T> target;
    private final T value;
    @Setter
    private boolean refresh;


    @SuppressWarnings("unchecked")
    public RedisUpdate(Object id, T value) {
        this(id, (Class<T>) ClassUtils.getUserClass(value), value, false);

    }

    public RedisUpdate(Object id, Class<T> target, T value, boolean refresh) {
        this.id = id;
        this.target = target;
        this.value = value;
        this.refresh = refresh;
    }
}
