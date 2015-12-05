package com.ruishengtech.rscc.crm.datamanager.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

//导入记录，防止数据重复
@Table(name = "new_data_group_call_link")
public class NewGroupCallDataLink { 
	
	//群呼任务ID
	@Key
    @Column(meta = "VARCHAR(64)", column = "groupcall_id")
    private String groupcallId;

	@Key
	@Column(meta = "VARCHAR(64)", column = "data_batch")
	private String dataBatch;
	
	@Key
	@Column(meta = "VARCHAR(64)", column = "data_dept")
	private String dataDept;
	
	@NColumn(meta = "INT DEFAULT 0", column = "data_count")
	private Integer dataCount;

	private Integer noAnswer;
	
	private Integer singleAnswer;
	
	private Integer answered;
	
	private String batchName;
	
	private String deptName;
	
	public Integer getDataCount() {
		return dataCount;
	}
	
	public void setDataCount(Integer dataCount) {
		this.dataCount = dataCount;
	}
	
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

	public Integer getNoAnswer() {
		return noAnswer;
	}

	public void setNoAnswer(Integer noAnswer) {
		this.noAnswer = noAnswer;
	}

	public Integer getSingleAnswer() {
		return singleAnswer;
	}

	public void setSingleAnswer(Integer singleAnswer) {
		this.singleAnswer = singleAnswer;
	}

	public Integer getAnswered() {
		return answered;
	}

	public void setAnswered(Integer answered) {
		this.answered = answered;
	}

	public String getGroupcallId() {
		return groupcallId;
	}

	public void setGroupcallId(String groupcallId) {
		this.groupcallId = groupcallId;
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

}
