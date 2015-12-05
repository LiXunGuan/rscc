package com.ruishengtech.rscc.crm.user.service.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.user.model.Action;
import com.ruishengtech.rscc.crm.user.model.Datarange;
import com.ruishengtech.rscc.crm.user.model.DatarangeRole;
import com.ruishengtech.rscc.crm.user.model.Group;
import com.ruishengtech.rscc.crm.user.model.Permission;
import com.ruishengtech.rscc.crm.user.model.PermissionRole;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.ActionService;
import com.ruishengtech.rscc.crm.user.service.DatarangeRoleService;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.service.GroupService;
import com.ruishengtech.rscc.crm.user.service.PermissionRoleService;
import com.ruishengtech.rscc.crm.user.service.PermissionService;
import com.ruishengtech.rscc.crm.user.service.UserService;
import com.ruishengtech.rscc.crm.user.solution.UserSolution;
import com.ruishengtech.rscc.crm.user.util.CollectionUtil;

@Service
@Transactional
public class UserServiceImp extends BaseService implements UserService {

	private List<User> userList;
	
	@Autowired
	private GroupService groupService; 
	
	@Autowired
	private DatarangeRoleService datarangeRoleService;
	
	@Autowired
	private PermissionRoleService permissionRoleService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private DatarangeService datarangeService;
	
	@Autowired
	private ActionService actionService;
	
	//获取未删除的所有用户
	public List<User> getAllUser() {
		if (userList == null) {
			userList = getBeanListWithSql(User.class,"select t.*,p.datarange_name departmentName from user_user t join user_datarange p on t.department=p.uuid and t.delete_flag=0 ");
		}
		return userList;
	}
	
	public void refreshUserList() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				userList = getBeanListWithSql(User.class,"select t.*,p.datarange_name departmentName from user_user t join user_datarange p on t.department=p.uuid and t.delete_flag=0 ");
			}
		}).start();
	}
	
	
	public boolean save(User u) {
		super.save(u);
		return true;
	}
	
	public void save(User u, String[] excludeFieldName) {
		super.save(u, excludeFieldName);
	}
	
	public boolean update(User u) {
		super.update(u, new String[]{"adminFlag"});	
		refreshUserList();
        return true;
    }
	
	public void update(User u, String[] excludeFieldName) {
		 super.update(u, excludeFieldName);
		 refreshUserList();
	}
	
	public boolean deleteById(UUID uuid) {
//			super.deleteById(User.class, uuid);
		if ("0".equals(uuid.toString()))
			return false;
		jdbcTemplate.update("UPDATE user_user SET delete_flag='1' WHERE uuid=?",uuid.toString());
		jdbcTemplate.update("DELETE FROM user_user_group_link WHERE user_uuid=?", uuid.toString());
		jdbcTemplate.update("DELETE FROM user_user_permissionrole_link WHERE user_uuid=?", uuid.toString());
		jdbcTemplate.update("DELETE FROM user_user_datarangerole_link WHERE user_uuid=?", uuid.toString());
		refreshUserList();
		return true;
	}
	
	public boolean deleteById(UUID uuid, boolean refresh) {
		if ("0".equals(uuid.toString()))
			return false;
		jdbcTemplate.update("UPDATE user_user SET delete_flag='1' WHERE uuid=?",uuid.toString());
		jdbcTemplate.update("DELETE FROM user_user_group_link WHERE user_uuid=?", uuid.toString());
		jdbcTemplate.update("DELETE FROM user_user_permissionrole_link WHERE user_uuid=?", uuid.toString());
		jdbcTemplate.update("DELETE FROM user_user_datarangerole_link WHERE user_uuid=?", uuid.toString());
		if (refresh) {
			refreshUserList();
		}
		return true;
	}
	
	//这里需要删除的也可以查询
	public User getByUuid(UUID uuid) {
		User u = super.getByUuid(User.class, uuid);
		if (u == null)
			return null;
		Datarange d = datarangeService.getByUuid(UUID.UUIDFromString(u.getDepartment()));
		if	(d != null) {
			u.setDepartmentName(d.getDatarangeName());
		} else {
			u.setDepartmentName("无");
		}
		return u;
	}
	
	//这里不能查询删除的，在solution中写出来了
	public PageResult<User> queryPage(ICondition condition) {
		return super.queryPage(new UserSolution(), condition, User.class);
	}
	
	//不允许删除的登录
	public User login(final String loginName, final String password) {
		List<User> list = getBeanList(User.class, "and loginname=? and password=? and delete_flag='0' ", loginName, password);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	//允许查询删除的
	public User getUserByLoginName(final String loginName, boolean isContainDeleted) {
		List<User> list = isContainDeleted?getBeanListWithOrder(User.class, "and loginname=?", "delete_flag ", loginName):getBeanList(User.class, "and loginname=? and delete_flag!='1'", loginName);
		if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	//根据验证信息查询用户
	public User getUserByCheckurl(String checkurl){
		List<User> list = getBeanList(User.class, "and checkurl=?", checkurl);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	//允许查询删除的
	public User getUserByPhone(final String phone) {
		List<User> list = getBeanList(User.class, "and phone=?", phone);
		if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}

	
	public boolean bindGroup(String userUuid, String groupUuid) {
		if(!StringUtils.isBlank(userUuid) && !StringUtils.isBlank(groupUuid))
			return 1==jdbcTemplate.update("INSERT INTO user_user_group_link (user_uuid, group_uuid) VALUES (?, ?)", userUuid, groupUuid);
		return true;
	}
	
	
	public boolean bindGroup(UUID userUuid, UUID groupUuid) {
		return bindGroup(userUuid.toString(), groupUuid.toString());
	}

	
	public int bindGroups(String userUuid, List<String> groups) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String g:groups)
			if(!StringUtils.isBlank(g))
				list.add(new Object[]{userUuid,g});
		int[] nums = jdbcTemplate.batchUpdate("INSERT INTO user_user_group_link (user_uuid, group_uuid) VALUES (?, ?)", list);
		return CollectionUtil.sum(nums);
	}
	
	
	public int bindGroups(UUID userUuid, List<UUID> groups) {
		return bindGroups(userUuid.toString(), CollectionUtil.uuidToString(groups));
	}

	
	public boolean unbindGroup(String userUuid, String groupUuid) {
		if(!StringUtils.isBlank(userUuid) && !StringUtils.isBlank(groupUuid))
			return 1==jdbcTemplate.update("DELETE FROM user_user_group_link WHERE user_uuid=? and group_uuid=?", userUuid, groupUuid);
		return true;
	}
	
	
	public boolean unbindGroup(UUID userUuid, UUID groupUuid) {
		return unbindGroup(userUuid.toString(), groupUuid.toString());
	}

	
	public int unbindGroups(String userUuid, List<String> groups) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String g:groups)
			if(!StringUtils.isBlank(g))
				list.add(new Object[]{userUuid,g});
		int[] nums = jdbcTemplate.batchUpdate("DELETE FROM user_user_group_link WHERE user_uuid=? and group_uuid=?", list);
		return CollectionUtil.sum(nums);
	}
	
	
	public int unbindGroups(UUID userUuid, List<UUID> groups) {
		return unbindGroups(userUuid.toString(), CollectionUtil.uuidToString(groups));
	}

	
	public int updateGroups(String userUuid, List<String> groups) {
		List<String> oldGroups = getGroups(userUuid);
		List<String> unbindGroups = new ArrayList<String>(oldGroups);
		List<String> bindGroups = new ArrayList<String>(groups);
		unbindGroups.removeAll(groups);
		bindGroups.removeAll(oldGroups);
		return unbindGroups(userUuid, unbindGroups) + bindGroups(userUuid, bindGroups);
	}
	
	
	public int updateGroups(UUID userUuid, List<UUID> groups) {
		return updateGroups(userUuid.toString(), CollectionUtil.uuidToString(groups));
	}

	
	public boolean bindPermissionRole(String userUuid, String permissionRoleUuid) {
		if(!StringUtils.isBlank(userUuid) && !StringUtils.isBlank(permissionRoleUuid))
			return 1==jdbcTemplate.update("INSERT INTO user_user_permissionrole_link (user_uuid, permissionrole_uuid) VALUES (?, ?)", userUuid, permissionRoleUuid);
		return true;
	}
	
	
	public boolean bindPermissionRole(UUID userUuid, UUID permissionRoleUuid) {
		return bindPermissionRole(userUuid.toString(), permissionRoleUuid.toString());
	}
	
	
	public int bindPermissionRoles(String userUuid, List<String> permissionRoles) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String p:permissionRoles)
			if(!StringUtils.isBlank(p))
				list.add(new Object[]{userUuid,p});
		int[] nums = jdbcTemplate.batchUpdate("INSERT INTO user_user_permissionrole_link (user_uuid, permissionrole_uuid) VALUES (?, ?)", list);
		return CollectionUtil.sum(nums);
	}
	
	
	public int bindPermissionRoles(UUID userUuid, List<UUID> permissionRoles) {
		return unbindPermissionRoles(userUuid.toString(), CollectionUtil.uuidToString(permissionRoles));
	}

	
	public boolean unbindPermissionRole(String userUuid, String permissionRoleUuid) {
		if(!StringUtils.isBlank(userUuid) && !StringUtils.isBlank(permissionRoleUuid))
			return 1==jdbcTemplate.update("DELETE FROM user_user_permissionrole_link WHERE user_uuid=? and permissionrole_uuid=?", userUuid, permissionRoleUuid);
		return true;
	}
	
	
	public boolean unbindPermissionRole(UUID userUuid, UUID permissionRoleUuid) {
		return unbindPermissionRole(userUuid.toString(), permissionRoleUuid.toString());
	}

	
	public int unbindPermissionRoles(String userUuid, List<String> permissionRoles) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String p:permissionRoles)
			if(!StringUtils.isBlank(p))
				list.add(new Object[]{userUuid,p});
		int[] nums = jdbcTemplate.batchUpdate("DELETE FROM user_user_permissionrole_link WHERE user_uuid=? and permissionrole_uuid=?", list);
		return CollectionUtil.sum(nums);
	}
	
	
	public int unbindPermissionRoles(UUID userUuid, List<UUID> permissionRoles) {
		return unbindPermissionRoles(userUuid.toString(), CollectionUtil.uuidToString(permissionRoles));
	}

	
	public int updatePermissionRoles(String userUuid, List<String> permissionRoles) {
		List<String> oldPermissionRoles = getPermissionRoles(userUuid);
		List<String> unbindPermissionRoles = new ArrayList<String>(oldPermissionRoles);
		List<String> bindPermissionRoles = new ArrayList<String>(permissionRoles);
		unbindPermissionRoles.removeAll(permissionRoles);
		bindPermissionRoles.removeAll(oldPermissionRoles);
		return unbindPermissionRoles(userUuid, unbindPermissionRoles) + bindPermissionRoles(userUuid, bindPermissionRoles);
	}
	
	
	public int updatePermissionRoles(UUID userUuid, List<UUID> permissionRoles) {
		return updatePermissionRoles(userUuid.toString(), CollectionUtil.uuidToString(permissionRoles));
	}

	
	public boolean bindDatarangeRole(String userUuid, String datarangeRoleUuid) {
		if(!StringUtils.isBlank(userUuid) && !StringUtils.isBlank(datarangeRoleUuid))
			return 1==jdbcTemplate.update("INSERT INTO user_user_datarangerole_link (user_uuid, datarangerole_uuid) VALUES (?, ?)", userUuid, datarangeRoleUuid);
		return true;
	}
	
	
	public boolean bindDatarangeRole(UUID userUuid, UUID datarangeRoleUuid) {
		return bindDatarangeRole(userUuid.toString(), datarangeRoleUuid.toString());
	}

	
	public int bindDatarangeRoles(String userUuid, List<String> datarangeRoles) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String d:datarangeRoles)
			if(!StringUtils.isBlank(d))
				list.add(new Object[]{userUuid,d});
		int[] nums = jdbcTemplate.batchUpdate("INSERT INTO user_user_datarangerole_link (user_uuid, datarangerole_uuid) VALUES (?, ?)", list);
		return CollectionUtil.sum(nums);
	}
	
	
	public int bindDatarangeRoles(UUID userUuid, List<UUID> datarangeRoles) {
		return bindDatarangeRoles(userUuid.toString(), CollectionUtil.uuidToString(datarangeRoles));
	} 

	
	public boolean unbindDatarangeRole(String userUuid, String datarangeRoleUuid) {
		if(!StringUtils.isBlank(userUuid) && !StringUtils.isBlank(datarangeRoleUuid))
			return 1==jdbcTemplate.update("DELETE FROM user_user_datarangerole_link WHERE user_uuid=? and datarangerole_uuid=?", userUuid, datarangeRoleUuid);
		return true;
	}
	
	
	public boolean unbindDatarangeRole(UUID userUuid, UUID datarangeRoleUuid) {
		return unbindDatarangeRole(userUuid.toString(), datarangeRoleUuid.toString());
	}

	
	public int unbindDatarangeRoles(String userUuid, List<String> datarangeRoles) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String d:datarangeRoles)
			if(!StringUtils.isBlank(d))
				list.add(new Object[]{userUuid,d});
		int[] nums = jdbcTemplate.batchUpdate("DELETE FROM user_user_datarangerole_link WHERE user_uuid=? and datarangerole_uuid=?", list);
		return CollectionUtil.sum(nums);
	}
	
	
	public int unbindDatarangeRoles(UUID userUuid, List<UUID> datarangeRoles) {
		return unbindDatarangeRoles(userUuid.toString(), CollectionUtil.uuidToString(datarangeRoles));
	}

	
	public int updateDatarangeRoles(String userUuid, List<String> DatarangeRoles) {
		List<String> oldDatarangeRoles = getDatarangeRoles(userUuid);
		List<String> unbindDatarangeRoles = new ArrayList<String>(oldDatarangeRoles);
		List<String> bindDatarangeRoles = new ArrayList<String>(DatarangeRoles);
		unbindDatarangeRoles.removeAll(DatarangeRoles);
		bindDatarangeRoles.removeAll(oldDatarangeRoles);
		return unbindDatarangeRoles(userUuid, unbindDatarangeRoles) + bindDatarangeRoles(userUuid, bindDatarangeRoles);
	}
	
	
	public int updateDatarangeRoles(UUID userUuid, List<UUID> DatarangeRoles) {
		return updateDatarangeRoles(userUuid.toString(), CollectionUtil.uuidToString(DatarangeRoles));
	}

	
	public List<String> getGroups(String userUuid) {
		List<String> list = jdbcTemplate.queryForList("SELECT group_uuid FROM user_user_group_link WHERE user_uuid = ?", String.class, userUuid);
		return list;
	}
	
	
	public List<UUID> getGroups(UUID userUuid) {
		List<String> list = getGroups(userUuid.toString());
		return CollectionUtil.stringToUuid(list);
	}

	
	public List<String> getPermissionRoles(String userUuid) {
		List<String> list = jdbcTemplate.queryForList("SELECT permissionrole_uuid FROM user_user_permissionrole_link WHERE user_uuid = ?", String.class, userUuid);
		return list;
	}
	
	
	public List<UUID> getPermissionRoles(UUID userUuid) {
		List<String> list = getPermissionRoles(userUuid.toString());
		return CollectionUtil.stringToUuid(list);
	}

	
	public List<String> getDatarangeRoles(String userUuid) {
		List<String> list = jdbcTemplate.queryForList("SELECT datarangerole_uuid FROM user_user_datarangerole_link WHERE user_uuid = ?", String.class, userUuid);
		return list;
	}
	
	
	public List<UUID> getDatarangeRoles(UUID userUuid) {
		List<String> list = getDatarangeRoles(userUuid.toString());
		return CollectionUtil.stringToUuid(list);
	}	

	
	public List<Group> getGroups(User user) {
		List<Group> list = getBeanList(Group.class, "and uuid IN (SELECT group_uuid FROM user_user_group_link WHERE user_uuid=?)", user.getUuid().toString());
		return list;
	}

	
	public List<PermissionRole> getPermissionRoles(User user) {
		List<PermissionRole> list = getBeanList(PermissionRole.class, "and uuid IN (SELECT permissionrole_uuid FROM user_user_permissionrole_link WHERE user_uuid=?)", user.getUuid().toString());
		return list;
	}

	
	public List<DatarangeRole> getDatarangeRoles(User user) {
		List<DatarangeRole> list = getBeanList(DatarangeRole.class, "and uuid IN (SELECT datarangerole_uuid FROM user_user_datarangerole_link WHERE user_uuid=?)", user.getUuid().toString());
		return list;
	}

	
	public Set<String> getAllPermissionRoles(String userUuid) {
		Set<String> set = new HashSet<String>(getPermissionRoles(userUuid));
		List<String> groups = getGroups(userUuid);
		for(String g:groups)
			set.addAll(groupService.getPermissionRoles(g));
		return set;
	}
	
	
	public Set<UUID> getAllPermissionRoles(UUID userUuid) {
		Set<UUID> set = new HashSet<UUID>(getPermissionRoles(userUuid));
		List<UUID> groups = getGroups(userUuid);
		for(UUID g:groups)
			set.addAll(groupService.getPermissionRoles(g));
		return set;
	}

	
	public Set<String> getAllDatarangeRoles(String userUuid) {
		Set<String> set = new HashSet<String>(getDatarangeRoles(userUuid));
		List<String> groups = getGroups(userUuid);
		for(String g:groups)
			set.addAll(groupService.getDatarangeRoles(g));
		return set;
	}
	
	
	public Set<UUID> getAllDatarangeRoles(UUID userUuid) {
		Set<UUID> set = new HashSet<UUID>(getDatarangeRoles(userUuid));
		List<UUID> groups = getGroups(userUuid);
		for(UUID g:groups)
			set.addAll(groupService.getDatarangeRoles(g));
		return set;
	}

	
//	public Set<String> getDataranges(String userUuid) {
//		Set<String> dataranges = new HashSet<String>();
//		Set<String> set = getAllDatarangeRoles(userUuid);
//		for(String d:set)
//			dataranges.addAll(datarangeRoleService.getDataranges(d));
//		return dataranges;
//	}
	
	public Set<String> getDataranges(String userUuid) {
		Set<String> list = new HashSet<String>(jdbcTemplate.queryForList("SELECT datarange_uuid FROM user_user_datarange_link WHERE user_uuid = ?", String.class, userUuid));
		Set<String> result = new HashSet<>();
		for (String str : list) {
			if (!result.contains(str)) {
				result.addAll(datarangeService.getAllChildren(str));
			}
		}
		return result;
	}
	
	public List<String> getDataranges(String userUuid, String type) {
		List<String> list = jdbcTemplate.queryForList("SELECT datarange_uuid FROM user_user_datarange_link WHERE user_uuid = ? and datarange_type = ?", String.class, userUuid, type);
		return list;
	}
	
	public List<User> getUserByDescribe(String describe){
		List<User> users = super.getBeanList(User.class, " AND user_describe = ? AND delete_flag != 1 ", describe);
		return users;
	}
	
	public Set<UUID> getDataranges(UUID userUuid) {
		Set<UUID> dataranges = new HashSet<UUID>();
		Set<UUID> set = getAllDatarangeRoles(userUuid);
		for(UUID d:set)
			dataranges.addAll(datarangeRoleService.getDataranges(d));
		return dataranges;
	}

	
	public Set<String> getPermissions(String userUuid) {
		Set<String> permissions = new HashSet<String>();
		Set<String> set = getAllPermissionRoles(userUuid);
		for(String p:set)
			permissions.addAll(permissionRoleService.getPermissions(p));
		return permissions;
	}
	
	
	public Set<UUID> getPermissions(UUID userUuid) {
		Set<UUID> permissions = new HashSet<UUID>();
		Set<UUID> set = getAllPermissionRoles(userUuid);
		for(UUID p:set)
			permissions.addAll(permissionRoleService.getPermissions(p));
		return permissions;
	}

	public List<Permission> getPermissions(User user) {
		Set<UUID> permissions = getPermissions(user.getUuid());
		return permissionService.getByList(new ArrayList<UUID>(permissions));
	}
	
	public List<String> getAllActions(User user) {
		List<String> list = new ArrayList<String>();
		Set<UUID> permissions = getPermissions(user.getUuid());
		List<Permission> plist = permissionService.getByList(new ArrayList<UUID>(permissions));
		for(Permission p:plist)
			list.add(p.getPermission());
		return list;
	}
	
	public List<Datarange> getDataranges(User user) {
		Set<UUID> dataranges = getDataranges(user.getUuid());
		return datarangeService.getByList(new ArrayList<UUID>(dataranges));
	}
	
	public boolean save(User u, String[] groups, String[] permissionRoles, String[] datarangeRoles) {
		super.save(u);
		int item = bindGroups(u.getUuid().toString(), Arrays.asList(groups)) +
		bindPermissionRoles(u.getUuid().toString(), Arrays.asList(permissionRoles)) +
		bindDatarangeRoles(u.getUuid().toString(), Arrays.asList(datarangeRoles));
		refreshUserList();
		return true;
	}

	public boolean update(User u, String[] groups, String[] permissionRoles, String[] datarangeRoles) {
		super.update(u);
		int item = updateGroups(u.getUuid().toString(), Arrays.asList(groups)) +
		updatePermissionRoles(u.getUuid().toString(), Arrays.asList(permissionRoles)) +
		updateDatarangeRoles(u.getUuid().toString(), Arrays.asList(datarangeRoles));
		refreshUserList();
		return true;
	}

	
	
	
	
	//简化模型新增方法
	
	public boolean save(User u, String[] permissions, String[] dataranges) {
		if(jdbcTemplate.queryForObject(" select count(*) from user_user where loginname = ? and delete_flag = 0 ", Integer.class, u.getLoginName()) == 0) {
			super.save(u, new String[]{"deleteFlag"});
			int item = bindPermissionRoles(u.getUuid().toString(), Arrays.asList(permissions)) +
					bindDataranges(u.getUuid().toString(), dataranges);
		}
		refreshUserList();
		return true;
	}
	public boolean update(User u, String[] permissions, String[] dataranges) {
		super.update(u, new String[]{"date","loginName","deleteFlag","adminFlag"});
		int item = updatePermissionRoles(u.getUuid().toString(), Arrays.asList(permissions)) +
		updateDataranges(u.getUuid().toString(), dataranges);
		refreshUserList();
		return true;
	}

	public boolean delete(UUID uuid) {
		if ("0".equals(uuid.toString()))
			return false;
		jdbcTemplate.update("UPDATE user_user SET delete_flag='1' WHERE uuid=?",uuid.toString());
//			super.deleteById(User.class, uuid);
//			jdbcTemplate.update("DELETE FROM user_user_permissionrole_link WHERE user_uuid=?", uuid.toString());
//			jdbcTemplate.update("DELETE FROM user_user_datarange_link WHERE user_uuid=?", uuid.toString());
		refreshUserList();
		return true;
	}
	
	public int bindPermissions(String userUuid, List<String> permissions) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String p:permissions)
			if(!StringUtils.isBlank(p))
				list.add(new Object[]{userUuid,p});
		int[] nums = jdbcTemplate.batchUpdate("INSERT INTO user_user_permission_link (user_uuid, permission_uuid) VALUES (?, ?)", list);
		return CollectionUtil.sum(nums);
	}
	
	public int bindDataranges(String userUuid, List<String> dataranges) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String d:dataranges)
			if(!StringUtils.isBlank(d))
				list.add(new Object[]{userUuid,d});
		int[] nums = jdbcTemplate.batchUpdate("INSERT INTO user_user_datarange_link (user_uuid, datarange_uuid) VALUES (?, ?)", list);
		return CollectionUtil.sum(nums);
	}
	
	public int bindDataranges(String userUuid, String[] dataranges) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String r:dataranges) {
			if(!StringUtils.isBlank(r)){
				String[] temp = r.split(":");
				if(temp.length == 2) {
					String datarangeType = temp[0];
					String[] uuids = temp[1].split(",");
					for (String u:uuids) {
						if(!StringUtils.isBlank(u)) {
							list.add(new Object[]{userUuid,datarangeType,u});
						}
					}
				}
			}
		}
		int[] nums = jdbcTemplate.batchUpdate("INSERT INTO user_user_datarange_link (user_uuid, datarange_type, datarange_uuid) VALUES (?, ?, ?)", list);
		return CollectionUtil.sum(nums);
	}
	
	public void unbindDataranges(String userUuid) {
		jdbcTemplate.update("delete from user_user_datarange_link where user_uuid=?", userUuid);
	}
	
	public int updateDataranges(String permissionRoleUuid, String[] roledataranges) {
		unbindDataranges(permissionRoleUuid);
		return bindDataranges(permissionRoleUuid, roledataranges);
	}
	
	public int unbindPermissions(String userUuid, List<String> permissions) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String p:permissions)
			if(!StringUtils.isBlank(p))
				list.add(new Object[]{userUuid,p});
		int[] nums = jdbcTemplate.batchUpdate("DELETE FROM user_user_permission_link WHERE user_uuid=? and permission_uuid=?", list);
		return CollectionUtil.sum(nums);
	}
	
	public int unbindDataranges(String userUuid, List<String> dataranges) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String d:dataranges)
			if(!StringUtils.isBlank(d))
				list.add(new Object[]{userUuid,d});
		int[] nums = jdbcTemplate.batchUpdate("DELETE FROM user_user_datarange_link WHERE user_uuid=? and datarange_uuid=?", list);
		return CollectionUtil.sum(nums);
	}

	public int updatePermissions(String userUuid, List<String> permissions) {
		List<String> oldPermissions = getAllPermissions(userUuid);
		List<String> unbindPermissions = new ArrayList<String>(oldPermissions);
		List<String> bindPermissions = new ArrayList<String>(permissions);
		unbindPermissions.removeAll(permissions);
		bindPermissions.removeAll(oldPermissions);
		return unbindPermissions(userUuid, unbindPermissions) + bindPermissions(userUuid, bindPermissions);
	}
	
	public int updateDataranges(String userUuid, List<String> dataranges) {
		List<String> oldDataranges = getAllDataranges(userUuid);
		List<String> unbindDataranges = new ArrayList<String>(oldDataranges);
		List<String> bindDataranges = new ArrayList<String>(dataranges);
		unbindDataranges.removeAll(dataranges);
		bindDataranges.removeAll(oldDataranges);
		return unbindDataranges(userUuid, unbindDataranges) + bindDataranges(userUuid, bindDataranges);
	}
	
	public List<String> getAllPermissions(String userUuid) {
		List<String> list = jdbcTemplate.queryForList("SELECT permission_uuid FROM user_user_permission_link WHERE user_uuid = ?", String.class, userUuid);
		return list;
	}
	
	public List<String> getAllDataranges(String userUuid) {
		List<String> list = jdbcTemplate.queryForList("SELECT datarange_uuid FROM user_user_datarange_link WHERE user_uuid = ?", String.class, userUuid);
		return list;
	}
	
	public List<String> getActions(User user) {
		List<String> list = new ArrayList<String>();
		Set<UUID> permissions = getPermissions(user.getUuid());
		List<Permission> plist = permissionService.getByList(permissions);
		for(Permission p:plist){
			list.add(p.getPermission());
		}
			
		Collections.sort(list);
		return list;
	}
	
	public Map<String, Action> getActionMaps(User user) {
		Map<String, Action> list = new HashMap<String, Action>();
		Set<UUID> permissions = getPermissions(user.getUuid());
		List<Permission> plist = permissionService.getByList(permissions);
		for(Permission p:plist) {
			Action action = actionService.getByUuid(UUID.UUIDFromString(p.getPermission()));
			if (action != null)
				list.put(action.getActionName(), action);
		}
		return list;
	}

	@Override
	public boolean batDelete(String[] uuids) {
		for(String u:uuids) {
			if ("0".equals(u)) {
				continue;
			}
			this.deleteById(UUID.UUIDFromString(u), false);
		}
		refreshUserList();
		return true;
	}

	//获取未删除的人数
	@Override
	public int getUserCount() {
		return jdbcTemplate.queryForObject("select count(*) from user_user where delete_flag='0' ", Integer.class);
	}

	@Override
	public List<String> getAllUser(ICondition condition) {
		StringBuilder querySql = new StringBuilder("select t.uuid ");
		List<Object> params = new ArrayList<Object>();
		UserSolution solution = new UserSolution();
		solution.getWhere(condition, querySql, params);
		return jdbcTemplate.queryForList(querySql.toString(), params.toArray(), String.class); 
	}
	
	//获取新的范围类型
	public Set<String> getRoleDataranges(String userUuid, String datarangeType) {
		Set<String> roleDataranges = new HashSet<String>();
		List<String> list = getPermissionRoles(userUuid);
		for(String l:list)
			roleDataranges.addAll(permissionRoleService.getRoleDataranges(l, datarangeType));
		return roleDataranges;
	}
	
	//不区分类型
	public Set<String> getRoleDataranges(String userUuid) {
		Set<String> roleDataranges = new HashSet<String>();
		List<String> list = getPermissionRoles(userUuid);
		for(String l:list)
			roleDataranges.addAll(permissionRoleService.getRoleDataranges(l));
		return roleDataranges;
	}
	
	public boolean hasDatarange(String userUuid, String datarangeUuid) {
//		List<String> list = getPermissionRoles(userUuid);
//		for(String l:list){
//			if (permissionRoleService.hasDarange(l, datarangeUuid))
//				return true;
//		}
//		return false;
		return newHasDatarange(userUuid, datarangeUuid);
	}
	
	public boolean hasDatarange(String userUuid, String datarangeType, String datarangeUuid) {
//		List<String> list = getPermissionRoles(userUuid);
//		for(String l:list){
		if ("datarange".equals(datarangeType) && newHasDatarange(userUuid, datarangeType, "ada"))//全部的情况
			return true;
		if ("queue".equals(datarangeType) && newHasDatarange(userUuid, datarangeType, "aee"))
			return true;
		if (newHasDatarange(userUuid, datarangeType, datarangeUuid))
			return true;
//		}
		return false;
	}

	@Override
	public List<User> getAllUser(Collection<String> dataranges) {
		if(dataranges == null || dataranges.size() == 0)
			return new ArrayList<User>();
		StringBuilder sb = new StringBuilder("select t.*,p.datarange_name departmentName from user_user t join user_datarange p on t.department = p.uuid where 1 = 1 and delete_flag != '1'");
		List<Object> l = new ArrayList<Object>();
		QueryUtils.in(sb, l, dataranges, " and t.department ");
		sb.append(" order by t.department");
		return getBeanListWithSql(User.class, sb.toString(), l.toArray());
	}

//	@Override
//	public List<String> getDepartments(String userUuid) {
//		if ("0".equals(userUuid) || "1".equals(userUuid) || hasDatarange(userUuid, "datarange", "ada"))
//			return datarangeService.getAllDatarangeUuid();
//		return new ArrayList<String>(getRoleDataranges(userUuid, "datarange"));
//	}
	
	//取到所有可管辖部门
	@Override
	public List<String> getDepartments(String userUuid) {
		if ("0".equals(userUuid) || "1".equals(userUuid) || hasDatarange(userUuid, "datarange", "ada"))
			return datarangeService.getAllDatarangeUuid();
		return new ArrayList<String>(getDataranges(userUuid));
	}
	
	public boolean newHasDatarange(String permissionRoleUuid, String datarangeType, String datarangeUuid) {
		Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM user_user_datarange_link WHERE user_uuid = ? and datarange_type = ? and datarange_uuid = ?", Integer.class, permissionRoleUuid, datarangeType, datarangeUuid);
		return count > 0;
	}
	
	public boolean newHasDatarange(String permissionRoleUuid, String datarangeUuid) {
		Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM user_user_datarange_link WHERE user_uuid = ? and datarange_uuid = ?", Integer.class, permissionRoleUuid, datarangeUuid);
		return count > 0;
	}

	@Override
	public Collection<String> getDatarangeUsers(String datarangeUuid) {
		List<String> roleList = jdbcTemplate.queryForList("SELECT user_uuid FROM user_user_datarange_link WHERE (datarange_uuid = ? or datarange_uuid='ada') and datarange_type = ?", String.class, datarangeUuid, "datarange");
//		Set<String> userList = new HashSet<>();
//		for (String string : roleList) {
//			userList.addAll(permissionRoleService.getRoleUsers(string));
//		}
//		return userList;
		return roleList;
	}

	@Override
	public Collection<String> getDatarangesUsers(Collection<String> datarangeUuids) {
		Set<String> userList = new HashSet<>();
		for (String string : datarangeUuids) {
			userList.addAll(getDatarangeUsers(string));
		}
		return userList;
	}
	
	public Collection<User> getUsersByUuids(Collection<String> uuids) {
		List<User> list = new ArrayList<>();
		for (String string : uuids) {
			User temp = this.getByUuid(UUID.UUIDFromString(string));
			if (temp != null)
				list.add(temp);
		}
		return list;
	}
	
	public Collection<String> getUsernamesByUuids(Collection<String> uuids) {
		Set<String> list = new HashSet<>();
		for (String string : uuids) {
			User temp = this.getByUuid(UUID.UUIDFromString(string));
			if (temp != null)
				list.add(temp.getLoginName());
		}
		return list;
	}
	
	public boolean hasPermission(String user, String permissionUuid) {
		if("0".equals(user))
			return true;
		Set<String> list = getPermissions(user);
		if(list.contains((permissionUuid))) {
			return true;
		}
		return false;
	}

	//获取一个人可管辖的所有用户uuid
	@Override
	public Collection<String> getManagerUsers(String uuid) {
		Collection<String> list = getDepartments(uuid);	//获取所有可管辖部门
		return datarangeService.getUserUuidsByDataranges(list);	//获取他所管辖部门的所有人
	}
	
	@Override
	public Collection<String> getManagerUsernames(String uuid) {
		return getUsernamesByUuids(getManagerUsers(uuid));
	}
	
	public JSONArray getDatarangePermissionTree(String userUuid) {
        JSONArray ret = new JSONArray();
        List<Datarange> dataranges = datarangeService.getAllDatarange();
        
        List<String> hasDataranges = getDepartments(userUuid);
   /*     var zNodes =[  
        { id:1, pId:0, name:"父节点 1", open:true},  
        { id:11, pId:1, name:"叶子节点 1-1"},  
        { id:2, pId:0, name:"父节点 2", open:true},  
        { id:21, pId:2, name:"叶子节点 2-1"}
        ];*/

        for (Datarange d : dataranges) {
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("id", d.getUuid().toString());
    		jsonObject.put("pId", d.getParentUuid());
    		jsonObject.put("name", d.getDatarangeName());
    		jsonObject.put("open", true);
    		jsonObject.put("chkDisabled", !hasDataranges.contains(d.getUid()));
    		ret.put(jsonObject);
        }
        return ret;
	}

	@Override
	/**
	 * taskCount:该用户的任务数量
	 * intentCount：该用户的意向客户数量
	 * cstmCount:该用户的成交客户数量
	 */
	public Map<String, Object> getUserDataByUserid(String userid) {
		String statisticsSql = "SELECT\n" +
							"	SUM(\n" +
							"		intent_type IS NULL\n" +
							"		AND own_department != \"global_share\"\n" +
							"	) AS taskCount,\n" +
							"	SUM(intent_type IS NOT NULL) AS intentCount,\n" +
							"	(\n" +
							"		SELECT\n" +
							"			COUNT(phone_number)\n" +
							"		FROM\n" +
							"			`cstm_customer`\n" +
							"		WHERE\n" +
							"			own_id = ? \n" +
							"		AND `status` = \"1\"\n" +
							"	) AS cstmCount\n" +
							"FROM\n" +
							"	new_data_department_user_"+getByUuid(UUID.UUIDFromString(userid)).getDepartment()+"\n" +
							"WHERE\n" +
							"	own_user = ? ";
		Map<String, Object> map = jdbcTemplate.queryForMap(statisticsSql, userid,userid);
		for(String key : map.keySet()){
			Object value = map.get(key);
			if(value == null){
				map.put(key, 0);
			}else  if(value instanceof Number){
				map.put(key, ((Number)value).intValue());
			}
		}
		return map;
	}	
	
	@SuppressWarnings("unchecked")
	public List<Datarange> getCurrentDataRange(final String currentId){
		
		if("0".equals(currentId)){
			return getBeanList(Datarange.class, " ORDER BY date ASC ");
		}
		
		return queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {

				sql.append("SELECT\n" +
						"	*\n" +
						"FROM\n" +
						"	user_datarange\n" +
						"WHERE\n" +
						"	uuid IN (\n" +
						"		SELECT\n" +
						"			datarange_uuid\n" +
						"		FROM\n" +
						"			user_user_datarange_link\n" +
						"		WHERE\n" +
						"			user_uuid = ?\n" +
						"	)\n" +
						"ORDER BY date ASC");
				
				params.add(currentId);
			}
			
		}, Datarange.class);
		
	}
	
	/**
	 * 根据部门ID获取下面所有的人
	 * @param deptId
	 * @return
	 */
	@Override
	public Collection<String> getAllUsersByDeptId(String deptId) {

		if (StringUtils.isNotBlank(deptId)) {

			List<String> coll = jdbcTemplate.queryForList("SELECT uuid FROM user_user WHERE department = ? ",String.class, deptId);

			if (null != coll) {

				return coll;
			}
		}
		return null;
	}	
	
	
	
}
