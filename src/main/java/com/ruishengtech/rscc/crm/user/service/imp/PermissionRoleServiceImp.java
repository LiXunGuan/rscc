package com.ruishengtech.rscc.crm.user.service.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.user.model.Permission;
import com.ruishengtech.rscc.crm.user.model.PermissionRole;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.PermissionRoleService;
import com.ruishengtech.rscc.crm.user.solution.PermissionRoleSolution;
import com.ruishengtech.rscc.crm.user.util.CollectionUtil;

@Service
@Transactional

public class PermissionRoleServiceImp extends BaseService implements PermissionRoleService {
	
	public List<PermissionRole> getAllPermissionRole() {
		return getBeanList(PermissionRole.class,"");
	}
	
	public boolean save(PermissionRole p) {
		super.save(p);
		return true;
	}
	
	public boolean save(PermissionRole p, String[] permissions) {
		super.save(p);
		int item = bindPermissions(p.getUuid().toString(), Arrays.asList(permissions));
		return true;
	}
	
	public boolean save(PermissionRole p, String[] permissions, String[] roledataranges) {
		super.save(p);
		int item = bindPermissions(p.getUuid().toString(), Arrays.asList(permissions));
		bindRoleDataranges(p.getUuid().toString(), roledataranges);
		return true;
	}
	
	public boolean update(PermissionRole p) {
		if(p.getUuid().toString().equals(p.getParentUuid()))
			return false;
        super.update(p);
		return true;
    }
	
	public boolean update(PermissionRole p, String[] permissions) {
		super.update(p);
		int item = updatePermissions(p.getUuid().toString(), Arrays.asList(permissions));
		return true;
	}
	
	public boolean update(PermissionRole p, String[] permissions, String[] roledataranges) {
		super.update(p);
		int item = updatePermissions(p.getUuid().toString(), Arrays.asList(permissions));
		updateRoleDataranges(p.getUuid().toString(), roledataranges);
		return true;
	}
	
	public boolean deleteById(UUID uuid) {
		
		deleteChildren(uuid);
		super.deleteById(PermissionRole.class, uuid);
		jdbcTemplate.update("DELETE FROM user_user_permissionrole_link WHERE permissionrole_uuid=?", uuid.toString());
		jdbcTemplate.update("DELETE FROM user_group_permissionrole_link WHERE permissionrole_uuid=?", uuid.toString());
		jdbcTemplate.update("DELETE FROM user_permissionrole_permission_link WHERE permissionrole_uuid=?", uuid.toString());
		jdbcTemplate.update("DELETE FROM user_role_datarange_link WHERE role_uuid=?", uuid.toString());
		return true;
	}
	
	public PermissionRole getByUuid(UUID uuid) {
		return super.getByUuid(PermissionRole.class, uuid);
	}
	
	public PageResult<PermissionRole> queryPage(ICondition condition) {
		return super.queryPage(new PermissionRoleSolution(), condition, PermissionRole.class);
	}
	
	public PermissionRole getPermissionRoleByName(String permissionRoleName) {
		List<PermissionRole> list = getBeanList(PermissionRole.class, "and role_name=?", permissionRoleName);
		if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}

	
	public boolean bindPermission(String permissionRoleUuid, String permissionUuid) {
		if(!StringUtils.isBlank(permissionRoleUuid) && !StringUtils.isBlank(permissionUuid))
			return 1==jdbcTemplate.update("INSERT INTO user_permissionrole_permission_link (permissionrole_uuid, permission_uuid) VALUES (?, ?)", permissionRoleUuid, permissionUuid);
		return true;
	}
	
	
	public boolean bindPermission(UUID permissionRoleUuid, UUID permissionUuid) {
		return bindPermission(permissionRoleUuid.toString(), permissionUuid.toString());
	}

	
	public int bindPermissions(String permissionRoleUuid, List<String> permissions) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String p:permissions)
			if(!StringUtils.isBlank(p))
				list.add(new Object[]{permissionRoleUuid,p});
		int[] nums = jdbcTemplate.batchUpdate("INSERT INTO user_permissionrole_permission_link (permissionrole_uuid, permission_uuid) VALUES (?, ?)", list);
		return CollectionUtil.sum(nums);
	}
	
	
	public int bindPermissions(UUID permissionRoleUuid, List<UUID> permissions) {
		return bindPermissions(permissionRoleUuid.toString(), CollectionUtil.uuidToString(permissions));
	}

	
	public boolean unbindPermission(String permissionRoleUuid, String permissionUuid) {
		if(!StringUtils.isBlank(permissionRoleUuid) && !StringUtils.isBlank(permissionUuid))
			return 1==jdbcTemplate.update("DELETE FROM user_permissionrole_permission_link WHERE permissionrole_uuid=? and permission_uuid=?", permissionRoleUuid, permissionUuid);
		return true;
	}
	
	
	public boolean unbindPermission(UUID permissionRoleUuid, UUID permissionUuid) {
		return unbindPermission(permissionRoleUuid.toString(), permissionUuid.toString());
	}

	
	public int unbindPermissions(String permissionRoleUuid, List<String> permissions) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String p:permissions)
			if(!StringUtils.isBlank(p))
				list.add(new Object[]{permissionRoleUuid,p});
		int[] nums = jdbcTemplate.batchUpdate("DELETE FROM user_permissionrole_permission_link WHERE permissionrole_uuid=? and permission_uuid=?", list);
		return CollectionUtil.sum(nums);
	}
	
	
	public int unbindPermissions(UUID permissionRoleUuid, List<UUID> permissions) {
		return unbindPermissions(permissionRoleUuid.toString(), CollectionUtil.uuidToString(permissions));
	}

	
	public int updatePermissions(String permissionRoleUuid, List<String> permissions) {
		List<String> oldPermissions = getPermissions(permissionRoleUuid);
		List<String> unbindPermissions = new ArrayList<String>(oldPermissions);
		List<String> bindPermissions = new ArrayList<String>(permissions);
		unbindPermissions.removeAll(permissions);
		bindPermissions.removeAll(oldPermissions);
		return unbindPermissions(permissionRoleUuid, unbindPermissions) + bindPermissions(permissionRoleUuid, bindPermissions);
	}
	
	
	public int updatePermissions(UUID permissionRoleUuid, List<UUID> permissions) {
		return updatePermissions(permissionRoleUuid.toString(), CollectionUtil.uuidToString(permissions));
	}

	//新的范围方法
	public int bindRoleDataranges(String permissionRoleUuid, String[] roledataranges) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String r:roledataranges) {
			if(!StringUtils.isBlank(r)){
				String[] temp = r.split(":");
				if(temp.length == 2) {
					String datarangeType = temp[0];
					String[] uuids = temp[1].split(",");
					for (String u:uuids){
						if(!StringUtils.isBlank(u)) {
							list.add(new Object[]{permissionRoleUuid,datarangeType,u});
						}
					}
				}
			}
		}
		int[] nums = jdbcTemplate.batchUpdate("INSERT INTO user_role_datarange_link (role_uuid, datarange_type, datarange_uuid) VALUES (?, ?, ?)", list);
		return CollectionUtil.sum(nums);
	}
	
	public void unbindRoleDataranges(String permissionRoleUuid) {
		jdbcTemplate.update("delete from user_role_datarange_link where role_uuid=?", permissionRoleUuid);
	}
	
	public void updateRoleDataranges(String permissionRoleUuid, String[] roledataranges) {
		unbindRoleDataranges(permissionRoleUuid);
		bindRoleDataranges(permissionRoleUuid, roledataranges);
	}

	public List<String> getRoleDataranges(String permissionRoleUuid, String datarangeType) {
		List<String> list = jdbcTemplate.queryForList("SELECT datarange_uuid FROM user_role_datarange_link WHERE role_uuid = ? and datarange_type = ?", String.class, permissionRoleUuid, datarangeType);
		return list;
	}
	
	public List<String> getRoleDataranges(String permissionRoleUuid) {
		List<String> list = jdbcTemplate.queryForList("SELECT datarange_uuid FROM user_role_datarange_link WHERE role_uuid = ?", String.class, permissionRoleUuid);
		return list;
	}
	
	public boolean hasDarange(String permissionRoleUuid, String datarangeType, String datarangeUuid) {
		Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM user_role_datarange_link WHERE role_uuid = ? and datarange_type = ? and datarange_uuid = ?", Integer.class, permissionRoleUuid, datarangeType, datarangeUuid);
		return count > 0;
	}
	
	public boolean hasDarange(String permissionRoleUuid, String datarangeUuid) {
		Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM user_role_datarange_link WHERE role_uuid = ? and datarange_uuid = ?", Integer.class, permissionRoleUuid, datarangeUuid);
		return count > 0;
	}
	//方法结束
	
	public List<String> getPermissions(String permissionRoleUuid) {
		List<String> list = jdbcTemplate.queryForList("SELECT permission_uuid FROM user_permissionrole_permission_link WHERE permissionrole_uuid = ?", String.class, permissionRoleUuid);
		return list;
	}
	
	public List<Permission> getPermissions(PermissionRole permissionRole) {
		List<Permission> list = getBeanList(Permission.class, "and uuid IN (SELECT permission_uuid FROM user_permissionrole_permission_link WHERE permissionrole_uuid=?)", permissionRole.getUuid().toString());
		return list;
	}
	
	
	public List<UUID> getPermissions(UUID permissionRoleUuid) {
		List<String> list = getPermissions(permissionRoleUuid.toString());
		return CollectionUtil.stringToUuid(list);
	}

	
	public List<PermissionRole> getChildren(String permissionRoleUuid){	//获取权限	
		return getBeanList(PermissionRole.class, "and parent_uuid = ?", permissionRoleUuid);
    }
	
	
	public List<PermissionRole> getChildren(UUID permissionRoleUuid){	//获取权限	
		return getChildren(permissionRoleUuid.toString());
    }
	
	public JSONArray getPermissionRoleTree() {
        JSONArray ret = new JSONArray();
        List<PermissionRole> permissionRoles = getAllPermissionRole();
   /*     var zNodes =[  
        { id:1, pId:0, name:"父节点 1", open:true},  
        { id:11, pId:1, name:"叶子节点 1-1"},  
        { id:2, pId:0, name:"父节点 2", open:true},  
        { id:21, pId:2, name:"叶子节点 2-1"}
        ];*/

        for (PermissionRole p : permissionRoles) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", p.getUuid().toString());
            jsonObject.put("pId", p.getParentUuid());
            jsonObject.put("name", p.getPermissionRoleName());
            jsonObject.put("open", true);
            ret.put(jsonObject);
        }
        return ret;
	}
	
	public PermissionRole getParent(PermissionRole permissionRole) {
		return getByUuid(UUID.UUIDFromString(permissionRole.getParentUuid()));
	}

	@Override
	public void deleteChildren(UUID uuid) {
		List<PermissionRole> children = getChildren(uuid);
		if(children.size()>0)
			for(PermissionRole p:children)
				deleteById(p.getUuid());
	}
	
	public boolean batDelete(String[] uuids) {
		for(String u:uuids){
			//判定默认管理员的角色不能删除
			if(!u.equals("000001")){
				this.deleteById(UUID.UUIDFromString(u));
			}
		}
		return true;
	}

	@Override
	public List<String> getAllPermissionRole(ICondition condition) {
		StringBuilder querySql = new StringBuilder("select t.uuid ");
		List<Object> params = new ArrayList<Object>();
		PermissionRoleSolution solution = new PermissionRoleSolution();
		solution.getWhere(condition, querySql, params);
		return jdbcTemplate.queryForList(querySql.toString(), params.toArray(), String.class); 
	}

	@Override
	public List<String> getRoleUsers(String permissionRoleUuid) {
		return jdbcTemplate.queryForList("SELECT user_uuid FROM user_user_permissionrole_link WHERE permissionrole_uuid = ?", String.class, permissionRoleUuid);
	}

	@Override
	public List<String> getRoleByUser(String userUuid) {
		List<String> roles = jdbcTemplate.queryForList("SELECT permissionrole_uuid FROM user_user_permissionrole_link WHERE user_uuid = ? ", String.class, userUuid);
		List<String> rolename = new ArrayList<>();
		if(roles.size()!=0){
			for(String role : roles){
				PermissionRole temp = getByUuid(PermissionRole.class, UUID.UUIDFromString(role));
				if (temp != null) {
					rolename.add(temp.getPermissionRoleName());
				}
			}
		}
		return rolename;
	}
	
	public List<String> getUseridByRoleName(String roleName){
		List<String> userids = jdbcTemplate.queryForList("SELECT\n" +
															"	user_uuid\n" +
															"FROM\n" +
															"	user_permission_role up\n" +
															"JOIN user_user_permissionrole_link uu ON up.uuid = uu.permissionrole_uuid\n" +
															"WHERE up.role_name LIKE ? ", String.class, roleName);
		return userids;
	}
}
