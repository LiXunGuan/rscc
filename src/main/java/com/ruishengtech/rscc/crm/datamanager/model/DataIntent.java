package com.ruishengtech.rscc.crm.datamanager.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "new_data_intent")
public class DataIntent extends CommonDbBean{
	
	@Column(meta = "VARCHAR(64)", column = "intent_name")
	private String intentName;
	
	@NColumn(meta = "VARCHAR(64)", column = "intent_info")
	private String intentInfo;

	@Column(meta = "SERIAL")
	private String seq;
	
	
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getIntentName() {
		return intentName;
	}

	public void setIntentName(String intentName) {
		this.intentName = intentName;
	}

	public String getIntentInfo() {
		return intentInfo;
	}

	public void setIntentInfo(String intentInfo) {
		this.intentInfo = intentInfo;
	}
	
}
