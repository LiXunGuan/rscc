package com.ruishengtech.rscc.crm.datamanager.manager.transfer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.rscc.crm.datamanager.manager.TransferResult;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BatchNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.IntentNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.UserNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.IntentToUserData;
import com.ruishengtech.rscc.crm.datamanager.model.UserData;

/**
 * Created by yaoliceng on 2015/8/18.
 */
@Component
@Transactional
public class IntentToUserTransfer extends AbstractTransfer<IntentNode, UserNode, IntentToUserData> {

	@Override
	public TransferResult transfer(IntentNode fromNode, UserNode toNode, IntentToUserData transferData) {
		
    	int count = change(toNode.getTableName(), transferData.getUserData(), transferData.isChange());
    	
    	TransferResult tr = new TransferResult();
    	tr.setTransferCount(count);
    	
    	return tr;
	}
	   
	//更改客户意向
	//没有数据更新，所以不用sync
	//其实是有intent_count更新的，再说
    private int change(String sourceTable, UserData userData, boolean isChange) {
		//修改intent_type字段即可，其实号码是唯一的，后面的判断可以省略一部分，这样加过索引速度就快了
		String updateSql = "update " + UserNode.tablePrefix + sourceTable + 
				" set intent_type=null,intent_timestamp=null where own_user=? and phone_number=?";
		String updateBatchSql = "update " + BatchNode.tablePrefix + userData.getBatchUuid() + 
				" set intent_type=null,intent_timestamp=null where own_user=? and phone_number=?";
		int updateCount = jdbcTemplate.update(updateSql, userData.getOwnUser(), userData.getPhoneNumber());
		if (updateCount == 0) {
			return updateCount;
		}
		jdbcTemplate.update(updateBatchSql, userData.getOwnUser(), userData.getPhoneNumber());
		if(!StringUtils.isNotBlank(userData.getIntentType())) {	//如果是修改，就不增加，不是修改才增加
			synchronzineData(userData.getBatchUuid(), sourceTable, userData.getOwnUser(), updateCount);
		}
		return updateCount;
    }

	@Override
	public void synchronzineData(String batchUuid, String depUuid, String userUuid, Integer count) {
		addIntent(batchUuid, batchUuid, depUuid, userUuid, -count);
	}
    
}
