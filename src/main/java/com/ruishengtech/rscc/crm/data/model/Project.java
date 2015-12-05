package com.ruishengtech.rscc.crm.data.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "data_project")

public class Project extends CommonDbBean { 

    @Column(meta = "VARCHAR(64)", column = "project_name")
    private String projectName;//=userName

    @Column(meta = "VARCHAR(64)", column = "project_info")
    private String projectInfo;//=useUuid

    @NColumn(meta = "INT", column = "data_count")
    private Integer dataCount;
    
    @NColumn(meta = "INT", column = "complete_count")
    private Integer userCount;//=completeCount
    
//    @NColumn(meta = "VARCHAR(64)", column = "department")
    private String department;
    
	@Column(meta = "VARCHAR(64)", column = "project_stat")
    private String projectStat;
	
	@Column(meta = "DATETIME", column = "create_date")
	private Date createDate;

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectInfo() {
		return projectInfo;
	}

	public void setProjectInfo(String projectInfo) {
		this.projectInfo = projectInfo;
	}
	
	public String getProjectStat() {
		return projectStat;
	}

	public void setProjectStat(String projectStat) {
		this.projectStat = projectStat;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public Integer getDataCount() {
		return dataCount;
	}

	public void setDataCount(Integer dataCount) {
		this.dataCount = dataCount;
	}

}
