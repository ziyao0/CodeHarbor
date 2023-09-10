package com.ziyao.harbor.gateway.core.factory.chain;

import com.ziyao.harbor.core.Ordered;
import com.ziyao.harbor.gateway.core.token.AccessControl;
import org.springframework.stereotype.Component;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Component
public class IPBlacklistHandler extends AbstractSecurityHandler {
    @Override
    protected void handle(AccessControl accessControl) throws Exception {

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
