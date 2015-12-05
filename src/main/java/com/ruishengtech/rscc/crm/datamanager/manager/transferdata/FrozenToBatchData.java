package com.ruishengtech.rscc.crm.datamanager.manager.transferdata;

import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;

public class FrozenToBatchData extends TransferData{
	private DataBatchData dataBatchData;

	public DataBatchData getDataBatchData() {
		return dataBatchData;
	}

	public void setDataBatchData(DataBatchData dataBatchData) {
		this.dataBatchData = dataBatchData;
	}
}
