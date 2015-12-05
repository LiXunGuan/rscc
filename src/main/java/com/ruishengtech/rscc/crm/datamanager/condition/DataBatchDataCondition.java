package com.ruishengtech.rscc.crm.datamanager.condition;

import java.util.Date;

import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.framework.core.db.condition.Page;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;

public class DataBatchDataCondition extends Page{
public static String tableName = DataBatchData.class.getAnnotation(Table.class).name();
	
	//数据批次id
    private String batchUuid;
	
    //电话号码
    private String phoneNumber;
	
	//本地保存路径
	private String json;
	
	//已领用部门
	private String ownDepartment;
	
	//部门领用时间
	private Date ownDepartmentTimestamp;
	
	//领用人
	private String ownUser;
	
	private Date ownUserTimestamp;

	private Integer callCount;

	private String lastCallDepartment;

	private String lastCallUser;
	
	private String lastCallResult;
	
	private Date lastCallTime;
	
	private String intentType;
	
	private Date intentTimestamp;
	
    //是否废弃
    private String isAbandon;
    
    private Date abandonTimestamp;
    
    //是否黑名单
    private String isBlacklist;
    
    private Date blacklistimestamp;

    //是否冷冻
    private String isFrozen;
    
    private Date frozenTimestamp;
    
    //保存为客户时的uuid
    private String customerUuid;
    
    private String operation;

	public static String getTableName() {
		return tableName;
	}

	public static void setTableName(String tableName) {
		DataBatchDataCondition.tableName = tableName;
	}

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

	public String getLastCallDepartment() {
		return lastCallDepartment;
	}

	public void setLastCallDepartment(String lastCallDepartment) {
		this.lastCallDepartment = lastCallDepartment;
	}

	public String getLastCallUser() {
		return lastCallUser;
	}

	public void setLastCallUser(String lastCallUser) {
		this.lastCallUser = lastCallUser;
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

	public String getIsAbandon() {
		return isAbandon;
	}

	public void setIsAbandon(String isAbandon) {
		this.isAbandon = isAbandon;
	}

	public Date getAbandonTimestamp() {
		return abandonTimestamp;
	}

	public void setAbandonTimestamp(Date abandonTimestamp) {
		this.abandonTimestamp = abandonTimestamp;
	}

	public String getIsBlacklist() {
		return isBlacklist;
	}

	public void setIsBlacklist(String isBlacklist) {
		this.isBlacklist = isBlacklist;
	}

	public Date getBlacklistimestamp() {
		return blacklistimestamp;
	}

	public void setBlacklistimestamp(Date blacklistimestamp) {
		this.blacklistimestamp = blacklistimestamp;
	}

	public String getIsFrozen() {
		return isFrozen;
	}

	public void setIsFrozen(String isFrozen) {
		this.isFrozen = isFrozen;
	}

	public Date getFrozenTimestamp() {
		return frozenTimestamp;
	}

	public void setFrozenTimestamp(Date frozenTimestamp) {
		this.frozenTimestamp = frozenTimestamp;
	}

	public String getCustomerUuid() {
		return customerUuid;
	}

	public void setCustomerUuid(String customerUuid) {
		this.customerUuid = customerUuid;
	}
    
	private String detail;

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
    
}
