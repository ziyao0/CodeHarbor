package com.ziyao.harbor.usercenter.security.core;

import com.ziyao.harbor.usercenter.security.api.AccessToken;
import com.ziyao.harbor.usercenter.security.auth.SuccessAuthDetails;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
public interface LoginPostProcessor extends GlobalProcessor<SuccessAuthDetails, AccessToken> {


}
