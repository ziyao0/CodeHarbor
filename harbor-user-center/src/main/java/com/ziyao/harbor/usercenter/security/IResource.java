package com.ziyao.harbor.usercenter.security;

import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2023/9/25
 */
public interface IResource {

    /**
     * @return printable name of the resource.
     */
    String getName();

    /**
     * Gets next resource in the hierarchy. Call hasParent first to make sure there is one.
     *
     * @return Resource parent (or IllegalStateException if there is none). Never a null.
     */
    IResource getParent();

    /**
     * Indicates whether or not this resource has a parent in the hierarchy.
     * <p>
     * Please perform this check before calling getParent() method.
     *
     * @return Whether or not the resource has a parent.
     */
    boolean hasParent();

    /**
     * @return Whether or not this resource exists in Cassandra.
     */
    boolean exists();

    /**
     * Returns the set of Permissions that may be applied to this resource
     * <p>
     * Certain permissions are not applicable to particular types of resources.
     * For instance, it makes no sense to talk about CREATE permission on table, or SELECT on a Role.
     * Here we filter a set of permissions depending on the specific resource they're being applied to.
     * This is necessary because the CQL syntax supports ALL as wildcard, but the set of permissions that
     * should resolve to varies by IResource.
     *
     * @return the permissions that may be granted on the specific resource
     */
    Set<Permission> applicablePermissions();
}
