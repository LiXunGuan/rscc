package com.ruishengtech.rscc.crm.data.service;

import java.util.List;
import java.util.Map;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.data.condition.AllocateCondition;
import com.ruishengtech.rscc.crm.data.condition.ProjectCondition;
import com.ruishengtech.rscc.crm.data.model.DataContainer;
import com.ruishengtech.rscc.crm.data.model.Project;
import com.ruishengtech.rscc.crm.data.model.ProjectTaskResultLink;

public interface ProjectService {

	public List<Project> getProjects();

	public List<String> getProjects(ProjectCondition condition);
	
	public Project getByUuid(UUID uuid);

	public Project getProjectByName(String projectName);

	public boolean save(Project p);

	public void save(Project p, String[] excludeFieldName);

	public boolean update(Project p);

	public void update(Project p, String[] excludeFieldName);

	public boolean deleteById(UUID uuid);

	public List<ProjectTaskResultLink> getAll();

	public List<ProjectTaskResultLink> getAllByUuid(String uuid);

	public List<String> getAllTask(String uuid);

	PageResult<Project> queryPage(ICondition condition);
	
	public int addUsers(String projectUuid, List<String> users);

	public int importToProject(String sourceTable, String projectUuid);

	public int distinct(String uid);

	List<String> getUsers(String projectUuid);
	
	public Map<String, String> getUserTasks();

	public int updateUsers(String projectUuid, String[] users);
	
	int deleteUsers(String projectUuid, List<String> users);

	public int getUserCount(String projectUuid);
	
	public void allocate(String projectUuid, int allocateType, int allocateMax);
	
	public void changeProjectStat(String projectUuid, String stat);

//	List<String> getDatas(String projectUuid);

	public boolean batDelete(String[] uuids);

	public void collection(String uuid, String[] users, int collection, boolean containAll, int i);

	public List<String> getAddedUsers();
	
	public void allocate(AllocateCondition condition);

	public void collection(String dataTable, Integer collection, String[] users);

	public int getData(String uuid, Integer getData, Integer allocateMax, String[] datas);
	
	public Map<String, DataContainer> getDataAllocate(String userUuid);

	public void revertData(String uuid, Integer revertData, String[] datas);

	public void batRevert(String[] users);
	
	public List<String> getDataSources(String userUuid);
	
}