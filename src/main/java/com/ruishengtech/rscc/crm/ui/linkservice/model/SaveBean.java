package com.ruishengtech.rscc.crm.ui.linkservice.model;

public class SaveBean {
	
	//客户信息
	private String customer_id;
	
	private String cstm_name;
	
	private String pool_id;
	
	private String own_id;
	
	private String phone_number;
	
	private String phone_number_a;
	
	//注意：这个是customerUuid！这个是customerUuid！这个是customerUuid！重要的事情说三遍
	private String uid;

	//预约提醒
	private String scheduleId;
	
	private String reminderTime;
	
	private String reminderTimeContent;
	
	private String oldstime;
	
	//呼叫小计
	private String call_session_uuid;
	
	private String text_log;
	
	private String log_uid;
	
	private String data_id;
	
	private String call_time;
	
	private String data_source;
	
	private String call_phone;
	
	private String agent_id;
	
	private String agent_name;
	
	//数据结果
	private String result;

	private String status;
	
	private String callStat;
	
	private String poptype;
	
	//这个号码的状态，根据这个状态来判断需要保存哪些
	private Integer phoneStat;
	
	//管理员详情操作
	private String newOwnUser;
	//当前数据的uuid
	private String dataUuid;
	//数据所在批次
	private String batchUuid;
	//当前数据归属人的部门
	private String deptUuid;
	//当前数据的归属人
	private String ownerUuid;
	//转移原因
	private String changeReason;
	
	public static final int EntryNotIntent = 0;		//在自己的数据表中，不是意向客户。保存预约记录，保存数据结果，保存呼叫小计，是否保存详细信息看result。
	public static final int EntryIntent = 1;		//在自己的数据表中，是自己的意向客户。保存预约记录，保存数据结果，保存呼叫小计，是否保存详细信息看result。
	public static final int NotEntryNotAcstm = 2;	//不在自己的数据表中，且没有数据详细信息(可能在某人的任务中，也可能是某人的意向)，只保存呼叫记录
	public static final int NotEntryAcstmNotMe = 3;	//不在自己的任务表中，但是有数据详细信息，但是数据详细信息中的拥有者不是自己(可能是某人的意向，或者某人的成交)，只保存呼叫记录
	public static final int NotEntryAcstmMe = 4;	//是自己的成交客户 ，不保存数据结果，其他都保存

	public String getDataUuid() {
		return dataUuid;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public void setDataUuid(String dataUuid) {
		this.dataUuid = dataUuid;
	}

	public String getBatchUuid() {
		return batchUuid;
	}

	public void setBatchUuid(String batchUuid) {
		this.batchUuid = batchUuid;
	}

	public String getDeptUuid() {
		return deptUuid;
	}

	public void setDeptUuid(String deptUuid) {
		this.deptUuid = deptUuid;
	}

	public String getOwnerUuid() {
		return ownerUuid;
	}

	public void setOwnerUuid(String ownerUuid) {
		this.ownerUuid = ownerUuid;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public String getNewOwnUser() {
		return newOwnUser;
	}

	public void setNewOwnUser(String newOwnUser) {
		this.newOwnUser = newOwnUser;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Integer getPhoneStat() {
		return phoneStat;
	}

	public void setPhoneStat(Integer phoneStat) {
		this.phoneStat = phoneStat;
	}

	public String getCallStat() {
		return callStat;
	}

	public void setCallStat(String callStat) {
		this.callStat = callStat;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getCstm_name() {
		return cstm_name;
	}

	public void setCstm_name(String cstm_name) {
		this.cstm_name = cstm_name;
	}

	public String getPool_id() {
		return pool_id;
	}

	public void setPool_id(String pool_id) {
		this.pool_id = pool_id;
	}

	public String getOwn_id() {
		return own_id;
	}

	public void setOwn_id(String own_id) {
		this.own_id = own_id;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(String reminderTime) {
		this.reminderTime = reminderTime;
	}

	public String getReminderTimeContent() {
		return reminderTimeContent;
	}

	public void setReminderTimeContent(String reminderTimeContent) {
		this.reminderTimeContent = reminderTimeContent;
	}

	public String getText_log() {
		return text_log;
	}

	public void setText_log(String text_log) {
		this.text_log = text_log;
	}

	public String getCall_session_uuid() {
		return call_session_uuid;
	}

	public void setCall_session_uuid(String call_session_uuid) {
		this.call_session_uuid = call_session_uuid;
	}

	public String getPoptype() {
		return poptype;
	}

	public void setPoptype(String poptype) {
		this.poptype = poptype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getPhone_number_a() {
		return phone_number_a;
	}

	public void setPhone_number_a(String phone_number_a) {
		this.phone_number_a = phone_number_a;
	}

	public String getLog_uid() {
		return log_uid;
	}

	public void setLog_uid(String log_uid) {
		this.log_uid = log_uid;
	}

	public String getData_id() {
		return data_id;
	}

	public void setData_id(String data_id) {
		this.data_id = data_id;
	}

	public String getCall_time() {
		return call_time;
	}

	public void setCall_time(String call_time) {
		this.call_time = call_time;
	}

	public String getData_source() {
		return data_source;
	}

	public void setData_source(String data_source) {
		this.data_source = data_source;
	}

	public String getCall_phone() {
		return call_phone;
	}

	public void setCall_phone(String call_phone) {
		this.call_phone = call_phone;
	}

	public String getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}

	public String getOldstime() {
		return oldstime;
	}

	public void setOldstime(String oldstime) {
		this.oldstime = oldstime;
	}
	
}
