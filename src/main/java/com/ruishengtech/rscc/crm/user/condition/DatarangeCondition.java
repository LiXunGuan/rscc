package com.ruishengtech.rscc.crm.user.condition;

import com.ruishengtech.framework.core.db.condition.Page;

public class DatarangeCondition extends Page {

	private String datarangeName;

	private String datarangeDescribe;
	
	public String getDatarangeDescribe() {
		return datarangeDescribe;
	}

	public void setDatarangeDescribe(String datarangeDescribe) {
		this.datarangeDescribe = datarangeDescribe;
	}

	public String getDatarangeName() {
		return datarangeName;
	}

	public void setDatarangeName(String datarangeName) {
		this.datarangeName = datarangeName;
	}

	
}
