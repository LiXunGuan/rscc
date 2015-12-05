package com.ruishengtech.rscc.crm.user.service.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.user.model.Permission;
import com.ruishengtech.rscc.crm.user.service.PermissionRoleService;
import com.ruishengtech.rscc.crm.user.service.PermissionService;
import com.ruishengtech.rscc.crm.user.solution.PermissionSolution;

@Service
@Transactional

public class PermissionServiceImp extends BaseService implements PermissionService {
	
	@Autowired
	private PermissionRoleService pmRoleService;
	
	public boolean save(Permission p) {
		super.save(p);
		return true;
	}
	
	public List<Permission> getAllPermission() {
		return getBeanList(Permission.class,"");
	}
	
	public List<Permission> getAllPermissionWithOrder() {
		return getBeanListWithOrder(Permission.class, "", "sequence");
	}
	
	public void save(Permission p, String[] excludeFieldName) {
		super.save(p, excludeFieldName);
	}
	
	public boolean update(Permission p) {
		if(p.getUuid().toString().equals(p.getParentUuid()))
			return false;
        super.update(p);
		return true;
    }
	
	public void update(Permission p, String[] excludeFieldName) {
		 super.update(p, excludeFieldName);
	}
	
	public boolean deleteById(UUID uuid) {
		deleteChildren(uuid);
		super.deleteById(Permission.class, uuid);
		jdbcTemplate.update("DELETE FROM user_permissionrole_permission_link WHERE permission_uuid=?", uuid.toString());
		return true;
	}
	
	public Permission getByUuid(UUID uuid) {
		return super.getByUuid(Permission.class, uuid);
	}
	
	public List<Permission> getByList(Collection<UUID> l) {
		ArrayList<Permission> list = new ArrayList<Permission>();
		for(UUID u:l)
			list.add(getByUuid(u));
		return list;
	}
	
	public PageResult<Permission> queryPage(ICondition condition) {
		return super.queryPage(new PermissionSolution(), condition, Permission.class);
	}
	public Permission getPermissionByName(String permissionName) {
		
		List<Permission> list = getBeanList(Permission.class, "and permission_name = ?", permissionName);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}

	
	public List<Permission> getChildren(String permissionUuid){	//获取权限
		return getBeanList(Permission.class, "and parent_uuid = ?", permissionUuid);
    }
	
	
	public List<Permission> getChildren(UUID permissionUuid){	//获取权限
		return getChildren(permissionUuid.toString());
    }
	
	public JSONArray getPermissionTree() {
        JSONArray ret = new JSONArray();
        List<Permission> permissions = getAllPermissionWithOrder();
   /*     var zNodes =[  
        { id:1, pId:0, name:"父节点 1", open:true},  
        { id:11, pId:1, name:"叶子节点 1-1"},  
        { id:2, pId:0, name:"父节点 2", open:true},  
        { id:21, pId:2, name:"叶子节点 2-1"}
        ];*/

        for (Permission p : permissions) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", p.getUuid().toString());
            jsonObject.put("pId", p.getParentUuid());
            jsonObject.put("name", p.getPermissionName());
            jsonObject.put("open", false);
            if (p.getPermissionDescribe().indexOf("png") >= 0)
            	jsonObject.put("icon", "./assets/js/plugin/ztree/css/metroStyle/img/diy/" + p.getPermissionDescribe());
            ret.put(jsonObject);
        }
        return ret;
	}
	
	public Permission getParent(Permission permission) {
		return getByUuid(UUID.UUIDFromString(permission.getParentUuid()));
	}
	
	public void deleteChildren(UUID uuid) {
		List<Permission> children = getChildren(uuid);
		if(children.size()>0)
			for(Permission p:children)
				deleteById(p.getUuid());
	}

	@Override
	public List<String> getPermissions() {
		return jdbcTemplate.queryForList("SELECT permission FROM user_permission", String.class);
	}

	@Override
	public List<String> getAdminPermissions() {
		String[] b = new String[]{"1", "10", "2", "3", "4", "5", "6", "7", "8", "9","21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "45", "49","67","68","69","91","94","95","96","102"};
		List<String> permissions = getPermissions();
		permissions.removeAll(Arrays.asList(b));
		return permissions;
	}
	
}
