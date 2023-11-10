package com.ziyao.harbor.usercenter.security.provider;

import com.ziyao.harbor.usercenter.comm.exception.Errors;
import com.ziyao.harbor.usercenter.entity.User;
import com.ziyao.harbor.usercenter.authenticate.UserStatusValidator;
import com.ziyao.harbor.usercenter.security.api.Authentication;
import com.ziyao.harbor.usercenter.security.api.AuthenticatedType;
import com.ziyao.harbor.usercenter.security.auth.FailureAuthDetails;
import com.ziyao.harbor.usercenter.security.auth.SuccessAuthDetails;
import com.ziyao.harbor.usercenter.cache.MemoryUserDetailsCache;
import com.ziyao.harbor.usercenter.cache.UserDetailsCache;
import com.ziyao.harbor.usercenter.authenticate.codec.PasswordEncryptor;
import com.ziyao.harbor.usercenter.authenticate.codec.BCryptPasswordEncryptor;
import com.ziyao.harbor.usercenter.security.core.PrimaryProvider;
import com.ziyao.harbor.usercenter.security.core.UserDetailsValidator;
import com.ziyao.harbor.usercenter.service.UserService;
import com.ziyao.harbor.web.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


/**
 * @author zhangziyao
 * @since 2023/4/24
 */
@Slf4j
@Component
public class UserDetailsPrimaryProvider implements PrimaryProvider {

    private final UserService userService;

    private final UserDetailsCache<User> userDetailsCache = new MemoryUserDetailsCache();

    private final PasswordEncryptor passwordEncryptor = new BCryptPasswordEncryptor();

    private final UserDetailsValidator checker = new UserStatusValidator();

    public UserDetailsPrimaryProvider(UserService userService) {
        this.userService = userService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) {
        try {
            Long appId = authentication.getAppId();
            String accessKey = authentication.getAccessKey();
            String secretKey = authentication.getSecretKey();

            // 获取用户信息，先从缓存获取，获取不到从数据获取并保存到缓存
            User userDetails = userDetailsCache.get(appId, accessKey);
            if (ObjectUtils.isEmpty(userDetails)) {
                userDetails = userService.loadUserDetails(appId, accessKey);
            }
            // 验证账号是否存在
            if (ObjectUtils.isEmpty(userDetails)) {
                throw new ServiceException(Errors.ERROR_100005);
            }
            // 验证密码
            if (!passwordEncryptor.matches(secretKey, userDetails.getSecretKey())) {
                throw new ServiceException(Errors.ERROR_100006);
            }
            // 校验账号状态
            checker.validate(userDetails);
            userDetailsCache.put(userDetails);
            return new SuccessAuthDetails(userDetails.getAppId(),
                    userDetails.getId(), userDetails.getAccessKey(), userDetails.getNickname()
                    , null, null, userDetails.getDeptId(), userDetails.getDeptName());
        } catch (ServiceException e) {
            return new FailureAuthDetails(e.getStatus(), e.getMessage());
        }
    }

    @Override
    public String getProviderName() {
        return AuthenticatedType.PASSWD.name();
    }
}
