package com.ruishengtech.rscc.crm.data.condition;

import com.ruishengtech.framework.core.db.condition.Page;

/**
 * @author Wangyao
 *
 */
public class ItemCondition extends Page {

	private String itemTable;

	private String itemName;

    private String itemPhone;

    private String itemAddress;
	
	private String itemJson;
	
	private String itemOwner;
	
	private String callTimes;
	
	private String dataFrom;
	
	public String getDataFrom() {
		return dataFrom;
	}

	public void setDataFrom(String dataFrom) {
		this.dataFrom = dataFrom;
	}

	public String getItemOwner() {
		return itemOwner;
	}

	public void setItemOwner(String itemOwner) {
		this.itemOwner = itemOwner;
	}

	public String getCallTimes() {
		return callTimes;
	}

	public void setCallTimes(String callTimes) {
		this.callTimes = callTimes;
	}

	public String getItemTable() {
		return itemTable;
	}
	
	public void setItemTable(String itemTable) {
		this.itemTable = itemTable;
	}
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemPhone() {
		return itemPhone;
	}

	public void setItemPhone(String itemPhone) {
		this.itemPhone = itemPhone;
	}

	public String getItemJson() {
		return itemJson;
	}

	public void setItemJson(String itemJson) {
		this.itemJson = itemJson;
	}

	public String getItemAddress() {
		return itemAddress;
	}

	public void setItemAddress(String itemAddress) {
		this.itemAddress = itemAddress;
	}

}
