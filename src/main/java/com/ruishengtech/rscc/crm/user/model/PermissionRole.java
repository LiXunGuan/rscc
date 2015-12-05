package com.ruishengtech.rscc.crm.user.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "user_permission_role")

public class PermissionRole extends CommonDbBean { 

    @Column(meta = "VARCHAR(64)", column = "role_name")
    private String permissionRoleName;
    
    @Column(meta = "VARCHAR(500)", column = "permissionrole_describe")
    private String permissionRoleDescribe;
    
	@NColumn(meta = "VARCHAR(64)", column = "parent_uuid")
    private String parentUuid;
	
	@Column(meta = "DATETIME")
	private Date date;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	public String getPermissionRoleName()
	{
		return permissionRoleName;
	}

	public void setPermissionRoleName(String permissionRoleName)
	{
		this.permissionRoleName = permissionRoleName;
	}

	public String getPermissionRoleDescribe()
	{
		return permissionRoleDescribe;
	}

	public void setPermissionRoleDescribe(String permissionRoleDescribe)
	{
		this.permissionRoleDescribe = permissionRoleDescribe;
	}

	public String getParentUuid() {
		return parentUuid;
	}

	public void setParentUuid(String parentUuid) {
		this.parentUuid = parentUuid;
	}

	
}
