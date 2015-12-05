package com.ruishengtech.rscc.crm.data.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "data_container_department_link")

public class DataContainerDepartmentLink { 

	@Key
	@Column(meta = "VARCHAR(64)", column = "datacontainer_uuid")
	private String datacontainer_uuid;
	
	@Key
	@Column(meta = "VARCHAR(64)", column = "department_uuid")
	private String department_uuid;

	public String getDatacontainer_uuid() {
		return datacontainer_uuid;
	}

	public void setDatacontainer_uuid(String datacontainer_uuid) {
		this.datacontainer_uuid = datacontainer_uuid;
	}

	public String getDepartment_uuid() {
		return department_uuid;
	}

	public void setDepartment_uuid(String department_uuid) {
		this.department_uuid = department_uuid;
	}
	
}
