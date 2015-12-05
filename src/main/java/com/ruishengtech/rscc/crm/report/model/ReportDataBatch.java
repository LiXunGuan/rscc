package com.ruishengtech.rscc.crm.report.model;



import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

/**
 * 
 * @author ning
 *
 */
@Table(name="report_databatch")
public class ReportDataBatch {
	@Key
	@Column(meta="SERIAL")
	private Long id;
	/**
	 * 时间
	 */
	@Index
	@Column(meta="VARCHAR(64)" ,column="op_time")
	private String optime;
	
	/**
	 * 坐席
	 */
	@Index
	@Column(meta="VARCHAR(64)",column="agent")
	private String agent;
	
	//通话数量
	@Column(meta="INT DEFAULT 0",column="call_count")
	private int callcount;
	
	//客户新增数量
	@Column(meta="INT DEFAULT 0",column="cstmadd_count")
	private int cstmaddcount;
	
	//客户减少数量
	@Column(meta="INT DEFAULT 0",column="cstmdel_count")
	private int cstmdelcount;
	
	//最终客户数量
	@Column(meta="INT DEFAULT 0",column="cstm_count")
	private int cstmcount;
	
	//意向新增量
	@Column(meta="INT DEFAULT 0",column="intentadd_count")
	private int intentaddcount;
	
	//意向减少量
	@Column(meta="INT DEFAULT 0",column="intentdel_count")
	private int intentdelcount;
	
	//意向最终量
	@Column(meta="INT DEFAULT 0",column="intent_count")
	private int intentcount;
	
	@Column(meta="INT DEFAULT 0",column="globalshare_count")
	private int globalsharecount;
	
	@Column(meta="INT DEFAULT 0",column="noanswer_count")
	private int noanswercount;
	
	@Column(meta="INT DEFAULT 0",column="blacklist_count")
	private int blacklistcount;
	
	@Column(meta="INT DEFAULT 0",column="abandon_count")
	private int abandoncount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOptime() {
		return optime;
	}

	public void setOptime(String optime) {
		this.optime = optime;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public int getCallcount() {
		return callcount;
	}

	public void setCallcount(int callcount) {
		this.callcount = callcount;
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
	
	
	
	
}
