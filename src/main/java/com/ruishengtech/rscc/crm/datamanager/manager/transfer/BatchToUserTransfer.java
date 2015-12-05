package com.ruishengtech.rscc.crm.datamanager.manager.transfer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.rscc.crm.datamanager.manager.TransferResult;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BatchNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.UserNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.BatchToUserData;

/**
 * Created by yaoliceng on 2015/8/18.
 */
@Component
@Transactional
public class BatchToUserTransfer extends AbstractTransfer<BatchNode, UserNode, BatchToUserData> {

	@Override
	public TransferResult transfer(BatchNode fromNode, UserNode toNode, BatchToUserData transferData) {
		
    	int count;
    	
    	if (transferData.getDatas() == null) {
    		count = copy(fromNode.getTableName(), toNode.getTableName(), transferData.getTransferUser(), transferData.getTransferNum());
    	} else {
    		count = copyByDatas(fromNode.getTableName(), toNode.getTableName(), transferData.getTransferUser(), transferData.getDatas());
    	}
    	
    	TransferResult tr = new TransferResult();
    	tr.setTransferCount(count);
    	
    	return tr;
	}
	   
	//直接分配给人,从哪个批次到哪个部门的哪个人
    private int copy(String sourceTable, String targetTable, String userUuid, int num) {
		if(num <= 0)
			return 0;
		//更新批次表，分配给人
		String updateSql = "update " + BatchNode.tablePrefix + sourceTable + " set own_department=?,own_department_timestamp=NOW(),"
				+ "own_user = ?,own_user_timestamp = NOW() "
				+ "where own_department is null and own_user is null and is_abandon != '1' "
				+ "and is_blacklist != '1' and is_frozen != '1' and customer_uuid is null and is_lock != '1' limit ?";
//				+ "where own_user is null and own_department is null and is_abandon is null and is_blacklist is null and is_frozen is null "
//				+ "and is_lock != '1' and customer_uuid is null limit ?";
		//更新人的表
		String insertSql = "insert into " + UserNode.tablePrefix + targetTable
				+ "(uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp,own_user,own_user_timestamp) "
				+ "(select uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp,own_user,own_user_timestamp from " 
				+ BatchNode.tablePrefix + sourceTable
				+ " where own_department=? and own_user=? and is_abandon != '1' "
				+ "and is_blacklist != '1' and is_frozen != '1' and customer_uuid is null and is_lock != '1' "
				+ "order by own_user_timestamp desc limit ? )";
		int updateCount = jdbcTemplate.update(updateSql, targetTable, userUuid, num);
		if (updateCount == 0) {
			return updateCount;
		}
		jdbcTemplate.update(insertSql, targetTable, userUuid, updateCount);
		synchronzineData(sourceTable, targetTable, userUuid, updateCount);
		return updateCount;
    }
    
    private int copyByDatas(String sourceTable, String targetTable, String userUuid, Collection<String> datas) {
		//更新批次表，分配给人
		
		List<Object> updateParam = new ArrayList<>();
		List<Object> insertParam = new ArrayList<>();
		
		updateParam.add(targetTable);
		updateParam.add(userUuid);
		String updateSql = "update " + BatchNode.tablePrefix + sourceTable + " set own_department=?,own_department_timestamp=NOW(),"
				+ "own_user = ?,own_user_timestamp = NOW() "
				+ "where own_department is null and own_user is null and is_abandon != '1' "
				+ "and is_blacklist != '1' and is_frozen != '1' and customer_uuid is null and is_lock != '1' and "
				+ QueryUtils.inString("uuid ", updateParam, datas);
		
		insertParam.add(targetTable);
		insertParam.add(userUuid);
		String insertSql = "insert into " + UserNode.tablePrefix + targetTable
				+ "(uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp,own_user,own_user_timestamp) "
				+ "(select uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp,own_user,own_user_timestamp from " 
				+ BatchNode.tablePrefix + sourceTable
				+ " where own_department=? and own_user=? and is_abandon != '1' "
				+ "and is_blacklist != '1' and is_frozen != '1' and customer_uuid is null and is_lock != '1' and "
				+ QueryUtils.inString("uuid ", insertParam, datas) + " )";
		int updateCount = jdbcTemplate.update(updateSql, updateParam.toArray());
		if (updateCount == 0) {
			return updateCount;
		}
		jdbcTemplate.update(insertSql, insertParam.toArray());
		synchronzineData(sourceTable, targetTable, userUuid, updateCount);
		return updateCount;
    }
    
	@Override
	public void synchronzineData(String batchUuid, String depUuid, String userUuid, Integer count) {
		addOwn(batchUuid, batchUuid, depUuid, count);
		addData(null, batchUuid, depUuid, userUuid, count);
	}

}
