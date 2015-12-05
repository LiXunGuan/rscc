package com.ruishengtech.rscc.crm.datamanager.manager.transferdata;

import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;

public class AbandonToBatchData extends TransferData {
	
	//把dataBatchData存进来，可以直接取到transferUser和transferPhone
	private DataBatchData dataBatchData;

	public DataBatchData getDataBatchData() {
		return dataBatchData;
	}

	public void setDataBatchData(DataBatchData dataBatchData) {
		this.dataBatchData = dataBatchData;
	}
	
}
