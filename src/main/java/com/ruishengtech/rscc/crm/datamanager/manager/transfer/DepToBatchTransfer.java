package com.ruishengtech.rscc.crm.datamanager.manager.transfer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.rscc.crm.datamanager.manager.TransferResult;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BatchNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.DepNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.DepToBatchData;

/**
 * Created by yaoliceng on 2015/8/18.
 */
@Component
@Transactional
public class DepToBatchTransfer extends AbstractTransfer<DepNode, BatchNode, DepToBatchData> {

	@Override
	public TransferResult transfer(DepNode fromNode, BatchNode toNode, DepToBatchData transferData) {
		
		int count = 0;
		if (transferData.getDatas() != null) {
			count = removeByDatas(fromNode.getTableName(), toNode.getTableName(), transferData.getDatas());
		} else if(transferData.getTransferNum() == null) {
			count = removeAll(fromNode.getTableName(), toNode.getTableName());
		} else {
			count = remove(fromNode.getTableName(), toNode.getTableName(), transferData.getTransferNum());
		}
    	
    	TransferResult tr = new TransferResult();
    	tr.setTransferCount(count);
    	
    	return tr;
	}
	   
    private int remove(String sourceTable, String targetTable, Integer num) {
		if(num == null || num <= 0)
			return 0;
		//一定要保证两个选出来的是一样的
		//从部门数据表一级回收，直接删除即可
		String deleteSql = "delete from " + DepNode.tablePrefix + sourceTable + " where batch_uuid=? and is_lock !='1' order by uuid limit ?";
		//把数据插入该部门表
		String updateSql = "update " + BatchNode.tablePrefix + targetTable + " set own_department=null,own_department_timestamp=null,"
				+ "own_user=null,own_user_timestamp=null where own_department=? and own_user is null and is_abandon != '1' "
				+ "and customer_uuid is null and is_blacklist != '1' and is_frozen != '1' and is_lock != '1' order by uuid limit ?";
		int updateCount = jdbcTemplate.update(deleteSql, targetTable, num);
		if (updateCount == 0) {
			return updateCount;
		}
		jdbcTemplate.update(updateSql, sourceTable, updateCount);
		synchronzineData(targetTable, sourceTable, null, updateCount);
		return updateCount;
    }
    
    private int removeAll(String sourceTable, String targetTable) {
    	String deleteSql = "delete from " + DepNode.tablePrefix + sourceTable + " where batch_uuid=? and is_lock !='1' ";
		//把数据插入该部门表
		String updateSql = "update " + BatchNode.tablePrefix + targetTable + " set own_department=null,own_department_timestamp=null,"
				+ "own_user=null,own_user_timestamp=null where own_department=? and own_user is null and is_abandon != '1' "
				+ "and customer_uuid is null and is_blacklist != '1' and is_frozen != '1' and is_lock != '1'";
		int updateCount = jdbcTemplate.update(deleteSql, targetTable);
		if (updateCount == 0) {
			return updateCount;
		}
		jdbcTemplate.update(updateSql, sourceTable);
		synchronzineData(targetTable, sourceTable, null, updateCount);
		return updateCount;
    }
    
    private int removeByDatas(String sourceTable, String targetTable, Collection<String> datas) {
    	
    	List<Object> deleteParam = new ArrayList<>();
 		List<Object> updateParam = new ArrayList<>();
 		
 		deleteParam.add(targetTable);
    	String deleteSql = "delete from " + DepNode.tablePrefix + sourceTable + " where batch_uuid=? and is_lock !='1' and " 
    			+ QueryUtils.inString("uuid ", deleteParam, datas);
    	
		//把数据插入该部门表
    	updateParam.add(sourceTable);
		String updateSql = "update " + BatchNode.tablePrefix + targetTable + " set own_department=null,own_department_timestamp=null,"
				+ "own_user=null,own_user_timestamp=null where own_department=? and own_user is null and is_abandon != '1' "
				+ "and customer_uuid is null and is_blacklist != '1' and is_frozen != '1' and is_lock != '1' and "
				+ QueryUtils.inString("uuid ", updateParam, datas);
		int updateCount = jdbcTemplate.update(deleteSql, deleteParam.toArray());
		if (updateCount == 0) {
			return updateCount;
		}
		jdbcTemplate.update(updateSql, updateParam.toArray());
		synchronzineData(targetTable, sourceTable, null, updateCount);
		return updateCount;
    }
    
	@Override
	public void synchronzineData(String batchUuid, String depUuid, String userUuid, Integer count) {
		addData(null, batchUuid, depUuid, null, -count);
		addOwn(batchUuid, null, null, -count);
	}

}
