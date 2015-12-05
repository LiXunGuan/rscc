package com.ruishengtech.rscc.crm.knowledge.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

/**
 * 
 * @author chengxin
 *
 */
@Table(name = "knowledge_label_link")
public class KnowledgeLabelLink {
	
	/**
	 * 知识uuid
	 */
	@Key
	@Column(meta = "VARCHAR(64)", column = "knowledge_uuid")
	private String knowledgeUUid;
	
	/**
	 * 标签uuid
	 */
	@Key
	@Column(meta = "VARCHAR(64)", column = "knowledge_label_uuid")
	private String knoeledgeLabelUUid;

	public String getKnowledgeUUid() {
		return knowledgeUUid;
	}

	public void setKnowledgeUUid(String knowledgeUUid) {
		this.knowledgeUUid = knowledgeUUid;
	}

	public String getKnoeledgeLabelUUid() {
		return knoeledgeLabelUUid;
	}

	public void setKnoeledgeLabelUUid(String knoeledgeLabelUUid) {
		this.knoeledgeLabelUUid = knoeledgeLabelUUid;
	}
	
}
