package com.ruishengtech.rscc.crm.user.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "user_datarange_role")

public class DatarangeRole extends CommonDbBean { 

    @Column(meta = "VARCHAR(64)", column = "role_name")
    private String datarangeRoleName;
    
    @Column(meta = "VARCHAR(500)", column = "datarangerole_describe")
    private String datarangeRoleDescribe;
    
	@Column(meta = "VARCHAR(64)", column = "parent_uuid")
    private String parentUuid;
	
	@Column(meta = "DATETIME")
	private Date date;

	public String getDatarangeRoleName()
	{
		return datarangeRoleName;
	}

	public void setDatarangeRoleName(String datarangeRoleName)
	{
		this.datarangeRoleName = datarangeRoleName;
	}

	public String getDatarangeRoleDescribe()
	{
		return datarangeRoleDescribe;
	}

	public void setDatarangeRoleDescribe(String datarangeRoleDescribe)
	{
		this.datarangeRoleDescribe = datarangeRoleDescribe;
	}

	public String getParentUuid() {
		return parentUuid;
	}

	public void setParentUuid(String parentUuid) {
		this.parentUuid = parentUuid;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}
	
	
	
}
