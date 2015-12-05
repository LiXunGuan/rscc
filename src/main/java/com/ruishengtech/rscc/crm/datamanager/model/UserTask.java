package com.ruishengtech.rscc.crm.datamanager.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "new_data_department_user")

//用户表（坐席列表）
public class UserTask extends CommonDbBean {
	
	public static String tableName = UserTask.class.getAnnotation(Table.class).name();
	
	@Index
	@Column(meta = "VARCHAR(64)", column = "department_uuid")
    private String departmentUuid;

	//单次上限
	@NColumn(meta = "INT DEFAULT 0", column = "single_limit")
	private Integer singleLimit;
		
	//单日上限
	@NColumn(meta = "INT DEFAULT 0", column = "day_limit")
	private Integer dayLimit;

	//部门数据总上限
	@NColumn(meta = "INT DEFAULT 0", column = "total_limit")
	private Integer totalLimit;
	
	//意向客户上限有两种设计方法，一种是直接以一个json格式的数据字段存储在这种表中，还有一种是以关联表的形式存放，这里先用json存，毕竟存取方便
	@NColumn(meta = "INT DEFAULT NULL", column = "intent_limit")
	private Integer intentLimit;
	
	//用户统计信息
	//用户数据量
	@NColumn(meta = "INT DEFAULT 0", column = "data_count")
	private Integer dataCount;
	
	//用户产生的意向客户量
	@NColumn(meta = "INT DEFAULT 0", column = "intent_count")
	private Integer intentCount;
	
	//用户产生的成交客户量
	@NColumn(meta = "INT DEFAULT 0", column = "customer_count")
	private Integer customerCount;
	
	//用户废号量
	@NColumn(meta = "INT DEFAULT 0", column = "abandon_count")
	private Integer abandonCount;
	
	//用户转共享量
	@NColumn(meta = "INT DEFAULT 0", column = "share_count")
	private Integer shareCount;
	
	//用户黑名单号码量
	@NColumn(meta = "INT DEFAULT 0", column = "blacklist_count")
	private Integer blacklistCount;

	private String loginName;
	
	private String userName;
	
	private String deptName;
	
	private Integer unCallCount;
	
	private Integer callUnCount;
	
	private Integer callCount;
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

	public Integer getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(Integer dayLimit) {
		this.dayLimit = dayLimit;
	}

	public Integer getTotalLimit() {
		return totalLimit;
	}

	public void setTotalLimit(Integer totalLimit) {
		this.totalLimit = totalLimit;
	}

	public Integer getIntentLimit() {
		return intentLimit;
	}

	public void setIntentLimit(Integer intentLimit) {
		this.intentLimit = intentLimit;
	}

	public Integer getDataCount() {
		return dataCount;
	}

	public void setDataCount(Integer dataCount) {
		this.dataCount = dataCount;
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

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public Integer getUnCallCount() {
		return unCallCount;
	}

	public void setUnCallCount(Integer unCallCount) {
		this.unCallCount = unCallCount;
	}

	public Integer getCallUnCount() {
		return callUnCount;
	}

	public void setCallUnCount(Integer callUnCount) {
		this.callUnCount = callUnCount;
	}

	public Integer getCallCount() {
		return callCount;
	}

	public void setCallCount(Integer callCount) {
		this.callCount = callCount;
	}
	
}
