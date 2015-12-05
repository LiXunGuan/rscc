package com.ruishengtech.rscc.crm.user.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "user_user_datarange_link")

public class UserDatarangeLink {
	@Key
	@Column(meta = "VARCHAR(64)", column = "user_uuid")
	private String userUuid;

	@Key
	@Column(meta = "VARCHAR(64)", column = "datarange_type")
	private String datarangeType;
	
	@Key
	@Column(meta = "VARCHAR(64)", column = "datarange_uuid")
	private String datarangeUuid;

	public String getUserUuid()
	{
		return userUuid;
	}

	public void setUserUuid(String userUuid)
	{
		this.userUuid = userUuid;
	}

	public String getDatarangeUuid() {
		return datarangeUuid;
	}

	public void setDatarangeUuid(String datarangeUuid) {
		this.datarangeUuid = datarangeUuid;
	}

	public String getDatarangeType() {
		return datarangeType;
	}

	public void setDatarangeType(String datarangeType) {
		this.datarangeType = datarangeType;
	}

}
