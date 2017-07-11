/*
Navicat MySQL Data Transfer

Source Server         : opena
Source Server Version : 50623
Source Host           : 127.0.0.1:22306
Source Database       : emag

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2017-07-11 17:50:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `s_cfg`
-- ----------------------------
DROP TABLE IF EXISTS `s_cfg`;
CREATE TABLE `s_cfg` (
  `key_` varchar(32) NOT NULL DEFAULT '',
  `value_` varchar(32) DEFAULT NULL,
  `title` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`key_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_cfg
-- ----------------------------
INSERT INTO `s_cfg` VALUES ('bullet_level_consume', '10', '子弹消耗倍数', '2017-07-11 17:49:43', null);
INSERT INTO `s_cfg` VALUES ('bullet_level_max', '100', '子弹最大等级', '2017-06-06 10:29:31', '');
INSERT INTO `s_cfg` VALUES ('bullet_level_min', '1', '子弹最小等级', '2017-07-11 17:47:41', null);

-- ----------------------------
-- Table structure for `s_user`
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user` (
  `id` varchar(32) NOT NULL,
  `user_name` varchar(32) DEFAULT NULL,
  `user_pass` varchar(32) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `sex` int(2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `mobile` varchar(32) DEFAULT NULL,
  `qq` varchar(32) DEFAULT NULL,
  `weixin` varchar(128) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `device_code` varchar(128) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `tool_1` int(11) DEFAULT NULL,
  `tool_2` int(11) DEFAULT NULL,
  `tool_3` int(11) DEFAULT NULL,
  `tool_4` int(11) DEFAULT NULL,
  `tool_5` int(11) DEFAULT NULL,
  `tool_6` int(11) DEFAULT NULL,
  `tool_7` int(11) DEFAULT NULL,
  `tool_8` int(11) DEFAULT NULL,
  `tool_9` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_user
-- ----------------------------
INSERT INTO `s_user` VALUES ('1', 'hx', 'e10adc3949ba59abbe56e057f20f883e', '1', null, null, null, null, null, null, null, '999999999', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `s_user` VALUES ('9c012a33aa8b4ecc8aaf20ea149a6f25', 'mega', 'e10adc3949ba59abbe56e057f20f883e', '1', null, null, null, null, null, null, null, '9999', '0', '0', '0', '0', '0', '0', '0', '0', '0');
