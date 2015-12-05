package com.ruishengtech.rscc.crm.data.service;

import java.util.List;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.data.model.DataLog;

public interface DataLogService {

	public List<DataLog> getDatas();

	public DataLog getByUuid(UUID uuid);

	public DataLog getDataLogByName(String dataName);

	public DataLog getDataLogByTable(String dataTable);

	public boolean save(DataLog d);

	public boolean save(DataLog d, String[] excludeFieldName);

	public boolean update(DataLog d);

	public void update(DataLog d, String[] excludeFieldName);

	public void update(String tableName);

	public boolean deleteById(UUID uuid);

	public boolean deleteByTable(String dataTable);

	//批次内去重
	public boolean distinct(DataLog d);

	public PageResult<DataLog> queryPage(ICondition condition);

	public void updateImportFlag(String dataTable);

	public boolean batDelete(String[] uuids);
	
//	public void updateImportCount(String dataName, int count);

}