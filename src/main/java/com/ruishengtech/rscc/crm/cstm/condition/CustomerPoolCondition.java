package com.ruishengtech.rscc.crm.cstm.condition;

import java.util.Collection;

import com.ruishengtech.framework.core.db.condition.Page;

/**
 * @author Frank
 *
 */
public class CustomerPoolCondition extends Page{
	
	/**
	 * 客户池名称查询
	 */
	private String poolName;

	/**
	 * 客户池名称查询
	 */
	private String creater;

	/**
	 * 客户池创建者查询
	 */
	private String poolCreater;
	
	/**
	 * 包含的客户池编号
	 */
	private Collection<String> ins;

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Collection<String> getIns() {
		return this.ins;
	}

	public void setIns(Collection<String> ins) {
		this.ins = ins;
	}

	public String getPoolCreater() {
		return this.poolCreater;
	}

	public void setPoolCreater(String poolCreater) {
		this.poolCreater = poolCreater;
	}
	

}