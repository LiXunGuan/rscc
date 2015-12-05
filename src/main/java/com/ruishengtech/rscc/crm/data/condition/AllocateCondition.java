package com.ruishengtech.rscc.crm.data.condition;


/**
 * @author Wangyao
 *
 */
public class AllocateCondition {
	
	//数据表名
	private String dataTable;
	
	//数据表分配类型，0全部分配，1部分分配
	private Integer dataType = 0;
	
	//选择为1时，可分配的数据上限
	private Integer dataMax; 

	//分配类型，0.1.2.3
	private	Integer allocate = 1;
	
	//分配类型为1.3时分配的上限
	private Integer allocateMax;
	
	//选择的所有人员
	private String[] users;
	
	//分配计算时是否包含已完成任务（通常不包含）
	private boolean containAll;

	public String getDataTable() {
		return dataTable;
	}

	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getDataMax() {
		return dataMax;
	}

	public void setDataMax(Integer dataMax) {
		this.dataMax = dataMax;
	}

	public Integer getAllocate() {
		return allocate;
	}

	public void setAllocate(Integer allocate) {
		this.allocate = allocate;
	}

	public Integer getAllocateMax() {
		return allocateMax;
	}

	public void setAllocateMax(Integer allocateMax) {
		this.allocateMax = allocateMax;
	}

	public String[] getUsers() {
		return users;
	}

	public void setUsers(String[] users) {
		this.users = users;
	}

	public boolean isContainAll() {
		return containAll;
	}

	public void setContainAll(boolean containAll) {
		this.containAll = containAll;
	}
	
}
