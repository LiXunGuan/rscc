package com.ruishengtech.rscc.crm.datamanager.condition;

import java.util.Date;

import com.ruishengtech.framework.core.db.condition.Page;

/**
 * @author Frank
 *
 */
public class NewUserDataCondition extends Page{
	
	// 数据批次名
	private String batchUuid;

	// 电话号码
	private String phoneNumber;

	// 其他json
	private String json;

	// 占有部门
	private String ownDepartment;

	// 占有时间
	private Date ownDepartmentTimestamp;

	private String ownUser;

	private Date ownUserTimestamp;

	private Integer callCount;

	private String lastCallResult;

	private Date lastCallTime;

	private String intentType;

	/**
	 * 是否已经呼叫
	 */
	private String callStatus;
	
	public String getCallStatus() {
		return callStatus;
	}

	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}

	private Date intentTimestamp;

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

	public String getOwnDepartment() {
		return ownDepartment;
	}

	public void setOwnDepartment(String ownDepartment) {
		this.ownDepartment = ownDepartment;
	}

	public Date getOwnDepartmentTimestamp() {
		return ownDepartmentTimestamp;
	}

	public void setOwnDepartmentTimestamp(Date ownDepartmentTimestamp) {
		this.ownDepartmentTimestamp = ownDepartmentTimestamp;
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

	public Date getIntentTimestamp() {
		return intentTimestamp;
	}

	public void setIntentTimestamp(Date intentTimestamp) {
		this.intentTimestamp = intentTimestamp;
	}

	public void setIntentType(String intentType) {
		this.intentType = intentType;
	}

	private String batchName;

	private String deptName;

	private String callCounts;

	private String callNoCounts;

	private String loginName;

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getBatchName() {
		return this.batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCallCounts() {
		return this.callCounts;
	}

	public void setCallCounts(String callCounts) {
		this.callCounts = callCounts;
	}

	public String getCallNoCounts() {
		return this.callNoCounts;
	}
	public void setCallNoCounts(String callNoCounts) {
		this.callNoCounts = callNoCounts;
	}
	
	private String detail;

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	
}
