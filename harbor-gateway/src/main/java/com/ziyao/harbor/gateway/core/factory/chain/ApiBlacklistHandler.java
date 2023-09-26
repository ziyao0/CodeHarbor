package com.ziyao.harbor.gateway.core.factory.chain;

import com.ziyao.harbor.core.error.Exceptions;
import com.ziyao.harbor.gateway.config.GatewayConfig;
import com.ziyao.harbor.gateway.core.support.SecurityPredicate;
import com.ziyao.harbor.gateway.core.token.AccessControl;
import org.springframework.stereotype.Component;

/**
 * 请求路径黑名单
 *
 * @author ziyao
 * @since 2023/4/23
 */
@Component
public class ApiBlacklistHandler extends AbstractSecurityHandler {
    private final GatewayConfig gatewayConfig;

    public ApiBlacklistHandler(GatewayConfig gatewayConfig) {
        this.gatewayConfig = gatewayConfig;
    }

    @Override
    public void handle(AccessControl accessControl) {
        if (getIllegalApis().isIllegal(accessControl.getApi())) {
            throw Exceptions.createIllegalAccessException(accessControl.getApi());
        }
        this.checkedNextHandler(accessControl);
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
