package com.ruishengtech.rscc.crm.datamanager.condition;

import java.util.Collection;

import com.ruishengtech.framework.core.db.condition.Page;

public class UserTaskCondition extends Page{
	
	private String username;
	
	private String loginname;
	
	private String deptname;
	
	private Collection<String> ins;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public Collection<String> getIns() {
		return ins;
	}

	public void setIns(Collection<String> ins) {
		this.ins = ins;
	}
		
}
