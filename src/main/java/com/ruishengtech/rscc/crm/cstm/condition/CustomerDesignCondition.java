package com.ruishengtech.rscc.crm.cstm.condition;

import com.ruishengtech.framework.core.db.condition.Page;

public class CustomerDesignCondition extends Page{
	
	
	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 是否展示所有 
	 */
	private String showAll;
	
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getShowAll() {
		return this.showAll;
	}

	public void setShowAll(String showAll) {
		this.showAll = showAll;
	}
	
	
}
