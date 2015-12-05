package com.ruishengtech.rscc.crm.datamanager.manager.transferdata;

import java.util.Arrays;
import java.util.Collection;

public class MultiTransferData extends TransferData {
	
	protected Collection<String> targetTables;

	public Collection<String> getTargetTables() {
		return targetTables;
	}


	public void setTargetTables(Collection<String> targetTables) {
		this.targetTables = targetTables;
	}

	public void setTargetTables(String[] tables) {
		this.targetTables = Arrays.asList(tables);
	}
	
}
