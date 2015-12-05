package com.ruishengtech.rscc.crm.ui.sysConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.ui.sysConfig.model.SysConfig;

public class SysConfigManager {
	
//	private SysConfigService sysConfigService = ApplicationHelper.getApplicationContext().getBean(SysConfigService.class);
	
	private BaseService sysConfigService = ApplicationHelper.getApplicationContext().getBean("baseService", BaseService.class);

	private static SysConfigManager configInstance = new SysConfigManager();

    public static SysConfigManager getInstance() {
		return configInstance;
	}
	
    /**
     * 系统配置的数据
     */
    private Map<String, SysConfig> dataMap = new HashMap<String, SysConfig>();

	public Map<String, SysConfig> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, SysConfig> dataMap) {
		this.dataMap = dataMap;
	}

	public SysConfigManager(){
		init();
	}
    
	/**
	 * 初始化
	 */
    public void init(){
    	List<SysConfig>  scs = sysConfigService.getAll(SysConfig.class);
    	for (SysConfig sc : scs) {
    		dataMap.put(sc.getSysKey(), sc);
		}
    }
}
