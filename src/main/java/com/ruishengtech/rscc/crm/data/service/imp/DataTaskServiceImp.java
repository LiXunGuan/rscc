package com.ruishengtech.rscc.crm.data.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.data.model.DataItem;
import com.ruishengtech.rscc.crm.data.model.DataTask;
import com.ruishengtech.rscc.crm.data.service.DataItemService;
import com.ruishengtech.rscc.crm.data.service.DataTaskService;
import com.ruishengtech.rscc.crm.data.service.SynchronizeService;
import com.ruishengtech.rscc.crm.data.solution.TaskSolution;

@Service
@Transactional
public class DataTaskServiceImp extends BaseService implements DataTaskService {
	
	@Autowired
	private DataItemService dataItemService; 

	@Autowired
	private SynchronizeService synchronizedService;
	
	public List<DataTask> getTasks(String tableName) {
		List<DataTask> list = getBeanListWithTable("data_task_" + tableName, DataTask.class, "");
		return list;
	}

	public DataTask getByUuid(String tableName, UUID uuid) {
		
		List<DataTask> list = getBeanListWithTable("data_task_" + tableName, DataTask.class, "and uuid = ?", uuid.toString());
		
		if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public DataTask getByName(String tableName, String itemName) {
		
		List<DataTask> list = getBeanListWithTable("data_task_" + tableName, DataTask.class, "and item_name = ?", itemName);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public List<DataTask> getByPhone(String tableName, String phone) {
		
		List<DataTask> list = getBeanListWithTable("data_task_" + tableName, DataTask.class, "and item_phone = ?", phone);
        return list;
	}
	
	//应该用不到，数据是直接插进来的
	public boolean save(String tableName, DataTask d) {
		try {
			super.save("data_task_" + tableName, d);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	//同上
	public void save(String tableName, DataItem d, String[] excludeFieldName) {
		super.save("data_task_" + tableName, d, excludeFieldName);
	}
		
	public boolean deleteById(String tableName, UUID uuid) {
		try {
			DataTask temp = getByUuid(tableName, uuid);
			jdbcTemplate.update("update data_item_" + temp.getDataSource() + " set item_owner = null, call_times = 0, allocate_time = null where uuid = ? ", uuid.toString());
			super.deleteById("data_task_" + tableName, DataItem.class, uuid);
			synchronizedService.updateContainer(temp.getDataSource());
			synchronizedService.updateProject(tableName);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean batDelete(String dataTable, String[] uuids) {
		for (String u:uuids)
			if (!this.deleteById(dataTable, UUID.UUIDFromString(u)))
				return false;
		return true;
	}
	
	public PageResult<DataTask> queryPage(ICondition condition) {
		return super.queryPage(new TaskSolution(), condition, DataTask.class);
	}
	
	//导入项目，删除源，同时更新数据量
	public int importToTask(String sourceTable, String targetTable) {
		int count = dataItemService.getCount(sourceTable);
		String insertSql = "insert into data_task_" + targetTable + "(uuid,item_name,item_phone,item_address,item_json,data_source) (select * ,'" + sourceTable + "' data_source from data_item_" + sourceTable + " limit 150)";
		String deleteSql = "delete from data_item_" + sourceTable + " limit 150";
		for(int i=0;i<count;i+=150){
			jdbcTemplate.update(insertSql);
			jdbcTemplate.update(deleteSql);
		}
		synchronizedService.updateContainer(sourceTable);
		return count;
	}

	//项目内去重
	public int distinct(String uuid) {
		int count = 0;
		try {
			StringBuilder distinctSql = new StringBuilder();
			distinctSql.append("delete b from ").append("data_task_" + uuid).
				append(" b ,(select *,max(item_owner) as id from ").append("data_task_" + uuid).
				append(" group by item_phone having count(item_phone) > 1) as d where b.uuid>d.uuid and b.item_phone = d.item_phone");
			count = getTemplate().update(distinctSql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int addCallTimes(String tableName, String taskUuid) {
		DataTask d = getByUuid(tableName, UUID.UUIDFromString(taskUuid));
		d.setCallTimes(d.getCallTimes() + 1);
		jdbcTemplate.update("update data_item_" + d.getDataSource() + " set call_times=? where uuid=?", d.getCallTimes(), taskUuid);
		this.update("data_task_" + tableName, d);
		synchronizedService.updateProject(tableName);
		return d.getCallTimes();
	}
	
	/**
	 * 获取任务总数
	 */
	public int getTaskCount(String tableName) {
		return jdbcTemplate.queryForObject(" SELECT count(*) FROM data_task_" + tableName, Integer.class);
	}
	
	/**
	 * 获取未分配任务数
	 */
	public int getUnallocateTaskCount(String projectuUuid) {
		return jdbcTemplate.queryForObject(" SELECT count(*) FROM data_task_" + projectuUuid + " where item_owner is null ", Integer.class);
	}
	
	/**
	 * 获取完成任务数
	 */
	public int getCompleteTaskCount(String projectuUuid) {
		return jdbcTemplate.queryForObject(" SELECT count(*) FROM data_task_" + projectuUuid + " where call_times >= 1 ", Integer.class);
	}
	
	/**
	 * 获取某用户任务总数
	 */
	public int getUserTaskCount(String projectuUuid) {
		return jdbcTemplate.queryForObject(" SELECT count(*) FROM data_task_" + projectuUuid, Integer.class);
	}
	
	/**
	 * 获取某用户未完成任务数
	 */
	public int getUserUncompleteCount(String projectUuid) {
		return jdbcTemplate.queryForObject(" SELECT count(*) FROM data_task_" + projectUuid + " where call_times is null or call_times = 0", Integer.class);
	}

	@Override
	public List<String> getDataSources(String taskTable) {
		return jdbcTemplate.queryForList(" SELECT container_name FROM data_task_" + taskTable + " a join data_container b on a.data_source = b.data_table group by a.data_source", String.class);
	}
	
	public Map<String, String> getDataSourceName(String taskTable) {
		 List<Map<String, Object>> list = jdbcTemplate.queryForList(" SELECT a.data_source m,container_name n FROM data_task_" + taskTable + " a join data_container b on a.data_source = b.data_table group by a.data_source");
		 Map<String, String> map = new HashMap<String, String>();
		 for (Map<String, Object> m : list) {
			map.put(String.valueOf(m.get("m")), String.valueOf(m.get("n")));
		}
		return map;
	}

	@Override
	public void moveTo(String projectUuid, String dataUuid, String poolUuid) {
		DataTask temp = getByUuid(projectUuid, UUID.UUIDFromString(dataUuid));
		if (temp.getDataSource().equals(poolUuid)) {
			this.deleteById(projectUuid, UUID.UUIDFromString(dataUuid));
			return;
		}
		super.deleteById("data_task_" + projectUuid, DataTask.class, temp.getUuid());
		super.deleteById("data_item_" + temp.getDataSource(), DataItem.class, temp.getUuid());
		jdbcTemplate.update("insert into data_item_" + poolUuid + "(uuid,item_name,item_phone,item_address,item_json,data_from) values (?,?,?,?,?,?)", temp.getUid(), temp.getItemName(), temp.getItemPhone(), temp.getItemAddress(), temp.getItemJson(),temp.getItemOwner());
		synchronizedService.updateContainer(temp.getDataSource());
		synchronizedService.updateContainer(poolUuid);
		synchronizedService.updateProject(projectUuid);
	}

	@Override
	public List<String> getTasks(ICondition condition) {
		StringBuilder querySql = new StringBuilder("select t.uuid ");
		List<Object> params = new ArrayList<Object>();
		TaskSolution solution = new TaskSolution();
		solution.getWhere(condition, querySql, params);
		return jdbcTemplate.queryForList(querySql.toString(), params.toArray(), String.class); 
	}

}
