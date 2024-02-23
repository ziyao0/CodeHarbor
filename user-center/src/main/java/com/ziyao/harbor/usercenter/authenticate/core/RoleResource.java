package com.ziyao.harbor.usercenter.authenticate.core;

import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ziyao zhang
 * @since 2023/9/25
 */
public final class RoleResource implements Resource, Comparable<RoleResource> {


    enum Level {
        ROOT, ROLE
    }

    // 可以授予对root级别资源的权限
    private static final Set<Permission> ROOT_LEVEL_PERMISSIONS = Sets.immutableEnumSet(
            Arrays.stream(Permission.values()).collect(Collectors.toSet()));
    // 可以授予对角色级别资源的权限
    private static final Set<Permission> ROLE_LEVEL_PERMISSIONS = Sets.immutableEnumSet(Permission.ALTER,
            Permission.DROP,
            Permission.AUTHORIZE,
            Permission.DESCRIBE);

    private static final String ROOT_NAME = "roles";
    private static final RoleResource ROOT_RESOURCE = new RoleResource();

    private final Level level;
    private final String name;

    private RoleResource() {
        level = Level.ROOT;
        name = null;
    }

    private RoleResource(String name) {
        level = Level.ROLE;
        this.name = name;
    }


    /**
     * @return the root-level resource.
     */
    public static RoleResource root() {
        return ROOT_RESOURCE;
    }

    /**
     * 创建表示单个角色的角色资源。
     *
     * @param name 角色的名称。
     * @return 角色资源实例重新请求角色。
     */
    public static RoleResource role(String name) {
        return new RoleResource(name);
    }

    @Override
    public String getName() {
        return level == Level.ROOT ? ROOT_NAME : String.format("%s/%s", ROOT_NAME, name);
    }

    @Override
    public Resource getParent() {
        if (level == Level.ROLE)
            return root();

        throw new IllegalStateException("Root-level resource can't have a parent");
    }

    @Override
    public boolean hasParent() {
        return level != Level.ROOT;
    }

    @Override
    public boolean exists() {
        //|| DatabaseDescriptor.getRoleManager().isExistingRole(this)
        return level == Level.ROOT;

    }

    @Override
    public Set<Permission> applicablePermissions() {
        return level == Level.ROOT ? ROOT_LEVEL_PERMISSIONS : ROLE_LEVEL_PERMISSIONS;
    }

    @Override
    public int compareTo(RoleResource o) {
        assert this.name != null;
        assert o.name != null;
        return this.name.compareTo(o.name);
    }
}
