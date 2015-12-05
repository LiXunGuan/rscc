package com.ruishengtech.rscc.crm.datamanager.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

//导入记录，防止数据重复
@Table(name = "new_data_group_call_result")
public class NewGroupCallResultLink extends CommonDbBean implements GroupCallData { 
	
	//群呼任务ID
	@Index
    @Column(meta = "VARCHAR(64)", column = "groupcall_id")
    private String groupcallId;

	@Index
	@Column(meta = "VARCHAR(64)", column = "data_batch")
	private String dataBatch;
	
	@Index
	@Column(meta = "VARCHAR(64)", column = "data_dept")
	private String dataDept;
	
	//呼叫号码
	@Index(type = IndexDefinition.TYPE_NORMAL, method = IndexDefinition.METHOD_HASH)
	@Column(meta = "VARCHAR(64)", column = "data_phone")
	private String dataPhone;
	
	@NColumn(meta = "VARCHAR(1) default '0'", column = "is_called")
	private String isCalled;
	
	@NColumn(meta = "VARCHAR(1) default '0'", column = "call_result")
	private String callResult;

	private String batchName;
	
	private String deptName;
	
	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getIsCalled() {
		return isCalled;
	}

	public void setIsCalled(String isCalled) {
		this.isCalled = isCalled;
	}

	public String getCallResult() {
		return callResult;
	}

	public void setCallResult(String callResult) {
		this.callResult = callResult;
	}

	public String getDataBatch() {
		return dataBatch;
	}

	public void setDataBatch(String dataBatch) {
		this.dataBatch = dataBatch;
	}

	public String getDataDept() {
		return dataDept;
	}

	public void setDataDept(String dataDept) {
		this.dataDept = dataDept;
	}

	public String getGroupcallId() {
		return groupcallId;
	}

	public void setGroupcallId(String groupcallId) {
		this.groupcallId = groupcallId;
	}

	public String getDataPhone() {
		return dataPhone;
	}

	public void setDataPhone(String dataPhone) {
		this.dataPhone = dataPhone;
	}

	@Override
	public String getBatchUuid() {
		return this.getDataBatch();
	}

	@Override
	public String getOwnDepartment() {
		return this.getDataDept();
	}

	@Override
	public String getPhoneNumber() {
		return this.getDataPhone();
	}

}
