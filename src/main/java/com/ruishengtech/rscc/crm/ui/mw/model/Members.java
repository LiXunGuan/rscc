package com.ruishengtech.rscc.crm.ui.mw.model;

import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Table;

/**
 * 
 * @author chengxin
 *
 */
@Table(name = "runtime_members")
public class Members extends CommonDbBean{


    private String name;

    private String cid_number;
	
    private Long joined_epoch;
    
    

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCid_number() {
		return cid_number;
	}

	public void setCid_number(String cid_number) {
		this.cid_number = cid_number;
	}

	public Long getJoined_epoch() {
		return joined_epoch;
	}

	public void setJoined_epoch(Long joined_epoch) {
		this.joined_epoch = joined_epoch;
	}
	
	

   
}
