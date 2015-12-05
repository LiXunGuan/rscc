-- ----------------------------
-- 创建crm库
-- ----------------------------

CREATE DATABASE IF NOT EXISTS crm DEFAULT CHARACTER SET utf8 ;
USE crm;

-- ----------------------------
-- 添加所有要用到的表结构
-- ----------------------------

-- ----------------------------
-- Table structure for user_user
-- ----------------------------

CREATE TABLE IF NOT EXISTS `user_user` (
  `uuid` varchar(64) NOT NULL,
  `loginname` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `user_describe` varchar(64) NOT NULL,
  `department` varchar(64) NOT NULL,
  `delete_flag` varchar(1) NOT NULL DEFAULT '0',
  `date` datetime NOT NULL,
  `phone` varchar(64) DEFAULT NULL,
  `agent_job_number` varchar(64) DEFAULT NULL,
  `caller_id_name` varchar(64) DEFAULT NULL,
  `caller_id_number` varchar(64) DEFAULT NULL,
  `admin_flag` varchar(1) DEFAULT '0',
  `mail` varchar(64) DEFAULT NULL,
  `checkurl` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_datarange
-- ----------------------------
CREATE TABLE IF NOT EXISTS `user_datarange` (
  `uuid` varchar(64) NOT NULL,
  `datarange_name` varchar(64) NOT NULL,
  `datarange_describe` varchar(500) NOT NULL,
  `parent_uuid` varchar(64) NOT NULL,
  `date` datetime NOT NULL,
  `type_uuid` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_action
-- ----------------------------
CREATE TABLE IF NOT EXISTS `user_action` (
  `uuid` varchar(64) NOT NULL,
  `action_name` varchar(64) NOT NULL,
  `action_type` int(11) NOT NULL,
  `action_url` varchar(64) DEFAULT NULL,
  `action_describe` varchar(500) DEFAULT NULL,
  `action_css` varchar(1000) DEFAULT NULL,
  `action_json` varchar(1000) DEFAULT NULL,
  `parent_uuid` varchar(64) DEFAULT NULL,
  `action_seq` int(11) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_permission
-- ----------------------------
CREATE TABLE IF NOT EXISTS `user_permission` (
  `uuid` varchar(64) NOT NULL,
  `permission_name` varchar(64) NOT NULL,
  `permission` varchar(64) NOT NULL,
  `permission_describe` varchar(500) NOT NULL,
  `parent_uuid` varchar(64) NOT NULL,
  `date` datetime NOT NULL,
  `sequence` int(11) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_permission_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS `user_permission_role` (
  `uuid` varchar(64) NOT NULL,
  `role_name` varchar(64) NOT NULL,
  `permissionrole_describe` varchar(500) NOT NULL,
  `date` datetime NOT NULL,
  `parent_uuid` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_role_datarange_link
-- ----------------------------
CREATE TABLE iF NOT EXISTS `user_role_datarange_link` (
  `role_uuid` varchar(64) NOT NULL,
  `datarange_type` varchar(64) NOT NULL,
  `datarange_uuid` varchar(64) NOT NULL,
  PRIMARY KEY (`role_uuid`,`datarange_type`,`datarange_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_permissionrole_permission_link
-- ----------------------------
CREATE TABLE IF NOT EXISTS `user_permissionrole_permission_link` (
  `permissionrole_uuid` varchar(64) NOT NULL,
  `permission_uuid` varchar(64) NOT NULL,
  PRIMARY KEY (`permissionrole_uuid`,`permission_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for design_table
-- ----------------------------
CREATE TABLE IF NOT EXISTS `design_table` (
  `uuid` varchar(64) NOT NULL,
  `table_name` varchar(64) NOT NULL,
  `table_json` varchar(256) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for design_column
-- ----------------------------
CREATE TABLE IF NOT EXISTS `design_column` (
  `uuid` varchar(64) NOT NULL,
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `orders` int(11) NOT NULL,
  `column_name` varchar(64) NOT NULL,
  `column_name_db` varchar(64) NOT NULL,
  `tableName` varchar(64) NOT NULL,
  `column_type` varchar(64) NOT NULL,
  `is_default` varchar(10) NOT NULL,
  `character_property` varchar(64) NOT NULL,
  `allow_select` varchar(10) NOT NULL,
  `allow_index` varchar(10) NOT NULL,
  `allow_show` varchar(10) NOT NULL,
  `allow_empty` varchar(10) NOT NULL,
  `column_value` varchar(64) DEFAULT NULL,
  `is_hidden` varchar(10) DEFAULT NULL,
  `is_readonly` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cstm_customer_pool
-- ----------------------------
CREATE TABLE IF NOT EXISTS `cstm_customer_pool` (
  `uuid` varchar(64) NOT NULL,
  `pool_name` varchar(64) NOT NULL,
  `pool_des` text,
  `create_time` datetime NOT NULL,
  `be_default` varchar(255) DEFAULT NULL,
  `creater` varchar(64) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_config` (
  `uuid` varchar(64) NOT NULL,
  `syskey` varchar(32) NOT NULL,
  `sysval` varchar(500) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for knowledge_directory
-- ----------------------------
CREATE TABLE IF NOT EXISTS `knowledge_directory` (
  `uuid` varchar(64) NOT NULL,
  `directory_name` varchar(64) NOT NULL,
  `directory_parent_uuid` varchar(64) DEFAULT NULL,
  `directory_remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for cstm_pool_department_link
-- ----------------------------
DROP TABLE IF EXISTS `cstm_pool_department_link`;
CREATE TABLE `cstm_pool_department_link` (
  `cstm_pool_uuid` varchar(64) NOT NULL,
  `department_uuid` varchar(64) NOT NULL,
  PRIMARY KEY (`cstm_pool_uuid`,`department_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户和角色关联表
-- ----------------------------
-- Table structure for user_user_permissionrole_link
-- ----------------------------
DROP TABLE IF EXISTS `user_user_permissionrole_link`;
CREATE TABLE `user_user_permissionrole_link` (
  `user_uuid` varchar(64) NOT NULL,
  `permissionrole_uuid` varchar(64) NOT NULL,
  PRIMARY KEY  (`user_uuid`,`permissionrole_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户和部门、队列 进行关联
DROP TABLE IF EXISTS `user_user_datarange_link`;
CREATE TABLE `user_user_datarange_link` (
  `user_uuid` varchar(64) NOT NULL,
  `datarange_type` varchar(64) NOT NULL,
  `datarange_uuid` varchar(64) NOT NULL,
  PRIMARY KEY  (`user_uuid`,`datarange_type`,`datarange_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- 添加Admin 和默认坐席
-- ----------------------------
INSERT INTO `user_user` VALUES ('0', 'admin', 'admin', '超级管理员', '01', '0', NOW(), '00000000', '000000', NULL, NULL, '1', '', NULL);
INSERT INTO `user_user` VALUES ('0f79d5bf774a4783b748e649b14ee9b8', '1101', '1101', '销售1组_坐席1101', '7cfe6566eeea4619ab8d8d89fe0fcb31', '0', NOW(), '', '1101', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('123ec82025cd4d7cbe569acf07e66ab0', '2204', '2204', '投诉组_坐席2204', '7e6e2ce4027042a99513dc5d9948d9a9', '0', NOW(), '', '2204', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('192f58a32adf410ca793d447eb30a2ed', '1201', '1201', '销售2组_坐席1201', '71801b50f29c475688c4912e5304afef', '0', NOW(), '', '1201', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('2f336911d9b04d3790282b7cae0c6999', '2202', '2202', '投诉组_坐席2202', '7e6e2ce4027042a99513dc5d9948d9a9', '0', NOW(), '', '2202', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('38ead9024cb045389f5c25703d1a020d', '1102', '1102', '销售1组_坐席1102', '7cfe6566eeea4619ab8d8d89fe0fcb31', '0', NOW(), '', '1102', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('3cad52d182164149a55eebbc43d4fb2a', '2105', '2105', '技术支持组_坐席2105', 'efef07f7f4aa447a922ba0206aa78aa4', '0', NOW(), '', '2105', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('3fb04c00fbb74f808e0add1018aec39b', '1104', '1104', '销售1组_坐席1104', '7cfe6566eeea4619ab8d8d89fe0fcb31', '0', NOW(), '', '1104', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('401560a4ff764ebf95964f33982f91be', '1203', '1203', '销售2组_坐席1203', '71801b50f29c475688c4912e5304afef', '0', NOW(), '', '1203', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('425b55e169ee49528bf5a7c9f1588dae', '1205', '1205', '销售2组_坐席1205', '71801b50f29c475688c4912e5304afef', '0', NOW(), '', '1205', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('42c125addb8f459492c836b96ef3dc04', '1000', '1000', '销售部经理【马五】', 'd20c58af9cd84fc582908e43fef4d90e', '0', NOW(), '', '', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('6433a20fda19467ca5b1c70ddb2ff994', '1204', '1204', '销售2组_坐席1204', '71801b50f29c475688c4912e5304afef', '0', NOW(), '', '1204', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('68744f19644e48da81977e5bbd86f9a4', '1105', '1105', '销售1组_坐席1105', '7cfe6566eeea4619ab8d8d89fe0fcb31', '0', NOW(), '', '1105', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('6ab08a0ce27640b7ad3729b534811aac', '2203', '2203', '投诉组_坐席2203', '7e6e2ce4027042a99513dc5d9948d9a9', '0', NOW(), '', '2203', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('6c329ebdca7340fd8bff8a02604cf90b', '2100', '2100', '客服_技术支持组长【王二】', 'efef07f7f4aa447a922ba0206aa78aa4', '0', NOW(), '', '', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('720547b0d0fc411483259ccc8f9f98de', '1100', '1100', '销售1组_组长【张三】', '7cfe6566eeea4619ab8d8d89fe0fcb31', '0', NOW(), '', '', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('8003b91b3d2243d8838101c87dd1b98d', '2101', '2101', '技术支持组_坐席2101', 'efef07f7f4aa447a922ba0206aa78aa4', '0', NOW(), '', '2101', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('8c8fd993a6d745ef8ce2c46d683aa415', '2000', '2000', '客服部经理【赵东】', '144855309df14df48572881edc4413bd', '0', NOW(), '', '', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('a6afc8a5f098438d830454627c4ce6e2', '2102', '2102', '技术支持组_坐席2102', 'efef07f7f4aa447a922ba0206aa78aa4', '0', NOW(), '', '2102', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('ad15e0314bfb4443b8f0a8aa6d9e69d8', '2205', '2205', '投诉组_坐席2205', '7e6e2ce4027042a99513dc5d9948d9a9', '0', NOW(), '', '2205', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('c0537b830e8b4b6c96520236bd0d6231', '1200', '1200', '销售2组_组长【李四】', '71801b50f29c475688c4912e5304afef', '0', NOW(), '', '', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('ce245863168747e0b6a36639cd046b59', '2103', '2103', '技术支持组_坐席2103', 'efef07f7f4aa447a922ba0206aa78aa4', '0', NOW(), '', '2103', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('d16934119aac46e29833cd286bffb70b', '2201', '2201', '投诉组_坐席2201', '7e6e2ce4027042a99513dc5d9948d9a9', '0', NOW(), '', '2201', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('e7a7f339212849d6be4703ccebb24e73', '1103', '1103', '销售1组_坐席1103', '7cfe6566eeea4619ab8d8d89fe0fcb31', '0', NOW(), '', '1103', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('f836ebde010442b4b7bcfa38d8a6708a', '1202', '1202', '销售2组_坐席1202', '71801b50f29c475688c4912e5304afef', '0',NOW(), '', '1202', NULL, NULL, NULL, '', NULL);
INSERT INTO `user_user` VALUES ('fc25a0d2497d48e387ee3dbb5875dead', '2200', '2200', '客服_投诉组组长【赵一】', '7e6e2ce4027042a99513dc5d9948d9a9', '0', NOW(), '', '', NULL, NULL, NULL, '', NULL);


-- ----------------------------
-- 添加默认部门
-- ----------------------------

INSERT INTO `user_datarange` VALUES ('01', '默认部门', '默认的内置部门，可以修改名称，不可删除', '', '2015-06-29 16:11:50', NULL);
INSERT INTO `user_datarange` VALUES ('144855309df14df48572881edc4413bd', '客服部', '客户部描述', '01', NOW(), NULL);
INSERT INTO `user_datarange` VALUES ('4c767f028fd649c1a1bac671ff51114c', '技术部', '技术部描述', '01', NOW(), NULL);
INSERT INTO `user_datarange` VALUES ('71801b50f29c475688c4912e5304afef', '销售2组', '销售2组描述', 'd20c58af9cd84fc582908e43fef4d90e', NOW(), NULL);
INSERT INTO `user_datarange` VALUES ('7cfe6566eeea4619ab8d8d89fe0fcb31', '销售1组', '销售1组描述', 'd20c58af9cd84fc582908e43fef4d90e', NOW(), NULL);
INSERT INTO `user_datarange` VALUES ('7e6e2ce4027042a99513dc5d9948d9a9', '客服_投诉组', '投诉组描述', '144855309df14df48572881edc4413bd', NOW(), NULL);
INSERT INTO `user_datarange` VALUES ('d20c58af9cd84fc582908e43fef4d90e', '销售部', '销售部描述', '01', NOW(), NULL);
INSERT INTO `user_datarange` VALUES ('efef07f7f4aa447a922ba0206aa78aa4', '客服_技术支持组', '技术支持组描述', '144855309df14df48572881edc4413bd', NOW(), NULL);


-- ----------------------------
-- 将默认管理员 班长 坐席的用户和对应角色绑定
-- ----------------------------
INSERT INTO `user_user_permissionrole_link` (`user_uuid`, `permissionrole_uuid`) VALUES ('0', '000001');
INSERT INTO `user_user_permissionrole_link` VALUES ('04ce79f7fe0941cc87e451ae8cf113fc', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('0f79d5bf774a4783b748e649b14ee9b8', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('123ec82025cd4d7cbe569acf07e66ab0', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('192f58a32adf410ca793d447eb30a2ed', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('2f336911d9b04d3790282b7cae0c6999', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('38ead9024cb045389f5c25703d1a020d', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('3cad52d182164149a55eebbc43d4fb2a', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('3fb04c00fbb74f808e0add1018aec39b', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('401560a4ff764ebf95964f33982f91be', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('425b55e169ee49528bf5a7c9f1588dae', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('42c125addb8f459492c836b96ef3dc04', '000002');
INSERT INTO `user_user_permissionrole_link` VALUES ('42c125addb8f459492c836b96ef3dc04', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('6433a20fda19467ca5b1c70ddb2ff994', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('68744f19644e48da81977e5bbd86f9a4', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('6ab08a0ce27640b7ad3729b534811aac', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('6c329ebdca7340fd8bff8a02604cf90b', '000002');
INSERT INTO `user_user_permissionrole_link` VALUES ('6c329ebdca7340fd8bff8a02604cf90b', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('720547b0d0fc411483259ccc8f9f98de', '000002');
INSERT INTO `user_user_permissionrole_link` VALUES ('720547b0d0fc411483259ccc8f9f98de', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('8003b91b3d2243d8838101c87dd1b98d', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('8c8fd993a6d745ef8ce2c46d683aa415', '000002');
INSERT INTO `user_user_permissionrole_link` VALUES ('8c8fd993a6d745ef8ce2c46d683aa415', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('a6afc8a5f098438d830454627c4ce6e2', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('ad15e0314bfb4443b8f0a8aa6d9e69d8', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('c0537b830e8b4b6c96520236bd0d6231', '000002');
INSERT INTO `user_user_permissionrole_link` VALUES ('c0537b830e8b4b6c96520236bd0d6231', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('ce245863168747e0b6a36639cd046b59', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('d16934119aac46e29833cd286bffb70b', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('e7a7f339212849d6be4703ccebb24e73', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('f836ebde010442b4b7bcfa38d8a6708a', '000003');
INSERT INTO `user_user_permissionrole_link` VALUES ('fc25a0d2497d48e387ee3dbb5875dead', '000002');
INSERT INTO `user_user_permissionrole_link` VALUES ('fc25a0d2497d48e387ee3dbb5875dead', '000003');

-- ----------------------------
-- 给初始用户绑定队列和部门信息
-- ----------------------------

INSERT INTO `user_user_datarange_link` VALUES ('0', 'datarange', 'ada');
INSERT INTO `user_user_datarange_link` VALUES ('123ec82025cd4d7cbe569acf07e66ab0', 'datarange', '7e6e2ce4027042a99513dc5d9948d9a9');
INSERT INTO `user_user_datarange_link` VALUES ('192f58a32adf410ca793d447eb30a2ed', 'datarange', '71801b50f29c475688c4912e5304afef');
INSERT INTO `user_user_datarange_link` VALUES ('2f336911d9b04d3790282b7cae0c6999', 'datarange', '7e6e2ce4027042a99513dc5d9948d9a9');
INSERT INTO `user_user_datarange_link` VALUES ('38ead9024cb045389f5c25703d1a020d', 'datarange', '7cfe6566eeea4619ab8d8d89fe0fcb31');
INSERT INTO `user_user_datarange_link` VALUES ('3cad52d182164149a55eebbc43d4fb2a', 'datarange', 'efef07f7f4aa447a922ba0206aa78aa4');
INSERT INTO `user_user_datarange_link` VALUES ('3fb04c00fbb74f808e0add1018aec39b', 'datarange', '7cfe6566eeea4619ab8d8d89fe0fcb31');
INSERT INTO `user_user_datarange_link` VALUES ('401560a4ff764ebf95964f33982f91be', 'datarange', '71801b50f29c475688c4912e5304afef');
INSERT INTO `user_user_datarange_link` VALUES ('425b55e169ee49528bf5a7c9f1588dae', 'datarange', '71801b50f29c475688c4912e5304afef');
INSERT INTO `user_user_datarange_link` VALUES ('42c125addb8f459492c836b96ef3dc04', 'datarange', '71801b50f29c475688c4912e5304afef');
INSERT INTO `user_user_datarange_link` VALUES ('42c125addb8f459492c836b96ef3dc04', 'datarange', '7cfe6566eeea4619ab8d8d89fe0fcb31');
INSERT INTO `user_user_datarange_link` VALUES ('42c125addb8f459492c836b96ef3dc04', 'datarange', 'd20c58af9cd84fc582908e43fef4d90e');
INSERT INTO `user_user_datarange_link` VALUES ('42c125addb8f459492c836b96ef3dc04', 'queue', 'aee');
INSERT INTO `user_user_datarange_link` VALUES ('6433a20fda19467ca5b1c70ddb2ff994', 'datarange', '71801b50f29c475688c4912e5304afef');
INSERT INTO `user_user_datarange_link` VALUES ('68744f19644e48da81977e5bbd86f9a4', 'datarange', '7cfe6566eeea4619ab8d8d89fe0fcb31');
INSERT INTO `user_user_datarange_link` VALUES ('6ab08a0ce27640b7ad3729b534811aac', 'datarange', '7e6e2ce4027042a99513dc5d9948d9a9');
INSERT INTO `user_user_datarange_link` VALUES ('6c329ebdca7340fd8bff8a02604cf90b', 'datarange', 'efef07f7f4aa447a922ba0206aa78aa4');
INSERT INTO `user_user_datarange_link` VALUES ('720547b0d0fc411483259ccc8f9f98de', 'datarange', '7cfe6566eeea4619ab8d8d89fe0fcb31');
INSERT INTO `user_user_datarange_link` VALUES ('8003b91b3d2243d8838101c87dd1b98d', 'datarange', 'efef07f7f4aa447a922ba0206aa78aa4');
INSERT INTO `user_user_datarange_link` VALUES ('8c8fd993a6d745ef8ce2c46d683aa415', 'datarange', '144855309df14df48572881edc4413bd');
INSERT INTO `user_user_datarange_link` VALUES ('8c8fd993a6d745ef8ce2c46d683aa415', 'datarange', '7e6e2ce4027042a99513dc5d9948d9a9');
INSERT INTO `user_user_datarange_link` VALUES ('8c8fd993a6d745ef8ce2c46d683aa415', 'datarange', 'efef07f7f4aa447a922ba0206aa78aa4');
INSERT INTO `user_user_datarange_link` VALUES ('8c8fd993a6d745ef8ce2c46d683aa415', 'queue', 'aee');
INSERT INTO `user_user_datarange_link` VALUES ('a6afc8a5f098438d830454627c4ce6e2', 'datarange', 'efef07f7f4aa447a922ba0206aa78aa4');
INSERT INTO `user_user_datarange_link` VALUES ('c0537b830e8b4b6c96520236bd0d6231', 'datarange', '71801b50f29c475688c4912e5304afef');
INSERT INTO `user_user_datarange_link` VALUES ('ce245863168747e0b6a36639cd046b59', 'datarange', 'efef07f7f4aa447a922ba0206aa78aa4');
INSERT INTO `user_user_datarange_link` VALUES ('d16934119aac46e29833cd286bffb70b', 'datarange', '7e6e2ce4027042a99513dc5d9948d9a9');
INSERT INTO `user_user_datarange_link` VALUES ('e7a7f339212849d6be4703ccebb24e73', 'datarange', '7cfe6566eeea4619ab8d8d89fe0fcb31');
INSERT INTO `user_user_datarange_link` VALUES ('f836ebde010442b4b7bcfa38d8a6708a', 'datarange', '71801b50f29c475688c4912e5304afef');
INSERT INTO `user_user_datarange_link` VALUES ('fc25a0d2497d48e387ee3dbb5875dead', 'datarange', '7e6e2ce4027042a99513dc5d9948d9a9');


-- ----------------------------
-- 添加一个默认客户池 给默认池关联部门
-- ----------------------------

INSERT INTO `cstm_customer_pool` VALUES ('de001', '成交客户A', '成交客户A', NOW(), '1', '0');

INSERT INTO `crm`.`cstm_pool_department_link` (`cstm_pool_uuid`, `department_uuid`) VALUES ('de001', '01');
INSERT INTO `crm`.`cstm_pool_department_link` (`cstm_pool_uuid`, `department_uuid`) VALUES ('de001', '144855309df14df48572881edc4413bd');
INSERT INTO `crm`.`cstm_pool_department_link` (`cstm_pool_uuid`, `department_uuid`) VALUES ('de001', '4c767f028fd649c1a1bac671ff51114c');
INSERT INTO `crm`.`cstm_pool_department_link` (`cstm_pool_uuid`, `department_uuid`) VALUES ('de001', '71801b50f29c475688c4912e5304afef');
INSERT INTO `crm`.`cstm_pool_department_link` (`cstm_pool_uuid`, `department_uuid`) VALUES ('de001', '7cfe6566eeea4619ab8d8d89fe0fcb31');
INSERT INTO `crm`.`cstm_pool_department_link` (`cstm_pool_uuid`, `department_uuid`) VALUES ('de001', '7e6e2ce4027042a99513dc5d9948d9a9');
INSERT INTO `crm`.`cstm_pool_department_link` (`cstm_pool_uuid`, `department_uuid`) VALUES ('de001', 'd20c58af9cd84fc582908e43fef4d90e');
INSERT INTO `crm`.`cstm_pool_department_link` (`cstm_pool_uuid`, `department_uuid`) VALUES ('de001', 'efef07f7f4aa447a922ba0206aa78aa4');
-- ----------------------------
-- 初始化权限动作
-- ----------------------------


INSERT INTO `user_action` VALUES ('1', '我的预约', '0', 'schedulereminder?level=agent', 'schedulereminder', 'fa fa-lg fa-fw fa-calendar', '0', '0', '1');
INSERT INTO `user_action` VALUES ('10', '我的知识库', '0', 'knowledge/knowledge?level=agent', 'knowledgeknowledge', 'fa fa-lg fa-fw fa-book', '0', '0', '10');
INSERT INTO `user_action` VALUES ('11', '我的数据历史', '0', 'userpooldata', 'userpooldata', 'fa fa-lg fa-fw fa-clock-o', '0', '0', '2');
INSERT INTO `user_action` VALUES ('26', '客户管理', '0', null, null, 'fa fa-lg fa-fw fa-user', '0', '0', '26');
INSERT INTO `user_action` VALUES ('27', '客户管理', '0', 'cstm/toAdminCstmIndex', 'cstmtoAdminCstmIndex', 'fa fa-lg fa-fw fa-user', '0', '26', '27');
INSERT INTO `user_action` VALUES ('28', '客户池管理', '0', 'cstmpool', 'cstmpool', 'fa fa-lg fa-fw fa-user', '0', '26', '28');
INSERT INTO `user_action` VALUES ('29', '工单管理', '0', 'cstmservice', 'cstmservice', 'fa fa-lg fa-fw fa-comments-o', '0', '0', '29');
INSERT INTO `user_action` VALUES ('3', '我的客户', '0', 'cstm?level=agent', 'cstm', 'fa fa-lg fa-fw fa-user', '0', '0', '3');
INSERT INTO `user_action` VALUES ('30', '订单管理', '0', null, null, 'fa fa-lg fa-fw fa-shopping-cart', '0', '0', '30');
INSERT INTO `user_action` VALUES ('31', '产品管理', '0', 'product?level=monitor', 'product', 'fa fa-lg fa-fw fa-shopping-cart', '0', '30', '31');
INSERT INTO `user_action` VALUES ('32', '订单管理', '0', 'neworderinfo', 'neworderinfo', 'fa fa-lg fa-fw fa-shopping-cart', '0', '30', '32');
INSERT INTO `user_action` VALUES ('33', '录音质检管理', '0', 'record', 'record', 'fa fa-lg fa-fw fa-microphone', '0', '0', '33');
INSERT INTO `user_action` VALUES ('34', '公告管理', '0', 'agentnotice/manager', 'agentnoticemanager', 'fa fa-lg fa-fw fa-comments', '0', '0', '34');
INSERT INTO `user_action` VALUES ('35', '知识库管理', '0', null, null, 'fa fa-lg fa-fw fa-book', '0', '0', '35');
INSERT INTO `user_action` VALUES ('36', '知识库管理', '0', 'knowledge/knowledge/manager', 'knowledgemanager', 'fa fa-lg fa-fw fa-book', '0', '35', '36');
INSERT INTO `user_action` VALUES ('37', '目录管理', '0', 'knowledge/directory', 'knowledgedirectory', 'fa fa-lg fa-fw fa-book', '0', '35', '37');
INSERT INTO `user_action` VALUES ('38', '技能组状态', '0', 'runtime/queueruntime/get', 'runtimequeueruntimeget', 'fa fa-lg fa-fw fa-star-o ', '0', '0', '38');
INSERT INTO `user_action` VALUES ('39', '系统配置', '0', NULL, NULL, 'fa fa-lg fa-fw fa-cogs', '0', '0', '41');

INSERT INTO `user_action` VALUES ('4', '我的工单', '0', 'cstmservice?level=agent', 'agentcstmservice', 'fa fa-lg fa-fw fa-comments-o', '0', '0', '4');
INSERT INTO `user_action` VALUES ('40', '用户管理', '0', 'user/user', 'useruser', 'fa fa-lg fa-fw fa-cogs', '0', '39', '1');
INSERT INTO `user_action` VALUES ('41', '角色管理', '0', 'user/permissionrole', 'userpermissionrole', 'fa fa-lg fa-fw fa-cogs', '0', '39', '2');
INSERT INTO `user_action` VALUES ('42', '部门管理', '0', 'user/datarange', 'userdatarange', 'fa fa-lg fa-fw fa-cogs', '0', '39', '3');
INSERT INTO `user_action` VALUES ('43', '自定义表管理', '0', 'design', 'design', 'fa fa-lg fa-fw fa-cogs', '0', '39', '4');
INSERT INTO `user_action` VALUES ('45', '录音质检', '1', null, null, null, '0', '0', '45');
INSERT INTO `user_action` VALUES ('46', '添加信息', '2', 'pop_event', 'addInfo', 'fa fa-lg fa-fw fa-clock-o', '0', '0', '46');
INSERT INTO `user_action` VALUES ('47', '呼叫', '2', 'usercall', 'usercall', 'fa fa-lg fa-fw fa-clock-o', '0', '0', '47');
INSERT INTO `user_action` VALUES ('48', '呼叫队列', '2', 'usercall/call_queue', 'startCall', 'fa fa-lg fa-fw fa-clock-o', '0', '0', '48');
INSERT INTO `user_action` VALUES ('49', '报表管理', '0', null, null, 'fa fa-lg fa-fw fa-table', '0', '0', '36');
INSERT INTO `user_action` VALUES ('5', '我的客服记录', '0', 'calllog?level=agent', 'calllog', 'fa fa-lg fa-fw fa-expand', '0', '0', '5');
INSERT INTO `user_action` VALUES ('52', '计费报表', '0', 'report/bill', 'reportbill', 'fa fa-lg fa-fw fa-table', '0', '49', '3');
INSERT INTO `user_action` VALUES ('55', '网络配置', '0', 'netconfig', 'netconfig', 'fa fa-lg fa-fw fa-cogs', '0', '39', '7');

INSERT INTO `user_action` VALUES ('56', 'SIP参数配置', '0', 'config/sipprofile', 'configsipprofile', 'fa fa-lg fa-fw fa-asterisk ', '0', '57', '8');
INSERT INTO `user_action` VALUES ('57', 'IPPBX配置', '0', null, null, 'fa fa-lg fa-fw fa-asterisk ', '0', '0', '39');
INSERT INTO `user_action` VALUES ('58', 'Gateway配置', '0', 'config/gateway', 'configgateway', 'fa fa-lg fa-fw fa-asterisk ', '0', '57', '1');
INSERT INTO `user_action` VALUES ('59', '接入号配置', '0', 'config/accessnumber', 'configaccessnumber', 'fa fa-lg fa-fw fa-asterisk ', '0', '57', '2');
INSERT INTO `user_action` VALUES ('6', '我的订单', '0', 'neworderinfo?level=agent', 'agentorderinfo', 'fa fa-lg fa-fw fa-shopping-cart', '0', '0', '6');
INSERT INTO `user_action` VALUES ('60', '分机配置', '0', 'config/sipuser', 'configsipuser', 'fa fa-lg fa-fw fa-asterisk ', '0', '57', '3');
INSERT INTO `user_action` VALUES ('61', '语音资源配置', '0', 'system/uploadingvoice', 'systemuploadingvoice', 'fa fa-lg fa-fw fa-asterisk ', '0', '57', '4');
INSERT INTO `user_action` VALUES ('62', 'IVR配置', '0', 'config/ivr', 'configivr', 'fa fa-lg fa-fw fa-asterisk ', '0', '57', '5');
INSERT INTO `user_action` VALUES ('63', '呼入路由', '0', 'config/accessnumberroute', 'configaccessnumberroute', 'fa fa-lg fa-fw fa-asterisk ', '0', '57', '6');
INSERT INTO `user_action` VALUES ('64', '呼出路由', '0', 'config/sysconfig', 'configsysconfig', 'fa fa-lg fa-fw fa-asterisk ', '0', '57', '7');
INSERT INTO `user_action` VALUES ('66', '技能组管理', '0', 'config/queue', 'configqueue', 'fa fa-lg fa-fw fa-asterisk ', '0', '57', '9');
INSERT INTO `user_action` VALUES ('67', '计费管理', '0', null, null, 'fa fa-lg fa-fw fa-jpy', '0', '0', '26');
INSERT INTO `user_action` VALUES ('68', '计费明细', '0', 'billing/data', 'billingdata', 'fa fa-lg fa-fw fa-jpy', '0', '67', '1');
INSERT INTO `user_action` VALUES ('69', '费率设置', '0', 'billing/rate', 'billingrate', 'fa fa-lg fa-fw fa-jpy', '0', '67', '2');
INSERT INTO `user_action` VALUES ('7', '我的录音', '0', 'record?level=agent', 'agentrecord', 'fa fa-lg fa-fw fa-microphone', '1', '0', '7');
INSERT INTO `user_action` VALUES ('70', '接入号-话务报表', '0', 'report/reportaccessnumbertraffic', 'reportreportaccessnumbertraffic', 'fa fa-lg fa-fw fa-table', '0', '49', '4');
INSERT INTO `user_action` VALUES ('71', '分机-话务报表', '0', 'report/reportextentraffic', 'reportreportextentraffic', 'fa fa-lg fa-fw fa-table', '0', '49', '5');
INSERT INTO `user_action` VALUES ('72', '坐席-话务报表', '0', 'report/reportagenttraffic', 'reportreportagenttraffic', 'fa fa-lg fa-fw fa-table', '0', '49', '6');
INSERT INTO `user_action` VALUES ('73', '坐席-KPI报表', '0', 'report/reportagentkpi', 'reportreportagentkpi', 'fa fa-lg fa-fw fa-table', '0', '49', '7');
INSERT INTO `user_action` VALUES ('74', '技能组-KPI报表', '0', 'report/reportqueuekpi', 'reportreportqueuekpi', 'fa fa-lg fa-fw fa-table', '0', '49', '8');
INSERT INTO `user_action` VALUES ('8', '我的多方通话', '0', 'confrence?level=agent', 'confrence', 'fa fa-lg fa-fw fa-group', '0', '0', '8');
INSERT INTO `user_action` VALUES ('9', '我的公告', '0', 'agentnotice?level=agent', 'agentnotice', 'fa fa-lg fa-fw fa-comments', '0', '0', '9');
INSERT INTO `user_action` VALUES ('75', '查看号码', '1', '', '', '', '0', '0', '75');
-- 新的数据模块
INSERT INTO `user_action` VALUES ('91', '数据管理', '0', NULL, NULL, 'fa fa-lg fa-fw fa-tasks', '0', '0', '21');
INSERT INTO `user_action` VALUES ('92', '批次管理', '0', 'databatch/data', 'databatch', 'fa fa-lg fa-fw fa-tasks', '0', '91', '22');
-- 新数据管理中添加群呼管理
INSERT INTO `user_action` VALUES ('121', '群呼任务', '0', 'newdata/groupCall', 'newGroupCall', 'fa fa-lg fa-fw fa-tasks', '0', '91', '27');
INSERT INTO `user_action` (`uuid`, `action_name`, `action_type`, `action_url`, `action_describe`, `action_css`, `action_json`, `parent_uuid`, `action_seq`) VALUES ('93', '部门数据管理', '0', 'deptdata/dept', 'deptdata', 'fa fa-lg fa-fw fa-tasks', '0', '91', '23');
INSERT INTO `user_action` (`uuid`, `action_name`, `action_type`, `action_url`, `action_describe`, `action_css`, `action_json`, `parent_uuid`, `action_seq`) VALUES ('94', '我的任务', '0', 'newuserdata', 'newuserdata', 'fa fa-lg fa-fw fa-tasks', '0', '0', '24');
INSERT INTO `user_action` (`uuid`, `action_name`, `action_type`, `action_url`, `action_describe`, `action_css`, `action_json`, `parent_uuid`, `action_seq`) VALUES ('95', '我的意向客户', '0', 'newuserdata/data_intend_index', 'newuserdata_data_intend_index', 'fa fa-lg fa-fw fa-tasks', '0', '0', '25');
-- 新功能页面添加 人员数据 2015/08/28 by ChengXin
INSERT INTO user_action (`uuid`, `action_name`, `action_type`, `action_url`, `action_describe`, `action_css`, `action_json`, `parent_uuid`, `action_seq`) VALUES ('96', '我的共享池', '0', 'globalsharedata/globalshare', 'globalsharedata', 'fa fa-lg fa-fw fa-tasks', '0', '0', '26');
-- 部门内数据
INSERT INTO user_action (`uuid`, `action_name`, `action_type`, `action_url`, `action_describe`, `action_css`, `action_json`, `parent_uuid`, `action_seq`) VALUES ('97', '部门内数据', '2', 'deptdata/dept/getDataCount', 'deptGetDataCount', 'fa fa-lg fa-fw fa-tasks', '0', '91', '27');
-- 二次领用数据
INSERT INTO user_action (`uuid`, `action_name`, `action_type`, `action_url`, `action_describe`, `action_css`, `action_json`, `parent_uuid`, `action_seq`) VALUES ('98', '二次领用数据', '2', 'deptdata/dept/getOwnCount', 'deptGetOwnCount', 'fa fa-lg fa-fw fa-tasks', '0', '91', '28');
-- 废号信息
INSERT INTO user_action (`uuid`, `action_name`, `action_type`, `action_url`, `action_describe`, `action_css`, `action_json`, `parent_uuid`, `action_seq`) VALUES ('99', '废号信息', '2', 'deptdata/dept/getAbandonCount', 'getAbandonCount', 'fa fa-lg fa-fw fa-tasks', '0', '91', '29');
-- 黑名单信息
INSERT INTO user_action (`uuid`, `action_name`, `action_type`, `action_url`, `action_describe`, `action_css`, `action_json`, `parent_uuid`, `action_seq`) VALUES ('100', '黑名单信息', '2', 'deptdata/dept/getBlackListCount', 'getBlackListCount', 'fa fa-lg fa-fw fa-tasks', '0', '91', '30');
-- 意向客户量
INSERT INTO user_action (`uuid`, `action_name`, `action_type`, `action_url`, `action_describe`, `action_css`, `action_json`, `parent_uuid`, `action_seq`) VALUES ('101', '意向客户量', '2', 'deptdata/dept/getIntentCount', 'getIntentCount', 'fa fa-lg fa-fw fa-tasks', '0', '91', '31');
-- 系统首页
INSERT INTO  user_action (`uuid`, `action_name`, `action_type`, `action_url`, `action_describe`, `action_css`, `action_json`, `parent_uuid`, `action_seq`) VALUES ('102', '系统首页', '0', 'homepage', 'homepage', 'fa fa-lg fa-fw fa-home', '0', '0', '0');
-- INSERT INTO `user_action` (`uuid`, `action_name`, `action_type`, `action_url`, `action_describe`, `action_css`, `action_json`, `parent_uuid`, `action_seq`) VALUES ('103', '字典管理', '0', 'dictionary', 'dictionary', 'fa fa-lg fa-fw fa-cogs', '0', '39', '49');
INSERT INTO `user_action` (`uuid`, `action_name`, `action_type`, `action_url`, `action_describe`, `action_css`, `action_json`, `parent_uuid`, `action_seq`) VALUES ('104', '坐席-数据报表', '0', 'report/databatch', 'reportdatabatch', 'fa fa-lg fa-fw fa-table', '0', '49', '6');
-- 添加呼出弹屏
INSERT INTO `user_action` (`uuid`, `action_name`, `action_type`, `action_url`, `action_describe`, `action_css`, `action_json`, `parent_uuid`, `action_seq`) VALUES ('110', '呼出弹屏', '2', 'newuserdata/newdatacall', 'newdatacall', 'fa fa-lg fa-fw fa-tasks', '0', '91', '27');
INSERT INTO `user_action` (`uuid`, `action_name`, `action_type`, `action_url`, `action_describe`, `action_css`, `action_json`, `parent_uuid`, `action_seq`) VALUES ('120', '用户任务管理', '0', 'userManage', 'userManage', 'fa fa-lg fa-fw fa-tasks', '0', '91', '27');
-- 添加 更多配置和共享池管理菜单
INSERT INTO `crm`.`user_action` (`uuid`, `action_name`, `action_type`, `action_url`, `action_describe`, `action_css`, `action_json`, `parent_uuid`, `action_seq`) VALUES ('105', '高级选项', '0', 'otherconfig', 'otherconfig', 'fa fa-lg fa-fw fa-cogs', '0', '39', '8');
INSERT INTO `crm`.`user_action` (`uuid`, `action_name`, `action_type`, `action_url`, `action_describe`, `action_css`, `action_json`, `parent_uuid`, `action_seq`) VALUES ('20', '共享池管理', '0', 'globalshare', 'globalshare', 'fa fa-lg fa-fw fa-tasks', '0', '91', '28');


-- UPDATE `crm`.`user_action` SET `uuid`='56', `action_name`='SIP参数配置', `action_type`='0', `action_url`='config/sipprofile', `action_describe`='configsipprofile', `action_css`='fa fa-lg fa-fw fa-asterisk ', `action_json`='0', `parent_uuid`='57', `action_seq`='8' WHERE (`uuid`='56');
-- UPDATE `crm`.`user_action` SET `uuid`='66', `action_name`='技能组管理', `action_type`='0', `action_url`='config/queue', `action_describe`='configqueue', `action_css`='fa fa-lg fa-fw fa-asterisk ', `action_json`='0', `parent_uuid`='57', `action_seq`='9' WHERE (`uuid`='66');
-- DELETE FROM user_action WHERE uuid='53' AND action_name LIKE '网络配置' AND parent_uuid = '0';
-- DELETE FROM user_action WHERE uuid='103' AND action_name LIKE '字典管理' AND parent_uuid = '39';
-- DELETE FROM user_action WHERE uuid='44' AND action_name LIKE '高级选项' AND parent_uuid = '39';
-- UPDATE `crm`.`user_action` SET `uuid`='105', `action_name`='高级选项', `action_type`='0', `action_url`='otherconfig', `action_describe`='otherconfig', `action_css`='fa fa-lg fa-fw fa-cogs', `action_json`='0', `parent_uuid`='39', `action_seq`='8' WHERE (`uuid`='105');
-- UPDATE `crm`.`user_action` SET `uuid`='40', `action_name`='用户管理', `action_type`='0', `action_url`='user/user', `action_describe`='useruser', `action_css`='fa fa-lg fa-fw fa-cogs', `action_json`='0', `parent_uuid`='39', `action_seq`='1' WHERE (`uuid`='40');
-- UPDATE `crm`.`user_action` SET `uuid`='41', `action_name`='角色管理', `action_type`='0', `action_url`='user/permissionrole', `action_describe`='userpermissionrole', `action_css`='fa fa-lg fa-fw fa-cogs', `action_json`='0', `parent_uuid`='39', `action_seq`='2' WHERE (`uuid`='41');
-- UPDATE `crm`.`user_action` SET `uuid`='42', `action_name`='部门管理', `action_type`='0', `action_url`='user/datarange', `action_describe`='userdatarange', `action_css`='fa fa-lg fa-fw fa-cogs', `action_json`='0', `parent_uuid`='39', `action_seq`='3' WHERE (`uuid`='42');
-- UPDATE `crm`.`user_action` SET `uuid`='43', `action_name`='自定义表管理', `action_type`='0', `action_url`='design', `action_describe`='design', `action_css`='fa fa-lg fa-fw fa-cogs', `action_json`='0', `parent_uuid`='39', `action_seq`='4' WHERE (`uuid`='43');
-- UPDATE `crm`.`user_action` SET `uuid`='55', `action_name`='网络配置', `action_type`='0', `action_url`='netconfig', `action_describe`='netconfig', `action_css`='fa fa-lg fa-fw fa-cogs', `action_json`='0', `parent_uuid`='39', `action_seq`='7' WHERE (`uuid`='55');
-- UPDATE `crm`.`user_action` SET `uuid`='39', `action_name`='系统配置', `action_type`='0', `action_url`=NULL, `action_describe`=NULL, `action_css`='fa fa-lg fa-fw fa-cogs', `action_json`='0', `parent_uuid`='0', `action_seq`='41' WHERE (`uuid`='39');
-- UPDATE `crm`.`user_action` SET `uuid`='57', `action_name`='IPPBX配置', `action_type`='0', `action_url`=NULL, `action_describe`=NULL, `action_css`='fa fa-lg fa-fw fa-asterisk ', `action_json`='0', `parent_uuid`='0', `action_seq`='39' WHERE (`uuid`='57');
-- DELETE FROM user_permission where uuid='60' AND permission_name LIKE '网络配置';
-- DELETE FROM user_permission where uuid='82' AND permission_name LIKE '字典管理';
-- DELETE FROM user_permission where uuid='54' AND permission_name LIKE '系统配置';
-- UPDATE `crm`.`user_permission` SET `uuid`='62', `permission_name`='网络配置', `permission`='55', `permission_describe`='网络配置', `parent_uuid`='49', `date`='2015-09-17 16:50:42', `sequence`='' WHERE (`uuid`='62');
-- UPDATE `crm`.`user_permission` SET `uuid`='63', `permission_name`='SIP参数配置', `permission`='56', `permission_describe`='SIP参数配置', `parent_uuid`='64', `date`='2015-09-17 16:50:42', `sequence`='8' WHERE (`uuid`='63');
-- UPDATE `crm`.`user_permission` SET `uuid`='73', `permission_name`='技能组管理', `permission`='66', `permission_describe`='技能组管理', `parent_uuid`='64', `date`='2015-09-17 16:50:43', `sequence`='9' WHERE (`uuid`='73');
-- UPDATE `crm`.`user_permission` SET `uuid`='49', `permission_name`='系统配置', `permission`='39', `permission_describe`='系统配置', `parent_uuid`='3', `date`='2015-09-17 16:50:42', `sequence`='31' WHERE (`uuid`='49');
-- UPDATE `crm`.`user_permission` SET `uuid`='64', `permission_name`='IPPBX配置', `permission`='57', `permission_describe`='IPPBX配置', `parent_uuid`='3', `date`='2015-09-17 16:50:43', `sequence`='29' WHERE (`uuid`='64');
-- DELETE FROM user_permissionrole_permission_link where permission_uuid = '60';
-- DELETE FROM user_permissionrole_permission_link where permission_uuid = '82';
-- DELETE FROM user_permissionrole_permission_link where permission_uuid = '54';
-- ----------------------------
-- 初始化所有权限
-- ----------------------------

INSERT INTO `user_permission` VALUES ('1', '坐席', '', '1_open.png', '', '2015-07-06 16:54:20', null);
INSERT INTO `user_permission` VALUES ('11', '我的预约', '1', '我的预约', '1', NOW(), '1');
INSERT INTO `user_permission` VALUES ('13', '我的客户', '3', '我的客户', '1', NOW(), '3');
INSERT INTO `user_permission` VALUES ('14', '我的工单', '4', '我的工单', '1', NOW(), '4');
INSERT INTO `user_permission` VALUES ('15', '我的客服记录', '5', '我的客服记录', '1', NOW(), '5');
INSERT INTO `user_permission` VALUES ('16', '我的订单', '6', '我的订单', '1', NOW(), '6');
INSERT INTO `user_permission` VALUES ('17', '我的录音', '7', '我的录音', '1', NOW(), '7');
INSERT INTO `user_permission` VALUES ('18', '我的多方通话', '8', '我的多方通话', '1', NOW(), '8');
INSERT INTO `user_permission` VALUES ('19', '我的公告', '9', '我的公告', '1', NOW(), '9');
INSERT INTO `user_permission` VALUES ('20', '我的知识库', '10', '我的知识库', '1', NOW(), '10');
INSERT INTO `user_permission` VALUES ('2', '班长', '', '1_open.png', '', NOW(), null);
INSERT INTO `user_permission` VALUES ('36', '客户管理', '26', '客户管理', '2', NOW(), '22');
INSERT INTO `user_permission` VALUES ('37', '客户管理', '27', '客户管理', '36', NOW(), '1');
INSERT INTO `user_permission` VALUES ('38', '客户池管理', '28', '客户池管理', '36', NOW(), '2');
INSERT INTO `user_permission` VALUES ('39', '工单管理', '29', '工单管理', '2', NOW(), '23');
INSERT INTO `user_permission` VALUES ('40', '订单管理', '30', '订单管理', '2', NOW(), '24');
INSERT INTO `user_permission` VALUES ('41', '产品管理', '31', '产品管理', '40', NOW(), null);
INSERT INTO `user_permission` VALUES ('42', '订单管理', '32', '订单管理', '40', NOW(), null);
INSERT INTO `user_permission` VALUES ('43', '录音质检管理', '33', '录音质检管理', '2', NOW(), '25');
INSERT INTO `user_permission` VALUES ('44', '公告管理', '34', '公告管理', '2', NOW(), '26');
INSERT INTO `user_permission` VALUES ('45', '知识库管理', '35', '知识库管理', '2', NOW(), '27');
INSERT INTO `user_permission` VALUES ('46', '知识库管理', '36', '知识库管理', '45', NOW(), null);
INSERT INTO `user_permission` VALUES ('47', '目录管理', '37', '目录管理', '45', NOW(), null);
INSERT INTO `user_permission` VALUES ('48', '技能组状态', '38', '技能组状态', '2', NOW(), '28');
INSERT INTO `user_permission` VALUES ('3', '管理员', '', '1_open.png', '', NOW(), NULL);
INSERT INTO `user_permission` VALUES ('49', '系统配置', '39', '系统配置', '3', NOW(), '31');
INSERT INTO `user_permission` VALUES ('50', '用户管理', '40', '用户管理', '49', NOW(), null);
INSERT INTO `user_permission` VALUES ('51', '角色管理', '41', '角色管理', '49', NOW(), null);
INSERT INTO `user_permission` VALUES ('52', '部门管理', '42', '部门管理', '49', NOW(), null);
INSERT INTO `user_permission` VALUES ('53', '自定义表管理', '43', '自定义表管理', '49', NOW(), null);
INSERT INTO `user_permission` VALUES ('55', '录音质检', '45', '3.png', '43', NOW(), null);
INSERT INTO `user_permission` VALUES ('56', '报表管理', '49', '报表管理', '2', NOW(), '27');
INSERT INTO `user_permission` VALUES ('59', '计费报表', '52', '计费报表', '56', NOW(), '3');

INSERT INTO `user_permission` VALUES ('62', '网络配置', '55', '网络配置', '49', NOW(), null);
INSERT INTO `user_permission` VALUES ('63', 'SIP参数配置', '56', 'SIP参数配置', '64', NOW(), '8');
INSERT INTO `user_permission` VALUES ('64', 'IPPBX配置', '57', 'IPPBX配置', '3', NOW(), '29');
INSERT INTO `user_permission` VALUES ('65', 'Gateway配置', '58', 'Gateway配置', '64', NOW(), '1');
INSERT INTO `user_permission` VALUES ('66', '接入号配置', '59', '接入号配置', '64', NOW(), '2');
INSERT INTO `user_permission` VALUES ('67', '分机配置', '60', '分机配置', '64', NOW(), '3');
INSERT INTO `user_permission` VALUES ('68', '语音资源配置', '61', '语音资源配置', '64', NOW(), '4');
INSERT INTO `user_permission` VALUES ('69', 'IVR配置', '62', 'IVR配置', '64', NOW(), '5');
INSERT INTO `user_permission` VALUES ('70', '呼入路由', '63', '呼入路由', '64', NOW(), '6');
INSERT INTO `user_permission` VALUES ('71', '呼出路由', '64', '呼出路由', '64', NOW(), '7');
-- INSERT INTO `user_permission` VALUES ('72', '呼叫中心配置', '65', '呼叫中心配置', '3', NOW(), '32');
INSERT INTO `user_permission` VALUES ('73', '技能组管理', '66', '技能组管理', '64', NOW(), '9');
INSERT INTO `user_permission` VALUES ('74', '计费管理', '67', '计费管理', '2', NOW(), '23');
INSERT INTO `user_permission` VALUES ('75', '计费明细', '68', '计费明细', '74', NOW(), '1');
INSERT INTO `user_permission` VALUES ('76', '费率设置', '69', '费率设置', '74', NOW(), '2');
INSERT INTO `user_permission` VALUES ('77', '接入号-话务报表', '70', '接入号-话务报表', '56', NOW(), '4');
INSERT INTO `user_permission` VALUES ('78', '分机-话务报表', '71', '分机-话务报表', '56', NOW(), '5');
INSERT INTO `user_permission` VALUES ('79', '坐席-话务报表', '72', '分机-话务报表', '56', NOW(), '6');
INSERT INTO `user_permission` VALUES ('80', '坐席-KPI报表', '73', '坐席-KPI报表', '56', NOW(), '7');
INSERT INTO `user_permission` VALUES ('81', '技能组-KPI报表', '74', '技能组-KPI报表', '56', NOW(), '8');
INSERT INTO `user_permission` VALUES ('90', '查看号码', '75', '3.png', '2', NOW(), '75');
-- 新的数据管理
INSERT INTO `user_permission` VALUES ('91', '数据管理', '91', '数据整理', '2', NOW(), NULL);
INSERT INTO `user_permission` VALUES ('92', '批次管理', '92', '批次管理', '91', NOW(), NULL);
INSERT INTO `user_permission` VALUES ('93', '部门数据管理', '93', '部门数据管理', '91', NOW(), NULL);
INSERT INTO `user_permission` VALUES ('94', '我的任务', '94', '我的任务', '1', NOW(), '11');
INSERT INTO `user_permission` VALUES ('95', '我的意向客户', '95', '我的意向客户', '1', NOW(), '12');
INSERT INTO  user_permission  VALUES ('96', '我的共享池', '96', '我的共享池', '1', '2015-06-26 16:05:07', '13');

-- INSERT INTO `user_permission` (`uuid`, `permission_name`, `permission`, `permission_describe`, `parent_uuid`, `date`, `sequence`) VALUES ('82', '字典管理', '103', '字典管理', '49', '2015-09-09 18:54:48', null);
INSERT INTO user_permission (`uuid`, `permission_name`, `permission`, `permission_describe`, `parent_uuid`, `date`, `sequence`) VALUES ('97', '系统首页', '102', '系统首页', '1', '2015-05-14 11:33:39', '0');
-- 添加新的数据报表 2015/9/11
INSERT INTO `user_permission` (`uuid`, `permission_name`, `permission`, `permission_describe`, `parent_uuid`, `date`, `sequence`) VALUES ('98', '坐席-数据报表', '104', '坐席-数据报表', '56', '2015-09-11 17:02:49', NULL);
INSERT INTO `user_permission` (`uuid`, `permission_name`, `permission`, `permission_describe`, `parent_uuid`, `date`, `sequence`) VALUES ('99', '用户任务管理', '120', '用户任务管理', '91', '2015-06-26 16:05:07', NULL);
INSERT INTO `user_permission` VALUES ('101', '群呼任务', '121', '群呼任务', '91', NOW(), NULL);
-- 添加更多配置【字典+高级】 和共享池管理菜单
INSERT INTO `crm`.`user_permission` (`uuid`, `permission_name`, `permission`, `permission_describe`, `parent_uuid`, `date`, `sequence`) VALUES ('105', '高级选项', '105', '高级选项', '49', '2015-09-09 18:54:48', NULL);
INSERT INTO `crm`.`user_permission` (`uuid`, `permission_name`, `permission`, `permission_describe`, `parent_uuid`, `date`, `sequence`) VALUES ('12', '共享池管理', '20', '共享池管理', '91', '2015-06-26 16:05:07', NULL);


-- ----------------------------
-- 初始化默认管辖范围
-- ----------------------------

INSERT INTO `user_role_datarange_link` VALUES ('000002', 'cstmPools', 'de001');
INSERT INTO `user_role_datarange_link` VALUES ('000003', 'cstmPools', 'de001');

-- ----------------------------
-- 初始化自定义表
-- ----------------------------

INSERT INTO `design_table` VALUES ('1', 'cstm_customer', '{}', '客户信息表');
INSERT INTO `design_table` VALUES ('2', 'product', '{}', '产品信息表');
INSERT INTO `design_table` VALUES ('3', 'order_info', '{}', '订单信息表');
INSERT INTO `design_table` VALUES ('4', 'sys_call_log', '{}', '客服记录表');

INSERT INTO `design_table` VALUES ('5', 'new_order_info', '{}', '订单管理');


-- ----------------------------
-- 初始化自定义列
-- ----------------------------

INSERT INTO `design_column` VALUES ('1', '1', '1', '客户编号', 'customer_id', 'cstm_customer', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('10', '10', '3', '产品数量', 'product_number', 'product', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('11', '11', '4', '产品价格', 'product_price', 'product', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('12', '12', '5', '产品图片', 'product_picture', 'product', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('13', '13', '6', '产品描述', 'product_remark', 'product', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('14', '14', '7', '创建时间', 'product_create_time', 'product', 'DATE', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('2', '2', '2', '客户姓名', 'cstm_name', 'cstm_customer', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('15', '15', '1', '订单编号', 'order_id', 'order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('16', '16', '2', '订单状态', 'order_status', 'order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('17', '17', '3', '收货人', 'receive_user_name', 'order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('18', '18', '4', '手机号码', 'receive_user_mobile', 'order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('19', '19', '5', '地      址', 'receive_user_address', 'order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('20', '20', '6', '支付状态', 'pay_status', 'order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('21', '21', '7', '运费', 'freight', 'order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('22', '22', '8', '发票类型', 'invoice_type', 'order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('23', '23', '9', '发票具体信息', 'invoice_info', 'order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('24', '24', '10', '创建用户', 'order_user_uuid', 'order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('25', '25', '11', '下单时间', 'order_create_time', 'order_info', 'DATE', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('38', '40', '12', '客户编号', 'cstm_id', 'order_info', 'VARCHAR', '1', '1', '1', '1', '0', '0', '1', '', '');
INSERT INTO `design_column` VALUES ('39', '42', '13', '客户姓名', 'cstm_name', 'order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', '', '');
INSERT INTO `design_column` VALUES ('26', '26', '1', '编号', 'uuid', 'sys_call_log', 'VARCHAR', '1', '64', '1', '1', '0', '0', '1', '1', '0');
INSERT INTO `design_column` VALUES ('27', '27', '2', '坐席编号', 'agent_id', 'sys_call_log', 'VARCHAR', '1', '64', '1', '1', '0', '0', '1', '1', '1');
INSERT INTO `design_column` VALUES ('28', '28', '3', '坐席名称', 'agent_name', 'sys_call_log', 'VARCHAR', '1', '64', '1', '1', '1', '0', '1', '1', '0');
INSERT INTO `design_column` VALUES ('29', '29', '4', '客户电话', 'call_phone', 'sys_call_log', 'VARCHAR', '1', '64', '1', '1', '1', '0', '1', '1', '0');
INSERT INTO `design_column` VALUES ('3', '3', '3', '客户池', 'pool_id', 'cstm_customer', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('30', '30', '5', '呼叫时间', 'call_time', 'sys_call_log', 'DATE', '1', '1', '1', '1', '1', '0', '1', '1', '1');
INSERT INTO `design_column` VALUES ('31', '31', '6', '内容小记', 'text_log', 'sys_call_log', 'VARCHAR', '1', '255', '1', '1', '1', '0', '1', '0', '0');
INSERT INTO `design_column` VALUES ('32', '32', '7', '呼叫标记', 'in_out_flag', 'sys_call_log', 'ENUM', '1', '呼入,呼出', '1', '0', '1', '0', '1', '1', '1');
INSERT INTO `design_column` VALUES ('33', '33', '8', '数据来源', 'data_source', 'sys_call_log', 'VARCHAR', '1', '64', '0', '0', '0', '0', '1', '1', '1');
INSERT INTO `design_column` VALUES ('34', '34', '9', '数据ID', 'data_id', 'sys_call_log', 'VARCHAR', '1', '64', '0', '0', '0', '0', '1', '1', '1');
INSERT INTO `design_column` VALUES ('35', '35', '10', '呼叫ID', 'call_session_uuid', 'sys_call_log', 'VARCHAR', '1', '64', '0', '0', '0', '0', '1', '1', '1');
INSERT INTO `design_column` VALUES ('36', '36', '11', '录音路径', 'record_path', 'sys_call_log', 'VARCHAR', '1', '255', '0', '0', '0', '0', '1', '1', '1');
INSERT INTO `design_column` VALUES ('37', '37', '12', '呼叫时长', 'talk_time', 'sys_call_log', 'INT', '1', '11', '1', '1', '1', '0', '1', '1', '1');
INSERT INTO `design_column` VALUES ('4', '4', '4', '标记时间', 'start_date', 'cstm_customer', 'DATE', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('5', '5', '5', '客户描述', 'customer_des', 'cstm_customer', 'VARCHAR', '1', '1', '1', '1', '1', '1', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('6', '6', '6', '拥有者', 'own_id', 'cstm_customer', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('7', '7', '7', '电话号码', 'phone_number', 'cstm_customer', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('8', '8', '1', '产品编号', 'product_id', 'product', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `design_column` VALUES ('9', '9', '2', '产品名称', 'product_name', 'product', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);

INSERT INTO `design_column` VALUES ('81', '81', '8', '状态', 'status', 'cstm_customer', 'VARCHAR', '1', '1', '1', '0', '1', '0', '1', '1', '');


-- 新订单管理
INSERT INTO `crm`.`design_column` (`uuid`,  `orders`, `column_name`, `column_name_db`, `tableName`, `column_type`, `is_default`, `character_property`, `allow_select`, `allow_index`, `allow_show`, `allow_empty`, `column_value`, `is_hidden`, `is_readonly`) VALUES ('40',  '1', '订单编号', 'order_id', 'new_order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `crm`.`design_column` (`uuid`,  `orders`, `column_name`, `column_name_db`, `tableName`, `column_type`, `is_default`, `character_property`, `allow_select`, `allow_index`, `allow_show`, `allow_empty`, `column_value`, `is_hidden`, `is_readonly`) VALUES ('41',  '2', '订单状态', 'order_status', 'new_order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `crm`.`design_column` (`uuid`,  `orders`, `column_name`, `column_name_db`, `tableName`, `column_type`, `is_default`, `character_property`, `allow_select`, `allow_index`, `allow_show`, `allow_empty`, `column_value`, `is_hidden`, `is_readonly`) VALUES ('42',  '3', '收货人', 'receive_user_name', 'new_order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `crm`.`design_column` (`uuid`,  `orders`, `column_name`, `column_name_db`, `tableName`, `column_type`, `is_default`, `character_property`, `allow_select`, `allow_index`, `allow_show`, `allow_empty`, `column_value`, `is_hidden`, `is_readonly`) VALUES ('43',  '4', '手机号码', 'receive_user_mobile', 'new_order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `crm`.`design_column` (`uuid`,  `orders`, `column_name`, `column_name_db`, `tableName`, `column_type`, `is_default`, `character_property`, `allow_select`, `allow_index`, `allow_show`, `allow_empty`, `column_value`, `is_hidden`, `is_readonly`) VALUES ('44',  '5', '地      址', 'receive_user_address', 'new_order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `crm`.`design_column` (`uuid`,  `orders`, `column_name`, `column_name_db`, `tableName`, `column_type`, `is_default`, `character_property`, `allow_select`, `allow_index`, `allow_show`, `allow_empty`, `column_value`, `is_hidden`, `is_readonly`) VALUES ('45',  '6', '支付状态', 'pay_status', 'new_order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `crm`.`design_column` (`uuid`,  `orders`, `column_name`, `column_name_db`, `tableName`, `column_type`, `is_default`, `character_property`, `allow_select`, `allow_index`, `allow_show`, `allow_empty`, `column_value`, `is_hidden`, `is_readonly`) VALUES ('46',  '7', '运费', 'freight', 'new_order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `crm`.`design_column` (`uuid`,  `orders`, `column_name`, `column_name_db`, `tableName`, `column_type`, `is_default`, `character_property`, `allow_select`, `allow_index`, `allow_show`, `allow_empty`, `column_value`, `is_hidden`, `is_readonly`) VALUES ('47',  '8', '发票类型', 'invoice_type', 'new_order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `crm`.`design_column` (`uuid`,  `orders`, `column_name`, `column_name_db`, `tableName`, `column_type`, `is_default`, `character_property`, `allow_select`, `allow_index`, `allow_show`, `allow_empty`, `column_value`, `is_hidden`, `is_readonly`) VALUES ('48',  '9', '发票具体信息', 'invoice_info', 'new_order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `crm`.`design_column` (`uuid`,  `orders`, `column_name`, `column_name_db`, `tableName`, `column_type`, `is_default`, `character_property`, `allow_select`, `allow_index`, `allow_show`, `allow_empty`, `column_value`, `is_hidden`, `is_readonly`) VALUES ('49',  '10', '创建用户', 'order_user_uuid', 'new_order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `crm`.`design_column` (`uuid`,  `orders`, `column_name`, `column_name_db`, `tableName`, `column_type`, `is_default`, `character_property`, `allow_select`, `allow_index`, `allow_show`, `allow_empty`, `column_value`, `is_hidden`, `is_readonly`) VALUES ('50',  '11', '下单时间', 'order_create_time', 'new_order_info', 'DATE', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);
INSERT INTO `crm`.`design_column` (`uuid`,  `orders`, `column_name`, `column_name_db`, `tableName`, `column_type`, `is_default`, `character_property`, `allow_select`, `allow_index`, `allow_show`, `allow_empty`, `column_value`, `is_hidden`, `is_readonly`) VALUES ('51',  '12', 'call_session_uuid', 'call_session_uuid', 'new_order_info', 'VARCHAR', '1', '1', '1', '1', '1', '0', '1', NULL, NULL);



-- ----------------------------
-- 添加默认系统配置
-- ----------------------------

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('de00001', 'sys.data.getDataNumber', '55');
INSERT INTO `sys_config` VALUES ('de00002', 'sys.data.down', '2244');
INSERT INTO `sys_config` VALUES ('de00003', 'sys.data.up', '11');
INSERT INTO `sys_config` VALUES ('de00005', 'addZero', '1');
INSERT INTO `sys_config` VALUES ('de00006', 'sys.pop.timing', 'ringing');
INSERT INTO `sys_config` VALUES ('de00007', 'sys.pop.save.action', '0');
INSERT INTO `sys_config` VALUES ('de00008', 'sys.first', '0');
INSERT INTO `sys_config` VALUES ('de00009', 'sys.recording.play.config', 'open');
INSERT INTO `sys_config` VALUES ('de00010', 'sys.billing', 'false');
INSERT INTO `sys_config` VALUES ('de00011', 'nginx', 'XXXXXXXX:18080');
-- 隐藏号码配置
INSERT INTO `sys_config` VALUES ('de00012', 'hiddenPhoneNumber', 'false');
-- INSERT INTO `sys_config` VALUES ('de00013', 'sys.data.distinct', 'false');
-- 登录页面公司信息配置
INSERT INTO `sys_config` VALUES ('de00014', 'companyname', '上海睿声软件技术有限公司');
INSERT INTO `sys_config` VALUES ('de00015', 'companyadd', 'www.rssoft.cc');
INSERT INTO `sys_config` VALUES ('de00016', 'companyphone', '021-60172133');

-- 弹屏配置  高级选项 2015/08/27 by Frank
INSERT INTO `sys_config` (`uuid`, `syskey`, `sysval`) VALUES ('de00017', 'sys.pop.save.pop.type', '1');

-- 共享池获取数据配置
INSERT INTO sys_config (`uuid`, `syskey`, `sysval`) VALUES ('de00018', 'sys.globalshare.getDataNum', '5');

INSERT INTO `crm`.`sys_config` (`uuid`, `syskey`, `sysval`) VALUES ('de00019', 'sys.data.getDataTotalLimit', '200');
INSERT INTO `crm`.`sys_config` (`uuid`, `syskey`, `sysval`) VALUES ('de00020', 'sys.data.getDataDayLimit', '100');
INSERT INTO `crm`.`sys_config` (`uuid`, `syskey`, `sysval`) VALUES ('de00021', 'sys.data.getDataSingleLimit', '50');

INSERT INTO `crm`.`sys_config` (`uuid`, `syskey`, `sysval`) VALUES ('de00022', 'sys.call.bindexten', '1');


-- ----------------------------
-- 默认添加角色班长和坐席
-- ----------------------------
INSERT INTO `user_permission_role` VALUES ('000001', '管理员', '管理员', NOW(), NULL);
INSERT INTO `user_permission_role` VALUES ('000002', '班长', '班长', NOW(), NULL);

INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '2');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '3');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '36');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '37');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '38');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '39');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '40');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '41');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '42');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '43');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '44');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '45');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '46');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '47');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '48');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '49');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '50');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '51');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '52');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '53');
-- INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '54');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '55');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '56');

INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '59');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '62');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '63');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '64');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '65');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '66');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '67');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '68');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '69');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '70');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '71');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '73');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '74');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '75');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '76');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '77');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '78');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '79');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '80');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '81');
-- INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '82');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '91');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '92');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '93');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '98');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '99');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '101');
-- 更多配置在管理员权限下，共享池管理在班长和管理员权限下
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '105');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000001', '12');
INSERT INTO `crm`.`user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000002', '12');



INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '2');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '36');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '37');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '38');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '39');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '40');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '41');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '42');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '43');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '44');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '45');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '46');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '47');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '48');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '55');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '56');

INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '59');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '74');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '75');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '76');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '77');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '78');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '79');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '80');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000002', '81');

INSERT INTO `user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000002', '91');
INSERT INTO `user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000002', '92');
INSERT INTO `user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000002', '93');
INSERT INTO `user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000002', '98');
INSERT INTO `user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000002', '99');
INSERT INTO `user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000002', '101');





INSERT INTO `user_permission_role` VALUES ('000003', '坐席', '坐席', NOW(), '');

INSERT INTO `user_permissionrole_permission_link` VALUES ('000003', '20');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000003', '19');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000003', '18');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000003', '17');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000003', '16');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000003', '15');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000003', '14');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000003', '13');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000003', '11');
INSERT INTO `user_permissionrole_permission_link` VALUES ('000003', '1');

INSERT INTO `user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000003', '94');
INSERT INTO `user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000003', '95');
INSERT INTO `user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000003', '96');
INSERT INTO `user_permissionrole_permission_link` (`permissionrole_uuid`, `permission_uuid`) VALUES ('000003', '97');



-- ----------------------------
-- 创建一个默认目录
-- ----------------------------
INSERT INTO `knowledge_directory` VALUES ('ede001', '默认目录', '', '');





















-- ----------------------------
-- 创建mw库
-- ----------------------------

CREATE DATABASE IF NOT EXISTS mw;
USE mw;

-- ----------------------------
-- Table structure for mw_api_register
-- ----------------------------

CREATE TABLE IF NOT EXISTS `mw_api_register` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `keyy` varchar(100) NOT NULL,
  `val` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for fs_queue
-- ----------------------------
CREATE TABLE IF NOT EXISTS `fs_queue` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `config` text NOT NULL,
  `is_static` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for mw_exten_route
-- ----------------------------
CREATE TABLE IF NOT EXISTS `mw_exten_route` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `extension` varchar(30) NOT NULL,
  `type` varchar(10) NOT NULL,
  `destid` int(11) NOT NULL,
  `deststring` varchar(300) DEFAULT NULL,
  `can_del` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for mw_agent
-- ----------------------------
CREATE TABLE IF NOT EXISTS `mw_agent` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uid` varchar(100) NOT NULL,
  `info` varchar(1000) NOT NULL,
  `group_leader` char(1) NOT NULL,
  `agroup` varchar(100) DEFAULT NULL,
  `password` varchar(1000) DEFAULT NULL,
  `manages` varchar(1000) DEFAULT NULL,
  `job_number` varchar(100) DEFAULT NULL,
  `caller_id_number` varchar(100) DEFAULT NULL,
  `caller_id_name` varchar(100) DEFAULT NULL,
  `static_exten` varchar(30) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `static_strategy` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rt_queue_status
-- ----------------------------
CREATE TABLE IF NOT EXISTS `rt_queue_status` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `queue_id` int(11) NOT NULL,
  `queue_name` varchar(100) NOT NULL,
  `member_count` int(11) NOT NULL,
  `in_count` int(11) NOT NULL,
  `in_answer_count` int(11) NOT NULL,
  `ringring_time` int(11) NOT NULL,
  `busy_ready_count` int(11) NOT NULL,
  `idle_ready_count` int(11) NOT NULL,
  `not_ready_count` int(11) NOT NULL,
  `offline_count` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=MEMORY DEFAULT CHARSET=utf8;


-- ----------------------------
-- 接收事件
-- ----------------------------
INSERT INTO `mw_api_register` VALUES ('1', 'POP_SUB@answer', 'http://127.0.0.1:8080/rscc/event');
INSERT INTO `mw_api_register` VALUES ('2', 'CDR_SUB', 'http://127.0.0.1:8080/rscc/event');
INSERT INTO `mw_api_register` VALUES ('3', 'AGENT_STATUS_SUB', 'http://127.0.0.1:8080/rscc/event');
INSERT INTO `mw_api_register` VALUES ('4', 'MISS_CALL_SUB', 'http://127.0.0.1:8080/rscc/event');
INSERT INTO `mw_api_register` VALUES ('5', 'CONFRENCE_STATUS_EVENT', 'http://127.0.0.1:8080/rscc/event');
INSERT INTO `mw_api_register` VALUES ('6', 'QUEUE_STATUS_SUB', 'http://127.0.0.1:8080/rscc/event');


-- ----------------------------
-- 初始化队列
-- ----------------------------
INSERT INTO `fs_queue` VALUES ('1', '默认队列', '{\"tier-rule-no-agent-no-wait\":\"false\",\"tier-rule-wait-second\":\"300\",\"max-wait-time-with-no-agent-time-reached\":\"5\",\"moh-sound\":\"/usr/local/freeswitch/sounds/moh/aw.wav\",\"strategy\":\"longest-idle-agent\",\"agentWaitTime\":\"15\",\"max-wait-time-with-no-agent\":\"0\",\"abandoned-resume-allowed\":\"false\",\"time-base-score\":\"system\",\"discard-abandoned-after\":\"0\",\"tier-rules-apply\":\"false\",\"max-wait-time\":\"60\",\"tier-rule-wait-multiply-level\":\"true\"}', '0');

INSERT INTO `mw_exten_route` VALUES ('2', '队列默认队列', '999999', 'CALLCENTER', '1', '默认队列', '1');

INSERT INTO `rt_queue_status` VALUES ('1', '1', '默认队列', '0', '0', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- 初始化admin的agent
-- ----------------------------
INSERT INTO `mw_exten_route` VALUES ('1', 'admin', 'agent:admin', 'AGENT', '1', 'admin', '1');

INSERT INTO `mw_agent` VALUES ('1', 'admin', '超级管理员', '0', '', NULL, NULL, '000000', NULL, NULL, NULL, NULL, NULL);


-- ----------------------------
-- INIT MW.FSHOST
-- ----------------------------


CREATE TABLE IF NOT EXISTS
    mw_fshost
    (
        id bigint unsigned NOT NULL AUTO_INCREMENT,
        name VARCHAR(64) NOT NULL,
        ag_ip VARCHAR(32) NOT NULL,
        ag_port VARCHAR(5) NOT NULL,
        esl_ip VARCHAR(32) NOT NULL,
        esl_port VARCHAR(5) NOT NULL,
        esl_password VARCHAR(20) NOT NULL,
        PRIMARY KEY (id),
        CONSTRAINT id UNIQUE (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO mw_fshost (id, name, ag_ip, ag_port, esl_ip, esl_port, esl_password) VALUES (1, 'local', 'XXXXXXXX', '8080', 'XXXXXXXX', '8021', 'ClueCon');

CREATE TABLE IF NOT EXISTS
    fs_sipprofile
    (
        id bigint unsigned NOT NULL AUTO_INCREMENT,
        name VARCHAR(64) NOT NULL,
        sipip VARCHAR(64) NOT NULL,
        sipport VARCHAR(5) NOT NULL,
        type CHAR(1) NOT NULL,
        fshost_id INT NOT NULL,
        codecstring VARCHAR(100) NOT NULL,
        ext_ip VARCHAR(20),
        PRIMARY KEY (id),
        CONSTRAINT id UNIQUE (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO fs_sipprofile (id, name, sipip, sipport, type, fshost_id, codecstring, ext_ip) VALUES (1, 'internal', 'XXXXXXXX', '5060', 'i', 1, 'G729,PCMU,PCMA,GSM', 'auto-nat');
INSERT INTO fs_sipprofile (id, name, sipip, sipport, type, fshost_id, codecstring, ext_ip) VALUES (2, 'external', 'XXXXXXXX', '5080', 'e', 1, 'G729,PCMU,PCMA,GSM', 'auto-nat');

