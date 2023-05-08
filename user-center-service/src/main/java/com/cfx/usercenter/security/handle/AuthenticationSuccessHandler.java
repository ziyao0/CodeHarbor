package com.cfx.usercenter.security.handle;

import com.cfx.usercenter.security.api.Authentication;
import com.cfx.usercenter.security.core.LoginPostProcessor;
import com.cfx.usercenter.security.core.SuccessHandler;
import com.cfx.web.utils.ApplicationContextUtils;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * @author Eason
 * @since 2023/5/8
 */
public class AuthenticationSuccessHandler implements SuccessHandler<Authentication, Authentication>, InitializingBean {


    private List<LoginPostProcessor> processors;

    @Override
    public Authentication onSuccess(Authentication authentication) {

        Authentication success = null;

        for (LoginPostProcessor processor : getProcessors()) {
            success = processor.process(authentication);
        }
        return success;
    }

    public List<LoginPostProcessor> getProcessors() {
        return processors;
    }

    public void setProcessors(List<LoginPostProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public void afterPropertiesSet() {
        List<LoginPostProcessor> beans = ApplicationContextUtils.getBeansOfType(LoginPostProcessor.class);
        this.setProcessors(beans);
    }
}
