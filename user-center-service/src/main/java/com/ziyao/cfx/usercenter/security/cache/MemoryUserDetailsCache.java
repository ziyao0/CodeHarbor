package com.ziyao.cfx.usercenter.security.cache;

import com.ziyao.cfx.usercenter.entity.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ziyao zhang
 * @since 2023/5/9
 */
public class MemoryUserDetailsCache implements UserDetailsCache<User> {

    private static final Map<String, User> USER_DETAILS_MAP = new ConcurrentHashMap<>();

    @Override
    public User get(Long appid, String accessKey) {
        return USER_DETAILS_MAP.get(getKey(appid, accessKey));
    }

    @Override
    public void put(User user) {
        USER_DETAILS_MAP.put(getKey(user.getAppId(), user.getAccessKey()), user);
    }

    @Override
    public void remove(Long appid, String accessKey) {
        USER_DETAILS_MAP.remove(getKey(appid, accessKey));
    }
}
