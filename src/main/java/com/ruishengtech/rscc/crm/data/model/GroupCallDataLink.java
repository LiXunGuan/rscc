package com.ruishengtech.rscc.crm.data.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

//导入记录，防止数据重复
@Table(name = "data_group_call_link")
public class GroupCallDataLink { 
	
	//群呼任务ID
	@Key
	@Index
    @Column(meta = "VARCHAR(64)", column = "groupcall_id")
    private String groupcallId;

	@Column(meta = "VARCHAR(64)", column = "data_id")
	private String dataId;
	
	//呼叫号码
	@Key
	@Index(type = IndexDefinition.TYPE_UNIQUE, method = IndexDefinition.METHOD_HASH)
	@Column(meta = "VARCHAR(64)", column = "data_phone")
	private String dataPhone;
	
	@NColumn(meta = "VARCHAR(1) default '0'", column = "call_flag")
	private String callFlag;
	
	@Index
	@Column(meta = "VARCHAR(64)", column = "data_source")
	private String dataSource;

	private String container;
	
	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public String getCallFlag() {
		return callFlag;
	}

	public void setCallFlag(String callFlag) {
		this.callFlag = callFlag;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
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

}
