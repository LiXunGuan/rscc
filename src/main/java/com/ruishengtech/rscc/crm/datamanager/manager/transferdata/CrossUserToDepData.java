package com.ruishengtech.rscc.crm.datamanager.manager.transferdata;

public class CrossUserToDepData extends TransferData {
	
	private Integer[] transferMod;

	//从哪个批次取数据
	private String batchUuid;
	
	private String userDept;
	
	public Integer[] getTransferMod() {
		return transferMod;
	}

	public void setTransferMod(Integer[] transferMod) {
		this.transferMod = transferMod;
	}

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
