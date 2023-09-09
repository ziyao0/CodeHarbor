package com.ziyao.harbor.gateway.core.factory;

import com.ziyao.harbor.core.factory.AbstractFactory;
import com.ziyao.harbor.core.utils.Collections;
import com.ziyao.harbor.gateway.core.factory.chain.AbstractSecurityHandler;
import com.ziyao.harbor.gateway.core.token.AccessControl;
import com.ziyao.harbor.gateway.support.ApplicationContextUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Component
public class AccessChainFactory extends AbstractFactory<AccessControl> {


    @PostConstruct
    @Override
    protected void init() {
        List<AbstractSecurityHandler> abstractSecurityHandlers = ApplicationContextUtils.getBeansOfType(AbstractSecurityHandler.class);
        if (!Collections.isEmpty(abstractSecurityHandlers)) {
            abstractSecurityHandlers.stream().sorted(Comparator.comparing(AbstractSecurityHandler::getOrder))
                    .forEach(abstractSecurityHandler -> getAbstractHandler().linkWith(abstractSecurityHandler));
        }
    }
}
