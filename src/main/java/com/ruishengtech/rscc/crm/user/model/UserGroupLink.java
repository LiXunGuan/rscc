package com.ruishengtech.rscc.crm.user.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "user_user_group_link")

public class UserGroupLink {
	@Key
	@Column(meta = "VARCHAR(64)", column = "user_uuid")
	private String userUuid;
	
	@Key
	@Column(meta = "VARCHAR(64)", column = "group_uuid")
	private String groupUuid;

	public String getUserUuid()
	{
		return userUuid;
	}

	public void setUserUuid(String userUuid)
	{
		this.userUuid = userUuid;
	}

	public String getGroupUuid()
	{
		return groupUuid;
	}

	public void setGroupUuid(String groupUuid)
	{
		this.groupUuid = groupUuid;
	}
}
