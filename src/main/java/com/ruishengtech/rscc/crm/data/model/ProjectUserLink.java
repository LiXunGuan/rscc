package com.ruishengtech.rscc.crm.data.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "data_project_user_link")

public class ProjectUserLink { 

	@Key
	@Column(meta = "VARCHAR(64)", column = "user_uuid")
	private String userUuid;
	
	@Column(meta = "VARCHAR(64)", column = "user_name")
	private String userName;

	@Column(meta = "VARCHAR(64)", column = "project_uuid")
    private String projectUuid;
	
	public String getUser_uuid() {
		return userUuid;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public String getProjectUuid() {
		return projectUuid;
	}

	public void setProjectUuid(String projectUuid) {
		this.projectUuid = projectUuid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
