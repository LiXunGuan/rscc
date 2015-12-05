package com.ruishengtech.rscc.crm.datamanager.condition;

import java.util.List;

import com.ruishengtech.framework.core.db.condition.Page;
import com.ruishengtech.rscc.crm.datamanager.model.DepartmentTable;

public class DepartmentDataCondition extends Page{

	private String deptUuid;
	
	private String batchUuid;
	
	private String userID;
	
	private String lastCallResult;
	
	private List<DepartmentTable> allDept;
	
	public List<DepartmentTable> getAllDept() {
		return allDept;
	}

	public void setAllDept(List<DepartmentTable> allDept) {
		this.allDept = allDept;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

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

	public String getLastCallResult() {
		return lastCallResult;
	}

	public void setLastCallResult(String lastCallResult) {
		this.lastCallResult = lastCallResult;
	}
}
