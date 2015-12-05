package com.ruishengtech.rscc.crm.knowledge.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * 
 * @author chengxin
 *
 */
@Table(name = "agent_notice_user_link")
public class AgentNoticeUserLink extends CommonDbBean{

	/**
	 * 坐席公告的uuid
	 */
	@Column(meta = "VARCHAR(64)", column = "agent_notice_uuid")
	private String agentNoticeUUID;
	
	/**
	 * 接收公告用户的uuid
	 */
	@Column(meta = "VARCHAR(64)", column = "user_uuid")
	private String userUUID;
	
	@Column(meta = "VARCHAR(64)", column = "publishuser_uuid")
	private String publishUserUUID;
	
	/**
	 * 发布时间
	 */
	@NColumn(meta = "DATETIME", column = "publish_time")
	private Date publishTime;
	
	public String getAgentNoticeUUID() {
		return agentNoticeUUID;
	}

	public void setAgentNoticeUUID(String agentNoticeUUID) {
		this.agentNoticeUUID = agentNoticeUUID;
	}

	public String getUserUUID() {
		return userUUID;
	}

	public void setUserUUID(String userUUID) {
		this.userUUID = userUUID;
	}

	public String getPublishUserUUID() {
		return publishUserUUID;
	}

	public void setPublishUserUUID(String publishUserUUID) {
		this.publishUserUUID = publishUserUUID;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	
}
