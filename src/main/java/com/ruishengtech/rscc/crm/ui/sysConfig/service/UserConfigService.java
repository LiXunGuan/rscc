package com.ruishengtech.rscc.crm.ui.sysConfig.service;

import com.ruishengtech.rscc.crm.ui.sysConfig.model.UserConfig;

public interface UserConfigService {
	
	public void save(UserConfig userConfig);
	
	public UserConfig getByUuid(String uuid);
	
}
