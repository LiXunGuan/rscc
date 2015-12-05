package com.ruishengtech.rscc.crm.data.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;

public interface ResultService {

	public void deleteByUuid(String tableName, String id);

	public void save(String tableName, Map<String, String[]> str);

	public void update(String tableName, Map<String, String[]> str);

//	public boolean importToResult(String sourceTable, String targetTable, String[] items);
	
	public int importToResult(String sourceTable, String targetTable);

	public void deleteById(String tableName, String id);

	public PageResult<Map<String, Object>> queryPage(Map<String, ColumnDesign> str, HttpServletRequest request);

	public JSONObject getJsonObject(Map<String, Object> str);
	
	public Map<String, Object> getResultById(String tableName, UUID uuid);
	
	public List<String> getAllResult(String tableName);
	
	public int getResultCount(String tableName);

	public int distinct(String uuid);
	
}