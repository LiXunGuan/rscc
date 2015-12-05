package com.ruishengtech.rscc.crm.user.condition;

import com.ruishengtech.framework.core.db.condition.Page;

public class PermissionRoleCondition extends Page {
	
	private String permissionRoleName;

	private String permissionRoleDescribe;
	
	public String getPermissionRoleDescribe() {
		return permissionRoleDescribe;
	}

	public void setPermissionRoleDescribe(String permissionRoleDescribe) {
		this.permissionRoleDescribe = permissionRoleDescribe;
	}

	public String getPermissionRoleName() {
		return permissionRoleName;
	}

	public void setPermissionRoleName(String permissionRoleName) {
		this.permissionRoleName = permissionRoleName;
	}
	
}
