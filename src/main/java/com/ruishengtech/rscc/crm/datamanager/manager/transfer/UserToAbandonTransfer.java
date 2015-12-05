package com.ruishengtech.rscc.crm.datamanager.manager.transfer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.rscc.crm.datamanager.manager.TransferResult;
import com.ruishengtech.rscc.crm.datamanager.manager.node.AbandonNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BatchNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.UserNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToAbandonData;
import com.ruishengtech.rscc.crm.datamanager.model.PhoneResource;

/**
 * Created by yaoliceng on 2015/8/18.
 */
@Component
@Transactional
public class UserToAbandonTransfer extends AbstractTransfer<UserNode, AbandonNode, UserToAbandonData> {

	@Override
	public TransferResult transfer(UserNode fromNode, AbandonNode toNode, UserToAbandonData transferData) {
		
    	int count = delete(fromNode.getTableName(), toNode.getTableName(), transferData.getUserData().getPhoneNumber(), transferData.getUserData().getOwnUser());
    	
    	TransferResult tr = new TransferResult();
    	tr.setTransferCount(count);
    	
    	return tr;
	}
	   
	//移动到黑名单，删除本条数据，增加统计黑名单数量
    private int delete(String sourceTable, String targetTable, String phoneNumber, String transferUser) {
		if(StringUtils.isBlank(phoneNumber))
			return 0;
		
		String deleteSql = "delete from " + UserNode.tablePrefix + sourceTable + " where phone_number=?";
		String updateSql = "update " + BatchNode.tablePrefix + targetTable 
				+ " set is_blacklist='0',blacklist_timestamp=null,intent_type=null,intent_timestamp=null,"
				+ "is_frozen='0',frozen_timestamp=null,customer_uuid=null,is_lock='0',lock_timestamp=null,"
				+ "is_abandon='1',abandon_timestamp=now(),customer_uuid=null where phone_number=?";
		String updatePhoneResource = "update " + PhoneResource.tableName 
				+ " set is_blacklist='0',blacklist_timestamp=null,is_abandon='1',abandon_timestamp=now(),"
				+ "is_frozen='0',frozen_timestamp=null,customer_uuid=null where phone_number=?";
		int updateCount = jdbcTemplate.update(deleteSql, phoneNumber);
		if (updateCount == 0) {
			return updateCount;
		}
		jdbcTemplate.update(updateSql, phoneNumber);
		jdbcTemplate.update(updatePhoneResource, phoneNumber);
		synchronzineData(targetTable, sourceTable, transferUser, updateCount);
		return updateCount;
    }
    
	@Override
	public void synchronzineData(String batchUuid, String depUuid, String userUuid, Integer count) {
		addAbandon(batchUuid, batchUuid, depUuid, userUuid, count);
		addData(null, batchUuid, depUuid, userUuid, -count);
		addOwn(null, batchUuid, depUuid, -count);
//		addOwn(batchUuid, batchUuid, depUuid, -count);
	}

}
