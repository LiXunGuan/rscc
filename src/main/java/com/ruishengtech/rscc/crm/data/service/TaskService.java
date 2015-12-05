package com.ruishengtech.rscc.crm.data.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;

public interface TaskService {
	
	public void deleteById(String tableName, String id);
	
	public void save(String tableName, Map<String, String[]> str);
	
	public void update(String tableName, Map<String, String[]> str);
	
//	public boolean importToTask(String sourceTable, String targetTable, String[] items);
	
	public int importToTask(String sourceTable, String targetTable);

/*	public boolean addTaskAndResult(String uuid, String tableName, Date createTime);

	public boolean addTaskAndResult(ProjectTaskResultLink p);

	public boolean deleteTaskAndResult(String uuid, String tableName);

	public boolean deleteTaskAndResult(ProjectTaskResultLink p);*/

	public PageResult<Map<String, Object>> queryPage(Map<String, ColumnDesign> str, HttpServletRequest request);

	public JSONObject getJsonObject(Map<String, Object> str);
	
	public Map<String, Object> getTaskById(String tableName, UUID uuid);
	
	public List<String> getAllTask(String tableName);
	
	public int getTaskCount(String tableName);
	
	public int distinct(String uuid);
	
	public int getUnallocateTaskCount(String projectuUuid);
	
	public int getUserTaskCount(String projectuUuid, String userUuid);
	
	public int getUserUncompleteCount(String projectuUuid, String userUuid);

	public int getCompleteTaskCount(String projectuUuid);
	
}