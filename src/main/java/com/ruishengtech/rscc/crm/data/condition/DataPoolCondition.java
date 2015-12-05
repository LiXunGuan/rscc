package com.ruishengtech.rscc.crm.data.condition;

import com.ruishengtech.framework.core.db.condition.Page;


/**
 * @author Wangyao
 *
 */
public class DataPoolCondition extends Page{

    private String poolName;

	private String poolDescribe;

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

}
