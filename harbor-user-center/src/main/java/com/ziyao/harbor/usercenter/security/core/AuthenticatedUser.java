package com.ziyao.harbor.usercenter.security.core;

import com.ziyao.harbor.usercenter.security.IResource;
import com.ziyao.harbor.usercenter.security.api.UserDetails;
import lombok.Data;

import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2023/9/26
 */
@Data
public class AuthenticatedUser<T extends UserDetails> {

    private T userDetails;

    private Set<? extends IResource> resources;

}
