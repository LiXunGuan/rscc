package com.ruishengtech.rscc.crm.knowledge.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Table;

/**
 * 
 * @author chengxin
 *
 */
@Table(name = "knowledge_clicknumbers")
public class KnowledgeClickNumbers extends CommonDbBean{

	/**
	 * 点击时间
	 */
	@Column(meta = "DATETIME", column = "click_time")
	private Date clickTime;
	
	/**
	 * 点击的知识
	 */
	@Column(meta = "VARCHAR(64)", column = "knowledge_uuid")
	private String knowledgeUUid;
	
	/**
	 * 点击人
	 */
	@Column(meta = "VARCHAR(64)", column = "user_uuid")
	private String userUUid;
	
	public Date getClickTime() {
		return clickTime;
	}

	public void setClickTime(Date clickTime) {
		this.clickTime = clickTime;
	}

	public String getKnowledgeUUid() {
		return knowledgeUUid;
	}

	public void setKnowledgeUUid(String knowledgeUUid) {
		this.knowledgeUUid = knowledgeUUid;
	}

	public String getUserUUid() {
		return userUUid;
	}

	public void setUserUUid(String userUUid) {
		this.userUUid = userUUid;
	}

}
