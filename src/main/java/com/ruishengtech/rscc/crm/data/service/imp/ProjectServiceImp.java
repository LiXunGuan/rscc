package com.ruishengtech.rscc.crm.data.service.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.framework.core.db.service.TableService;
import com.ruishengtech.rscc.crm.data.condition.AllocateCondition;
import com.ruishengtech.rscc.crm.data.condition.ProjectCondition;
import com.ruishengtech.rscc.crm.data.model.DataContainer;
import com.ruishengtech.rscc.crm.data.model.DataTask;
import com.ruishengtech.rscc.crm.data.model.Project;
import com.ruishengtech.rscc.crm.data.model.ProjectTaskResultLink;
import com.ruishengtech.rscc.crm.data.service.DataContainerService;
import com.ruishengtech.rscc.crm.data.service.DataItemService;
import com.ruishengtech.rscc.crm.data.service.DataTaskService;
import com.ruishengtech.rscc.crm.data.service.ProjectService;
import com.ruishengtech.rscc.crm.data.service.SynchronizeService;
import com.ruishengtech.rscc.crm.data.solution.ProjectSolution;
import com.ruishengtech.rscc.crm.data.util.CollectionUtil;

@Service
@Transactional
public class ProjectServiceImp extends BaseService implements ProjectService {

	@Autowired
	private DataTaskService dataTaskService;
	
	@Autowired
	private DataContainerService dataContainerService;
	
	@Autowired
	private DataItemService dataItemService; 
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private SynchronizeService synchronizedService;
	
	public List<Project> getProjects() {
		List<Project> list = getBeanList(Project.class,"");
		return list;
	}

	public Project getByUuid(UUID uuid) {
		return super.getByUuid(Project.class, uuid);
	}
	
	public Project getProjectByName(String projectName) {
		
		List<Project> list = getBeanList(Project.class, "and project_name = ?", projectName);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public boolean save(Project p) {
		try {
			super.save(p);
			jdbcTemplate.update("UPDATE data_project SET uuid=? where project_info=? ", p.getProjectInfo(), p.getProjectInfo());
			tableService.createTable(DataTask.class, "data_task_" + p.getProjectInfo());
//			this.addTaskAndResult(p.getUuid().toString(), p.getUuid().toString(), new Date());
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public void save(Project p, String[] excludeFieldName) {
		super.save(p, excludeFieldName);
		tableService.createTable(DataTask.class, "data_task_" + p.getUid());
	}
	
	public boolean update(Project p) {
		try {
	        super.update(p,new String[]{"projectStat","dataCount","createDate"});
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public void update(Project p, String[] excludeFieldName) {
		 super.update(p, excludeFieldName);
	}
	
	public boolean deleteById(UUID uuid) {
		if(this.getByUuid(uuid)==null)
			return true;
		try {
//			List<ProjectTaskResultLink> l = this.getAllByUuid(uuid.toString());
//			for(ProjectTaskResultLink p:l)
//			this.deleteTaskAndResult(uuid.toString(), uuid.toString());
			this.revertData(uuid.toString(), 2);
			super.deleteById(Project.class, uuid);
			tableService.deleteTable("data_task_" + uuid.toString());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	//现在没用
	public List<ProjectTaskResultLink> getAll() {
		List<ProjectTaskResultLink> list = getBeanList(ProjectTaskResultLink.class, "");
		return list;
	}
	
	//现在没用
	public List<ProjectTaskResultLink> getAllByUuid(String uuid) {
		List<ProjectTaskResultLink> list = getBeanListWithOrder(ProjectTaskResultLink.class, "and project_uuid=?", " project_uuid asc ", uuid);
		return list;
	}
	
	//现在没用
	public List<String> getAllTask(String uuid) {
		List<String> list = new ArrayList<String>();
		List<ProjectTaskResultLink> l = getAllByUuid(uuid);
		for (ProjectTaskResultLink p : l) {
			list.add(p.getTaskTable());
		}
		return list;
	}
	
	@Override
	public PageResult<Project> queryPage(ICondition condition) {
		return super.queryPage(new ProjectSolution(), condition, Project.class);
	}
	
//	public boolean addTaskAndResult(String uuid, String tableName, Date createTime) {
//		try {
//			if(!StringUtils.isBlank(tableName)) {
//				taskDesignService.createTable(tableName);
//				resultDesignService.createTable(tableName);
////				jdbcTemplate.update("INSERT INTO project_task_result_link (project_uuid, task_table, result_table, create_time) VALUES (?, ?, ?, ?)", uuid, tableName, tableName, createTime);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return true;
//	}
	
//	public boolean addTaskAndResult(ProjectTaskResultLink p) {
//		return this.addTaskAndResult(p.getProjectUuid(), p.getTaskTable(), p.getCreateTime());
//	}
	
//	public boolean deleteTaskAndResult(String uuid, String tableName) {
//		try {
//			if(!StringUtils.isBlank(tableName)) {
//				taskDesignService.deleteTable(tableName);
//				resultDesignService.deleteTable(tableName);
////				jdbcTemplate.update("DELETE FROM project_task_result_link WHERE project_uuid=? and task_table=? ", uuid, tableName);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return true;
//	}
	
//	public boolean deleteTaskAndResult(ProjectTaskResultLink p) {
//		return this.deleteTaskAndResult(p.getProjectUuid(), p.getTaskTable());
//	}
	
	public int importToProject(String sourceTable, String projectUuid) {
		int count = dataTaskService.importToTask(sourceTable, projectUuid);
//		jdbcTemplate.update("insert into data_project_data_link (data_uuid, project_uuid) values (?,?)", sourceTable, projectUuid);
		return count;
	}

	@Override
	public int addUsers(String projectUuid, List<String> users) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String u:users)
			if(!StringUtils.isBlank(u))
				list.add(new Object[]{projectUuid,u});
		int[] nums = jdbcTemplate.batchUpdate("INSERT INTO data_project_user_link (project_uuid,user_uuid) VALUES (?, ?)", list);
		return CollectionUtil.sum(nums);
		/*String sql = "insert into data_project_user_link (user_uuid, project_uuid) values (?,?)";
		List<String> oldUsers = getUsers(projectUuid);
		List<Object[]> l = new ArrayList<Object[]>();
		for(String u:users)
			if(oldUsers == null || !oldUsers.contains(u))
				l.add(new Object[]{u,projectUuid});
		return CollectionUtil.sum(jdbcTemplate.batchUpdate(sql, l));*/
	}

	public int updateUsers(String projectUuid, String[] users){
		List<String> oldUsers = getUsers(projectUuid);
		List<String> deleteUsers = new ArrayList<String>(oldUsers);
		List<String> addUsers = new ArrayList<String>(users==null?new ArrayList<String>():Arrays.asList(users));
		deleteUsers.removeAll(addUsers);
		addUsers.removeAll(oldUsers);
		return deleteUsers(projectUuid, deleteUsers) + addUsers(projectUuid, addUsers);
	}

	
	@Override
	public int deleteUsers(String projectUuid, List<String> users) {
		List<Object[]> list = new ArrayList<Object[]>();
		for(String u:users)
			if(!StringUtils.isBlank(u)){
				jdbcTemplate.update("update data_task_" + projectUuid + " set item_owner = null, allocate_time = null, call_times = 0 where item_owner = ?", u);
				list.add(new Object[]{projectUuid,u});
			}
		int[] nums = jdbcTemplate.batchUpdate("DELETE FROM data_project_user_link WHERE project_uuid=? and user_uuid=?", list);
		//删除用户时，同时删除其所有任务（把任务归属人，分配时间，呼叫次数归零）
		return CollectionUtil.sum(nums);
	}
	
	@Override
	public List<String> getUsers(String projectUuid) {
		return jdbcTemplate.queryForList("select user_uuid from data_project_user_link where project_uuid = ? ", String.class, projectUuid);
	}
	
	public Map<String,String> getUserTasks() {
		Map<String,String> map = new HashMap<String,String>();
		List<Project> list = this.getProjects();
		for(Project l:list)
			map.put(l.getUid(), String.valueOf(dataTaskService.getUserUncompleteCount(l.getUid())));
		return map;
	}
	
	public Map<String,String> getUserStat() {
		Map<String,String> map = new HashMap<String,String>();
		List<Project> list = this.getProjects();
		for(Project l:list)
			map.put(l.getUid(), dataTaskService.getUserUncompleteCount(l.getUid()) + "/" + dataTaskService.getTaskCount(l.getUid()));
		return map;
	}
	
//	public Map<String,String> getUserTasks() {
//		Map<String,String> map = new HashMap<String,String>();
//		List<Project> list = this.getProjects();
//		for(Project l:list)
//			map.put(l.getProjectName(), String.valueOf(dataTaskService.getUserUncompleteCount(l.getUid())) + "/" + String.valueOf(dataTaskService.getUserTaskCount(l.getUid())) );
//		return map;
//	}
	
	@Override
	public int getUserCount(String projectUuid) {
		return jdbcTemplate.queryForObject("select count(user_uuid) from data_project_user_link where project_uuid = ? ", Integer.class, projectUuid);
	}
	
	
	/*项目内去重，去重后更新项目数据量*/
	public int distinct(String uuid) {
		int count = 0;
//		try {
//			StringBuilder distinctSql = new StringBuilder();
//			distinctSql.append("delete b from ").append("data_task_" + uuid).
//				append(" b ,(select *,max(item_owner) as id from ").append("data_task_" + uuid).
//				append(" group by item_phone having count(item_phone) > 1) as d where b.uuid>d.uuid and b.item_phone = d.item_phone");
//			count = getTemplate().update(distinctSql.toString());
////			d.setDistinctFlag("1");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		count = dataTaskService.distinct(uuid);
		this.update(uuid);
		return count;
	}

	public void update(String uuid) {
		StringBuilder updateSql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		updateSql.append("UPDATE data_project SET data_count=?,complete_count=? WHERE uuid=?");
		params.add(getTemplate().queryForObject("select count(*) from data_task_" + uuid, Integer.class));
		params.add(getTemplate().queryForObject("select count(*) from data_task_" + uuid + " where call_times > 0 ", Integer.class));
		params.add(uuid);
		getTemplate().update(updateSql.toString(), params.toArray());
	}
	
	/**
	 * 0平均分给所有人
	 * 1每人分多少条
	 * 2平均所有人现有任务
	 * 3分配至上限
	 */
	public void allocate(String projectUuid, int allocateType, int allocateMax){
		int totalTask = dataTaskService.getTaskCount(projectUuid);
		int unallocateTask = dataTaskService.getUnallocateTaskCount(projectUuid);
		int totalUser = getUserCount(projectUuid);
		List<String> users = getUsers(projectUuid);
		switch (allocateType) {
		case 0: {
			int average = (int) Math.ceil((double) unallocateTask
					/ (double) totalUser);
			for (String u : users)
				allocateToUser(projectUuid, u, average, 0);
			break;
		}
		case 1: {
			int average = allocateMax;
			for (String u : users)
				allocateToUser(projectUuid, u, average, 0);
			break;
		}
		case 2: {
			Map<String,Integer> map = getUserNum(projectUuid);
			int average = (int) Math.ceil((double) totalTask
					/ (double) totalUser);
			List<Integer> list = new ArrayList<Integer>(map.values());
			while(list.size() > 0 && Collections.max(list) > average) {
				for(int i = 0;i < list.size(); i++)
				{
					if(list.get(i)>average){
						totalTask-=list.remove(i--);
						totalUser--;
					}
				}
				average=(int) Math.ceil((double) totalTask
						/ (double) totalUser);
			}
			for (String u : users)
				allocateToUser(projectUuid, u, 0, average);
			break;
		}
		case 3: {
			for (String u : users)
				allocateToUser(projectUuid, u, 0, allocateMax);
			break;
		}
		}
		
	}
	
	/**
	 * 
	 * @param projectUuid 项目uuid
	 * @param userUuid 分配给谁
	 * @param allocateNum 分配多少
	 * @param allocateMax 分配上限，0为无限制，非0的时候allocateNum无效
	 */
	public void allocateToUser(String projectUuid, String userUuid, int allocateNum, int allocateMax){
		int taskCount = dataTaskService.getUserUncompleteCount(projectUuid);
		int allocateCount = allocateMax==0?allocateNum:allocateMax-taskCount;
		if(allocateCount>0){
			jdbcTemplate.update("update data_task_" + projectUuid + " set item_owner=?, allocate_time=? where item_owner is null limit " + allocateCount, userUuid, new Date());
		}
	}
	
	public Map<String,Integer> getUserNum(String projectUuid){
		List<Map<String,Object>> list = jdbcTemplate.queryForList("select item_owner user,count(item_owner) num from data_task_" + projectUuid + " where call_times is null or call_times = 0 group by item_owner");
		Map<String,Integer> map = new HashMap<String,Integer>();
		for(Map<String,Object> m:list) {
			if(Integer.valueOf(m.get("num").toString())!=0)
				map.put(m.get("user").toString(), Integer.valueOf(m.get("num").toString()));
		}
		return map;
	}
	
	public Map<String,Integer> getUserTotal(String projectUuid){
		List<Map<String,Object>> list = jdbcTemplate.queryForList("select item_owner user,count(item_owner) num from data_task_" + projectUuid + " group by item_owner");
		Map<String,Integer> map = new HashMap<String,Integer>();
		for(Map<String,Object> m:list) {
			if(Integer.valueOf(m.get("num").toString())!=0)
				map.put(m.get("user").toString(), Integer.valueOf(m.get("num").toString()));
		}
		return map;
	}

	@Override
	public void changeProjectStat(String projectUuid, String stat) {
		jdbcTemplate.update("update data_project set project_stat=? where uuid=?",stat, projectUuid);
	}

	@Override
	public boolean batDelete(String[] uuids) {
		for(String u:uuids)
			this.deleteById(UUID.UUIDFromString(u));
		return true;
	}

	@Override
	public void collection(String uuid, String[] users, int collectionType, boolean containAll, int collectionNum) {
		StringBuilder whereSql = new StringBuilder(" where item_owner = ? ");
		StringBuilder updateSql = new StringBuilder("update data_task_" + uuid + " set item_owner = null, allocate_time = null, call_times = 0 ");
		StringBuilder limitSql = new StringBuilder(" order by call_times asc ");
		List<Object[]> params = new ArrayList<Object[]>();
		if(!containAll)
			whereSql.append(" and (call_times = 0 or call_times is null)");
		switch (collectionType) {
		case 0: {
			for(String u:users)
				params.add(new Object[]{u});
			break;//不做限制，直接全部回收
		}
		case 1: { 
			//已选择人员每人回收？
			limitSql.append(" limit ? ");
			for(String u:users)
				params.add(new Object[]{u,collectionNum});
			break;
		}
		case 2: {
			//已选择人员每人回收至？
			limitSql.append(" limit ? ");
			Map<String,Integer> map = getUserNum(uuid);
			for(String u:users)
				params.add(new Object[]{u,map.get(u) - collectionNum});
			break;
		}
		}
		jdbcTemplate.batchUpdate(updateSql.append(whereSql).append(limitSql).toString(), params);
	}

	@Override
	public List<String> getAddedUsers() {
		return jdbcTemplate.queryForList("select project_info from data_project ", String.class);
	}
	
	
	
	/**
	 *
	 * 0平均分给所有人
	 * 1每人分多少条
	 * 2平均所有人现有任务
	 * 3分配至上限
	 * 这里map存储的键为用户名，衡量一下使用uuid存会不会好点
	 * @param dataTable 要分配数据的表
	 * @param users 分配给哪些用户
	 * @param dataType 分配方法，0为全部分配，1为有上限分配
	 * @param dataMax 分配数据的上限
	 * @param allocateType 分配类型，见上
	 * @param allocateMax 分配上限
	 * @param containAll 分配计算时，是否包含已完成数据
	 * 
	 * 可优化：从数据最少的人开始分配。bug：containAll对0,2模式不起作用
	 */
	public void allocate(AllocateCondition condition){
		//总数据，只用获取未分配的即可
		int totalData = dataItemService.getUnallocateCount(condition.getDataTable());
		//计算要分配出去的数据
		totalData = condition.getDataType()==0?totalData:condition.getDataMax()<totalData?condition.getDataMax():totalData;
		Map<String,Integer> map = getUserDataCount(condition.isContainAll(), condition.getUsers());
//		Map<String,Project> mapProject = getUserProjectMap();
		int totalUser = condition.getUsers().length;
		String[] users = condition.getUsers();
		switch (condition.getAllocate()) {
		case 0: {//平均分配给多人
			int average = (int) Math.ceil((double) totalData / (double) totalUser);
			for (String u : users) {
				allocateToUser(condition.getDataTable(), u, Math.min(average, totalData));
				totalData -= Math.min(average, totalData);
			}
			break;
		}
		case 1: {//每人分配多少条，为了分配均衡，每次分出去50条
			int i = 0;
			while(i < condition.getAllocateMax()){
				for (String u : users) {
					allocateToUser(condition.getDataTable(), u, Math.min(totalData, Math.min(condition.getAllocateMax()-i,50)));
					totalData -= Math.min(totalData, Math.min(condition.getAllocateMax()-i,50));
				}
				i+=50;
			}
			break;
		}
		case 2: {//动态分配，需要先获取所有人的数量
			int total = CollectionUtil.sum(map.values()) + totalData;
			int average = (int) Math.ceil((double) total / (double) totalUser);
			List<Integer> list = new ArrayList<Integer>(map.values());
			while (list.size() > 0 && Collections.max(list) > average) {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i) > average) {
						total -= list.remove(i--);
						totalUser--;
					}
				}
				average = (int) Math.ceil((double) total / (double) totalUser);
			}
			for (String u : users) {
				allocateToUser(condition.getDataTable(), u, Math.min(average - map.get(u), totalData));
				totalData -= Math.max(0, average - map.get(u));
			}
			break;
		}
		case 3: {//每人分配到上限，同样需要动态化分配
			int i = 0;
			while(i < condition.getAllocateMax()){//这里偷懒，不再计算最大值，就用比最大值大的数，到时候里面判断小于0跳出
				for (String u : users) {
					allocateToUser(condition.getDataTable(), u, Math.min(totalData, Math.min(condition.getAllocateMax()-map.get(u)-i,50)));
					totalData -= Math.min(totalData, Math.min(condition.getAllocateMax()-map.get(u)-i,50));
				}
				i+=50;
			}
			break;
		}
		}
		synchronizedService.updateContainer(condition.getDataTable());
		for (String u : users)
			synchronizedService.updateProject(u);
	}
	
//	public void allocate(String dataTable,String[] users, int dataType, int dataMax, int allocateType, int allocateMax, boolean containAll){
//		int totalData = dataItemService.getCount(dataTable);
//		//计算要分配出去的数据
//		totalData = dataType==0?totalData:dataMax<totalData?dataMax:totalData;
//		Map<String,Integer> map = getUserDataCount(containAll, users);
//		Map<String,Project> mapProject = getUserProjectMap();
//		int totalUser = users.length;
//		switch (allocateType) {
//		case 0: {//平均分配给多人
//			int average = (int) Math.ceil((double) totalData / (double) totalUser);
//			for (String u : users) {
//				allocateToUser(dataTable, mapProject.get(u).getProjectInfo(), average < totalData ? average : totalData);
//				totalData -= average;
//			}
//			break;
//		}
//		case 1: {//每人分配多少条，为了分配均衡，每次分出去50条
//			int i = 0;
//			while(i < allocateMax){
//				for (String u : users)
//					allocateToUser(dataTable, mapProject.get(u).getProjectInfo(), (allocateMax-i)<50?allocateMax-i:50);
//				i+=50;
//			}
//			break;
//		}
//		case 2: {//动态分配，需要先获取所有人的数量
//			int total = CollectionUtil.sum(map.values()) + totalData;
//			int average = (int) Math.ceil((double) total / (double) totalUser);
//			List<Integer> list = new ArrayList<Integer>(map.values());
//			while (list.size() > 0 && Collections.max(list) > average) {
//				for (int i = 0; i < list.size(); i++) {
//					if (list.get(i) > average) {
//						total -= list.remove(i--);
//						totalUser--;
//					}
//				}
//				average = (int) Math.ceil((double) total / (double) totalUser);
//			}
//			for (String u : users)
//				allocateToUser(dataTable, mapProject.get(u).getProjectInfo(), average);
//			break;
//		}
//		case 3: {//每人分配到上限，同样需要动态化分配
//			int i = 0;
//			while(i < allocateMax){//这里偷懒，不再计算最大值，就用比最大值大的数，到时候里面判断小于0跳出
//				for (String u : users)
//					allocateToUser(dataTable, mapProject.get(u).getProjectInfo(), (allocateMax-map.get(u)-i)<50?allocateMax-map.get(u)-i:50);
//				i+=50;
//			}
//			break;
//		}
//		}
//		dataContainerService.update(dataTable);
//		for (String u : users)
//			update(mapProject.get(u).getProjectInfo());
//	}
//	
	
	//这里看，还是要用用户uuid命名表名最好(用相同的了。。。)
	//导入项目，删除源，同时更新数据量
	//注意两个语句执行如果不同步的话可能会出错，想一下怎么解决
	public int allocateToUser(String sourceTable, String targetTable, int allocateNum) {
		if(allocateNum <= 0)
			return 0;
		String updateSql = "update data_item_" + sourceTable + " set item_owner = ?,allocate_time = NOW(),stat_flag = 1 where item_owner is null limit ?";
		String insertSql = "insert into data_task_" + targetTable + "(uuid,item_name,item_phone,item_address,item_json,item_owner,call_times,allocate_time,data_source) (select uuid,item_name,item_phone,item_address,item_json,item_owner,call_times,allocate_time, '" + sourceTable + "' data_source from data_item_" + sourceTable + " where item_owner = ? and stat_flag = 1 limit ? )";
//		for(int i=0;i<count;i+=allocateNum){
		String changeStat = "update data_item_" + sourceTable + " set stat_flag = 0 where stat_flag = 1 and item_owner = ? limit ?";
		jdbcTemplate.update(updateSql, targetTable, allocateNum);
		int num = jdbcTemplate.update(insertSql, targetTable, allocateNum);
		jdbcTemplate.update(changeStat, targetTable, allocateNum);
		return num;
//		}
	}
	
	public Map<String,Project> getUserProjectMap(){
		Map<String,Project> map = new HashMap<String,Project>();
		List<Project> list = getProjects();
		for(Project p:list)
			map.put(p.getUid(), p);
		return map;
	}
	
	public Map<String,Integer> getUserDataCount(boolean containAll){
		Map<String,Integer> map = new HashMap<String,Integer>();
		Map<String,Project> bigMap = getUserProjectMap();
		for(Entry<String,Project> e: bigMap.entrySet()){
			map.put(e.getKey(),containAll?e.getValue().getDataCount():e.getValue().getDataCount()-e.getValue().getUserCount());
		}
		return map;
	}
	
	public Map<String,Integer> getUserDataCount(boolean containAll, String[] users){
		Map<String,Integer> map = new HashMap<String,Integer>();
		Map<String,Project> bigMap = getUserProjectMap();
		List<String> list = Arrays.asList(users);
		for(Entry<String,Project> e: bigMap.entrySet()){
			if(list.contains(e.getKey()))
				map.put(e.getKey(),containAll?e.getValue().getDataCount():e.getValue().getDataCount()-e.getValue().getUserCount());
		}
		return map;
	}

	@Override
	public void collection(String dataTable, Integer collection, String[] users) {
	}

	@Override
	public int getData(String uuid, Integer getData, Integer allocateMax, String[] datas) {
		Project current = getByUuid(UUID.UUIDFromString(uuid));
		if(getData == 1)
			allocateMax -= current.getDataCount() - current.getUserCount();
		int startNum = allocateMax;
		int i = 0;
		while(i<datas.length && allocateMax > 0)
			allocateMax -= allocateToUser(datas[i++], uuid, allocateMax);
		synchronizedService.updateProject(uuid);
		for (String u : datas)
			synchronizedService.updateContainer(u);
		return startNum - allocateMax;
	}

	@Override
	public Map<String, DataContainer> getDataAllocate(String userUuid) {
		Map<String, DataContainer> map = new HashMap<String, DataContainer>();
		List<Map<String,Object>> list = jdbcTemplate.queryForList("SELECT a.data_source,b.container_name,COUNT(a.uuid) dataCount,SUM(CASE WHEN not a.call_times > 0 THEN 1 ELSE 0 END) uncomplete FROM data_task_" + userUuid + " a LEFT JOIN data_container b ON a.data_source = b.data_table GROUP BY a.data_source;");
		for(Map<String, Object> m:list) {
			DataContainer temp = new DataContainer();
			temp.setDataTable(m.get("data_source").toString());
			temp.setContainerName(m.get("container_name").toString());
			temp.setDataCount(Integer.valueOf(m.get("dataCount").toString()));
			temp.setAllocateCount(Integer.valueOf(m.get("uncomplete").toString()));
			map.put(temp.getDataTable(), temp);
		}
		return map;
	}
	
	public List<String> getDataSources(String userUuid) {
		return jdbcTemplate.queryForList("SELECT data_source from data_task_" + userUuid + " group by data_source", String.class);
	}

	@Override
	public void revertData(String uuid, Integer revertData, String[] datas) {
		StringBuilder whereSql = new StringBuilder(" where item_owner = ? ");
		if (revertData == 0)
			whereSql.append(" and call_times = 0 ");
		else if (revertData == 1)
			whereSql.append(" and call_times > 0 ");
		for (String d:datas) {
			jdbcTemplate.update("update data_item_" + d + " set item_owner = null, call_times = 0, allocate_time = null " + whereSql.toString(), uuid);
			jdbcTemplate.update("delete from data_task_" + uuid + " " + whereSql.toString() + " and data_source = ? ", uuid, d);
		}
		synchronizedService.updateProject(uuid);
		for (String u : datas)
			synchronizedService.updateContainer(u);
	}
	
	public void revertData(String uuid, Integer revertData) {
		List<String> list = jdbcTemplate.queryForList("select data_source from data_task_" + uuid + " group by data_source", String.class);
		StringBuilder whereSql = new StringBuilder(" where item_owner = ? ");
		if (revertData == 0)
			whereSql.append(" and call_times = 0 ");
		else if (revertData == 1)
			whereSql.append(" and call_times > 0 ");
		for (String u : list)
			jdbcTemplate.update("update data_item_" + u + " set item_owner = null, call_times = 0, allocate_time = null " + whereSql.toString(), uuid);
//		jdbcTemplate.update("delete from data_task_" + uuid + " " + whereSql.toString(), uuid);
//		synchronizedService.updateProject(uuid);
		for (String u : list)
			synchronizedService.updateContainer(u);
	}
	
	public void batRevert(String[] users){
		for(String u:users)
			this.deleteById(UUID.UUIDFromString(u));
	}

	@Override
	public List<String> getProjects(ProjectCondition condition) {
		StringBuilder querySql = new StringBuilder("select t.uuid ");
		List<Object> params = new ArrayList<Object>();
		ProjectSolution solution = new ProjectSolution();
		solution.getWhere(condition, querySql, params);
		return jdbcTemplate.queryForList(querySql.toString(), params.toArray(), String.class); 
	}
	
}
