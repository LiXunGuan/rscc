package com.ruishengtech.rscc.crm.datamanager.manager.transferdata;

public class CrossUserToUserData extends TransferData {
	
	//从哪个批转移数据
	private String batchUuid;
	
	private String userDept;
	
	public String getBatchUuid() {
		return batchUuid;
	}

	public void setBatchUuid(String batchUuid) {
		this.batchUuid = batchUuid;
	}

	public String getUserDept() {
		return userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

}
