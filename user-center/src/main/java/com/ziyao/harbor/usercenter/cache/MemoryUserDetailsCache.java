package com.ziyao.harbor.usercenter.cache;

import com.ziyao.harbor.usercenter.entity.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ziyao zhang
 * @since 2023/5/9
 */
public class MemoryUserDetailsCache implements UserDetailsCache<User> {

    private static final Map<String, User> USER_DETAILS_MAP = new ConcurrentHashMap<>();

    @Override
    public User get(String username) {
        return USER_DETAILS_MAP.get(getKey(username));
    }

    @Override
    public void put(User user) {
        USER_DETAILS_MAP.put(getKey(user.getUsername()), user);
    }

    @Override
    public void remove(String username) {
        USER_DETAILS_MAP.remove(getKey(username));
    }
}
