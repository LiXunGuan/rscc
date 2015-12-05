package com.ruishengtech.rscc.crm.datamanager.manager.transfer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.rscc.crm.datamanager.manager.TransferResult;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BatchNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.CustomerNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.UserNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToCustomerData;
import com.ruishengtech.rscc.crm.datamanager.model.PhoneResource;

/**
 * Created by yaoliceng on 2015/8/18.
 */
@Component
@Transactional
public class UserToCustomerTransfer extends AbstractTransfer<UserNode, CustomerNode, UserToCustomerData> {

	@Override
	public TransferResult transfer(UserNode fromNode, CustomerNode toNode, UserToCustomerData transferData) {
		
    	int count = delete(fromNode.getTableName(), toNode.getTableName(), 
    			transferData.getUserData().getPhoneNumber(), transferData.getUserData().getOwnUser(), transferData.getUserData().getBatchUuid());
    	
    	TransferResult tr = new TransferResult();
    	tr.setTransferCount(count);
    	
    	return tr;
	}
	   
	//移动到客户，删除本条数据，增加统计客户数量
    private int delete(String sourceTable, String targetTable, String phoneNumber, String transferUser, String transferBatch) {
		if(StringUtils.isBlank(phoneNumber))
			return 0;
		String deleteSql = "delete from " + UserNode.tablePrefix + sourceTable + " where phone_number=?";
		String updateSql = "update " + BatchNode.tablePrefix + transferBatch 
				+ " set is_blacklist='0',blacklist_timestamp=null,intent_type=null,intent_timestamp=null,"
				+ "is_frozen='0',frozen_timestamp=null,customer_uuid=?,is_lock='0',lock_timestamp=null,"
				+ "is_abandon='0',abandon_timestamp=null where phone_number=?";
		String updatePhoneResource = "update " + PhoneResource.tableName 
				+ " set is_blacklist='0',blacklist_timestamp=null,is_abandon='0',abandon_timestamp=null,"
				+ "is_frozen='0',frozen_timestamp=null,customer_uuid=? where phone_number=?";
		int updateCount = jdbcTemplate.update(deleteSql, phoneNumber);
		if (updateCount == 0) {
			return updateCount;
		}
		jdbcTemplate.update(updateSql, targetTable, phoneNumber);
		jdbcTemplate.update(updatePhoneResource, targetTable, phoneNumber);
		synchronzineData(transferBatch, sourceTable, transferUser, updateCount);
		return updateCount;
    }
    
    @Override
    public void synchronzineData(String batchUuid, String depUuid, String userUuid, Integer count) {
    	addCustomer(batchUuid, batchUuid, depUuid, userUuid, count);
    	addData(null, batchUuid, depUuid, userUuid, -count);
//    	addOwn(batchUuid, batchUuid, depUuid, -count);
    	addOwn(null, batchUuid, depUuid, -count);
    }

}
