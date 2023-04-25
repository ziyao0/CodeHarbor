package com.cfx.usercenter.security.provider;

import com.cfx.usercenter.security.api.Authentication;
import com.cfx.usercenter.security.api.ProviderName;
import com.cfx.usercenter.security.core.PrimaryAuthProvider;

/**
 * @author zhangziyao
 * @date 2023/4/24
 */
public class UserDetailsPrimaryAuthProvider implements PrimaryAuthProvider {

    private String beanName;

    @Override
    public Authentication authenticate(Authentication authentication) {
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
