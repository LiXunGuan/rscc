package com.ruishengtech.rscc.crm.user.service.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
import com.ruishengtech.framework.core.db.service.TableService;
import com.ruishengtech.rscc.crm.user.model.Datarange;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.solution.DatarangeSolution;
@Service
@Transactional
public class DatarangeServiceImp extends BaseService implements DatarangeService {

	public final static String ALL_DATARANGE = "ada";
	
	public final static String ALL_QUEUE = "aee";
	
	public static Set<String> resultSet = new HashSet<>();
	
	@Autowired
	private TableService tableService;
	
	public List<Datarange> getAllDatarange() {
//		return getBeanListWithOrder(Datarange.class, "", "datarange_name");
		return getBeanList(Datarange.class, " ORDER BY date ASC ");
	}
	
	public boolean save(Datarange d) {
		super.save(d);
		return true;
	}
	
	public void save(Datarange d, String[] excludeFieldName) {
		super.save(d, excludeFieldName);
	}
	
	public boolean update(Datarange d) {
		if(d.getUuid().toString().equals(d.getParentUuid()) || "0".equals(d.getUid()) || "0".equals(d.getParentUuid()))//0不能作为父
			return false;
        super.update(d, new String[]{"date"});
		return true;
	}
	
	public void update(Datarange d, String[] excludeFieldName) {
		 super.update(d, excludeFieldName);
	}
	
	public boolean deleteById(UUID uuid) {
		if("01".equals(uuid.toString()) || "00".equals(uuid.toString()))
			return true;
		deleteChildren(uuid);
		jdbcTemplate.update("update user_user set department='01' where department=?", uuid.toString());
		super.deleteById(Datarange.class, uuid);
		jdbcTemplate.update("DELETE FROM user_datarangerole_datarange_link WHERE datarange_uuid=?", uuid.toString());
		jdbcTemplate.update("DELETE FROM user_user_datarange_link WHERE datarange_uuid=?", uuid.toString());
		return true;
	}
	
	public Datarange getByUuid(UUID uuid) {
		return super.getByUuid(Datarange.class, uuid);
	}
	
	public PageResult<Datarange> queryPage(ICondition condition) {
		return super.queryPage(new DatarangeSolution(), condition, Datarange.class);
	}
	
	public Datarange getDatarangeByName(String datarangeName) {
		
		List<Datarange> list = getBeanList(Datarange.class, "and datarange_name = ?", datarangeName);
		
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}

	
	public List<Datarange> getChildren(String datarangeUuid){	//获取子区域
    	
		return getBeanList(Datarange.class, "and parent_uuid = ? ORDER BY date ASC", datarangeUuid);
    }

	public Set<String> getAllChildren(String datarangeUuid){	//获取子区域
		Set<String> resultSet = new TreeSet<>();
		resultSet.add(datarangeUuid);
		List<String> children = jdbcTemplate.queryForList("select uuid from user_datarange where parent_uuid=? ORDER BY date ASC", String.class, datarangeUuid);
		for (String child : children) {
			resultSet.addAll(getAllChildren(child));
		}
		return resultSet;
	}
	
	public List<Datarange> getChildren(UUID datarangeUuid){	//获取子区域
    	
		return getChildren(datarangeUuid.toString());
    }
	
	@Override
	public JSONArray getChildrenDatarangeTree(String datarangeUuid) {
		JSONArray ret = new JSONArray();
		Set<String> childs = getAllChildren(datarangeUuid);
		for (String str : childs) {
			Datarange d = getByUuid(UUID.UUIDFromString(str));
			JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", d.getUuid().toString());
            jsonObject.put("pId", d.getParentUuid());
            jsonObject.put("name", d.getDatarangeName());
            jsonObject.put("open", true);
            ret.put(jsonObject);
		}
		return ret;
	}
	
	public JSONArray getDatarangeTree() {
        JSONArray ret = new JSONArray();
        List<Datarange> dataranges = getAllDatarange();
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
            ret.put(jsonObject);
        }
        return ret;
	}	
	
	public JSONArray getDatarangeRootTree() {
        JSONArray ret = new JSONArray();
        
        JSONObject jsonRoot = new JSONObject();
        jsonRoot.put("id", "datarange");
        jsonRoot.put("pId", "root");
        jsonRoot.put("name", "部门");
        jsonRoot.put("open", true);
        ret.put(jsonRoot);
        
//        JSONObject jsonAll = new JSONObject();
//        jsonAll.put("id", "ada");
//        jsonAll.put("pId", "datarange");
//        jsonAll.put("name", "全部部门");
//        jsonAll.put("open", true);
//        ret.put(jsonAll);
        List<Datarange> dataranges = getAllDatarange();
   /*     var zNodes =[  
        { id:1, pId:0, name:"父节点 1", open:true},  
        { id:11, pId:1, name:"叶子节点 1-1"},  
        { id:2, pId:0, name:"父节点 2", open:true},  
        { id:21, pId:2, name:"叶子节点 2-1"}
        ];*/

        for (Datarange d : dataranges) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", "1".equals(d.getUid())?"01":d.getUid());
            jsonObject.put("pId", StringUtils.isBlank(d.getParentUuid())?"datarange":"1".equals(d.getParentUuid())?"01":d.getParentUuid());
            jsonObject.put("name", d.getDatarangeName());
            jsonObject.put("open", true);
            ret.put(jsonObject);
        }
        return ret;
	}
	
	public Datarange getParent(Datarange datarange) {
		return getByUuid(UUID.UUIDFromString(datarange.getParentUuid()));
	}
	
	public void deleteChildren(UUID uuid) {
		List<Datarange> children = getChildren(uuid);
		if(children.size()>0)
			for(Datarange p:children)
				deleteById(p.getUuid());
	}

	@Override
	public List<Datarange> getByList(List<UUID> l) {
		ArrayList<Datarange> list = new ArrayList<Datarange>();
		for(UUID u:l)
			list.add(getByUuid(u));
		return list;
	}
	
	public List<Datarange> getByType(UUID typeUuid) {
		List<Datarange> list = getBeanList(Datarange.class, "and type_uuid = ?", typeUuid.toString());
		return list;
	}
	
	public Map<String, Datarange> getLeafNodes() {
		List<Datarange> list = getAllDatarange();
		Set<String> s = new HashSet<String>();
		Map<String, Datarange> m = new HashMap<String, Datarange>();
		for(Datarange l:list){
			s.add(l.getParentUuid());
		}
		for(Datarange l:list) {
			if(!s.contains(l.getUuid().toString()))
				m.put(l.getTypeUuid(), l);
		}
		return m;
	}

	@Override
	public boolean batDelete(String[] uuids) {
		for(String u:uuids)
			this.deleteById(UUID.UUIDFromString(u));
		return true;
	}

	@Override
	public List<String> getAllDatarange(ICondition condition) {
		StringBuilder querySql = new StringBuilder("select t.uuid ");
		List<Object> params = new ArrayList<Object>();
		DatarangeSolution solution = new DatarangeSolution();
		solution.getWhere(condition, querySql, params);
		return jdbcTemplate.queryForList(querySql.toString(), params.toArray(), String.class); 
	}

	@Override
	public List<User> getUsers(String datarange) {
		return this.getBeanListWithSql(User.class, "select p.* from user_user_datarange_link t join user_user p on t.user_uuid = p.uuid where t.datarange_uuid = ?", datarange);
	}

	@Override
	public List<User> getUsersByDatarange(String datarange) {
		return this.getBeanListWithSql(User.class, "select u.*,v.datarange_name departmentName from user_user u left join user_datarange v on u.department=v.uuid where u.department = ? and u.delete_flag != '1'", datarange);
	}
	
	@Override
	public List<String> getUserUuids(String datarange) {
		return jdbcTemplate.queryForList("select u.uuid from user_user u where u.department = ? and delete_flag != '1'", String.class, datarange);
	}
	
	@Override
	public Collection<String> getUserUuidsByDataranges(Collection<String> dataranges) {
		Set<String> set = new HashSet<String>();
		for (String string : dataranges) {
			set.addAll(getUserUuids(string));
		}
		return set;
	}


	@Override
	public List<Datarange> getDataranges(Collection<String> list) {
		List<Datarange> l = new ArrayList<Datarange>();
		for (String s : list) {
			Datarange datarange = this.getByUuid(UUID.UUIDFromString(s));
			if(datarange!=null)
				l.add(datarange);
		}
		return l;
	}

	@Override
	public List<String> getAllDatarangeUuid() {
		return jdbcTemplate.queryForList("select uuid from user_datarange", String.class);
	}

	@Override
	public Set<String> getParentUuid(String uuid) {
		String pUuid = jdbcTemplate.queryForObject("select parent_uuid from user_datarange where uuid = ?", String.class, uuid);
		if(StringUtils.isNotBlank(pUuid)){
			resultSet.add(pUuid);
			return getParentUuid(pUuid);
		}
		return resultSet;
	}

	@Override
	public String getOpenFlag(String batchUuid, String deptUuid) {
		List<String> strs = jdbcTemplate.queryForList("select open_flag from new_data_batch_department_link where data_batch_uuid = ? and department_uuid = ?", String.class, batchUuid, deptUuid);
		if(strs.size() > 0){
			return strs.get(0);
		}
		return null;
	}
	
	public String getPar(String uuid){
		return jdbcTemplate.queryForObject("select parent_uuid from user_datarange where uuid = ?", String.class, uuid);
	}

}
