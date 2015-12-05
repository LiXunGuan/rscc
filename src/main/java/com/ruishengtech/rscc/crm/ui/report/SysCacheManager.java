package com.ruishengtech.rscc.crm.ui.report;

import java.util.HashMap;
import java.util.Map;


public class SysCacheManager {
	
    private static SysCacheManager ourInstance = new SysCacheManager();
    
    private SysCacheManager() {
    }
    public static SysCacheManager getInstance() {
        return ourInstance;
    }
    private Map<String, String> linuxInfoMap = new HashMap<String, String>();
    private String netname;

	public String getNetname() {
		return netname;
	}
	
	public void setNetname(String netname) {
		this.netname = netname;
	}
	public Map<String, String> getLinuxInfoMap() {
		return linuxInfoMap;
	}
	
}
