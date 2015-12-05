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
import com.ruishengtech.rscc.crm.user.model.Datarange;
import com.ruishengtech.rscc.crm.user.model.DatarangeRole;
import com.ruishengtech.rscc.crm.user.service.DatarangeRoleService;
import com.ruishengtech.rscc.crm.user.solution.DatarangeRoleSolution;
import com.ruishengtech.rscc.crm.user.util.CollectionUtil;
@Service
@Transactional
public class DatarangeRoleServiceImp extends BaseService implements DatarangeRoleService {

	public List<DatarangeRole> getAllDatarangeRole() {
		return getBeanList(DatarangeRole.class,"");
	}
	
	public DatarangeRole getDatarangeRoleByName(String datarangeRoleName) {
		List<DatarangeRole> list = getBeanList(DatarangeRole.class, "and role_name=?", datarangeRoleName);
		if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public boolean save(DatarangeRole d) {
		super.save(d);
		return true;
	}
	
	public boolean save(DatarangeRole d, String[] dataranges) {
		super.save(d);
		int item = bindDataranges(d.getUuid().toString(), Arrays.asList(dataranges));
		return true;
	}
	
	public boolean update(DatarangeRole d) {
		if(d.getUuid().toString().equals(d.getParentUuid()))
			return false;
        super.update(d);
		return true;
    }
	
	public boolean update(DatarangeRole d, String[] dataranges) {
		super.update(d);
		int item = updateDataranges(d.getUuid().toString(), Arrays.asList(dataranges));
		return true;
	}
	
	public boolean deleteById(UUID uuid) {
		deleteChildren(uuid);
		super.deleteById(DatarangeRole.class, uuid);
		jdbcTemplate.update("DELETE FROM user_user_datarangerole_link WHERE datarangerole_uuid=?", uuid.toString());
		jdbcTemplate.update("DELETE FROM user_group_datarangerole_link WHERE datarangerole_uuid=?", uuid.toString());
		jdbcTemplate.update("DELETE FROM user_datarangerole_datarange_link WHERE datarangerole_uuid=?", uuid.toString());
		return true;
	}
	
	public DatarangeRole getByUuid(UUID uuid) {
		return super.getByUuid(DatarangeRole.class, uuid);
	}
	
	public PageResult<DatarangeRole> queryPage(ICondition condition) {
		return super.queryPage(new DatarangeRoleSolution(), condition, DatarangeRole.class);
	}
	
	public boolean bindDatarange(String datarangeRoleUuid, String datarangeUuid) {
		if(!StringUtils.isBlank(datarangeRoleUuid) && !StringUtils.isBlank(datarangeUuid))
			return 1==jdbcTemplate.update("INSERT INTO user_datarangerole_datarange_link (datarangerole_uuid, datarange_uuid) VALUES (?, ?)", datarangeRoleUuid, datarangeUuid);
		return true;
	}
	
	
	public boolean bindDatarange(UUID datarangeRoleUuid, UUID datarangeUuid) {
		return bindDatarange(datarangeRoleUuid.toString(), datarangeUuid.toString());
	}

	
	public int bindDataranges(String datarangeRoleUuid, List<String> dataranges) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String d:dataranges)
			if(!StringUtils.isBlank(d))
				list.add(new Object[]{datarangeRoleUuid,d});
		int[] nums = jdbcTemplate.batchUpdate("INSERT INTO user_datarangerole_datarange_link (datarangerole_uuid, datarange_uuid) VALUES (?, ?)", list);
		return CollectionUtil.sum(nums);
	}
	
	
	public int bindDataranges(UUID datarangeRoleUuid, List<UUID> dataranges) {
		return bindDataranges(datarangeRoleUuid.toString(), CollectionUtil.uuidToString(dataranges));
	}

	
	public boolean unbindDatarange(String datarangeRoleUuid, String datarangeUuid) {
		if(!StringUtils.isBlank(datarangeRoleUuid) && !StringUtils.isBlank(datarangeUuid))
			return 1==jdbcTemplate.update("DELETE FROM user_datarangerole_datarange_link WHERE datarangerole_uuid=? and datarange_uuid=?", datarangeRoleUuid, datarangeUuid);
		return true;
	}
	
	
	public boolean unbindDatarange(UUID datarangeRoleUuid, UUID datarangeUuid) {
		return unbindDatarange(datarangeRoleUuid.toString(), datarangeUuid.toString());
	}

	
	public int unbindDataranges(String datarangeRoleUuid, List<String> dataranges) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String d:dataranges)
			if(!StringUtils.isBlank(d))
				list.add(new Object[]{datarangeRoleUuid,d});
		int[] nums = jdbcTemplate.batchUpdate("DELETE FROM user_datarangerole_datarange_link WHERE datarangerole_uuid=? and datarange_uuid=?", list);
		return CollectionUtil.sum(nums);
	}
	
	
	public int unbindDataranges(UUID datarangeRoleUuid, List<UUID> dataranges) {
		return unbindDataranges(datarangeRoleUuid.toString(), CollectionUtil.uuidToString(dataranges));
	}
	
	
	public int updateDataranges(String datarangeRoleUuid, List<String> dataranges) {
		List<String> oldDataranges = getDataranges(datarangeRoleUuid);
		List<String> unbindDataranges = new ArrayList<String>(oldDataranges);
		List<String> bindDataranges = new ArrayList<String>(dataranges);
		unbindDataranges.removeAll(dataranges);
		bindDataranges.removeAll(oldDataranges);
		return unbindDataranges(datarangeRoleUuid, unbindDataranges) + bindDataranges(datarangeRoleUuid, bindDataranges);
	}
	
	
	public int updateDataranges(UUID datarangeRoleUuid, List<UUID> dataranges) {
		return updateDataranges(datarangeRoleUuid.toString(), CollectionUtil.uuidToString(dataranges));
	}

	
	public List<String> getDataranges(String datarangeRoleUuid) {
		List<String> list = jdbcTemplate.queryForList("SELECT datarange_uuid FROM user_datarangerole_datarange_link WHERE datarangerole_uuid = ?", String.class, datarangeRoleUuid);
		return list;
	}
	
	
	public List<UUID> getDataranges(UUID datarangeRoleUuid) {
		List<String> list = getDataranges(datarangeRoleUuid.toString());
		return CollectionUtil.stringToUuid(list);
	}

	
	public List<Datarange> getDataranges(DatarangeRole datarangeRole) {
		List<Datarange> list = getBeanList(Datarange.class, "and uuid IN (SELECT datarange_uuid FROM user_datarangerole_datarange_link WHERE datarangerole_uuid=?)", datarangeRole.getUuid().toString());
		return list;
	}

	
	public List<DatarangeRole> getChildren(String datarangeRoleUuid){	//获取子分组
		return getBeanList(DatarangeRole.class, "and parent_uuid = ?", datarangeRoleUuid);
    }
	
	
	public List<DatarangeRole> getChildren(UUID datarangeRoleUuid){	//获取子分组
		return getChildren(datarangeRoleUuid.toString());
    }
	
	public DatarangeRole getParent(DatarangeRole datarangeRole) {
		return getByUuid(UUID.UUIDFromString(datarangeRole.getParentUuid()));
	}

	public JSONArray getDatarangeRoleTree() {
        JSONArray ret = new JSONArray();
        List<DatarangeRole> datarangeRoles = getAllDatarangeRole();
   /*     var zNodes =[  
        { id:1, pId:0, name:"父节点 1", open:true},  
        { id:11, pId:1, name:"叶子节点 1-1"},  
        { id:2, pId:0, name:"父节点 2", open:true},  
        { id:21, pId:2, name:"叶子节点 2-1"}
        ];*/

        for (DatarangeRole d : datarangeRoles) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", d.getUuid().toString());
            jsonObject.put("pId", d.getParentUuid());
            jsonObject.put("name", d.getDatarangeRoleName());
            jsonObject.put("open", true);
            ret.put(jsonObject);
        }
        return ret;
	}

	public void deleteChildren(UUID uuid) {
		List<DatarangeRole> children = getChildren(uuid);
		if(children.size()>0)
			for(DatarangeRole p:children)
				deleteById(p.getUuid());
	}
	
}
