package com.ruishengtech.rscc.crm.data.condition;

import com.ruishengtech.framework.core.db.condition.Page;

public class GroupCallDataCondition extends Page {
	
    private String groupcall_id;

    private String data_phone;
    
    private String container_name;
    
    private String call_flag;

	public String getGroupcall_id() {
		return groupcall_id;
	}

	public void setGroupcall_id(String groupcall_id) {
		this.groupcall_id = groupcall_id;
	}

	public String getData_phone() {
		return data_phone;
	}

	public void setData_phone(String data_phone) {
		this.data_phone = data_phone;
	}

	public String getContainer_name() {
		return container_name;
	}

	public void setContainer_name(String container_name) {
		this.container_name = container_name;
	}

	public String getCall_flag() {
		return call_flag;
	}

	public void setCall_flag(String call_flag) {
		this.call_flag = call_flag;
	}

}
