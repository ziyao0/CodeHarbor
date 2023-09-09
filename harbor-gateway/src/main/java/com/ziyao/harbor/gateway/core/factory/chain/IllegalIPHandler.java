package com.ziyao.harbor.gateway.core.factory.chain;

import com.ziyao.harbor.gateway.core.token.AccessControl;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class IllegalIPHandler extends AbstractSecurityHandler {
    @Override
    protected void handle(AccessControl accessControl) throws Exception {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
