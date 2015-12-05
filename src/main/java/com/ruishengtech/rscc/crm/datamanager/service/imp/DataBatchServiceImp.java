package com.ruishengtech.rscc.crm.datamanager.service.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.framework.core.db.service.TableService;
import com.ruishengtech.framework.core.util.CollectionUtil;
import com.ruishengtech.rscc.crm.data.model.DataItem;
import com.ruishengtech.rscc.crm.datamanager.manager.DataManagers;
import com.ruishengtech.rscc.crm.datamanager.manager.ExcelImporter;
import com.ruishengtech.rscc.crm.datamanager.manager.ExcelTask;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BatchNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.DepNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.UserNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.DepToBatchData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToDepData;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatch;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchDepartmentLink;
import com.ruishengtech.rscc.crm.datamanager.model.DepartmentTable;
import com.ruishengtech.rscc.crm.datamanager.model.UserTask;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchDataService;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.datamanager.service.DeptDataService;
import com.ruishengtech.rscc.crm.datamanager.solution.DataBatchDataSolution;
import com.ruishengtech.rscc.crm.datamanager.solution.DataBatchSolution;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

@Service
@Transactional
public class DataBatchServiceImp extends BaseService implements DataBatchService {
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DataManagers dataManagers;
	
	@Autowired
	private DataBatchDataService batchDataService;
	
	@Autowired
	private DeptDataService deptDataService;
	
	@Autowired
	private DataBatchDepartmentLinkServiceImp dataBatchDepartmentLinkService;
	
	public List<DataBatch> getDatas() {
		List<DataBatch> list = getBeanList(DataBatch.class,"");
		return list;
	}
	
	public List<String> getUuids() {
		List<String> list = jdbcTemplate.queryForList("select uuid from new_data_batch", String.class);
		return list;
	}

	public DataBatch getByUuid(UUID uuid) {
		return super.getByUuid(DataBatch.class, uuid);
	}

	public void createDefaultBatch() {
		jdbcTemplate.update("insert into new_data_batch (uuid,batch_name,data_table,upload_user,file_name,file_path) values(?,?,?,?,?,?)", "", "默认批次", "", "0", "", "");
	}
	
	public DataBatchData getDataByPhone(String tableName, String phone) {
		
		List<DataBatchData> list = getBeanListWithTable("new_data_batch_" + tableName, DataBatchData.class, "and phone_number = ?", phone);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	
	public boolean save(DataBatch d) {
		super.save(d, new String[]{"dataCount","ownCount","intentCount","customerCount","frozenCount","abandonCount","blacklistCount"});
		return true;
	}
	
	public boolean save(DataBatch db, String[] excludeFieldName) {
		super.save(db, excludeFieldName);
		return true;
	}
	
	public boolean update(DataBatch db) {
        super.update(db, new String[]{"intentCount","customerCount","frozenCount","abandonCount","blacklistCount"});
		tableService.createTable(DataBatchData.class, "new_data_batch_" + db.getUid());
		return true;
	}
	
	public void updateName(String uuid, String batchName) {
		String updateSql = "update new_data_batch set batch_name = ? where uuid = ?";
		jdbcTemplate.update(updateSql, batchName,uuid);
	}
	
	public void update(DataBatch d, String[] excludeFieldName) {
		tableService.createTable(DataItem.class, "new_data_batch_" + d.getUid());
		super.update(d, excludeFieldName);
	}
	
	public PageResult<DataBatch> queryPage(ICondition condition) {
		return super.queryPage(new DataBatchSolution(), condition, DataBatch.class);
	}
	
	public Set<String> getDepartmentsDataBatchs(Collection<String> uuids) {
		Set<String> set = new HashSet<String>();
		for (String string : uuids) {
			set.addAll(jdbcTemplate.queryForList("select data_batch_uuid from " + DataBatchDepartmentLink.tableName + " where department_uuid=?", String.class, string));
		}
		return set;
	}
	
	
	public int bindDepartment(String uuid, String[] departments, boolean isNew) {
		if(departments == null || departments.length == 0)
			return 0;
		
		//查询批次部门关联表中该批次所关联的所有部门UUID
		List<String> departmentList = jdbcTemplate.queryForList("select department_uuid FROM new_data_batch_department_link WHERE data_batch_uuid=?", 
				String.class, uuid);
		
		//要更新的列表
		ArrayList<Object[]> updatelist = new ArrayList<Object[]>();
		//要插入的列表
		ArrayList<Object[]> insertlist = new ArrayList<Object[]>();
		//同步的列表
		ArrayList<Object[]> syncList = new ArrayList<Object[]>();
		//页面勾选的部门id列表
		ArrayList<String> list = new ArrayList<>();
		
		for(String d:departments) {
			if(!StringUtils.isBlank(d)){
				String[] dept = d.split("-");
				if (dept.length >= 3 && departmentList.contains(dept[0])) { //包含时为更新
					updatelist.add(new Object[]{dept[1],dept[2],uuid,dept[0]});
				} else if (dept.length >= 3) {//不包含时插入
					insertlist.add(new Object[]{uuid,dept[0],dept[1],dept[2]});
				}
				list.add(dept[0]);
			}
		}
		if (updatelist.size() > 0) {
			jdbcTemplate.batchUpdate("update new_data_batch_department_link set single_limit=?,day_limit=?,is_auto='0' where data_batch_uuid=? and department_uuid=?", updatelist);
		}
		if (insertlist.size() > 0) {
			jdbcTemplate.batchUpdate("insert into new_data_batch_department_link(data_batch_uuid,department_uuid,single_limit,day_limit) values(?,?,?,?)", insertlist);
		}
		
		//可以结合在上面，如果不是新创建的，再更新数据量
		if (!isNew) {
			String syncSql = "update new_data_batch_department_link set data_count=?,own_count=?,intent_count=?,customer_count=?,abandon_count=?,blacklist_count=? "
					+ " where data_batch_uuid=? and department_uuid=?";
			for (String s : list) {
				Map<String, Object> map = deptDataService.getDataDeptStat(uuid, s);
				syncList.add(new Object[]{(int)map.get("dataCount") + (int)map.get("ownCount"), map.get("ownCount"), map.get("intentCount"), map.get("customerCount"), 
						map.get("abandonCount"), map.get("blacklistCount"), uuid, s});
			}
			if (syncList.size() > 0) {
				jdbcTemplate.batchUpdate(syncSql, syncList);
			}
		}
		
		return 0;
	}
	
	public int autoBindDepartment(String batchUuid, String department) {
		ArrayList<Object> list = new ArrayList<>();
		list.add(batchUuid);
		list.add(department);
		//这里设置为0更好理解，但是分配的时候要判断
		list.add(5000);
		list.add(5000);
		//设置自动标志，标记不可获取
		list.add("1");
		
		jdbcTemplate.update("insert into new_data_batch_department_link(data_batch_uuid,department_uuid,single_limit,day_limit,is_auto) values(?,?,?,?,?)", list.toArray());
		
		//可以结合在上面，如果不是新创建的，再更新数据量
		String syncSql = "update new_data_batch_department_link set data_count=?,own_count=?,intent_count=?,customer_count=?,abandon_count=?,blacklist_count=? "
				+ " where data_batch_uuid=? and department_uuid=?";
		Map<String, Object> map = deptDataService.getDataDeptStat(batchUuid, department);
		jdbcTemplate.update(syncSql, (int)map.get("dataCount") + (int)map.get("ownCount"), map.get("ownCount"), map.get("intentCount"), map.get("customerCount"), 
				map.get("abandonCount"), map.get("blacklistCount"), batchUuid, department);
		
		return 0;
	}
	
	public int unbindDepartment(String uuid, String[] undepartments) {
		if(undepartments == null || undepartments.length == 0)
			return 0;
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		for(String d:undepartments){
			if(!StringUtils.isBlank(d)){
//				if(dataBatchDepartmentLinkService.getByLink(uuid, d) != null){
//					jdbcTemplate.update("update new_data_batch_department_link set is_auto=1 where data_batch_uuid=? and department_uuid=?", uuid, d);
//				}else{
//					list.add(new Object[]{uuid,d});
//				}
				list.add(new Object[]{uuid,d});
			}
		}
		int[] nums = jdbcTemplate.batchUpdate("DELETE from new_data_batch_department_link where data_batch_uuid=? and department_uuid=?",list);
		return CollectionUtil.sum(nums);
	}

	@Override
	public List<String> getDatas(ICondition condition) {
		StringBuilder querySql = new StringBuilder("select t.uuid ");
		List<Object> params = new ArrayList<Object>();
		DataBatchSolution solution = new DataBatchSolution();
		solution.getWhere(condition, querySql, params);
		return jdbcTemplate.queryForList(querySql.toString(), params.toArray(), String.class); 
	}
	
	public List<String> getDataBatchDepartments(String data_batch_uuid) {
		return jdbcTemplate.queryForList("select department_uuid from new_data_batch_department_link where data_batch_uuid=?", String.class, data_batch_uuid);
	}
	
	public List<String> getDataBatchDepartmentsIgnoreLink(String data_batch_uuid) {
		return jdbcTemplate.queryForList("select distinct(own_department) from new_data_batch_" + data_batch_uuid, String.class);
	}
	
	public List<String> getDepartmentDataBatchs(String deptuuid) {
		return jdbcTemplate.queryForList("select data_batch_uuid from new_data_batch_department_link where department_uuid=?", String.class, deptuuid);
	}
	
	public DepartmentTable getDepartmentById(String deptid){
		return super.getByUuid(DepartmentTable.class, UUID.UUIDFromString(deptid));
	}
	
	@Override
	public List<DepartmentTable> getAllDepartment() {
		return getBeanListWithOrder(DepartmentTable.class, "", "department_name asc");
	}
	
	public List<User> getUserBydeptId(String deptid){
		
		List<UserTask> uts = super.getBeanList(UserTask.class, " and department_uuid = ?", deptid);
		List<String> userid = new ArrayList<>();
		List<User> users = new ArrayList<>();
		if(uts.size()!=0){
			for(UserTask ut : uts){
				userid.add(ut.getUid());
			}
		}
		if(userid.size()!=0){
			for(String id : userid){
				users.add(userService.getByUuid(UUID.UUIDFromString(id)));
			}
		}
		return users;
	}
	public UserTask getUserTaskByUUID(String userid){
		return super.getByUuid(UserTask.class, UUID.UUIDFromString(userid));
	}
	
	public void importExcelToTable(String uuid, String filePath, String uploader, String tableName, Collection<String> relatedUsers) {
		ExcelTask task = new ExcelTask(uuid, filePath, uploader, tableName, relatedUsers);
		ExcelImporter.getInstance().exe(task);
	}

	@Override
	public DataBatch getDataBatchByTable(String dataTable) {
		List<DataBatch> list = getBeanList(DataBatch.class, "and data_table = ?", dataTable);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public DataBatch getDataBatchByBatchName(String batchName){
		List<DataBatch> list = getBeanList(DataBatch.class, "and batch_name = ? ",batchName);
		if (list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public String getLimitByDeptId(String batchid,String deptid) {
		List<DataBatchDepartmentLink>  batchDept = getBeanListWithSql(DataBatchDepartmentLink.class, "select data_batch_uuid,department_uuid,single_limit,day_limit from new_data_batch_department_link where data_batch_uuid = ? and department_uuid = ?",batchid,deptid);
		if(batchDept.size()<=1){
			for(DataBatchDepartmentLink dd : batchDept){
				return dd.getSingleLimit().toString()+"-"+dd.getDayLimit().toString();
			}
		}
		return "";
	}
	
	public List<String> getBatchData(ICondition condition) {
		StringBuilder querySql = new StringBuilder("select t.uuid ");
		List<Object> params = new ArrayList<Object>();
		DataBatchDataSolution solution = new DataBatchDataSolution();
		solution.getWhere(condition, querySql, params);
		return jdbcTemplate.queryForList(querySql.toString(), params.toArray(), String.class);
	}
	
	@Override
	public void batRecoveryData(String batchUuid,String[] dataUuids) {
		List<String> deptUuid = getDataBatchDepartments(batchUuid);
		//先从用户回收到部门
		for(String id : deptUuid){
			UserNode fromNode = new UserNode(id);
			DepNode toNode = new DepNode(id);
			UserToDepData transferData  = new UserToDepData();
			transferData.setDatas(dataUuids);
			transferData.setBatchUuid(batchUuid);
			
			dataManagers.transfer(fromNode, toNode, transferData);
		}
		//再从部门回收回批次
		for(String id : deptUuid){
			DepNode fromNode = new DepNode(id);
			BatchNode toNode = new BatchNode(batchUuid);
			DepToBatchData transferData = new DepToBatchData();
			transferData.setDatas(dataUuids);
			
			dataManagers.transfer(fromNode, toNode, transferData);
		}
		
	}
	
	@Override
	public void collectData(String batchUuid,String[] depts) {
		DepNode fromNode = new DepNode();
		BatchNode toNode = new BatchNode(batchUuid);
		DepToBatchData transferData = new DepToBatchData();
		for(String id : depts){
			fromNode.setTableName(id);
			dataManagers.transfer(fromNode, toNode, transferData);
		}
	}
	
	public String getPhoneBatch(String phone) {
		String batch = null;
		try {
			batch = jdbcTemplate.queryForObject("select batch_uuid from new_data_phone_resource where phone_number=?", String.class, phone);
		} catch (EmptyResultDataAccessException e) {
			batch = null;
		}
		return batch;
	}

	@Override
	public void deleteBatch(String uuid) {
		super.deleteById(DataBatch.class, UUID.UUIDFromString(uuid));
		tableService.deleteTable("new_data_batch_" + uuid);
		jdbcTemplate.update("delete from new_data_phone_resource where batch_uuid=?", uuid);
		jdbcTemplate.update("delete from new_data_batch_department_link where data_batch_uuid=?", uuid);
	}

	@Override
	public DataBatchData getDataBatchDataByUuid(String uuid, String source) {
		List<DataBatchData> list = getBeanListWithTable("new_data_batch_" + source, DataBatchData.class, "and uuid = ?", uuid);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}

	@Override
	public boolean batchDeleteData(HttpServletRequest request, String batchUuid) {
		String[] uuids = request.getParameterValues("uuids[]");
		List<String> uids = new ArrayList<String>();
		
		for (int i = 0; i < uuids.length; i++) {
			DataBatchData db = getDataBatchDataByUuid(uuids[i], batchUuid);
			if(db != null){
				if(db.getOwnDepartment() != null || db.getOwnUser() != null || db.getCallCount() > 0){
					return false;
				}
				uids.add(uuids[i]);
			}
		}
		
		for (String uid : uids) {
			DataBatchData db = getDataBatchDataByUuid(uid, batchUuid);
			DataBatch ba = getByUuid(UUID.UUIDFromString(batchUuid));
			// 删除批次表相关数据信息
			batchDataService.deleteDataByUuid(uid, batchUuid);
			// 删除号码表相关数据信息
			batchDataService.deleteDataByPhoneNumber(db.getPhoneNumber(), batchUuid);
			// 更新数据的数量
			batchDataService.updateDataCount(ba.getDataCount() - 1, batchUuid);
		}
		
		return true;
	}
	
	
	//暂时不考虑被群呼占用，以后可以直接提示被群呼占用，不允许删除
	@Override
	public boolean forceDeleteBatch(String uuid) {
		
		//不允许删除被群呼占用的数据
		if(jdbcTemplate.queryForObject("select count(*) from new_data_group_call_link where data_batch=?", Integer.class, uuid) > 0) {
			return false;
		}
		
		//把资源总表中的号码修改位置
		jdbcTemplate.update("update new_data_phone_resource t set batch_uuid='' where t.batch_uuid=? and (t.customer_uuid is not null or "
				+ " exists (select phone_number from new_data_batch_" + uuid + " p where t.phone_number=p.phone_number and p.intent_type is not null))", uuid);
		jdbcTemplate.update("delete from new_data_phone_resource where batch_uuid=?", uuid);
		
		List<String> departments = jdbcTemplate.queryForList("select uuid from new_data_department", String.class);
		Map<String, Integer> deptResult = new HashMap<>();
		Map<String, Integer> userResult = new HashMap<>();
		List<Map<String, Object>> userList = new ArrayList<>();
		
		for (String dept : departments) {
			
			//删除部门下数据
			String deleteSql = "delete from new_data_department_" + dept + " where batch_uuid=?";
			deptResult.put(dept, jdbcTemplate.update(deleteSql, uuid));
			
			//获取非意向数量
			String getUserCountSql = "select count(*) count,own_user from new_data_department_user_" + dept + 
					" where batch_uuid=? and (intent_type is null or intent_type='') group by own_user";
			userList.addAll(jdbcTemplate.queryForList(getUserCountSql, uuid));
			
			//删除非意向数据
			String deleteUserSql = "delete from new_data_department_user_" + dept + " where batch_uuid=? and (intent_type is null or intent_type='')";
			userResult.put(dept, jdbcTemplate.update(deleteUserSql, uuid));
			
			//数据归并到默认批次
			String updateSql = "update new_data_department_user_" + dept + " set batch_uuid='' where batch_uuid=? ";
			jdbcTemplate.update(updateSql, uuid);
			
			
		}
		
		//更新数量
		String updateCountSql = "update new_data_department_user set data_count=data_count-? where uuid=?";
		for (Map<String, Object> map : userList) {
			jdbcTemplate.update(updateCountSql, map.get("count"), map.get("own_user"));
		}
		
		//从共享池中删除数据
		String deleteFromShare = "delete from new_data_global_share where batch_uuid=?";
		jdbcTemplate.update(deleteFromShare, uuid);
		
		//保留意向客户和成交客户
		String insertSql = "insert into new_data_batch_ (select * from new_data_batch_" + uuid + 
				" where (intent_type is not null and intent_type!='') or customer_uuid is not null)";
		jdbcTemplate.update(insertSql);
		String updateBatchUuid = "update new_data_batch_ set batch_uuid='' where batch_uuid=?";
		jdbcTemplate.update(updateBatchUuid, uuid);
		
		//还缺少一个把客户表中来源修改的操作

		//删除实体
		super.deleteById(DataBatch.class, UUID.UUIDFromString(uuid));
		
		//删除表
		tableService.deleteTable("" + uuid);
		
		//连接表中删除
		jdbcTemplate.update("delete from new_data_batch_department_link where data_batch_uuid=?", uuid);

		//资源总表中删除，删多了。。。要判断上面是否删除了这个数据。我觉得把他挪到上面。
		//jdbcTemplate.update("delete from new_data_phone_resource where batch_uuid=?", uuid);
		
		Map<String, Object> stat = getBatchStat("");
		jdbcTemplate.update("update new_data_batch set data_count=?,own_count=?,intent_count=?,customer_count=?,"
				+ "frozen_count=?,abandon_count=?,share_count=?,blacklist_count=? where uuid=''",
				stat.get("totalCount"), stat.get("ownCount"), stat.get("intentCount"), stat.get("customerCount"), stat.get("frozenCount"), 
				stat.get("abandonCount"), stat.get("shareCount"), stat.get("blacklistCount"));
		
		return true;
	}

	/*
	 * dataCount数据总量
	 * ownCount领用数据量，包含共享池，冷冻，成交，无效和黑名单的号码（因为他们是从领用的数据打出去的）
	 * intentCount意向数量
	 * shareCount共享池中数据量
	 * frozenCount冻结数据量
	 * customerCount成交客户量
	 * abandonCount废号量
	 * blacklistCount黑名单数量
	 * @see com.ruishengtech.rscc.crm.datamanager.service.DeptDataService#getDataDeptStat(java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> getBatchStat(String batchUuid) {
		String statisticSql = "SELECT count(t.uuid) dataCount, "
				+ "sum(own_department IS NOT NULL) ownCount, "
				+ "sum( own_user IS NOT NULL AND intent_type IS NOT NULL ) intentCount, "
				+ "sum( own_department = 'global_share' AND own_user IS NULL AND is_abandon = '0' AND is_blacklist = '0' AND is_frozen = '0' AND customer_uuid IS NULL ) shareCount, "
				+ "sum(is_frozen) frozenCount, "
				+ "sum(customer_uuid IS NOT NULL) customerCount, "
				+ "sum(is_abandon) abandonCount, "
				+ "sum(is_blacklist) blacklistCount "
				+ "FROM new_data_batch_" + batchUuid + " t";
		Map<String, Object> map = jdbcTemplate.queryForMap(statisticSql);
		for(String key:map.keySet()) {
			Object value = map.get(key);
			if (value == null) {
				map.put(key, 0);
			} else if (value instanceof Number) {
				map.put(key, ((Number) value).intValue());
			} 
		}
		return map;
	}
	
	//获取部门数据信息
	public List<DataBatchDepartmentLink> getBatchDepInfo(String batchUuid) {
		
		return getBeanListWithSql(DataBatchDepartmentLink.class, "SELECT count(*) dataCount,'" + batchUuid + "' data_batch_uuid,"
				+ "t.own_department department_uuid,p.department_name deptname "
				+ "FROM new_data_batch_" + batchUuid + 
				" t join new_data_department p on t.own_department=p.uuid "
				+ " where own_user is null and is_frozen=0 GROUP BY t.own_department");
	}
	
	
}
