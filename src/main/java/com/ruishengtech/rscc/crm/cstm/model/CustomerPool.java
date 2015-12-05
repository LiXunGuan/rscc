package com.ruishengtech.rscc.crm.cstm.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * @author Frank
 *
 */
@Table(name = "cstm_customer_pool")
public class CustomerPool extends CommonDbBean{
	
	/**
	 * 客户池名称
	 */
	@Column(meta = "VARCHAR(64)", column = "pool_name")
	private String poolName;
	
	/**
	 * 客户池描述
	 */
	@NColumn(meta = "VARCHAR(64)", column = "pool_des")
	private String poolDes;
	
	/**
	 * 客户池创建时间
	 */
	@Column(meta = "DATETIME", column = "create_time")
	private Date createTime;

	/**
	 * 是否默认
	 */
	@NColumn(meta = "VARCHAR(64)", column = "be_default")
	private String beDefault;

	/**
	 * 创建者
	 */
	@Column(meta = "VARCHAR(64)", column = "creater")
	private String creater;
	
	private String poolCreater;

	
	
	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public String getPoolDes() {
		return poolDes;
	}

	public void setPoolDes(String poolDes) {
		this.poolDes = poolDes;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBeDefault() {
		return this.beDefault;
	}

	public void setBeDefault(String beDefault) {
		this.beDefault = beDefault;
	}

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	@Override
	public boolean equals(Object obj) {

		CustomerPool customerPool = (CustomerPool)obj;
		
		return	this.getUid().equals(customerPool.getUid());
	}

	@Override
	public int hashCode() {

		return this.getUid().hashCode();
	}

	public String getPoolCreater() {
		return this.poolCreater;
	}

	public void setPoolCreater(String poolCreater) {
		this.poolCreater = poolCreater;
	}
	
	
	
}
