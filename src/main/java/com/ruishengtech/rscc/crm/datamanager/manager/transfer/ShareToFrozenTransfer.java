package com.ruishengtech.rscc.crm.datamanager.manager.transfer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.rscc.crm.datamanager.manager.TransferResult;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BatchNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.FrozenNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.ShareNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.ShareToFrozenData;
import com.ruishengtech.rscc.crm.datamanager.model.PhoneResource;

/**
 * Created by yaoliceng on 2015/8/18.
 */
@Component
@Transactional
public class ShareToFrozenTransfer extends AbstractTransfer<ShareNode, FrozenNode, ShareToFrozenData> {

	@Override
	public TransferResult transfer(ShareNode fromNode, FrozenNode toNode, ShareToFrozenData transferData) {
		
    	int count = delete(toNode.getTableName(), transferData.getTransferPhone());
    	
    	TransferResult tr = new TransferResult();
    	tr.setTransferCount(count);
    	
    	return tr;
	}
	   
	//移动到冷冻池中
	//从共享池到某个批次
    private int delete(String targetTable, String phoneNumber) {
		if(StringUtils.isBlank(phoneNumber))
			return 0;
		
		String deleteSql = "delete from " + ShareNode.tableName + " where phone_number=?";
		String updateSql = "update " + BatchNode.tablePrefix + targetTable 
				+ " set is_blacklist='0',blacklist_timestamp=null,intent_type=null,intent_timestamp=null,"
				+ "customer_uuid=null,is_lock='0',lock_timestamp=null,"
				+ "is_abandon='0',abandon_timestamp=null,is_frozen='1',frozen_timestamp=now(),customer_uuid=null where phone_number=?";
		String updatePhoneResource = "update " + PhoneResource.tableName 
				+ " set is_blacklist='0',blacklist_timestamp=null,is_abandon='0',abandon_timestamp=null,"
				+ "is_frozen='1',frozen_timestamp=now(),customer_uuid=null where phone_number=?";
		int updateCount = jdbcTemplate.update(deleteSql, phoneNumber);
		if (updateCount == 0) {
			return updateCount;
		}
		jdbcTemplate.update(updateSql, phoneNumber);
		jdbcTemplate.update(updatePhoneResource, phoneNumber);
		synchronzineData(targetTable, null, null, updateCount);
		return updateCount;
    }
    
	@Override
	public void synchronzineData(String batchUuid, String depUuid, String userUuid, Integer count) {
		addFrozen(batchUuid, count);
	}

}
