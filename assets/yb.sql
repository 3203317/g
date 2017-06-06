/*
Navicat MySQL Data Transfer

Source Server         : opena
Source Server Version : 50623
Source Host           : 127.0.0.1:22306
Source Database       : yb

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2017-06-06 16:18:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `s_role`
-- ----------------------------
DROP TABLE IF EXISTS `s_role`;
CREATE TABLE `s_role` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `role_name` varchar(32) DEFAULT NULL,
  `role_desc` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_role
-- ----------------------------
INSERT INTO `s_role` VALUES ('1', 'superman', '超级管理员', '2017-06-06 10:29:31', '1');
INSERT INTO `s_role` VALUES ('2', 'guest', '游客', '2017-06-06 10:29:44', '0');

-- ----------------------------
-- Table structure for `s_user`
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `p_id` varchar(32) DEFAULT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  `user_pass` varchar(64) DEFAULT NULL,
  `sex` int(2) DEFAULT NULL,
  `mobile` varchar(64) DEFAULT NULL,
  `qq` varchar(64) DEFAULT NULL,
  `weixin` varchar(64) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `device_code` varchar(255) DEFAULT NULL,
  `nickname` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_user
-- ----------------------------
INSERT INTO `s_user` VALUES ('1', 'superman', 'hx', '2017-06-06 10:29:31', '1', null, null, null, null, null, null, null, null);
INSERT INTO `s_user` VALUES ('2', 'guest', 'wy', '2017-06-06 10:29:44', '0', null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `s_user_friends`
-- ----------------------------
DROP TABLE IF EXISTS `s_user_friends`;
CREATE TABLE `s_user_friends` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `friend_a` varchar(32) DEFAULT NULL,
  `friend_b` varchar(64) DEFAULT NULL,
  `friend_a_alias` varchar(64) DEFAULT NULL,
  `friend_b_alias` varchar(64) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_user_friends
-- ----------------------------
