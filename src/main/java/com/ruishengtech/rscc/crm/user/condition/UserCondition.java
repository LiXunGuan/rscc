package com.ruishengtech.rscc.crm.user.condition;

import java.util.Collection;
import java.util.List;

import com.ruishengtech.framework.core.db.condition.Page;

public class UserCondition extends Page {
	/**
	 * 查询的姓名
	 */
	private String loginName;
	
	private String phone;
	
	private String role;
	
	private String userDescribe;

	private String departmentName;
	
	private Collection<String> ins;
	
	private List<String> uuids;
	
	public List<String> getUuids() {
		return uuids;
	}

	public void setUuids(List<String> uuids) {
		this.uuids = uuids;
	}

	public Collection<String> getIns() {
		return ins;
	}

	public void setIns(Collection<String> ins) {
		this.ins = ins;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserDescribe() {
		return userDescribe;
	}

	public void setUserDescribe(String userDescribe) {
		this.userDescribe = userDescribe;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


}
