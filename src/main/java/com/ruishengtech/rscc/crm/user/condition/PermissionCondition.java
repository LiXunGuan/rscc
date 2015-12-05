package com.ruishengtech.rscc.crm.user.condition;

import com.ruishengtech.framework.core.db.condition.Page;

public class PermissionCondition extends Page {

	private String permissionName;

	private String permissionDescribe;
	
	public String getPermissionDescribe() {
		return permissionDescribe;
	}

	public void setPermissionDescribe(String permissionDescribe) {
		this.permissionDescribe = permissionDescribe;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

}
