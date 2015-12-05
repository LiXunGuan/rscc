package com.ruishengtech.rscc.crm.data.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

//导入记录，防止数据重复
@Table(name = "data_project_data_link")
public class ProjectDataLink { 

	@Key
    @Column(meta = "VARCHAR(64)", column = "data_uuid")
    private String dataUuid;

	@Key
    @Column(meta = "VARCHAR(64)", column = "project_uuid")
    private String projectUuid;

    @Column(meta = "timestamp")
    private Date createTime;
    
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDataUuid() {
		return dataUuid;
	}

	public void setDataUuid(String dataUuid) {
		this.dataUuid = dataUuid;
	}

	public String getProjectUuid() {
		return projectUuid;
	}

	public void setProjectUuid(String projectUuid) {
		this.projectUuid = projectUuid;
	}

}
