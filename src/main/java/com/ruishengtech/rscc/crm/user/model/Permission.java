package com.ruishengtech.rscc.crm.user.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "user_permission")

public class Permission extends CommonDbBean { 

    @Column(meta = "VARCHAR(64)", column = "permission_name")
    private String permissionName;
    
    @Column(meta = "VARCHAR(64)")
    private String permission;

    @Column(meta = "VARCHAR(500)", column = "permission_describe")
    private String permissionDescribe;
    
    //修改角色的菜单权限的时候菜单显示的位置 1：在坐席权限中显示  2：在班长权限中显示 3：在管理员权限中显示
	@Column(meta = "VARCHAR(64)", column = "parent_uuid")
    private String parentUuid;
	
	@Column(meta = "DATETIME")
	private Date date;
	
	@Column(meta = "INT")
	private Integer sequence;
	
	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	//标记权限类型
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getPermissionDescribe() {
		return permissionDescribe;
	}

	public void setPermissionDescribe(String permissionDescribe) {
		this.permissionDescribe = permissionDescribe;
	}

	public String getParentUuid() {
		return parentUuid;
	}

	public void setParentUuid(String parentUuid) {
		this.parentUuid = parentUuid;
	}
	
}
