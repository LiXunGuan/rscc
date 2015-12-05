package com.ruishengtech.rscc.crm.data.service.imp;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.rscc.crm.data.service.ResultDesignService;
import com.ruishengtech.rscc.crm.data.service.TaskDesignService;
import com.ruishengtech.rscc.crm.data.service.TaskService;
import com.ruishengtech.rscc.crm.data.solution.OldTaskSolution;

@Service
@Transactional
public class TaskServiceImp extends DiyTableService implements TaskService {
	
	@Autowired
	private TaskDesignService taskDesignService;
	
	@Autowired
	private ResultDesignService resultDesignService;
	
	public void deleteById(String tableName, String uuid) {
		super.deleteById("data_task_" + tableName, null, UUID.UUIDFromString(uuid));
	}
	
	public void save(String tableName, Map<String, String[]> str) {
		taskDesignService.save(tableName, str);
	}
	
	public void update(String tableName, Map<String, String[]> str) {
		taskDesignService.update(tableName, str);
	}
	
	public int importToTask(String sourceTable, String targetTable) {
		String sql = "insert into data_task_" + targetTable + "(uuid,item_name,item_phone,item_describe,item_address,item_stat) (select * from data_item_" + sourceTable + ")";
		return jdbcTemplate.update(sql);
	}
	
/*	public boolean addTaskAndResult(String uuid, String tableName, Date createTime) {
		try {
			if(!StringUtils.isBlank(tableName)) {
				taskDesignService.createTable(tableName);
				resultDesignService.createTable(tableName);
				jdbcTemplate.update("INSERT INTO project_task_result_link (project_uuid, task_table, result_table, create_time) VALUES (?, ?, ?, ?)", uuid, tableName, tableName, createTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean addTaskAndResult(ProjectTaskResultLink p) {
		return this.addTaskAndResult(p.getProjectUuid(), p.getTaskTable(), p.getCreateTime());
	}
	
	public boolean deleteTaskAndResult(String uuid, String tableName) {
		try {
			if(!StringUtils.isBlank(tableName)) {
				taskDesignService.deleteTable(tableName);
				resultDesignService.deleteTable(tableName);
				jdbcTemplate.update("DELETE FROM project_task_result_link WHERE project_uuid=? and task_table=? ", uuid, tableName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean deleteTaskAndResult(ProjectTaskResultLink p) {
		return this.deleteTaskAndResult(p.getProjectUuid(), p.getTaskTable());
	}*/

	@Override
	public PageResult<Map<String, Object>> queryPage(Map<String, ColumnDesign> str,
			HttpServletRequest request) {
		return queryPage(new OldTaskSolution(), str,request);
	}
	
	public JSONObject getJsonObject(Map<String, Object> str){

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		JSONObject jsonObject2 = new JSONObject();
		Map<String,ColumnDesign> ret = taskDesignService.getTableDef("task");
		
		for (ColumnDesign columnDesign : ret.values()) {
			if(ColumnDesign.ALLOWSHOW.equals(columnDesign.getAllowShow())){//如果是要显示的
				
				if(columnDesign.getColumnType().equals(ColumnDesign.DATETYPE)){//如果是时间类型 格式化时间
					try {
						jsonObject2.put(columnDesign.getColumnNameDB(),fmt.format(str.get(columnDesign.getColumnNameDB())));
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}else{
					jsonObject2.put(columnDesign.getColumnNameDB(),str.get(columnDesign.getColumnNameDB()));
				}
			}
		}
		
		jsonObject2.put("uid",str.get("uuid"));
        
        return jsonObject2;
	}

	@Override
	public Map<String, Object> getTaskById(String tableName, UUID uuid) {
		return jdbcTemplate.queryForMap(" SELECT * FROM data_task_" + tableName + " WHERE uuid = ? ",uuid.toString());
	}
	
	public List<String> getAllTask(String tableName) {
		return jdbcTemplate.queryForList(" SELECT uuid FROM data_task_" + tableName, String.class);
	}
	
	public int distinct(String uuid) {
		int count = 0;
		try {
			StringBuilder distinctSql = new StringBuilder();
			distinctSql.append("delete b from ").append("data_task_" + uuid).
				append(" b ,(select *,max(item_owner) as id from ").append("data_task_" + uuid).
				append(" group by item_phone having count(item_phone) > 1) as d where b.uuid>d.uuid and b.item_phone = d.item_phone");
			count = getTemplate().update(distinctSql.toString());
//			d.setDistinctFlag("1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 获取任务总数
	 */
	public int getTaskCount(String tableName) {
		return jdbcTemplate.queryForObject(" SELECT count(*) FROM data_task_" + tableName, Integer.class);
	}
	
	/**
	 * 获取完成任务数
	 */
	@Override
	public int getCompleteTaskCount(String projectuUuid) {
		return jdbcTemplate.queryForObject(" SELECT count(*) FROM data_task_" + projectuUuid + " where task_pool is not null ", Integer.class);
	}
	
	/**
	 * 获取未分配任务数
	 */
	@Override
	public int getUnallocateTaskCount(String projectuUuid) {
		return jdbcTemplate.queryForObject(" SELECT count(*) FROM data_task_" + projectuUuid + " where item_owner is null ", Integer.class);
	}
	
	/**
	 * 获取某用户任务总数
	 */
	public int getUserTaskCount(String projectuUuid, String userUuid) {
		return jdbcTemplate.queryForObject(" SELECT count(*) FROM data_task_" + projectuUuid + " where item_owner = ? ", Integer.class, userUuid);
	}
	
	/**
	 * 获取某用户未完成任务数
	 */
	@Override
	public int getUserUncompleteCount(String projectuUuid, String userUuid) {
		return jdbcTemplate.queryForObject(" SELECT count(*) FROM data_task_" + projectuUuid + " where item_owner = ? and task_pool is null", Integer.class, userUuid);
	}
	
}
