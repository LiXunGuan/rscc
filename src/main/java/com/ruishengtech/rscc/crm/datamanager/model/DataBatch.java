package com.ruishengtech.rscc.crm.datamanager.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * @author Wangyao
 *
 */
@Table(name = "new_data_batch")

//批次表

public class DataBatch extends CommonDbBean { 

	public static String tableName = DataBatch.class.getAnnotation(Table.class).name();
	
	//数据批次名
	@Column(meta = "VARCHAR(64)", column = "batch_name")
    private String batchName;
	
	//0时为导入的文件名
	@NColumn(meta = "VARCHAR(64)", column = "file_name")
    private String fileName;

	//保存的数据表名，不允许名称重复，0为上传文件id
    @NColumn(meta = "VARCHAR(64)", column = "data_table")
    private String dataTable;

    //创建时间
	@NColumn(meta = "TIMESTAMP DEFAULT NOW()", column = "data_import_timestamp")
    private Date dataImportTimestamp;
	
	//本地保存路径
	@NColumn(meta = "VARCHAR(64)", column = "file_path")
	private String filePath;
	
	//上传者
	@NColumn(meta = "VARCHAR(64)", column = "upload_user")
	private String uploadUser;
	
	
	//批次的统计信息
	//批次内数据量
	@NColumn(meta = "INT DEFAULT 0", column = "data_count")
	private Integer dataCount;
	
	//批次内被领用的数据量
	@NColumn(meta = "INT DEFAULT 0", column = "own_count")
	private Integer ownCount;
	
	//批次内意向客户量
	@NColumn(meta = "INT DEFAULT 0", column = "intent_count")
	private Integer intentCount;
	
	//批次内成交客户量
	@NColumn(meta = "INT DEFAULT 0", column = "customer_count")
	private Integer customerCount;
	
	//批次内冻结号码量
	@NColumn(meta = "INT DEFAULT 0", column = "frozen_count")
	private Integer frozenCount;
	
	//批次内废号量
	@NColumn(meta = "INT DEFAULT 0", column = "abandon_count")
	private Integer abandonCount;
	
	//批次内转共享量
	@NColumn(meta = "INT DEFAULT 0", column = "share_count")
	private Integer shareCount;
	
	//批次内黑名单号码量
	@NColumn(meta = "INT DEFAULT 0", column = "blacklist_count")
	private Integer blacklistCount;
	
	//是否被群呼锁定
	@NColumn(meta = "CHAR(1) DEFAULT '0'", column = "is_lock")
	private String isLock;
	
	@NColumn(meta = "DATETIME", column = "lock_timestamp")
	private Date lockTimestamp;
	
	//记录导入进度
	private String progress;
	
	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	public Date getLockTimestamp() {
		return lockTimestamp;
	}

	public void setLockTimestamp(Date lockTimestamp) {
		this.lockTimestamp = lockTimestamp;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDataTable() {
		return dataTable;
	}

	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}

	public Date getDataImportTimestamp() {
		return dataImportTimestamp;
	}

	public void setDataImportTimestamp(Date dataImportTimestamp) {
		this.dataImportTimestamp = dataImportTimestamp;
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

	public Integer getDataCount() {
		return dataCount;
	}

	public void setDataCount(Integer dataCount) {
		this.dataCount = dataCount;
	}

	public Integer getOwnCount() {
		return ownCount;
	}

	public void setOwnCount(Integer ownCount) {
		this.ownCount = ownCount;
	}

	public Integer getIntentCount() {
		return intentCount;
	}

	public void setIntentCount(Integer intentCount) {
		this.intentCount = intentCount;
	}

	public Integer getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(Integer customerCount) {
		this.customerCount = customerCount;
	}

	public Integer getFrozenCount() {
		return frozenCount;
	}

	public void setFrozenCount(Integer frozenCount) {
		this.frozenCount = frozenCount;
	}

	public Integer getAbandonCount() {
		return abandonCount;
	}

	public void setAbandonCount(Integer abandonCount) {
		this.abandonCount = abandonCount;
	}

	public Integer getBlacklistCount() {
		return blacklistCount;
	}

	public void setBlacklistCount(Integer blacklistCount) {
		this.blacklistCount = blacklistCount;
	}

}
