package com.ruishengtech.rscc.crm.datamanager.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "new_data_department_")

//部门数据表
public class DepartmentData extends CommonDbBean implements GroupCallData {
	
	public static String tableName = DepartmentData.class.getAnnotation(Table.class).name();
	
	//从哪个批次取得数据
	@Index
	@Column(meta = "VARCHAR(64)", column = "batch_uuid")
    private String batchUuid;

	//号码
	@Index(type = IndexDefinition.TYPE_UNIQUE, method = IndexDefinition.METHOD_BTREE)
	@Column(meta = "VARCHAR(20)", column = "phone_number")
	private String phoneNumber;
	
	//数据的其他信息
	@NColumn(meta = "VARCHAR(1024)", column = "json")
	private String json;
	
	//当前被哪个部门占用
	@NColumn(meta = "VARCHAR(64)", column = "own_department")
	private String ownDepartment;
	
	//部门占用时间
	@Index
	@NColumn(meta = "TIMESTAMP DEFAULT NOW()", column = "own_department_timestamp")
	private Date ownDepartmentTimestamp;
	
	//是否被群呼锁定
	@NColumn(meta = "CHAR(1) DEFAULT '0'", column = "is_lock")
    private String isLock;
	
	//群呼过程中的状态，用于区分是否已被获取
	@NColumn(meta = "CHAR(1) DEFAULT '0'", column = "call_stat")
    private String callStat;
    
    @NColumn(meta = "DATETIME", column = "lock_timestamp")
    private Date lockTimestamp;
    
    private String batchname;
    
    private String deptname;

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

}
