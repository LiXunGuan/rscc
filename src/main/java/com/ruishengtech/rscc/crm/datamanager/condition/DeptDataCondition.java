package com.ruishengtech.rscc.crm.datamanager.condition;

import java.util.Collection;

import com.ruishengtech.framework.core.db.condition.Page;

public class DeptDataCondition extends Page{

	private Collection<String> ins;
	
	private String batchname;
	
	private String deptname;

	public Collection<String> getIns() {
		return ins;
	}

	public void setIns(Collection<String> ins) {
		this.ins = ins;
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
