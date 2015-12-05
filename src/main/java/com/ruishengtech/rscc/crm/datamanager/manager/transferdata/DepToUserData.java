package com.ruishengtech.rscc.crm.datamanager.manager.transferdata;

public class DepToUserData extends TransferData {
	
	private Integer transferNum;

	//从哪个批次取数据
	private String batchUuid;
	
	public Integer getTransferNum() {
		return transferNum;
	}

	public void setTransferNum(Integer transferNum) {
		this.transferNum = transferNum;
	}

	public String getBatchUuid() {
		return batchUuid;
	}

	public void setBatchUuid(String batchUuid) {
		this.batchUuid = batchUuid;
	}
}
