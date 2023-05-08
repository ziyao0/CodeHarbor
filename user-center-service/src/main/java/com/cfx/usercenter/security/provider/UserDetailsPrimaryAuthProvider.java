package com.cfx.usercenter.security.provider;

import com.cfx.common.exception.ServiceException;
import com.cfx.usercenter.comm.exception.ErrorsIMessage;
import com.cfx.usercenter.security.UserStatusChecker;
import com.cfx.usercenter.security.api.Authentication;
import com.cfx.usercenter.security.api.ProviderName;
import com.cfx.usercenter.security.api.UserDetails;
import com.cfx.usercenter.security.cache.UserDetailsCache;
import com.cfx.usercenter.security.codec.DefaultPdEncryptor;
import com.cfx.usercenter.security.codec.Encryptor;
import com.cfx.usercenter.security.core.PrimaryAuthProvider;
import com.cfx.usercenter.security.core.UserDetailsChecker;
import com.cfx.usercenter.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * @author zhangziyao
 * @date 2023/4/24
 */
@Component
public class UserDetailsPrimaryAuthProvider implements PrimaryAuthProvider {

    private String beanName;
    @Resource
    private UserService userService;
    @Resource
    private UserDetailsCache userDetailsCache;

    private final Encryptor encryptor = new DefaultPdEncryptor();

    private final UserDetailsChecker checker = new UserStatusChecker();


    @Override
    public Authentication authenticate(Authentication authentication) {

        Long appId = authentication.getAppId();

        String accessKey = authentication.getAccessKey();

        String secretKey = authentication.getSecretKey();

        // 获取用户信息，先从缓存获取，获取不到从数据获取并保存到缓存
        UserDetails userDetails = userDetailsCache.getUserDetailsOfCache().apply(appId, accessKey);
        if (ObjectUtils.isEmpty(userDetails)) {
            userDetails = userService.loadUserDetails(appId, accessKey);
            userDetailsCache.putUserDetailsInCache().accept(userDetails);
        }
        // 验证账号是否存在
        if (ObjectUtils.isEmpty(userDetails)) {
            throw new ServiceException(ErrorsIMessage.ACCOUNT_NULL);
        }
        // 验证密码
        if (!encryptor.matches(secretKey, userDetails.getSecretKey())) {
            throw new ServiceException(ErrorsIMessage.ACCOUNT_PD_NULL);
        }

        checker.check(userDetails);
        return null;
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
