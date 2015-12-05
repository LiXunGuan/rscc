-- ----------------------------
-- 创建一些索引
-- ----------------------------

USE crm;

ALTER TABLE `sys_call_log`
ADD UNIQUE INDEX `call_session_uuid` (`call_session_uuid`) USING HASH ,
ADD INDEX `call_phone` (`call_phone`) USING BTREE ,
ADD INDEX `agent_id` (`agent_id`) USING BTREE ,
ADD INDEX `agent_name` (`agent_name`) USING BTREE ,
ADD INDEX `call_time` (`call_time`) USING BTREE ,
ADD INDEX `data_id` (`data_id`) USING BTREE ,
ADD INDEX `data_source` (`data_source`) USING BTREE ;

-- 表字段添加索引
alter table qualitycheck add INDEX uuid_obj(uuid_obj);

-- 群呼任务表添加索引
ALTER TABLE `data_group_call_link`
ADD UNIQUE INDEX `data_phone` USING HASH (`data_phone`) ,
ADD INDEX `groupcall_id` USING BTREE (`groupcall_id`) ,
ADD INDEX `data_source` USING BTREE (`data_source`) ;


-- 计费表添加索引
ALTER TABLE `billing_bill`
ADD INDEX `caller` (`caller`) USING BTREE ,
ADD INDEX `startTimestamp` (`start_stamp`) USING BTREE ,
ADD INDEX `endTimestamp` (`end_stamp`) USING BTREE ;


-- 添加cstm_customer索引
ALTER TABLE `cstm_customer`
ADD INDEX `phone_number` (`phone_number`) USING BTREE ,
ADD INDEX `own_id` (`own_id`) USING BTREE ,
ADD INDEX `customer_id` (`customer_id`) USING HASH ,
ADD INDEX `start_date` (`start_date`) USING BTREE ;

ALTER TABLE `cstm_phone`
ADD INDEX `uuid` (`uuid`) USING HASH ,
ADD INDEX `main_phone` (`main_number`) USING BTREE ,
ADD INDEX `minor_number` (`minor_number`) USING BTREE ;

ALTER TABLE `cstm_pop_log`
ADD INDEX `date` (`date`) USING BTREE ,
ADD INDEX `cstm_phone` (`cstm_phone`) USING BTREE ;


ALTER TABLE `cstm_cstmservice`
ADD  INDEX `cstmservice_id` (`cstmservice_id`) USING HASH ;


USE MW;
ALTER TABLE `sys_mobile_info`
ADD UNIQUE INDEX `MobileNumber` USING HASH (`MobileNumber`) ;


-- ----------------------------
-- 创建一些索引
-- ----------------------------

USE crm;

ALTER TABLE `sys_call_log`
ADD INDEX `session_id` (`call_session_uuid`) USING HASH ,
ADD INDEX `phone` (`call_phone`) USING BTREE ,
ADD INDEX `agent_name` (`agent_name`) USING BTREE ;

-- 公告关联表添加字段（仅第一次运行时需要）
alter table agent_notice_user_link add publish_time datetime Null;

-- 表字段添加索引
alter table qualitycheck add INDEX uuid_obj(uuid_obj);

-- 群呼任务表添加索引
ALTER TABLE `data_group_call_link`
ADD UNIQUE INDEX `data_phone` USING HASH (`data_phone`) ;

-- 计费表添加索引
ALTER TABLE `billing_bill`
ADD INDEX `caller` (`caller`) USING BTREE ,
ADD INDEX `startTimestamp` (`start_stamp`) USING BTREE ,
ADD INDEX `endTimestamp` (`end_stamp`) USING BTREE ;
