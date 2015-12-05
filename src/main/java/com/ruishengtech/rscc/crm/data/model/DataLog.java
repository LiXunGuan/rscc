package com.ruishengtech.rscc.crm.data.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * @author Wangyao
 *
 */
@Table(name = "data_log")

public class DataLog extends CommonDbBean { 

	@Column(meta = "VARCHAR(64)", column = "data_name")
    private String dataName;
	
    @NColumn(meta = "VARCHAR(64)", column = "source_name")
    private String sourceName;

    @NColumn(meta = "VARCHAR(64)", column = "data_table")
    private String dataTable;

    @NColumn(meta = "VARCHAR(64)", column = "distinct_flag")
    private String distinctFlag;

    @NColumn(meta = "VARCHAR(64)", column = "import_flag")
    private String importFlag;
    
	@NColumn(meta = "DateTime", column = "data_createtime")
    private Date dataCreateTime;
	
	@NColumn(meta = "INT", column = "data_count")
	private Integer dataCount;

	@NColumn(meta = "VARCHAR(64)", column = "file_path")
	private String filePath;
	
	@NColumn(meta = "VARCHAR(64)", column = "upload_user")
	private String uploadUser;
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getUploadUser() {
		return uploadUser;
	}

	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
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

	public Date getDataCreateTime() {
		return dataCreateTime;
	}

	public void setDataCreateTime(Date dataCreateTime) {
		this.dataCreateTime = dataCreateTime;
	}

	public Integer getDataCount() {
		return dataCount;
	}

	public void setDataCount(Integer dataCount) {
		this.dataCount = dataCount;
	}

	public String getDataTable() {
		return dataTable;
	}

	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

}
