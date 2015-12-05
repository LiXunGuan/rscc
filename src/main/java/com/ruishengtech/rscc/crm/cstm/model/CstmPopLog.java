package com.ruishengtech.rscc.crm.cstm.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.Table;

/**
 * 坐席弹屏和客户的沟通日志
 * @author Frank
 *
 */
@Table(name="cstm_pop_log")
public class CstmPopLog extends CommonDbBean{
	
	/**
	 * 坐席编号
	 */
	@Column(meta = "VARCHAR(64)",column = "agent_id")
	private String agentId;

	/**
	 * 客户电话
	 */
	@Column(meta = "VARCHAR(64)",column = "cstm_phone")
	@Index(name = "cstmPhone", type = IndexDefinition.TYPE_NORMAL)
	private String cstmPhone;
	
	/**
	 * 沟通时间
	 */
	@Column(meta = "DATETIME",column = "date")
	@Index(name = "start_date", type = IndexDefinition.TYPE_NORMAL,method = IndexDefinition.METHOD_BTREE)
	private Date date;
	
	/**
	 * 沟通信息
	 */
	@Column(meta = "TEXT",column = "text_log")
	private String textLog;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}


	public String getCstmPhone() {
		return this.cstmPhone;
	}

	public void setCstmPhone(String cstmPhone) {
		this.cstmPhone = cstmPhone;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTextLog() {
		return textLog;
	}

	public void setTextLog(String textLog) {
		this.textLog = textLog;
	}
	
}
