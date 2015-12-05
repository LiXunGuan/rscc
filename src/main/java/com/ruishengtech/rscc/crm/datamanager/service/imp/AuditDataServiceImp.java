package com.ruishengtech.rscc.crm.datamanager.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.datamanager.manager.DataManagers;
import com.ruishengtech.rscc.crm.datamanager.manager.node.CustomerNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.IntentNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.IntentToCustomerData;
import com.ruishengtech.rscc.crm.datamanager.model.UserData;
import com.ruishengtech.rscc.crm.datamanager.service.AuditDataService;
import com.ruishengtech.rscc.crm.datamanager.service.NewUserDataService;
import com.ruishengtech.rscc.crm.datamanager.solution.AuditDataAdminSolution;

@Service
@Transactional
public class AuditDataServiceImp extends BaseService implements AuditDataService {
	
	@Autowired
	private DataManagers dataManager;
	
	@Autowired
	private NewUserDataService newUserDataService;
	
	public PageResult<UserData> queryPage(ICondition condition) {
		return super.queryPage(new AuditDataAdminSolution(), condition, UserData.class);
	}
	
	public void reject(String batchUuid, String department, String phoneNumber) {
		String updateSql = "update new_data_department_user_" + department + " set be_audit = '2' where batch_uuid=? and phone_number=?";
		jdbcTemplate.update(updateSql, batchUuid, phoneNumber);
	}
	
	public void accept(String batchUuid, String department, String phoneNumber, String customerUuid) {
		
		UserData userData = newUserDataService.getUserDataByPhone(department, phoneNumber);
		IntentNode fromNode = new IntentNode(userData.getDeptUuid());
		CustomerNode toNode = new CustomerNode(customerUuid);
		IntentToCustomerData data = new IntentToCustomerData();
		data.setUserData(userData);
		dataManager.transfer(fromNode, toNode, data);
		
	}
}
