package com.ruishengtech.rscc.crm.data.condition;

import java.util.Collection;

import com.ruishengtech.framework.core.db.condition.Page;


/**
 * @author Wangyao
 *
 */
public class DataContainerCondition extends Page{

    private String containerName;

    private String dataInfo;

    private String containerType;
    
    private String dataTable;

    private String distinctFlag;

	private Integer dataCountMin;

	private Integer dataCountMax;
	
	private Integer allocateMin;

	private Integer allocateMax;

	private Collection<String> ins;
	
	public Collection<String> getIns() {
		return ins;
	}

	public void setIns(Collection<String> ins) {
		this.ins = ins;
	}

	public Integer getAllocateMin() {
		return allocateMin;
	}

	public void setAllocateMin(Integer allocateMin) {
		this.allocateMin = allocateMin;
	}

	public Integer getAllocateMax() {
		return allocateMax;
	}

	public void setAllocateMax(Integer allocateMax) {
		this.allocateMax = allocateMax;
	}

	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}

	public String getDataInfo() {
		return dataInfo;
	}

	public void setDataInfo(String dataInfo) {
		this.dataInfo = dataInfo;
	}

	public String getDataTable() {
		return dataTable;
	}

	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}

	public String getDistinctFlag() {
		return distinctFlag;
	}

	public void setDistinctFlag(String distinctFlag) {
		this.distinctFlag = distinctFlag;
	}

	public Integer getDataCountMin() {
		return dataCountMin;
	}

	public void setDataCountMin(Integer dataCountMin) {
		this.dataCountMin = dataCountMin;
	}

	public Integer getDataCountMax() {
		return dataCountMax;
	}

	public void setDataCountMax(Integer dataCountMax) {
		this.dataCountMax = dataCountMax;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

}
