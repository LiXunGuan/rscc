package com.ruishengtech.rscc.crm.data.service;

import java.util.List;
import java.util.Map;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.data.model.DataItem;
import com.ruishengtech.rscc.crm.data.model.DataTask;

public interface DataTaskService {

	public List<DataTask> getTasks(String tableName);
	
	public List<String> getTasks(ICondition condition);

	public DataTask getByUuid(String tableName, UUID uuid);

	public DataTask getByName(String tableName, String itemName);
	
	public List<DataTask> getByPhone(String tableName, String Phone);

	//应该用不到，数据是直接插进来的
	public boolean save(String tableName, DataTask d);

	//同上
	public void save(String tableName, DataItem d, String[] excludeFieldName);

	public boolean deleteById(String tableName, UUID uuid);

	public boolean batDelete(String dataTable, String[] uuids);

	public PageResult<DataTask> queryPage(ICondition condition);

	//导入项目，删除源，同时更新数据量
	public int importToTask(String sourceTable, String targetTable);

	//项目内去重
	public int distinct(String uuid);

	public int addCallTimes(String tableName, String taskUuid);

	/**
	 * 获取任务总数
	 */
	public int getTaskCount(String tableName);

	/**
	 * 获取未分配任务数
	 */
	public int getUnallocateTaskCount(String projectuUuid);

	/**
	 * 获取完成任务数
	 */
	public int getCompleteTaskCount(String projectuUuid);

	/**
	 * 获取某用户任务总数
	 */
	public int getUserTaskCount(String projectuUuid);

	/**
	 * 获取某用户未完成任务数
	 */
	public int getUserUncompleteCount(String projectUuid);

	public List<String> getDataSources(String taskTable);

	public Map<String, String> getDataSourceName(String taskTable);
	
	public void moveTo(String projectUuid, String dataUuid, String poolUuid);
	
}