package com.ruishengtech.rscc.crm.datamanager.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "new_data_batch_department_link")

public class DataBatchDepartmentLink { 

	public static String tableName = DataBatchDepartmentLink.class.getAnnotation(Table.class).name();
	
	//批次id
	@Key
	@Column(meta = "VARCHAR(64)", column = "data_batch_uuid")
	private String dataBatchUuid;
	
	//关联部门id
	@Key
	@Column(meta = "VARCHAR(64)", column = "department_uuid")
	private String departmentUuid;

	//单次上限
	@NColumn(meta = "INT DEFAULT 0", column = "single_limit")
	private Integer singleLimit;
	
	//单日上限
	@NColumn(meta = "INT DEFAULT 0", column = "day_limit")
	private Integer dayLimit;

	//是否领用了，以及是否允许二级领用--为空时没有被该部门领用，非空为已领用。0不允许二级领用，1允许二级领用
	@NColumn(meta = "CHAR(1) DEFAULT '0'", column = "open_flag")
	private String openFlag;
	
	
	//这个批次分配给这个部门的数据，的数据量
	@NColumn(meta = "INT DEFAULT 0", column = "data_count")
	private Integer dataCount;
	
	//这个批次分配给这个部门的数据，被二次领用的数据量
	@NColumn(meta = "INT DEFAULT 0", column = "own_count")
	private Integer ownCount;
	
	//批次内转共享量
	@NColumn(meta = "INT DEFAULT 0", column = "share_count")
	private Integer shareCount;
	
	//这个批次分配给这个部门的数据，产生的意向客户量
	@NColumn(meta = "INT DEFAULT 0", column = "intent_count")
	private Integer intentCount;
	
	//这个批次分配给这个部门的数据，产生的成交客户量
	@NColumn(meta = "INT DEFAULT 0", column = "customer_count")
	private Integer customerCount;
	
	//这个批次分配给这个部门的数据，产生的废号量
	@NColumn(meta = "INT DEFAULT 0", column = "abandon_count")
	private Integer abandonCount;
	
	//这个批次分配给这个部门的数据，产生的黑名单量
	@NColumn(meta = "INT DEFAULT 0", column = "blacklist_count")
	private Integer blacklistCount;
	
	//是否被群呼锁定
	@NColumn(meta = "CHAR(1) DEFAULT '0'", column = "is_lock")
	private String isLock;
	
	//归还次数
	@NColumn(meta = "INT DEFAULT 0", column = "return_times")
	private Integer returnTimes;
	
	//是否是被系统添加的链接，如果是则不可以主动获取
	@NColumn(meta = "CHAR(1) DEFAULT '0'", column = "is_auto")
	private String isAuto;
	
	public String getIsAuto() {
		return isAuto;
	}

	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
	}

	// 批次名
	private String batchname;
	
	// 部门名
	private String deptname;
	
	private Integer totalLimit;
	
	// 相关批次内的总数据量
	private Integer batchDataCount;
	
	// 相关批次内的已领用数据量
	private Integer batchOwnCount;
	
	public Integer getTotalLimit() {
		return totalLimit;
	}

	public void setTotalLimit(Integer totalLimit) {
		this.totalLimit = totalLimit;
	}

	public String getDataBatchUuid() {
		return dataBatchUuid;
	}

	public void setDataBatchUuid(String dataBatchUuid) {
		this.dataBatchUuid = dataBatchUuid;
	}

	public String getDepartmentUuid() {
		return departmentUuid;
	}

	public void setDepartmentUuid(String departmentUuid) {
		this.departmentUuid = departmentUuid;
	}

	public Integer getSingleLimit() {
		return singleLimit;
	}

	public void setSingleLimit(Integer singleLimit) {
		this.singleLimit = singleLimit;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public Integer getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(Integer dayLimit) {
		this.dayLimit = dayLimit;
	}

	public String getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getBatchname() {
		return batchname;
	}

	public void setBatchname(String batchname) {
		this.batchname = batchname;
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

	public Integer getBatchDataCount() {
		return batchDataCount;
	}

	public void setBatchDataCount(Integer batchDataCount) {
		this.batchDataCount = batchDataCount;
	}

	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	public Integer getBatchOwnCount() {
		return batchOwnCount;
	}

	public void setBatchOwnCount(Integer batchOwnCount) {
		this.batchOwnCount = batchOwnCount;
	}

	public Integer getReturnTimes() {
		return returnTimes;
	}

	public void setReturnTimes(Integer returnTimes) {
		this.returnTimes = returnTimes;
	}

	
}
