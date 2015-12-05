package com.ruishengtech.rscc.crm.datamanager.condition;

import com.ruishengtech.framework.core.db.condition.Page;

public class DataGlobalShareRecordCondition extends Page{

	private String useruuid;
	
	private String deptuuid;

	public String getUseruuid() {
		return useruuid;
	}

	public void setUseruuid(String useruuid) {
		this.useruuid = useruuid;
	}

	public String getDeptuuid() {
		return deptuuid;
	}

	public void setDeptuuid(String deptuuid) {
		this.deptuuid = deptuuid;
	}

}
