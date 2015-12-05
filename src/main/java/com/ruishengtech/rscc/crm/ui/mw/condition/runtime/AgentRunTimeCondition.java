package com.ruishengtech.rscc.crm.ui.mw.condition.runtime;

import com.ruishengtech.framework.core.db.condition.Page;

/**
 * Created by yaoliceng on 2014/11/3.
 */
public class AgentRunTimeCondition extends Page {
	
	private String uid;
	
	private String extension;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
}
