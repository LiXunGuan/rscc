package com.ruishengtech.rscc.crm.report.condition;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.condition.Page;

public class ReportDataBatchCondition extends Page{
	
	private Long id;
	
	private Date optime;
	
	private String agent;
		
	//客户新增数量
	private int cstmaddcount;
	
	//客户减少数量
	private int cstmdelcount;
	
	//意向新增量
	private int intentaddcount;
	
	//意向减少量
	private int intentdelcount;

	private int callcount;
	
	private int cstmcount;
	
	private int intentcount;
	
	private int globalsharecount;
	
	private int noanswercount;
	
	private int blacklistcount;
	
	private int abandoncount;
	
	private String startTime;
	
	private String endTime;
	
	private String selectionReport;
	
	private Collection<String> agents;
	
	//登录用户权限下的所有坐席
	private Collection<String> showagents;
	
	private String loginuser;
	
	
	public Collection<String> getShowagents() {
		return showagents;
	}

	public void setShowagents(Collection<String> showagents) {
		this.showagents = showagents;
	}

	public String getLoginuser() {
		return loginuser;
	}

	public void setLoginuser(String loginuser) {
		this.loginuser = loginuser;
	}

	public String getSelectionReport() {
		return selectionReport;
	}

	public void setSelectionReport(String selectionReport) {
		this.selectionReport = selectionReport;
	}
	
	public Collection<String> getAgents() {
		return agents;
	}

	public void setAgents(Collection<String> agents) {
		this.agents = agents;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public int getCstmaddcount() {
		return cstmaddcount;
	}

	public void setCstmaddcount(int cstmaddcount) {
		this.cstmaddcount = cstmaddcount;
	}

	public int getCstmdelcount() {
		return cstmdelcount;
	}

	public void setCstmdelcount(int cstmdelcount) {
		this.cstmdelcount = cstmdelcount;
	}

	public int getIntentaddcount() {
		return intentaddcount;
	}

	public void setIntentaddcount(int intentaddcount) {
		this.intentaddcount = intentaddcount;
	}

	public int getIntentdelcount() {
		return intentdelcount;
	}

	public void setIntentdelcount(int intentdelcount) {
		this.intentdelcount = intentdelcount;
	}

	public int getCallcount() {
		return callcount;
	}

	public void setCallcount(int callcount) {
		this.callcount = callcount;
	}

	public int getCstmcount() {
		return cstmcount;
	}

	public void setCstmcount(int cstmcount) {
		this.cstmcount = cstmcount;
	}

	public int getIntentcount() {
		return intentcount;
	}

	public void setIntentcount(int intentcount) {
		this.intentcount = intentcount;
	}

	public int getGlobalsharecount() {
		return globalsharecount;
	}

	public void setGlobalsharecount(int globalsharecount) {
		this.globalsharecount = globalsharecount;
	}

	public int getNoanswercount() {
		return noanswercount;
	}

	public void setNoanswercount(int noanswercount) {
		this.noanswercount = noanswercount;
	}

	public int getBlacklistcount() {
		return blacklistcount;
	}

	public void setBlacklistcount(int blacklistcount) {
		this.blacklistcount = blacklistcount;
	}

	public int getAbandoncount() {
		return abandoncount;
	}

	public void setAbandoncount(int abandoncount) {
		this.abandoncount = abandoncount;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
