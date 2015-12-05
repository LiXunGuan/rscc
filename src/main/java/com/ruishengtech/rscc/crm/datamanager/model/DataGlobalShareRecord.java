package com.ruishengtech.rscc.crm.datamanager.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "new_data_global_share_record")

public class DataGlobalShareRecord extends CommonDbBean {
	
	public static String tableName = DataGlobalShareRecord.class.getAnnotation(Table.class).name();
	
	// 共享池UUID
	@Index
	@Column(meta = "VARCHAR(64)", column = "globalshare_uuid")
    private String globalShareUuid;
	
	// 当前谁获取
	@Index
	@Column(meta = "VARCHAR(64)", column = "own_user")
	private String ownUser;

	// 号码
	@Index
	@Column(meta = "VARCHAR(20)", column = "phone_number")
	private String phoneNumber;
	
	// 获取时间
	@Index
	@Column(meta = "DATETIME", column = "own_user_timestamp")
	private Date ownUserTimestamp;
	
	private String batchName;

	private String json;
	
	// 占有部门
	private String deptName;
	
	// 部门占有时间
	private Date ownDepartmentTimestamp;
	
	private Integer callCount;
	
	private String lastCallResult;
	
	private Date lastCallTime;
	
	private String intentType;
	
	private Date intentTimestamp;
	
	/**
	 * 标记是否保存过数据
	 * y 已保存过
	 * n 未保存
	 */
	@Column(meta = "CHAR(1)", column = "mark_save")
    private String markSave;
	
	/**
	 * 已保存过的记录
	 */
	public static String Y = "1";
    
	/**
	 * 未保存过的记录
	 */
    public static String N = "0";
    
	public String getGlobalShareUuid() {
		return globalShareUuid;
	}

	public void setGlobalShareUuid(String globalShareUuid) {
		this.globalShareUuid = globalShareUuid;
	}

	public String getOwnUser() {
		return ownUser;
	}

	public void setOwnUser(String ownUser) {
		this.ownUser = ownUser;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getOwnUserTimestamp() {
		return ownUserTimestamp;
	}

	public void setOwnUserTimestamp(Date ownUserTimestamp) {
		this.ownUserTimestamp = ownUserTimestamp;
	}

	public String getMarkSave() {
		return markSave;
	}

	public void setMarkSave(String markSave) {
		this.markSave = markSave;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Date getOwnDepartmentTimestamp() {
		return ownDepartmentTimestamp;
	}

	public void setOwnDepartmentTimestamp(Date ownDepartmentTimestamp) {
		this.ownDepartmentTimestamp = ownDepartmentTimestamp;
	}

}
