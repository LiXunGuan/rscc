package com.ruishengtech.rscc.crm.user.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;


@Table(name = "user_group_datarangerole_link")

public class GroupDatarangeRoleLink {
	@Key
	@Column(meta = "VARCHAR(64)", column = "group_uuid")
	private String groupUuid;
	
	@Key
	@Column(meta = "VARCHAR(64)", column = "datarangerole_uuid")
	private String datarangeRoleUuid;



	public String getGroupUuid() {
		return groupUuid;
	}

	public void setGroupUuid(String groupUuid) {
		this.groupUuid = groupUuid;
	}

	public String getDatarangeUuid() {
		return datarangeRoleUuid;
	}

	public void setDatarangeUuid(String datarangeRoleUuid) {
		this.datarangeRoleUuid = datarangeRoleUuid;
	}


}
