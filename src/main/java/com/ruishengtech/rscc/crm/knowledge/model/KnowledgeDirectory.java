package com.ruishengtech.rscc.crm.knowledge.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * 
 * @author chengxin
 *
 */
@Table(name = "knowledge_directory")
public class KnowledgeDirectory extends CommonDbBean{
	
	/**
	 * 目录名
	 */
	@Column(meta = "VARCHAR(64)", column = "directory_name")
	private String directoryName;
	
	/**
	 * 父目录
	 */
	@NColumn(meta = "VARCHAR(64)", column = "directory_parent_uuid")
	private String directoryParentUUid;
	
	/**
	 * 目录备注
	 */
	@NColumn(meta = "VARCHAR(200)", column = "directory_remark")
	private String directoryRemark;

	public String getDirectoryName() {
		return directoryName;
	}

	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

	public String getDirectoryParentUUid() {
		return directoryParentUUid;
	}

	public void setDirectoryParentUUid(String directoryParentUUid) {
		this.directoryParentUUid = directoryParentUUid;
	}

	public String getDirectoryRemark() {
		return directoryRemark;
	}

	public void setDirectoryRemark(String directoryRemark) {
		this.directoryRemark = directoryRemark;
	}
	
}
