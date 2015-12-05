package com.ruishengtech.rscc.crm.report.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

@Table(name="report_agentcall")
public class ReportAgentCall {
	
	@Key
	@Column(meta = "SERIAL")
	private Long id;
	
	//坐席id
	@Column(meta="VARCHAR(64)",column="agent_id")
	private String agentid;
	
	//坐席姓名
	private String agentname;
	
	//通话类型
	private String calltype;
	
	//报表类型
	private String selectionReport;
	
	//开始时间
	private String starttime;
	
	//结束时间
	private String endtime;
	
	//呼叫时间
	private Date calltime;
	
	/*****************呼出统计**************/
	
	//呼出次数
	private String outcallcount_p1;
	
	//呼出通话时长
	private String outcallduration_p1;
	
	//呼出次数
	private String outcallcount_p2;
	
	//呼出通话时长
	private String outcallduration_p2;
	
	//呼出次数
	private String outcallcount_p3;
	
	//呼出通话时长
	private String outcallduration_p3;
	
	//呼出次数
	private String outcallcount_p4;
	
	//呼出通话时长
	private String outcallduration_p4;
	
	//呼出次数
	private String outcallcount;
	
	//呼出通话时长
	private String outcallduration;
	
	
	/*******************呼入统计***************/
	
	//呼入次数
	private String incallcount_p1;
	
	//呼入通话时长
	private String incallduration_p1;
	
	//呼入次数
	private String incallcount_p2;
	
	//呼入通话时长
	private String incallduration_p2;
	
	//呼入次数
	private String incallcount_p3;
	
	//呼入通话时长
	private String incallduration_p3;
	
	//呼入次数
	private String incallcount_p4;
	
	//呼入通话时长
	private String incallduration_p4;

	//呼入次数
	private String incallcount;
	
	//呼入通话时长
	private String incallduration;
	
	
	
	//呼出统计时长
	private String outduration;
	
	//呼入统计时长
	private String induration;

	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	public String getAgentname() {
		return agentname;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}

	public String getCalltype() {
		return calltype;
	}

	public void setCalltype(String calltype) {
		this.calltype = calltype;
	}

	public String getSelectionReport() {
		return selectionReport;
	}

	public void setSelectionReport(String selectionReport) {
		this.selectionReport = selectionReport;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public Date getCalltime() {
		return calltime;
	}

	public void setCalltime(Date calltime) {
		this.calltime = calltime;
	}

	public String getOutcallcount_p1() {
		return outcallcount_p1;
	}

	public void setOutcallcount_p1(String outcallcount_p1) {
		this.outcallcount_p1 = outcallcount_p1;
	}

	public String getOutcallduration_p1() {
		return outcallduration_p1;
	}

	public void setOutcallduration_p1(String outcallduration_p1) {
		this.outcallduration_p1 = outcallduration_p1;
	}

	public String getOutcallcount_p2() {
		return outcallcount_p2;
	}

	public void setOutcallcount_p2(String outcallcount_p2) {
		this.outcallcount_p2 = outcallcount_p2;
	}

	public String getOutcallduration_p2() {
		return outcallduration_p2;
	}

	public void setOutcallduration_p2(String outcallduration_p2) {
		this.outcallduration_p2 = outcallduration_p2;
	}

	public String getOutcallcount_p3() {
		return outcallcount_p3;
	}

	public void setOutcallcount_p3(String outcallcount_p3) {
		this.outcallcount_p3 = outcallcount_p3;
	}

	public String getOutcallduration_p3() {
		return outcallduration_p3;
	}

	public void setOutcallduration_p3(String outcallduration_p3) {
		this.outcallduration_p3 = outcallduration_p3;
	}

	public String getOutcallcount_p4() {
		return outcallcount_p4;
	}

	public void setOutcallcount_p4(String outcallcount_p4) {
		this.outcallcount_p4 = outcallcount_p4;
	}

	public String getOutcallduration_p4() {
		return outcallduration_p4;
	}

	public void setOutcallduration_p4(String outcallduration_p4) {
		this.outcallduration_p4 = outcallduration_p4;
	}

	public String getOutcallcount() {
		return outcallcount;
	}

	public void setOutcallcount(String outcallcount) {
		this.outcallcount = outcallcount;
	}

	public String getOutcallduration() {
		return outcallduration;
	}

	public void setOutcallduration(String outcallduration) {
		this.outcallduration = outcallduration;
	}

	public String getIncallcount_p1() {
		return incallcount_p1;
	}

	public void setIncallcount_p1(String incallcount_p1) {
		this.incallcount_p1 = incallcount_p1;
	}

	public String getIncallduration_p1() {
		return incallduration_p1;
	}

	public void setIncallduration_p1(String incallduration_p1) {
		this.incallduration_p1 = incallduration_p1;
	}

	public String getIncallcount_p2() {
		return incallcount_p2;
	}

	public void setIncallcount_p2(String incallcount_p2) {
		this.incallcount_p2 = incallcount_p2;
	}

	public String getIncallduration_p2() {
		return incallduration_p2;
	}

	public void setIncallduration_p2(String incallduration_p2) {
		this.incallduration_p2 = incallduration_p2;
	}

	public String getIncallcount_p3() {
		return incallcount_p3;
	}

	public void setIncallcount_p3(String incallcount_p3) {
		this.incallcount_p3 = incallcount_p3;
	}

	public String getIncallduration_p3() {
		return incallduration_p3;
	}

	public void setIncallduration_p3(String incallduration_p3) {
		this.incallduration_p3 = incallduration_p3;
	}

	public String getIncallcount_p4() {
		return incallcount_p4;
	}

	public void setIncallcount_p4(String incallcount_p4) {
		this.incallcount_p4 = incallcount_p4;
	}

	public String getIncallduration_p4() {
		return incallduration_p4;
	}

	public void setIncallduration_p4(String incallduration_p4) {
		this.incallduration_p4 = incallduration_p4;
	}

	public String getIncallcount() {
		return incallcount;
	}

	public void setIncallcount(String incallcount) {
		this.incallcount = incallcount;
	}

	public String getIncallduration() {
		return incallduration;
	}

	public void setIncallduration(String incallduration) {
		this.incallduration = incallduration;
	}

	public String getOutduration() {
		return outduration;
	}

	public void setOutduration(String outduration) {
		this.outduration = outduration;
	}

	public String getInduration() {
		return induration;
	}

	public void setInduration(String induration) {
		this.induration = induration;
	}
	
}
