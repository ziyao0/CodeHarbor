package com.ziyao.harbor.gateway.core.factory.chain;

import com.ziyao.harbor.core.error.HarborExceptions;
import com.ziyao.harbor.gateway.config.GatewayConfig;
import com.ziyao.harbor.gateway.core.SecurityPredicate;
import com.ziyao.harbor.gateway.core.token.AccessControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 请求路径黑名单
 *
 * @author ziyao
 * @since 2023/4/23
 */
@Component
public class ApiBlacklistHandler extends AbstractSecurityHandler {
    @Autowired
    private GatewayConfig gatewayConfig;

    @Override
    protected void handle(AccessControl accessControl) {
        if (getIllegalApis().isIllegal(accessControl.getApi())) {
            throw HarborExceptions.createIllegalAccessException(accessControl.getApi());
        }

    }

    @Override
    public int getOrder() {
        return 0;
    }

    private SecurityPredicate getIllegalApis() {
        return SecurityPredicate.initIllegalApis(gatewayConfig.getDefaultDisallowApis())
                .addIllegalApis(gatewayConfig.getDisallowApis());
    }
}
