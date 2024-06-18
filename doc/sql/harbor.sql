create table if not exists access_restriction
(
    id      int          not null comment '主键'
        primary key,
    rule_id int unsigned not null,
    value   varchar(200) not null comment '规则对应的value值',
    remark  varchar(255) not null
);

create index access_restriction_rule_id_index
    on access_restriction (rule_id);

create table if not exists application
(
    app_id                    int unsigned             not null comment '主键id'
        primary key,
    app_type                  int unsigned default '1' not null comment '应用类型 0内部系统应用 1三方平台应用 ',
    authorization_grant_types varchar(200)             not null comment '客户端允许的授权类型',
    scopes                    varchar(1000)            null comment '授权范围',
    state                     int unsigned default '0' not null comment '删除状态 0正常 1失效',
    issued_at                 timestamp                null comment '颁发时间',
    app_secret                varchar(200)             null comment '应用秘钥',
    app_secret_expires_at     timestamp                null comment '应用秘钥过期时间',
    app_name                  varchar(100)             not null comment '应用名称',
    redirect_uri              varchar(100)             not null comment '系统重定向路径',
    post_logout_redirect_uri  varchar(100)             not null,
    token_settings            varchar(1000)            null,
    remark                    varchar(500)             not null comment '系统简介'
)
    comment '应用系统' row_format = DYNAMIC;

create index application_app_type_state_index
    on application (app_type, state);

create table if not exists authorization
(
    id                            int unsigned             not null comment '主键id'
        primary key,
    appid                         int unsigned             not null comment '应用系统id',
    user_id                       int unsigned             not null,
    authorization_grant_type      varchar(50)              not null comment '授权类型',
    authorized_scopes             varchar(200)             null comment '授权范围',
    attributes                    varchar(255)             null comment '授权附加属性',
    state                         int unsigned default '1' not null comment '状态 1正常 2失效',
    authorization_code_value      varchar(100)             null comment '授权码值',
    authorization_code_issued_at  timestamp                null comment '授权码颁发时间',
    authorization_code_expires_at timestamp                null comment '授权码失效时间',
    authorization_code_metadata   varchar(1000)            null comment '授权码元数据信息',
    access_token_value            varchar(255)             null comment '认证令牌值',
    access_token_issued_at        timestamp                null comment '认证令牌颁发时间',
    access_token_expires_at       timestamp                null comment '认证临牌失效时间',
    access_token_metadata         varchar(1000)            null comment '认证令牌元数据信息',
    access_token_type             varchar(100)             null comment '认证令牌类型',
    access_token_scopes           varchar(100)             null comment '认证令牌范围',
    refresh_token_value           varchar(255)             null comment '刷新令牌值',
    refresh_token_issued_at       timestamp                null comment '刷新令牌颁发时间',
    refresh_token_expires_at      timestamp                null comment '刷新令牌过期时间',
    refresh_token_metadata        varchar(1000)            null comment '元数据信息',
    constraint oauth2_authorization_pk
        unique (authorization_code_value),
    constraint oauth2_authorization_pk_2
        unique (access_token_value),
    constraint oauth2_authorization_pk_3
        unique (refresh_token_value)
);

create index oauth2_authorization_appid_user_id_index
    on authorization (appid, user_id);

create table if not exists department
(
    id          int unsigned                        not null comment '主键id'
        primary key,
    app_id      int unsigned                        not null comment '系统id',
    dept_name   varchar(50)                         not null comment '部门名称',
    parent_id   int unsigned                        not null comment '上级部门id',
    created_by  int unsigned                        not null comment '创建人id',
    created_at  timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    modified_by int unsigned                        null comment '修改人id',
    modified_at timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint department_app_id_dept_name_index
        unique (app_id, dept_name),
    constraint id
        unique (id)
)
    comment '部门表' row_format = DYNAMIC;

create index parent_id
    on department (parent_id);

create table if not exists menu
(
    id         int unsigned auto_increment comment '资源ID'
        primary key,
    app_id     int unsigned                                not null comment '系统id',
    name       varchar(50)                                 not null comment '资源名称',
    code       varchar(50)                                 not null comment '菜单编码',
    url        varchar(255)                                null comment '资源URL',
    icon       varchar(50)                                 null comment '资源图标',
    parent_id  int unsigned                                not null comment '上级资源ID',
    level      tinyint unsigned  default '1'               not null comment '资源级别',
    sort       smallint unsigned default '0'               not null comment '排序',
    created_by int unsigned                                not null comment '创建人ID',
    created_at timestamp         default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_by int unsigned                                not null comment '更新人ID',
    updated_at timestamp         default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '菜单资源表' row_format = DYNAMIC;

create index menu_app_id_code_index
    on menu (app_id, code);

create index menu_name_index
    on menu (name);

create table if not exists role
(
    id           int unsigned                                     not null comment '角色id'
        primary key,
    name         varchar(50)                                      not null comment '角色名称',
    role         varchar(50)                                      not null comment '角色编码',
    type         int unsigned                                     not null,
    category     int unsigned           default '1'               null comment '角色类别 1 权限角色 2 组织角色',
    access_level enum ('read', 'write') default 'write'           not null invisible,
    active       tinyint(1)             default 1                 not null comment '1 启用 0禁用',
    description  varchar(255)                                     null comment '角色描述',
    created_by   int unsigned                                     not null comment '创建人id',
    created_at   timestamp              default CURRENT_TIMESTAMP not null comment '创建时间',
    modified_by  int unsigned                                     null comment '修改人id',
    modified_at  timestamp              default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint role_role_code_index
        unique (role, category, active, access_level)
)
    comment '角色表' row_format = DYNAMIC;

create table if not exists role_menu
(
    app_id     int unsigned                        not null comment '系统id',
    role_id    int unsigned                        not null comment '角色id',
    menu_id    int unsigned                        not null comment '菜单id',
    created_at timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    primary key (app_id, role_id, menu_id)
)
    comment '角色菜单表' row_format = DYNAMIC;

create table if not exists user
(
    id            int unsigned                                   not null comment '用户id'
        primary key,
    username      varchar(50)                                    not null comment '用户账号',
    nickname      varchar(90)                                    null comment '昵称',
    password      varchar(255)                                   null comment '用户凭证',
    mobile        varchar(11)                                    null comment '手机号',
    id_card_name  varchar(50)                                    null comment '姓名',
    id_card_no    varchar(18)                                    null comment '身份证号',
    gender        enum ('M', 'F', 'O') default 'O'               null comment '性别（M: 男, F: 女, O: 其他）',
    date_of_birth timestamp                                      null comment '出生日期',
    address       varchar(500)                                   null comment '地址',
    status        int unsigned         default '1'               not null comment '账号状态 1 正常 2 失效 3 禁用',
    sort          smallint unsigned    default '0'               not null comment '排序',
    deleted       int unsigned         default '0'               not null comment '删除状态 0正常 1 删除',
    last_login    timestamp                                      null comment '最后登录时间',
    created_by    int unsigned                                   not null comment '创建人id',
    created_at    timestamp            default CURRENT_TIMESTAMP not null comment '创建时间',
    modified_by   int unsigned                                   null comment '修改人id ',
    modified_at   timestamp            default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint user_pk
        unique (username)
)
    comment '用户表' row_format = DYNAMIC;

create index user_username_deleted_index
    on user (username, deleted);

create table if not exists user_role
(
    user_id      int unsigned                        not null,
    role_id      int unsigned                        not null,
    role         varchar(50)                         not null,
    category     int unsigned                        not null,
    access_level enum ('read', 'write')              not null,
    created_at   timestamp default CURRENT_TIMESTAMP not null,
    primary key (user_id, role_id)
)
    row_format = DYNAMIC;

