package com.ruishengtech.rscc.crm.user.service;

import java.util.List;
import java.util.Set;

import org.json.JSONArray;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.user.model.Datarange;
import com.ruishengtech.rscc.crm.user.model.DatarangeRole;
import com.ruishengtech.rscc.crm.user.model.Group;
import com.ruishengtech.rscc.crm.user.model.Permission;
import com.ruishengtech.rscc.crm.user.model.PermissionRole;
import com.ruishengtech.rscc.crm.user.model.User;

public interface GroupService {

	/**
	 * 获取所有分组
	 * @return
	 */
	public List<Group> getAllGroup();
	
	/**
	 * 通过分组名获取分组实体
	 * @param name
	 * @return
	 */
	public Group getGroupByName(String name);

	public boolean save(Group g);

	public void save(Group g, String[] excludeFieldName);
	
	public boolean save(Group g, String[] permissionRoles, String[] datarangeRoles);
	
	public boolean update(Group g);

	public void update(Group g, String[] excludeFieldName);
	
	public boolean update(Group g, String[] permissionRoles, String[] datarangeRoles);

	public boolean deleteById(UUID uuid);
	
	public void deleteChildren(UUID uuid);

	public Group getByUuid(UUID uuid);
	
	public List<Permission> getPermissions(Group group);
	
	public List<Datarange> getDataranges(Group group);

	public PageResult<Group> queryPage(ICondition condition);

	/**
	 * 为分组添加一个用户
	 * @param groupUuid
	 * @param userUuid
	 * @return
	 */
	public boolean bindUser(String groupUuid, String userUuid);

	/**
	 * 为分组添加一个用户
	 * @param groupUuid
	 * @param userUuid
	 * @return
	 */
	public boolean bindUser(UUID groupUuid, UUID userUuid);

	/**
	 * 为分组添加多个用户
	 * @param groupUuid
	 * @param users
	 * @return
	 */
	public int bindUsers(String groupUuid, List<String> users);

	/**
	 * 为分组添加多个用户
	 * @param groupUuid
	 * @param users
	 * @return
	 */
	public int bindUsers(UUID groupUuid, List<UUID> users);

	/**
	 * 为分组删除一个用户
	 * @param groupUuid
	 * @param userUuid
	 * @return
	 */
	public boolean unbindUser(String groupUuid, String userUuid);

	/**
	 * 为分组删除一个用户
	 * @param groupUuid
	 * @param userUuid
	 * @return
	 */
	public boolean unbindUser(UUID groupUuid, UUID userUuid);

	/**
	 * 为分组删除多个用户
	 * @param groupUuid
	 * @param users
	 * @return
	 */
	public int unbindUsers(String groupUuid, List<String> users);

	/**
	 * 为分组删除多个用户
	 * @param groupUuid
	 * @param users
	 * @return
	 */
	public int unbindUsers(UUID groupUuid, List<UUID> users);

	/**
	 * 为分组更新多个用户，包含删除和增加
	 * @param groupUuid
	 * @param users 更新后的所有用户
	 * @return 
	 */
	public int updateUsers(String groupUuid, List<String> users);

	/**
	 * 为分组更新多个用户，包含删除和增加
	 * @param groupUuid
	 * @param users 更新后的所有用户
	 * @return 
	 */
	public int updateUsers(UUID groupUuid, List<UUID> users);

	/**
	 * 为分组绑定一个权限角色
	 * @param groupUuid
	 * @param permissionRoleUuid
	 * @return
	 */
	public boolean bindPermissionRole(String groupUuid,
                                      String permissionRoleUuid);

	/**
	 * 为分组绑定一个权限角色
	 * @param groupUuid
	 * @param permissionRoleUuid
	 * @return
	 */
	public boolean bindPermissionRole(UUID groupUuid, UUID permissionRoleUuid);

	/**
	 * 为分组绑定多个权限角色
	 * @param groupUuid
	 * @param permissionRoles
	 * @return
	 */
	public int bindPermissionRoles(String groupUuid,
                                   List<String> permissionRoles);

	/**
	 * 为分组绑定多个权限角色
	 * @param groupUuid
	 * @param permissionRoles
	 * @return
	 */
	public int bindPermissionRoles(UUID groupUuid, List<UUID> permissionRoles);

	/**
	 * 为分组解绑一个权限角色
	 * @param groupUuid
	 * @param permissionRoleUuid
	 * @return
	 */
	public boolean unbindPermissionRole(String groupUuid,
                                        String permissionRoleUuid);

	/**
	 * 为分组解绑一个权限角色
	 * @param groupUuid
	 * @param permissionRoleUuid
	 * @return
	 */
	public boolean unbindPermissionRole(UUID groupUuid, UUID permissionRoleUuid);

	/**
	 * 为分组解绑多个权限角色
	 * @param groupUuid
	 * @param permissionRoles
	 * @return
	 */
	public int unbindPermissionRoles(String groupUuid,
                                     List<String> permissionRoles);

	/**
	 * 为分组解绑多个权限角色
	 * @param groupUuid
	 * @param permissionRoles
	 * @return
	 */
	public int unbindPermissionRoles(UUID groupUuid, List<UUID> permissionRoles);
	
	/**
	 * 为分组更新多个权限角色，包括增加和删除
	 * @param groupUuid
	 * @param permissionRoles 更新后的所有权限角色
	 * @return
	 */
	public int updatePermissionRoles(String groupUuid,
                                     List<String> permissionRoles);
	
	/**
	 * 为分组更新多个权限角色，包括增加和删除
	 * @param groupUuid
	 * @param permissionRoles 更新后的所有权限角色
	 * @return
	 */
	public int updatePermissionRoles(UUID userUuid, List<UUID> permissionRoles);

	/**
	 * 为分组绑定一个范围角色
	 * @param groupUuid
	 * @param datarangeRoleUuid
	 * @return
	 */
	public boolean bindDatarangeRole(String groupUuid, String datarangeRoleUuid);

	/**
	 * 为分组绑定一个范围角色
	 * @param groupUuid
	 * @param datarangeRoleUuid
	 * @return
	 */
	public boolean bindDatarangeRole(UUID groupUuid, UUID datarangeRoleUuid);

	/**
	 * 为分组绑定多个范围角色
	 * @param groupUuid
	 * @param datarangeRoles
	 * @return
	 */
	public int bindDatarangeRoles(String groupUuid, List<String> datarangeRoles);

	/**
	 * 为分组绑定多个范围角色
	 * @param groupUuid
	 * @param datarangeRoles
	 * @return
	 */
	public int bindDatarangeRoles(UUID groupUuid, List<UUID> datarangeRoles);

	/**
	 * 为分组解绑一个范围角色
	 * @param groupUuid
	 * @param datarangeRoleUuid
	 * @return
	 */
	public boolean unbindDatarangeRole(String groupUuid,
                                       String datarangeRoleUuid);

	/**
	 * 为分组解绑一个范围角色
	 * @param groupUuid
	 * @param datarangeRoleUuid
	 * @return
	 */
	public boolean unbindDatarangeRole(UUID groupUuid, UUID datarangeRoleUuid);

	/**
	 * 为分组解绑多个范围角色
	 * @param groupUuid
	 * @param datarangeRoles
	 * @return
	 */
	public int unbindDatarangeRoles(String groupUuid,
                                    List<String> datarangeRoles);

	/**
	 * 为分组解绑多个范围角色
	 * @param groupUuid
	 * @param datarangeRoles
	 * @return
	 */
	public int unbindDatarangeRoles(UUID groupUuid, List<UUID> datarangeRoles);

	/**
	 * 为分组更新多个范围角色，包括添加和删除
	 * @param groupUuid
	 * @param DatarangeRoles 更新后的所有范围角色
	 * @return
	 */
	public int updateDatarangeRoles(String groupUuid,
                                    List<String> DatarangeRoles);

	/**
	 * 为分组更新多个范围角色，包括添加和删除
	 * @param groupUuid
	 * @param DatarangeRoles 更新后的所有范围角色
	 * @return
	 */
	public int updateDatarangeRoles(UUID groupUuid, List<UUID> DatarangeRoles);

	/**
	 * 获取分组所有用户UUID list
	 * @param groupUuid
	 * @return
	 */
	public List<String> getUsers(String groupUuid);

	/**
	 * 获取分组所有用户UUID list
	 * @param groupUuid
	 * @return
	 */
	public List<UUID> getUsers(UUID groupUuid);

	/**
	 * 获取所有权限角色UUID list
	 * @param groupUuid
	 * @return
	 */
	public List<String> getPermissionRoles(String groupUuid);

	/**
	 * 获取所有权限角色UUID list
	 * @param groupUuid
	 * @return
	 */
	public List<UUID> getPermissionRoles(UUID groupUuid);

	/**
	 * 获取所有范围角色UUID list
	 * @param groupUuid
	 * @return
	 */
	public List<String> getDatarangeRoles(String groupUuid);

	/**
	 * 获取所有范围角色UUID list
	 * @param groupUuid
	 * @return
	 */
	public List<UUID> getDatarangeRoles(UUID groupUuid);

	/**
	 * 获取所有用户实体list
	 * @param group
	 * @return
	 */
	public List<User> getUsers(Group group);

	/**
	 * 获取所有权限角色实体list
	 * @param group
	 * @return
	 */
	public List<PermissionRole> getPermissionRoles(Group group);

	/**
	 * 获取所有范围角色实体list
	 * @param group
	 * @return
	 */
	public List<DatarangeRole> getDatarangeRoles(Group group);

	/**
	 * 获取所有子分组实体list
	 * @param groupUuid
	 * @return
	 */
	public List<Group> getChildren(String groupUuid);

	/**
	 * 获取所有子分组实体list
	 * @param groupUuid
	 * @return
	 */
	public List<Group> getChildren(UUID groupUuid);

	/**
	 * 获取所有可操作范围
	 * @param groupUuid
	 * @return
	 */
	public Set<String> getDataranges(String groupUuid);

	/**
	 * 获取所有可操作范围
	 * @param groupUuid
	 * @return
	 */
	public Set<UUID> getDataranges(UUID groupUuid);

	/**
	 * 获取所有可操作权限
	 * @param groupUuid
	 * @return
	 */
	public Set<String> getPermissions(String groupUuid);

	/**
	 * 获取所有可操作权限
	 * @param groupUuid
	 * @return
	 */
	public Set<UUID> getPermissions(UUID groupUuid);
	
	/**
	 * 获取分组树Json对象
	 * @return
	 */
	
	public Group getParent(Group group) ;
	
	public JSONArray getGroupTree();

}