package com.ruishengtech.rscc.crm.datamanager.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.cstm.model.Customer;
import com.ruishengtech.rscc.crm.datamanager.model.UserTask;
import com.ruishengtech.rscc.crm.datamanager.solution.UserTaskSolution;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

@Service
@Transactional
public class UserTaskServiceImp extends BaseService {

	@Autowired
	private UserService userService;
	
	public UserTask getByUuid(String userUuid) {
		return super.getByUuid(UserTask.class, UUID.UUIDFromString(userUuid));
	}
	
	public void save(UserTask d) {
		super.save(d);
	}
	
	public void update(UserTask d) {
		super.update(d);
	}

	public List<UserTask> getAll(){
		return getBeanList(UserTask.class, "");
	}
	
	public List<String> getAll(ICondition condition) {
		StringBuilder querySql = new StringBuilder("select t.uuid ");
		List<Object> params = new ArrayList<Object>();
		UserTaskSolution solution = new UserTaskSolution();
		solution.getWhere(condition, querySql, params);
		return jdbcTemplate.queryForList(querySql.toString(), params.toArray(), String.class); 
	}

	public PageResult<UserTask> queryPage(ICondition condition) {
		return super.queryPage(new UserTaskSolution(), condition, UserTask.class);
	}

	public void changeLimit(String userUuid, Integer totalLimit, Integer dayLimit, Integer singleLimit, Integer intentLimit) {
		jdbcTemplate.update("update new_data_department_user set total_limit=?,day_limit=?,single_limit=?,intent_limit=? where uuid=?", totalLimit, dayLimit, singleLimit, intentLimit, userUuid);
	}
	//批量修改用户任务上限
	public void changebat(String[] uuids,String changetype, Integer totalLimit, Integer dayLimit, Integer singleLimit, Integer intentLimit) {

//		String wheresql = "";
//		if("default".equals(changetype)){
//			wheresql = " total_limit='1000' and day_limit='1000' and single_limit='500' and intent_limit='500' ";
//		}else{
//			wheresql = " 1=1 ";
//		}
		
		List<Object> params = new ArrayList<Object>();
		List<String> us = new ArrayList<>();
		for(String u : uuids){
			us.add(u);
		}
		String ins = QueryUtils.inString(" uuid ", params, us);
		String sql = "UPDATE new_data_department_user "
				+ "SET total_limit="+totalLimit+",day_limit="+dayLimit+",single_limit="+singleLimit+",intent_limit="+intentLimit+" WHERE "+ins+"";
		
		jdbcTemplate.update(sql,params.toArray());
	}
	
	public void levelUp() {
		List<Customer> customerList = getBeanList(Customer.class, "");
		for (Customer customer : customerList) {
			String uuid = UUID.randomUUID().toString();
			User user = userService.getByUuid(UUID.UUIDFromString(customer.getOwnId()));
			if (user == null) {
				continue;
			}
			int exist = jdbcTemplate.queryForObject("select count(*) from new_data_batch_ where phone_number=?", Integer.class, customer.getPhoneNumber());
			if (exist > 0) {
				continue;
			}
			jdbcTemplate.update("REPLACE INTO new_data_phone_resource (phone_number,batch_uuid,customer_uuid) VALUES(?,?,?)", customer.getPhoneNumber(), "", customer.getUid());
			jdbcTemplate.update("INSERT INTO new_data_batch_ (uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp," +
					"own_user,own_user_timestamp,call_count,last_call_department,last_call_user,last_call_result,last_call_time,customer_uuid) VALUES "
					+ "(?,?,?,?,?,now(),?,now(),?,?,?,?,now(),?)", uuid, "", customer.getPhoneNumber(), "{}", user.getDepartment(), user.getUid(), 1, user.getDepartment(), user.getUid(), "1", customer.getUid());
		}
	}
	
	//比较最大值
	public String compareLimit(String string) {

		String sql = "MAX(single_limit)";
		if("day".equals(string)){
			sql = "MAX(day_limit)";
		}
		return jdbcTemplate.queryForObject("SELECT "+ sql +" FROM `new_data_department_user`", String.class);
	}
	
}
