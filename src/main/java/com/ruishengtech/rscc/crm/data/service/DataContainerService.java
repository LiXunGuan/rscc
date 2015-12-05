package com.ruishengtech.rscc.crm.data.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.data.model.DataContainer;
import com.ruishengtech.rscc.crm.data.model.Project;

public interface DataContainerService {

	public List<DataContainer> getDatas();
	
	//0为批次，1为池
	public List<DataContainer> getDatas(String containerType);

	public List<DataContainer> getDatas(Collection<String> uuids);
	
	public List<DataContainer> getDatas(String containerType, Collection<String> uuids);
	
	public List<String> getDatas(ICondition Condition);

	public DataContainer getByUuid(UUID uuid);

	public DataContainer getDataContainerByName(String dataName);

	public DataContainer getDataContainerByTable(String dataTable);

	public boolean save(DataContainer d);

	public boolean save(DataContainer d, String[] excludeFieldName);

	public boolean update(DataContainer d);

	public void update(DataContainer d, String[] excludeFieldName);

	public void update(String tableName);

	public boolean deleteById(UUID uuid);
	
	public boolean deleteById(UUID uuid, String operator);

	public boolean deleteByTable(String dataTable);

	//批次内去重
	public int distinct(DataContainer d);
	
	public int distinct(DataContainer d, List<String> phones);

	public PageResult<DataContainer> queryPage(ICondition condition);

	public boolean batDelete(String[] uuids);
	
	public boolean batDelete(String[] uuids, String operator);

	public int batSave(String[] containerName, String[] dataInfo);
	
	public Map<String, Project> getUserAllocate(String containerName);

	public void move(String uuid, Integer moveData, String targetPool);
	
	public boolean deleteByIdOnlyWithTable(UUID uuid);
	
	public JSONArray getDataContainerTree();

	public void getBack(String dataTable, String dataId, String userId);

	public void updateName(String uuid, String containerName, String dataInfo, String operater);
	
	public DataContainer getDataContainerByContainerName(String containerName,String container_type);

	public int batSave(String containerName, String dataInfo, String creator, String[] departments);
	
	//获得一个容器和哪些部门有关联
	public List<String> getDataContainerDepartments(String uuid);
	
	//获取一个部门关联的所有容器
	public List<String> getDepartmentDataContainers(String uuid);

	public int bindDepartment(String uuid, String[] departments);
	
	public int unbindDepartment(String uuid, String[] departments);
	
	//根据多个部门查到这些部门有哪些关联的数据容器
	public Set<String> getDepartmentsDataContainers(Collection<String> uuids);
	
	public boolean createLog(DataContainer d);
	
}