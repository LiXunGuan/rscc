package com.ruishengtech.rscc.crm.data.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "data_data_pool")

public class DataPool extends CommonDbBean {
	
	@Column(meta = "VARCHAR(64)", column = "pool_name")
    private String poolName;
	
	@Column(meta = "VARCHAR(64)", column = "pool_describe")
    private String poolDescribe;
	
	@Column(meta = "VARCHAR(1) DEFAULT '0' ", column = "pool_type")
	private String poolType;
	
	@Column(meta = "TIMESTAMP DEFAULT NOW() ", column = "create_time")
	private Date createTime;

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public String getPoolDescribe() {
		return poolDescribe;
	}

	public void setPoolDescribe(String poolDescribe) {
		this.poolDescribe = poolDescribe;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
