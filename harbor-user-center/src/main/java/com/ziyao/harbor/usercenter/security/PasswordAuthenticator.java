package com.ziyao.harbor.usercenter.security;

import com.ziyao.harbor.usercenter.mysql.QueryHandler;
import com.ziyao.harbor.usercenter.mysql.QueryProcessor;
import com.ziyao.harbor.usercenter.security.api.UserDetails;
import com.ziyao.harbor.usercenter.security.core.AuthenticatedUser;
import com.ziyao.harbor.usercenter.security.core.Authenticator;

import java.util.Set;

/**
 * 用户密码主要身份验证器
 *
 * @author ziyao zhang
 * @since 2023/9/26
 */
public class PasswordAuthenticator implements Authenticator {

    private QueryHandler queryHandler;

    // 列名称
    private static final String COLUMN_KEY = "username,password";

    // username and password
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";

    private AuthenticatedUser authenticate(String username, String password) {
        try {
            return doAuthenticate(username, password);
        } catch (Exception e) {

        }
        return null;
    }

    private AuthenticatedUser doAuthenticate(String username, String password) {
        try {
            UserDetails userDetails = queryHandler.process(UserDetails.class, username);
            protectedResources();
        } catch (Exception e) {

        }
        return null;
    }


    @Override
    public boolean requireAuthentication() {
        return false;
    }

    @Override
    public void setup() {
        String query = String.format("SELECT %s FROM user WHERE %s = ?",
                COLUMN_KEY,
                USERNAME_KEY);
        queryHandler = prepare(query);
    }

    @Override
    public void validate() {

    }

    private QueryHandler prepare(String query) {
        return new QueryProcessor(null, query);
    }

    @Override
    public Set<? extends IResource> protectedResources() {
        return null;
    }

    @Override
    public AuthenticatedUser getAuthenticatedUser() {

        return null;
    }
}
