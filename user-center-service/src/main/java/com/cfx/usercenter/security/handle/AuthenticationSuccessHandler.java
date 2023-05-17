package com.cfx.usercenter.security.handle;

import com.cfx.usercenter.security.api.AccessToken;
import com.cfx.usercenter.security.auth.SuccessAuthDetails;
import com.cfx.usercenter.security.core.LoginPostProcessor;
import com.cfx.usercenter.security.core.SuccessHandler;
import com.cfx.usercenter.security.core.TokenEnhancer;
import com.cfx.web.utils.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
@Slf4j
@Component
public class AuthenticationSuccessHandler implements SuccessHandler<SuccessAuthDetails, AccessToken>, InitializingBean {

    private List<LoginPostProcessor> processors;

    private TokenEnhancer tokenEnhancer;

    @Override
    public AccessToken onSuccess(SuccessAuthDetails details) {

        AccessToken success = null;

        for (LoginPostProcessor processor : getProcessors()) {
            success = processor.process(details);
        }
        if (!ObjectUtils.isEmpty(tokenEnhancer)) {
            // 如果存在则增强token
            success = tokenEnhancer.enhance(success);
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
        try {
            this.tokenEnhancer = ApplicationContextUtils.getBean(TokenEnhancer.class);
        } catch (Exception e) {
            log.debug("为获取到令牌增强器。error:{}", e.getMessage());
        }
    }
}
