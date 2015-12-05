package com.ruishengtech.rscc.crm.datamanager.condition;

import java.util.Collection;
import java.util.Date;

import com.ruishengtech.framework.core.db.condition.Page;

public class DataBatchCondition extends Page{
	
		//数据批次名
	    private String batchName;
		
		//0时为导入的文件名
	    private String fileName;

		//保存的数据表名，不允许名称重复，0为上传文件id
	    private String dataTable;

	    //创建时间
	    private Date dataImportTimestamp;
		
		//本地保存路径
		private String filePath;
		
		//上传者
		private String uploadUser;
		
		//批次的统计信息
		//批次内数据量
		private Integer dataCount;
		
		//批次内被领用的数据量
		private Integer ownCount;
		
		//批次内意向客户量
		private Integer intentCount;
		
		//批次内成交客户量
		private Integer customerCount;
		
		//批次内冻结号码量
		private Integer frozenCount;
		
		//批次内废号量
		private Integer abandonCount;
		
		//批次内黑名单号码量
		private Integer blacklistCount;
		
		//记录导入进度
		private String progress;

		private Collection<String> ins;
		
		public Collection<String> getIns() {
			return ins;
		}

		public void setIns(Collection<String> ins) {
			this.ins = ins;
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

		public String getProgress() {
			return progress;
		}

		public void setProgress(String progress) {
			this.progress = progress;
		}
   
		
}
