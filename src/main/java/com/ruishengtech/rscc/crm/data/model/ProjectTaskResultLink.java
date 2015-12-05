package com.ruishengtech.rscc.crm.data.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

//@Table(name = "project_task_result_link")

public class ProjectTaskResultLink {
	@Key
	@Column(meta = "VARCHAR(64)", column = "project_uuid")
	private String projectUuid;
	
	@Key
	@Column(meta = "VARCHAR(64)", column = "task_table")
	private String taskTable;

	@Key
	@Column(meta = "VARCHAR(64)", column = "result_table")
	private String resultTable;
	
	@Column(meta = "DATETIME", column = "create_time")
	private Date createTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getProjectUuid() {
		return projectUuid;
	}

	public void setProjectUuid(String projectUuid) {
		this.projectUuid = projectUuid;
	}

	public String getTaskTable() {
		return taskTable;
	}

	public void setTaskTable(String taskTable) {
		this.taskTable = taskTable;
	}

	public String getResultTable() {
		return resultTable;
	}

	public void setResultTable(String resultTable) {
		this.resultTable = resultTable;
	}
	
}
