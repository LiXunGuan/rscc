package com.ruishengtech.rscc.crm.user.service;

import java.util.Collection;
import java.util.List;

import org.json.JSONArray;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.user.model.Permission;

public interface PermissionService {
	
	public List<Permission> getAllPermission();
	
	public List<String> getPermissions();
	
	/**
	 * 通过权限名获取权限实体
	 * @param permissionName
	 * @return
	 */
	public Permission getPermissionByName(String permissionName);

	public boolean save(Permission p);

	public void save(Permission p, String[] excludeFieldName);

	public boolean update(Permission p);

	public void update(Permission p, String[] excludeFieldName);

	public boolean deleteById(UUID uuid);
	
	public void deleteChildren(UUID uuid);

	public Permission getByUuid(UUID uuid);

	public List<Permission> getByList(Collection<UUID> l);
	
	public PageResult<Permission> queryPage(ICondition condition);
	
	/**
	 * 获取子权限实体list
	 * @param permissionUuid
	 * @return
	 */
	public List<Permission> getChildren(String permissionUuid);

	/**
	 * 获取子权限实体list
	 * @param permissionUuid
	 * @return
	 */
	public List<Permission> getChildren(UUID permissionUuid);

	public Permission getParent(Permission permission);
	
	public JSONArray getPermissionTree();
	
	public List<String> getAdminPermissions();
	
}