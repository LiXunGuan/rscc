package com.ruishengtech.rscc.crm.user.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "user_permissionrole_permission_link")

public class PermissionRolePermissionLink {
	
	@Key
	@Column(meta = "VARCHAR(64)", column = "permissionrole_uuid")
	private String permissionRoleUuid;
	
	@Key
	@Column(meta = "VARCHAR(64)", column = "permission_uuid")
	private String permissionUuid;

	public String getPermissionRoleUuid() {
		return permissionRoleUuid;
	}

	public void setPermissionRoleUuid(String permissionRoleUuid) {
		this.permissionRoleUuid = permissionRoleUuid;
	}

	public String getPermissionUuid() {
		return permissionUuid;
	}

	public void setPermissionUuid(String permissionUuid) {
		this.permissionUuid = permissionUuid;
	}
}
