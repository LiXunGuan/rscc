package com.ruishengtech.rscc.crm.datamanager.service.imp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.datamanager.condition.DepartmentDataCondition;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatch;
import com.ruishengtech.rscc.crm.datamanager.model.DepartmentTable;
import com.ruishengtech.rscc.crm.datamanager.model.UserData;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.datamanager.service.IntentDataService;
import com.ruishengtech.rscc.crm.datamanager.solution.IntentDataAdminSolution;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

@Service
@Transactional
public class IntentDataServiceImp extends BaseService implements IntentDataService{
	@Autowired
	private DepartmentTableServiceImp deptTableService;
	
	@Autowired
	private DataBatchService dataBatchService;
	
	@Autowired
	private UserService userService;
	
	public PageResult<UserData> queryPage(DepartmentDataCondition condition) {
		if(StringUtils.isBlank(condition.getDeptUuid())){
			List<DepartmentTable> depts = deptTableService.getAll();
			condition.setAllDept(depts);
		}
		return super.queryPage(new IntentDataAdminSolution(), condition, UserData.class);
	}
	
	public void getAllBatchAndUser(List<UserData> list,DepartmentDataCondition departmentDataCondition,JSONObject jsonObject2){
		//如果页面选择全部部门的话，要把显示出来的数据的所有的部门和批次放到json里面
		if(StringUtils.isBlank(departmentDataCondition.getDeptUuid())){
			Set<String> bats = new HashSet<>();
			Set<String> uss = new HashSet<>();
			for(UserData ud : list){
				bats.add(ud.getBatchUuid());
				uss.add(ud.getOwnUser());
			}
			List<DataBatch> bat = new ArrayList<>();
			List<User> us = new ArrayList<>();
			for(String b : bats){
				bat.add(dataBatchService.getByUuid(UUID.UUIDFromString(b)));
			}
			for(String u : uss){
				us.add(userService.getByUuid(UUID.UUIDFromString(u)));
			}
			jsonObject2.put("users", new JSONArray(us));
			jsonObject2.put("batch", new JSONArray(bat));
		}else{
			
			jsonObject2.put("users", deptTableService.getUserByDept(departmentDataCondition.getDeptUuid()));
			jsonObject2.put("batch", deptTableService.getBatchByDept(departmentDataCondition.getDeptUuid()));
		}
	}
}
