package com.ziyao.harbor.usercenter.authenticate;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2023/9/25
 */
public enum Permission {

    CREATE, // 创建

    ALTER,//

    DROP, // 删除

    SELECT,// 查询
    MODIFY, // 在数据资源上插入、更新、删除

    // permission management
    AUTHORIZE, // 授予和撤销权限或角色

    DESCRIBE, // 在root-level角色资源上需要列出所有角色

    // UDF permissions
    EXECUTE;  // 执行

    public static final Set<Permission> ALL =
            Sets.immutableEnumSet(EnumSet.range(Permission.CREATE, Permission.EXECUTE));
    public static final Set<Permission> NONE = ImmutableSet.of();
}
