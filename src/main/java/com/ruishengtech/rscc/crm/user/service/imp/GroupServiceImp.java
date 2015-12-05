package com.ruishengtech.rscc.crm.user.service.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.user.model.Datarange;
import com.ruishengtech.rscc.crm.user.model.DatarangeRole;
import com.ruishengtech.rscc.crm.user.model.Group;
import com.ruishengtech.rscc.crm.user.model.Permission;
import com.ruishengtech.rscc.crm.user.model.PermissionRole;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.DatarangeRoleService;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.service.GroupService;
import com.ruishengtech.rscc.crm.user.service.PermissionRoleService;
import com.ruishengtech.rscc.crm.user.service.PermissionService;
import com.ruishengtech.rscc.crm.user.solution.GroupSolution;
import com.ruishengtech.rscc.crm.user.util.CollectionUtil;
@Service
@Transactional
public class GroupServiceImp extends BaseService implements GroupService {
	
	@Autowired
	private DatarangeRoleService datarangeRoleService;
	
	@Autowired
	private PermissionRoleService permissionRoleService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private DatarangeService datarangeService;
	public GroupServiceImp() {
	}
	public boolean save(Group g) {
		super.save(g);
		return true;
	}
	
	public void save(Group g, String[] excludeFieldName) {
		super.save(g, excludeFieldName);
	}
	
	public boolean save(Group g, String[] permissionRoles, String[] datarangeRoles) {
		super.save(g);
		int item = bindPermissionRoles(g.getUuid().toString(), Arrays.asList(permissionRoles)) +
		bindDatarangeRoles(g.getUuid().toString(), Arrays.asList(datarangeRoles));
		return true;
	}
	
	public boolean update(Group g) {
        super.update(g);
		return true;
    }
	
	public boolean update(Group g, String[] permissionRoles, String[] datarangeRoles) {
		if(g.getUuid().toString().equals(g.getParentUuid()))
			return false;
		super.update(g);
		int item = updatePermissionRoles(g.getUuid().toString(), Arrays.asList(permissionRoles)) +
		updateDatarangeRoles(g.getUuid().toString(), Arrays.asList(datarangeRoles));
		return true;
	}
	
	public void update(Group g, String[] excludeFieldName) {
		 super.update(g, excludeFieldName);
	}
	
	public boolean deleteById(UUID uuid) {
		deleteChildren(uuid);
		super.deleteById(Group.class, uuid);
		jdbcTemplate.update("DELETE FROM user_user_group_link WHERE group_uuid=?", uuid.toString());
		jdbcTemplate.update("DELETE FROM user_group_permissionrole_link WHERE group_uuid=?", uuid.toString());
		jdbcTemplate.update("DELETE FROM user_group_datarangerole_link WHERE group_uuid=?", uuid.toString());
		return true;
	}
	
	public Group getByUuid(UUID uuid) {
		return super.getByUuid(Group.class, uuid);
	}
	
	public PageResult<Group> queryPage(ICondition condition) {
		return super.queryPage(new GroupSolution(), condition, Group.class);
	}
	
	public Group getGroupByName(final String name) {
		List<Group> list = getBeanList(Group.class, "and name=?", name);
		if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public List<Group> getAllGroup() {
		return getBeanList(Group.class,"");
	}
	
	public boolean bindUser(String groupUuid, String userUuid) {
		if(!StringUtils.isBlank(groupUuid) && !StringUtils.isBlank(userUuid))
			return 1==jdbcTemplate.update("INSERT INTO user_user_group_link (user_uuid, group_uuid) VALUES (?, ?)", userUuid, groupUuid);
		return true;
	}
	
	
	public boolean bindUser(UUID groupUuid, UUID userUuid) {
		return bindUser(groupUuid.toString(), userUuid.toString());
	}
	
	
	public int bindUsers(String groupUuid, List<String> users) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String u:users)
			if(!StringUtils.isBlank(u))
				list.add(new Object[]{u,groupUuid});
		int[] nums = jdbcTemplate.batchUpdate("INSERT INTO user_user_group_link (user_uuid, group_uuid) VALUES (?, ?)", list);
		return CollectionUtil.sum(nums);
	}
	
	
	public int bindUsers(UUID groupUuid, List<UUID> users) {
		return bindUsers(groupUuid.toString(), CollectionUtil.uuidToString(users));
	}
	
	
	public boolean unbindUser(String groupUuid, String userUuid) {
		if(!StringUtils.isBlank(groupUuid) && !StringUtils.isBlank(userUuid))
			return 1==jdbcTemplate.update("DELETE FROM user_user_group_link WHERE user_uuid=? and group_uuid=?", userUuid, groupUuid);
		return true;
	}
	
	
	public boolean unbindUser(UUID groupUuid, UUID userUuid) {
		return unbindUser(groupUuid.toString(), userUuid.toString());
	}
	
	
	public int unbindUsers(String groupUuid, List<String> users) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String u:users)
			if(!StringUtils.isBlank(u))
				list.add(new Object[]{u,groupUuid});
		int[] nums = jdbcTemplate.batchUpdate("DELETE FROM user_user_group_link WHERE user_uuid=? and group_uuid=?", list);
		return CollectionUtil.sum(nums);
	}
	
	
	public int unbindUsers(UUID groupUuid, List<UUID> users) {
		return unbindUsers(groupUuid.toString(), CollectionUtil.uuidToString(users));
	}
	
	
	public int updateUsers(String groupUuid, List<String> users) {
		List<String> oldUsers = getUsers(groupUuid);
		List<String> unbindUsers = new ArrayList<String>(oldUsers);
		List<String> bindUsers = new ArrayList<String>(users);
		unbindUsers.removeAll(users);
		bindUsers.removeAll(oldUsers);
		return unbindUsers(groupUuid, unbindUsers) + bindUsers(groupUuid, bindUsers);
	}
	
	
	public int updateUsers(UUID groupUuid, List<UUID> users) {
		return updateUsers(groupUuid.toString(), CollectionUtil.uuidToString(users));
	}

	
	public boolean bindPermissionRole(String groupUuid, String permissionRoleUuid) {
		if(!StringUtils.isBlank(groupUuid) && !StringUtils.isBlank(permissionRoleUuid))
			return 1==jdbcTemplate.update("INSERT INTO user_group_permissionrole_link (group_uuid, permissionrole_uuid) VALUES (?, ?)", groupUuid, permissionRoleUuid);
		return true;
	}
	
	
	public boolean bindPermissionRole(UUID groupUuid, UUID permissionRoleUuid) {
		return bindPermissionRole(groupUuid.toString(), permissionRoleUuid.toString());
	}
	
	
	public int bindPermissionRoles(String groupUuid, List<String> permissionRoles) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String p:permissionRoles)
			if(!StringUtils.isBlank(p))
				list.add(new Object[]{groupUuid,p});
		int[] nums = jdbcTemplate.batchUpdate("INSERT INTO user_group_permissionrole_link (group_uuid, permissionrole_uuid) VALUES (?, ?)", list);
		return CollectionUtil.sum(nums);
	}
	
	
	public int bindPermissionRoles(UUID groupUuid, List<UUID> permissionRoles) {
		return bindPermissionRoles(groupUuid.toString(), CollectionUtil.uuidToString(permissionRoles));
	}
	
	
	public boolean unbindPermissionRole(String groupUuid, String permissionRoleUuid) {
		if(!StringUtils.isBlank(groupUuid) && !StringUtils.isBlank(permissionRoleUuid))
			return 1==jdbcTemplate.update("DELETE FROM user_group_permissionrole_link WHERE group_uuid=? and permissionrole_uuid=?", groupUuid, permissionRoleUuid);
		return true;
	}
	
	
	public boolean unbindPermissionRole(UUID groupUuid, UUID permissionRoleUuid) {
		return unbindPermissionRole(groupUuid.toString(), permissionRoleUuid.toString());
	}

	
	public int unbindPermissionRoles(String groupUuid, List<String> permissionRoles) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String p:permissionRoles)
			list.add(new Object[]{groupUuid,p});
		int[] nums = jdbcTemplate.batchUpdate("DELETE FROM user_group_permissionrole_link WHERE group_uuid=? and permissionrole_uuid=?", list);
		return CollectionUtil.sum(nums);
	}
	
	
	public int unbindPermissionRoles(UUID groupUuid, List<UUID> permissionRoles) {
		return unbindPermissionRoles(groupUuid.toString(), CollectionUtil.uuidToString(permissionRoles));
	}

	
	public int updatePermissionRoles(String groupUuid, List<String> permissionRoles) {
		List<String> oldPermissionRoles = getPermissionRoles(groupUuid);
		List<String> unbindPermissionRoles = new ArrayList<String>(oldPermissionRoles);
		List<String> bindPermissionRoles = new ArrayList<String>(permissionRoles);
		unbindPermissionRoles.removeAll(permissionRoles);
		bindPermissionRoles.removeAll(oldPermissionRoles);
		return unbindPermissionRoles(groupUuid, unbindPermissionRoles) + bindPermissionRoles(groupUuid, bindPermissionRoles);
	}
	
	
	public int updatePermissionRoles(UUID userUuid, List<UUID> permissionRoles) {
		return updatePermissionRoles(userUuid.toString(), CollectionUtil.uuidToString(permissionRoles));
	}

	
	public boolean bindDatarangeRole(String groupUuid, String datarangeRoleUuid) {
		if(!StringUtils.isBlank(groupUuid) && !StringUtils.isBlank(datarangeRoleUuid))
			return 1==jdbcTemplate.update("INSERT INTO user_group_datarangerole_link (group_uuid, datarangerole_uuid) VALUES (?, ?)", groupUuid, datarangeRoleUuid);
		return true;
	}
	
	
	public boolean bindDatarangeRole(UUID groupUuid, UUID datarangeRoleUuid) {
		return bindDatarangeRole(groupUuid.toString(), datarangeRoleUuid.toString());
	}

	
	public int bindDatarangeRoles(String groupUuid, List<String> datarangeRoles) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String d:datarangeRoles)
			if(!StringUtils.isBlank(d))
				list.add(new Object[]{groupUuid,d});
		int[] nums = jdbcTemplate.batchUpdate("INSERT INTO user_group_datarangerole_link (group_uuid, datarangerole_uuid) VALUES (?, ?)", list);
		return CollectionUtil.sum(nums);
	}
	
	
	public int bindDatarangeRoles(UUID groupUuid, List<UUID> datarangeRoles) {
		return bindDatarangeRoles(groupUuid.toString(), CollectionUtil.uuidToString(datarangeRoles));
	} 

	
	public boolean unbindDatarangeRole(String groupUuid, String datarangeRoleUuid) {
		if(!StringUtils.isBlank(groupUuid) && !StringUtils.isBlank(datarangeRoleUuid))
			return 1==jdbcTemplate.update("DELETE FROM user_group_datarangerole_link WHERE group_uuid=? and datarangerole_uuid=?", groupUuid, datarangeRoleUuid);
		return true;
	}
	
	
	public boolean unbindDatarangeRole(UUID groupUuid, UUID datarangeRoleUuid) {
		return unbindDatarangeRole(groupUuid.toString(), datarangeRoleUuid.toString());
	}

	
	public int unbindDatarangeRoles(String groupUuid, List<String> datarangeRoles) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String d:datarangeRoles)
			if(!StringUtils.isBlank(d))
				list.add(new Object[]{groupUuid,d});
		int[] nums = jdbcTemplate.batchUpdate("DELETE FROM user_group_datarangerole_link WHERE group_uuid=? and datarangerole_uuid=?", list);
		return CollectionUtil.sum(nums);
	}
	
	
	public int unbindDatarangeRoles(UUID groupUuid, List<UUID> datarangeRoles) {
		return unbindDatarangeRoles(groupUuid.toString(), CollectionUtil.uuidToString(datarangeRoles));
	}

	
	public int updateDatarangeRoles(String groupUuid, List<String> DatarangeRoles) {
		List<String> oldDatarangeRoles = getDatarangeRoles(groupUuid);
		List<String> unbindDatarangeRoles = new ArrayList<String>(oldDatarangeRoles);
		List<String> bindDatarangeRoles = new ArrayList<String>(DatarangeRoles);
		unbindDatarangeRoles.removeAll(DatarangeRoles);
		bindDatarangeRoles.removeAll(oldDatarangeRoles);
		return unbindDatarangeRoles(groupUuid, unbindDatarangeRoles) + bindDatarangeRoles(groupUuid, bindDatarangeRoles);
	}
	
	
	public int updateDatarangeRoles(UUID groupUuid, List<UUID> DatarangeRoles) {
		return updateDatarangeRoles(groupUuid.toString(), CollectionUtil.uuidToString(DatarangeRoles));
	}
	
	
	public List<String> getUsers(String groupUuid) {
		List<String> list = jdbcTemplate.queryForList("SELECT user_uuid FROM user_user_group_link WHERE group_uuid = ?", String.class, groupUuid);
		return list;
	}
	
	
	public List<UUID> getUsers(UUID groupUuid) {
		List<String> list = getUsers(groupUuid.toString());
		return CollectionUtil.stringToUuid(list);
	}

	
	public List<String> getPermissionRoles(String groupUuid) {
		List<String> list = jdbcTemplate.queryForList("SELECT permissionrole_uuid FROM user_group_permissionrole_link WHERE group_uuid = ?", String.class, groupUuid);
		return list;
	}
	
	
	public List<UUID> getPermissionRoles(UUID groupUuid) {
		List<String> list = getPermissionRoles(groupUuid.toString());
		return CollectionUtil.stringToUuid(list);
	}

	
	public List<String> getDatarangeRoles(String groupUuid) {
		List<String> list = jdbcTemplate.queryForList("SELECT datarangerole_uuid FROM user_group_datarangerole_link WHERE group_uuid = ?", String.class, groupUuid);
		return list;
	}
	
	
	public List<UUID> getDatarangeRoles(UUID groupUuid) {
		List<String> list = getDatarangeRoles(groupUuid.toString());
		return CollectionUtil.stringToUuid(list);
	}

	
	public List<User> getUsers(Group group) {
		List<User> list = getBeanList(User.class, "and uuid IN (SELECT user_uuid FROM user_user_group_link WHERE group_uuid=?)", group.getUuid().toString());
		return list;
	}
	
	
	public List<PermissionRole> getPermissionRoles(Group group) {
		List<PermissionRole> list = getBeanList(PermissionRole.class, "and uuid IN (SELECT permissionrole_uuid FROM user_user_permissionrole_link WHERE group_uuid=?)", group.getUuid().toString());
		return list;
	}

	
	public List<DatarangeRole> getDatarangeRoles(Group group) {
		List<DatarangeRole> list = getBeanList(DatarangeRole.class, "and uuid IN (SELECT datarangerole_uuid FROM user_user_datarangerole_link WHERE group_uuid=?)", group.getUuid().toString());
		return list;
	}

	
	public List<Group> getChildren(String groupUuid){	//获取子分组	
		return getBeanList(Group.class, "and parent_uuid = ?", groupUuid);
    }
	
	
	public List<Group> getChildren(UUID groupUuid){	//获取子分组	
		return getChildren(groupUuid.toString());
    }
	
	public void deleteChildren(UUID uuid){
		List<Group> children = getChildren(uuid);
		if(children.size()>0)
			for(Group g:children)
				deleteById(g.getUuid());
	}
	
	public Set<String> getDataranges(String groupUuid) {
		Set<String> dataranges = new HashSet<String>();
		List<String> list = getDatarangeRoles(groupUuid);
		for(String d:list)
			dataranges.addAll(datarangeRoleService.getDataranges(d));
		return dataranges;
	}
	
	
	public Set<UUID> getDataranges(UUID groupUuid) {
		Set<UUID> dataranges = new HashSet<UUID>();
		List<UUID> list = getDatarangeRoles(groupUuid);
		for(UUID d:list)
			dataranges.addAll(datarangeRoleService.getDataranges(d));
		return dataranges;
	}

	
	public Set<String> getPermissions(String groupUuid) {
		Set<String> permissions = new HashSet<String>();
		List<String> list = getPermissionRoles(groupUuid);
		for(String p:list)
			permissions.addAll(permissionRoleService.getPermissions(p));
		return permissions;
	}
	
	
	public Set<UUID> getPermissions(UUID groupUuid) {
		Set<UUID> permissions = new HashSet<UUID>();
		List<UUID> list = getPermissionRoles(groupUuid);
		for(UUID p:list)
			permissions.addAll(permissionRoleService.getPermissions(p));
		return permissions;
	}
	
	public List<Permission> getPermissions(Group group) {
		Set<UUID> permissions = getPermissions(group.getUuid());
		return permissionService.getByList(new ArrayList<UUID>(permissions));
	}
	
	public List<Datarange> getDataranges(Group group) {
		Set<UUID> dataranges = getDataranges(group.getUuid());
		return datarangeService.getByList(new ArrayList<UUID>(dataranges));
	}
	
	public JSONArray getGroupTree() {
        JSONArray ret = new JSONArray();
        List<Group> groups = getAllGroup();
   /*     var zNodes =[  
        { id:1, pId:0, name:"父节点 1", open:true},  
        { id:11, pId:1, name:"叶子节点 1-1"},  
        { id:2, pId:0, name:"父节点 2", open:true},  
        { id:21, pId:2, name:"叶子节点 2-1"}
        ];*/

        for (Group g : groups) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", g.getUuid().toString());
            jsonObject.put("pId", g.getParentUuid());
            jsonObject.put("name", g.getGroupName());
            jsonObject.put("open", true);
            ret.put(jsonObject);
        }
        return ret;
	}	
	
	public Group getParent(Group group) {
		return getByUuid(UUID.UUIDFromString(group.getParentUuid()));
	}
}
