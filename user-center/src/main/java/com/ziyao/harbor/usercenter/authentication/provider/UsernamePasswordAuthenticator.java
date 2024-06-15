package com.ziyao.harbor.usercenter.authentication.provider;

import com.ziyao.harbor.usercenter.authentication.codec.BCryptPasswordEncryptor;
import com.ziyao.harbor.usercenter.authentication.codec.PasswordEncryptor;
import com.ziyao.harbor.usercenter.authentication.core.Authentication;
import com.ziyao.harbor.usercenter.authentication.core.UserDetails;
import com.ziyao.harbor.usercenter.authentication.support.UserDetailsValidator;
import com.ziyao.harbor.usercenter.authentication.token.UsernamePasswordAuthenticationToken;
import com.ziyao.harbor.usercenter.common.exception.Errors;
import com.ziyao.harbor.usercenter.common.exception.UserStatusException;
import com.ziyao.harbor.usercenter.service.user.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author ziyao zhang
 * @time 2024/6/11
 */
@Component
public class UsernamePasswordAuthenticator implements OAuth2Authenticator {

    private final UserDetailsService userDetailsService;

    private final PasswordEncryptor passwordEncryptor = new BCryptPasswordEncryptor();

    public UsernamePasswordAuthenticator(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;

        String username = (String) authenticationToken.getPrincipal();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UserDetailsValidator.assertExists(userDetails);
        // 输入的密码
        String credentials = (String) authenticationToken.getCredentials();

        if (!passwordEncryptor.matches(credentials, userDetails.getPassword())) {
            throw new UserStatusException(Errors.ERROR_100009);
        }

        UserDetailsValidator.validated(userDetails);
        return UsernamePasswordAuthenticationToken.authenticated(
                userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationClass);
    }
}
