package com.ruishengtech.rscc.crm.datamanager.manager.transfer;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.rscc.crm.datamanager.manager.TransferResult;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BatchNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.UserNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToUserData;
import com.ruishengtech.rscc.crm.datamanager.model.UserData;

/**
 * Created by yaoliceng on 2015/8/18.
 */
@Component
@Transactional
//自己到自己，就是更新呼叫结果
public class UserToUserTransfer extends AbstractTransfer<UserNode, UserNode, UserToUserData> {

	@Override
	public TransferResult transfer(UserNode fromNode, UserNode toNode, UserToUserData transferData) {
		
    	int count = change(fromNode.getTableName(), transferData.getUserData());
    	
    	TransferResult tr = new TransferResult();
    	tr.setTransferCount(count);
    	
    	return tr;
	}
	   
	//普通呼叫一次，通过号码更新两个表即可
	//没有数据更新，所以不用sync
    private int change(String sourceTable, UserData userData) {
		//修改呼叫相关字段即可，其实号码是唯一的，后面的判断可以省略一部分，这样加过索引速度就快了
    	//注意数据库如果是null查询出来是空字符串还是null，如果是空字符串还需要转换为null
		String updateSql = "update " + UserNode.tablePrefix + sourceTable + 
				" set call_count=?,last_call_result=?,last_call_time=? where phone_number=?";
		String updateBatchSql = "update " + BatchNode.tablePrefix + userData.getBatchUuid() + 
				" set call_count=?,last_call_department=?,last_call_user=?,last_call_result=?,last_call_time=? "
				+ "where phone_number=?";
		int updateCount = jdbcTemplate.update(updateSql, userData.getCallCount(), userData.getLastCallResult(), userData.getLastCallTime(), userData.getPhoneNumber());
		if (updateCount == 0) {
			return updateCount;
		}
		jdbcTemplate.update(updateBatchSql, userData.getCallCount(), userData.getDeptUuid(), userData.getOwnUser(), userData.getLastCallResult(), 
				userData.getLastCallTime(), userData.getPhoneNumber());
		return updateCount;
    }

	@Override
	public void synchronzineData(String batchUuid, String depUuid, String userUuid, Integer count) {
		// TODO Auto-generated method stub
		
	}
    
}
