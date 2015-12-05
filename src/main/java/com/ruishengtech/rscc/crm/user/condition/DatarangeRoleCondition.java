package com.ruishengtech.rscc.crm.user.condition;

import com.ruishengtech.framework.core.db.condition.Page;

public class DatarangeRoleCondition extends Page {
	
	private String datarangeRoleName;

	private String datarangeRoleDescribe;
	
	public String getDatarangeRoleDescribe() {
		return datarangeRoleDescribe;
	}

	public void setDatarangeRoleDescribe(String datarangeRoleDescribe) {
		this.datarangeRoleDescribe = datarangeRoleDescribe;
	}

	public String getDatarangeRoleName() {
		return datarangeRoleName;
	}

	public void setDatarangeRoleName(String datarangeRoleName) {
		this.datarangeRoleName = datarangeRoleName;
	}
	

}
