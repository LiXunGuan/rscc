package com.ruishengtech.rscc.crm.user.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "user_datarangerole_datarange_link")

public class DatarangeRoleDatarangeLink {
	@Key
	@Column(meta = "VARCHAR(64)", column = "datarangerole_uuid")
	private String datarangeRoleUuid;
	
	@Key
	@Column(meta = "VARCHAR(64)", column = "datarange_uuid")
	private String datarangeUuid;

	public String getDatarangeRoleUuid() {
		return datarangeRoleUuid;
	}

	public void setDatarangeRoleUuid(String datarangeRoleUuid) {
		this.datarangeRoleUuid = datarangeRoleUuid;
	}

	public String getDatarangeUuid() {
		return datarangeUuid;
	}

	public void setDatarangeUuid(String datarangeUuid) {
		this.datarangeUuid = datarangeUuid;
	}
}
