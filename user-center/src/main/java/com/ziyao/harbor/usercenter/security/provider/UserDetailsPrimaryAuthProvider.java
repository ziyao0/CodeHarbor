package com.ziyao.harbor.usercenter.security.provider;

import com.ziyao.harbor.common.exception.ServiceException;
import com.ziyao.harbor.usercenter.comm.exception.ErrorsIMessage;
import com.ziyao.harbor.usercenter.entity.User;
import com.ziyao.harbor.usercenter.security.UserStatusChecker;
import com.ziyao.harbor.usercenter.security.api.Authentication;
import com.ziyao.harbor.usercenter.security.api.ProviderName;
import com.ziyao.harbor.usercenter.security.auth.FailureAuthDetails;
import com.ziyao.harbor.usercenter.security.auth.SuccessAuthDetails;
import com.ziyao.harbor.usercenter.security.cache.MemoryUserDetailsCache;
import com.ziyao.harbor.usercenter.security.cache.UserDetailsCache;
import com.ziyao.harbor.usercenter.security.codec.BCEncryptor;
import com.ziyao.harbor.usercenter.security.codec.Encryptor;
import com.ziyao.harbor.usercenter.security.core.PrimaryAuthProvider;
import com.ziyao.harbor.usercenter.security.core.UserDetailsChecker;
import com.ziyao.harbor.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


/**
 * @author zhangziyao
 * @since 2023/4/24
 */
@Slf4j
@Component
public class UserDetailsPrimaryAuthProvider implements PrimaryAuthProvider {

    private String beanName;
    @Autowired
    private UserService userService;

    private final UserDetailsCache<User> userDetailsCache = new MemoryUserDetailsCache();

    private final Encryptor encryptor = new BCEncryptor();

    private final UserDetailsChecker checker = new UserStatusChecker();


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
                throw new ServiceException(ErrorsIMessage.ACCOUNT_NULL);
            }
            // 验证密码
            if (!encryptor.matches(secretKey, userDetails.getSecretKey())) {
                throw new ServiceException(ErrorsIMessage.ACCOUNT_PD_NULL);
            }
            // 校验账号状态
            checker.check(userDetails);
            userDetailsCache.put(userDetails);
            return new SuccessAuthDetails(userDetails.getAppId(),
                    userDetails.getId(), userDetails.getAccessKey(), userDetails.getNickname()
                    , null, null, userDetails.getDeptId(), userDetails.getDeptName());
        } catch (ServiceException e) {
            return new FailureAuthDetails(e.getStatus(), e.getMessage());
        }
    }

    @Override
    public ProviderName getProviderName() {
        return ProviderName.PASSWD;
    }


    @Override
    public String getBeanName() {
        return beanName;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
