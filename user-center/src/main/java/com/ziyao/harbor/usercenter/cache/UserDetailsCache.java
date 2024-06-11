package com.ziyao.harbor.usercenter.cache;

import com.ziyao.harbor.usercenter.authentication.core.UserDetails;

import java.util.function.Function;

/**
 * @author zhangziyao
 * @since 2023/4/24
 */
public interface UserDetailsCache<T extends UserDetails> {

    /**
     * Obtains a {@link UserDetails} from the cache.
     * <p>
     * loginName the {@link UserDetails#getUsername()}将用户放在缓存中
     * the populated <code>UserDetails</code> or <code>null</code> if the user
     * could not be found or if the cache entry has expired
     *
     * @return {@link  Function}
     */
    T get(String accessKey);

    /**
     * 将用户对象放入缓存中
     * <p>
     * userDetails {@link UserDetails} to cache
     */
    void put(T t);

    /**
     * 清除缓存中的用户信息
     * <p>
     * loginName {@link UserDetails#getUsername()}
     */
    void remove(String accessKey);

    /**
     * 获取key
     *
     * @param accessKey 用户名
     * @return 返回map key
     */
    default String getKey(String accessKey) {
        return accessKey;
    }
}
