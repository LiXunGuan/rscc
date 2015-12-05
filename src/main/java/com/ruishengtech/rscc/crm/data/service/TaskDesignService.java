package com.ruishengtech.rscc.crm.data.service;

import java.util.Map;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.rscc.crm.data.condition.TaskDesignCondition;

public interface TaskDesignService {

	public void save(ColumnDesign cd) throws Exception;

	public void deleteByUuid(UUID uuid);
	
	public void save(String tableName, Map<String, String[]> str);
	
	public void update(String tableName, Map<String, String[]> str);

	public void createTable(String tableName);

	public void deleteTable(String tableName);

	public PageResult<ColumnDesign> queryPage(TaskDesignCondition condition);
	
	public void alterAdd(ColumnDesign customerDesign) throws Exception;
	
	public String getColumnName();

	public void deleteColumn(String id, String tableName);
	
	public Map<String,ColumnDesign> getTableDef(String tableName);
	
	public Map<String,String> getColumns();
	
	public Map<String,String> getSelects();
	
}