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
@Table(name = "data_container_log")

public class DataContainerLog extends CommonDbBean { 

	public static final String OPERATE_CREATE = "0";

	public static final String OPERATE_UPDATE = "1";
	
	public static final String OPERATE_DELETE = "2";
	
	//数据容器名，池或者批次
	@Column(meta = "VARCHAR(64)", column = "container_uuid")
    private String containerUuid;
	
	//数据容器名，池或者批次
	@NColumn(meta = "VARCHAR(64)", column = "container_name")
    private String containerName;
	
	//保存的数据表名，不允许容器名称重复的情况，0为上传文件id，1为uuid
    @NColumn(meta = "VARCHAR(64)", column = "data_table")
    private String dataTable;

    //进行的操作，新增，删除，修改
    @Column(meta = "VARCHAR(1)", column = "operate")
    private String operate;

    //操作时间
	@NColumn(meta = "TIMESTAMP DEFAULT NOW()", column = "operate_time")
    private Date operateTime;
	
	//本地保存路径
	@NColumn(meta = "VARCHAR(64)", column = "file_path")
	private String filePath;
	
	//操作者
	@NColumn(meta = "VARCHAR(64)", column = "operater")
	private String operater;

	public String getContainerUuid() {
		return containerUuid;
	}

	public void setContainerUuid(String containerUuid) {
		this.containerUuid = containerUuid;
	}

	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}

	public String getDataTable() {
		return dataTable;
	}

	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}
	
}
