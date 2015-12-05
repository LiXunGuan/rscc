package com.ruishengtech.rscc.crm.data.condition;

import java.util.Collection;

import com.ruishengtech.framework.core.db.condition.Page;

public class ProjectCondition extends Page {
	
    private String projectName;

    private Integer dataMin;

    private Integer dataMax;
    
    private Integer completeMin;
   
    private Integer completeMax;
    
    private String department;
    
    private String projectInfo;

    private String projectStat;

    private String projectDescribe;

    private Collection<String> ins;
    
	public Collection<String> getIns() {
		return ins;
	}

	public void setIns(Collection<String> ins) {
		this.ins = ins;
	}

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

	public Integer getDataMin() {
		return dataMin;
	}

	public void setDataMin(Integer dataMin) {
		this.dataMin = dataMin;
	}

	public Integer getDataMax() {
		return dataMax;
	}

	public void setDataMax(Integer dataMax) {
		this.dataMax = dataMax;
	}

	public Integer getCompleteMin() {
		return completeMin;
	}

	public void setCompleteMin(Integer completeMin) {
		this.completeMin = completeMin;
	}

	public Integer getCompleteMax() {
		return completeMax;
	}

	public void setCompleteMax(Integer completeMax) {
		this.completeMax = completeMax;
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

	public String getProjectDescribe() {
		return projectDescribe;
	}

	public void setProjectDescribe(String projectDescribe) {
		this.projectDescribe = projectDescribe;
	}
	
}
