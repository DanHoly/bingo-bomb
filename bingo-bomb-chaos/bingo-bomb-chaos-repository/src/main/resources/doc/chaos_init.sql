/*
Navicat MySQL Data Transfer

Source Server         : dev_master
Source Server Version : 50633
Source Host           : 10.20.33.33:3306
Source Database       : chaos

Target Server Type    : MYSQL
Target Server Version : 50633
File Encoding         : 65001

Date: 2017-08-18 17:03:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '资源名称',
  `type` varchar(50) DEFAULT NULL COMMENT '类型 1菜单 2 按钮 3 超链接',
  `url` varchar(200) DEFAULT NULL COMMENT '资源路径',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父ID',
  `parent_ids` varchar(100) DEFAULT NULL,
  `permission` varchar(100) DEFAULT NULL COMMENT '权限',
  `active` tinyint(1) DEFAULT '1' COMMENT '0已删除 1有效',
  `version` bigint(20) DEFAULT NULL COMMENT '数据版本',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` decimal(38,0) DEFAULT NULL COMMENT '创建人Id',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user_id` decimal(38,0) DEFAULT NULL COMMENT '更新人Id',
  PRIMARY KEY (`id`),
  KEY `idx_sys_resource_parent_id` (`parent_id`),
  KEY `idx_sys_resource_parent_ids` (`parent_ids`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', '资源', 'menu', '', '0', '0/', '', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('11', '组织机构管理', 'menu', '/organization', '1', '0/1/', 'organization:*', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('12', '组织机构新增', 'button', '', '11', '0/1/11/', 'organization:create', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('13', '组织机构修改', 'button', '', '11', '0/1/11/', 'organization:update', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('14', '组织机构删除', 'button', '', '11', '0/1/11/', 'organization:delete', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('15', '组织机构查看', 'button', '', '11', '0/1/11/', 'organization:view', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('21', '用户管理', 'menu', '/user', '1', '0/1/', 'user:*', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('22', '用户新增', 'button', '', '21', '0/1/21/', 'user:create', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('23', '用户修改', 'button', '', '21', '0/1/21/', 'user:update', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('24', '用户删除', 'button', '', '21', '0/1/21/', 'user:delete', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('25', '用户查看', 'button', '', '21', '0/1/21/', 'user:view', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('31', '资源管理', 'menu', '/resource', '1', '0/1/', 'resource:*', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('32', '资源新增', 'button', '', '31', '0/1/31/', 'resource:create', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('33', '资源修改', 'button', '', '31', '0/1/31/', 'resource:update', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('34', '资源删除', 'button', '', '31', '0/1/31/', 'resource:delete', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('35', '资源查看', 'button', '', '31', '0/1/31/', 'resource:view', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('41', '角色管理', 'menu', '/role', '1', '0/1/', 'role:*', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('42', '角色新增', 'button', '', '41', '0/1/41/', 'role:create', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('43', '角色修改', 'button', '', '41', '0/1/41/', 'role:update', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('44', '角色删除', 'button', '', '41', '0/1/41/', 'role:delete', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('45', '角色查看', 'button', '', '41', '0/1/41/', 'role:view', null, null, null, null, null, null);
INSERT INTO `sys_resource` VALUES ('51', '会话管理', 'menu', '/sessions', '1', '0/1/', 'session:*', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `resource_ids` varchar(100) DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL COMMENT '0已删除 1有效',
  `version` bigint(20) DEFAULT NULL COMMENT '数据版本',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` decimal(38,0) DEFAULT NULL COMMENT '创建人Id',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user_id` decimal(38,0) DEFAULT NULL COMMENT '更新人Id',
  PRIMARY KEY (`id`),
  KEY `idx_sys_role_resource_ids` (`resource_ids`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', '超级管理员', '11,21,31,41,51', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(100) DEFAULT NULL COMMENT '盐',
  `role_ids` varchar(100) DEFAULT NULL COMMENT '角色ID，逗号分隔',
  `active` tinyint(1) DEFAULT '1' COMMENT '0已删除 1有效',
  `version` bigint(20) DEFAULT NULL COMMENT '数据版本',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` decimal(38,0) DEFAULT NULL COMMENT '创建人Id',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user_id` decimal(38,0) DEFAULT NULL COMMENT '更新人Id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_user_username` (`user_name`),
  KEY `idx_sys_user_organization_id` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '1', 'admin', 'd3c59d25033dbf980d29554025c23a75', '8d78869f470951332959580424d4bf4f', '1', '1', '1', '2017-08-18 10:55:13', '11231231', '2017-08-18 10:55:26', '1');
