package com.ruishengtech.rscc.crm.datamanager.service.imp;

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
import com.ruishengtech.rscc.crm.data.service.DataTaskService;
import com.ruishengtech.rscc.crm.data.util.CollectionUtil;
import com.ruishengtech.rscc.crm.datamanager.condition.NewGroupCallCondition;
import com.ruishengtech.rscc.crm.datamanager.condition.NewGroupCallDataCondition;
import com.ruishengtech.rscc.crm.datamanager.model.DepartmentData;
import com.ruishengtech.rscc.crm.datamanager.model.GroupCallData;
import com.ruishengtech.rscc.crm.datamanager.model.NewGroupCall;
import com.ruishengtech.rscc.crm.datamanager.model.NewGroupCallDataLink;
import com.ruishengtech.rscc.crm.datamanager.model.NewGroupCallResultLink;
import com.ruishengtech.rscc.crm.datamanager.solution.NewGroupCallDataSolution;
import com.ruishengtech.rscc.crm.datamanager.solution.NewGroupCallSolution;
import com.ruishengtech.rscc.crm.datamanager.solution.NewGroupCallStatisticSolution;

@Service
@Transactional
public class NewGroupCallServiceImp extends BaseService {
	
	@Autowired
	private DataTaskService dataTaskService;
	
	@Autowired
	private TableService tableService;
	
	public List<NewGroupCall> getGroupCalls() {
		List<NewGroupCall> list = getBeanList(NewGroupCall.class,"");
		return list;
	}

	public NewGroupCall getByUuid(UUID uuid) {
		return super.getByUuid(NewGroupCall.class, uuid);
	}
	
	public NewGroupCall getGroupCallByCallId(String callId) {
		
		List<NewGroupCall> list = getBeanList(NewGroupCall.class, "and groupcall_id = ?", callId);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	public NewGroupCall getGroupCallBydescription(String description) {
		
		List<NewGroupCall> list = getBeanList(NewGroupCall.class, "and description = ?", description);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public boolean save(NewGroupCall g) {
		super.save(g);
		return true;
	}
	
	public void save(NewGroupCall g, String[] excludeFieldName) {
		super.save(g, excludeFieldName);
	}
	
	public boolean update(NewGroupCall g) {
        super.update(g, new String[]{"dataCount","stat"});
		return true;
	}
	
	public void update(NewGroupCall p, String[] excludeFieldName) {
		 super.update(p, excludeFieldName);
	}
	
	//删除一个群呼任务时，需要做五个操作。1、删除群呼任务表中一行数据。2、清空结果表中该群呼产生的所有结果。3、删除关联表所有和这个群呼关联的数据。
	//4、清空这批数据上的锁定标志。5、复位关联的部门表中的数据。
	public boolean deleteById(UUID uuid) {
		NewGroupCall g = getByUuid(uuid);
		List<NewGroupCallDataLink> list = getBeanList(NewGroupCallDataLink.class, " and groupcall_id=?", g.getGroupcall_id());
		
		//4
		String removeLockSql = "update new_data_batch_department_link set is_lock='0' where data_batch_uuid=? and department_uuid=? ";
		for (NewGroupCallDataLink newGroupCallDataLink : list) {
			//5
			String resetDataStat = "update new_data_department_" + newGroupCallDataLink.getDataDept() + " set call_stat='0' where batch_uuid=?";
			jdbcTemplate.update(resetDataStat, newGroupCallDataLink.getDataBatch());
			jdbcTemplate.update(removeLockSql, newGroupCallDataLink.getDataBatch(), newGroupCallDataLink.getDataDept());
		}
		//3
		jdbcTemplate.update("delete from new_data_group_call_link where groupcall_id=?", g.getGroupcall_id());
		//2
		jdbcTemplate.update("delete from new_data_group_call_result where groupcall_id=?", g.getGroupcall_id());
		//1
		super.deleteById(NewGroupCall.class, uuid);
		return true;
	}
	
	public boolean deleteByGroupCallId(String groupCallId) {
		NewGroupCall g = getGroupCallByCallId(groupCallId);
		this.deleteById(g.getUuid());
		return true;
	}
	
	public PageResult<NewGroupCall> queryPage(ICondition condition) {
		return super.queryPage(new NewGroupCallSolution(), condition, NewGroupCall.class);
	}
	
	public Map<String, Object> getCalledDataStat(String groupcallId) {
		return jdbcTemplate.queryForMap("SELECT SUM(CASE call_result WHEN '0' THEN 1 ELSE 0 END) not_get, "
				+ "SUM(CASE call_result WHEN '1' THEN 1 ELSE 0 END) geted, "
				+ "SUM(CASE call_result WHEN '2' THEN 1 ELSE 0 END) called, "
				+ "SUM(CASE call_result WHEN '3' THEN 1 ELSE 0 END) single_answered, "
				+ "SUM(CASE call_result WHEN '4' THEN 1 ELSE 0 END) answered, "
				+ "SUM(CASE call_result WHEN '5' THEN 1 ELSE 0 END) errored FROM new_data_group_call_result WHERE groupcall_id = ?", groupcallId);
	}
	
	public Integer getCalledDataCount(String groupcallId) {
		return jdbcTemplate.queryForObject("SELECT count(*) FROM new_data_group_call_result WHERE groupcall_id = ?", Integer.class, groupcallId);
	}
	
	public void removeDatas(String uuid, int removeData, String[] datas){
		StringBuilder deleteSql = new StringBuilder("delete from data_group_call_link where groupcall_id = ? ");
		switch (removeData) {
		case 0:
			deleteSql.append(" and call_result is null ");
			break;
		case 1:
			deleteSql.append(" and call_result is not null ");
			break;
		default:
			break;
		}
		deleteSql.append(" and data_source=? ");
		for (String data : datas) {
			jdbcTemplate.update(deleteSql.toString(), uuid, data);
		}
	}
	
	public List<String> getAdded() {
		return jdbcTemplate.queryForList("select data_source from data_group_call_link", String.class);
	}

	public PageResult<NewGroupCallResultLink> queryResultPage(ICondition condition) {
		return super.queryPage(new NewGroupCallDataSolution(), condition, NewGroupCallResultLink.class);
	}
	
	public PageResult<NewGroupCallDataLink> queryStatistic(ICondition condition) {
		return super.queryPage(new NewGroupCallStatisticSolution(), condition, NewGroupCallDataLink.class);
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
		List<NewGroupCall> list = this.getGroupCalls();
		for(NewGroupCall l:list)
			map.put(l.getUid(), String.valueOf(dataTaskService.getUserUncompleteCount(l.getUid())));
		return map;
	}
	
	public Map<String,String> getUserStat() {
		Map<String,String> map = new HashMap<String,String>();
		List<NewGroupCall> list = this.getGroupCalls();
		for(NewGroupCall l:list)
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

	public Map<String,NewGroupCall> getUserGroupCallMap(){
		Map<String,NewGroupCall> map = new HashMap<String,NewGroupCall>();
		List<NewGroupCall> list = getGroupCalls();
		for(NewGroupCall p:list)
			map.put(p.getUid(), p);
		return map;
	}

	public List<String> getGroupCalls(NewGroupCallCondition condition) {
		StringBuilder querySql = new StringBuilder("select t.uuid ");
		List<Object> params = new ArrayList<Object>();
		NewGroupCallSolution solution = new NewGroupCallSolution();
		solution.getWhere(condition, querySql, params);
		return jdbcTemplate.queryForList(querySql.toString(), params.toArray(), String.class); 
	}

	//新增修改flag和限制数量
	public List<GroupCallData> getData(String groupcall_id, int qty) {
		NewGroupCall groupCall = getGroupCallByCallId(groupcall_id);
		List<GroupCallData> totalList = new ArrayList<>();
		if (groupCall.getCurrentSource() == null || groupCall.getCurrentSource() == 0) {
			//获取和当前群呼关联的部门批次链接，即新建任务时加入的那些数据
			List<NewGroupCallDataLink> linkList = getBeanList(NewGroupCallDataLink.class, " and groupcall_id=?", groupcall_id);
			for (NewGroupCallDataLink newGroupCallDataLink : linkList) {
				List<DepartmentData> list = getBeanListWithSql(DepartmentData.class, " select uuid, batch_uuid, phone_number,? own_department from "
						+ "new_data_department_" + newGroupCallDataLink.getDataDept() + " where call_stat='0' and batch_uuid=? order by phone_number limit ?", 
						newGroupCallDataLink.getDataDept(), newGroupCallDataLink.getDataBatch(), qty);
				jdbcTemplate.update("update new_data_department_" + newGroupCallDataLink.getDataDept() + 
						" set call_stat = '1' where call_stat='0' and batch_uuid=? order by phone_number limit ?", newGroupCallDataLink.getDataBatch(), qty);
				qty -= list==null?0:list.size();
				if (list != null) {
					totalList.addAll(list);
				}
				if (qty == 0) {
					break;
				}
			}
		} else {
			//防止重复呼叫
			List<NewGroupCallResultLink> linkList = getBeanList(NewGroupCallResultLink.class, 
					" and groupcall_id=? and call_result=? and is_called='0' order by data_phone limit ?", 
					groupcall_id, groupCall.getCurrentSource(), qty);
			jdbcTemplate.update("update new_data_group_call_result set is_called='1' where groupcall_id=? and call_result=? "
					+ "and is_called='0' order by data_phone limit ?", 
					groupcall_id, groupCall.getCurrentSource(), qty);
			if (linkList != null) {
				totalList.addAll(linkList);
			}
		}
		return totalList; 
	}

	public void saveNewGroupCallDataLink(NewGroupCallDataLink NewGroupCallDataLink) {
		super.save(NewGroupCallDataLink);
	}
	
	//新的可用
	public void changeStat(String uuid, String stat) {
		jdbcTemplate.update("update new_data_group_call set stat=? where uuid=?",stat, uuid);
	}
	
	//新的可用
	//停止任务时，把所有获取过但是没呼叫的数据复位，可能有问题
	public void stopTask(NewGroupCall groupCall) {
		changeStat(groupCall.getUid(), "0");
		List<NewGroupCallDataLink> list = getBeanList(NewGroupCallDataLink.class, " and groupcall_id=?", groupCall.getGroupcall_id());
		
		//结束后复位数据来源
		jdbcTemplate.update("update new_data_group_call set current_source=? where groupcall_Id=?", 0, groupCall.getGroupcall_id());
		
		for (NewGroupCallDataLink newGroupCallDataLink : list) {
			jdbcTemplate.update("update new_data_department_" + newGroupCallDataLink.getDataDept() + 
					" set call_stat=? where call_stat='1' and batch_uuid=?", NewGroupCall.stat_not_get, newGroupCallDataLink.getDataBatch());
		}
		
	}
	
//	public void collection(String groupCall) {
////		changeStat(groupCall.getUid(), "0");
////		GroupCall g = getByUuid(UUID.UUIDFromString(groupCallId));
//		jdbcTemplate.update("update data_group_call_link set call_result=? where groupcall_id=? and call_result='2'", NewGroupCall.stat_not_get, groupCall);
//	}
	
	//新的
	//修改一条数据为已呼叫状态
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
			dataStat = NewGroupCall.stat_errored;
		} else {
			//bridgesec大于0，双方接通。bridgesec等于0，billsec大于0，单方接通。
			dataStat = cdr.optInt("bridgesec") > 0?NewGroupCall.stat_answered:
				cdr.optInt("billsec") > 0?NewGroupCall.stat_single_answered:NewGroupCall.stat_called;
		}

		NewGroupCallResultLink result = new NewGroupCallResultLink();
		String dataId = data.optString("data_id");
		
		String[] dataInfo = new String[3];
		String[] dataSplit = dataId.split("-");
		
		//防止数据出错
		for (int i = 0; i < 3; i++) {
			if (i >= dataSplit.length) {
				dataInfo[i] = "";
			} else {
				dataInfo[i] = dataSplit[i];
			}
		}
		
		result.setDataBatch(dataInfo[0]);
		result.setDataDept(dataInfo[1]);
		result.setDataPhone(data.optString("phonenumber"));
		result.setCallResult(dataStat);
		result.setGroupcallId(jsonObject.optString("groupcall_id"));
		result.setIsCalled("1");
		//先删除之前的结果
		jdbcTemplate.update("delete from new_data_group_call_result where groupcall_id=? and data_phone=?", result.getGroupcallId(), result.getDataPhone());		
		super.save(result);
		
		jdbcTemplate.update("update new_data_department_" + result.getDataDept() +  " set call_stat=? where phone_number=?", dataStat, result.getDataPhone());
		
	}
	
	//删除这一条会导致无法再重新呼叫，除非重新加入群呼任务，ok
	public void removeOneData(String groupCallId, String dataPhone) {
		jdbcTemplate.update("delete from new_data_group_call_result where groupcall_Id=? and data_phone=?", groupCallId, dataPhone);
	}
	
	public void batchRemoveData(String groupCallId, String[] phones) {
		for (String phone : phones) {
			jdbcTemplate.update("delete from data_group_call_link where groupcall_Id=? and data_phone=?", groupCallId, phone);
		}
	}

	public List<String> getAllItem(NewGroupCallDataCondition condition) {
		StringBuilder querySql = new StringBuilder("select t.data_phone ");
		List<Object> params = new ArrayList<Object>();
		NewGroupCallDataSolution solution = new NewGroupCallDataSolution();
		solution.getWhere(condition, querySql, params);
		return jdbcTemplate.queryForList(querySql.toString(), params.toArray(), String.class); 
	}

	//复位呼叫信息，is_called，以便群呼取数据
	public boolean recallData(String groupcallId, int type) {
		if(!"0".equals(jdbcTemplate.queryForObject("select stat from new_data_group_call where groupcall_Id=?", String.class, groupcallId))) {
			return false;
		}
		//type为2重呼未接通，3重呼单方接通
		String recallWhat = null;
		if (type == 2) {
			recallWhat = NewGroupCall.stat_called;
		} else if (type == 3) {
			recallWhat = NewGroupCall.stat_single_answered;
		}
		jdbcTemplate.update("update new_data_group_call set current_source=? where groupcall_Id=?", recallWhat, groupcallId);
		jdbcTemplate.update("update new_data_group_call_result set is_called='0' where groupcall_Id=? and call_result=?", groupcallId, recallWhat);
		return true;
	}


	//创建链接时，需要三个操作。1、链接表中增加一行数据。2、修改该数据在部门中的锁定状态。3、更新群呼任务的数据量。
	public void createDataLinks(String groupcall_id, String[] datas) {
		
		String addLockSql = "update new_data_batch_department_link set is_lock='1' where data_batch_uuid=? and department_uuid=? ";
		
		String insertSql = "replace into new_data_group_call_link(groupcall_id,data_batch,data_dept,data_count) values(?,?,?,?)";
		
		Integer count = 0;
		
		for (String data : datas) {
			String[] sources = data.split("-");
			String seleceSql = "select count(*) from new_data_department_" + sources[1] + " where batch_uuid=?";
			int currentCount = jdbcTemplate.queryForObject(seleceSql, Integer.class, sources[0]);
			count += currentCount;
			jdbcTemplate.update(insertSql, groupcall_id, sources[0], sources[1], currentCount);
			jdbcTemplate.update(addLockSql, sources[0], sources[1]);
		}
		
		jdbcTemplate.update("update new_data_group_call set data_count=? where groupcall_id=?", count, groupcall_id);
		
		
	}
	
	//删除链接时，需要五个操作。1、删除链接表中一行数据。2、删除该数据生成的结果。3、修改该数据在部门中的锁定状态。4、复位关联的部门表中的数据。5、更新群呼任务的数据量。
	public void removeDataLinks(String groupcallId, String[] datas){
		
		//3
		String removeLockSql = "update new_data_batch_department_link set is_lock='0' where data_batch_uuid=? and department_uuid=? ";
		//1
		String deleteSql = "delete from new_data_group_call_link where groupcall_id=? and data_batch=? and data_dept=?";
		
		Integer count = 0;
		
		for (String data : datas) {
			String[] sources = data.split("-");
			//1
			jdbcTemplate.update(deleteSql, groupcallId, sources[0], sources[1]);
			//3
			jdbcTemplate.update(removeLockSql, sources[0], sources[1]);
			
			String seleceSql = "select count(*) from new_data_department_" + sources[1] + " where batch_uuid=?";
			count += jdbcTemplate.queryForObject(seleceSql, Integer.class, sources[0]);
			//
			jdbcTemplate.update("update new_data_department_" + sources[1] + " set call_stat='0' where batch_uuid=?", sources[0]);
			//2
			jdbcTemplate.update("delete from new_data_group_call_result where groupcall_id=? and data_batch=? and data_dept=?", 
					groupcallId, sources[0], sources[1]);
		}
		//5
		jdbcTemplate.update("update new_data_group_call set data_count=data_count-? where groupcall_id=?", count, groupcallId);
	}
	
	public List<NewGroupCallDataLink> getAddedLinks(){
		List<NewGroupCallDataLink> linkList = getBeanList(NewGroupCallDataLink.class, "");
		return linkList;
	}
	
	public void resetData(String groupcallId, String type) {
		//type为0重置单方接通，1重置双方接通
		String resetWhat = null;
		if ("0".equals(type)) {
			resetWhat = NewGroupCall.stat_single_answered;
		} else if ("1".equals(type)) {
			resetWhat = NewGroupCall.stat_called;
		}
		jdbcTemplate.update("update data_group_call_link set call_result='0' where groupcall_Id=? and call_result=?", groupcallId, resetWhat);
	}
}
