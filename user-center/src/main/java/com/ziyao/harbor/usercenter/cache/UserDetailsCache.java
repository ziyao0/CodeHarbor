package com.ziyao.harbor.usercenter.cache;

import com.ziyao.harbor.usercenter.authenticate.core.UserDetails;

import java.util.function.Function;

/**
 * @author zhangziyao
 * @since 2023/4/24
 */
public interface UserDetailsCache<T extends UserDetails> {

    /**
     * Obtains a {@link UserDetails} from the cache.
     * <p>
     * loginName the {@link UserDetails#getAccessKey()}将用户放在缓存中
     * the populated <code>UserDetails</code> or <code>null</code> if the user
     * could not be found or if the cache entry has expired
     *
     * @return {@link  Function}
     */
    T get(Long appid, String accessKey);

    /**
     * 将用户对象放入缓存中
     * <p>
     * userDetails {@link UserDetails} to cache
     */
    void put(T t);

    /**
     * 清除缓存中的用户信息
     * <p>
     * loginName {@link UserDetails#getAccessKey()}
     */
    void remove(Long appid, String accessKey);

    /**
     * 获取key
     *
     * @param appid     系统id
     * @param accessKey 用户名
     * @return 返回map key
     */
    default String getKey(Long appid, String accessKey) {
        return appid + ":" + accessKey;
    }
}
