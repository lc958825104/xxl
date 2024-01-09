/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50636
Source Host           : localhost:3306
Source Database       : xxl_job

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2024-01-09 17:56:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `xxl_job_check`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_check`;
CREATE TABLE `xxl_job_check` (
  `child_jobid` int(11) NOT NULL COMMENT '子任务id',
  `done_jobid` int(11) NOT NULL COMMENT '已完成的父任务id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='子任务的前置任务检查。一旦检查完毕，删除此子任务行，并且运行此此任务。';
