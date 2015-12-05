package com.ruishengtech.rscc.crm.data.service.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.framework.core.db.service.TableService;
import com.ruishengtech.rscc.crm.data.condition.GroupCallCondition;
import com.ruishengtech.rscc.crm.data.condition.GroupCallDataCondition;
import com.ruishengtech.rscc.crm.data.model.DataContainer;
import com.ruishengtech.rscc.crm.data.model.GroupCall;
import com.ruishengtech.rscc.crm.data.model.GroupCallDataLink;
import com.ruishengtech.rscc.crm.data.service.DataTaskService;
import com.ruishengtech.rscc.crm.data.solution.GroupCallDataSolution;
import com.ruishengtech.rscc.crm.data.solution.GroupCallSolution;
import com.ruishengtech.rscc.crm.data.util.CollectionUtil;

@Service
@Transactional
public class GroupCallServiceImp extends BaseService {
	
	@Autowired
	private DataTaskService dataTaskService;
	
	@Autowired
	private TableService tableService;
	
	public List<GroupCall> getGroupCalls() {
		List<GroupCall> list = getBeanList(GroupCall.class,"");
		return list;
	}

	public GroupCall getByUuid(UUID uuid) {
		return super.getByUuid(GroupCall.class, uuid);
	}
	
	public GroupCall getGroupCallByCallId(String callId) {
		
		List<GroupCall> list = getBeanList(GroupCall.class, "and groupcall_id = ?", callId);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	public GroupCall getGroupCallBydescription(String description) {
		
		List<GroupCall> list = getBeanList(GroupCall.class, "and description = ?", description);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public boolean save(GroupCall g) {
		try {
			super.save(g);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public void save(GroupCall g, String[] excludeFieldName) {
		super.save(g, excludeFieldName);
	}
	
	public boolean update(GroupCall g) {
		try {
	        super.update(g);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public void update(GroupCall p, String[] excludeFieldName) {
		 super.update(p, excludeFieldName);
	}
	
	public boolean deleteById(UUID uuid) {
		try {
			GroupCall g = getByUuid(uuid);
			jdbcTemplate.update("delete from data_group_call_link where groupcall_id=?", g.getGroupcall_id());
			jdbcTemplate.update("delete from data_group_call_user_link where groupcall_id=?", g.getGroupcall_id());
			super.deleteById(GroupCall.class, uuid);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean deleteByGroupCallId(String groupCallId) {
		try {
			GroupCall g = getGroupCallByCallId(groupCallId);
			this.deleteById(g.getUuid());
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public PageResult<GroupCall> queryPage(ICondition condition) {
		return super.queryPage(new GroupCallSolution(), condition, GroupCall.class);
	}
	
	public int getDataCount(String groupcallId) {
		return jdbcTemplate.queryForObject("select count(data_phone) from data_group_call_link where groupcall_id = ? ", Integer.class, groupcallId);
	}
	
	public int getUncompleteDataCount(String groupcallId) {
		return jdbcTemplate.queryForObject("select count(data_phone) from data_group_call_link where groupcall_id = ? and (call_flag = '' or call_flag = '1')", Integer.class, groupcallId);
	}
	
	public int getCalledDataCount(String groupcallId) {
		return jdbcTemplate.queryForObject("select count(data_phone) from data_group_call_link where groupcall_id = ? and call_flag = '2'", Integer.class, groupcallId);
	}
	
	public int getReadyToCallDataCount(String groupcallId) {
		return jdbcTemplate.queryForObject("select count(data_phone) from data_group_call_link where groupcall_id = ? and call_flag = '1'", Integer.class, groupcallId);
	}
	
	public Map<String, Object> getCalledDataStat(String groupcallId) {
		return jdbcTemplate.queryForMap("SELECT SUM(CASE call_flag WHEN '0' THEN 1 ELSE 0 END) not_get, "
				+ "SUM(CASE call_flag WHEN '1' THEN 1 ELSE 0 END) geted, "
				+ "SUM(CASE call_flag WHEN '2' THEN 1 ELSE 0 END) called, "
				+ "SUM(CASE call_flag WHEN '3' THEN 1 ELSE 0 END) answered, "
				+ "SUM(CASE call_flag WHEN '4' THEN 1 ELSE 0 END) single_answered, "
				+ "SUM(CASE call_flag WHEN '5' THEN 1 ELSE 0 END) errored FROM data_group_call_link WHERE groupcall_id = ?", groupcallId);
	}
	
	public int importToTask(String sourceTable, String groupcallId) { //防止插入重复数据
		int count = jdbcTemplate.update("insert into data_group_call_link (groupcall_id, data_id, data_phone, data_source) "
				+ " (select ? groupcall_id,uuid data_id,item_phone data_phone,? data_source from data_item_" + sourceTable + " where not exists("
						+ "select * from data_group_call_link where item_phone = data_group_call_link.data_phone) and item_owner is null)", groupcallId, sourceTable);
		return count;
	}
	
	public void removeDatas(String uuid, int removeData, String[] datas){
		StringBuilder deleteSql = new StringBuilder("delete from data_group_call_link where groupcall_id = ? ");
		switch (removeData) {
		case 0:
			deleteSql.append(" and call_flag is null ");
			break;
		case 1:
			deleteSql.append(" and call_flag is not null ");
			break;
		default:
			break;
		}
		deleteSql.append(" and data_source=? ");
		for (String data : datas) {
			jdbcTemplate.update(deleteSql.toString(), uuid, data);
		}
	}
	
	public Map<String, DataContainer> getDataStats(String groupcallId) {
		List<Map<String,Object>> list = jdbcTemplate.queryForList("select p.data_table data, p.container_name dataname, count(t.data_source) num ,sum(case when t.call_flag>'0' then 1 else 0 end) complete from data_group_call_link t JOIN data_container p ON t.data_source = p.data_table where t.groupcall_id = ? group by t.data_source", groupcallId);
		Map<String, DataContainer> map = new HashMap<String, DataContainer>();
		for(Map<String,Object> m:list) {
			if(Integer.valueOf(m.get("num").toString())!=0){
				DataContainer d = new DataContainer();
				d.setUid(m.get("data").toString());
				d.setContainerName(m.get("dataname").toString());
				d.setDataCount(Integer.valueOf(m.get("num").toString()));
				d.setAllocateCount(Integer.valueOf(m.get("complete").toString()));
				map.put(m.get("data").toString(), d);
			}
		}
		return map;
	}
	
	public List<String> getAdded() {
		return jdbcTemplate.queryForList("select data_source from data_group_call_link", String.class);
	}

	public PageResult<GroupCallDataLink> queryDataPage(ICondition condition) {
		return super.queryPage(new GroupCallDataSolution(), condition, GroupCallDataLink.class);
	}

	public int addUsers(String groupcallId, List<String> users) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String u:users)
			if(!StringUtils.isBlank(u))
				list.add(new Object[]{groupcallId,u});
		int[] nums = jdbcTemplate.batchUpdate("INSERT INTO data_group_call_user_link (groupcall_id,user_uuid) VALUES (?, ?)", list);
		return CollectionUtil.sum(nums);
	}

	public int updateUsers(String groupcallId, String[] users){
		List<String> oldUsers = getUsers(groupcallId);
		List<String> deleteUsers = new ArrayList<String>(oldUsers);
		List<String> addUsers = new ArrayList<String>(users==null?new ArrayList<String>():Arrays.asList(users));
		deleteUsers.removeAll(addUsers);
		addUsers.removeAll(oldUsers);
		return deleteUsers(groupcallId, deleteUsers) + addUsers(groupcallId, addUsers);
	}

	public int deleteUsers(String groupcallId, List<String> users) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String u:users)
			if(!StringUtils.isBlank(u)){
				list.add(new Object[]{groupcallId,u});
			}
		int[] nums = jdbcTemplate.batchUpdate("DELETE FROM data_group_call_user_link WHERE groupcall_id=? and user_uuid=?", list);
		return CollectionUtil.sum(nums);
	}
	
	public List<String> getUsers(String groupcallId) {
		return jdbcTemplate.queryForList("select user_uuid from data_group_call_user_link where groupcall_id = ? ", String.class, groupcallId);
	}
	
	public Map<String,String> getUserTasks() {
		Map<String,String> map = new HashMap<String,String>();
		List<GroupCall> list = this.getGroupCalls();
		for(GroupCall l:list)
			map.put(l.getUid(), String.valueOf(dataTaskService.getUserUncompleteCount(l.getUid())));
		return map;
	}
	
	public Map<String,String> getUserStat() {
		Map<String,String> map = new HashMap<String,String>();
		List<GroupCall> list = this.getGroupCalls();
		for(GroupCall l:list)
			map.put(l.getUid(), dataTaskService.getUserUncompleteCount(l.getUid()) + "/" + dataTaskService.getTaskCount(l.getUid()));
		return map;
	}
	
	public int getUserCount(String groupcallId) {
		return jdbcTemplate.queryForObject("select count(user_uuid) from data_group_call_user_link where groupcall_id = ? ", Integer.class, groupcallId);
	}

	public void update(String uuid) {
		StringBuilder updateSql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		updateSql.append("UPDATE data_project SET data_count=?,complete_count=? WHERE uuid=?");
		params.add(getTemplate().queryForObject("select count(*) from data_task_" + uuid, Integer.class));
		params.add(getTemplate().queryForObject("select count(*) from data_task_" + uuid + " where call_times > 0 ", Integer.class));
		params.add(uuid);
		getTemplate().update(updateSql.toString(), params.toArray());
	}

	public boolean batDelete(String[] uuids) {
		for(String u:uuids)
			this.deleteByGroupCallId(u);
		return true;
	}

	public Map<String,GroupCall> getUserGroupCallMap(){
		Map<String,GroupCall> map = new HashMap<String,GroupCall>();
		List<GroupCall> list = getGroupCalls();
		for(GroupCall p:list)
			map.put(p.getUid(), p);
		return map;
	}

	public List<String> getGroupCalls(GroupCallCondition condition) {
		StringBuilder querySql = new StringBuilder("select t.uuid ");
		List<Object> params = new ArrayList<Object>();
		GroupCallSolution solution = new GroupCallSolution();
		solution.getWhere(condition, querySql, params);
		return jdbcTemplate.queryForList(querySql.toString(), params.toArray(), String.class); 
	}

	//新增修改flag和限制数量
	public List<GroupCallDataLink> getData(String groupcall_id, int qty) {
		List<GroupCallDataLink> list = getBeanListWithOrder(GroupCallDataLink.class, " and groupcall_id=? and call_flag='0' ", " data_phone limit ?", groupcall_id, qty);
		jdbcTemplate.update("update data_group_call_link set call_flag = '1' where groupcall_id=? and call_flag='0' order by data_phone limit ?", groupcall_id, qty);
		return list; 
	}

	public void saveGroupCallDataLink(GroupCallDataLink groupCallDataLink) {
		super.save(groupCallDataLink);
	}
	
	public void changeStat(String uuid, String stat) {
		jdbcTemplate.update("update data_group_call set stat=? where uuid=?",stat, uuid);
	}
	
	public void stopTask(GroupCall groupCall) {
		changeStat(groupCall.getUid(), "0");
//		GroupCall g = getByUuid(UUID.UUIDFromString(groupCallId));
		jdbcTemplate.update("update data_group_call_link set call_flag=? where groupcall_id=? and call_flag='1'", GroupCall.stat_not_get, groupCall.getGroupcall_id());
	}
	
	public void collection(String groupCall) {
//		changeStat(groupCall.getUid(), "0");
//		GroupCall g = getByUuid(UUID.UUIDFromString(groupCallId));
		jdbcTemplate.update("update data_group_call_link set call_flag=? where groupcall_id=? and call_flag='2'", GroupCall.stat_not_get, groupCall);
	}
	
	public void changeDataStat(JSONObject jsonObject) {
		if(jsonObject == null)
			return; 
		JSONObject data = jsonObject.optJSONObject("data");
		if(data == null)
			return;
		JSONObject cdr = jsonObject.optJSONObject("cdr");
		String dataStat = null;
		//如果没有收到cdr，这条消息异常。收到cdr时长为0，未接通。收到cdr时长大于0，接通。
		if(cdr == null) {
			dataStat = GroupCall.stat_errored;
		} else {
			//bridgesec大于0，双方接通。bridgesec等于0，billsec大于0，单方接通。
			dataStat = cdr.optInt("bridgesec") > 0?GroupCall.stat_answered:
				cdr.optInt("billsec") > 0?GroupCall.stat_single_answered:GroupCall.stat_called;
		}
		jdbcTemplate.update("update data_group_call_link set call_flag=? where data_id=?", dataStat, data.optString("data_id"));
	}
	
	public void removeOneData(String groupCallId, String dataPhone) {
		jdbcTemplate.update("delete from data_group_call_link where groupcall_Id=? and data_phone=?", groupCallId, dataPhone);
	}
	
	public void batchRemoveData(String groupCallId, String[] phones) {
		for (String phone : phones) {
			jdbcTemplate.update("delete from data_group_call_link where groupcall_Id=? and data_phone=?", groupCallId, phone);
		}
	}

	public List<String> getAllItem(GroupCallDataCondition condition) {
		StringBuilder querySql = new StringBuilder("select t.data_phone ");
		List<Object> params = new ArrayList<Object>();
		GroupCallDataSolution solution = new GroupCallDataSolution();
		solution.getWhere(condition, querySql, params);
		return jdbcTemplate.queryForList(querySql.toString(), params.toArray(), String.class); 
	}

	public void resetData(String groupcallId, String type) {
		//type为0重置单方接通，1重置双方接通
		String resetWhat = null;
		if ("0".equals(type)) {
			resetWhat = GroupCall.stat_single_answered;
		} else if ("1".equals(type)) {
			resetWhat = GroupCall.stat_called;
		}
		jdbcTemplate.update("update data_group_call_link set call_flag='0' where groupcall_Id=? and call_flag=?", groupcallId, resetWhat);
	}
	
}
