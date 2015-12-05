package com.ruishengtech.rscc.crm.ui.mw.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * Created by yaoliceng on 2014/10/21.
 */
@Table(name = "mw.fs_accessnumber_gateway")
public class AccessNumberGateway extends CommonDbBean {


    @Column(meta = "INT")
    private Long accessnumber_id;
    
    @Column(meta = "INT")
    private Long gateway_id;

    
	public Long getAccessnumber_id() {
		return accessnumber_id;
	}

	public void setAccessnumber_id(Long accessnumber_id) {
		this.accessnumber_id = accessnumber_id;
	}

	public Long getGateway_id() {
		return gateway_id;
	}

	public void setGateway_id(Long gateway_id) {
		this.gateway_id = gateway_id;
	}
    
   
}
