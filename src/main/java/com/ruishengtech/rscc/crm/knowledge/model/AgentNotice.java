package com.ruishengtech.rscc.crm.knowledge.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * 
 * @author chengxin
 *
 */
@Table(name = "agent_notice")
public class AgentNotice extends CommonDbBean{
	
	/**
	 * 坐席公告标题
	 */
	@Column(meta = "VARCHAR(64)", column = "notice_title")
	private String noticeTitle;
	
	/**
	 * 坐席公告内容
	 */
	@Column(meta = "LONGTEXT", column = "notice_content")
	private String noticeContent;
	
	/**
	 * 公告创建时间
	 */
	@Column(meta = "DATETIME", column = "create_time")
	private Date createTime;
	
	/**
	 * 公告创建人
	 */
	@Column(meta = "VARCHAR(64)", column = "create_user_uuid")
	private String createUserUUID;
	
	/**
	 * 公告状态
	 * 1:发布
	 * 0:未发布
	 */
	@NColumn(meta = "CHAR(1)", column = "publish_status")
	private String publishStatus;
	
	private Date publishTime;
	
	private String pubTime;
	
	public static final String PUBLISH = "1";
	

	public static final String UNPUBLISH = "0";
	
	public static Map<String,String> ISPUBLISH =new LinkedHashMap<String,String>(){
		{
			put(PUBLISH,"是");
			put(UNPUBLISH,"否");
		}
	};
	
	/**
	 * 来自谁的公告(姓名)
	 */
	private String publishUserName;
	
	/**
	 * 来自谁的公告(UUID)
	 */
	private String publishUserUuid;
	
	public String getPublishUserUuid() {
		return publishUserUuid;
	}

	public void setPublishUserUuid(String publishUserUuid) {
		this.publishUserUuid = publishUserUuid;
	}

	public String getPublishUserName() {
		return publishUserName;
	}

	public void setPublishUserName(String publishUserName) {
		this.publishUserName = publishUserName;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserUUID() {
		return createUserUUID;
	}

	public void setCreateUserUUID(String createUserUUID) {
		this.createUserUUID = createUserUUID;
	}

	public String getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(String publishStatus) {
		this.publishStatus = publishStatus;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getPubTime() {
		return pubTime;
	}

	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}
	
}
