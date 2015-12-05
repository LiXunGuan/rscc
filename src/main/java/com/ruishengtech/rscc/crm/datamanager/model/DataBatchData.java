package com.ruishengtech.rscc.crm.datamanager.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * @author Wangyao
 *
 */
@Table(name = "new_data_batch_")

//批次表

public class DataBatchData extends CommonDbBean { 

	public static String tableName = DataBatchData.class.getAnnotation(Table.class).name();
	
	//数据批次id
	@Column(meta = "VARCHAR(32)", column = "batch_uuid")
    private String batchUuid;
	
    //电话号码
	@Index(name = "phoneNumber", type = IndexDefinition.TYPE_NORMAL, method = IndexDefinition.METHOD_HASH)
	@Column(meta = "VARCHAR(20)", column = "phone_number")
    private String phoneNumber;
	
	//本地保存路径
	@NColumn(meta = "VARCHAR(1024)", column = "json")
	private String json;
	
	//已领用部门
	@Index
	@NColumn(meta = "VARCHAR(64)", column = "own_department")
	private String ownDepartment;
	
	//部门领用时间
	@Index
	@NColumn(meta = "DATETIME", column = "own_department_timestamp")
	private Date ownDepartmentTimestamp;
	
	//部门分配给个人的名字
	@Index
	@NColumn(meta = "VARCHAR(64)", column = "own_user")
	private String ownUser;
	
	//部门分配给个人的时间
	@Index
	@NColumn(meta = "DATETIME", column = "own_user_timestamp")
	private Date ownUserTimestamp;

	@NColumn(meta = "INT DEFAULT 0", column = "call_count")
	private Integer callCount;

	@NColumn(meta = "VARCHAR(64)", column = "last_call_department")
	private String lastCallDepartment;

	@NColumn(meta = "VARCHAR(64)", column = "last_call_user")
	private String lastCallUser;
	
	@NColumn(meta = "VARCHAR(64)", column = "last_call_result")
	private String lastCallResult;
	
	@Index
	@NColumn(meta = "DATETIME", column = "last_call_time")
	private Date lastCallTime;
	
	@Index
	@NColumn(meta = "VARCHAR(64)", column = "intent_type")
	private String intentType;
	
	@Index
	@NColumn(meta = "DATETIME", column = "intent_timestamp")
	private Date intentTimestamp;
	
	//是否被群呼锁定
	@NColumn(meta = "CHAR(1) DEFAULT '0'", column = "is_lock")
    private String isLock;
    
	@Index
    @NColumn(meta = "DATETIME", column = "lock_timestamp")
    private Date lockTimestamp;
	
    //是否废弃
    @NColumn(meta = "CHAR(1) DEFAULT '0'", column = "is_abandon")
    private String isAbandon;
    
    @Index
    @NColumn(meta = "DATETIME", column = "abandon_timestamp")
    private Date abandonTimestamp;
    
    //是否黑名单
    @NColumn(meta = "CHAR(1) DEFAULT '0'", column = "is_blacklist")
    private String isBlacklist;
    
    @Index
    @NColumn(meta = "DATETIME", column = "blacklist_timestamp")
    private Date blacklistimestamp;

    //是否冷冻
    @NColumn(meta = "CHAR(1) DEFAULT '0'", column = "is_frozen")
    private String isFrozen;
    
    @Index
    @NColumn(meta = "DATETIME", column = "frozen_timestamp")
    private Date frozenTimestamp;
    
    //保存为客户时的uuid
    @Index
    @NColumn(meta = "VARCHAR(32)", column = "customer_uuid")
    private String customerUuid;
    
    private String dataBatchName;
    
    private String deptName;
    
    private String userName;

	public String getDataBatchName() {
		return dataBatchName;
	}

	public void setDataBatchName(String dataBatchName) {
		this.dataBatchName = dataBatchName;
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

	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	public Date getLockTimestamp() {
		return lockTimestamp;
	}

	public void setLockTimestamp(Date lockTimestamp) {
		this.lockTimestamp = lockTimestamp;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
