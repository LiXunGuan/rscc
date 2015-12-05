package com.ruishengtech.rscc.crm.data.service;

import java.util.List;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.data.model.DataPool;

public interface DataPoolService {

	public List<DataPool> getAllTaskPool();

	public boolean save(DataPool d);

	public void save(DataPool d, String[] excludeFieldName);

	public boolean update(DataPool d);

	public void update(DataPool d, String[] excludeFieldName);

	public boolean deleteById(UUID uuid);

	public DataPool getByUuid(UUID uuid);

	public PageResult<DataPool> queryPage(ICondition condition);

	public DataPool getTaskPoolByName(String datarangeName);

	public List<DataPool> getByList(List<UUID> l);

	public boolean batDelete(String[] uuids);

	public int batSave(String[] poolName, String[] poolDescribe);

}