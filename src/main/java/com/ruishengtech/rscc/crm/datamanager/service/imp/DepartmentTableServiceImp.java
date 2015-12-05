package com.ruishengtech.rscc.crm.datamanager.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.framework.core.db.service.TableService;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatch;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchDepartmentLink;
import com.ruishengtech.rscc.crm.datamanager.model.DepartmentData;
import com.ruishengtech.rscc.crm.datamanager.model.DepartmentTable;
import com.ruishengtech.rscc.crm.datamanager.model.UserData;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.datamanager.solution.DataBatchSolution;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

@Service
@Transactional
public class DepartmentTableServiceImp extends BaseService {
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private DataBatchService dataBatchService;
	
	@Autowired
	private UserService userService;
	
	public DepartmentTable getByUuid(UUID uuid) {
		return super.getByUuid(DepartmentTable.class, uuid);
	}
	
	public List<DepartmentTable> getAll() {
		return getBeanListWithOrder(DepartmentTable.class, "", "department_name desc");
	}
	
	public DepartmentTable getByName(String departmentName) {
		List<DepartmentTable> list = getBeanList(DepartmentTable.class, "and department_name = ?", departmentName);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public void save(String uuid, String departmentName, int totalLimit) {
		DepartmentTable d = new DepartmentTable();
		d.setDepartmentName(departmentName);
		d.setTotalLimit(totalLimit);
		super.save(d, new String[]{"dataCount", "shareCount", "ownCount", "intentCount", "customerCount", "abandonCount", "blacklistCount"});
		jdbcTemplate.update("update " + DepartmentTable.tableName + " set uuid=? where uuid=?", uuid, d.getUid());
		tableService.createTable(DepartmentData.class, DepartmentData.tableName + uuid);
		tableService.createTable(UserData.class, UserData.tableName + uuid);
	}
	
	public void update(DepartmentTable d) {
        super.update(d);
	}
	
	public void update(DataBatch d, String[] excludeFieldName) {
		super.update(d, excludeFieldName);
	}
	
	//暂时不考虑被群呼占用。。。
	//这里有可能在去除连接之后，删除有问题
	public boolean deleteById(UUID uuid) {
		List<Integer> count = jdbcTemplate.queryForList("select sum(data_count) count from " + DataBatchDepartmentLink.tableName + " where department_uuid=? group by department_uuid", Integer.class, String.valueOf(uuid));
		List<Integer> owncount = jdbcTemplate.queryForList("select sum(own_count) owncount from " + DataBatchDepartmentLink.tableName + " where department_uuid=? group by department_uuid", Integer.class, String.valueOf(uuid));
		
		if (count.size() > 0 || owncount.size() > 0) {
			return false;
		}
		super.deleteById(DepartmentTable.class, uuid);
		jdbcTemplate.update("delete from new_data_batch_department_link where department_uuid=?", uuid.toString());
		tableService.deleteTable(DepartmentData.tableName + uuid.toString());
		tableService.deleteTable(UserData.tableName + uuid);
		
		// create by ChengXin 2015-11-25
		List<Integer> defaultBatchCount = jdbcTemplate.queryForList(" select count(*)  from " + DataBatchData.tableName + " where own_department = ? ", Integer.class, String.valueOf(uuid));
		
		// 将默认批次中部门的数据改为默认部门下面
		if(defaultBatchCount.size() > 0){
			jdbcTemplate.update(" update "+ DataBatchData.tableName +" set own_department = '01' where own_department = ? ", String.valueOf(uuid));
		}
		
		return true;
	}
	
	public PageResult<DataBatch> queryPage(ICondition condition) {
		return super.queryPage(new DataBatchSolution(), condition, DataBatch.class);
	}

	public void updateLimit(String[] departmentUuid, Integer[] totalLimit) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		for (int i = 0; i < departmentUuid.length; i++) {
			String uuid = departmentUuid[i];
			if (StringUtils.isNotBlank(uuid)) {
				
				list.add(new Object[]{totalLimit[i], uuid});
			}
		}
		jdbcTemplate.batchUpdate("update new_data_department set total_limit=? where uuid=?", list);
	}
	
	/**
	 * 根据部门id获取到该部门关联的所有批次信息
	 */
	public JSONArray getBatchByDept(String deptid){
		List<String> batchids = jdbcTemplate.queryForList("select DISTINCT(batch_uuid) From new_data_department_user_" + deptid + " where intent_type is not null", String.class);
		List<DataBatch> batch = new ArrayList<>();
		for(String batchid : batchids){
			batch.add(dataBatchService.getByUuid(UUID.UUIDFromString(batchid)));
		}
		return new JSONArray(batch);
	}
	
	/**
	 * 根据部门id获取到该部门下所有拥有意向客户的坐席对象
	 */
	public JSONArray getUserByDept(String deptid){
		List<String> userid = jdbcTemplate.queryForList("SELECT DISTINCT(own_user) FROM new_data_department_user_"+ deptid +" where intent_type is NOT NULL",String.class);
		List<User> users = new ArrayList<>();
		if(userid != null && userid.size() != 0){
			for(String id : userid){
				users.add(userService.getByUuid(UUID.UUIDFromString(id)));
			}
		}
		return new JSONArray(users);
	}
}
