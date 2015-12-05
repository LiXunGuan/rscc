 
CREATE DATABASE mw;

use mw;


CREATE TABLE `agents` (
  `name` varchar(255) DEFAULT NULL,
  `system` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `uuid` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `type` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `contact` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `status` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `state` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `max_no_answer` int(11) NOT NULL DEFAULT '0',
  `wrap_up_time` int(11) NOT NULL DEFAULT '0',
  `reject_delay_time` int(11) NOT NULL DEFAULT '0',
  `busy_delay_time` int(11) NOT NULL DEFAULT '0',
  `no_answer_delay_time` int(11) NOT NULL DEFAULT '0',
  `last_bridge_start` int(11) NOT NULL DEFAULT '0',
  `last_bridge_end` int(11) NOT NULL DEFAULT '0',
  `last_offered_call` int(11) NOT NULL DEFAULT '0',
  `last_status_change` int(11) NOT NULL DEFAULT '0',
  `no_answer_count` int(11) NOT NULL DEFAULT '0',
  `calls_answered` int(11) NOT NULL DEFAULT '0',
  `talk_time` int(11) NOT NULL DEFAULT '0',
  `ready_time` int(11) NOT NULL DEFAULT '0'
) ENGINE=MEMORY DEFAULT CHARSET=utf8;


CREATE TABLE `tiers` (
  `queue` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `agent` varchar(255) DEFAULT NULL,
  `state` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `level` int(11) NOT NULL DEFAULT '1',
  `position` int(11) NOT NULL DEFAULT '1'
) ENGINE=MEMORY DEFAULT CHARSET=utf8;

CREATE TABLE `members` (
  `queue` varchar(255) DEFAULT NULL,
  `system` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) NOT NULL DEFAULT '',
  `session_uuid` varchar(255) NOT NULL DEFAULT '',
  `cid_number` varchar(255) DEFAULT NULL,
  `cid_name` varchar(255) DEFAULT NULL,
  `system_epoch` int(11) NOT NULL DEFAULT '0',
  `joined_epoch` int(11) NOT NULL DEFAULT '0',
  `rejoined_epoch` int(11) NOT NULL DEFAULT '0',
  `bridge_epoch` int(11) NOT NULL DEFAULT '0',
  `abandoned_epoch` int(11) NOT NULL DEFAULT '0',
  `base_score` int(11) NOT NULL DEFAULT '0',
  `skill_score` int(11) NOT NULL DEFAULT '0',
  `serving_agent` varchar(255) DEFAULT NULL,
  `serving_system` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL
) ENGINE=MEMORY DEFAULT CHARSET=latin1;



CREATE TABLE `registrations` (
  `reg_user` varchar(256) DEFAULT NULL,
  `realm` varchar(256) DEFAULT NULL,
  `token` varchar(256) DEFAULT NULL,
  `url` varchar(1000) DEFAULT NULL,
  `expires` int(11) DEFAULT NULL,
  `network_ip` varchar(256) DEFAULT NULL,
  `network_port` varchar(256) DEFAULT NULL,
  `network_proto` varchar(256) DEFAULT NULL,
  `hostname` varchar(256) DEFAULT NULL,
  `metadata` varchar(256) DEFAULT NULL,
  KEY `regindex1` (`reg_user`,`realm`,`hostname`)
) ENGINE=MEMORY DEFAULT CHARSET=latin1;



CREATE TABLE `channels` (
  `uuid` varchar(256) NOT NULL DEFAULT '',
  `direction` varchar(32) DEFAULT NULL,
  `created` varchar(128) DEFAULT NULL,
  `created_epoch` int(11) DEFAULT NULL,
  `name` varchar(1024) DEFAULT NULL,
  `state` varchar(64) DEFAULT NULL,
  `cid_name` varchar(1024) DEFAULT NULL,
  `cid_num` varchar(256) DEFAULT NULL,
  `ip_addr` varchar(256) DEFAULT NULL,
  `dest` varchar(1024) DEFAULT NULL,
  `application` varchar(128) DEFAULT NULL,
  `application_data` varchar(4096) DEFAULT NULL,
  `dialplan` varchar(128) DEFAULT NULL,
  `context` varchar(128) DEFAULT NULL,
  `read_codec` varchar(128) DEFAULT NULL,
  `read_rate` varchar(32) DEFAULT NULL,
  `read_bit_rate` varchar(32) DEFAULT NULL,
  `write_codec` varchar(128) DEFAULT NULL,
  `write_rate` varchar(32) DEFAULT NULL,
  `write_bit_rate` varchar(32) DEFAULT NULL,
  `secure` varchar(64) DEFAULT NULL,
  `hostname` varchar(256) DEFAULT NULL,
  `presence_id` varchar(4096) DEFAULT NULL,
  `presence_data` varchar(4096) DEFAULT NULL,
  `callstate` varchar(64) DEFAULT NULL,
  `callee_name` varchar(1024) DEFAULT NULL,
  `callee_num` varchar(256) DEFAULT NULL,
  `callee_direction` varchar(5) DEFAULT NULL,
  `call_uuid` varchar(256) DEFAULT NULL,
  `sent_callee_name` varchar(1024) DEFAULT NULL,
  `sent_callee_num` varchar(256) DEFAULT NULL,
  `initial_cid_name` varchar(1024) DEFAULT NULL,
  `initial_cid_num` varchar(256) DEFAULT NULL,
  `initial_ip_addr` varchar(256) DEFAULT NULL,
  `initial_dest` varchar(1024) DEFAULT NULL,
  `initial_dialplan` varchar(128) DEFAULT NULL,
  `initial_context` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `channels1` (`hostname`),
  KEY `chidx1` (`hostname`),
  KEY `uuindex` (`uuid`,`hostname`),
  KEY `uuindex2` (`call_uuid`)
) ENGINE=MEMORY DEFAULT CHARSET=latin1;



CREATE TABLE `calls` (
  `call_uuid` varchar(255) DEFAULT NULL,
  `call_created` varchar(128) DEFAULT NULL,
  `call_created_epoch` int(11) DEFAULT NULL,
  `caller_uuid` varchar(256) DEFAULT NULL,
  `callee_uuid` varchar(256) DEFAULT NULL,
  `hostname` varchar(256) DEFAULT NULL,
  KEY `calls1` (`hostname`),
  KEY `callsidx1` (`hostname`),
  KEY `eruuindex` (`caller_uuid`,`hostname`),
  KEY `eeuuindex` (`callee_uuid`),
  KEY `eeuuindex2` (`call_uuid`)
) ENGINE=MEMORY DEFAULT CHARSET=latin1;




CREATE TABLE `rt_channal` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uuid` varchar(64) NOT NULL,
  `session_uuid` varchar(64) NOT NULL,
  `createtime` datetime NOT NULL,
  `type` char(1) NOT NULL,
  `caller_id_number` varchar(64) DEFAULT NULL,
  `answertime` datetime DEFAULT NULL,
  `route_number` varchar(64) DEFAULT NULL,
  `route_type` varchar(64) DEFAULT NULL,
  `route_string` varchar(64) DEFAULT NULL,
  `fs_user_id` varchar(64) DEFAULT NULL,
  `agent_uid` varchar(64) DEFAULT NULL,
  `agent_info` varchar(64) DEFAULT NULL,
  `gateway_name` varchar(64) DEFAULT NULL,
  `access_number` varchar(64) DEFAULT NULL,
  `fshost_name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=MEMORY  DEFAULT CHARSET=utf8;



CREATE TABLE `rt_agent_status` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `agent_uid` varchar(100) NOT NULL,
  `agent_info` varchar(200) NOT NULL,
  `queue` varchar(100) NOT NULL,
  `out_count` int(11) NOT NULL,
  `in_count` int(11) NOT NULL,
  `miss_count` int(11) NOT NULL,
  `in_time` int(11) NOT NULL,
  `out_time` int(11) NOT NULL,
  `ringing_time` int(11) NOT NULL,
  `binder_time` int(11) NOT NULL,
  `busy_time` int(11) NOT NULL,
  `status` varchar(10) NOT NULL,
  `state` varchar(20) NOT NULL,
  `uuid` varchar(64) DEFAULT NULL,
  `buuid` varchar(64) DEFAULT NULL,
  `call_session_uuid` varchar(64) DEFAULT NULL,
  `extern` varchar(100) DEFAULT NULL,
  `last_binder_date` datetime DEFAULT NULL,
  `last_busy_date` datetime DEFAULT NULL,
  `busy_reason` varchar(100) DEFAULT NULL,
  `number` varchar(100) DEFAULT NULL,
  `up_time` datetime DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=MEMORY  DEFAULT CHARSET=utf8;



CREATE TABLE `rt_queue_status` (
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

INSERT INTO `rt_queue_status` VALUES ('1', '1', '默认队列', '0', '0', '0', '0', '0', '0', '0', '0');



CREATE TABLE `rt_conference_room` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `fshotip` varchar(1000) NOT NULL,
  `create_agent_uid` varchar(64) NOT NULL,
  `create_agent_info` varchar(200) NOT NULL,
  `establish_time` datetime NOT NULL,
  `record` varchar(200) DEFAULT NULL,
  `call_session_uuid` varchar(1000) DEFAULT NULL,
  `conference_uid` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `rt_conference_details` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `conference_name` varchar(100) NOT NULL,
  `call_session_uuid` varchar(64) NOT NULL,
  `uuid` varchar(64) NOT NULL,
  `extension` varchar(100) NOT NULL,
  `fshost` char(30) NOT NULL,
  `invitation_state` char(1) NOT NULL,
  `mute_state` char(1) NOT NULL,
  `agent_id` varchar(100) DEFAULT NULL,
  `agent_info` varchar(100) DEFAULT NULL,
  `establish_time` datetime DEFAULT NULL,
  `answer_time` datetime DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `cdr` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `call_session_uuid` varchar(64) NOT NULL,
  `fshost_name` varchar(20) NOT NULL,
  `start_stamp` datetime NOT NULL,
  `end_stamp` datetime NOT NULL,
  `duration` int(11) NOT NULL,
  `billsec` int(11) NOT NULL,
  `hangup_cause` varchar(50) NOT NULL,
  `sip_hangup_disposition` varchar(64) NOT NULL,
  `aleguuid` varchar(64) NOT NULL,
  `context` varchar(100) NOT NULL,
  `type` varchar(10) NOT NULL,
  `answer_stamp` datetime DEFAULT NULL,
  `bridge_stamp` datetime DEFAULT NULL,
  `bridgesec` int(11) DEFAULT NULL,
  `bleg_uuid` varchar(64) DEFAULT NULL,
  `caller_id_number` varchar(30) DEFAULT NULL,
  `caller_type` varchar(20) DEFAULT NULL,
  `caller_name` varchar(50) DEFAULT NULL,
  `caller_agent_interface_type` varchar(30) DEFAULT NULL,
  `caller_agent_interface_name` varchar(30) DEFAULT NULL,
  `caller_agent_interface_exten` varchar(30) DEFAULT NULL,
  `caller_agent_id` varchar(100) DEFAULT NULL,
  `caller_agent_info` varchar(200) DEFAULT NULL,
  `dest_number` varchar(30) DEFAULT NULL,
  `dest_type` varchar(20) DEFAULT NULL,
  `dest_name` varchar(20) DEFAULT NULL,
  `dest_agent_interface_type` varchar(20) DEFAULT NULL,
  `dest_agent_interface_name` varchar(20) DEFAULT NULL,
  `dest_agent_interface_exten` varchar(20) DEFAULT NULL,
  `dest_agent_id` varchar(20) DEFAULT NULL,
  `dest_agent_info` varchar(20) DEFAULT NULL,
  `gateway_name` varchar(64) DEFAULT NULL,
  `call_direction` varchar(10) DEFAULT NULL,
  `access_number` varchar(64) DEFAULT NULL,
  `record_file` varchar(200) DEFAULT NULL,
  `is_upload` char(1) DEFAULT NULL,
  `user_field` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `index_start_stamp` (`start_stamp`),
  KEY `index_caller_id_number` (`caller_id_number`),
  KEY `index_dest_number` (`dest_number`),
  KEY `index_dest_agent_interface_exten` (`dest_agent_interface_exten`),
  KEY `index_call_session_uuid` (`call_session_uuid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;




CREATE TABLE `report_accessnumber` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `year` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `week` int(11) NOT NULL,
  `day` int(11) NOT NULL,
  `hour` int(11) NOT NULL,
  `timestamp` datetime NOT NULL,
  `name` varchar(30) NOT NULL,
  `out_f_callcount` int(11) NOT NULL,
  `in_f_callcount` int(11) NOT NULL,
  `out_t_callcount` int(11) NOT NULL,
  `in_t_callcount` int(11) NOT NULL,
  `out_t_duration` int(11) NOT NULL,
  `in_t_duration` int(11) NOT NULL,
  `max_concurrent` int(11) NOT NULL,
  `min_concurrent` int(11) NOT NULL,
  `info` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `index_name` (`name`),
  KEY `index_timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE TABLE `report_agent` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `year` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `week` int(11) NOT NULL,
  `day` int(11) NOT NULL,
  `hour` int(11) NOT NULL,
  `timestamp` datetime NOT NULL,
  `name` varchar(30) NOT NULL,
  `out_f_callcount` int(11) NOT NULL,
  `in_f_callcount` int(11) NOT NULL,
  `in_f_ringduration` int(11) NOT NULL,
  `in_t_ringduration` int(11) NOT NULL,
  `out_t_callcount` int(11) NOT NULL,
  `in_t_callcount` int(11) NOT NULL,
  `out_t_duration` int(11) NOT NULL,
  `in_t_duration` int(11) NOT NULL,
  `info` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `index_name` (`name`),
  KEY `index_timestamp` (`timestamp`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;



CREATE TABLE `report_agentkpi` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `year` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `week` int(11) NOT NULL,
  `day` int(11) NOT NULL,
  `timestamp` datetime NOT NULL,
  `name` varchar(30) NOT NULL,
  `info` varchar(300) DEFAULT NULL,
  `online_time` int(11) DEFAULT NULL,
  `score_count` int(11) DEFAULT NULL,
  `score_sum` int(11) DEFAULT NULL,
  `pause_reason_1_count` int(11) DEFAULT NULL,
  `pause_reason_2_count` int(11) DEFAULT NULL,
  `pause_reason_3_count` int(11) DEFAULT NULL,
  `pause_reason_4_count` int(11) DEFAULT NULL,
  `pause_reason_5_count` int(11) DEFAULT NULL,
  `pause_reason_6_count` int(11) DEFAULT NULL,
  `pause_reason_7_count` int(11) DEFAULT NULL,
  `pause_reason_8_count` int(11) DEFAULT NULL,
  `pause_reason_9_count` int(11) DEFAULT NULL,
  `pause_reason_10_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `index_name` (`name`),
  KEY `index_timestamp` (`timestamp`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;



CREATE TABLE `report_exten` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `year` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `week` int(11) NOT NULL,
  `day` int(11) NOT NULL,
  `hour` int(11) NOT NULL,
  `timestamp` datetime NOT NULL,
  `name` varchar(30) NOT NULL,
  `out_f_callcount` int(11) NOT NULL,
  `in_f_callcount` int(11) NOT NULL,
  `in_f_ringduration` int(11) NOT NULL,
  `in_t_ringduration` int(11) NOT NULL,
  `out_t_callcount` int(11) NOT NULL,
  `in_t_callcount` int(11) NOT NULL,
  `out_t_duration` int(11) NOT NULL,
  `in_t_duration` int(11) NOT NULL,
  `info` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `index_name` (`name`),
  KEY `index_timestamp` (`timestamp`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;



CREATE TABLE `report_queue` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `year` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `week` int(11) NOT NULL,
  `day` int(11) NOT NULL,
  `hour` int(11) NOT NULL,
  `timestamp` datetime NOT NULL,
  `name` varchar(30) NOT NULL,
  `in_f_callcount` int(11) NOT NULL,
  `in_t_callcount` int(11) NOT NULL,
  `in_f_wait` int(11) NOT NULL,
  `in_t_wait` int(11) NOT NULL,
  `in_t_duration` int(11) NOT NULL,
  `max_queued` int(11) NOT NULL,
  `min_queued` int(11) NOT NULL,
  `answercount_0_5` int(11) NOT NULL,
  `answercount_5_10` int(11) NOT NULL,
  `answercount_10_15` int(11) NOT NULL,
  `answercount_15_20` int(11) NOT NULL,
  `answercount_20_25` int(11) NOT NULL,
  `answercount_25_30` int(11) NOT NULL,
  `answercount_30_40` int(11) NOT NULL,
  `answercount_40_50` int(11) NOT NULL,
  `answercount_50_60` int(11) NOT NULL,
  `answercount_60_max` int(11) NOT NULL,
  `info` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `index_name` (`name`),
  KEY `index_timestamp` (`timestamp`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;




CREATE TABLE `sys_areacode` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `area_code` varchar(255) NOT NULL,
  `post_code` varchar(255) NOT NULL,
  `mobile_area` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE sys_areacode ADD INDEX index_area_code (area_code);

CREATE TABLE `sys_mobile_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `MobileNumber` varchar(255) NOT NULL,
  `MobileArea` varchar(255) NOT NULL,
  `MobileType` varchar(255) NOT NULL,
  `AreaCode` varchar(255) NOT NULL,
  `PostCode` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE sys_mobile_info ADD INDEX index_MobileNumber (MobileNumber);

CREATE TABLE `sys_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `val` varchar(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


INSERT INTO sys_config (id, name, val) VALUES
  (1, 'template', '{"reject-delay-time":"0","status":"Available","no-answer-delay-time":"0","wrap-up-time":"0","busy-reason":"","busy-delay-time":"0","max-no-answer":"0"}');
INSERT INTO `sys_config` (`id`, `name`, `val`) VALUES 
 ('2', 'agentbusy', '{\"state_6\":0,\"name_5\":\"其他！\",\"name_3\":\"晚餐！\",\"name_4\":\"洗手间！\",\"name_2\":\"午餐！\",\"name_1\":\"小休！\",\"name_6\":\"自定义！\",\"state_1\":1,\"state_2\":1,\"state_3\":1,\"state_4\":1,\"state_5\":1}');

CREATE TABLE `fs_queue` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `config` text NOT NULL,
  `is_static` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

INSERT INTO `fs_queue` VALUES ('1', '默认队列', '{\"tier-rule-no-agent-no-wait\":\"false\",\"tier-rule-wait-second\":\"300\",\"max-wait-time-with-no-agent-time-reached\":\"5\",\"moh-sound\":\"/usr/local/freeswitch/sounds/moh/aw.wav\",\"strategy\":\"longest-idle-agent\",\"agentWaitTime\":\"15\",\"max-wait-time-with-no-agent\":\"0\",\"abandoned-resume-allowed\":\"false\",\"time-base-score\":\"system\",\"discard-abandoned-after\":\"0\",\"tier-rules-apply\":\"false\",\"max-wait-time\":\"60\",\"tier-rule-wait-multiply-level\":\"true\"}', '0');


create VIEW members_view as
(select * from members m,fs_queue q where m.queue=q.id and m.state!='Answered' and m.state!='Abandoned');


CREATE TABLE `sys_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `account_name` varchar(64) NOT NULL,
  `account_password` varchar(32) NOT NULL,
  `menustring` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


REPLACE INTO sys_user (`id`, `account_name`, `account_password`, `menustring`) VALUES ('1', 'admin', 'admin', '1,11,21,31,51,2,3,4,5,6,7,12,13,22,23,24,25,26,32,33,34,35,36,37,38,39,40,41,42,52,53,54,55,56');


CREATE TABLE `sys_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `parentid` int(11) NOT NULL,
  `actionurl` varchar(256) NOT NULL,
  `seq` int(11) NOT NULL,
  `action_css` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('1', '运行状态', '0', '', '1', 'fa fa-lg fa-fw fa-dashboard');
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('2', '系统状态', '1', 'runtime/sysinforuntime', '1', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('3', '分机状态', '1', 'runtime/sipuserruntime', '2', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('4', '技能组状态', '1', 'runtime/queueruntime', '3', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('5', '技能组详情', '1', 'runtime/queueruntime/get', '4', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('6', '坐席状态', '1', 'runtime/agentruntime', '5', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('7', '通话状态', '1', 'runtime/callruntime', '6', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('11', '录音管理', '0', '', '2', 'fa fa-lg fa-fw fa-microphone');
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('12', '录音查询', '11', 'record/record', '1', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('13', '会议室录音', '11', 'cdrconference', '2', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('21', '报表', '0', '', '3', 'fa fa-lg fa-fw fa-bar-chart-o');
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('22', '接入号-话务报表', '21', 'report/reportaccessnumbertraffic', '1', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('23', '分机-话务报表', '21', 'report/reportextentraffic', '2', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('24', '坐席-话务报表', '21', 'report/reportagenttraffic', '3', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('25', '坐席-KPI报表', '21', 'report/reportagentkpi', '4', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('26', '技能组-KPI报表', '21', 'report/reportqueuekpi', '5', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('31', '呼叫中心配置', '0', '', '4', 'fa fa-lg fa-fw fa-wrench');
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('32', '集群管理', '31', 'config/fshost', '1', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('33', 'SIP Profile', '31', 'config/sipprofile', '2', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('34', 'Gateway', '31', 'config/gateway', '3', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('35', '接入号管理', '31', 'config/accessnumber', '4', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('36', 'SIP 账号', '31', 'config/sipuser', '5', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('37', '技能组管理', '31', 'config/queue', '6', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('38', '拨号计划', '31', 'config/fsxml', '7', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('39', 'ivr配置', '31', 'config/ivr', '8', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('40', '坐席管理', '31', 'config/agent', '9', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('41', '分机号管理', '31', 'config/extenroute', '10', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('42', '接入号路由配置', '31', 'config/accessnumberroute', '11', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('51', '系统配置', '0', '', '5', 'fa fa-lg fa-fw fa-cogs');
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('52', '置忙原因配置', '51', 'system/agentbusy', '1', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('53', '默认路由', '51', 'config/sysconfig', '2', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('54', '语音管理', '51', 'system/uploadingvoice', '3', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('55', '号码归属地', '51', 'system/mobileinfo', '4', NULL);
INSERT INTO sys_menu (`id`, `name`, `parentid`, `actionurl`, `seq`, `action_css`) VALUES ('56', '账号设置', '51', 'system/account', '5', NULL);

CREATE TABLE `fs_xml` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `cata` varchar(64) NOT NULL,
  `type` char(10) NOT NULL,
  `content` longtext NOT NULL,
  `dest` varchar(200) NOT NULL,
  `status` char(1) NOT NULL,
  `hostname` char(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;


INSERT INTO `fs_xml` (`id`, `name`, `cata`, `type`, `content`, `dest`, `status`, `hostname`) VALUES ('1', 'default', 'CATA_XML-DIALPLAN', 'single', '<include>\n\n<context name=\"default\">\n\n    <extension name=\"core\">\n        <condition field=\"destination_number\" expression=\"\\d+|^c-\">\n            <action application=\"set_audio_level\" data=\"read -1\"></action>\n            <action application=\"set_audio_level\" data=\"write 0\"></action>\n\n\n            <action application=\"set\" data=\"hangup_complete_with_xml=true\"></action>\n            <action application=\"set\" data=\"action=route\"></action>\n\n            <action application=\"set\" data=\"bridge_pre_execute_bleg_app=bind_digit_action\"></action>\n            <action application=\"set\" data=\"bridge_pre_execute_bleg_data=mwtransfer,1890,exec:execute_extension,transfer XML default\"></action>\n\n            <action application=\"set\" data=\"mw_extension=${destination_number}\"></action>\n            <action application=\"socket\" data=\"127.0.0.1:19198 async full\"></action>\n             \n             <action application=\"set\" data=\"mw_dailway=m\"></action>\n             <action application=\"export\" data=\"mw_inout=${mw_inout}\"></action>\n          \n            <action application=\"export\" data=\"session_uuid=${session_uuid}\"></action>\n           \n            <action application=\"export\" data=\"record_file_name=${cdrrecord}/${strftime(%Y/%m/%d)}/${session_uuid}.mp3\" ></action>\n            <action application=\"record_session\" data=\"${record_file_name}\"></action>\n            \n            <action application=\"transfer\" data=\"${mw_route}\"></action>\n        </condition>\n    </extension>\n\n   \n\n    <extension name=\"user\">\n        <condition field=\"destination_number\" expression=\"^BRIDGE$\">\n            <action application=\"set\" data=\"ignore_early_media=false\"></action>\n            <action application=\"bridge\" data=\"${mw_data}\"></action>\n        </condition>\n    </extension>\n\n    <extension name=\"cc\">\n        <condition field=\"destination_number\" expression=\"^CC$\">\n        \n             <action application=\"set\" data=\"foragent=true\"></action> \n             \n              <action application=\"set\" data=\"effective_caller_id_number=${mw_caller}\"></action>   \n              <action application=\"set\" data=\"effective_caller_id_name=${mw_caller}\"></action> \n\n             <action application=\"playback\" data=\"${welcome}\"></action>\n\n            <action application=\"set\" data=\"cc_export_vars=session_uuid,mw_inout,foragent,record_file_name\"></action>\n            <action application=\"callcenter\" data=\"${mw_data}\"></action>\n        </condition>\n    </extension>\n\n    <extension name=\"ivr\">\n        <condition field=\"destination_number\" expression=\"^IVR$\">\n            <action application=\"ivr\" data=\"${mw_data}\"></action>\n        </condition>\n    </extension>\n\n    <extension name=\"dialplan\">\n        <condition field=\"destination_number\" expression=\"^DP$\">\n            <action application=\"transfer\" data=\"${mw_data}\"></action>\n        </condition>\n    </extension>\n\n    <extension name=\"conference\">\n        <condition field=\"destination_number\" expression=\"^CONFERENCE$\">\n            <action application=\"conference\" data=\"${mw_data}\"></action>\n        </condition>\n    </extension>\n\n    <extension name=\"transfer\">\n        <condition field=\"destination_number\" expression=\"^transfer$\">\n            <action application=\"answer\"></action>\n            <action application=\"transfer\" data=\"-bleg ${last_matching_digits} XML default\"></action>\n        </condition>\n    </extension>\n\n</context>\n</include>', '/usr/local/freeswitch/conf/dialplan/default.xml', '0', '*');
INSERT INTO `fs_xml` (`id`, `name`, `cata`, `type`, `content`, `dest`, `status`, `hostname`) VALUES ('2', 'public', 'CATA_XML-DIALPLAN', 'single', '<include>\n\n    <context name=\"public\">\n        <extension name=\"ivr_b\">\n            <condition field=\"destination_number\" expression=\"^(\\d+)$\" require-nested=\"false\">\n                <action application=\"answer\"></action>\n\n                <action application=\"set\" data=\"session_uuid=${create_uuid()}\"></action>\n                <action application=\"set\" data=\"mw_inout=i\"></action>\n               \n\n                <action application=\"set\" data=\"mw_caller=${caller_id_number}\"></action>\n                <action application=\"set\" data=\"mw_dest=${destination_number}\"></action>\n\n                <action application=\"set\" data=\"action=callin\"></action>\n                <action application=\"socket\" data=\"127.0.0.1:19198 async full\"></action>\n\n                <action application=\"transfer\" data=\"${next} XML default\"></action>\n            </condition>\n        </extension>\n    </context>\n\n</include>', '/usr/local/freeswitch/conf/dialplan/public.xml', '0', '*');


-- ----------------------------
-- Table structure for mw_agent
-- ----------------------------
DROP TABLE IF EXISTS `mw_agent`;
CREATE TABLE `mw_agent` (
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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mw_agent
-- ----------------------------
INSERT INTO `mw_agent` VALUES ('1', 'admin', '超级管理员', '0', '', null, null, '000000', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('2', '2205', '投诉组_坐席2205', '0', '', null, null, '2205', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('3', '2204', '投诉组_坐席2204', '0', '', null, null, '2204', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('4', '2203', '投诉组_坐席2203', '0', '', null, null, '2203', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('5', '2202', '投诉组_坐席2202', '0', '', null, null, '2202', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('6', '2201', '投诉组_坐席2201', '0', '', null, null, '2201', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('7', '2200', '客服_投诉组组长【赵一】', '0', '', null, null, '', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('8', '2105', '技术支持组_坐席2105', '0', '', null, null, '2105', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('9', '2103', '技术支持组_坐席2103', '0', '', null, null, '2103', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('10', '2102', '技术支持组_坐席2102', '0', '', null, null, '2102', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('11', '2101', '技术支持组_坐席2101', '0', '', null, null, '2101', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('12', '2100', '客服_技术支持组长【王二】', '0', '', null, null, '', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('13', '2000', '客服部经理【赵东】', '0', '', null, null, '', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('14', '1205', '销售2组_坐席1205', '0', '', null, null, '1205', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('15', '1204', '销售2组_坐席1204', '0', '', null, null, '1204', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('16', '1202', '销售2组_坐席1202', '0', '', null, null, '1202', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('17', '1201', '销售2组_坐席1201', '0', '', null, null, '1201', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('18', '1200', '销售2组_组长【李四】', '0', '', null, null, '', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('19', '1105', '销售1组_坐席1105', '0', '', null, null, '1105', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('20', '1104', '销售1组_坐席1104', '0', '', null, null, '1104', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('21', '1103', '销售1组_坐席1103', '0', '', null, null, '1103', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('22', '1102', '销售1组_坐席1102', '0', '', null, null, '1102', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('23', '1101', '销售1组_坐席1101', '0', '', null, null, '1101', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('24', '1100', '销售1组_组长【张三】', '0', '', null, null, '', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('25', '1000', '销售部经理【马五】', '0', '', null, null, '', null, null, null, null, null);
INSERT INTO `mw_agent` VALUES ('26', '1203', '销售2组_坐席1203', '0', '', null, null, '1203', null, null, null, null, null);

















