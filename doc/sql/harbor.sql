/*
 Navicat MySQL Data Transfer

 Source Server         : 腾讯云
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : 124.222.42.249:33306
 Source Schema         : harbor

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 15/05/2023 14:37:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app
-- ----------------------------
DROP TABLE IF EXISTS `app`;
CREATE TABLE `app`  (
  `ID` int UNSIGNED NOT NULL COMMENT '主键id',
  `APP_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统名称',
  `URL` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统访问路径',
  `INTRODUCE` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统介绍',
  `DELETED` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '删除状态 0正常 1 删除',
  `CREATED_BY` int UNSIGNED NOT NULL COMMENT '创建人id',
  `CREATED_AT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `MODIFIED_BY` int UNSIGNED NULL DEFAULT NULL COMMENT '修改人id',
  `MODIFIED_AT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `ID`(`ID` ASC) USING BTREE,
  UNIQUE INDEX `APP_NAME`(`APP_NAME` ASC, `URL` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '应用系统' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `ID` int UNSIGNED NOT NULL COMMENT '主键id',
  `APP_ID` int UNSIGNED NOT NULL COMMENT '系统id',
  `DEPT_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `PARENT_ID` int UNSIGNED NOT NULL COMMENT '上级部门id',
  `CREATED_BY` int UNSIGNED NOT NULL COMMENT '创建人id',
  `CREATED_AT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `MODIFIED_BY` int UNSIGNED NULL DEFAULT NULL COMMENT '修改人id',
  `MODIFIED_AT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `ID`(`ID` ASC) USING BTREE,
  UNIQUE INDEX `DEPARTMENT_APP_ID_DEPT_NAME_uindex`(`APP_ID` ASC, `PARENT_ID` ASC, `DEPT_NAME` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '资源ID',
  `app_id` int UNSIGNED NOT NULL COMMENT '系统id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单编码',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源URL',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源图标',
  `parent_id` int UNSIGNED NOT NULL COMMENT '上级资源ID',
  `level` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '资源级别',
  `sort` smallint UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序',
  `created_by` int UNSIGNED NOT NULL COMMENT '创建人ID',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` int UNSIGNED NOT NULL COMMENT '更新人ID',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `menu_name_index`(`name` ASC) USING BTREE,
  INDEX `menu_app_id_code_index`(`app_id` ASC, `code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单资源表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `ID` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `APP_ID` int UNSIGNED NOT NULL COMMENT '系统id',
  `ROLE_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `ROLE_CODE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编码',
  `DESCRIPTION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `CREATED_BY` int UNSIGNED NOT NULL COMMENT '创建人id',
  `CREATED_AT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `MODIFIED_BY` int UNSIGNED NULL DEFAULT NULL COMMENT '修改人id',
  `MODIFIED_AT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `ROLE_APP_ID_ROLE_CODE_index`(`APP_ID` ASC, `ROLE_CODE` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `APP_ID` int UNSIGNED NOT NULL COMMENT '系统id',
  `ROLE_ID` int UNSIGNED NOT NULL COMMENT '角色id',
  `MENU_ID` int UNSIGNED NOT NULL COMMENT '菜单id',
  `CREATED_BY` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人id',
  `CREATED_AT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`APP_ID`, `ROLE_ID`, `MENU_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `ID` int UNSIGNED NOT NULL COMMENT '用户ID',
  `APP_ID` int UNSIGNED NOT NULL COMMENT '系统ID',
  `ACCESS_KEY` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `NICKNAME` varchar(90) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `SECRET_KEY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户凭证',
  `STATUS` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '账号状态',
  `DEPT_ID` int UNSIGNED NOT NULL COMMENT '部门ID',
  `DEPT_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `sort` smallint UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序',
  `DELETED` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '删除状态 0正常 1 删除',
  `CREATED_BY` int UNSIGNED NOT NULL COMMENT '创建人id',
  `CREATED_AT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `MODIFIED_BY` int UNSIGNED NULL DEFAULT NULL COMMENT '修改人id ',
  `MODIFIED_AT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `USER_APP_ID_ACCESS_KEY_uindex`(`APP_ID` ASC, `DEPT_ID` ASC, `ACCESS_KEY` ASC, `STATUS` ASC, `DELETED` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `APP_ID` int UNSIGNED NOT NULL COMMENT '系统id',
  `USER_ID` int UNSIGNED NOT NULL,
  `ROLE_ID` int UNSIGNED NOT NULL,
  `CREATED_AT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CREATED_BY` int UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`APP_ID`, `USER_ID`, `ROLE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
