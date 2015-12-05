package com.ruishengtech.rscc.crm.user.service;

import java.util.List;

import org.json.JSONArray;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.user.model.Permission;
import com.ruishengtech.rscc.crm.user.model.PermissionRole;
import com.ruishengtech.rscc.crm.user.model.User;

public interface PermissionRoleService {

	public List<PermissionRole> getAllPermissionRole();
	
	public List<String> getAllPermissionRole(ICondition condition);
	
	/**
	 * 通过权限角色名获取权限角色实体类
	 * @param permissionRoleName
	 * @return
	 */
	public PermissionRole getPermissionRoleByName(String permissionRoleName);

	public boolean save(PermissionRole p);

	public boolean save(PermissionRole p, String[] permissions);

	public boolean save(PermissionRole p, String[] permissions, String[] roledataranges);

	public boolean update(PermissionRole p);

	public boolean update(PermissionRole p, String[] permissions);

	public boolean update(PermissionRole p, String[] permissions, String[] roledataranges);

	public boolean deleteById(UUID uuid);
	
	public void deleteChildren(UUID uuid);

	public PermissionRole getByUuid(UUID uuid);

	public PageResult<PermissionRole> queryPage(ICondition condition);
	
	/**
	 * 为权限角色绑定一个权限
	 * @param permissionRoleUuid
	 * @param permissionUuid
	 * @return
	 */
	public boolean bindPermission(String permissionRoleUuid,
                                  String permissionUuid);

	/**
	 * 为权限角色绑定一个权限
	 * @param permissionRoleUuid
	 * @param permissionUuid
	 * @return
	 */
	public boolean bindPermission(UUID permissionRoleUuid, UUID permissionUuid);

	/**
	 * 为权限角色绑定多个权限
	 * @param permissionRoleUuid
	 * @param permissions
	 * @return
	 */
	public int bindPermissions(String permissionRoleUuid,
                               List<String> permissions);

	/**
	 * 为权限角色绑定多个权限
	 * @param permissionRoleUuid
	 * @param permissions
	 * @return
	 */
	public int bindPermissions(UUID permissionRoleUuid, List<UUID> permissions);

	/**
	 * 为权限角色解绑一个权限
	 * @param permissionRoleUuid
	 * @param permissionUuid
	 * @return
	 */
	public boolean unbindPermission(String permissionRoleUuid,
                                    String permissionUuid);

	/**
	 * 为权限角色解绑一个权限
	 * @param permissionRoleUuid
	 * @param permissionUuid
	 * @return
	 */
	public boolean unbindPermission(UUID permissionRoleUuid, UUID permissionUuid);

	/**
	 * 为权限角色解绑多个权限
	 * @param permissionRoleUuid
	 * @param permissions
	 * @return
	 */
	public int unbindPermissions(String permissionRoleUuid,
                                 List<String> permissions);

	/**
	 * 为权限角色解绑多个权限
	 * @param permissionRoleUuid
	 * @param permissions
	 * @return
	 */
	public int unbindPermissions(UUID permissionRoleUuid, List<UUID> permissions);

	/**
	 * 为权限角色更新所有权限，包括增加和删除
	 * @param permissionRoleUuid
	 * @param permissions 更新后的所有权限list
	 * @return
	 */
	public int updatePermissions(String permissionRoleUuid,
                                 List<String> permissions);

	/**
	 * 为权限角色更新所有权限，包括增加和删除
	 * @param permissionRoleUuid
	 * @param permissions 更新后的所有权限list
	 * @return
	 */
	public int updatePermissions(UUID permissionRoleUuid, List<UUID> permissions);

	/**
	 * 获取所有权限UUID list
	 * @param permissionRoleUuid
	 * @return
	 */
	public List<String> getPermissions(String permissionRoleUuid);

	/**
	 * 获取所有权限实体list
	 * @param permissionRoleUuid
	 * @return
	 */
	public List<Permission> getPermissions(PermissionRole permissionRole);

	/**
	 * 获取所有权限UUID list
	 * @param permissionRoleUuid
	 * @return
	 */
	public List<UUID> getPermissions(UUID permissionRoleUuid);
	
	/**
	 * 获取所有子权限角色实体list
	 * @param permissionRoleUuid
	 * @return
	 */
	public List<PermissionRole> getChildren(String permissionRoleUuid);
	
	/**
	 * 获取所有子权限角色实体list
	 * @param permissionRoleUuid
	 * @return
	 */
	public List<PermissionRole> getChildren(UUID permissionRoleUuid);

	public PermissionRole getParent(PermissionRole permissionRole);
	
	public JSONArray getPermissionRoleTree();

	public boolean batDelete(String[] uuids);
	
	public int bindRoleDataranges(String permissionRoleUuid, String[] roledataranges);
	
	public void unbindRoleDataranges(String permissionRoleUuid);
	
	public void updateRoleDataranges(String permissionRoleUuid, String[] roledataranges);
	
	public List<String> getRoleDataranges(String permissionRoleUuid, String datarangeType);
	
	public List<String> getRoleDataranges(String permissionRoleUuid);
	
	public boolean hasDarange(String permissionRoleUuid, String datarangeType, String datarangeUuid);
	
	public boolean hasDarange(String permissionRoleUuid, String datarangeUuid);
	
	public List<String> getRoleUsers(String permissionRoleUuid);
	
	public List<String> getRoleByUser(String userUuid);
	
	public List<String> getUseridByRoleName(String roleName);
	
}