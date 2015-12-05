package com.ruishengtech.rscc.crm.data.condition;



/**
 * @author Wangyao
 *
 */
public class ProjectTaskItemsCondition {

	private String projectName;

	private String taskName;

    private String dataTable;

//    private List<String> items = new ArrayList<String>();
    
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDataTable() {
		return dataTable;
	}

	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}

}
