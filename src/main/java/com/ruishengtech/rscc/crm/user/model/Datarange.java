package com.ruishengtech.rscc.crm.user.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "user_datarange")

public class Datarange extends CommonDbBean { 

	@Index
    @Column(meta = "VARCHAR(64)", column = "datarange_name")
    private String datarangeName;

    @Column(meta = "VARCHAR(500)", column = "datarange_describe")
    private String datarangeDescribe;

	@Column(meta = "VARCHAR(64)", column = "parent_uuid")
    private String parentUuid;
	
	@NColumn(meta = "VARCHAR(64)", column = "type_uuid")
    private String typeUuid;
	
	public String getTypeUuid() {
		return typeUuid;
	}

	public void setTypeUuid(String typeUuid) {
		this.typeUuid = typeUuid;
	}

	@Column(meta = "DATETIME")
	private Date date;
	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDatarangeName() {
		return datarangeName;
	}

	public void setDatarangeName(String datarangeName) {
		this.datarangeName = datarangeName;
	}

	public String getDatarangeDescribe() {
		return datarangeDescribe;
	}

	public void setDatarangeDescribe(String datarangeDescribe) {
		this.datarangeDescribe = datarangeDescribe;
	}

	public String getParentUuid() {
		return parentUuid;
	}

	public void setParentUuid(String parentUuid) {
		this.parentUuid = parentUuid;
	}

}
