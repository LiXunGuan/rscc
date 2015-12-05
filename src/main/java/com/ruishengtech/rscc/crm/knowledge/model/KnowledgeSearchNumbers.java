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
@Table(name = "knowledge_searchnumbers")
public class KnowledgeSearchNumbers extends CommonDbBean{

	/**
	 * 搜索时间
	 */
	@Column(meta = "DATETIME", column = "search_time")
	private Date searchTime;
	
	/**
	 * 搜索人
	 */
	@Column(meta = "VARCHAR(64)", column = "user_uuid")
	private String userUUid;
	
	/**
	 * 搜索的关键字
	 */
	@Column(meta = "VARCHAR(64)", column = "keyword")
	private String keyword;


	public Date getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(Date searchTime) {
		this.searchTime = searchTime;
	}

	public String getUserUUid() {
		return userUUid;
	}

	public void setUserUUid(String userUUid) {
		this.userUUid = userUUid;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
