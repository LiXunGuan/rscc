package com.ruishengtech.rscc.crm.datamanager.service;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.model.UserData;

public interface AuditDataService {
	
	public PageResult<UserData> queryPage(ICondition condition);
	
	public void reject(String batchUuid, String department, String phoneNumber);
	
	public void accept(String batchUuid, String department, String phoneNumber, String customerUuid);
	
}
