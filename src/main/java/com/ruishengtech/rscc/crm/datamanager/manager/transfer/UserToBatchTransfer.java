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
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToBatchData;

/**
 * Created by yaoliceng on 2015/8/18.
 */
@Component
@Transactional
public class UserToBatchTransfer extends AbstractTransfer<UserNode, BatchNode, UserToBatchData> {

	@Override
	public TransferResult transfer(UserNode fromNode, BatchNode toNode, UserToBatchData transferData) {
		
		int count = 0;
		if(transferData.getDatas() != null) {
			count = removeByDatas(fromNode.getTableName(), toNode.getTableName(), transferData.getTransferUser(), transferData.getDatas());
		}
		else if(transferData.getTransferNum() == null) {
			count = removeAll(fromNode.getTableName(), toNode.getTableName(), transferData.getTransferUser());
		} else {
//			count = remove(fromNode.getTableName(), toNode.getTableName(), transferData.getTransferNum());
		}
    	
    	TransferResult tr = new TransferResult();
    	tr.setTransferCount(count);
    	
    	return tr;
	}
	
//    private int remove(String sourceTable, String targetTable, Integer num) {
//		if(num == null || num <= 0)
//			return 0;
//		//从部门数据表一级回收，直接删除即可
//		String deleteSql = "delete from " + DepNode.tablePrefix + sourceTable + " where batch_uuid=? order by uuid limit ?";
//		//把数据插入该部门表
//		String updateSql = "update " + BatchNode.tablePrefix + targetTable + " set own_department=null,own_department_timestamp=null,"
//				+ "own_user=null,own_user_timestamp=null where own_department=? and own_user is null order by uuid limit ?";
//		int updateCount = jdbcTemplate.update(deleteSql, targetTable, num);
//		jdbcTemplate.update(updateSql, sourceTable, updateCount);
//		synchronzineData(sourceTable, targetTable);
//		return updateCount;
//    }
    
	//同样的只回收无意向客户
    private int removeAll(String sourceTable, String targetTable, String userUuid) {
    	String deleteSql = "delete from " + UserNode.tablePrefix + sourceTable + " where batch_uuid=? and own_department=? and "
    			+ " own_user=? and intent_type is null";
		//更新批次表
		String updateSql = "update " + BatchNode.tablePrefix + targetTable + " set own_user=null,own_user_timestamp=null"
				+ " where own_user=? and own_department=? and is_abandon != '1' "
				+ "and customer_uuid is null and is_blacklist != '1' and is_frozen != '1' and is_lock != '1' and intent_type is null";
		int updateCount = jdbcTemplate.update(deleteSql, targetTable, sourceTable, userUuid);
		if (updateCount == 0) {
			return updateCount;
		}
		jdbcTemplate.update(updateSql, userUuid, sourceTable);
		synchronzineData(targetTable, sourceTable, userUuid, updateCount);
		return updateCount;
    }
    
    private int removeByDatas(String sourceTable, String targetTable, String userUuid, Collection<String> datas) {
    	
 		List<Object> deleteParam = new ArrayList<>();
 		List<Object> updateParam = new ArrayList<>();
    	
 		deleteParam.add(targetTable);
 		deleteParam.add(sourceTable);
 		deleteParam.add(userUuid);
    	String deleteSql = "delete from " + UserNode.tablePrefix + sourceTable + " where batch_uuid=? and own_department=? and "
    			+ " own_user=? and intent_type is null and " + QueryUtils.inString("uuid ", deleteParam, datas);
    	
    	
		//更新批次表
    	updateParam.add(userUuid);
    	updateParam.add(sourceTable);
		String updateSql = "update " + BatchNode.tablePrefix + targetTable + " set own_user=null,own_user_timestamp=null"
				+ " where own_user=? and own_department=? and is_abandon != '1' "
				+ "and customer_uuid is null and is_blacklist != '1' and is_frozen != '1' and is_lock != '1' and intent_type is null and "
				+ QueryUtils.inString("uuid ", updateParam, datas);
		int updateCount = jdbcTemplate.update(deleteSql, deleteParam.toArray());
		if (updateCount == 0) {
			return updateCount;
		}
		jdbcTemplate.update(updateSql, updateParam.toArray());
		synchronzineData(targetTable, sourceTable, userUuid, updateCount);
		return updateCount;
    }
    
	@Override
	public void synchronzineData(String batchUuid, String depUuid, String userUuid, Integer count) {
		addData(null, batchUuid, depUuid, userUuid, -count);
		addOwn(batchUuid, null, null, -count);
	}

}
