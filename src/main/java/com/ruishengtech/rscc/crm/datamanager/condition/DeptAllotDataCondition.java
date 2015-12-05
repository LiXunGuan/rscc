package com.ruishengtech.rscc.crm.datamanager.condition;


public class DeptAllotDataCondition{

	private String dataBatchUuid; 
	
	private String deptUuid; 
	
	private String[] deptusers;
	
	private String[] depts;

	//分配类型，1,2
	private	Integer allocate = 1;
	
	//分配类型为2时,可分配的数据上限
	private Integer allocateMax;

	public String getDataBatchUuid() {
		return dataBatchUuid;
	}

	public void setDataBatchUuid(String dataBatchUuid) {
		this.dataBatchUuid = dataBatchUuid;
	}

	public String getDeptUuid() {
		return deptUuid;
	}

	public void setDeptUuid(String deptUuid) {
		this.deptUuid = deptUuid;
	}

	public String[] getDeptusers() {
		return deptusers;
	}

	public void setDeptusers(String[] deptusers) {
		this.deptusers = deptusers;
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

	public String[] getDepts() {
		return depts;
	}

	public void setDepts(String[] depts) {
		this.depts = depts;
	}
}
