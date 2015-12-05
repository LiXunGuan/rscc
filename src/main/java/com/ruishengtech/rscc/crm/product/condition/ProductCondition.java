package com.ruishengtech.rscc.crm.product.condition;

import com.ruishengtech.framework.core.db.condition.Page;

public class ProductCondition extends Page{

	private String pid;
	
	private String pname;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	
}
