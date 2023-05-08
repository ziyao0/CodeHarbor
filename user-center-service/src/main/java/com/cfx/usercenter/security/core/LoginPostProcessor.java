package com.cfx.usercenter.security.core;

import com.cfx.usercenter.security.api.AccessToken;
import com.cfx.usercenter.security.auth.SuccessAuthDetails;

/**
 * @author Eason
 * @since 2023/5/8
 */
public interface LoginPostProcessor extends GlobalProcessor<SuccessAuthDetails, AccessToken> {


}
