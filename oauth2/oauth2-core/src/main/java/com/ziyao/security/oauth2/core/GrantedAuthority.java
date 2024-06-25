package com.ziyao.security.oauth2.core;

import com.ziyao.harbor.core.utils.Collections;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author ziyao
 * @since 2024/06/11 10:58:39
 */
public interface GrantedAuthority extends Serializable {

    /**
     * 获取权限信息
     */
    String getAuthority();

    /**
     * 返回当前角色的操作权限
     *
     * @return 如果返回结果为null获取为集合为空，则代表能操作该角色下的任何功能
     */
    Collection<Permission> getPermissions();

    /**
     * 是否要继续进行授权
     */
    default boolean isContinued() {
        return Collections.nonNull(getPermissions());
    }
}
