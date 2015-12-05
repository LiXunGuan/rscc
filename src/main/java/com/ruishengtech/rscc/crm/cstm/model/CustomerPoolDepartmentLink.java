package com.ruishengtech.rscc.crm.cstm.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "cstm_pool_department_link")
public class CustomerPoolDepartmentLink { 

	@Key
	@Column(meta = "VARCHAR(64)", column = "cstm_pool_uuid")
	@Index(name = "cstm_pool_uuid",type = IndexDefinition.TYPE_NORMAL)
	private String cstm_pool_uuid;
	
	@Key
	@Column(meta = "VARCHAR(64)", column = "department_uuid")
	@Index(name = "department_uuid",type = IndexDefinition.TYPE_NORMAL)
	private String department_uuid;


	public String getDepartment_uuid() {
		return department_uuid;
	}

	public void setDepartment_uuid(String department_uuid) {
		this.department_uuid = department_uuid;
	}

	public String getCstm_pool_uuid() {
		return this.cstm_pool_uuid;
	}

	public void setCstm_pool_uuid(String cstm_pool_uuid) {
		this.cstm_pool_uuid = cstm_pool_uuid;
	}
	
}
