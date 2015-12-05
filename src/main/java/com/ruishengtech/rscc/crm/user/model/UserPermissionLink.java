package com.ruishengtech.rscc.crm.user.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "user_user_permission_link")

public class UserPermissionLink {
	@Key
	@Column(meta = "VARCHAR(64)", column = "user_uuid")
	private String userUuid;
	
	@Key
	@Column(meta = "VARCHAR(64)", column = "permission_uuid")
	private String permissionUuid;

	public String getUserUuid()
	{
		return userUuid;
	}

	public void setUserUuid(String userUuid)
	{
		this.userUuid = userUuid;
	}

	public String getPermissionUuid() {
		return permissionUuid;
	}

	public void setPermissionUuid(String permissionUuid) {
		this.permissionUuid = permissionUuid;
	}

}
