/*
 Navicat Premium Data Transfer

 Source Server         : 165腾讯云
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : 115.159.2.165:3306
 Source Schema         : short_link_02

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 15/11/2020 13:56:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for min_short_url_
-- ----------------------------
DROP TABLE IF EXISTS `min_short_url_`;
CREATE TABLE `min_short_url_`  (
  `id` bigint(0) NOT NULL COMMENT '编号',
  `username` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '长链接',
  `short_url` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '短链接',
  `client_count` int(0) NULL DEFAULT NULL COMMENT '点击次数',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_click_time` datetime(0) NULL DEFAULT NULL COMMENT '最新的点击时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `short_url`(`short_url`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for short_url_
-- ----------------------------
DROP TABLE IF EXISTS `short_url_`;
CREATE TABLE `short_url_`  (
  `id` bigint(0) NOT NULL COMMENT '编号',
  `username` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '长链接',
  `short_url` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '短链接',
  `client_count` int(0) NULL DEFAULT NULL COMMENT '点击次数',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_click_time` datetime(0) NULL DEFAULT NULL COMMENT '最新的点击时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `short_url`(`short_url`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
