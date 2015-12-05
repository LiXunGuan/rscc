package com.ruishengtech.rscc.crm.ui.sysConfig.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Table;

/**
 * 
 * @author chengxin
 *
 */
@Table(name = "sys_config")
public class SysConfig extends CommonDbBean{

    /**
     * 键值
     */
    @Column(meta = "VARCHAR(32)")
    private String sysKey;
    
    /**
     * 值
     */
    @Column(meta = "VARCHAR(500)")
    private String sysVal;
    

	public String getSysKey() {
		return sysKey;
	}

	public void setSysKey(String sysKey) {
		this.sysKey = sysKey;
	}

	public String getSysVal() {
		return sysVal;
	}

	public void setSysVal(String sysVal) {
		this.sysVal = sysVal;
	}
}
