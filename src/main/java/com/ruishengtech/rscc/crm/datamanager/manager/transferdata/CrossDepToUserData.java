package com.ruishengtech.rscc.crm.datamanager.manager.transferdata;

public class CrossDepToUserData extends TransferData {
	
	//从哪个批次取数据
	private String batchUuid;
	
	private String userDept;
	
	private Integer transferNum;

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

	public Integer getTransferNum() {
		return transferNum;
	}

	public void setTransferNum(Integer transferNum) {
		this.transferNum = transferNum;
	}
	
}
