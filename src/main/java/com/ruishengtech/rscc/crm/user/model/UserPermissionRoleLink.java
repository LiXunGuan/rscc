package com.ruishengtech.rscc.crm.user.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "user_user_permissionrole_link")
public class UserPermissionRoleLink {
	@Key
	@Column(meta = "VARCHAR(64)", column = "user_uuid")
	private String userUuid;
	
	@Key
	@Column(meta = "VARCHAR(64)", column = "permissionrole_uuid")
	private String permissionRoleUuid;

	public String getUserUuid()
	{
		return userUuid;
	}

	public void setUserUuid(String userUuid)
	{
		this.userUuid = userUuid;
	}

	public String getPermissionRoleUuid() {
		return permissionRoleUuid;
	}

	public void setPermissionRoleUuid(String permissionRoleUuid) {
		this.permissionRoleUuid = permissionRoleUuid;
	}

}
