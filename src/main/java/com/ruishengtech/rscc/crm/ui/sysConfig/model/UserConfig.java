package com.ruishengtech.rscc.crm.ui.sysConfig.model;

import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "user_config")
public class UserConfig extends CommonDbBean {

	@NColumn(meta = "VARCHAR(1024)", column = "config")
	private String config;

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}
	
}
