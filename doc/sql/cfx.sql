/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50742
 Source Host           :
 Source Schema         : cfx

 Target Server Type    : MySQL
 Target Server Version : 50742
 File Encoding         : 65001

 Date: 05/05/2023 17:01:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app
-- ----------------------------
DROP TABLE IF EXISTS `app`;
CREATE TABLE `app`
(
    `ID`          int(11) UNSIGNED                                              NOT NULL COMMENT '主键id',
    `APP_NAME`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统名称',
    `URL`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统访问路径',
    `INTRODUCE`   varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统介绍',
    `DELETED`     int(1) UNSIGNED                                               NOT NULL DEFAULT 0 COMMENT '删除状态 0正常 1 删除',
    `CREATED_BY`  int(11) UNSIGNED                                              NOT NULL COMMENT '创建人id',
    `CREATED_AT`  timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `MODIFIED_BY` int(11) UNSIGNED                                              NULL     DEFAULT NULL COMMENT '修改人id',
    `MODIFIED_AT` timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `APP_NAME` (`APP_NAME`) USING BTREE,
    UNIQUE INDEX `ID` (`ID`) USING BTREE,
    UNIQUE INDEX `URL` (`URL`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '应用系统'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app
-- ----------------------------
INSERT INTO `app`
VALUES (1, '测试系统', 'http://test:8080', '测试系统', 1, 1, '2023-05-05 12:50:55', NULL, '2023-05-05 07:57:03');
INSERT INTO `app`
VALUES (1002, '内部系统', 'http://localhost:8888', '内部系统', 0, 1, '2023-04-27 01:58:06', 1, '2023-04-27 01:58:06');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`
(
    `ID`          int(10) UNSIGNED                                             NOT NULL COMMENT '主键id',
    `APP_ID`      int(10) UNSIGNED                                             NOT NULL COMMENT '系统id',
    `DEPT_NAME`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
    `PARENT_ID`   int(10) UNSIGNED                                             NOT NULL COMMENT '上级部门id',
    `CREATED_BY`  int(11) UNSIGNED                                             NOT NULL COMMENT '创建人id',
    `CREATED_AT`  timestamp                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `MODIFIED_BY` int(11) UNSIGNED                                             NULL     DEFAULT NULL COMMENT '修改人id',
    `MODIFIED_AT` timestamp                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `DEPARTMENT_APP_ID_DEPT_NAME_uindex` (`APP_ID`, `DEPT_NAME`) USING BTREE,
    UNIQUE INDEX `ID` (`ID`) USING BTREE,
    INDEX `PARENT_ID` (`PARENT_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '部门表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`
(
    `id`         int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT '资源ID',
    `app_id`     int(10) UNSIGNED                                              NOT NULL COMMENT '系统id',
    `name`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '资源名称',
    `code`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '菜单编码',
    `url`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '资源URL',
    `icon`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '资源图标',
    `parent_id`  int(10) UNSIGNED                                              NOT NULL COMMENT '上级资源ID',
    `level`      tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 1 COMMENT '资源级别',
    `sort`       smallint(5) UNSIGNED                                          NOT NULL DEFAULT 0 COMMENT '排序',
    `created_by` int(10) UNSIGNED                                              NOT NULL COMMENT '创建人ID',
    `created_at` timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` int(10) UNSIGNED                                              NOT NULL COMMENT '更新人ID',
    `updated_at` timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `menu_name_index` (`name`) USING BTREE,
    INDEX `menu_app_id_code_index` (`app_id`, `code`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '菜单资源表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `ID`          int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `APP_ID`      int(10) UNSIGNED                                              NOT NULL COMMENT '系统id',
    `ROLE_NAME`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '角色名称',
    `ROLE_CODE`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '角色编码',
    `DESCRIPTION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '角色描述',
    `CREATED_BY`  int(11) UNSIGNED                                              NOT NULL COMMENT '创建人id',
    `CREATED_AT`  timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `MODIFIED_BY` int(11) UNSIGNED                                              NULL     DEFAULT NULL COMMENT '修改人id',
    `MODIFIED_AT` timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `ROLE_APP_ID_ROLE_CODE_index` (`APP_ID`, `ROLE_CODE`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '角色表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`
(
    `APP_ID`     int(10) UNSIGNED                                             NOT NULL COMMENT '系统id',
    `ROLE_ID`    int(10) UNSIGNED                                             NOT NULL COMMENT '角色id',
    `MENU_ID`    int(10) UNSIGNED                                             NOT NULL COMMENT '菜单id',
    `CREATED_BY` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人id',
    `CREATED_AT` timestamp                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`APP_ID`, `ROLE_ID`, `MENU_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '角色菜单表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `ID`          int(11) UNSIGNED                                              NOT NULL COMMENT '用户ID',
    `APP_ID`      int(11) UNSIGNED                                              NOT NULL COMMENT '系统ID',
    `ACCESS_KEY`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '用户账号',
    `NICKNAME`    varchar(90) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '昵称',
    `SECRET_KEY`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '用户凭证',
    `STATUS`      tinyint(4) UNSIGNED                                           NOT NULL DEFAULT 0 COMMENT '账号状态',
    `DEPT_ID`     int(11) UNSIGNED                                              NOT NULL COMMENT '部门ID',
    `DEPT_NAME`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '部门名称',
    `sort`        smallint(5) UNSIGNED                                          NOT NULL DEFAULT 0 COMMENT '排序',
    `DELETED`     tinyint(3) UNSIGNED                                           NOT NULL COMMENT '删除状态 0正常 1 删除',
    `CREATED_BY`  int(11) UNSIGNED                                              NOT NULL COMMENT '创建人id',
    `CREATED_AT`  timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `MODIFIED_BY` int(11) UNSIGNED                                              NULL     DEFAULT NULL COMMENT '修改人id ',
    `MODIFIED_AT` timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `USER_APP_ID_ACCESS_KEY_uindex` (`APP_ID`, `ACCESS_KEY`) USING BTREE,
    INDEX `USER_DEPT_ID_index` (`DEPT_ID`) USING BTREE,
    INDEX `user_DELETED_index` (`DELETED`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `APP_ID`     int(10) UNSIGNED NOT NULL COMMENT '系统id',
    `USER_ID`    int(10) UNSIGNED NOT NULL,
    `ROLE_ID`    int(10) UNSIGNED NOT NULL,
    `CREATED_AT` timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `CREATED_BY` int(10) UNSIGNED NULL     DEFAULT NULL,
    PRIMARY KEY (`APP_ID`, `USER_ID`, `ROLE_ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
