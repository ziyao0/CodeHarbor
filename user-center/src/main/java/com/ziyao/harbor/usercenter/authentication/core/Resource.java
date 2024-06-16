package com.ziyao.harbor.usercenter.authentication.core;

import com.ziyao.security.oauth2.core.Permission;

import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2023/9/25
 */
public interface Resource {

    /**
     * @return 资源名称
     */
    String getName();

    /**
     * 获取层次结构中的下一个资源。 Call {@link Resource#hasParent()} first to make sure there is one.
     *
     * @return Resource parent (or IllegalStateException if there is none). Never a null.
     */
    Resource getParent();

    /**
     * 指示此资源在层次结构中是否具有父资源。
     * <p>
     * 请在调用 {@link Resource#getParent()}方法之前执行此检查。
     *
     * @return 资源是否具有父级。
     */
    boolean hasParent();

    /**
     * @return 资源是否存在
     */
    boolean exists();

    /**
     * 返回可能应用于此资源的权限集
     *
     * @return 可以授予对特定资源的权限
     */
    Set<Permission> applicablePermissions();
}
