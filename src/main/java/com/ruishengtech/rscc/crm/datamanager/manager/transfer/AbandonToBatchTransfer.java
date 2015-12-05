package com.ruishengtech.rscc.crm.datamanager.manager.transfer;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.rscc.crm.datamanager.manager.TransferResult;
import com.ruishengtech.rscc.crm.datamanager.manager.node.AbandonNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BatchNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.AbandonToBatchData;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;
import com.ruishengtech.rscc.crm.datamanager.model.PhoneResource;

/**
 * Created by yaoliceng on 2015/8/18.
 */
@Component
@Transactional
public class AbandonToBatchTransfer extends AbstractTransfer<AbandonNode, BatchNode, AbandonToBatchData> {

	@Override
	public TransferResult transfer(AbandonNode fromNode, BatchNode toNode, AbandonToBatchData transferData) {
		
		int count = 0;
		count = remove(toNode.getTableName(), transferData.getDataBatchData());
    	
    	TransferResult tr = new TransferResult();
    	tr.setTransferCount(count);
    	
    	return tr;
	}
	
    private int remove(String targetTable, DataBatchData dataBatchData) {
    	String updateSql = "update " + BatchNode.tablePrefix + targetTable + " set own_department=null,own_department_timestamp=null,"
				+ "own_user=null,own_user_timestamp=null,call_count=0,last_call_department=null,last_call_user=null,last_call_result=null,"
				+ "last_call_time=null,intent_type=null,intent_timestamp=null,is_lock='0',lock_timestamp=null,"
				+ "is_abandon='0',abandon_timestamp=null,is_blacklist='0',blacklist_timestamp=null,is_frozen='0',frozen_timestamp=null,customer_uuid=null "
				+ "where phone_number=?";
		//更新资源表
		String updatePhoneResource = "update " + PhoneResource.tableName + 
				" set is_abandon='0',abandon_timestamp=null,is_blacklist='0',blacklist_timestamp=null,is_frozen='0',frozen_timestamp=null,customer_uuid=null "
				+ "where phone_number=? ";
		int updateCount = jdbcTemplate.update(updateSql, dataBatchData.getPhoneNumber());
		if (updateCount == 0) {
			return updateCount;
		}
		jdbcTemplate.update(updatePhoneResource, dataBatchData.getPhoneNumber());
		synchronzineData(targetTable, dataBatchData.getOwnDepartment(), dataBatchData.getOwnUser(), updateCount);
		return updateCount;
    }
    
	@Override
	public void synchronzineData(String batchUuid, String depUuid, String userUuid, Integer count) {
		addAbandon(batchUuid, batchUuid, depUuid, userUuid, -count);
		addOwn(batchUuid, null, null, -count);
	}

}
