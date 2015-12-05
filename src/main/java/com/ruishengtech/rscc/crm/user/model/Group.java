package com.ruishengtech.rscc.crm.user.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "user_group")

public class Group extends CommonDbBean { 

    @Column(meta = "VARCHAR(64)", column = "group_name")
    private String groupName;

    @Column(meta = "VARCHAR(500)", column = "group_describe")
    private String groupDescribe;
    
	@Column(meta = "VARCHAR(64)", column = "parent_uuid")
    private String parentUuid;
    
	@Column(meta = "DATETIME")
	private Date date;
	
	public String getGroupDescribe()
	{
		return groupDescribe;
	}

	public void setGroupDescribe(String groupDescribe)
	{
		this.groupDescribe = groupDescribe;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getParentUuid() {
		return parentUuid;
	}

	public void setParentUuid(String parentUuid) {
		this.parentUuid = parentUuid;
	}

	
}