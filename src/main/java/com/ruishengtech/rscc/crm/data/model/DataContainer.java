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
@Table(name = "data_container")

public class DataContainer extends CommonDbBean { 

	//数据容器名，池或者批次
	@Column(meta = "VARCHAR(64)", column = "container_name")
    private String containerName;
	
	//0为文件导入的批次，1为池
	@Column(meta = "VARCHAR(1) DEFAULT '0'", column = "container_type")
    private String containerType;
	
	//0时为导入的文件名，1时为填写的信息
	@NColumn(meta = "VARCHAR(64)", column = "data_info")
    private String dataInfo;

	//保存的数据表名，不允许容器名称重复的情况，0为上传文件id，1为uuid
    @NColumn(meta = "VARCHAR(64)", column = "data_table")
    private String dataTable;

    //是否进行了去重处理，一般为是
    @NColumn(meta = "VARCHAR(1) DEFAULT '0' ", column = "distinct_flag")
    private String distinctFlag;

    //创建时间
	@NColumn(meta = "TIMESTAMP DEFAULT NOW()", column = "data_createtime")
    private Date dataCreateTime;
	
	//本地保存路径
	@NColumn(meta = "VARCHAR(64)", column = "file_path")
	private String filePath;
	
	//上传者
	@NColumn(meta = "VARCHAR(64)", column = "upload_user")
	private String uploadUser;
	
	//数据总量
	@Column(meta = "INT DEFAULT 0", column = "data_count")
	private Integer dataCount;
	
	//已分配数量
	@Column(meta = "INT DEFAULT 0", column = "allocate_count")
	private Integer allocateCount;

	//记录导入进度
	private String progress;
	
	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public Integer getDataCount() {
		return dataCount;
	}

	public void setDataCount(Integer dataCount) {
		this.dataCount = dataCount;
	}

	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
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

	public Date getDataCreateTime() {
		return dataCreateTime;
	}

	public void setDataCreateTime(Date dataCreateTime) {
		this.dataCreateTime = dataCreateTime;
	}

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

	public Integer getAllocateCount() {
		return allocateCount;
	}

	public void setAllocateCount(Integer allocateCount) {
		this.allocateCount = allocateCount;
	}
	
}
