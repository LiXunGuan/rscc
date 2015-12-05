package com.ruishengtech.rscc.crm.knowledge.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Table;

/**
 * @author chengxin
 */
@Table(name = "knowledge_info")
public class Knowledge extends CommonDbBean{
	
	/**
	 * 知识标题
	 */
	@Column(meta = "VARCHAR(64)", column = "knowledge_title")
	private String knowledgeTitle;
	
	/**
	 * 知识内容
	 */
	@Column(meta = "LONGTEXT", column = "knowledge_content")
	private String knowledgeContent;
	
	/**
	 * 知识所属目录
	 */
	@Column(meta = "VARCHAR(64)", column = "directory_uuid")
	private String directoryUUid;
	
	/**
	 * 知识标签
	 */
	private String knowledgeTags;
	
	/**
	 * 知识目录
	 */
	private String direName;
	
	public String getKnowledgeTags() {
		return knowledgeTags;
	}

	public void setKnowledgeTags(String knowledgeTags) {
		this.knowledgeTags = knowledgeTags;
	}

	public String getKnowledgeTitle() {
		return knowledgeTitle;
	}

	public void setKnowledgeTitle(String knowledgeTitle) {
		this.knowledgeTitle = knowledgeTitle;
	}

	public String getKnowledgeContent() {
		return knowledgeContent;
	}

	public void setKnowledgeContent(String knowledgeContent) {
		this.knowledgeContent = knowledgeContent;
	}

	public String getDirectoryUUid() {
		return directoryUUid;
	}

	public void setDirectoryUUid(String directoryUUid) {
		this.directoryUUid = directoryUUid;
	}

	public String getDireName() {
		return direName;
	}

	public void setDireName(String direName) {
		this.direName = direName;
	}
}
