package com.ruishengtech.rscc.crm.data.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "data_group_call_user_link")

public class GroupCallUserLink { 

	@Key
	@Column(meta = "VARCHAR(64)", column = "groupcall_id")
	private String groupcall_id;
	
	@Key
	@Column(meta = "VARCHAR(64)", column = "user_uuid")
	private String user_uuid;

	public String getGroupcall_id() {
		return groupcall_id;
	}

	public void setGroupcall_id(String groupcall_id) {
		this.groupcall_id = groupcall_id;
	}

	public String getUser_uuid() {
		return user_uuid;
	}

	public void setUser_uuid(String user_uuid) {
		this.user_uuid = user_uuid;
	}

	
	
}
