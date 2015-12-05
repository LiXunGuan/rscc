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
@Table(name = "new_data_department_user_")

//批次表
public class UserData extends CommonDbBean {

	public static String tableName = UserData.class.getAnnotation(Table.class).name();
	
	// 数据批次名
	@Index
	@Column(meta = "VARCHAR(32)", column = "batch_uuid")
	private String batchUuid;

	// 电话号码
	@Index(type = IndexDefinition.TYPE_UNIQUE, method = IndexDefinition.METHOD_BTREE)
	@Column(meta = "VARCHAR(20)", column = "phone_number")
	private String phoneNumber;

	// 其他json
	@NColumn(meta = "VARCHAR(1024)", column = "json")
	private String json;

	// 占有部门
	@Index
	@NColumn(meta = "VARCHAR(64)", column = "own_department")
	private String ownDepartment;

	// 占有时间
	@Index
	@NColumn(meta = "DATETIME", column = "own_department_timestamp")
	private Date ownDepartmentTimestamp;

	@Index
	@NColumn(meta = "VARCHAR(64)", column = "own_user")
	private String ownUser;

	@Index
	@NColumn(meta = "DATETIME", column = "own_user_timestamp")
	private Date ownUserTimestamp;

	@NColumn(meta = "INT DEFAULT 0", column = "call_count")
	private Integer callCount;

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
	
	//是否保存过一下，一般用于标记从共享池中来的数据被保存过，暂时保留不适用
	@NColumn(meta = "CHAR(1) DEFAULT '0'", column = "be_audit")
	private String beAudit;
	
	public String getBeAudit() {
		return beAudit;
	}

	public void setBeAudit(String beAudit) {
		this.beAudit = beAudit;
	}

	private String intentTypeName;
	
	public String getIntentTypeName() {
		return intentTypeName;
	}

	public void setIntentTypeName(String intentTypeName) {
		this.intentTypeName = intentTypeName;
	}

	private String batchname;
	
	private String deptname;
	
	private String username;
	
	private String deptUuid;
	
	private String userDepartment;

	public String getDeptUuid() {
		return deptUuid;
	}

	public void setDeptUuid(String deptUuid) {
		this.deptUuid = deptUuid;
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

	private String loginName;

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getBatchname() {
		return batchname;
	}

	public void setBatchname(String batchname) {
		this.batchname = batchname;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserDepartment() {
		return userDepartment;
	}

	public void setUserDepartment(String userDepartment) {
		this.userDepartment = userDepartment;
	}
	
}
