package com.ruishengtech.rscc.crm.billing.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "billrate_sipuser")
public class BillRateSipuserLink {
	
	/**
	 * 费率UUID
	 */
	@Key
	@Column(meta="VARCHAR(64)",column="billrate_uuid")
	private String billRateUUID;
	
	/**
	 * 分机ID
	 */
	@Key
	@Column(meta="VARCHAR(64)",column="sipuser_id")
	private String sipuserID;
	
	public String getBillRateUUID() {
		return billRateUUID;
	}

	public void setBillRateUUID(String billRateUUID) {
		this.billRateUUID = billRateUUID;
	}

	public String getSipuserID() {
		return sipuserID;
	}

	public void setSipuserID(String sipuserID) {
		this.sipuserID = sipuserID;
	}
	
}
