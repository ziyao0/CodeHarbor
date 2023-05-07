package com.cfx.usercenter.security.cache;

import com.cfx.usercenter.security.api.UserDetails;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author zhangziyao
 * @date 2023/4/24
 */
public interface UserDetailsCache {

    /**
     * Obtains a {@link com.cfx.usercenter.security.api.UserDetails} from the cache.
     * <p>
     * loginName the {@link com.cfx.usercenter.security.api.UserDetails#getAccessKey()}将用户放在缓存中
     * the populated <code>UserDetails</code> or <code>null</code> if the user
     * could not be found or if the cache entry has expired
     *
     * @return {@link  Function}
     */
    BiFunction<String, String, UserDetails> getUserDetailsOfCache();

    /**
     * 将用户对象放入缓存中
     * <p>
     * userDetails {@link UserDetails} to cache
     *
     * @return {@link  Consumer <UserDetails>}
     */
    Consumer<UserDetails> putUserDetailsInCache();

    /**
     * 清除缓存中的用户信息
     * <p>
     * loginName {@link UserDetails#getAccessKey()}
     *
     * @return {@link  Consumer<String>}
     */
    Consumer<String> removeUserDetails();
}
