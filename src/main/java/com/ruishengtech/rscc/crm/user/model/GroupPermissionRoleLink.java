package com.ruishengtech.rscc.crm.user.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "user_group_permissionrole_link")
public class GroupPermissionRoleLink {
	@Key
	@Column(meta = "VARCHAR(64)", column = "group_uuid")
	private String groupUuid;
	
	@Key
	@Column(meta = "VARCHAR(64)", column = "permissionrole_uuid")
	private String permissionRoleUuid;

	public String getGroupUuid() {
		return groupUuid;
	}

	public void setGroupUuid(String groupUuid) {
		this.groupUuid = groupUuid;
	}

	public String getPermissionRoleUuid() {
		return permissionRoleUuid;
	}

	public void setPermissionRoleUuid(String permissionRoleUuid) {
		this.permissionRoleUuid = permissionRoleUuid;
	}

}
