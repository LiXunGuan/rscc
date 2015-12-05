package com.ruishengtech.rscc.crm.datamanager.manager.transferdata;

public class ShareToUserData extends TransferData {
	
	private Integer transferNum;

	private String transferData;
	
	//从哪个批次取数据
	private String batchUuid;
	
	private String transferUser;
	
	public Integer getTransferNum() {
		return transferNum;
	}

	public void setTransferNum(Integer transferNum) {
		this.transferNum = transferNum;
	}

	public String getTransferData() {
		return transferData;
	}

	public void setTransferData(String transferData) {
		this.transferData = transferData;
	}

	public String getBatchUuid() {
		return batchUuid;
	}

	public void setBatchUuid(String batchUuid) {
		this.batchUuid = batchUuid;
	}

	public String getTransferUser() {
		return transferUser;
	}

	public void setTransferUser(String transferUser) {
		this.transferUser = transferUser;
	}

}
