/*
 Navicat Premium Data Transfer

 Source Server         : 我的服务器
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 47.100.173.103:3306
 Source Schema         : coolq

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 15/05/2020 11:11:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名字',
  `cover` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '封面',
  `begin_time` datetime DEFAULT NULL COMMENT '开始时间',
  `over_time` datetime DEFAULT NULL COMMENT '结束时间',
  `importance` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '重要性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for admin_power
-- ----------------------------
DROP TABLE IF EXISTS `admin_power`;
CREATE TABLE `admin_power` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `qq` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '管理员qq号 -- 只有管理员可以控制所有权限',
  `admin` int(1) NOT NULL COMMENT '购买者/系统管理员',
  `status` int(1) NOT NULL COMMENT '开关',
  `my_group` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '购买者绑定的群号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for checkpoint
-- ----------------------------
DROP TABLE IF EXISTS `checkpoint`;
CREATE TABLE `checkpoint` (
  `id` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '关卡名字',
  `sort` int(255) DEFAULT NULL COMMENT '排序号',
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '说明',
  `score` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '最高分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for count
-- ----------------------------
DROP TABLE IF EXISTS `count`;
CREATE TABLE `count` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `qq_group` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT 'QQ群号',
  `today` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '年月日',
  `middle_one` int(255) NOT NULL COMMENT '大小姐单抽总量',
  `middle_ten` int(255) NOT NULL COMMENT '大小姐十连总量',
  `special_one` int(255) NOT NULL COMMENT '魔法少女单抽总量',
  `special_ten` int(255) NOT NULL COMMENT '魔法少女十连总量',
  `custom_one` int(255) NOT NULL COMMENT '魔女单抽总量',
  `custom_ten` int(255) NOT NULL COMMENT '魔女十连总量',
  `high_one` int(255) NOT NULL COMMENT '公主单抽总量',
  `high_ten` int(255) NOT NULL COMMENT '公主十连总量',
  `se_count` int(255) NOT NULL COMMENT '色图总量',
  `ai_count` int(255) NOT NULL COMMENT 'ai总量',
  `eq_count` int(255) NOT NULL COMMENT '装备查询总量',
  `aq_count` int(255) NOT NULL COMMENT '问/答总量',
  `sign_count` int(255) NOT NULL COMMENT '签到总量',
  `up_count` int(255) NOT NULL COMMENT '查询up总量',
  `json_flush` int(255) DEFAULT NULL COMMENT '刷新',
  `festival_one` int(255) NOT NULL COMMENT '梦想单抽',
  `festival_ten` int(255) NOT NULL COMMENT '梦想十连',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5530 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for files
-- ----------------------------
DROP TABLE IF EXISTS `files`;
CREATE TABLE `files` (
  `uuid` varchar(40) COLLATE utf8_unicode_ci NOT NULL COMMENT 'id',
  `filename` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '文件名',
  `suffix` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '文件后缀',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for group_power
-- ----------------------------
DROP TABLE IF EXISTS `group_power`;
CREATE TABLE `group_power` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `qq_group` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT 'QQ群',
  `fun_id` int(255) NOT NULL COMMENT '功能id--禁止/启用 群内某功能',
  `status` int(1) NOT NULL COMMENT '开关',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1081 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for person
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `qq` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `star` int(10) NOT NULL COMMENT '星数量',
  `signin` int(11) NOT NULL,
  `draw` int(1) NOT NULL COMMENT '抽签',
  `sign_time` datetime DEFAULT NULL,
  PRIMARY KEY (`qq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for pray
-- ----------------------------
DROP TABLE IF EXISTS `pray`;
CREATE TABLE `pray` (
  `qq` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `highqujian` int(2) NOT NULL,
  `highbaodi` int(1) NOT NULL,
  `customqujian` int(1) NOT NULL,
  `custombaodi` int(1) NOT NULL,
  `middlequjian` int(2) NOT NULL,
  `middlebaodi` int(1) NOT NULL,
  `specialqujian` int(2) NOT NULL,
  `specialbaodi` int(1) NOT NULL,
  `festivalqujian` int(2) DEFAULT NULL,
  `festivalbaodi` int(1) DEFAULT NULL,
  PRIMARY KEY (`qq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for qq_power
-- ----------------------------
DROP TABLE IF EXISTS `qq_power`;
CREATE TABLE `qq_power` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `qq` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT 'qq号--禁止/启用 某人使用某些功能',
  `fun_id` int(255) DEFAULT NULL COMMENT '功能id',
  `status` int(1) DEFAULT NULL COMMENT '开关',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for qqgroup
-- ----------------------------
DROP TABLE IF EXISTS `qqgroup`;
CREATE TABLE `qqgroup` (
  `groupid` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '群号',
  `signin_count` int(11) NOT NULL COMMENT '签到人数',
  PRIMARY KEY (`groupid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `id` varchar(40) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT 'id',
  `user_id` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户id',
  `strategy_id` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '攻略id',
  `operation` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作',
  `operation_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for strategy
-- ----------------------------
DROP TABLE IF EXISTS `strategy`;
CREATE TABLE `strategy` (
  `id` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `author` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户id',
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '标题',
  `content` longtext COLLATE utf8_unicode_ci COMMENT '富文本内容',
  `push_time` datetime DEFAULT NULL COMMENT '发布时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(40) COLLATE utf8_unicode_ci NOT NULL COMMENT 'id',
  `nick_name` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '昵称',
  `account` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账号',
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '密码',
  `role` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色',
  `qq` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'QQ号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;
