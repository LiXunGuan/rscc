package com.ruishengtech.rscc.crm.data.service;

import java.io.File;
import java.util.Collection;
import java.util.List;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.data.model.DataItem;

public interface DataItemService {

	public List<DataItem> getItems(String tableName);
	
	public List<String> getItems(ICondition condition);

	public DataItem getByUuid(String tableName, UUID uuid);

	public DataItem getByName(String tableName, String itemName);

	public boolean save(String tableName, DataItem d);

	public void save(String tableName, DataItem d, String[] excludeFieldName);

	public boolean deleteById(String tableName, UUID uuid);

	public int getCount(String tableName);
	
	public int getUnallocateCount(String tableName);

	public PageResult<DataItem> queryPage(ICondition condition);

	public int importExcelToTable(File excel, String tableName) throws Exception;
	
	public void importExcelToTable(String uuid, String filePath, String uploader, String tableName, Collection<String> relatedUsers);

	public void collection(String dataTable, Integer collection, String[] users);
	
	public void collection(String dataTable, Integer collection);
//	public int changeImportFlag(String tableName, String[] items);

	public boolean batDelete(String dataTable, String[] uuids);

}