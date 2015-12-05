package com.ruishengtech.rscc.crm.user.condition;

import com.ruishengtech.framework.core.db.condition.Page;

public class GroupCondition extends Page {
	
	private String groupName;

	private String groupDescribe;
	
	
	public String getGroupDescribe() {
		return groupDescribe;
	}

	public void setGroupDescribe(String groupDescribe) {
		this.groupDescribe = groupDescribe;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


}
