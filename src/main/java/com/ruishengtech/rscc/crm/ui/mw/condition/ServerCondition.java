package com.ruishengtech.rscc.crm.ui.mw.condition;

import com.ruishengtech.framework.core.db.condition.Page;

/**
 * Created by yaoliceng on 2014/11/3.
 */
public class ServerCondition extends Page {
	private Integer serverStrat;
	private Integer serverEnd;
	private String name;
	
	public Integer getServerStrat() {
		return serverStrat;
	}
	public void setServerStrat(Integer serverStrat) {
		this.serverStrat = serverStrat;
	}
	public Integer getServerEnd() {
		return serverEnd;
	}
	public void setServerEnd(Integer serverEnd) {
		this.serverEnd = serverEnd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
