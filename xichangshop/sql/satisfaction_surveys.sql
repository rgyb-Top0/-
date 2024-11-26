/*
 Navicat Premium Data Transfer

 Source Server         : db001
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : localhost:3306
 Source Schema         : xichangshop

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 26/11/2024 15:28:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for satisfaction_surveys
-- ----------------------------
DROP TABLE IF EXISTS `satisfaction_surveys`;
CREATE TABLE `satisfaction_surveys`  (
  `id` int NOT NULL,
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `satisfaction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '满意度',
  `comments` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评价',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of satisfaction_surveys
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
