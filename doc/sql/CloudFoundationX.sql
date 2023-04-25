-- auto-generated definition
create table app
(
    ID          int(11) unsigned                    not null
        primary key,
    APP_NAME    varchar(100) charset latin1         not null,
    URL         varchar(100) charset latin1         not null,
    INTRODUCE   varchar(500) charset latin1         not null,
    CREATED_BY  int(11) unsigned                    not null comment '创建人id',
    CREATED_AT  timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    MODIFIED_BY int(11) unsigned                    null comment '修改人id',
    MODIFIED_AT timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint APP_NAME
        unique (APP_NAME),
    constraint ID
        unique (ID),
    constraint URL
        unique (URL)
)
    comment '应用系统';

-- auto-generated definition
create table department
(
    ID          int unsigned                        not null
        primary key,
    APP_ID      int unsigned                        not null,
    DEPT_NAME   varchar(50)                         not null,
    PARENT_ID   int unsigned                        not null,
    CREATED_BY  int(11) unsigned                    not null comment '创建人id',
    CREATE_TIME timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    MODIFIED_BY int(11) unsigned                    null comment '修改人id',
    MODIFY_TIME timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint DEPARTMENT_APP_ID_DEPT_NAME_uindex
        unique (APP_ID, DEPT_NAME),
    constraint ID
        unique (ID)
);

create index PARENT_ID
    on department (PARENT_ID);

-- auto-generated definition
create table user
(
    ID          int(11) unsigned                            not null comment '用户ID;主键ID'
        primary key,
    APP_ID      int(11) unsigned                            not null comment '系统ID',
    ACCESS_KEY  varchar(50) charset latin1                  not null comment '用户账号;账号',
    NICKNAME    varchar(90) charset latin1                  null comment '昵称;昵称',
    SECRET_KEY  varchar(255) charset latin1                 null comment '用户凭证;凭证',
    STATUS      tinyint           default 0                 not null comment '账号状态;账号状态',
    DEPT_ID     int(11) unsigned                            not null comment '部门ID',
    DEPT_NAME   varchar(50) charset latin1                  not null,
    sort        smallint unsigned default 0                 not null comment '排序',
    CREATED_BY  int(11) unsigned                            not null comment '创建人id',
    CREATE_TIME timestamp         default CURRENT_TIMESTAMP not null comment '创建时间',
    MODIFIED_BY int(11) unsigned                            null comment '修改人id ',
    MODIFY_TIME timestamp         default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint USER_APP_ID_ACCESS_KEY_uindex
        unique (APP_ID, ACCESS_KEY)
)
    comment '用户表';

create index USER_DEPT_ID_index
    on user (DEPT_ID);

-- auto-generated definition
create table role
(
    ID          int unsigned auto_increment comment '角色ID'
        primary key,
    APP_ID      int unsigned                        not null comment '系统id',
    ROLE_NAME   varchar(50)                         not null comment '角色名称',
    ROLE_CODE   varchar(50)                         not null comment '角色编码',
    DESCRIPTION varchar(255)                        null comment '角色描述',
    CREATED_BY  int(11) unsigned                    not null comment '创建人id',
    CREATED_AT  timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    MODIFIED_BY int(11) unsigned                    null comment '修改人id',
    MODIFIED_AT timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint ROLE_APP_ID_ROLE_CODE_index
        unique (APP_ID, ROLE_CODE)
)
    comment '角色表';

-- auto-generated definition
create table menu
(
    id         int unsigned auto_increment comment '资源ID'
        primary key,
    app_id     int unsigned                                not null,
    name       varchar(50)                                 not null comment '资源名称',
    code       varchar(50)                                 not null comment '菜单编码',
    url        varchar(255)                                null comment '资源URL',
    icon       varchar(50)                                 null comment '资源图标',
    parent_id  int unsigned                                not null comment '上级资源ID',
    level      tinyint unsigned  default 1                 not null comment '资源级别',
    sort       smallint unsigned default 0                 not null comment '排序',
    created_by int unsigned                                not null comment '创建人ID',
    created_at timestamp         default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_by int unsigned                                not null comment '更新人ID',
    updated_at timestamp         default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '菜单资源表';

create index menu_app_id_code_index
    on menu (app_id, code);

create index menu_name_index
    on menu (name);

-- auto-generated definition
create table user_role
(
    APP_ID     int unsigned                        not null comment '系统id',
    USER_ID    int unsigned                        not null,
    ROLE_ID    int unsigned                        not null,
    CREATED_AT timestamp default CURRENT_TIMESTAMP not null,
    CREATED_BY int unsigned                        null,
    primary key (APP_ID, USER_ID, ROLE_ID)
);

-- auto-generated definition
create table role_menu
(
    APP_ID     int unsigned                        not null comment '系统id',
    ROLE_ID    int unsigned                        not null comment '角色id',
    MENU_ID    int unsigned                        not null comment '菜单id',
    CREATED_BY varchar(50)                         not null comment '创建人id',
    CREATED_AT timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    primary key (APP_ID, ROLE_ID, MENU_ID)
);

