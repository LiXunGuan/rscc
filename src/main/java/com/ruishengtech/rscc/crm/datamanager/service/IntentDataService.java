package com.ruishengtech.rscc.crm.datamanager.service;

import java.util.List;

import org.json.JSONObject;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.condition.DepartmentDataCondition;
import com.ruishengtech.rscc.crm.datamanager.model.UserData;

public interface IntentDataService {
	
	public PageResult<UserData> queryPage(DepartmentDataCondition condition);
	
	public void getAllBatchAndUser(List<UserData> list,DepartmentDataCondition departmentDataCondition,JSONObject jsonObject2);
}
