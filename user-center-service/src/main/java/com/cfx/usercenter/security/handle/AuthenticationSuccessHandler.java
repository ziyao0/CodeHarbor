package com.cfx.usercenter.security.handle;

import com.cfx.usercenter.security.api.AccessToken;
import com.cfx.usercenter.security.api.Authentication;
import com.cfx.usercenter.security.auth.SuccessAuthDetails;
import com.cfx.usercenter.security.core.LoginPostProcessor;
import com.cfx.usercenter.security.core.SuccessHandler;
import com.cfx.web.utils.ApplicationContextUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Eason
 * @since 2023/5/8
 */
@Component
public class AuthenticationSuccessHandler implements SuccessHandler<SuccessAuthDetails, AccessToken>, InitializingBean {


    private List<LoginPostProcessor> processors;

    @Override
    public AccessToken onSuccess(SuccessAuthDetails details) {

        AccessToken success = null;

        for (LoginPostProcessor processor : getProcessors()) {
            success = processor.process(details);
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
