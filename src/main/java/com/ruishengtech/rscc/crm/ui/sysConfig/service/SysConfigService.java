package com.ruishengtech.rscc.crm.ui.sysConfig.service;

import com.ruishengtech.rscc.crm.ui.sysConfig.model.SysConfig;

public interface SysConfigService {

	/**
	 * 根据键获取值得信息
	 * @param key
	 * @return
	 */
	public abstract SysConfig getSysConfigByKey(String key);
	
	public abstract void saveOrUpdate(SysConfig sys);
	
	public abstract void saveOrUpdate(com.ruishengtech.rscc.crm.ui.mw.model.SysConfig sys);
	
	public abstract SysConfig getSysConfigByUid(String uid);
	
	public abstract void updatSysConfig(String val, String key);

	void updatSysConfigByUUID(String val, String key, String uuid);
	
	public com.ruishengtech.rscc.crm.ui.mw.model.SysConfig getSysConfig(final String name);

	void reload();
}
