package com.ruishengtech.rscc.crm.datamanager.condition;

import com.ruishengtech.framework.core.db.condition.Page;

public class NewGroupCallDataCondition extends Page {
	
    private String groupcall_id;

    private String data_phone;
    
    private String call_result;
    
    private String dept_name;
    
    private String batch_name;

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

	public String getCall_result() {
		return call_result;
	}

	public void setCall_result(String call_result) {
		this.call_result = call_result;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getBatch_name() {
		return batch_name;
	}

	public void setBatch_name(String batch_name) {
		this.batch_name = batch_name;
	}
	
}
