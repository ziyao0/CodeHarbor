package com.ziyao.cfx.usercenter.security.core;

import com.ziyao.cfx.usercenter.security.api.AccessToken;
import com.ziyao.cfx.usercenter.security.auth.SuccessAuthDetails;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
public interface LoginPostProcessor extends GlobalProcessor<SuccessAuthDetails, AccessToken> {


}
