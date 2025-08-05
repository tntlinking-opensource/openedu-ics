-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: teach_paike
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `gen_table`
--

DROP TABLE IF EXISTS `gen_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `gen_table` (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `tpl_web_type` varchar(30) DEFAULT '' COMMENT '前端模板类型（element-ui模版 element-plus模版）',
  `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代码生成业务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table`
--

LOCK TABLES `gen_table` WRITE;
/*!40000 ALTER TABLE `gen_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `gen_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_table_column`
--

DROP TABLE IF EXISTS `gen_table_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `gen_table_column` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint(20) DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代码生成业务表字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table_column`
--

LOCK TABLES `gen_table_column` WRITE;
/*!40000 ALTER TABLE `gen_table_column` DISABLE KEYS */;
/*!40000 ALTER TABLE `gen_table_column` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Blob类型的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_blob_triggers`
--

LOCK TABLES `qrtz_blob_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_blob_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_blob_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS `qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日历信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_calendars`
--

LOCK TABLES `qrtz_calendars` WRITE;
/*!40000 ALTER TABLE `qrtz_calendars` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_calendars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Cron类型的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_cron_triggers`
--

LOCK TABLES `qrtz_cron_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_cron_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_cron_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_fired_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint(13) NOT NULL COMMENT '触发的时间',
  `sched_time` bigint(13) NOT NULL COMMENT '定时器制定的时间',
  `priority` int(11) NOT NULL COMMENT '优先级',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `job_name` varchar(200) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='已触发的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_fired_triggers`
--

LOCK TABLES `qrtz_fired_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_fired_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_fired_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS `qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_job_details` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) NOT NULL COMMENT '任务组名',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务详细信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_job_details`
--

LOCK TABLES `qrtz_job_details` WRITE;
/*!40000 ALTER TABLE `qrtz_job_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_job_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS `qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储的悲观锁信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_locks`
--

LOCK TABLES `qrtz_locks` WRITE;
/*!40000 ALTER TABLE `qrtz_locks` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_locks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='暂停的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_paused_trigger_grps`
--

LOCK TABLES `qrtz_paused_trigger_grps` WRITE;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint(13) NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint(13) NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='调度器状态表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_scheduler_state`
--

LOCK TABLES `qrtz_scheduler_state` WRITE;
/*!40000 ALTER TABLE `qrtz_scheduler_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_scheduler_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint(7) NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint(12) NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint(10) NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='简单触发器的信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simple_triggers`
--

LOCK TABLES `qrtz_simple_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simple_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simple_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simprop_triggers`
--

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int(11) DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int(11) DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint(20) DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint(20) DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='同步机制的行锁表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simprop_triggers`
--

LOCK TABLES `qrtz_simprop_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS `qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint(13) DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint(13) DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int(11) DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) NOT NULL COMMENT '触发器的类型',
  `start_time` bigint(13) NOT NULL COMMENT '开始时间',
  `end_time` bigint(13) DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint(2) DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='触发器详细信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_triggers`
--

LOCK TABLES `qrtz_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_config` (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='参数配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'主框架页-默认皮肤样式名称','sys.index.skinName','skin-blue','Y','admin','2025-05-22 12:30:58','',NULL,'蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow'),(2,'用户管理-账号初始密码','sys.user.initPassword','123456','Y','admin','2025-05-22 12:30:58','',NULL,'初始化密码 123456'),(3,'主框架页-侧边栏主题','sys.index.sideTheme','theme-dark','Y','admin','2025-05-22 12:30:58','',NULL,'深色主题theme-dark，浅色主题theme-light'),(4,'账号自助-验证码开关','sys.account.captchaEnabled','true','Y','admin','2025-05-22 12:30:58','',NULL,'是否开启验证码功能（true开启，false关闭）'),(5,'账号自助-是否开启用户注册功能','sys.account.registerUser','false','Y','admin','2025-05-22 12:30:58','',NULL,'是否开启注册用户功能（true开启，false关闭）'),(6,'用户登录-黑名单列表','sys.login.blackIPList','','Y','admin','2025-05-22 12:30:58','',NULL,'设置登录IP黑名单限制，多个匹配项以;分隔，支持匹配（*通配、网段）');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8 COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES (100,0,'0','若依科技',0,'若依','15888888888','ry@qq.com','0','0','admin','2025-05-22 12:30:56','',NULL),(101,100,'0,100','深圳总公司',1,'若依','15888888888','ry@qq.com','0','0','admin','2025-05-22 12:30:56','',NULL),(102,100,'0,100','长沙分公司',2,'若依','15888888888','ry@qq.com','0','0','admin','2025-05-22 12:30:56','',NULL),(103,101,'0,100,101','研发部门',1,'若依','15888888888','ry@qq.com','0','0','admin','2025-05-22 12:30:56','',NULL),(104,101,'0,100,101','市场部门',2,'若依','15888888888','ry@qq.com','0','0','admin','2025-05-22 12:30:56','',NULL),(105,101,'0,100,101','测试部门',3,'若依','15888888888','ry@qq.com','0','0','admin','2025-05-22 12:30:56','',NULL),(106,101,'0,100,101','财务部门',4,'若依','15888888888','ry@qq.com','0','0','admin','2025-05-22 12:30:56','',NULL),(107,101,'0,100,101','运维部门',5,'若依','15888888888','ry@qq.com','0','0','admin','2025-05-22 12:30:56','',NULL),(108,102,'0,100,102','市场部门',1,'若依','15888888888','ry@qq.com','0','0','admin','2025-05-22 12:30:56','',NULL),(109,102,'0,100,102','财务部门',2,'若依','15888888888','ry@qq.com','0','0','admin','2025-05-22 12:30:56','',NULL);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` VALUES (1,1,'男','0','sys_user_sex','','','Y','0','admin','2025-05-22 12:30:58','',NULL,'性别男'),(2,2,'女','1','sys_user_sex','','','N','0','admin','2025-05-22 12:30:58','',NULL,'性别女'),(3,3,'未知','2','sys_user_sex','','','N','0','admin','2025-05-22 12:30:58','',NULL,'性别未知'),(4,1,'显示','0','sys_show_hide','','primary','Y','0','admin','2025-05-22 12:30:58','',NULL,'显示菜单'),(5,2,'隐藏','1','sys_show_hide','','danger','N','0','admin','2025-05-22 12:30:58','',NULL,'隐藏菜单'),(6,1,'正常','0','sys_normal_disable','','primary','Y','0','admin','2025-05-22 12:30:58','',NULL,'正常状态'),(7,2,'停用','1','sys_normal_disable','','danger','N','0','admin','2025-05-22 12:30:58','',NULL,'停用状态'),(8,1,'正常','0','sys_job_status','','primary','Y','0','admin','2025-05-22 12:30:58','',NULL,'正常状态'),(9,2,'暂停','1','sys_job_status','','danger','N','0','admin','2025-05-22 12:30:58','',NULL,'停用状态'),(10,1,'默认','DEFAULT','sys_job_group','','','Y','0','admin','2025-05-22 12:30:58','',NULL,'默认分组'),(11,2,'系统','SYSTEM','sys_job_group','','','N','0','admin','2025-05-22 12:30:58','',NULL,'系统分组'),(12,1,'是','Y','sys_yes_no','','primary','Y','0','admin','2025-05-22 12:30:58','',NULL,'系统默认是'),(13,2,'否','N','sys_yes_no','','danger','N','0','admin','2025-05-22 12:30:58','',NULL,'系统默认否'),(14,1,'通知','1','sys_notice_type','','warning','Y','0','admin','2025-05-22 12:30:58','',NULL,'通知'),(15,2,'公告','2','sys_notice_type','','success','N','0','admin','2025-05-22 12:30:58','',NULL,'公告'),(16,1,'正常','0','sys_notice_status','','primary','Y','0','admin','2025-05-22 12:30:58','',NULL,'正常状态'),(17,2,'关闭','1','sys_notice_status','','danger','N','0','admin','2025-05-22 12:30:58','',NULL,'关闭状态'),(18,99,'其他','0','sys_oper_type','','info','N','0','admin','2025-05-22 12:30:58','',NULL,'其他操作'),(19,1,'新增','1','sys_oper_type','','info','N','0','admin','2025-05-22 12:30:58','',NULL,'新增操作'),(20,2,'修改','2','sys_oper_type','','info','N','0','admin','2025-05-22 12:30:58','',NULL,'修改操作'),(21,3,'删除','3','sys_oper_type','','danger','N','0','admin','2025-05-22 12:30:58','',NULL,'删除操作'),(22,4,'授权','4','sys_oper_type','','primary','N','0','admin','2025-05-22 12:30:58','',NULL,'授权操作'),(23,5,'导出','5','sys_oper_type','','warning','N','0','admin','2025-05-22 12:30:58','',NULL,'导出操作'),(24,6,'导入','6','sys_oper_type','','warning','N','0','admin','2025-05-22 12:30:58','',NULL,'导入操作'),(25,7,'强退','7','sys_oper_type','','danger','N','0','admin','2025-05-22 12:30:58','',NULL,'强退操作'),(26,8,'生成代码','8','sys_oper_type','','warning','N','0','admin','2025-05-22 12:30:58','',NULL,'生成操作'),(27,9,'清空数据','9','sys_oper_type','','danger','N','0','admin','2025-05-22 12:30:58','',NULL,'清空操作'),(28,1,'成功','0','sys_common_status','','primary','N','0','admin','2025-05-22 12:30:58','',NULL,'正常状态'),(29,2,'失败','1','sys_common_status','','danger','N','0','admin','2025-05-22 12:30:58','',NULL,'停用状态');
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
INSERT INTO `sys_dict_type` VALUES (1,'用户性别','sys_user_sex','0','admin','2025-05-22 12:30:58','',NULL,'用户性别列表'),(2,'菜单状态','sys_show_hide','0','admin','2025-05-22 12:30:58','',NULL,'菜单状态列表'),(3,'系统开关','sys_normal_disable','0','admin','2025-05-22 12:30:58','',NULL,'系统开关列表'),(4,'任务状态','sys_job_status','0','admin','2025-05-22 12:30:58','',NULL,'任务状态列表'),(5,'任务分组','sys_job_group','0','admin','2025-05-22 12:30:58','',NULL,'任务分组列表'),(6,'系统是否','sys_yes_no','0','admin','2025-05-22 12:30:58','',NULL,'系统是否列表'),(7,'通知类型','sys_notice_type','0','admin','2025-05-22 12:30:58','',NULL,'通知类型列表'),(8,'通知状态','sys_notice_status','0','admin','2025-05-22 12:30:58','',NULL,'通知状态列表'),(9,'操作类型','sys_oper_type','0','admin','2025-05-22 12:30:58','',NULL,'操作类型列表'),(10,'系统状态','sys_common_status','0','admin','2025-05-22 12:30:58','',NULL,'登录状态列表');
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job`
--

DROP TABLE IF EXISTS `sys_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='定时任务调度表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job`
--

LOCK TABLES `sys_job` WRITE;
/*!40000 ALTER TABLE `sys_job` DISABLE KEYS */;
INSERT INTO `sys_job` VALUES (1,'系统默认（无参）','DEFAULT','ryTask.ryNoParams','0/10 * * * * ?','3','1','1','admin','2025-05-22 12:30:58','',NULL,''),(2,'系统默认（有参）','DEFAULT','ryTask.ryParams(\'ry\')','0/15 * * * * ?','3','1','1','admin','2025-05-22 12:30:58','',NULL,''),(3,'系统默认（多参）','DEFAULT','ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)','0/20 * * * * ?','3','1','1','admin','2025-05-22 12:30:58','',NULL,'');
/*!40000 ALTER TABLE `sys_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job_log`
--

DROP TABLE IF EXISTS `sys_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务调度日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job_log`
--

LOCK TABLES `sys_job_log` WRITE;
/*!40000 ALTER TABLE `sys_job_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_job_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_logininfor`
--

DROP TABLE IF EXISTS `sys_logininfor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`),
  KEY `idx_sys_logininfor_s` (`status`),
  KEY `idx_sys_logininfor_lt` (`login_time`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8 COMMENT='系统访问记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_logininfor`
--

LOCK TABLES `sys_logininfor` WRITE;
/*!40000 ALTER TABLE `sys_logininfor` DISABLE KEYS */;
INSERT INTO `sys_logininfor` VALUES (100,'admin','127.0.0.1','内网IP','Chrome 11','Windows 10','0','登录成功','2025-05-22 13:28:28'),(101,'admin','127.0.0.1','内网IP','Chrome 11','Windows 10','0','登录成功','2025-05-22 15:47:11'),(102,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-05-23 11:37:13'),(103,'admin','127.0.0.1','内网IP','Chrome 11','Windows 10','0','登录成功','2025-05-26 12:22:37'),(104,'admin','127.0.0.1','内网IP','Chrome 11','Windows 10','0','登录成功','2025-05-26 14:00:37');
/*!40000 ALTER TABLE `sys_logininfor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) DEFAULT NULL COMMENT '路由参数',
  `route_name` varchar(50) DEFAULT '' COMMENT '路由名称',
  `is_frame` int(1) DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2028 DEFAULT CHARSET=utf8 COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'系统管理',0,91,'system',NULL,'','',1,0,'M','0','0','','system','admin','2025-05-22 12:30:56','admin','2025-05-22 13:36:38','系统管理目录'),(2,'系统监控',0,92,'monitor',NULL,'','',1,0,'M','0','0','','monitor','admin','2025-05-22 12:30:56','admin','2025-05-22 13:36:43','系统监控目录'),(3,'系统工具',0,93,'tool',NULL,'','',1,0,'M','0','0','','tool','admin','2025-05-22 12:30:56','admin','2025-05-22 13:36:46','系统工具目录'),(4,'若依官网',0,94,'http://ruoyi.vip',NULL,'','',0,0,'M','0','0','','guide','admin','2025-05-22 12:30:56','admin','2025-05-22 13:36:50','若依官网地址'),(100,'用户管理',1,1,'user','system/user/index','','',1,0,'C','0','0','system:user:list','user','admin','2025-05-22 12:30:56','',NULL,'用户管理菜单'),(101,'角色管理',1,2,'role','system/role/index','','',1,0,'C','0','0','system:role:list','peoples','admin','2025-05-22 12:30:56','',NULL,'角色管理菜单'),(102,'菜单管理',1,3,'menu','system/menu/index','','',1,0,'C','0','0','system:menu:list','tree-table','admin','2025-05-22 12:30:56','',NULL,'菜单管理菜单'),(103,'部门管理',1,4,'dept','system/dept/index','','',1,0,'C','0','0','system:dept:list','tree','admin','2025-05-22 12:30:56','',NULL,'部门管理菜单'),(104,'岗位管理',1,5,'post','system/post/index','','',1,0,'C','0','0','system:post:list','post','admin','2025-05-22 12:30:56','',NULL,'岗位管理菜单'),(105,'字典管理',1,6,'dict','system/dict/index','','',1,0,'C','0','0','system:dict:list','dict','admin','2025-05-22 12:30:56','',NULL,'字典管理菜单'),(106,'参数设置',1,7,'config','system/config/index','','',1,0,'C','0','0','system:config:list','edit','admin','2025-05-22 12:30:56','',NULL,'参数设置菜单'),(107,'通知公告',1,8,'notice','system/notice/index','','',1,0,'C','0','0','system:notice:list','message','admin','2025-05-22 12:30:56','',NULL,'通知公告菜单'),(108,'日志管理',1,9,'log','','','',1,0,'M','0','0','','log','admin','2025-05-22 12:30:56','',NULL,'日志管理菜单'),(109,'在线用户',2,1,'online','monitor/online/index','','',1,0,'C','0','0','monitor:online:list','online','admin','2025-05-22 12:30:56','',NULL,'在线用户菜单'),(110,'定时任务',2,2,'job','monitor/job/index','','',1,0,'C','0','0','monitor:job:list','job','admin','2025-05-22 12:30:56','',NULL,'定时任务菜单'),(111,'数据监控',2,3,'druid','monitor/druid/index','','',1,0,'C','0','0','monitor:druid:list','druid','admin','2025-05-22 12:30:56','',NULL,'数据监控菜单'),(112,'服务监控',2,4,'server','monitor/server/index','','',1,0,'C','0','0','monitor:server:list','server','admin','2025-05-22 12:30:56','',NULL,'服务监控菜单'),(113,'缓存监控',2,5,'cache','monitor/cache/index','','',1,0,'C','0','0','monitor:cache:list','redis','admin','2025-05-22 12:30:56','',NULL,'缓存监控菜单'),(114,'缓存列表',2,6,'cacheList','monitor/cache/list','','',1,0,'C','0','0','monitor:cache:list','redis-list','admin','2025-05-22 12:30:56','',NULL,'缓存列表菜单'),(115,'表单构建',3,1,'build','tool/build/index','','',1,0,'C','0','0','tool:build:list','build','admin','2025-05-22 12:30:56','',NULL,'表单构建菜单'),(116,'代码生成',3,2,'gen','tool/gen/index','','',1,0,'C','0','0','tool:gen:list','code','admin','2025-05-22 12:30:56','',NULL,'代码生成菜单'),(117,'系统接口',3,3,'swagger','tool/swagger/index','','',1,0,'C','0','0','tool:swagger:list','swagger','admin','2025-05-22 12:30:56','',NULL,'系统接口菜单'),(500,'操作日志',108,1,'operlog','monitor/operlog/index','','',1,0,'C','0','0','monitor:operlog:list','form','admin','2025-05-22 12:30:56','',NULL,'操作日志菜单'),(501,'登录日志',108,2,'logininfor','monitor/logininfor/index','','',1,0,'C','0','0','monitor:logininfor:list','logininfor','admin','2025-05-22 12:30:56','',NULL,'登录日志菜单'),(1000,'用户查询',100,1,'','','','',1,0,'F','0','0','system:user:query','#','admin','2025-05-22 12:30:56','',NULL,''),(1001,'用户新增',100,2,'','','','',1,0,'F','0','0','system:user:add','#','admin','2025-05-22 12:30:56','',NULL,''),(1002,'用户修改',100,3,'','','','',1,0,'F','0','0','system:user:edit','#','admin','2025-05-22 12:30:56','',NULL,''),(1003,'用户删除',100,4,'','','','',1,0,'F','0','0','system:user:remove','#','admin','2025-05-22 12:30:56','',NULL,''),(1004,'用户导出',100,5,'','','','',1,0,'F','0','0','system:user:export','#','admin','2025-05-22 12:30:56','',NULL,''),(1005,'用户导入',100,6,'','','','',1,0,'F','0','0','system:user:import','#','admin','2025-05-22 12:30:56','',NULL,''),(1006,'重置密码',100,7,'','','','',1,0,'F','0','0','system:user:resetPwd','#','admin','2025-05-22 12:30:56','',NULL,''),(1007,'角色查询',101,1,'','','','',1,0,'F','0','0','system:role:query','#','admin','2025-05-22 12:30:56','',NULL,''),(1008,'角色新增',101,2,'','','','',1,0,'F','0','0','system:role:add','#','admin','2025-05-22 12:30:56','',NULL,''),(1009,'角色修改',101,3,'','','','',1,0,'F','0','0','system:role:edit','#','admin','2025-05-22 12:30:56','',NULL,''),(1010,'角色删除',101,4,'','','','',1,0,'F','0','0','system:role:remove','#','admin','2025-05-22 12:30:56','',NULL,''),(1011,'角色导出',101,5,'','','','',1,0,'F','0','0','system:role:export','#','admin','2025-05-22 12:30:56','',NULL,''),(1012,'菜单查询',102,1,'','','','',1,0,'F','0','0','system:menu:query','#','admin','2025-05-22 12:30:56','',NULL,''),(1013,'菜单新增',102,2,'','','','',1,0,'F','0','0','system:menu:add','#','admin','2025-05-22 12:30:56','',NULL,''),(1014,'菜单修改',102,3,'','','','',1,0,'F','0','0','system:menu:edit','#','admin','2025-05-22 12:30:56','',NULL,''),(1015,'菜单删除',102,4,'','','','',1,0,'F','0','0','system:menu:remove','#','admin','2025-05-22 12:30:56','',NULL,''),(1016,'部门查询',103,1,'','','','',1,0,'F','0','0','system:dept:query','#','admin','2025-05-22 12:30:56','',NULL,''),(1017,'部门新增',103,2,'','','','',1,0,'F','0','0','system:dept:add','#','admin','2025-05-22 12:30:56','',NULL,''),(1018,'部门修改',103,3,'','','','',1,0,'F','0','0','system:dept:edit','#','admin','2025-05-22 12:30:56','',NULL,''),(1019,'部门删除',103,4,'','','','',1,0,'F','0','0','system:dept:remove','#','admin','2025-05-22 12:30:56','',NULL,''),(1020,'岗位查询',104,1,'','','','',1,0,'F','0','0','system:post:query','#','admin','2025-05-22 12:30:56','',NULL,''),(1021,'岗位新增',104,2,'','','','',1,0,'F','0','0','system:post:add','#','admin','2025-05-22 12:30:56','',NULL,''),(1022,'岗位修改',104,3,'','','','',1,0,'F','0','0','system:post:edit','#','admin','2025-05-22 12:30:56','',NULL,''),(1023,'岗位删除',104,4,'','','','',1,0,'F','0','0','system:post:remove','#','admin','2025-05-22 12:30:56','',NULL,''),(1024,'岗位导出',104,5,'','','','',1,0,'F','0','0','system:post:export','#','admin','2025-05-22 12:30:56','',NULL,''),(1025,'字典查询',105,1,'#','','','',1,0,'F','0','0','system:dict:query','#','admin','2025-05-22 12:30:56','',NULL,''),(1026,'字典新增',105,2,'#','','','',1,0,'F','0','0','system:dict:add','#','admin','2025-05-22 12:30:56','',NULL,''),(1027,'字典修改',105,3,'#','','','',1,0,'F','0','0','system:dict:edit','#','admin','2025-05-22 12:30:56','',NULL,''),(1028,'字典删除',105,4,'#','','','',1,0,'F','0','0','system:dict:remove','#','admin','2025-05-22 12:30:56','',NULL,''),(1029,'字典导出',105,5,'#','','','',1,0,'F','0','0','system:dict:export','#','admin','2025-05-22 12:30:56','',NULL,''),(1030,'参数查询',106,1,'#','','','',1,0,'F','0','0','system:config:query','#','admin','2025-05-22 12:30:56','',NULL,''),(1031,'参数新增',106,2,'#','','','',1,0,'F','0','0','system:config:add','#','admin','2025-05-22 12:30:57','',NULL,''),(1032,'参数修改',106,3,'#','','','',1,0,'F','0','0','system:config:edit','#','admin','2025-05-22 12:30:57','',NULL,''),(1033,'参数删除',106,4,'#','','','',1,0,'F','0','0','system:config:remove','#','admin','2025-05-22 12:30:57','',NULL,''),(1034,'参数导出',106,5,'#','','','',1,0,'F','0','0','system:config:export','#','admin','2025-05-22 12:30:57','',NULL,''),(1035,'公告查询',107,1,'#','','','',1,0,'F','0','0','system:notice:query','#','admin','2025-05-22 12:30:57','',NULL,''),(1036,'公告新增',107,2,'#','','','',1,0,'F','0','0','system:notice:add','#','admin','2025-05-22 12:30:57','',NULL,''),(1037,'公告修改',107,3,'#','','','',1,0,'F','0','0','system:notice:edit','#','admin','2025-05-22 12:30:57','',NULL,''),(1038,'公告删除',107,4,'#','','','',1,0,'F','0','0','system:notice:remove','#','admin','2025-05-22 12:30:57','',NULL,''),(1039,'操作查询',500,1,'#','','','',1,0,'F','0','0','monitor:operlog:query','#','admin','2025-05-22 12:30:57','',NULL,''),(1040,'操作删除',500,2,'#','','','',1,0,'F','0','0','monitor:operlog:remove','#','admin','2025-05-22 12:30:57','',NULL,''),(1041,'日志导出',500,3,'#','','','',1,0,'F','0','0','monitor:operlog:export','#','admin','2025-05-22 12:30:57','',NULL,''),(1042,'登录查询',501,1,'#','','','',1,0,'F','0','0','monitor:logininfor:query','#','admin','2025-05-22 12:30:57','',NULL,''),(1043,'登录删除',501,2,'#','','','',1,0,'F','0','0','monitor:logininfor:remove','#','admin','2025-05-22 12:30:57','',NULL,''),(1044,'日志导出',501,3,'#','','','',1,0,'F','0','0','monitor:logininfor:export','#','admin','2025-05-22 12:30:57','',NULL,''),(1045,'账户解锁',501,4,'#','','','',1,0,'F','0','0','monitor:logininfor:unlock','#','admin','2025-05-22 12:30:57','',NULL,''),(1046,'在线查询',109,1,'#','','','',1,0,'F','0','0','monitor:online:query','#','admin','2025-05-22 12:30:57','',NULL,''),(1047,'批量强退',109,2,'#','','','',1,0,'F','0','0','monitor:online:batchLogout','#','admin','2025-05-22 12:30:57','',NULL,''),(1048,'单条强退',109,3,'#','','','',1,0,'F','0','0','monitor:online:forceLogout','#','admin','2025-05-22 12:30:57','',NULL,''),(1049,'任务查询',110,1,'#','','','',1,0,'F','0','0','monitor:job:query','#','admin','2025-05-22 12:30:57','',NULL,''),(1050,'任务新增',110,2,'#','','','',1,0,'F','0','0','monitor:job:add','#','admin','2025-05-22 12:30:57','',NULL,''),(1051,'任务修改',110,3,'#','','','',1,0,'F','0','0','monitor:job:edit','#','admin','2025-05-22 12:30:57','',NULL,''),(1052,'任务删除',110,4,'#','','','',1,0,'F','0','0','monitor:job:remove','#','admin','2025-05-22 12:30:57','',NULL,''),(1053,'状态修改',110,5,'#','','','',1,0,'F','0','0','monitor:job:changeStatus','#','admin','2025-05-22 12:30:57','',NULL,''),(1054,'任务导出',110,6,'#','','','',1,0,'F','0','0','monitor:job:export','#','admin','2025-05-22 12:30:57','',NULL,''),(1055,'生成查询',116,1,'#','','','',1,0,'F','0','0','tool:gen:query','#','admin','2025-05-22 12:30:57','',NULL,''),(1056,'生成修改',116,2,'#','','','',1,0,'F','0','0','tool:gen:edit','#','admin','2025-05-22 12:30:57','',NULL,''),(1057,'生成删除',116,3,'#','','','',1,0,'F','0','0','tool:gen:remove','#','admin','2025-05-22 12:30:57','',NULL,''),(1058,'导入代码',116,4,'#','','','',1,0,'F','0','0','tool:gen:import','#','admin','2025-05-22 12:30:57','',NULL,''),(1059,'预览代码',116,5,'#','','','',1,0,'F','0','0','tool:gen:preview','#','admin','2025-05-22 12:30:57','',NULL,''),(1060,'生成代码',116,6,'#','','','',1,0,'F','0','0','tool:gen:code','#','admin','2025-05-22 12:30:57','',NULL,''),(2000,'排课',0,1,'paike',NULL,NULL,'',1,0,'M','0','0','','#','admin','2025-05-22 12:31:09','admin','2025-05-22 13:36:32',NULL),(2001,'排课任务',2000,1,'lessonSchedule','teach/paike/lessonSchedule/index',NULL,'',1,0,'C','0','0','teach.paike:lessonSchedule','#','admin','2025-05-22 12:31:09','',NULL,'排课任务菜单'),(2002,'基础信息',2000,2,'base','teach/paike/base/index',NULL,'',1,0,'C','0','0','','#','admin','2025-05-22 12:31:09','admin','2025-05-26 14:03:32',NULL),(2003,'作息安排',2002,1,'time','teach/paike/base/time/index',NULL,'',1,0,'C','0','0','teach.paike:time','#','admin','2025-05-22 12:31:09','admin','2025-05-22 14:41:51','作息安排菜单'),(2004,'任课信息',2002,2,'lessonPlan','teach/paike/base/lessonPlan/index',NULL,'',1,0,'C','0','0','teach.paike:lessonPlan','#','admin','2025-05-22 12:31:09','',NULL,'任课信息菜单'),(2005,'班级教室',2002,3,'classroom','teach/paike/base/classroom/index',NULL,'',1,0,'C','0','0','teach.paike:classroom','#','admin','2025-05-22 12:31:09','',NULL,'班级教室菜单'),(2006,'特殊教室',2002,4,'classroomSpecial','teach/paike/base/classroomSpecial/index',NULL,'',1,0,'C','0','0','teach.paike:classroomSpecial','#','admin','2025-05-22 12:31:09','',NULL,'特殊教室菜单'),(2007,'智能排课',2000,3,'intellect','teach/paike/intellect/index',NULL,'',1,0,'C','0','0','','#','admin','2025-05-22 12:31:09','admin','2025-05-26 14:03:47',NULL),(2008,'全校排课时间',2007,1,'schoolTimes','teach/paike/intellect/schoolTimes/index',NULL,'',1,0,'C','0','0','teach.paike:schoolTimes','#','admin','2025-05-22 12:31:10','',NULL,'全校排课时间菜单'),(2009,'课程排课时间',2007,2,'courseTimes','teach/paike/intellect/courseTimes/index',NULL,'',1,0,'C','0','0','teach.paike:courseTimes','#','admin','2025-05-22 12:31:10','',NULL,'课程排课时间菜单'),(2010,'班级排课时间',2007,3,'adminclassTimes','teach/paike/intellect/adminclassTimes/index',NULL,'',1,0,'C','0','0','teach.paike:adminclassTimes','#','admin','2025-05-22 12:31:10','',NULL,'班级排课时间菜单'),(2011,'教师排课时间',2007,4,'teacherTimes','teach/paike/intellect/teacherTimes/index',NULL,'',1,0,'C','0','0','teach.paike:teacherTimes','#','admin','2025-05-22 12:31:10','',NULL,'教师排课时间菜单'),(2012,'合班',2007,5,'mixedClass','teach/paike/intellect/mixedClass/index',NULL,'',1,0,'C','0','0','teach.paike:mixedClass','#','admin','2025-05-22 12:31:10','',NULL,'合班菜单'),(2013,'预排',2007,6,'lessonSchedulePre','teach/paike/intellect/lessonSchedulePre/index',NULL,'',1,0,'C','0','0','teach.paike:lessonSchedulePre','#','admin','2025-05-22 12:31:10','',NULL,'排课预排菜单'),(2014,'自动排课',2007,7,'lessonScheduleAuto','teach/paike/intellect/lessonScheduleAuto/index',NULL,'',1,0,'C','0','0','teach.paike:lessonScheduleAuto','#','admin','2025-05-22 12:31:10','',NULL,'排课自动排课菜单'),(2015,'课表调整',2000,4,'adjust','teach/paike/adjust/index',NULL,'',1,0,'C','0','0','','#','admin','2025-05-22 12:31:10','admin','2025-05-26 14:04:02',NULL),(2016,'按班级调课',2015,1,'adminclass','teach/paike/adjust/adminclass',NULL,'',1,0,'C','0','0','teach.paike:adjust','#','admin','2025-05-22 12:31:10','',NULL,'按班级调课菜单'),(2017,'按教师调课',2015,2,'teacher','teach/paike/adjust/teacher',NULL,'',1,0,'C','0','0','teach.paike:adjust','#','admin','2025-05-22 12:31:10','',NULL,'按教师调课菜单'),(2018,'排课存档',2015,3,'record','teach/paike/adjust/record/index',NULL,'',1,0,'C','0','0','teach.paike:record','#','admin','2025-05-22 12:31:10','',NULL,'排课存档菜单'),(2019,'查看课表',2000,5,'view','teach/paike/view/index',NULL,'',1,0,'C','0','0','','#','admin','2025-05-22 12:31:10','admin','2025-05-26 14:04:17',NULL),(2020,'班级课表',2019,1,'adminclass','teach/paike/view/adminclass',NULL,'',1,0,'C','0','0','teach.paike:view','#','admin','2025-05-22 12:31:10','',NULL,'班级课表菜单'),(2021,'教师课表',2019,2,'teacher','teach/paike/view/teacher',NULL,'',1,0,'C','0','0','teach.paike:view','#','admin','2025-05-22 12:31:10','',NULL,'教师课表菜单'),(2022,'教室课表',2019,3,'classroom','teach/paike/view/classroom',NULL,'',1,0,'C','0','0','teach.paike:view','#','admin','2025-05-22 12:31:10','',NULL,'教室课表菜单'),(2023,'基础数据',0,0,'school',NULL,NULL,'',1,0,'M','0','0','','#','admin','2025-05-22 13:33:15','admin','2025-05-22 13:36:27',NULL),(2024,'学年学期',2023,1,'teachCalendar','teach/school/teachCalendar/index',NULL,'',1,0,'C','0','0','teach.school:teachCalendar','#','admin','2025-05-22 13:35:47','',NULL,'学年学期菜单'),(2025,'年级',2023,2,'grade','teach/school/grade/index',NULL,'',1,0,'C','0','0','teach.school:grade','#','admin','2025-05-22 14:55:21','',NULL,'年级菜单'),(2027,'课程',2023,3,'course','teach/school/course/index',NULL,'',1,0,'C','0','0','teach.school:course','#','admin','2025-05-22 15:50:41','',NULL,'课程菜单');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_notice` (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) NOT NULL COMMENT '公告标题',
  `notice_type` char(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='通知公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
INSERT INTO `sys_notice` VALUES (1,'温馨提醒：2018-07-01 若依新版本发布啦','2',_binary '新版本内容','0','admin','2025-05-22 12:30:58','',NULL,'管理员'),(2,'维护通知：2018-07-01 若依系统凌晨维护','1',_binary '维护内容','0','admin','2025-05-22 12:30:58','',NULL,'管理员');
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oper_log`
--

DROP TABLE IF EXISTS `sys_oper_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(200) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `status` int(1) DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint(20) DEFAULT '0' COMMENT '消耗时间',
  PRIMARY KEY (`oper_id`),
  KEY `idx_sys_oper_log_bt` (`business_type`),
  KEY `idx_sys_oper_log_s` (`status`),
  KEY `idx_sys_oper_log_ot` (`oper_time`)
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=utf8 COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oper_log`
--

LOCK TABLES `sys_oper_log` WRITE;
/*!40000 ALTER TABLE `sys_oper_log` DISABLE KEYS */;
INSERT INTO `sys_oper_log` VALUES (100,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin','研发部门','/system/menu','127.0.0.1','内网IP','{\"children\":[],\"createTime\":\"2025-05-22 12:31:09\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2000,\"menuName\":\"排课\",\"menuType\":\"M\",\"orderNum\":0,\"params\":{},\"parentId\":0,\"path\":\"paike\",\"perms\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 12:31:21',17),(101,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin','研发部门','/system/menu','127.0.0.1','内网IP','{\"children\":[],\"createTime\":\"2025-05-22 13:33:15\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2023,\"menuName\":\"基础数据\",\"menuType\":\"M\",\"orderNum\":0,\"params\":{},\"parentId\":0,\"path\":\"school\",\"perms\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 13:36:27',33),(102,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin','研发部门','/system/menu','127.0.0.1','内网IP','{\"children\":[],\"createTime\":\"2025-05-22 12:31:09\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2000,\"menuName\":\"排课\",\"menuType\":\"M\",\"orderNum\":1,\"params\":{},\"parentId\":0,\"path\":\"paike\",\"perms\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 13:36:32',16),(103,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin','研发部门','/system/menu','127.0.0.1','内网IP','{\"children\":[],\"createTime\":\"2025-05-22 12:30:56\",\"icon\":\"system\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":1,\"menuName\":\"系统管理\",\"menuType\":\"M\",\"orderNum\":91,\"params\":{},\"parentId\":0,\"path\":\"system\",\"perms\":\"\",\"query\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 13:36:39',15),(104,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin','研发部门','/system/menu','127.0.0.1','内网IP','{\"children\":[],\"createTime\":\"2025-05-22 12:30:56\",\"icon\":\"monitor\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2,\"menuName\":\"系统监控\",\"menuType\":\"M\",\"orderNum\":92,\"params\":{},\"parentId\":0,\"path\":\"monitor\",\"perms\":\"\",\"query\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 13:36:43',16),(105,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin','研发部门','/system/menu','127.0.0.1','内网IP','{\"children\":[],\"createTime\":\"2025-05-22 12:30:56\",\"icon\":\"tool\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":3,\"menuName\":\"系统工具\",\"menuType\":\"M\",\"orderNum\":93,\"params\":{},\"parentId\":0,\"path\":\"tool\",\"perms\":\"\",\"query\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 13:36:46',15),(106,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin','研发部门','/system/menu','127.0.0.1','内网IP','{\"children\":[],\"createTime\":\"2025-05-22 12:30:56\",\"icon\":\"guide\",\"isCache\":\"0\",\"isFrame\":\"0\",\"menuId\":4,\"menuName\":\"若依官网\",\"menuType\":\"M\",\"orderNum\":94,\"params\":{},\"parentId\":0,\"path\":\"http://ruoyi.vip\",\"perms\":\"\",\"query\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 13:36:50',15),(107,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"year\":\"2024\"}',NULL,1,'Cannot invoke \"com.ruoyi.teach.system.domain.School.getId()\" because \"school\" is null','2025-05-22 13:39:54',4),(108,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"year\":\"2024\"}',NULL,1,'Cannot invoke \"com.ruoyi.teach.system.domain.School.getId()\" because \"school\" is null','2025-05-22 13:40:16',1),(109,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (school_id = ?)\r\n### The error may exist in com/ruoyi/teach/school/mapper/TeachCalendarMapper.java (best guess)\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.update\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (school_id = ?)','2025-05-22 13:40:54',24),(110,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?\r\n### The error may exist in com/ruoyi/teach/school/mapper/TeachCalendarMapper.java (best guess)\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.update\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?','2025-05-22 13:42:24',5),(111,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?\r\n### The error may exist in com/ruoyi/teach/school/mapper/TeachCalendarMapper.java (best guess)\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.update\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?','2025-05-22 13:42:42',3),(112,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id >= ?)\r\n### The error may exist in com/ruoyi/teach/school/mapper/TeachCalendarMapper.java (best guess)\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.update\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id >= ?)','2025-05-22 13:45:24',8),(113,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?\r\n### The error may exist in com/ruoyi/teach/school/mapper/TeachCalendarMapper.java (best guess)\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.update\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?','2025-05-22 13:45:49',4),(114,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id >= ?)\r\n### The error may exist in com/ruoyi/teach/school/mapper/TeachCalendarMapper.java (best guess)\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.update\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id >= ?)','2025-05-22 13:47:00',4),(115,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id >= ?)\r\n### The error may exist in com/ruoyi/teach/school/mapper/TeachCalendarMapper.java (best guess)\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.update\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id >= ?)','2025-05-22 13:52:03',4),(116,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id >= ?)\r\n### The error may exist in com/ruoyi/teach/school/mapper/TeachCalendarMapper.java (best guess)\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.update\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id >= ?)','2025-05-22 13:56:02',84),(117,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id >= ?)\r\n### The error may exist in com/ruoyi/teach/school/mapper/TeachCalendarMapper.java (best guess)\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.update\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id >= ?)','2025-05-22 13:56:09',5),(118,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id >= ?)\r\n### The error may exist in com/ruoyi/teach/school/mapper/TeachCalendarMapper.java (best guess)\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.update\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id >= ?)','2025-05-22 13:56:55',7),(119,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id >= ?)\r\n### The error may exist in com/ruoyi/teach/school/mapper/TeachCalendarMapper.java (best guess)\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.update\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id >= ?)','2025-05-22 13:57:42',8),(120,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id >= ?)\r\n### The error may exist in com/ruoyi/teach/school/mapper/TeachCalendarMapper.java (best guess)\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.update\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id >= ?)','2025-05-22 14:01:09',4),(121,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id >= ?)\r\n### The error may exist in com/ruoyi/teach/school/mapper/TeachCalendarMapper.java (best guess)\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.update\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id >= ?)','2025-05-22 14:03:45',68),(122,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id = ?)\r\n### The error may exist in com/ruoyi/teach/school/mapper/TeachCalendarMapper.java (best guess)\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.update\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: UPDATE ts_teach_calendar  SET current=?  \n \n WHERE (id = ?)','2025-05-22 14:04:18',8),(123,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"id\":1,\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"weekNum\":22,\"year\":\"2024\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 14:06:13',53),(124,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"id\":2,\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"weekNum\":22,\"year\":\"2024\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 14:06:22',17),(125,'学年学期',3,'com.ruoyi.teach.school.controller.TeachCalendarController.remove()','DELETE',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 14:07:16',27),(126,'学年学期',2,'com.ruoyi.teach.school.controller.TeachCalendarController.edit()','PUT',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"delFlag\":\"0\",\"endDate\":\"2025-06-30\",\"id\":1,\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"weekNum\":22,\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: update ts_teach_calendar\n         SET year = ?,\n            term = ?,\n            start_date = ?,\n            end_date = ?,\n            current = ?,\n            week_num = ? \n        where id = ?\r\n### The error may exist in file [D:\\workspace\\teach-cloud\\teach-paike\\ruoyi-paike\\target\\classes\\mapper\\teach\\school\\TeachCalendarMapper.xml]\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.updateTeachCalendar\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: update ts_teach_calendar\n         SET year = ?,\n            term = ?,\n            start_date = ?,\n            end_date = ?,\n            current = ?,\n            week_num = ? \n        where id = ?','2025-05-22 14:07:20',4),(127,'学年学期',2,'com.ruoyi.teach.school.controller.TeachCalendarController.edit()','PUT',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"delFlag\":\"0\",\"endDate\":\"2025-06-30\",\"id\":1,\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"weekNum\":22,\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: update ts_teach_calendar\n         SET year = ?,\n            term = ?,\n            start_date = ?,\n            end_date = ?,\n            current = ?,\n            week_num = ? \n        where id = ?\r\n### The error may exist in file [D:\\workspace\\teach-cloud\\teach-paike\\ruoyi-paike\\target\\classes\\mapper\\teach\\school\\TeachCalendarMapper.xml]\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.updateTeachCalendar\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: update ts_teach_calendar\n         SET year = ?,\n            term = ?,\n            start_date = ?,\n            end_date = ?,\n            current = ?,\n            week_num = ? \n        where id = ?','2025-05-22 14:07:33',4),(128,'学年学期',2,'com.ruoyi.teach.school.controller.TeachCalendarController.edit()','PUT',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"delFlag\":\"0\",\"endDate\":\"2025-06-30\",\"id\":1,\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"weekNum\":22,\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: update ts_teach_calendar\n         SET year = ?,\n            term = ?,\n            start_date = ?,\n            end_date = ?,\n            current = ?,\n            week_num = ? \n        where id = ?\r\n### The error may exist in file [D:\\workspace\\teach-cloud\\teach-paike\\ruoyi-paike\\target\\classes\\mapper\\teach\\school\\TeachCalendarMapper.xml]\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.updateTeachCalendar\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: update ts_teach_calendar\n         SET year = ?,\n            term = ?,\n            start_date = ?,\n            end_date = ?,\n            current = ?,\n            week_num = ? \n        where id = ?','2025-05-22 14:13:24',57),(129,'学年学期',2,'com.ruoyi.teach.school.controller.TeachCalendarController.edit()','PUT',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"delFlag\":\"0\",\"endDate\":\"2025-06-30\",\"id\":1,\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"weekNum\":22,\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: update ts_teach_calendar\n         SET year = ?,\n            term = ?,\n            start_date = ?,\n            end_date = ?,\n            current = ?,\n            week_num = ? \n        where id = ?\r\n### The error may exist in file [D:\\workspace\\teach-cloud\\teach-paike\\ruoyi-paike\\target\\classes\\mapper\\teach\\school\\TeachCalendarMapper.xml]\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.updateTeachCalendar\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: update ts_teach_calendar\n         SET year = ?,\n            term = ?,\n            start_date = ?,\n            end_date = ?,\n            current = ?,\n            week_num = ? \n        where id = ?','2025-05-22 14:16:41',49),(130,'学年学期',2,'com.ruoyi.teach.school.controller.TeachCalendarController.edit()','PUT',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"delFlag\":\"0\",\"endDate\":\"2025-06-30\",\"id\":1,\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"weekNum\":22,\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: update ts_teach_calendar\n         SET year = ?,\n            term = ?,\n            start_date = ?,\n            end_date = ?,\n            current = ?,\n            week_num = ? \n        where id = ?\r\n### The error may exist in file [D:\\workspace\\teach-cloud\\teach-paike\\ruoyi-paike\\target\\classes\\mapper\\teach\\school\\TeachCalendarMapper.xml]\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.updateTeachCalendar\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Failed to process, Error SQL: update ts_teach_calendar\n         SET year = ?,\n            term = ?,\n            start_date = ?,\n            end_date = ?,\n            current = ?,\n            week_num = ? \n        where id = ?','2025-05-22 14:16:47',3),(131,'学年学期',2,'com.ruoyi.teach.school.controller.TeachCalendarController.edit()','PUT',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"delFlag\":\"0\",\"endDate\":\"2025-06-30\",\"id\":1,\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"weekNum\":22,\"year\":\"2024\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 14:17:51',67),(132,'学年学期',2,'com.ruoyi.teach.school.controller.TeachCalendarController.edit()','PUT',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"delFlag\":\"0\",\"endDate\":\"2025-06-30\",\"id\":1,\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"weekNum\":22,\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Prohibition of table update operation\r\n### The error may exist in com/ruoyi/teach/school/mapper/TeachCalendarMapper.java (best guess)\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.update\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Prohibition of table update operation','2025-05-22 14:18:12',24),(133,'学年学期',2,'com.ruoyi.teach.school.controller.TeachCalendarController.edit()','PUT',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"delFlag\":\"0\",\"endDate\":\"2025-06-30\",\"id\":1,\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"weekNum\":22,\"year\":\"2024\"}',NULL,1,'nested exception is org.apache.ibatis.exceptions.PersistenceException: \r\n### Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Prohibition of table update operation\r\n### The error may exist in com/ruoyi/teach/school/mapper/TeachCalendarMapper.java (best guess)\r\n### The error may involve com.ruoyi.teach.school.mapper.TeachCalendarMapper.update\r\n### The error occurred while executing an update\r\n### Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Prohibition of table update operation','2025-05-22 14:18:23',3),(134,'学年学期',2,'com.ruoyi.teach.school.controller.TeachCalendarController.edit()','PUT',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"delFlag\":\"0\",\"endDate\":\"2025-06-30\",\"id\":1,\"name\":\"2024-2\",\"startDate\":\"2025-02-01\",\"term\":\"2\",\"weekNum\":22,\"year\":\"2024\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 14:18:52',47),(135,'排课任务',1,'com.ruoyi.teach.paike.controller.LessonScheduleController.add()','POST',1,'admin','研发部门','/teach/paike/lessonSchedule','127.0.0.1','内网IP','{\"isCurrent\":\"Y\",\"name\":\"2024-2\",\"teachCalenderId\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 14:24:48',45),(136,'排课任务',2,'com.ruoyi.teach.paike.controller.LessonScheduleController.edit()','PUT',1,'admin','研发部门','/teach/paike/lessonSchedule','127.0.0.1','内网IP','{\"createTime\":\"2025-05-22 14:24:48\",\"id\":1925437670358315010,\"isAutoSchedule\":\"N\",\"isConflict\":\"N\",\"isCurrent\":\"Y\",\"name\":\"2024-2\",\"teachCalender\":{\"name\":\"2024-2\",\"term\":\"2\",\"year\":\"2024\"},\"teachCalenderId\":1,\"updateTime\":\"2025-05-22 14:27:49.348\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 14:27:49',52),(137,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin','研发部门','/system/menu','127.0.0.1','内网IP','{\"children\":[],\"component\":\"teach/paike/base/time/index\",\"createTime\":\"2025-05-22 12:31:09\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2003,\"menuName\":\"作息安排\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2002,\"path\":\"time\",\"perms\":\"teach.paike:time\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 14:41:51',23),(138,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"id\":3,\"name\":\"2024-2\",\"schoolId\":0,\"startDate\":\"2025-02-01\",\"term\":\"2\",\"weekNum\":22,\"year\":\"2024\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 14:45:54',59),(139,'学年学期',1,'com.ruoyi.teach.school.controller.TeachCalendarController.add()','POST',1,'admin','研发部门','/school/teachCalendar','127.0.0.1','内网IP','{\"current\":\"Y\",\"endDate\":\"2025-06-30\",\"id\":1,\"name\":\"2024-2\",\"schoolId\":0,\"startDate\":\"2025-02-01\",\"term\":\"2\",\"weekNum\":22,\"year\":\"2024\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 14:52:20',84),(140,'排课任务',1,'com.ruoyi.teach.paike.controller.LessonScheduleController.add()','POST',1,'admin','研发部门','/teach/paike/lessonSchedule','127.0.0.1','内网IP','{\"isCurrent\":\"Y\",\"name\":\"2024-2\",\"schoolId\":0,\"teachCalenderId\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 14:52:51',71),(141,'作息安排',1,'com.ruoyi.teach.paike.controller.LessonScheduleTimeController.add()','POST',1,'admin','研发部门','/teach/paike/time','127.0.0.1','内网IP','{\"afternoon\":3,\"count\":7,\"id\":1925444755120758785,\"lessonScheduleId\":1925444731540381698,\"morning\":4,\"night\":0,\"schoolId\":0,\"times\":\"1111111;1111111;1111111;1111111;1111111\",\"total\":35,\"weekdays\":5}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 14:52:57',15),(142,'年级',2,'com.ruoyi.teach.school.controller.GradeController.saveOrUpdate()','POST',1,'admin','研发部门','/teach/school/grade','127.0.0.1','内网IP','{\"code\":\"01\",\"id\":1925458784878940162,\"name\":\"一年级\",\"schoolId\":0}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 15:48:42',39),(143,'年级',2,'com.ruoyi.teach.school.controller.GradeController.saveOrUpdate()','POST',1,'admin','研发部门','/teach/school/grade','127.0.0.1','内网IP','{\"code\":\"02\",\"id\":1925458813312126977,\"name\":\"二年级\",\"schoolId\":0}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 15:48:49',13),(144,'年级',2,'com.ruoyi.teach.school.controller.GradeController.saveOrUpdate()','POST',1,'admin','研发部门','/teach/school/grade','127.0.0.1','内网IP','{\"code\":\"03\",\"id\":1925458838624751617,\"name\":\"三年级\",\"schoolId\":0}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 15:48:55',16),(145,'菜单管理',3,'com.ruoyi.web.controller.system.SysMenuController.remove()','DELETE',1,'admin','研发部门','/system/menu/2026','127.0.0.1','内网IP','{}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 15:50:58',17),(146,'课程',1,'com.ruoyi.teach.school.controller.CourseController.add()','POST',1,'admin','研发部门','/teach/school/course','127.0.0.1','内网IP','{\"code\":\"01\",\"criterion\":1.0,\"id\":1925459540814155778,\"name\":\"语文\",\"schoolId\":0,\"sort\":1,\"status\":\"0\"}',NULL,1,'\r\n### Error updating database.  Cause: java.sql.SQLSyntaxErrorException: Unknown column \'criterion\' in \'field list\'\r\n### The error may exist in file [D:\\workspace\\teach-cloud\\teach-paike\\ruoyi-paike\\target\\classes\\mapper\\teach\\school\\CourseMapper.xml]\r\n### The error may involve com.ruoyi.teach.school.mapper.CourseMapper.insertCourse-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into ts_course          ( school_id,             sort,             code,             name,             criterion,             status )           values ( ?,             ?,             ?,             ?,             ?,             ? )\r\n### Cause: java.sql.SQLSyntaxErrorException: Unknown column \'criterion\' in \'field list\'\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Unknown column \'criterion\' in \'field list\'','2025-05-22 15:51:42',11),(147,'课程',1,'com.ruoyi.teach.school.controller.CourseController.add()','POST',1,'admin','研发部门','/teach/school/course','127.0.0.1','内网IP','{\"code\":\"01\",\"criterion\":1.0,\"id\":1925459562314158081,\"name\":\"语文\",\"schoolId\":0,\"sort\":1,\"status\":\"0\"}',NULL,1,'\r\n### Error updating database.  Cause: java.sql.SQLSyntaxErrorException: Unknown column \'criterion\' in \'field list\'\r\n### The error may exist in file [D:\\workspace\\teach-cloud\\teach-paike\\ruoyi-paike\\target\\classes\\mapper\\teach\\school\\CourseMapper.xml]\r\n### The error may involve com.ruoyi.teach.school.mapper.CourseMapper.insertCourse-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into ts_course          ( school_id,             sort,             code,             name,             criterion,             status )           values ( ?,             ?,             ?,             ?,             ?,             ? )\r\n### Cause: java.sql.SQLSyntaxErrorException: Unknown column \'criterion\' in \'field list\'\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Unknown column \'criterion\' in \'field list\'','2025-05-22 15:51:47',3),(148,'课程',1,'com.ruoyi.teach.school.controller.CourseController.add()','POST',1,'admin','研发部门','/teach/school/course','127.0.0.1','内网IP','{\"code\":\"01\",\"criterion\":1.0,\"id\":1,\"name\":\"语文\",\"schoolId\":0,\"sort\":1,\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 15:52:51',24),(149,'课程',1,'com.ruoyi.teach.school.controller.CourseController.add()','POST',1,'admin','研发部门','/teach/school/course','127.0.0.1','内网IP','{\"code\":\"02\",\"criterion\":1.0,\"id\":2,\"name\":\"数学\",\"schoolId\":0,\"sort\":2,\"status\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 15:53:01',11),(150,'任课信息',2,'com.ruoyi.teach.paike.controller.LessonPlanController.saveBatch()','POST',1,'admin','研发部门','/teach/paike/lessonPlan/saveBatch','127.0.0.1','内网IP','{\"adminclass\":\"1班\",\"grade\":\"一年级\",\"lessonScheduleId\":1925444731540381698,\"plans\":[{\"adminclass\":\"1班\",\"course\":\"语文\",\"grade\":\"一年级\",\"hour\":10,\"hours\":\"10\",\"id\":1925460365909250049,\"lessonScheduleId\":1925444731540381698,\"schoolId\":0,\"teacher\":\"\"},{\"adminclass\":\"1班\",\"course\":\"数学\",\"grade\":\"一年级\",\"hour\":10,\"hours\":\"10\",\"id\":1925460365909250050,\"lessonScheduleId\":1925444731540381698,\"schoolId\":0,\"teacher\":\"\"},{\"teacher\":\"\"},{\"teacher\":\"\"},{\"teacher\":\"\"}],\"removePlanIds\":[],\"schoolId\":0}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 15:54:59',31),(151,'自动排课',2,'com.ruoyi.teach.paike.intellect.controller.LessonScheduleAutoController.startSchedule()','POST',1,'admin','研发部门','/teach/paike/lessonScheduleAuto/startSchedule','127.0.0.1','内网IP','','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-22 15:55:27',76),(152,'任课信息',2,'com.ruoyi.teach.paike.controller.LessonPlanController.saveBatch()','POST',1,'admin','研发部门','/teach/paike/lessonPlan/saveBatch','127.0.0.1','内网IP','{\"adminclass\":\"2班\",\"grade\":\"一年级\",\"lessonScheduleId\":1925444731540381698,\"plans\":[{\"adminclass\":\"2班\",\"course\":\"数学\",\"grade\":\"一年级\",\"hour\":8,\"hours\":\"8\",\"id\":1925758336190205954,\"lessonScheduleId\":1925444731540381698,\"schoolId\":0,\"teacher\":\"张三\"},{\"adminclass\":\"2班\",\"course\":\"语文\",\"grade\":\"一年级\",\"hour\":8,\"hours\":\"8\",\"id\":1925758336190205955,\"lessonScheduleId\":1925444731540381698,\"schoolId\":0,\"teacher\":\"李四\"},{\"teacher\":\"\"},{\"teacher\":\"\"},{\"teacher\":\"\"}],\"removePlanIds\":[],\"schoolId\":0}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-23 11:39:00',38),(153,'自动排课',2,'com.ruoyi.teach.paike.intellect.controller.LessonScheduleAutoController.startSchedule()','POST',1,'admin','研发部门','/teach/paike/lessonScheduleAuto/startSchedule','127.0.0.1','内网IP','','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-23 11:39:07',103),(154,'任课信息',3,'com.ruoyi.teach.paike.controller.LessonPlanController.deleteByAdminclass()','POST',1,'admin','研发部门','/teach/paike/lessonPlan/deleteByAdminclass','127.0.0.1','内网IP','{\"adminclass\":\"1班\",\"lessonScheduleId\":1925444731540381698}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-26 13:00:11',131),(155,'任课信息',3,'com.ruoyi.teach.paike.controller.LessonPlanController.deleteByAdminclass()','POST',1,'admin','研发部门','/teach/paike/lessonPlan/deleteByAdminclass','127.0.0.1','内网IP','{\"adminclass\":\"2班\",\"lessonScheduleId\":1925444731540381698}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-26 13:00:14',11),(156,'任课信息管理',6,'com.ruoyi.teach.paike.controller.LessonPlanController.importData()','POST',1,'admin','研发部门','/teach/paike/lessonPlan/importData','127.0.0.1','内网IP','{\"updateSupport\":\"false\"}','{\"msg\":\"恭喜您，数据已全部导入成功！共 24 条。\",\"code\":200}',0,NULL,'2025-05-26 13:00:27',548),(157,'自动排课',2,'com.ruoyi.teach.paike.intellect.controller.LessonScheduleAutoController.startSchedule()','POST',1,'admin','研发部门','/teach/paike/lessonScheduleAuto/startSchedule','127.0.0.1','内网IP','','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-26 13:00:49',117),(158,'自动排课',2,'com.ruoyi.teach.paike.intellect.controller.LessonScheduleAutoController.startSchedule()','POST',1,'admin','研发部门','/teach/paike/lessonScheduleAuto/startSchedule','127.0.0.1','内网IP','','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-26 13:02:05',46),(159,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin','研发部门','/system/menu','127.0.0.1','内网IP','{\"children\":[],\"component\":\"teach/paike/base/index\",\"createTime\":\"2025-05-22 12:31:09\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2002,\"menuName\":\"基础信息\",\"menuType\":\"C\",\"orderNum\":2,\"params\":{},\"parentId\":2000,\"path\":\"base\",\"perms\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-26 14:03:32',17),(160,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin','研发部门','/system/menu','127.0.0.1','内网IP','{\"children\":[],\"component\":\"teach/paike/intellect/index\",\"createTime\":\"2025-05-22 12:31:09\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2007,\"menuName\":\"智能排课\",\"menuType\":\"C\",\"orderNum\":3,\"params\":{},\"parentId\":2000,\"path\":\"intellect\",\"perms\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-26 14:03:47',13),(161,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin','研发部门','/system/menu','127.0.0.1','内网IP','{\"children\":[],\"component\":\"teach/paike/adjust/index\",\"createTime\":\"2025-05-22 12:31:10\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2015,\"menuName\":\"课表调整\",\"menuType\":\"C\",\"orderNum\":4,\"params\":{},\"parentId\":2000,\"path\":\"adjust\",\"perms\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-26 14:04:02',13),(162,'菜单管理',2,'com.ruoyi.web.controller.system.SysMenuController.edit()','PUT',1,'admin','研发部门','/system/menu','127.0.0.1','内网IP','{\"children\":[],\"component\":\"teach/paike/view/index\",\"createTime\":\"2025-05-22 12:31:10\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2019,\"menuName\":\"查看课表\",\"menuType\":\"C\",\"orderNum\":5,\"params\":{},\"parentId\":2000,\"path\":\"view\",\"perms\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-26 14:04:17',20),(163,'任课信息',3,'com.ruoyi.teach.paike.controller.LessonPlanController.deleteByAdminclass()','POST',1,'admin','研发部门','/teach/paike/lessonPlan/deleteByAdminclass','127.0.0.1','内网IP','{\"adminclass\":\"一（1）班\",\"lessonScheduleId\":1925444731540381698}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-26 14:10:43',12),(164,'任课信息',3,'com.ruoyi.teach.paike.controller.LessonPlanController.deleteByAdminclass()','POST',1,'admin','研发部门','/teach/paike/lessonPlan/deleteByAdminclass','127.0.0.1','内网IP','{\"adminclass\":\"一（2）班\",\"lessonScheduleId\":1925444731540381698}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-26 14:10:45',12),(165,'任课信息',3,'com.ruoyi.teach.paike.controller.LessonPlanController.deleteByAdminclass()','POST',1,'admin','研发部门','/teach/paike/lessonPlan/deleteByAdminclass','127.0.0.1','内网IP','{\"adminclass\":\"二（1）班\",\"lessonScheduleId\":1925444731540381698}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-26 14:10:50',12),(166,'任课信息',3,'com.ruoyi.teach.paike.controller.LessonPlanController.deleteByAdminclass()','POST',1,'admin','研发部门','/teach/paike/lessonPlan/deleteByAdminclass','127.0.0.1','内网IP','{\"adminclass\":\"二（2）班\",\"lessonScheduleId\":1925444731540381698}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-26 14:10:52',11),(167,'任课信息管理',6,'com.ruoyi.teach.paike.controller.LessonPlanController.importData()','POST',1,'admin','研发部门','/teach/paike/lessonPlan/importData','127.0.0.1','内网IP','{\"updateSupport\":\"false\"}','{\"msg\":\"恭喜您，数据已全部导入成功！共 450 条。\",\"code\":200}',0,NULL,'2025-05-26 14:10:57',156),(168,'自动排课',2,'com.ruoyi.teach.paike.intellect.controller.LessonScheduleAutoController.startSchedule()','POST',1,'admin','研发部门','/teach/paike/lessonScheduleAuto/startSchedule','127.0.0.1','内网IP','','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-26 14:11:24',561),(169,'任课信息',3,'com.ruoyi.teach.paike.controller.LessonPlanController.remove()','DELETE',1,'admin','研发部门','/teach/paike/lessonPlan','127.0.0.1','内网IP','{}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-26 14:17:45',113),(170,'任课信息管理',6,'com.ruoyi.teach.paike.controller.LessonPlanController.importData()','POST',1,'admin','研发部门','/teach/paike/lessonPlan/importData','127.0.0.1','内网IP','{\"updateSupport\":\"false\"}','{\"msg\":\"恭喜您，数据已全部导入成功！共 450 条。\",\"code\":200}',0,NULL,'2025-05-26 14:17:58',107),(171,'任课信息',3,'com.ruoyi.teach.paike.controller.LessonPlanController.remove()','DELETE',1,'admin','研发部门','/teach/paike/lessonPlan','127.0.0.1','内网IP','{}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-26 14:20:16',101),(172,'任课信息管理',6,'com.ruoyi.teach.paike.controller.LessonPlanController.importData()','POST',1,'admin','研发部门','/teach/paike/lessonPlan/importData','127.0.0.1','内网IP','{\"updateSupport\":\"false\"}','{\"msg\":\"恭喜您，数据已全部导入成功！共 450 条。\",\"code\":200}',0,NULL,'2025-05-26 14:23:45',150528),(173,'任课信息',3,'com.ruoyi.teach.paike.controller.LessonPlanController.remove()','DELETE',1,'admin','研发部门','/teach/paike/lessonPlan','127.0.0.1','内网IP','{}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-26 14:24:18',149),(174,'任课信息管理',6,'com.ruoyi.teach.paike.controller.LessonPlanController.importData()','POST',1,'admin','研发部门','/teach/paike/lessonPlan/importData','127.0.0.1','内网IP','{\"updateSupport\":\"false\"}','{\"msg\":\"恭喜您，数据已全部导入成功！共 432 条。\",\"code\":200}',0,NULL,'2025-05-26 14:24:25',513),(175,'自动排课',2,'com.ruoyi.teach.paike.intellect.controller.LessonScheduleAutoController.startSchedule()','POST',1,'admin','研发部门','/teach/paike/lessonScheduleAuto/startSchedule','127.0.0.1','内网IP','','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-05-26 14:24:35',678);
/*!40000 ALTER TABLE `sys_oper_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_post`
--

DROP TABLE IF EXISTS `sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_post` (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='岗位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_post`
--

LOCK TABLES `sys_post` WRITE;
/*!40000 ALTER TABLE `sys_post` DISABLE KEYS */;
INSERT INTO `sys_post` VALUES (1,'ceo','董事长',1,'0','admin','2025-05-22 12:30:56','',NULL,''),(2,'se','项目经理',2,'0','admin','2025-05-22 12:30:56','',NULL,''),(3,'hr','人力资源',3,'0','admin','2025-05-22 12:30:56','',NULL,''),(4,'user','普通员工',4,'0','admin','2025-05-22 12:30:56','',NULL,'');
/*!40000 ALTER TABLE `sys_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'超级管理员','admin',1,'1',1,1,'0','0','admin','2025-05-22 12:30:56','',NULL,'超级管理员'),(2,'普通角色','common',2,'2',1,1,'0','0','admin','2025-05-22 12:30:56','',NULL,'普通角色');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_dept`
--

DROP TABLE IF EXISTS `sys_role_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和部门关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_dept`
--

LOCK TABLES `sys_role_dept` WRITE;
/*!40000 ALTER TABLE `sys_role_dept` DISABLE KEYS */;
INSERT INTO `sys_role_dept` VALUES (2,100),(2,101),(2,105);
/*!40000 ALTER TABLE `sys_role_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (2,1),(2,2),(2,3),(2,4),(2,100),(2,101),(2,102),(2,103),(2,104),(2,105),(2,106),(2,107),(2,108),(2,109),(2,110),(2,111),(2,112),(2,113),(2,114),(2,115),(2,116),(2,117),(2,500),(2,501),(2,1000),(2,1001),(2,1002),(2,1003),(2,1004),(2,1005),(2,1006),(2,1007),(2,1008),(2,1009),(2,1010),(2,1011),(2,1012),(2,1013),(2,1014),(2,1015),(2,1016),(2,1017),(2,1018),(2,1019),(2,1020),(2,1021),(2,1022),(2,1023),(2,1024),(2,1025),(2,1026),(2,1027),(2,1028),(2,1029),(2,1030),(2,1031),(2,1032),(2,1033),(2,1034),(2,1035),(2,1036),(2,1037),(2,1038),(2,1039),(2,1040),(2,1041),(2,1042),(2,1043),(2,1044),(2,1045),(2,1046),(2,1047),(2,1048),(2,1049),(2,1050),(2,1051),(2,1052),(2,1053),(2,1054),(2,1055),(2,1056),(2,1057),(2,1058),(2,1059),(2,1060);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,103,'admin','若依','00','ry@163.com','15888888888','1','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','127.0.0.1','2025-05-26 14:00:37','admin','2025-05-22 12:30:56','','2025-05-26 14:00:37','管理员'),(2,105,'ry','若依','00','ry@qq.com','15666666666','1','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','127.0.0.1','2025-05-22 12:30:56','admin','2025-05-22 12:30:56','',NULL,'测试员');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_post`
--

DROP TABLE IF EXISTS `sys_user_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_user_post` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与岗位关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_post`
--

LOCK TABLES `sys_user_post` WRITE;
/*!40000 ALTER TABLE `sys_user_post` DISABLE KEYS */;
INSERT INTO `sys_user_post` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `sys_user_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tpk_classroom`
--

DROP TABLE IF EXISTS `tpk_classroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tpk_classroom` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `school_id` bigint(20) NOT NULL COMMENT '学校ID',
  `lesson_schedule_id` bigint(20) NOT NULL COMMENT '排课任务ID',
  `grade` varchar(10) NOT NULL COMMENT '年级',
  `adminclass` varchar(10) DEFAULT NULL COMMENT '班级',
  `building` varchar(10) NOT NULL COMMENT '教学楼',
  `room` varchar(10) NOT NULL COMMENT '教室号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级教室';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tpk_classroom`
--

LOCK TABLES `tpk_classroom` WRITE;
/*!40000 ALTER TABLE `tpk_classroom` DISABLE KEYS */;
/*!40000 ALTER TABLE `tpk_classroom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tpk_classroom_special`
--

DROP TABLE IF EXISTS `tpk_classroom_special`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tpk_classroom_special` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `school_id` bigint(20) NOT NULL COMMENT '学校ID',
  `lesson_schedule_id` bigint(20) NOT NULL COMMENT '排课任务ID',
  `course` varchar(10) NOT NULL COMMENT '课程',
  `building` varchar(10) NOT NULL COMMENT '教学楼',
  `room` varchar(10) DEFAULT NULL COMMENT '教室号',
  `lesson_limit` int(11) NOT NULL COMMENT '最大开班数',
  `grade` varchar(255) NOT NULL COMMENT '年级',
  `adminclass` varchar(255) DEFAULT NULL COMMENT '班级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='特殊教室';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tpk_classroom_special`
--

LOCK TABLES `tpk_classroom_special` WRITE;
/*!40000 ALTER TABLE `tpk_classroom_special` DISABLE KEYS */;
/*!40000 ALTER TABLE `tpk_classroom_special` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tpk_lesson_plan`
--

DROP TABLE IF EXISTS `tpk_lesson_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tpk_lesson_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `school_id` bigint(20) NOT NULL COMMENT '学校ID',
  `lesson_schedule_id` bigint(20) NOT NULL COMMENT '排课任务ID',
  `grade` varchar(8) NOT NULL COMMENT '年级',
  `adminclass` varchar(8) NOT NULL COMMENT '班级',
  `course` varchar(16) NOT NULL COMMENT '课程',
  `hours` varchar(16) NOT NULL COMMENT '课时',
  `hour` int(11) NOT NULL COMMENT '课时',
  `teacher` varchar(16) NOT NULL COMMENT '任课教师',
  `times_pres` varchar(255) DEFAULT NULL COMMENT '预排',
  `times` varchar(255) DEFAULT NULL COMMENT '排课',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1926887127360741420 DEFAULT CHARSET=utf8 COMMENT='任课信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tpk_lesson_plan`
--

LOCK TABLES `tpk_lesson_plan` WRITE;
/*!40000 ALTER TABLE `tpk_lesson_plan` DISABLE KEYS */;
INSERT INTO `tpk_lesson_plan` VALUES (1926887127134248961,0,1925444731540381698,'一年级','一（1）班','体育','5',5,'马平佳',NULL,'1-5、2-7、3-2、4-7、5-4'),(1926887127134248962,0,1925444731540381698,'一年级','一（1）班','写字','1',1,'李桦',NULL,'4-6'),(1926887127134248963,0,1925444731540381698,'一年级','一（1）班','唱游','2',2,'时佳薇',NULL,'1-3、5-1'),(1926887127134248964,0,1925444731540381698,'一年级','一（1）班','探究','1',1,'陆异毅',NULL,'3-6'),(1926887127134248965,0,1925444731540381698,'一年级','一（1）班','数学','3',3,'钮晨熹',NULL,'1-1、2-2、5-3'),(1926887127134248966,0,1925444731540381698,'一年级','一（1）班','校班会','1',1,'李桦',NULL,'1-2'),(1926887127134248967,0,1925444731540381698,'一年级','一（1）班','活动1','1',1,'',NULL,'2-1'),(1926887127134248968,0,1925444731540381698,'一年级','一（1）班','活动2','1',1,'李桦',NULL,'3-3'),(1926887127134248969,0,1925444731540381698,'一年级','一（1）班','活动3','1',1,'钮晨熹',NULL,'4-1'),(1926887127134248970,0,1925444731540381698,'一年级','一（1）班','活动4','1',1,'郑妮',NULL,'3-5'),(1926887127134248971,0,1925444731540381698,'一年级','一（1）班','美术','2',2,'陆斐',NULL,'2-4、5-7'),(1926887127163609089,0,1925444731540381698,'一年级','一（1）班','自然','2',2,'苏春婷',NULL,'2-3、4-5'),(1926887127163609090,0,1925444731540381698,'一年级','一（1）班','英语','2',2,'郑妮',NULL,'2-5、5-2'),(1926887127163609091,0,1925444731540381698,'一年级','一（1）班','语文','8',8,'李桦',NULL,'1-6、1-7、2-6、3-1、4-2、4-3、5-5、5-6'),(1926887127163609092,0,1925444731540381698,'一年级','一（1）班','道法','2',2,'刘馨梅',NULL,'1-4、3-7'),(1926887127163609093,0,1925444731540381698,'一年级','一（2）班','体育','5',5,'马平佳',NULL,'1-7、2-1、3-1、4-1、5-6'),(1926887127163609094,0,1925444731540381698,'一年级','一（2）班','写字','1',1,'陈彦炜',NULL,'2-3'),(1926887127163609095,0,1925444731540381698,'一年级','一（2）班','唱游','2',2,'时佳薇',NULL,'2-4、3-3'),(1926887127163609096,0,1925444731540381698,'一年级','一（2）班','探究','1',1,'陆异毅',NULL,'1-5'),(1926887127163609097,0,1925444731540381698,'一年级','一（2）班','数学','3',3,'张弦筝',NULL,'2-5、3-2、4-7'),(1926887127163609098,0,1925444731540381698,'一年级','一（2）班','校班会','1',1,'郑妮',NULL,'4-4'),(1926887127163609099,0,1925444731540381698,'一年级','一（2）班','活动1','1',1,'',NULL,'1-4'),(1926887127163609100,0,1925444731540381698,'一年级','一（2）班','活动2','1',1,'陈彦炜',NULL,'5-5'),(1926887127163609101,0,1925444731540381698,'一年级','一（2）班','活动3','1',1,'张弦筝',NULL,'4-2'),(1926887127163609102,0,1925444731540381698,'一年级','一（2）班','活动4','1',1,'郑妮',NULL,'5-3'),(1926887127163609103,0,1925444731540381698,'一年级','一（2）班','美术','2',2,'陆斐',NULL,'1-3、4-6'),(1926887127163609104,0,1925444731540381698,'一年级','一（2）班','自然','2',2,'苏春婷',NULL,'2-2、5-7'),(1926887127163609105,0,1925444731540381698,'一年级','一（2）班','英语','2',2,'郑妮',NULL,'1-6、3-7'),(1926887127163609106,0,1925444731540381698,'一年级','一（2）班','语文','8',8,'陈彦炜',NULL,'1-1、1-2、2-6、2-7、3-5、3-6、4-3、5-4'),(1926887127163609107,0,1925444731540381698,'一年级','一（2）班','道法','2',2,'刘馨梅',NULL,'3-4、5-2'),(1926887127163609108,0,1925444731540381698,'一年级','一（3）班','体育','5',5,'徐淑蕾',NULL,'1-1、2-2、3-4、4-3、5-6'),(1926887127163609109,0,1925444731540381698,'一年级','一（3）班','写字','1',1,'尹瑛',NULL,'1-7'),(1926887127163609110,0,1925444731540381698,'一年级','一（3）班','唱游','2',2,'李怡秋',NULL,'3-3、5-3'),(1926887127163609111,0,1925444731540381698,'一年级','一（3）班','探究','1',1,'陆异毅',NULL,'4-1'),(1926887127163609112,0,1925444731540381698,'一年级','一（3）班','数学','3',3,'丰悦',NULL,'1-5、3-6、5-5'),(1926887127163609113,0,1925444731540381698,'一年级','一（3）班','校班会','1',1,'尹瑛',NULL,'3-7'),(1926887127163609114,0,1925444731540381698,'一年级','一（3）班','活动1','1',1,'',NULL,'3-5'),(1926887127163609115,0,1925444731540381698,'一年级','一（3）班','活动2','1',1,'尹瑛',NULL,'4-4'),(1926887127163609116,0,1925444731540381698,'一年级','一（3）班','活动3','1',1,'丰悦',NULL,'5-7'),(1926887127163609117,0,1925444731540381698,'一年级','一（3）班','活动4','1',1,'许辰婕',NULL,'2-6'),(1926887127163609118,0,1925444731540381698,'一年级','一（3）班','美术','2',2,'陆斐',NULL,'2-5、4-5'),(1926887127163609119,0,1925444731540381698,'一年级','一（3）班','自然','2',2,'苏春婷',NULL,'2-4、4-7'),(1926887127163609120,0,1925444731540381698,'一年级','一（3）班','英语','2',2,'许辰婕',NULL,'2-3、4-6'),(1926887127163609121,0,1925444731540381698,'一年级','一（3）班','语文','8',8,'尹瑛',NULL,'1-3、1-4、2-7、3-1、3-2、4-2、5-1、5-2'),(1926887127163609122,0,1925444731540381698,'一年级','一（3）班','道法','2',2,'刘馨梅',NULL,'2-1、5-4'),(1926887127163609123,0,1925444731540381698,'一年级','一（4）班','体育','5',5,'徐淑蕾',NULL,'1-3、2-1、3-1、4-4、5-4'),(1926887127163609124,0,1925444731540381698,'一年级','一（4）班','写字','1',1,'蔡怡静',NULL,'3-2'),(1926887127163609125,0,1925444731540381698,'一年级','一（4）班','唱游','2',2,'李怡秋',NULL,'1-7、4-2'),(1926887127163609126,0,1925444731540381698,'一年级','一（4）班','探究','1',1,'陆异毅',NULL,'5-5'),(1926887127163609127,0,1925444731540381698,'一年级','一（4）班','数学','3',3,'丰悦',NULL,'2-7、3-5、5-2'),(1926887127163609128,0,1925444731540381698,'一年级','一（4）班','校班会','1',1,'蔡怡静',NULL,'4-1'),(1926887127163609129,0,1925444731540381698,'一年级','一（4）班','活动1','1',1,'',NULL,'2-5'),(1926887127163609130,0,1925444731540381698,'一年级','一（4）班','活动2','1',1,'蔡怡静',NULL,'1-1'),(1926887127163609131,0,1925444731540381698,'一年级','一（4）班','活动3','1',1,'丰悦',NULL,'4-3'),(1926887127163609132,0,1925444731540381698,'一年级','一（4）班','活动4','1',1,'孟淑芬',NULL,'2-4'),(1926887127163609133,0,1925444731540381698,'一年级','一（4）班','美术','2',2,'陆斐',NULL,'3-7、4-7'),(1926887127163609134,0,1925444731540381698,'一年级','一（4）班','自然','2',2,'金敏',NULL,'2-6、5-1'),(1926887127163609135,0,1925444731540381698,'一年级','一（4）班','英语','2',2,'孟淑芬',NULL,'2-3、3-6'),(1926887127163609136,0,1925444731540381698,'一年级','一（4）班','语文','8',8,'蔡怡静',NULL,'1-6、2-2、3-3、3-4、4-5、4-6、5-6、5-7'),(1926887127163609137,0,1925444731540381698,'一年级','一（4）班','道法','2',2,'黄若妍',NULL,'1-4、5-3'),(1926887127163609138,0,1925444731540381698,'一年级','一（5）班','体育','5',5,'董亮杰',NULL,'1-6、2-5、3-1、4-7、5-6'),(1926887127163609139,0,1925444731540381698,'一年级','一（5）班','写字','1',1,'刘聆溪',NULL,'3-4'),(1926887127163609140,0,1925444731540381698,'一年级','一（5）班','唱游','2',2,'李怡秋',NULL,'2-3、5-5'),(1926887127163609141,0,1925444731540381698,'一年级','一（5）班','探究','1',1,'陆异毅',NULL,'4-5'),(1926887127163609142,0,1925444731540381698,'一年级','一（5）班','数学','3',3,'张弦筝',NULL,'1-7、3-7、5-1'),(1926887127163609143,0,1925444731540381698,'一年级','一（5）班','校班会','1',1,'张弦筝',NULL,'5-7'),(1926887127163609144,0,1925444731540381698,'一年级','一（5）班','活动1','1',1,'',NULL,'2-6'),(1926887127163609145,0,1925444731540381698,'一年级','一（5）班','活动2','1',1,'刘聆溪',NULL,'1-2'),(1926887127163609146,0,1925444731540381698,'一年级','一（5）班','活动3','1',1,'张弦筝',NULL,'3-6'),(1926887127163609147,0,1925444731540381698,'一年级','一（5）班','活动4','1',1,'陈玲',NULL,'1-4'),(1926887127163609148,0,1925444731540381698,'一年级','一（5）班','美术','2',2,'李元基',NULL,'2-4、4-4'),(1926887127163609149,0,1925444731540381698,'一年级','一（5）班','自然','2',2,'金敏',NULL,'2-2、4-6'),(1926887127163609150,0,1925444731540381698,'一年级','一（5）班','英语','2',2,'陈玲',NULL,'1-3、4-3'),(1926887127163609151,0,1925444731540381698,'一年级','一（5）班','语文','8',8,'刘聆溪',NULL,'1-5、2-1、3-2、3-3、4-1、4-2、5-3、5-4'),(1926887127163609152,0,1925444731540381698,'一年级','一（5）班','道法','2',2,'黄若妍',NULL,'3-5、5-2'),(1926887127163609153,0,1925444731540381698,'一年级','一（6）班','体育','5',5,'董亮杰',NULL,'1-4、2-7、3-6、4-6、5-7'),(1926887127163609154,0,1925444731540381698,'一年级','一（6）班','写字','1',1,'贾懿',NULL,'5-2'),(1926887127163609155,0,1925444731540381698,'一年级','一（6）班','唱游','2',2,'时佳薇',NULL,'2-5、4-7'),(1926887127163609156,0,1925444731540381698,'一年级','一（6）班','探究','1',1,'陆异毅',NULL,'3-3'),(1926887127163609157,0,1925444731540381698,'一年级','一（6）班','数学','3',3,'黄若妍',NULL,'2-4、4-4、5-4'),(1926887127163609158,0,1925444731540381698,'一年级','一（6）班','校班会','1',1,'贾懿',NULL,'5-1'),(1926887127163609159,0,1925444731540381698,'一年级','一（6）班','活动1','1',1,'',NULL,'4-5'),(1926887127163609160,0,1925444731540381698,'一年级','一（6）班','活动2','1',1,'黄琼',NULL,'2-2'),(1926887127163609161,0,1925444731540381698,'一年级','一（6）班','活动3','1',1,'黄若妍',NULL,'3-2'),(1926887127230717954,0,1925444731540381698,'一年级','一（6）班','活动4','1',1,'徐煚',NULL,'1-5'),(1926887127230717955,0,1925444731540381698,'一年级','一（6）班','美术','2',2,'李元基',NULL,'2-6、3-1'),(1926887127230717956,0,1925444731540381698,'一年级','一（6）班','自然','2',2,'金敏',NULL,'2-1、4-3'),(1926887127230717957,0,1925444731540381698,'一年级','一（6）班','英语','2',2,'徐煚',NULL,'1-6、3-7'),(1926887127230717958,0,1925444731540381698,'一年级','一（6）班','语文','8',8,'黄琼',NULL,'1-2、1-3、2-3、3-5、4-1、4-2、5-5、5-6'),(1926887127230717959,0,1925444731540381698,'一年级','一（6）班','道法','2',2,'赵倩',NULL,'3-4、5-3'),(1926887127230717960,0,1925444731540381698,'二年级','二（1）班','体育','5',5,'李爱菊',NULL,'1-1、2-7、3-1、4-7、5-2'),(1926887127230717961,0,1925444731540381698,'二年级','二（1）班','写字','1',1,'杜美玲',NULL,'3-6'),(1926887127230717962,0,1925444731540381698,'二年级','二（1）班','唱游','2',2,'时佳薇',NULL,'2-6、4-5'),(1926887127230717963,0,1925444731540381698,'二年级','二（1）班','探究','1',1,'李元基',NULL,'4-6'),(1926887127230717964,0,1925444731540381698,'二年级','二（1）班','数学','4',4,'刘静静',NULL,'1-7、2-1、4-3、5-5'),(1926887127230717965,0,1925444731540381698,'二年级','二（1）班','校班会','1',1,'杜美玲',NULL,'3-2'),(1926887127230717966,0,1925444731540381698,'二年级','二（1）班','活动1','1',1,'',NULL,'1-2'),(1926887127230717967,0,1925444731540381698,'二年级','二（1）班','活动2','1',1,'杜美玲',NULL,'5-7'),(1926887127230717968,0,1925444731540381698,'二年级','二（1）班','活动3','1',1,'刘静静',NULL,'3-7'),(1926887127230717969,0,1925444731540381698,'二年级','二（1）班','美术','2',2,'李元基',NULL,'1-5、5-1'),(1926887127230717970,0,1925444731540381698,'二年级','二（1）班','自然','2',2,'苏春婷',NULL,'3-5、4-4'),(1926887127230717971,0,1925444731540381698,'二年级','二（1）班','英语','2',2,'邱佳蓉',NULL,'1-6、4-2'),(1926887127230717972,0,1925444731540381698,'二年级','二（1）班','语文','8',8,'杜美玲',NULL,'1-3、1-4、2-3、2-4、3-3、3-4、4-1、5-4'),(1926887127230717973,0,1925444731540381698,'二年级','二（1）班','道法','2',2,'刘聆溪',NULL,'2-2、5-6'),(1926887127230717974,0,1925444731540381698,'二年级','二（2）班','体育','5',5,'李爱菊',NULL,'1-4、2-4、3-5、4-5、5-6'),(1926887127230717975,0,1925444731540381698,'二年级','二（2）班','写字','1',1,'刘天煜',NULL,'1-1'),(1926887127230717976,0,1925444731540381698,'二年级','二（2）班','唱游','2',2,'朱梦云',NULL,'2-2、4-6'),(1926887127230717977,0,1925444731540381698,'二年级','二（2）班','探究','1',1,'李元基',NULL,'4-7'),(1926887127230717978,0,1925444731540381698,'二年级','二（2）班','数学','4',4,'金敏',NULL,'1-3、3-7、4-1、5-5'),(1926887127230717979,0,1925444731540381698,'二年级','二（2）班','校班会','1',1,'朱梦云',NULL,'4-2'),(1926887127230717980,0,1925444731540381698,'二年级','二（2）班','活动1','1',1,'',NULL,'3-1'),(1926887127230717981,0,1925444731540381698,'二年级','二（2）班','活动2','1',1,'黄雯静',NULL,'2-7'),(1926887127230717982,0,1925444731540381698,'二年级','二（2）班','活动3','1',1,'金敏',NULL,'5-7'),(1926887127230717983,0,1925444731540381698,'二年级','二（2）班','美术','2',2,'李元基',NULL,'2-3、3-6'),(1926887127230717984,0,1925444731540381698,'二年级','二（2）班','自然','2',2,'苏春婷',NULL,'1-5、4-3'),(1926887127230717985,0,1925444731540381698,'二年级','二（2）班','英语','2',2,'徐煚',NULL,'2-1、5-4'),(1926887127230717986,0,1925444731540381698,'二年级','二（2）班','语文','8',8,'刘天煜',NULL,'1-6、1-7、2-5、2-6、3-3、4-4、5-2、5-3'),(1926887127230717987,0,1925444731540381698,'二年级','二（2）班','道法','2',2,'郑妮',NULL,'3-4、5-1'),(1926887127230717988,0,1925444731540381698,'二年级','二（3）班','体育','4',4,'莫融',NULL,'1-4、2-3、3-7、5-2'),(1926887127230717989,0,1925444731540381698,'二年级','二（3）班','写字','1',1,'赵倩',NULL,'2-1'),(1926887127230717990,0,1925444731540381698,'二年级','二（3）班','唱游','2',2,'时佳薇',NULL,'3-4、4-4'),(1926887127230717991,0,1925444731540381698,'二年级','二（3）班','探究','1',1,'李元基',NULL,'1-2'),(1926887127230717992,0,1925444731540381698,'二年级','二（3）班','数学','4',4,'刘静静',NULL,'2-5、3-6、4-2、5-6'),(1926887127230717993,0,1925444731540381698,'二年级','二（3）班','校班会','1',1,'刘静静',NULL,'2-4'),(1926887127230717994,0,1925444731540381698,'二年级','二（3）班','活动1','1',1,'',NULL,'4-5'),(1926887127230717995,0,1925444731540381698,'二年级','二（3）班','活动2','1',1,'赵倩',NULL,'4-1'),(1926887127230717996,0,1925444731540381698,'二年级','二（3）班','活动3','1',1,'刘静静',NULL,'3-5'),(1926887127230717997,0,1925444731540381698,'二年级','二（3）班','美术','2',2,'吴忆明',NULL,'1-1、5-7'),(1926887127230717998,0,1925444731540381698,'二年级','二（3）班','自然','2',2,'苏春婷',NULL,'1-3、5-5'),(1926887127230717999,0,1925444731540381698,'二年级','二（3）班','英语','2',2,'蒋莉霞',NULL,'3-3、4-3'),(1926887127230718000,0,1925444731540381698,'二年级','二（3）班','语文','8',8,'赵倩',NULL,'1-5、1-6、2-6、2-7、3-1、3-2、4-6、5-1'),(1926887127230718001,0,1925444731540381698,'二年级','二（3）班','道法','2',2,'邱佳蓉',NULL,'2-2、5-4'),(1926887127230718002,0,1925444731540381698,'二年级','二（4）班','体育','4',4,'莫融',NULL,'1-3、2-4、3-6、5-1'),(1926887127230718003,0,1925444731540381698,'二年级','二（4）班','写字','1',1,'周洋洋',NULL,'2-1'),(1926887127230718004,0,1925444731540381698,'二年级','二（4）班','唱游','2',2,'朱梦云',NULL,'3-7、5-3'),(1926887127230718005,0,1925444731540381698,'二年级','二（4）班','探究','1',1,'李元基',NULL,'5-4'),(1926887127230718006,0,1925444731540381698,'二年级','二（4）班','数学','4',4,'杨棋',NULL,'1-6、2-2、3-2、5-2'),(1926887127230718007,0,1925444731540381698,'二年级','二（4）班','校班会','1',1,'周洋洋',NULL,'2-7'),(1926887127230718008,0,1925444731540381698,'二年级','二（4）班','活动1','1',1,'',NULL,'1-4'),(1926887127230718009,0,1925444731540381698,'二年级','二（4）班','活动2','1',1,'周洋洋',NULL,'5-5'),(1926887127230718010,0,1925444731540381698,'二年级','二（4）班','活动3','1',1,'杨棋',NULL,'2-3'),(1926887127230718011,0,1925444731540381698,'二年级','二（4）班','美术','2',2,'吴忆明',NULL,'2-5、4-4'),(1926887127230718012,0,1925444731540381698,'二年级','二（4）班','自然','2',2,'刘馨梅',NULL,'3-1、4-3'),(1926887127230718013,0,1925444731540381698,'二年级','二（4）班','英语','2',2,'徐煚',NULL,'3-4、4-7'),(1926887127230718014,0,1925444731540381698,'二年级','二（4）班','语文','8',8,'周洋洋',NULL,'1-1、1-2、2-6、3-3、4-5、4-6、5-6、5-7'),(1926887127230718015,0,1925444731540381698,'二年级','二（4）班','道法','2',2,'戎璐',NULL,'1-5、4-1'),(1926887127230718016,0,1925444731540381698,'二年级','二（5）班','体育','5',5,'马平佳',NULL,'1-6、2-5、3-4、4-4、5-1'),(1926887127230718017,0,1925444731540381698,'二年级','二（5）班','写字','1',1,'孙诗艺',NULL,'5-2'),(1926887127230718018,0,1925444731540381698,'二年级','二（5）班','唱游','2',2,'朱梦云',NULL,'2-3、5-6'),(1926887127230718019,0,1925444731540381698,'二年级','二（5）班','探究','1',1,'李元基',NULL,'3-5'),(1926887127230718020,0,1925444731540381698,'二年级','二（5）班','数学','4',4,'杨棋',NULL,'1-4、3-1、4-5、5-7'),(1926887127230718021,0,1925444731540381698,'二年级','二（5）班','校班会','1',1,'邱佳蓉',NULL,'1-3'),(1926887127230718022,0,1925444731540381698,'二年级','二（5）班','活动1','1',1,'',NULL,'2-1'),(1926887127230718023,0,1925444731540381698,'二年级','二（5）班','活动2','1',1,'孙诗艺',NULL,'3-3'),(1926887127230718024,0,1925444731540381698,'二年级','二（5）班','活动3','1',1,'杨棋',NULL,'1-7'),(1926887127230718025,0,1925444731540381698,'二年级','二（5）班','美术','2',2,'吴忆明',NULL,'2-6、4-3'),(1926887127230718026,0,1925444731540381698,'二年级','二（5）班','自然','2',2,'刘馨梅',NULL,'3-2、5-5'),(1926887127230718027,0,1925444731540381698,'二年级','二（5）班','英语','2',2,'邱佳蓉',NULL,'1-5、4-7'),(1926887127230718028,0,1925444731540381698,'二年级','二（5）班','语文','8',8,'孙诗艺',NULL,'1-1、1-2、2-7、3-6、3-7、4-1、4-2、5-3'),(1926887127230718029,0,1925444731540381698,'二年级','二（5）班','道法','2',2,'蒋莉霞',NULL,'2-4、4-6'),(1926887127230718030,0,1925444731540381698,'二年级','二（6）班','体育','5',5,'马平佳',NULL,'1-4、2-6、3-3、4-5、5-3'),(1926887127230718031,0,1925444731540381698,'二年级','二（6）班','写字','1',1,'孔晨晨',NULL,'2-7'),(1926887127230718032,0,1925444731540381698,'二年级','二（6）班','唱游','2',2,'朱梦云',NULL,'1-1、5-5'),(1926887127230718033,0,1925444731540381698,'二年级','二（6）班','探究','1',1,'李元基',NULL,'2-1'),(1926887127230718034,0,1925444731540381698,'二年级','二（6）班','数学','4',4,'曹晗',NULL,'1-6、3-7、4-1、5-4'),(1926887127230718035,0,1925444731540381698,'二年级','二（6）班','校班会','1',1,'蒋莉霞',NULL,'5-6'),(1926887127230718036,0,1925444731540381698,'二年级','二（6）班','活动1','1',1,'',NULL,'4-2'),(1926887127230718037,0,1925444731540381698,'二年级','二（6）班','活动2','1',1,'孔晨晨',NULL,'2-5'),(1926887127230718038,0,1925444731540381698,'二年级','二（6）班','活动3','1',1,'曹晗',NULL,'1-2'),(1926887127230718039,0,1925444731540381698,'二年级','二（6）班','美术','2',2,'吴忆明',NULL,'1-5、3-4'),(1926887127230718040,0,1925444731540381698,'二年级','二（6）班','自然','2',2,'刘馨梅',NULL,'3-5、4-7'),(1926887127230718041,0,1925444731540381698,'二年级','二（6）班','英语','2',2,'蒋莉霞',NULL,'3-6、5-2'),(1926887127230718042,0,1925444731540381698,'二年级','二（6）班','语文','8',8,'孔晨晨',NULL,'1-7、2-3、2-4、3-1、3-2、4-3、4-4、5-7'),(1926887127230718043,0,1925444731540381698,'二年级','二（6）班','道法','2',2,'戎璐',NULL,'1-3、4-6'),(1926887127230718044,0,1925444731540381698,'三年级','三（1）班','体育','5',5,'汪雪松',NULL,'1-5、2-5、3-6、4-6、5-2'),(1926887127230718045,0,1925444731540381698,'三年级','三（1）班','信息','2',2,'张哨红',NULL,'3-2、4-5'),(1926887127230718046,0,1925444731540381698,'三年级','三（1）班','写字','1',1,'施沁',NULL,'2-1'),(1926887127230718047,0,1925444731540381698,'三年级','三（1）班','探究','1',1,'陆异毅',NULL,'5-6'),(1926887127230718048,0,1925444731540381698,'三年级','三（1）班','数学','4',4,'陈韻',NULL,'2-6、3-7、4-2、5-5'),(1926887127230718049,0,1925444731540381698,'三年级','三（1）班','校班会','1',1,'施沁',NULL,'1-3'),(1926887127230718050,0,1925444731540381698,'三年级','三（1）班','活动1','1',1,'',NULL,'3-4'),(1926887127230718051,0,1925444731540381698,'三年级','三（1）班','活动2','1',1,'施沁',NULL,'2-2'),(1926887127230718052,0,1925444731540381698,'三年级','三（1）班','活动3','1',1,'陈韻',NULL,'1-4'),(1926887127230718053,0,1925444731540381698,'三年级','三（1）班','美术','2',2,'陆斐',NULL,'3-5、5-4'),(1926887127230718054,0,1925444731540381698,'三年级','三（1）班','自然','2',2,'罗学锋',NULL,'2-4、4-4'),(1926887127230718055,0,1925444731540381698,'三年级','三（1）班','英语','4',4,'王雅萍',NULL,'1-2、3-3、4-1、5-7'),(1926887127230718056,0,1925444731540381698,'三年级','三（1）班','语文','5',5,'施沁',NULL,'1-6、2-3、3-1、4-7、5-1'),(1926887127230718057,0,1925444731540381698,'三年级','三（1）班','道法','2',2,'姜薇',NULL,'1-1、5-3'),(1926887127230718058,0,1925444731540381698,'三年级','三（1）班','音乐','2',2,'王瑾',NULL,'2-7、4-3'),(1926887127230718059,0,1925444731540381698,'三年级','三（2）班','体育','5',5,'汪雪松',NULL,'1-6、2-4、3-7、4-7、5-5'),(1926887127230718060,0,1925444731540381698,'三年级','三（2）班','信息','2',2,'张哨红',NULL,'1-7、4-6'),(1926887127230718061,0,1925444731540381698,'三年级','三（2）班','写字','1',1,'徐海燕',NULL,'2-2'),(1926887127230718062,0,1925444731540381698,'三年级','三（2）班','探究','1',1,'陆异毅',NULL,'3-1'),(1926887127230718063,0,1925444731540381698,'三年级','三（2）班','数学','4',4,'周洁',NULL,'2-1、3-4、4-4、5-6'),(1926887127230718064,0,1925444731540381698,'三年级','三（2）班','校班会','1',1,'周洁',NULL,'3-6'),(1926887127230718065,0,1925444731540381698,'三年级','三（2）班','活动1','1',1,'',NULL,'5-1'),(1926887127230718066,0,1925444731540381698,'三年级','三（2）班','活动2','1',1,'徐海燕',NULL,'4-3'),(1926887127230718067,0,1925444731540381698,'三年级','三（2）班','活动3','1',1,'周洁',NULL,'2-7'),(1926887127230718068,0,1925444731540381698,'三年级','三（2）班','美术','2',2,'陆斐',NULL,'2-3、5-2'),(1926887127230718069,0,1925444731540381698,'三年级','三（2）班','自然','2',2,'罗学锋',NULL,'1-2、4-5'),(1926887127230718070,0,1925444731540381698,'三年级','三（2）班','英语','4',4,'徐亦超',NULL,'1-1、2-6、3-5、5-3'),(1926887127230718071,0,1925444731540381698,'三年级','三（2）班','语文','5',5,'徐海燕',NULL,'1-5、2-5、3-2、4-2、5-7'),(1926887127230718072,0,1925444731540381698,'三年级','三（2）班','道法','2',2,'姜薇',NULL,'3-3、5-4'),(1926887127230718073,0,1925444731540381698,'三年级','三（2）班','音乐','2',2,'王瑾',NULL,'1-3、4-1'),(1926887127230718074,0,1925444731540381698,'三年级','三（3）班','体育','5',5,'汪雪松',NULL,'1-4、2-7、3-2、4-4、5-6'),(1926887127230718075,0,1925444731540381698,'三年级','三（3）班','信息','2',2,'张哨红',NULL,'2-4、5-7'),(1926887127230718076,0,1925444731540381698,'三年级','三（3）班','写字','1',1,'张嘉宜',NULL,'2-2'),(1926887127230718077,0,1925444731540381698,'三年级','三（3）班','探究','1',1,'陆异毅',NULL,'5-4'),(1926887127230718078,0,1925444731540381698,'三年级','三（3）班','数学','4',4,'吴流妹',NULL,'1-3、3-6、4-1、5-3'),(1926887127230718079,0,1925444731540381698,'三年级','三（3）班','校班会','1',1,'张嘉宜',NULL,'2-1'),(1926887127230718080,0,1925444731540381698,'三年级','三（3）班','活动1','1',1,'',NULL,'5-1'),(1926887127230718081,0,1925444731540381698,'三年级','三（3）班','活动2','1',1,'张嘉宜',NULL,'2-5'),(1926887127230718082,0,1925444731540381698,'三年级','三（3）班','活动3','1',1,'吴流妹',NULL,'3-1'),(1926887127230718083,0,1925444731540381698,'三年级','三（3）班','美术','2',2,'陆斐',NULL,'1-5、4-3'),(1926887127230718084,0,1925444731540381698,'三年级','三（3）班','自然','2',2,'罗学锋',NULL,'1-6、4-7'),(1926887127297826818,0,1925444731540381698,'三年级','三（3）班','英语','4',4,'陈嘉春',NULL,'1-2、3-3、4-2、5-5'),(1926887127297826819,0,1925444731540381698,'三年级','三（3）班','语文','5',5,'张嘉宜',NULL,'1-1、2-6、3-5、4-6、5-2'),(1926887127297826820,0,1925444731540381698,'三年级','三（3）班','道法','2',2,'陈颖芳',NULL,'3-4、4-5'),(1926887127297826821,0,1925444731540381698,'三年级','三（3）班','音乐','2',2,'王瑾',NULL,'2-3、3-7'),(1926887127297826822,0,1925444731540381698,'三年级','三（4）班','体育','5',5,'严浩卿',NULL,'1-5、2-5、3-4、4-6、5-1'),(1926887127297826823,0,1925444731540381698,'三年级','三（4）班','信息','2',2,'张哨红',NULL,'2-2、4-3'),(1926887127297826824,0,1925444731540381698,'三年级','三（4）班','写字','1',1,'程云霞',NULL,'4-1'),(1926887127297826825,0,1925444731540381698,'三年级','三（4）班','探究','1',1,'陈颖芳',NULL,'4-4'),(1926887127297826826,0,1925444731540381698,'三年级','三（4）班','数学','4',4,'吴流妹',NULL,'1-7、2-4、3-3、5-6'),(1926887127297826827,0,1925444731540381698,'三年级','三（4）班','校班会','1',1,'吴流妹',NULL,'1-1'),(1926887127297826828,0,1925444731540381698,'三年级','三（4）班','活动1','1',1,'',NULL,'5-5'),(1926887127297826829,0,1925444731540381698,'三年级','三（4）班','活动2','1',1,'程云霞',NULL,'5-3'),(1926887127297826830,0,1925444731540381698,'三年级','三（4）班','活动3','1',1,'吴流妹',NULL,'4-5'),(1926887127297826831,0,1925444731540381698,'三年级','三（4）班','美术','2',2,'万启星',NULL,'2-1、3-6'),(1926887127297826832,0,1925444731540381698,'三年级','三（4）班','自然','2',2,'罗学锋',NULL,'2-6、3-1'),(1926887127297826833,0,1925444731540381698,'三年级','三（4）班','英语','4',4,'顾艳琼',NULL,'1-6、3-7、4-2、5-7'),(1926887127297826834,0,1925444731540381698,'三年级','三（4）班','语文','5',5,'程云霞',NULL,'1-2、2-3、3-2、4-7、5-4'),(1926887127297826835,0,1925444731540381698,'三年级','三（4）班','道法','2',2,'姜薇',NULL,'2-7、3-5'),(1926887127297826836,0,1925444731540381698,'三年级','三（4）班','音乐','2',2,'苏雯雯',NULL,'1-4、5-2'),(1926887127297826837,0,1925444731540381698,'三年级','三（5）班','体育','5',5,'严浩卿',NULL,'1-7、2-6、3-2、4-1、5-5'),(1926887127297826838,0,1925444731540381698,'三年级','三（5）班','信息','2',2,'张哨红',NULL,'1-4、4-2'),(1926887127297826839,0,1925444731540381698,'三年级','三（5）班','写字','1',1,'周建晟',NULL,'4-5'),(1926887127297826840,0,1925444731540381698,'三年级','三（5）班','探究','1',1,'陆异毅',NULL,'3-4'),(1926887127297826841,0,1925444731540381698,'三年级','三（5）班','数学','4',4,'周洁',NULL,'2-3、3-7、4-6、5-3'),(1926887127297826842,0,1925444731540381698,'三年级','三（5）班','校班会','1',1,'王雅萍',NULL,'5-1'),(1926887127297826843,0,1925444731540381698,'三年级','三（5）班','活动1','1',1,'',NULL,'3-5'),(1926887127297826844,0,1925444731540381698,'三年级','三（5）班','活动2','1',1,'周建晟',NULL,'5-7'),(1926887127297826845,0,1925444731540381698,'三年级','三（5）班','活动3','1',1,'周洁',NULL,'1-6'),(1926887127297826846,0,1925444731540381698,'三年级','三（5）班','美术','2',2,'万启星',NULL,'2-2、3-1'),(1926887127297826847,0,1925444731540381698,'三年级','三（5）班','自然','2',2,'罗学锋',NULL,'1-5、5-6'),(1926887127297826848,0,1925444731540381698,'三年级','三（5）班','英语','4',4,'王雅萍',NULL,'1-3、2-4、4-4、5-4'),(1926887127297826849,0,1925444731540381698,'三年级','三（5）班','语文','5',5,'周建晟',NULL,'1-2、2-7、3-3、4-7、5-2'),(1926887127297826850,0,1925444731540381698,'三年级','三（5）班','道法','2',2,'陈颖芳',NULL,'1-1、4-3'),(1926887127297826851,0,1925444731540381698,'三年级','三（5）班','音乐','2',2,'苏雯雯',NULL,'2-5、3-6'),(1926887127297826852,0,1925444731540381698,'三年级','三（6）班','体育','5',5,'严浩卿',NULL,'1-6、2-7、3-3、4-3、5-4'),(1926887127297826853,0,1925444731540381698,'三年级','三（6）班','信息','2',2,'张哨红',NULL,'3-5、5-5'),(1926887127297826854,0,1925444731540381698,'三年级','三（6）班','写字','1',1,'沈佘佳',NULL,'5-2'),(1926887127297826855,0,1925444731540381698,'三年级','三（6）班','探究','1',1,'陆异毅',NULL,'3-2'),(1926887127297826856,0,1925444731540381698,'三年级','三（6）班','数学','4',4,'陈韻',NULL,'1-2、2-1、3-6、5-7'),(1926887127297826857,0,1925444731540381698,'三年级','三（6）班','校班会','1',1,'沈佘佳',NULL,'4-6'),(1926887127297826858,0,1925444731540381698,'三年级','三（6）班','活动1','1',1,'',NULL,'4-5'),(1926887127297826859,0,1925444731540381698,'三年级','三（6）班','活动2','1',1,'沈佘佳',NULL,'1-3'),(1926887127297826860,0,1925444731540381698,'三年级','三（6）班','活动3','1',1,'陈韻',NULL,'2-5'),(1926887127297826861,0,1925444731540381698,'三年级','三（6）班','美术','2',2,'万启星',NULL,'1-1、3-4'),(1926887127297826862,0,1925444731540381698,'三年级','三（6）班','自然','2',2,'罗学锋',NULL,'1-7、4-2'),(1926887127297826863,0,1925444731540381698,'三年级','三（6）班','英语','4',4,'徐亦超',NULL,'1-4、2-4、3-1、5-1'),(1926887127297826864,0,1925444731540381698,'三年级','三（6）班','语文','5',5,'沈佘佳',NULL,'1-5、2-2、3-7、4-7、5-6'),(1926887127297826865,0,1925444731540381698,'三年级','三（6）班','道法','2',2,'陈颖芳',NULL,'2-3、4-1'),(1926887127297826866,0,1925444731540381698,'三年级','三（6）班','音乐','2',2,'苏雯雯',NULL,'2-6、4-4'),(1926887127297826867,0,1925444731540381698,'四年级','四（1）班','体育','5',5,'蔡章',NULL,'1-1、2-7、3-3、4-6、5-6'),(1926887127297826868,0,1925444731540381698,'四年级','四（1）班','写字','1',1,'钱锡娣',NULL,'4-3'),(1926887127297826869,0,1925444731540381698,'四年级','四（1）班','劳技','1',1,'杨瑾',NULL,'1-7'),(1926887127297826870,0,1925444731540381698,'四年级','四（1）班','探究','1',1,'张洁',NULL,'4-7'),(1926887127297826871,0,1925444731540381698,'四年级','四（1）班','数学','5',5,'张洁',NULL,'1-2、2-4、3-2、4-5、5-5'),(1926887127297826872,0,1925444731540381698,'四年级','四（1）班','校班会','1',1,'宋依妮',NULL,'2-6'),(1926887127297826873,0,1925444731540381698,'四年级','四（1）班','活动1','1',1,'',NULL,'5-3'),(1926887127297826874,0,1925444731540381698,'四年级','四（1）班','活动2','1',1,'曹晗',NULL,'5-2'),(1926887127297826875,0,1925444731540381698,'四年级','四（1）班','美术','1',1,'吴忆明',NULL,'3-1'),(1926887127297826876,0,1925444731540381698,'四年级','四（1）班','自然','2',2,'杨瑾',NULL,'2-5、3-6'),(1926887127297826877,0,1925444731540381698,'四年级','四（1）班','英语','5',5,'吴佳',NULL,'1-6、2-1、3-4、4-4、5-4'),(1926887127297826878,0,1925444731540381698,'四年级','四（1）班','语文','5',5,'钱锡娣',NULL,'1-5、2-2、3-5、4-1、5-1'),(1926887127297826879,0,1925444731540381698,'四年级','四（1）班','道法','3',3,'宋依妮',NULL,'2-3、4-2、5-7'),(1926887127297826880,0,1925444731540381698,'四年级','四（1）班','音乐','2',2,'李怡秋',NULL,'1-3、3-7'),(1926887127297826881,0,1925444731540381698,'四年级','四（2）班','体育','5',5,'蔡章',NULL,'1-6、2-4、3-7、4-4、5-3'),(1926887127297826882,0,1925444731540381698,'四年级','四（2）班','写字','1',1,'王娇',NULL,'4-7'),(1926887127297826883,0,1925444731540381698,'四年级','四（2）班','劳技','1',1,'杨瑾',NULL,'4-6'),(1926887127297826884,0,1925444731540381698,'四年级','四（2）班','探究','1',1,'张洁',NULL,'1-5'),(1926887127297826885,0,1925444731540381698,'四年级','四（2）班','数学','5',5,'蔡传钰',NULL,'1-2、2-3、3-4、4-2、5-1'),(1926887127297826886,0,1925444731540381698,'四年级','四（2）班','校班会','1',1,'王娇',NULL,'3-6'),(1926887127297826887,0,1925444731540381698,'四年级','四（2）班','活动1','1',1,'',NULL,'3-1'),(1926887127297826888,0,1925444731540381698,'四年级','四（2）班','活动2','1',1,'王娇',NULL,'2-1'),(1926887127297826889,0,1925444731540381698,'四年级','四（2）班','美术','1',1,'吴忆明',NULL,'5-2'),(1926887127297826890,0,1925444731540381698,'四年级','四（2）班','自然','2',2,'杨瑾',NULL,'1-3、5-6'),(1926887127297826891,0,1925444731540381698,'四年级','四（2）班','英语','5',5,'吴佳',NULL,'1-1、2-6、3-5、4-1、5-7'),(1926887127297826892,0,1925444731540381698,'四年级','四（2）班','语文','5',5,'王娇',NULL,'1-4、2-2、3-2、4-5、5-5'),(1926887127297826893,0,1925444731540381698,'四年级','四（2）班','道法','3',3,'宋依妮',NULL,'2-5、4-3、5-4'),(1926887127297826894,0,1925444731540381698,'四年级','四（2）班','音乐','2',2,'朱梦云',NULL,'1-7、3-3'),(1926887127297826895,0,1925444731540381698,'四年级','四（3）班','体育','5',5,'蔡章',NULL,'1-4、2-2、3-4、4-1、5-4'),(1926887127297826896,0,1925444731540381698,'四年级','四（3）班','写字','1',1,'李丹妮',NULL,'5-3'),(1926887127297826897,0,1925444731540381698,'四年级','四（3）班','劳技','1',1,'杨瑾',NULL,'3-1'),(1926887127297826898,0,1925444731540381698,'四年级','四（3）班','探究','1',1,'张洁',NULL,'2-1'),(1926887127297826899,0,1925444731540381698,'四年级','四（3）班','数学','5',5,'阮惠玲',NULL,'1-3、2-6、3-2、4-4、5-2'),(1926887127297826900,0,1925444731540381698,'四年级','四（3）班','校班会','1',1,'李丹妮',NULL,'3-3'),(1926887127297826901,0,1925444731540381698,'四年级','四（3）班','活动1','1',1,'',NULL,'4-2'),(1926887127297826902,0,1925444731540381698,'四年级','四（3）班','活动2','1',1,'李丹妮',NULL,'1-2'),(1926887127297826903,0,1925444731540381698,'四年级','四（3）班','美术','1',1,'吴忆明',NULL,'5-6'),(1926887127297826904,0,1925444731540381698,'四年级','四（3）班','自然','2',2,'胡剑',NULL,'2-4、5-7'),(1926887127297826905,0,1925444731540381698,'四年级','四（3）班','英语','5',5,'陈玲',NULL,'1-7、2-7、3-5、4-6、5-1'),(1926887127297826906,0,1925444731540381698,'四年级','四（3）班','语文','5',5,'李丹妮',NULL,'1-6、2-5、3-6、4-3、5-5'),(1926887127297826907,0,1925444731540381698,'四年级','四（3）班','道法','3',3,'丰悦',NULL,'1-1、2-3、4-5'),(1926887127297826908,0,1925444731540381698,'四年级','四（3）班','音乐','2',2,'朱梦云',NULL,'1-5、4-7'),(1926887127297826909,0,1925444731540381698,'四年级','四（4）班','体育','4',4,'莫融',NULL,'1-7、2-6、4-4、5-5'),(1926887127297826910,0,1925444731540381698,'四年级','四（4）班','写字','1',1,'岱瑛',NULL,'1-2'),(1926887127297826911,0,1925444731540381698,'四年级','四（4）班','劳技','1',1,'杨瑾',NULL,'4-1'),(1926887127297826912,0,1925444731540381698,'四年级','四（4）班','探究','1',1,'张洁',NULL,'2-5'),(1926887127297826913,0,1925444731540381698,'四年级','四（4）班','数学','5',5,'李欣荣',NULL,'1-4、2-4、3-6、4-6、5-1'),(1926887127297826914,0,1925444731540381698,'四年级','四（4）班','校班会','1',1,'岱瑛',NULL,'5-3'),(1926887127297826915,0,1925444731540381698,'四年级','四（4）班','活动1','1',1,'',NULL,'3-5'),(1926887127297826916,0,1925444731540381698,'四年级','四（4）班','活动2','1',1,'岱瑛',NULL,'3-1'),(1926887127297826917,0,1925444731540381698,'四年级','四（4）班','美术','1',1,'吴忆明',NULL,'4-7'),(1926887127297826918,0,1925444731540381698,'四年级','四（4）班','自然','2',2,'胡剑',NULL,'2-1、5-4'),(1926887127297826919,0,1925444731540381698,'四年级','四（4）班','英语','5',5,'徐然',NULL,'1-6、2-7、3-2、4-5、5-2'),(1926887127297826920,0,1925444731540381698,'四年级','四（4）班','语文','5',5,'岱瑛',NULL,'1-1、2-3、3-3、4-2、5-7'),(1926887127297826921,0,1925444731540381698,'四年级','四（4）班','道法','3',3,'宋依妮',NULL,'1-3、3-7、5-6'),(1926887127297826922,0,1925444731540381698,'四年级','四（4）班','音乐','2',2,'李怡秋',NULL,'1-5、4-3'),(1926887127297826923,0,1925444731540381698,'四年级','四（5）班','体育','5',5,'严浩卿',NULL,'1-1、2-3、3-1、4-4、5-2'),(1926887127297826924,0,1925444731540381698,'四年级','四（5）班','写字','1',1,'刘萌鹃',NULL,'4-5'),(1926887127297826925,0,1925444731540381698,'四年级','四（5）班','劳技','1',1,'杨瑾',NULL,'1-2'),(1926887127297826926,0,1925444731540381698,'四年级','四（5）班','探究','1',1,'张洁',NULL,'3-4'),(1926887127297826927,0,1925444731540381698,'四年级','四（5）班','数学','5',5,'蔡传钰',NULL,'1-5、2-2、3-2、4-7、5-3'),(1926887127297826928,0,1925444731540381698,'四年级','四（5）班','校班会','1',1,'徐然',NULL,'5-6'),(1926887127297826929,0,1925444731540381698,'四年级','四（5）班','活动1','1',1,'',NULL,'5-1'),(1926887127297826930,0,1925444731540381698,'四年级','四（5）班','活动2','1',1,'刘萌鹃',NULL,'4-2'),(1926887127297826931,0,1925444731540381698,'四年级','四（5）班','美术','1',1,'吴忆明',NULL,'1-7'),(1926887127297826932,0,1925444731540381698,'四年级','四（5）班','自然','2',2,'胡剑',NULL,'2-7、3-5'),(1926887127297826933,0,1925444731540381698,'四年级','四（5）班','英语','5',5,'徐然',NULL,'1-3、2-6、3-6、4-6、5-4'),(1926887127297826934,0,1925444731540381698,'四年级','四（5）班','语文','5',5,'刘萌鹃',NULL,'1-6、2-5、3-3、4-3、5-5'),(1926887127297826935,0,1925444731540381698,'四年级','四（5）班','道法','3',3,'陈彦炜',NULL,'2-4、3-7、5-7'),(1926887127297826936,0,1925444731540381698,'四年级','四（5）班','音乐','2',2,'李怡秋',NULL,'1-4、4-1'),(1926887127297826937,0,1925444731540381698,'四年级','四（6）班','体育','4',4,'莫融',NULL,'2-1、3-2、4-6、5-6'),(1926887127297826938,0,1925444731540381698,'四年级','四（6）班','写字','1',1,'王娟',NULL,'1-6'),(1926887127297826939,0,1925444731540381698,'四年级','四（6）班','劳技','1',1,'杨瑾',NULL,'4-7'),(1926887127297826940,0,1925444731540381698,'四年级','四（6）班','探究','1',1,'张洁',NULL,'3-7'),(1926887127297826941,0,1925444731540381698,'四年级','四（6）班','数学','5',5,'阮惠玲',NULL,'1-7、2-5、3-3、4-1、5-4'),(1926887127297826942,0,1925444731540381698,'四年级','四（6）班','校班会','1',1,'王娟',NULL,'4-4'),(1926887127297826943,0,1925444731540381698,'四年级','四（6）班','活动1','1',1,'',NULL,'2-2'),(1926887127297826944,0,1925444731540381698,'四年级','四（6）班','活动2','1',1,'王娟',NULL,'4-2'),(1926887127297826945,0,1925444731540381698,'四年级','四（6）班','美术','1',1,'吴忆明',NULL,'1-3'),(1926887127297826946,0,1925444731540381698,'四年级','四（6）班','自然','2',2,'罗学锋',NULL,'3-4、5-1'),(1926887127297826947,0,1925444731540381698,'四年级','四（6）班','英语','5',5,'陈玲',NULL,'1-1、2-4、3-6、4-5、5-3'),(1926887127297826948,0,1925444731540381698,'四年级','四（6）班','语文','5',5,'王娟',NULL,'1-2、2-3、3-5、4-3、5-5'),(1926887127297826949,0,1925444731540381698,'四年级','四（6）班','道法','3',3,'宋依妮',NULL,'1-5、3-1、5-2'),(1926887127297826950,0,1925444731540381698,'四年级','四（6）班','音乐','2',2,'李怡秋',NULL,'2-7、5-7'),(1926887127297826951,0,1925444731540381698,'五年级','五（1）班','体育','5',5,'董亮杰',NULL,'1-5、2-6、3-7、4-1、5-2'),(1926887127297826952,0,1925444731540381698,'五年级','五（1）班','写字','1',1,'崔秀娟',NULL,'3-5'),(1926887127297826953,0,1925444731540381698,'五年级','五（1）班','劳技','1',1,'杨瑾',NULL,'2-1'),(1926887127297826954,0,1925444731540381698,'五年级','五（1）班','探究','1',1,'陆异毅',NULL,'2-3'),(1926887127297826955,0,1925444731540381698,'五年级','五（1）班','数学','5',5,'邓桢轶',NULL,'1-4、2-7、3-1、4-5、5-5'),(1926887127297826956,0,1925444731540381698,'五年级','五（1）班','校班会','1',1,'邓桢轶',NULL,'1-7'),(1926887127297826957,0,1925444731540381698,'五年级','五（1）班','活动1','1',1,'',NULL,'4-4'),(1926887127297826958,0,1925444731540381698,'五年级','五（1）班','活动2','1',1,'曹晗+俞洁霞',NULL,'4-2'),(1926887127297826959,0,1925444731540381698,'五年级','五（1）班','美术','1',1,'万启星',NULL,'3-2'),(1926887127297826960,0,1925444731540381698,'五年级','五（1）班','自然','2',2,'吴峥嵘',NULL,'2-2、5-7'),(1926887127297826961,0,1925444731540381698,'五年级','五（1）班','英语','5',5,'许辰婕',NULL,'1-3、2-4、3-3、4-3、5-6'),(1926887127297826962,0,1925444731540381698,'五年级','五（1）班','语文','5',5,'崔秀娟',NULL,'1-2、2-5、3-6、4-6、5-3'),(1926887127297826963,0,1925444731540381698,'五年级','五（1）班','道法','3',3,'崔秀娟',NULL,'1-6、3-4、4-7'),(1926887127297826964,0,1925444731540381698,'五年级','五（1）班','音乐','2',2,'苏雯雯',NULL,'1-1、5-1'),(1926887127297826965,0,1925444731540381698,'五年级','五（2）班','体育','5',5,'董亮杰',NULL,'1-7、2-2、3-4、4-4、5-3'),(1926887127297826966,0,1925444731540381698,'五年级','五（2）班','写字','1',1,'龚艳',NULL,'1-3'),(1926887127297826967,0,1925444731540381698,'五年级','五（2）班','劳技','1',1,'杨瑾',NULL,'3-2'),(1926887127297826968,0,1925444731540381698,'五年级','五（2）班','探究','1',1,'沈萍平',NULL,'2-1'),(1926887127297826969,0,1925444731540381698,'五年级','五（2）班','数学','5',5,'邓桢轶',NULL,'1-1、2-6、3-7、4-2、5-2'),(1926887127297826970,0,1925444731540381698,'五年级','五（2）班','校班会','1',1,'龚艳',NULL,'5-1'),(1926887127297826971,0,1925444731540381698,'五年级','五（2）班','活动1','1',1,'',NULL,'1-5'),(1926887127297826972,0,1925444731540381698,'五年级','五（2）班','活动2','1',1,'曹晗+俞洁霞',NULL,'4-1'),(1926887127297826973,0,1925444731540381698,'五年级','五（2）班','美术','1',1,'万启星',NULL,'1-2'),(1926887127297826974,0,1925444731540381698,'五年级','五（2）班','自然','2',2,'吴峥嵘',NULL,'3-5、5-6'),(1926887127297826975,0,1925444731540381698,'五年级','五（2）班','英语','5',5,'孟淑芬',NULL,'1-6、2-5、3-3、4-3、5-5'),(1926887127297826976,0,1925444731540381698,'五年级','五（2）班','语文','5',5,'龚艳',NULL,'1-4、2-4、3-1、4-7、5-7'),(1926887127297826977,0,1925444731540381698,'五年级','五（2）班','道法','3',3,'许琦',NULL,'2-3、4-5、5-4'),(1926887127297826978,0,1925444731540381698,'五年级','五（2）班','音乐','2',2,'苏雯雯',NULL,'2-7、4-6'),(1926887127297826979,0,1925444731540381698,'五年级','五（3）班','体育','5',5,'钱佳明',NULL,'1-1、2-4、3-4、4-2、5-2'),(1926887127297826980,0,1925444731540381698,'五年级','五（3）班','写字','1',1,'田莉莉',NULL,'3-5'),(1926887127297826981,0,1925444731540381698,'五年级','五（3）班','劳技','1',1,'杨瑾',NULL,'4-4'),(1926887127297826982,0,1925444731540381698,'五年级','五（3）班','探究','1',1,'沈萍平',NULL,'1-2'),(1926887127297826983,0,1925444731540381698,'五年级','五（3）班','数学','5',5,'郭纪琼',NULL,'1-3、2-3、3-2、4-3、5-4'),(1926887127297826984,0,1925444731540381698,'五年级','五（3）班','校班会','1',1,'田莉莉',NULL,'3-3'),(1926887127297826985,0,1925444731540381698,'五年级','五（3）班','活动1','1',1,'',NULL,'3-7'),(1926887127297826986,0,1925444731540381698,'五年级','五（3）班','活动2','1',1,'曹晗+俞洁霞',NULL,'5-6'),(1926887127297826987,0,1925444731540381698,'五年级','五（3）班','美术','1',1,'万启星',NULL,'1-4'),(1926887127297826988,0,1925444731540381698,'五年级','五（3）班','自然','2',2,'吴峥嵘',NULL,'2-6、5-5'),(1926887127297826989,0,1925444731540381698,'五年级','五（3）班','英语','5',5,'许辰婕',NULL,'1-6、2-7、3-1、4-1、5-1'),(1926887127297826990,0,1925444731540381698,'五年级','五（3）班','语文','5',5,'田莉莉',NULL,'1-7、2-5、3-6、4-5、5-3'),(1926887127297826991,0,1925444731540381698,'五年级','五（3）班','道法','3',3,'沈琳',NULL,'1-5、2-1、4-7'),(1926887127360741377,0,1925444731540381698,'五年级','五（3）班','音乐','2',2,'倪佳',NULL,'2-2、4-6'),(1926887127360741378,0,1925444731540381698,'五年级','五（4）班','体育','5',5,'钱佳明',NULL,'1-3、2-1、3-5、4-6、5-7'),(1926887127360741379,0,1925444731540381698,'五年级','五（4）班','写字','1',1,'吴晓华',NULL,'2-5'),(1926887127360741380,0,1925444731540381698,'五年级','五（4）班','劳技','1',1,'杨瑾',NULL,'4-3'),(1926887127360741381,0,1925444731540381698,'五年级','五（4）班','探究','1',1,'陆异毅',NULL,'3-7'),(1926887127360741382,0,1925444731540381698,'五年级','五（4）班','数学','5',5,'王莹',NULL,'1-4、2-2、3-6、4-2、5-1'),(1926887127360741383,0,1925444731540381698,'五年级','五（4）班','校班会','1',1,'鲍怡静',NULL,'4-4'),(1926887127360741384,0,1925444731540381698,'五年级','五（4）班','活动1','1',1,'',NULL,'2-4'),(1926887127360741385,0,1925444731540381698,'五年级','五（4）班','活动2','1',1,'曹晗+俞洁霞',NULL,'1-7'),(1926887127360741386,0,1925444731540381698,'五年级','五（4）班','美术','1',1,'万启星',NULL,'5-5'),(1926887127360741387,0,1925444731540381698,'五年级','五（4）班','自然','2',2,'吴峥嵘',NULL,'3-2、5-4'),(1926887127360741388,0,1925444731540381698,'五年级','五（4）班','英语','5',5,'鲍怡静',NULL,'1-2、2-7、3-1、4-7、5-3'),(1926887127360741389,0,1925444731540381698,'五年级','五（4）班','语文','5',5,'吴晓华',NULL,'1-6、2-3、3-4、4-1、5-6'),(1926887127360741390,0,1925444731540381698,'五年级','五（4）班','道法','3',3,'吴晓华',NULL,'1-5、2-6、5-2'),(1926887127360741391,0,1925444731540381698,'五年级','五（4）班','音乐','2',2,'倪佳',NULL,'1-1、4-5'),(1926887127360741392,0,1925444731540381698,'五年级','五（5）班','体育','5',5,'钱佳明',NULL,'1-7、2-2、3-6、4-4、5-1'),(1926887127360741393,0,1925444731540381698,'五年级','五（5）班','写字','1',1,'徐燕',NULL,'5-7'),(1926887127360741394,0,1925444731540381698,'五年级','五（5）班','劳技','1',1,'杨瑾',NULL,'3-5'),(1926887127360741395,0,1925444731540381698,'五年级','五（5）班','探究','1',1,'沈萍平',NULL,'1-3'),(1926887127360741396,0,1925444731540381698,'五年级','五（5）班','数学','5',5,'沈萍平',NULL,'1-5、2-6、3-2、4-6、5-5'),(1926887127360741397,0,1925444731540381698,'五年级','五（5）班','校班会','1',1,'沈萍平',NULL,'5-3'),(1926887127360741398,0,1925444731540381698,'五年级','五（5）班','活动1','1',1,'',NULL,'4-2'),(1926887127360741399,0,1925444731540381698,'五年级','五（5）班','活动2','1',1,'曹晗+俞洁霞',NULL,'3-4'),(1926887127360741400,0,1925444731540381698,'五年级','五（5）班','美术','1',1,'万启星',NULL,'4-1'),(1926887127360741401,0,1925444731540381698,'五年级','五（5）班','自然','2',2,'吴峥嵘',NULL,'1-2、4-7'),(1926887127360741402,0,1925444731540381698,'五年级','五（5）班','英语','5',5,'鲍怡静',NULL,'1-4、2-1、3-3、4-5、5-6'),(1926887127360741403,0,1925444731540381698,'五年级','五（5）班','语文','5',5,'徐燕',NULL,'1-6、2-3、3-7、4-3、5-2'),(1926887127360741404,0,1925444731540381698,'五年级','五（5）班','道法','3',3,'徐燕',NULL,'1-1、2-7、5-4'),(1926887127360741405,0,1925444731540381698,'五年级','五（5）班','音乐','2',2,'苏雯雯',NULL,'2-4、3-1'),(1926887127360741406,0,1925444731540381698,'五年级','五（6）班','体育','5',5,'钱佳明',NULL,'1-5、2-6、3-2、4-3、5-6'),(1926887127360741407,0,1925444731540381698,'五年级','五（6）班','写字','1',1,'吴伟芳',NULL,'2-3'),(1926887127360741408,0,1925444731540381698,'五年级','五（6）班','劳技','1',1,'杨瑾',NULL,'3-7'),(1926887127360741409,0,1925444731540381698,'五年级','五（6）班','探究','1',1,'陆异毅',NULL,'5-3'),(1926887127360741410,0,1925444731540381698,'五年级','五（6）班','数学','5',5,'王莹',NULL,'1-3、2-1、3-3、4-5、5-4'),(1926887127360741411,0,1925444731540381698,'五年级','五（6）班','校班会','1',1,'王莹',NULL,'1-6'),(1926887127360741412,0,1925444731540381698,'五年级','五（6）班','活动1','1',1,'',NULL,'3-6'),(1926887127360741413,0,1925444731540381698,'五年级','五（6）班','活动2','1',1,'曹晗+俞洁霞',NULL,'1-4'),(1926887127360741414,0,1925444731540381698,'五年级','五（6）班','美术','1',1,'万启星',NULL,'4-2'),(1926887127360741415,0,1925444731540381698,'五年级','五（6）班','自然','2',2,'吴峥嵘',NULL,'2-5、4-6'),(1926887127360741416,0,1925444731540381698,'五年级','五（6）班','英语','5',5,'孟淑芬',NULL,'1-2、2-7、3-4、4-1、5-2'),(1926887127360741417,0,1925444731540381698,'五年级','五（6）班','语文','5',5,'吴伟芳',NULL,'1-7、2-4、3-1、4-7、5-7'),(1926887127360741418,0,1925444731540381698,'五年级','五（6）班','道法','3',3,'吴伟芳',NULL,'1-1、3-5、4-4'),(1926887127360741419,0,1925444731540381698,'五年级','五（6）班','音乐','2',2,'苏雯雯',NULL,'2-2、5-5');
/*!40000 ALTER TABLE `tpk_lesson_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tpk_lesson_schedule`
--

DROP TABLE IF EXISTS `tpk_lesson_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tpk_lesson_schedule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `school_id` bigint(20) NOT NULL COMMENT '学校ID',
  `teach_calender_id` bigint(20) NOT NULL COMMENT '学年学期',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `is_conflict` varchar(1) DEFAULT 'N' COMMENT '状态',
  `is_auto_schedule` varchar(1) DEFAULT 'N' COMMENT '是否自动排课',
  `is_current` varchar(1) NOT NULL COMMENT '是否当前排课',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1925444731540381699 DEFAULT CHARSET=utf8 COMMENT='排课任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tpk_lesson_schedule`
--

LOCK TABLES `tpk_lesson_schedule` WRITE;
/*!40000 ALTER TABLE `tpk_lesson_schedule` DISABLE KEYS */;
INSERT INTO `tpk_lesson_schedule` VALUES (1925444731540381698,0,1,'2024-2','N','Y','Y','2025-05-22 06:52:52','2025-05-22 06:52:52');
/*!40000 ALTER TABLE `tpk_lesson_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tpk_lesson_schedule_record`
--

DROP TABLE IF EXISTS `tpk_lesson_schedule_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tpk_lesson_schedule_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `lesson_schedule_id` bigint(20) NOT NULL COMMENT '排课任务ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `json` text NOT NULL COMMENT '数据',
  `create_time` timestamp NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='排课存档';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tpk_lesson_schedule_record`
--

LOCK TABLES `tpk_lesson_schedule_record` WRITE;
/*!40000 ALTER TABLE `tpk_lesson_schedule_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `tpk_lesson_schedule_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tpk_lesson_schedule_rule`
--

DROP TABLE IF EXISTS `tpk_lesson_schedule_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tpk_lesson_schedule_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `school_id` bigint(20) NOT NULL COMMENT '学校ID',
  `lesson_schedule_id` bigint(20) NOT NULL COMMENT '排课任务ID',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `code` varchar(255) NOT NULL COMMENT '代码',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `json` text NOT NULL COMMENT '数据',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='排课规则';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tpk_lesson_schedule_rule`
--

LOCK TABLES `tpk_lesson_schedule_rule` WRITE;
/*!40000 ALTER TABLE `tpk_lesson_schedule_rule` DISABLE KEYS */;
/*!40000 ALTER TABLE `tpk_lesson_schedule_rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tpk_lesson_schedule_time`
--

DROP TABLE IF EXISTS `tpk_lesson_schedule_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tpk_lesson_schedule_time` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `school_id` bigint(20) NOT NULL COMMENT '学校ID',
  `lesson_schedule_id` bigint(20) NOT NULL COMMENT '排课任务ID',
  `weekdays` int(11) NOT NULL COMMENT '每周天数',
  `morning` int(11) NOT NULL COMMENT '上午节数',
  `afternoon` int(11) NOT NULL COMMENT '下午节数',
  `night` int(11) NOT NULL COMMENT '晚上节数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `lesson_schedule_id_UNIQUE` (`lesson_schedule_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1925444755120758786 DEFAULT CHARSET=utf8 COMMENT='作息安排';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tpk_lesson_schedule_time`
--

LOCK TABLES `tpk_lesson_schedule_time` WRITE;
/*!40000 ALTER TABLE `tpk_lesson_schedule_time` DISABLE KEYS */;
INSERT INTO `tpk_lesson_schedule_time` VALUES (1925444755120758785,0,1925444731540381698,5,4,3,0);
/*!40000 ALTER TABLE `tpk_lesson_schedule_time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_course`
--

DROP TABLE IF EXISTS `ts_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ts_course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `school_id` bigint(20) NOT NULL COMMENT '学校ID',
  `sort` int(11) NOT NULL COMMENT '排序',
  `code` varchar(64) NOT NULL COMMENT '代码',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `criterion` float DEFAULT '1' COMMENT '系数',
  `status` varchar(1) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='课程';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_course`
--

LOCK TABLES `ts_course` WRITE;
/*!40000 ALTER TABLE `ts_course` DISABLE KEYS */;
INSERT INTO `ts_course` VALUES (1,0,1,'01','语文',1,'0'),(2,0,2,'02','数学',1,'0');
/*!40000 ALTER TABLE `ts_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_grade`
--

DROP TABLE IF EXISTS `ts_grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ts_grade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `school_id` bigint(20) NOT NULL COMMENT '学校ID',
  `code` varchar(16) NOT NULL COMMENT '编号',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `teacher_id` bigint(20) DEFAULT NULL COMMENT '年级组长',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1925458838624751618 DEFAULT CHARSET=utf8 COMMENT='年级';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_grade`
--

LOCK TABLES `ts_grade` WRITE;
/*!40000 ALTER TABLE `ts_grade` DISABLE KEYS */;
INSERT INTO `ts_grade` VALUES (1925458784878940162,0,'01','一年级',NULL,0),(1925458813312126977,0,'02','二年级',NULL,0),(1925458838624751617,0,'03','三年级',NULL,0);
/*!40000 ALTER TABLE `ts_grade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_lesson_time`
--

DROP TABLE IF EXISTS `ts_lesson_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ts_lesson_time` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `school_id` bigint(20) NOT NULL COMMENT '学校ID',
  `sort` int(11) NOT NULL COMMENT '排序',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `start_time` varchar(10) DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(10) DEFAULT NULL COMMENT '结束时间',
  `category` varchar(64) NOT NULL COMMENT '上午/下午',
  `lesson_type` varchar(255) DEFAULT '课程' COMMENT '节次类型',
  `time_index` int(11) DEFAULT NULL COMMENT '节次顺序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1925461024536612874 DEFAULT CHARSET=utf8 COMMENT='上课节次';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_lesson_time`
--

LOCK TABLES `ts_lesson_time` WRITE;
/*!40000 ALTER TABLE `ts_lesson_time` DISABLE KEYS */;
INSERT INTO `ts_lesson_time` VALUES (1925461024536612866,0,1,'第一节',NULL,NULL,'上午','课程',1),(1925461024536612867,0,2,'第二节',NULL,NULL,'上午','课程',2),(1925461024536612868,0,3,'第三节',NULL,NULL,'上午','课程',3),(1925461024536612869,0,4,'第四节',NULL,NULL,'上午','课程',4),(1925461024536612870,0,5,'第五节',NULL,NULL,'下午','课程',5),(1925461024536612871,0,6,'第六节',NULL,NULL,'下午','课程',6),(1925461024536612872,0,7,'第七节',NULL,NULL,'下午','课程',7),(1925461024536612873,0,8,'第八节',NULL,NULL,'下午','课程',8);
/*!40000 ALTER TABLE `ts_lesson_time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_teach_calendar`
--

DROP TABLE IF EXISTS `ts_teach_calendar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ts_teach_calendar` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `school_id` bigint(20) NOT NULL COMMENT '学校ID',
  `year` varchar(64) NOT NULL COMMENT '学年',
  `term` varchar(64) NOT NULL COMMENT '学期',
  `start_date` date NOT NULL COMMENT '开始日期',
  `end_date` date NOT NULL COMMENT '开始日期',
  `current` varchar(10) NOT NULL COMMENT '是否当前学期',
  `week_num` int(11) NOT NULL COMMENT '周数',
  `del_flag` int(11) DEFAULT '0' COMMENT '删除标志 0:正常 1:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='学年学期';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_teach_calendar`
--

LOCK TABLES `ts_teach_calendar` WRITE;
/*!40000 ALTER TABLE `ts_teach_calendar` DISABLE KEYS */;
INSERT INTO `ts_teach_calendar` VALUES (1,0,'2024','2','2025-02-01','2025-06-30','Y',22,0);
/*!40000 ALTER TABLE `ts_teach_calendar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_teacher`
--

DROP TABLE IF EXISTS `ts_teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ts_teacher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `school_id` bigint(20) NOT NULL COMMENT '学校ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `code` varchar(20) NOT NULL COMMENT '工号',
  `name` varchar(64) NOT NULL COMMENT '姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `card` varchar(30) DEFAULT NULL COMMENT '卡号',
  `status` varchar(1) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ts_teacher_code` (`school_id`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教师';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_teacher`
--

LOCK TABLES `ts_teacher` WRITE;
/*!40000 ALTER TABLE `ts_teacher` DISABLE KEYS */;
/*!40000 ALTER TABLE `ts_teacher` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-11 15:47:50
