package com.ruishengtech.rscc.crm.datamanager.manager.transferdata;

public class BatchToUserData extends TransferData {
	
	private Integer transferNum;

	//到人有两个值要知道，一个是人所在的部门，因为人是以部门为表名的，二是人的uuid，因为要分配给人
	private String transferUser;
	
	public Integer getTransferNum() {
		return transferNum;
	}

	public void setTransferNum(Integer transferNum) {
		this.transferNum = transferNum;
	}

	public String getTransferUser() {
		return transferUser;
	}

	public void setTransferUser(String transferUser) {
		this.transferUser = transferUser;
	}

}
