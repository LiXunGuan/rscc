package com.ruishengtech.rscc.crm.user.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "user_user_datarangerole_link")

public class UserDatarangeRoleLink {
	@Key
	@Column(meta = "VARCHAR(64)", column = "user_uuid")
	private String userUuid;
	
	@Key
	@Column(meta = "VARCHAR(64)", column = "datarangerole_uuid")
	private String datarangeRoleUuid;

	public String getUserUuid()
	{
		return userUuid;
	}

	public void setUserUuid(String userUuid)
	{
		this.userUuid = userUuid;
	}

	public String getDatarangeUuid() {
		return datarangeRoleUuid;
	}

	public void setDatarangeUuid(String datarangeRoleUuid) {
		this.datarangeRoleUuid = datarangeRoleUuid;
	}


}
