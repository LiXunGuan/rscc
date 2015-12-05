package com.ruishengtech.rscc.crm.data.condition;

import com.ruishengtech.framework.core.db.condition.Page;


/**
 * @author Wangyao
 *
 */
public class DataLogCondition extends Page{

    private String dataName;

    private String sourceName;

    private String dataTable;

    private String distinctFlag;

    private String importFlag;
    
	private Integer dataCountMin;

	private Integer dataCountMax;

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
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

	public String getImportFlag() {
		return importFlag;
	}

	public void setImportFlag(String importFlag) {
		this.importFlag = importFlag;
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
	
}
