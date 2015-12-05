package com.ruishengtech.rscc.crm.datamanager.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "new_data_department")

//部门表
public class DepartmentTable extends CommonDbBean {
	
	public static String tableName = DepartmentTable.class.getAnnotation(Table.class).name();
	
	@Column(meta = "VARCHAR(64)", column = "department_name")
    private String departmentName;

	//部门数据总上限
	@NColumn(meta = "INT DEFAULT 0", column = "total_limit")
	private Integer totalLimit;
	
	//部门统计信息
	//部门内数据量
	@NColumn(meta = "INT DEFAULT 0", column = "data_count")
	private Integer dataCount;
	
	//批次内转共享量
	@NColumn(meta = "INT DEFAULT 0", column = "share_count")
	private Integer shareCount;
	
	//部门内被二次领用的数据量
	@NColumn(meta = "INT DEFAULT 0", column = "own_count")
	private Integer ownCount;
	
	//部门内产生的意向客户量
	@NColumn(meta = "INT DEFAULT 0", column = "intent_count")
	private Integer intentCount;
	
	//部门内产生的成交客户量
	@NColumn(meta = "INT DEFAULT 0", column = "customer_count")
	private Integer customerCount;
	
	//部门内废号量
	@NColumn(meta = "INT DEFAULT 0", column = "abandon_count")
	private Integer abandonCount;
	
	//部门内黑名单号码量
	@NColumn(meta = "INT DEFAULT 0", column = "blacklist_count")
	private Integer blacklistCount;
	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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

	public Integer getTotalLimit() {
		return totalLimit;
	}

	public void setTotalLimit(Integer totalLimit) {
		this.totalLimit = totalLimit;
	}

	public Integer getBlacklistCount() {
		return blacklistCount;
	}

	public void setBlacklistCount(Integer blacklistCount) {
		this.blacklistCount = blacklistCount;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}
}
