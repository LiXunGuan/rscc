package com.ruishengtech.rscc.crm.datamanager.manager.transfer;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.rscc.crm.datamanager.manager.TransferResult;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BatchNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.FrozenNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.FrozenToBatchData;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;
import com.ruishengtech.rscc.crm.datamanager.model.PhoneResource;
/**
 * 
 * @author ning 2015/10/12
 *
 */
@Component
@Transactional
public class FrozenToBatchTransfer extends AbstractTransfer<FrozenNode, BatchNode, FrozenToBatchData> {

	@Override
	public TransferResult transfer(FrozenNode fromNode, BatchNode toNode,
			FrozenToBatchData transferData) {
		int count = 0;
		count = remove(toNode.getTableName(), transferData.getDataBatchData());
		
		TransferResult tr = new TransferResult();
		tr.setTransferCount(count);
		return tr;
	}
	
	private int remove(String targetTable,DataBatchData dataBatchData){
		//恢复数据第一步：在批次数据明细中将拥有部门，拥有者，呼叫信息，意向信息，废弃信息 等全部重置为分配前状态
		String updateSql = "update " + BatchNode.tablePrefix + targetTable + " set own_department=null,own_department_timestamp=null,"
				+ "own_user=null,own_user_timestamp=null,call_count=0,last_call_department=null,last_call_user=null,last_call_result=null,"
				+ "last_call_time=null,intent_type=null,intent_timestamp=null,is_lock='0',lock_timestamp=null,"
				+ "is_abandon='0',abandon_timestamp=null,is_blacklist='0',blacklist_timestamp=null,is_frozen='0',frozen_timestamp=null,customer_uuid=null "
				+ "where phone_number=?";
		//恢复数据第二步：重置资源表中该条号码的相关信息
		String updatePhoneResource = "update " + PhoneResource.tableName + 
				" set is_abandon='0',abandon_timestamp=null,is_blacklist='0',blacklist_timestamp=null,is_frozen='0',frozen_timestamp=null,customer_uuid=null "
				+ "where phone_number=? ";
		//恢复数据第三部：更新相关统计数据
		int updateCount = jdbcTemplate.update(updateSql, dataBatchData.getPhoneNumber());
		if (updateCount == 0){
			return updateCount;
		}
		jdbcTemplate.update(updatePhoneResource,dataBatchData.getPhoneNumber());
		synchronzineData(targetTable, dataBatchData.getOwnDepartment(), dataBatchData.getOwnUser(), updateCount);
		return updateCount;
	}
	@Override
	public void synchronzineData(String batchUuid, String depUuid,
			String userUuid, Integer count) {
		addFrozen(batchUuid, -count);
	}

}
