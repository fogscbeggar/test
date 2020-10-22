/*
Navicat MySQL Data Transfer

Source Server         : 本地演示项目
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : xiaoshuodb

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2020-10-14 11:22:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admininfo
-- ----------------------------
DROP TABLE IF EXISTS `admininfo`;
CREATE TABLE `admininfo` (
  `id` int(11) NOT NULL COMMENT '管理员id',
  `realname` varchar(50) NOT NULL COMMENT '姓名',
  `sex` int(1) NOT NULL COMMENT '性别，1男，2女',
  `tel` varchar(11) DEFAULT NULL COMMENT '电话',
  `qq` varchar(16) DEFAULT NULL COMMENT 'qq',
  `weixin` varchar(255) DEFAULT NULL COMMENT '微信',
  `email` varchar(255) DEFAULT NULL COMMENT '电子邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admininfo
-- ----------------------------

-- ----------------------------
-- Table structure for admins
-- ----------------------------
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `loginname` varchar(20) NOT NULL COMMENT '登录名',
  `loginpwd` varchar(32) NOT NULL COMMENT '登录密码',
  `roleid` int(11) DEFAULT '1' COMMENT '角色id',
  `regdate` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `stat` int(1) NOT NULL DEFAULT '1' COMMENT '0不可用，1可用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginname` (`loginname`),
  KEY `fk_admins_roleid` (`roleid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='管理员表';

-- ----------------------------
-- Records of admins
-- ----------------------------
INSERT INTO `admins` VALUES ('1', 'admin', 'E10ADC3949BA59ABBE56E057F20F883E', '1', '2020-09-14 16:32:53', '1');

-- ----------------------------
-- Table structure for alibookshelf
-- ----------------------------
DROP TABLE IF EXISTS `alibookshelf`;
CREATE TABLE `alibookshelf` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `novel_id` int(11) unsigned NOT NULL DEFAULT '0',
  `chapter_id` int(11) unsigned NOT NULL DEFAULT '0',
  `chapter_key` char(20) DEFAULT NULL,
  `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '数据状态',
  `create_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户书架';

-- ----------------------------
-- Records of alibookshelf
-- ----------------------------

-- ----------------------------
-- Table structure for alicategory
-- ----------------------------
DROP TABLE IF EXISTS `alicategory`;
CREATE TABLE `alicategory` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `title` varchar(50) NOT NULL DEFAULT '' COMMENT '标题',
  `pid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '上级分类ID',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序（同级有效）',
  `meta_title` varchar(50) NOT NULL DEFAULT '' COMMENT 'SEO的网页标题',
  `meta_keywords` varchar(255) NOT NULL DEFAULT '' COMMENT '关键字',
  `meta_description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `icon` varchar(255) NOT NULL DEFAULT '' COMMENT '图标',
  `template_index` varchar(100) DEFAULT NULL COMMENT '频道页模板',
  `template_detail` varchar(100) DEFAULT NULL COMMENT '详情页模板',
  `template_filter` varchar(100) DEFAULT NULL COMMENT '筛选页模板',
  `link` varchar(255) NOT NULL DEFAULT '' COMMENT '外链地址',
  `create_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '更新时间',
  `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '数据状态',
  `type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '分类模型',
  PRIMARY KEY (`id`),
  KEY `pid` (`pid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='分类表';

-- ----------------------------
-- Table structure for alicomment
-- ----------------------------
DROP TABLE IF EXISTS `alicomment`;
CREATE TABLE `alicomment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `create_time` int(11) unsigned NOT NULL DEFAULT '0',
  `uid` int(11) unsigned NOT NULL DEFAULT '0',
  `status` tinyint(2) NOT NULL DEFAULT '1',
  `up` int(11) unsigned DEFAULT '0',
  `pid` int(11) unsigned DEFAULT '0',
  `mid` int(11) unsigned DEFAULT '0',
  `type` char(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='评论表';

-- ----------------------------
-- Records of alicomment
-- ----------------------------

-- ----------------------------
-- Table structure for alimessage
-- ----------------------------
DROP TABLE IF EXISTS `alimessage`;
CREATE TABLE `alimessage` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `create_time` int(11) unsigned NOT NULL DEFAULT '0',
  `uid` int(11) unsigned NOT NULL DEFAULT '0',
  `status` tinyint(2) NOT NULL DEFAULT '1',
  `up` int(11) unsigned DEFAULT '0',
  `pid` int(11) unsigned DEFAULT '0',
  `mid` int(11) unsigned DEFAULT '0',
  `type` char(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='消息表';

-- ----------------------------
-- Records of alimessage
-- ----------------------------

-- ----------------------------
-- Table structure for alinovel
-- ----------------------------
DROP TABLE IF EXISTS `alinovel`;
CREATE TABLE `alinovel` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `category` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '所属分类',
  `title` char(80) NOT NULL DEFAULT '' COMMENT '名称',
  `author` char(120) DEFAULT NULL COMMENT '作者',
  `pic` varchar(255) DEFAULT NULL COMMENT '图片',
  `content` text COMMENT '说明',
  `tag` varchar(255) DEFAULT NULL COMMENT '标签',
  `up` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '顶',
  `down` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '踩',
  `hits` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '浏览数量',
  `rating` char(10) NOT NULL DEFAULT '0' COMMENT '评分',
  `rating_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '评分人数',
  `serialize` tinyint(2) DEFAULT '0' COMMENT '0连载,1完结',
  `favorites` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '收藏',
  `position` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '推荐位',
  `template` varchar(100) DEFAULT NULL COMMENT '模板',
  `link` varchar(255) DEFAULT NULL COMMENT '外链地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '更新时间',
  `reurl` char(255) DEFAULT NULL COMMENT '来源',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态,0表示回收站，1正常',
  `upshelf` tinyint(1) NOT NULL DEFAULT '1' COMMENT '上架下架,0表示下架，1上架',
  `hits_day` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '日浏览',
  `hits_week` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '周浏览',
  `hits_month` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '月浏览',
  `hits_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '浏览时间',
  `word` int(11) DEFAULT '0' COMMENT '字数',
  `recommend` int(11) DEFAULT '0' COMMENT '推荐票',
  `keywords` varchar(255) DEFAULT NULL COMMENT '关键字',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `title` (`title`),
  KEY `reurl` (`reurl`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='小说表';

-- ----------------------------
-- Table structure for alinovelchapter
-- ----------------------------
DROP TABLE IF EXISTS `alinovelchapter`;
CREATE TABLE `alinovelchapter` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `novel_id` int(11) unsigned NOT NULL,
  `chapter_title` varchar(255) NOT NULL DEFAULT '' COMMENT '章节标题',
  `chapter_content` longtext COMMENT '内容',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态',
  `reurl` char(255) DEFAULT NULL COMMENT '来源',
  `source_name` char(255) DEFAULT NULL COMMENT '来源名称',
  `collect_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '采集id',
  `run_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '运行时间',
  PRIMARY KEY (`id`),
  KEY `novel_id` (`novel_id`),
  KEY `status` (`status`),
  KEY `collect_id` (`collect_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='小说章节';

-- ----------------------------
-- Records of alinovelchapter
-- ----------------------------

-- ----------------------------
-- Table structure for aliuser
-- ----------------------------
DROP TABLE IF EXISTS `aliuser`;
CREATE TABLE `aliuser` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` char(64) DEFAULT '' COMMENT '用户帐号',
  `email` char(32) NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `userpwd` char(32) NOT NULL COMMENT '密码',
  `realname` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `sex` int(1) unsigned DEFAULT '1' COMMENT '用户的性别，值为1时是男性，值为2时是女性',
  `province` char(20) DEFAULT NULL COMMENT '用户个人资料填写的省份',
  `city` char(20) DEFAULT NULL COMMENT '普通用户个人资料填写的城市',
  `country` char(20) DEFAULT NULL COMMENT '国家，如中国为CN',
  `headimgurl` varchar(255) DEFAULT NULL COMMENT '头像',
  `introduce` varchar(255) DEFAULT NULL COMMENT '介绍',
  `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '用户状态',
  `login` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '登录次数',
  `login_ip` bigint(20) NOT NULL DEFAULT '0' COMMENT '最后登录IP',
  `login_time` int(11) unsigned DEFAULT '0' COMMENT '最后登录时间',
  `exp` int(11) DEFAULT '0' COMMENT '经验',
  `integral` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `recommend` tinyint(3) DEFAULT '0' COMMENT '推荐票',
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_useranme` (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Table structure for sitesetting
-- ----------------------------
DROP TABLE IF EXISTS `sitesetting`;
CREATE TABLE `sitesetting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT '网站标题',
  `logo` varchar(255) DEFAULT NULL COMMENT '网站logo',
  `domain` varchar(255) DEFAULT NULL COMMENT '域名',
  `keywords` varchar(255) DEFAULT NULL COMMENT '关键字',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `shieldwords` text COMMENT '屏蔽字符',
  `tel` varchar(11) DEFAULT NULL COMMENT '电话',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机',
  `qq` varchar(16) DEFAULT NULL COMMENT 'qq',
  `weixin` varchar(255) DEFAULT NULL COMMENT '微信',
  `email` varchar(255) DEFAULT NULL COMMENT '电子邮箱',
  `contacts` varchar(50) DEFAULT NULL COMMENT '联系人',
  `address` varchar(50) DEFAULT NULL COMMENT '联系地址',
  `copyright` varchar(255) DEFAULT NULL COMMENT '底部信息',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='网站设置表';

-- ----------------------------
-- Records of sitesetting
-- ----------------------------
INSERT INTO `sitesetting` VALUES ('1', '我的网站', null, 'localhost', '网站关键字', '网站描述', '妈,狗,滚', '028-8888888', '13888888888', '66699955', 'caweatmeet', 'caweatmeet@126.com', '肉牛', '虚空世界', 'copyright@');

-- ----------------------------
-- View structure for chaptercount
-- ----------------------------
DROP VIEW IF EXISTS `chaptercount`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`  VIEW `chaptercount` AS SELECT
if(count(novel_id) is null,0,count(novel_id)) AS chapter_count,
alinovelchapter.novel_id
from alinovelchapter
group by novel_id ;

-- ----------------------------
-- View structure for novelinfo
-- ----------------------------
DROP VIEW IF EXISTS `novelinfo`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER  VIEW `novelinfo` AS SELECT
nov.id,
nov.title,
nov.category,
nov.author,
nov.pic,
nov.content,
nov.hits,
nov.tag,
nov.up,
nov.down,
nov.rating,
nov.rating_count,
nov.serialize,
nov.favorites,
nov.position,
nov.template,
nov.link,
nov.create_time,
nov.update_time,
nov.upshelf,
nov.reurl,
nov.recommend,
nov.`status`,
nov.hits_day,
nov.hits_month,
nov.hits_time,
nov.hits_week,
nov.word,
nov.keywords,
nov.description,
if(chap.chapter_count is null,0,chap.chapter_count) AS chapter_count,
cat.title categorytitle
from alinovel nov 
right join alicategory  cat on nov.category=cat.id 
left join chaptercount chap on chap.novel_id=nov.id ;

-- ----------------------------
-- Procedure structure for addNovels
-- ----------------------------
DROP PROCEDURE IF EXISTS `addNovels`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addNovels`()
BEGIN
declare i int default 1;
while i<=30000 DO -- 循环开始
insert into alinovel(title,keywords,author,content,description,hits,category)
values(concat('斗罗大陆',i),'斗罗大陆好，真的好',concat('会飞的牛',i),'牛B的很哦，是的','斗罗大陆好，真的好，斗罗大陆妙',ceil(1000+rand()*8999),ceil(rand()*8));
set i=i+1;
end while; -- 循环结束
end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for addUsers
-- ----------------------------
DROP PROCEDURE IF EXISTS `addUsers`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addUsers`()
BEGIN
declare i int default 1;
while i<=300 DO -- 循环开始
insert into aliuser(username,userpwd)
values(concat('jack',i),'E10ADC3949BA59ABBE56E057F20F883E');
set i=i+1;
end while; -- 循环结束
end
;;
DELIMITER ;
