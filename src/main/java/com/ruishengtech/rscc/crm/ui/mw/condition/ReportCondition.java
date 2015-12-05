package com.ruishengtech.rscc.crm.ui.mw.condition;

import java.util.Collection;

import com.ruishengtech.framework.core.db.condition.Page;

/**
 * Created by yaoliceng on 2014/11/3.
 */
public class ReportCondition extends Page {
	
	private String selectionReport;
	
	private String stime;
	
	private String etime;
	
	private String name;
	
	private String nameUid;
	
	private Collection<String> agentsList;
	
	private Collection<String> queuesList;
	
	public String getSelectionReport() {
		return selectionReport;
	}
	public void setSelectionReport(String selectionReport) {
		this.selectionReport = selectionReport;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Collection<String> getAgentsList() {
		return agentsList;
	}
	public void setAgentsList(Collection<String> agentsList) {
		this.agentsList = agentsList;
	}
	public String getNameUid() {
		return nameUid;
	}
	public void setNameUid(String nameUid) {
		this.nameUid = nameUid;
	}
	public Collection<String> getQueuesList() {
		return queuesList;
	}
	public void setQueuesList(Collection<String> queuesList) {
		this.queuesList = queuesList;
	}
}
