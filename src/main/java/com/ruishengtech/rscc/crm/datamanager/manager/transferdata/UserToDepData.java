package com.ruishengtech.rscc.crm.datamanager.manager.transferdata;

public class UserToDepData extends TransferData {
	
	private Integer[] transferMod;

	//从哪个批次取数据
	private String batchUuid;
	
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

}
