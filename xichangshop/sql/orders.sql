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

 Date: 26/11/2024 15:28:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int NOT NULL,
  `user_id` int NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL COMMENT '状态，1待付款、2已付款、3已发货、4已完成、5已取消',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '订单总价',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
