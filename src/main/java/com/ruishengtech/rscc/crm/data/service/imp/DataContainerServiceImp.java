package com.ruishengtech.rscc.crm.data.service.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.framework.core.db.service.TableService;
import com.ruishengtech.framework.core.util.CollectionUtil;
import com.ruishengtech.rscc.crm.data.model.DataContainer;
import com.ruishengtech.rscc.crm.data.model.DataContainerLog;
import com.ruishengtech.rscc.crm.data.model.DataItem;
import com.ruishengtech.rscc.crm.data.model.Project;
import com.ruishengtech.rscc.crm.data.service.DataContainerService;
import com.ruishengtech.rscc.crm.data.service.DataItemService;
import com.ruishengtech.rscc.crm.data.service.SynchronizeService;
import com.ruishengtech.rscc.crm.data.solution.DataContainerSolution;

@Service
@Transactional
public class DataContainerServiceImp extends BaseService implements DataContainerService {
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private SynchronizeService synchronizedService;
	
	@Autowired
	private DataItemService dataItemService;

	@Autowired
	private DataContainerLogServiceImp dataContainerLogService;
	
	public List<DataContainer> getDatas() {
		List<DataContainer> list = getBeanList(DataContainer.class,"");
		return list;
	}
	
	public List<DataContainer> getDatas(String containerType) {
		List<DataContainer> list = getBeanList(DataContainer.class," and container_type=? ", containerType);
		return list;
	}

	public DataContainer getByUuid(UUID uuid) {
		return super.getByUuid(DataContainer.class, uuid);
	}
	
	public DataContainer getDataContainerByName(String dataName) {
		
		List<DataContainer> list = getBeanList(DataContainer.class, "and data_table = ?", dataName);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	public DataContainer getDataContainerByContainerName(String containerName,String container_type){
		List<DataContainer> list = getBeanList(DataContainer.class, "and container_name = ? and container_type = ?", containerName,container_type);
		if (list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public DataContainer getDataContainerByTable(String dataTable) {
		
		List<DataContainer> list = getBeanList(DataContainer.class, "and data_table = ?", dataTable);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public boolean save(DataContainer d) {
		super.save(d, new String[]{"dataCount","dataCreateTime","allocateCount"});
		return true;
	}
	
	public boolean save(DataContainer d, String[] excludeFieldName) {
		try {
			super.save(d, excludeFieldName);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean update(DataContainer d) {
        super.update(d, new String[]{"containerName","containerType","dataCreateTime"});
		tableService.createTable(DataItem.class, "data_item_" + d.getDataTable());
		return true;
	}
	
	public void update(DataContainer d, String[] excludeFieldName) {
		tableService.createTable(DataItem.class, "data_item_" + d.getDataTable());
		super.update(d, excludeFieldName);
	}
	
	public void update(String tableName) {
		StringBuilder updateSql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		updateSql.append("UPDATE data_container SET data_count=?,allocate_count=? WHERE data_table=?");
		params.add(getTemplate().queryForObject("select count(*) from data_item_" + tableName, Integer.class));
		params.add(getTemplate().queryForObject("select count(*) from data_item_" + tableName + " where item_owner is not null ", Integer.class));
		params.add(tableName);
		getTemplate().update(updateSql.toString(), params.toArray());
	}
	
	public boolean createLog(DataContainer d) {
		DataContainerLog dl = new DataContainerLog();
		dl.setContainerUuid(d.getUid());
		dl.setContainerName(d.getContainerName());
		dl.setDataTable(d.getDataTable());
		dl.setFilePath(d.getFilePath());
		dl.setOperate(DataContainerLog.OPERATE_CREATE);
		dl.setOperater(d.getUploadUser());
		dataContainerLogService.save(dl);
		return true;
	}
	
	/*public void update(String tableName, String distinctFlag) {
		StringBuilder updateSql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		updateSql.append("UPDATE data_log SET data_count=? , distinct_flag=? WHERE data_table=?");
		params.add(dataItemService.getCount(tableName));
		params.add(distinctFlag);
		params.add(tableName);
	}
	
	public void update(String tableName, String importFlag) {
		StringBuilder updateSql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		updateSql.append("UPDATE data_log SET data_count=? , import_flag=? WHERE data_table=?");
		params.add(dataItemService.getCount(tableName));
		params.add(importFlag);
		params.add(tableName);
	}*/
	
	/*public void updateImportCount(String dataName, int count) {
		StringBuilder updateSql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		updateSql.append("UPDATE data_log SET import_count=? WHERE data_table=?");
		params.add(count);
		params.add(dataName);
		getTemplate().update(updateSql.toString(), params.toArray());
	}*/
	
	public boolean deleteById(UUID uuid) {
		DataContainer temp = getByUuid(uuid);
		super.deleteById(DataContainer.class, uuid);
//			getUserAllocate(uuid.toString());
		dataItemService.collection(temp.getDataTable(), 2);				
		tableService.deleteTable("data_item_" + temp.getDataTable());
		unbindAllDepartment(uuid.toString());
		return true;
	}
	
	public boolean deleteById(UUID uuid, String operator) {
		DataContainer temp = getByUuid(uuid);
		super.deleteById(DataContainer.class, uuid);
		dataItemService.collection(temp.getDataTable(), 2);	
		tableService.deleteTable("data_item_" + temp.getDataTable());
		unbindAllDepartment(uuid.toString());
		
		DataContainerLog dl = new DataContainerLog();
		dl.setContainerUuid(temp.getUid());
		dl.setContainerName(temp.getContainerName());
		dl.setDataTable(temp.getDataTable());
		dl.setFilePath(temp.getFilePath());
		dl.setOperate(DataContainerLog.OPERATE_DELETE);
		dl.setOperater(operator);
		dataContainerLogService.save(dl);
		
		return true;
	}
	
	public boolean deleteByIdOnlyWithTable(UUID uuid) {
		DataContainer temp = getByUuid(uuid);
		super.deleteById(DataContainer.class, uuid);	
		tableService.deleteTable("data_item_" + temp.getDataTable());
		unbindAllDepartment(uuid.toString());
		return true;
	}
	
	public boolean deleteByTable(String dataTable) {
		StringBuilder deleteSql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		deleteSql.append(" DELETE FROM data_container");
		deleteSql.append(" where data_table=? ");
		params.add(dataTable);
		getTemplate().update(deleteSql.toString(), params.toArray());
		tableService.deleteTable(dataTable);
		return true;
	}

	//批次内去重，只对未分配的去重
	public int distinct(DataContainer d) {
		int count = 0;
		StringBuilder distinctSql = new StringBuilder();
		distinctSql.append("delete b from ").append("data_item_" + d.getDataTable()).
			append(" b ,(select *,min(uuid) as id from ").append("data_item_" + d.getDataTable()).
			append(" group by item_phone having count(item_phone) > 1) as d where b.uuid>d.id and b.item_phone = d.item_phone and b.item_owner is null");
		count = getTemplate().update(distinctSql.toString());
		d.setDistinctFlag("1");
		d.setDataCount(d.getDataCount() - count);
		this.update(d);
		return count;
	}
	
	//批次内去重，只对未分配的去重
	public int distinct(DataContainer d, List<String> phones) {
		int count = 0;
		if(phones == null || phones.size() == 0)
			return count;
		StringBuilder distinctSql = new StringBuilder();
		String[] param = new String[phones.size()];
		Arrays.fill(param, "?");
		String inSql = Arrays.toString(param);
		distinctSql.append("delete b from ").append("data_item_" + d.getDataTable()).
			append(" b where b.item_phone in ( ").append(inSql.substring(1, inSql.length()-1)).append(") and b.item_owner is null ");
		count = getTemplate().update(distinctSql.toString(), phones.toArray());
		d.setDistinctFlag("1");
		d.setDataCount(d.getDataCount() - count);
		this.update(d);
		return count;
	}
	
	public PageResult<DataContainer> queryPage(ICondition condition) {
		return super.queryPage(new DataContainerSolution(), condition, DataContainer.class);
	}

	@Override
	public int batSave(String[] containerName, String[] dataInfo){
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		for(int i=0;i<containerName.length;i++){
			if(StringUtils.isNotBlank(containerName[i])){
				String uuid = UUID.randomUUID().toString();
				list.add(new String[]{uuid,containerName[i], dataInfo[i], uuid});
			}
		}
		int[] nums = jdbcTemplate.batchUpdate("REPLACE INTO data_container (uuid, container_name, container_type, data_info, data_table) VALUES (?, ?, '1', ?, ?)", list);
		for(Object[] s:list){
			tableService.createTable(DataItem.class, "data_item_" + s[0]);
		}
		return CollectionUtil.sum(nums);
	}
	
	@Override
	public int batSave(String containerName, String dataInfo, String creator, String[] departments){
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		String uuid = UUID.randomUUID().toString();
		jdbcTemplate.update("INSERT INTO data_container (uuid, container_name, container_type, data_info, data_table, upload_user) VALUES (?, ?, '1', ?, ?, ?)", uuid, containerName, dataInfo, uuid, creator);
		tableService.createTable(DataItem.class, "data_item_" + uuid);
		int[] nums = null;
		if(departments != null) {
			for(String d:departments) 
				if(!StringUtils.isBlank(d))
					list.add(new Object[]{uuid,d});
			nums = jdbcTemplate.batchUpdate("INSERT INTO data_container_department_link (datacontainer_uuid, department_uuid) VALUES (?, ?)", list);
		}
		
		DataContainerLog dl = new DataContainerLog();
		dl.setContainerUuid(uuid);
		dl.setContainerName(containerName);
		dl.setDataTable(uuid);
//		dl.setFilePath("");
		dl.setOperate(DataContainerLog.OPERATE_CREATE);
		dl.setOperater(creator);
		dataContainerLogService.save(dl);
		
		return CollectionUtil.sum(nums);
	}
	
	public int bindDepartment(String uuid, String[] departments) {
		if(departments == null || departments.length == 0)
			return 0;
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		for(String d:departments)
			if(!StringUtils.isBlank(d))
				list.add(new Object[]{uuid,d});
		int[] nums = jdbcTemplate.batchUpdate("REPLACE INTO data_container_department_link (datacontainer_uuid, department_uuid) VALUES (?, ?)", list);
		return CollectionUtil.sum(nums);
	}
	
	public boolean batDelete(String[] uuids) {
		for(String u:uuids)
			this.deleteById(UUID.UUIDFromString(u));
		return true;
	}
	
	public boolean batDelete(String[] uuids, String operator) {
		for(String u:uuids)
			this.deleteById(UUID.UUIDFromString(u), operator);
		return true;
	}
	
	public Map<String, Project> getUserAllocate(String containerName) {
		Map<String, Project> map = new LinkedHashMap<String, Project>();
		List<Map<String,Object>> list = jdbcTemplate.queryForList("SELECT project_name, project_info, SUM( CASE WHEN call_times > 0 THEN 1 ELSE 0 END ) completeCount, COUNT(*) dataCount, c.department department FROM data_item_" + containerName + " a JOIN data_project b ON a.item_owner = b.uuid JOIN user_user c on b.uuid = c.uuid GROUP BY item_owner ORDER BY department;");
		for(Map<String, Object> m:list) {
			Project temp = new Project();
			temp.setUid(m.get("project_info").toString());
			temp.setProjectName(m.get("project_name").toString());
			temp.setDataCount(Integer.valueOf(m.get("dataCount").toString()));
			temp.setUserCount(Integer.valueOf(m.get("completeCount").toString()));
			temp.setDepartment(m.get("department").toString());
			map.put(temp.getUid(), temp);
		}
		return map;
	}

	@Override
	public void move(String uuid, Integer moveData, String targetPool) {
		DataContainer d = getByUuid(UUID.UUIDFromString(uuid));
		if("0".equals(d.getContainerType()))
			return;
		List<String> list = null;
		StringBuilder whereSql = new StringBuilder();
		switch (moveData) {
		case 0:{ //移动未分配数据
			whereSql.append(" where item_owner is null ");
			break;
		}
		case 1:{
			whereSql.append(" where item_owner is not null ");
			list = jdbcTemplate.queryForList("select item_owner from data_item_" + d.getDataTable() + " where item_owner is not null group by item_owner", String.class);
			break;
		}
		case 2:{
			list = jdbcTemplate.queryForList("select item_owner from data_item_" + d.getDataTable() + " where item_owner is not null group by item_owner", String.class);
			break;
		}
		}
		if (list != null)
			for (String l : list)
				jdbcTemplate.update("update data_task_" + l + " set data_source=? where data_source=?", targetPool, uuid);
		jdbcTemplate.update("insert into data_item_" + targetPool + " (select * from data_item_" + uuid + whereSql.toString() + ")");
		jdbcTemplate.update("delete from data_item_" + uuid + whereSql.toString());
		synchronizedService.updateContainer(uuid);
		synchronizedService.updateContainer(targetPool);
	}

	@Override
	public List<String> getDatas(ICondition condition) {
		StringBuilder querySql = new StringBuilder("select t.uuid ");
		List<Object> params = new ArrayList<Object>();
		DataContainerSolution solution = new DataContainerSolution();
		solution.getWhere(condition, querySql, params);
		return jdbcTemplate.queryForList(querySql.toString(), params.toArray(), String.class); 
	}
	
	public JSONArray getDataContainerTree() {
        JSONArray ret = new JSONArray();
        List<DataContainer> dataContainer = getDatas("1");
   /*     var zNodes =[  
        { id:1, pId:0, name:"父节点 1", open:true},  
        { id:11, pId:1, name:"叶子节点 1-1"},  
        { id:2, pId:0, name:"父节点 2", open:true},  
        { id:21, pId:2, name:"叶子节点 2-1"}
        ];*/

        JSONObject jsonRoot = new JSONObject();
        jsonRoot.put("id", "datapool");
        jsonRoot.put("pId", "root");
        jsonRoot.put("name", "数据池");
        jsonRoot.put("open", true);
        
        ret.put(jsonRoot);
        for (DataContainer d : dataContainer) {
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.put("id", d.getUuid().toString());
        	jsonObject.put("pId", "datapool");
        	jsonObject.put("name", d.getContainerName());
        	jsonObject.put("open", true);
            ret.put(jsonObject);
        }
        return ret;
	}

	@Override
	public void getBack(String dataTable, String dataId, String userId) {
		String updateSql = "update data_item_" + dataTable + " set item_owner = ?,allocate_time = NOW() where uuid = ?";
		String insertSql = "insert into data_task_" + userId + "(uuid,item_name,item_phone,item_address,item_json,item_owner,call_times,allocate_time,data_source) (select uuid,item_name,item_phone,item_address,item_json,item_owner,call_times,allocate_time, '" + dataTable + "' data_source from data_item_" + dataTable + " where uuid = ? )";
		jdbcTemplate.update(updateSql, userId, dataId);
		jdbcTemplate.update(insertSql, dataId);
	}

	@Override
	public void updateName(String uuid, String containerName, String dataInfo, String operater) {
		DataContainer d = getByUuid(UUID.UUIDFromString(uuid));
		DataContainerLog dl = new DataContainerLog();
		dl.setContainerUuid(d.getUid());
		dl.setContainerName(containerName);
		dl.setDataTable(d.getDataTable());
		dl.setFilePath(d.getFilePath());
		dl.setOperate(DataContainerLog.OPERATE_UPDATE);
		dl.setOperater(operater);
		dataContainerLogService.save(dl);
		
		String updateSql = "update data_container set container_name = ?,data_info = ? where uuid = ?";
		jdbcTemplate.update(updateSql, containerName, dataInfo, uuid);
	}
	
	public List<String> getDataContainerDepartments(String uuid) {
		return jdbcTemplate.queryForList("select department_uuid from data_container_department_link where datacontainer_uuid=?", String.class, uuid);
	}
	
	public List<String> getDepartmentDataContainers(String uuid) {
		return jdbcTemplate.queryForList("select datacontainer_uuid from data_container_department_link where department_uuid=?", String.class, uuid);
	}
	
	public Set<String> getDepartmentsDataContainers(Collection<String> uuids) {
		Set<String> set = new HashSet<String>();
		for (String string : uuids) {
			set.addAll(jdbcTemplate.queryForList("select datacontainer_uuid from data_container_department_link where department_uuid=?", String.class, string));
		}
		return set;
	}

	@Override
	public int unbindDepartment(String uuid, String[] departments) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		for(String d:departments)
			if(!StringUtils.isBlank(d))
				list.add(new Object[]{uuid,d});
		int[] nums = jdbcTemplate.batchUpdate("DELETE from data_container_department_link where datacontainer_uuid=? and department_uuid=?", list);
		return CollectionUtil.sum(nums);
	}
	
	public void unbindAllDepartment(String uuid) {
		jdbcTemplate.update("DELETE from data_container_department_link where datacontainer_uuid=?", uuid);
	}
	
	public void deleteDepartment(String uuid) {
		jdbcTemplate.update("DELETE from data_container_department_link where department_uuid=?", uuid);
	}

	@Override
	public List<DataContainer> getDatas(Collection<String> uuids) {
		if(uuids == null || uuids.size() == 0)
			return new ArrayList<DataContainer>();
		StringBuilder sb = new StringBuilder("select * from data_container where 1 = 1");
		List<Object> l = new ArrayList<Object>();
		QueryUtils.in(sb, l, uuids, " and uuid ");
		sb.append(" order by container_type ");
		return getBeanListWithSql(DataContainer.class, sb.toString(), l.toArray());
	}

	@Override
	public List<DataContainer> getDatas(String containerType,
			Collection<String> uuids) {
		if(uuids == null || uuids.size() == 0)
			return new ArrayList<DataContainer>();
		StringBuilder sb = new StringBuilder("select * from data_container where 1 = 1");
		List<Object> l = new ArrayList<Object>();
		QueryUtils.in(sb, l, uuids, " and uuid ");
		QueryUtils.queryData(sb, l, containerType, " and container_type=? ");
		return getBeanListWithSql(DataContainer.class, sb.toString(), l.toArray());
	}
}
