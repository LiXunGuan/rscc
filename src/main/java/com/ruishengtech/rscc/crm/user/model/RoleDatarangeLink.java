package com.ruishengtech.rscc.crm.user.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "user_role_datarange_link")

public class RoleDatarangeLink {
	@Key
	@Column(meta = "VARCHAR(64)", column = "role_uuid")
	private String roleUuid;
	
	@Key
	@Column(meta = "VARCHAR(64)", column = "datarange_type")
	private String datarangeType;
	
	@Key
	@Column(meta = "VARCHAR(64)", column = "datarange_uuid")
	private String datarangeUuid;

	public String getRoleUuid() {
		return roleUuid;
	}

	public void setRoleUuid(String roleUuid) {
		this.roleUuid = roleUuid;
	}

	public String getDatarangeType() {
		return datarangeType;
	}

	public void setDatarangeType(String datarangeType) {
		this.datarangeType = datarangeType;
	}

	public String getDatarangeUuid() {
		return datarangeUuid;
	}

	public void setDatarangeUuid(String datarangeUuid) {
		this.datarangeUuid = datarangeUuid;
	}

	

}
