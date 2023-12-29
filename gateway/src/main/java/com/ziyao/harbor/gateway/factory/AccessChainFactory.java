package com.ziyao.harbor.gateway.factory;

import com.ziyao.harbor.core.factory.AbstractFactory;
import com.ziyao.harbor.core.utils.Collections;
import com.ziyao.harbor.gateway.factory.chain.AbstractSecurityHandler;
import com.ziyao.harbor.gateway.core.token.DefaultAccessToken;
import com.ziyao.harbor.gateway.support.ApplicationContextUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Component
public class AccessChainFactory extends AbstractFactory<DefaultAccessToken> {


    private AbstractSecurityHandler abstractSecurityHandler;


    public void filter(DefaultAccessToken defaultAccessToken) {
        abstractSecurityHandler.handle(defaultAccessToken);
    }


    @PostConstruct
    @Override
    protected void init() {
        List<AbstractSecurityHandler> abstractSecurityHandlers = ApplicationContextUtils.getBeansOfType(AbstractSecurityHandler.class);
        if (!Collections.isEmpty(abstractSecurityHandlers)) {
            abstractSecurityHandlers.stream().sorted(Comparator.comparing(AbstractSecurityHandler::getOrder))
                    .forEach(abstractSecurityHandler -> {
                                if (Objects.isNull(this.abstractSecurityHandler)) {
                                    this.abstractSecurityHandler = abstractSecurityHandler;
                                } else {
                                    this.abstractSecurityHandler.linkWith(abstractSecurityHandler);
                                }
                            }
                    );
        }
    }
}
