package com.ruishengtech.rscc.crm.knowledge.condition;

import com.ruishengtech.framework.core.db.condition.Page;

public class KnowledgeCondition extends Page {
	
	private String title;
	
	private String content;
	
	private String directory;
	
	private String tag;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
}
