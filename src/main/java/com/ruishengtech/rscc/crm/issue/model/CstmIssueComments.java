package com.ruishengtech.rscc.crm.issue.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Table;


/**
 * @author Frank
 *
 *case评论
 */
@Table(name = "cstmservice_comment")
public class CstmIssueComments extends CommonDbBean{

	/**
	 *case名字
	 */
	@Column(meta = "VARCHAR(64)" , column="cstmservice_uuid")
	private String cstmserviceUUID;
	/**
	 *评论时间 
	 */
	@Column(meta = "DATETIME" , column="comment_time")
	private Date commentTime;
	
	
	@Column(meta = "VARCHAR(64)" , column="cstm_name")
	private String cstmName;
	/**
	 * 评论
	 */
	@Column(meta = "VARCHAR(1024)" , column = "comments")
	private String commonts;

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public String getCommonts() {
		return commonts;
	}

	public void setCommonts(String commonts) {
		this.commonts = commonts;
	}

	public String getCstmName() {
		return cstmName;
	}

	public String getCstmserviceUUID() {
		return cstmserviceUUID;
	}

	public void setCstmserviceUUID(String cstmserviceUUID) {
		this.cstmserviceUUID = cstmserviceUUID;
	}

	public void setCstmName(String cstmName) {
		this.cstmName = cstmName;
	}
	
}
