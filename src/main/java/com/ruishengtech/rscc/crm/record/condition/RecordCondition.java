package com.ruishengtech.rscc.crm.record.condition;

import java.util.Collection;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.condition.Page;

/**
 * @author Frank
 *
 */
public class RecordCondition extends Page{

	public RecordCondition(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}
	/**
	 * 分数
	 */
	private Integer score1;
	
	private Integer score2;
	
	private String database;
	
	private String caller_id_number;

	private String access_number;
	
	private String dest_agent_interface_exten;
	
	//通话时长(开始)
	private String sduration;
	
	//通话时长(结束)
	private String eduration;
	
	//接通时长(开始)
	private String sbillsec;
	
	//接通时长(结束)
	private String ebillsec;
	
	//呼叫方向
	private String calldirection;
	
	//是否接通
	private String bridgesec;
	
	private String stime;
	
	private String etime;
	
	private String adminflag;
	
	private String username;
	
	private String level;
	
	private Collection<String> queuesList;

	private String agentinfo;
	
	public String getAdminflag() {
		return adminflag;
	}

	public void setAdminflag(String adminflag) {
		this.adminflag = adminflag;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public Integer getScore1() {
		return this.score1;
	}

	public void setScore1(Integer score1) {
		this.score1 = score1;
	}

	public Integer getScore2() {
		return this.score2;
	}

	public void setScore2(Integer score2) {
		this.score2 = score2;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getCaller_id_number() {
		return caller_id_number;
	}

	public void setCaller_id_number(String caller_id_number) {
		this.caller_id_number = caller_id_number;
	}

	public String getAccess_number() {
		return access_number;
	}

	public void setAccess_number(String access_number) {
		this.access_number = access_number;
	}

	public String getDest_agent_interface_exten() {
		return dest_agent_interface_exten;
	}

	public void setDest_agent_interface_exten(String dest_agent_interface_exten) {
		this.dest_agent_interface_exten = dest_agent_interface_exten;
	}

	public String getSduration() {
		return sduration;
	}

	public void setSduration(String sduration) {
		this.sduration = sduration;
	}

	public String getEduration() {
		return eduration;
	}

	public void setEduration(String eduration) {
		this.eduration = eduration;
	}

	public String getSbillsec() {
		return sbillsec;
	}

	public void setSbillsec(String sbillsec) {
		this.sbillsec = sbillsec;
	}

	public String getEbillsec() {
		return ebillsec;
	}

	public void setEbillsec(String ebillsec) {
		this.ebillsec = ebillsec;
	}

	public String getCalldirection() {
		return calldirection;
	}

	public void setCalldirection(String calldirection) {
		this.calldirection = calldirection;
	}

	public String getBridgesec() {
		return bridgesec;
	}

	public void setBridgesec(String bridgesec) {
		this.bridgesec = bridgesec;
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Collection<String> getQueuesList() {
		return queuesList;
	}

	public void setQueuesList(Collection<String> queuesList) {
		this.queuesList = queuesList;
	}

	public String getAgentinfo() {
		return agentinfo;
	}

	public void setAgentinfo(String agentinfo) {
		this.agentinfo = agentinfo;
	}
	
}
