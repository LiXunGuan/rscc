package com.ruishengtech.rscc.crm.datamanager.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "new_data_global_share")
public class DataGlobalShare extends CommonDbBean {
	
	public static String tableName = DataGlobalShare.class.getAnnotation(Table.class).name();
	
	//从哪个批次取得数据
	@Index
	@Column(meta = "VARCHAR(64)", column = "batch_uuid")
    private String batchUuid;

	//号码
	@Index(type = IndexDefinition.TYPE_UNIQUE)
	@Column(meta = "VARCHAR(20)", column = "phone_number")
	private String phoneNumber;
	
	//数据的其他信息
	@NColumn(meta = "VARCHAR(1024)", column = "json")
	private String json;
	
	//当前被哪人占用
	@NColumn(meta = "VARCHAR(64)", column = "own_user")
	private String ownUser;
	
	//占用时间
	@NColumn(meta = "DATETIME", column = "own_user_timestamp")
	private Date ownUserTimestamp;
	
	//从谁那里移动进来的
	@Index
	@NColumn(meta = "VARCHAR(64)", column = "transfer_department")
	private String transferDepartment;
			
	//从谁那里移动进来的
	@Index
	@NColumn(meta = "VARCHAR(64)", column = "transfer_user")
	private String transferUser;
		
	//进池时间
	@Index
	@NColumn(meta = "DATETIME", column = "transfer_timestamp")
	private Date transferTimestamp;

	@NColumn(meta = "INT DEFAULT 0", column = "call_count")
	private Integer callCount;
	
	//空的是没通，1是接通
	@NColumn(meta = "VARCHAR(64)", column = "last_call_result")
	private String lastCallResult;
	
	@Index
	@NColumn(meta = "TIMESTAMP", column = "last_call_time")
	private Date lastCallTime;
	
	//进池次数
	@NColumn(meta = "INT DEFAULT 0 ", column = "enter_count")
	private Integer enterCount;

	@NColumn(meta = "VARCHAR(64)", column = "intent_type")
	private String intentType;
	
	@NColumn(meta = "DATETIME", column = "intent_timestamp")
	private Date intentTimestamp;
	
	private String batchName;
	
	private String ownUserName;
	
	private String transferUserName;
	
	private String transferDeptName;
	
	public String getBatchUuid() {
		return batchUuid;
	}

	public void setBatchUuid(String batchUuid) {
		this.batchUuid = batchUuid;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getOwnUser() {
		return ownUser;
	}

	public void setOwnUser(String ownUser) {
		this.ownUser = ownUser;
	}

	public Date getOwnUserTimestamp() {
		return ownUserTimestamp;
	}

	public void setOwnUserTimestamp(Date ownUserTimestamp) {
		this.ownUserTimestamp = ownUserTimestamp;
	}

	public String getTransferUser() {
		return transferUser;
	}

	public void setTransferUser(String transferUser) {
		this.transferUser = transferUser;
	}

	public Date getTransferTimestamp() {
		return transferTimestamp;
	}

	public void setTransferTimestamp(Date transferTimestamp) {
		this.transferTimestamp = transferTimestamp;
	}

	public Integer getCallCount() {
		return callCount;
	}

	public void setCallCount(Integer callCount) {
		this.callCount = callCount;
	}

	public String getLastCallResult() {
		return lastCallResult;
	}

	public void setLastCallResult(String lastCallResult) {
		this.lastCallResult = lastCallResult;
	}

	public Date getLastCallTime() {
		return lastCallTime;
	}

	public void setLastCallTime(Date lastCallTime) {
		this.lastCallTime = lastCallTime;
	}

	public Integer getEnterCount() {
		return enterCount;
	}

	public void setEnterCount(Integer enterCount) {
		this.enterCount = enterCount;
	}

	public String getIntentType() {
		return intentType;
	}

	public void setIntentType(String intentType) {
		this.intentType = intentType;
	}

	public Date getIntentTimestamp() {
		return intentTimestamp;
	}

	public void setIntentTimestamp(Date intentTimestamp) {
		this.intentTimestamp = intentTimestamp;
	}

	public String getTransferDepartment() {
		return transferDepartment;
	}

	public void setTransferDepartment(String transferDepartment) {
		this.transferDepartment = transferDepartment;
	}
	
	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getOwnUserName() {
		return ownUserName;
	}

	public void setOwnUserName(String ownUserName) {
		this.ownUserName = ownUserName;
	}

	public String getTransferUserName() {
		return transferUserName;
	}

	public void setTransferUserName(String transferUserName) {
		this.transferUserName = transferUserName;
	}

	public String getTransferDeptName() {
		return transferDeptName;
	}

	public void setTransferDeptName(String transferDeptName) {
		this.transferDeptName = transferDeptName;
	}
	
}
