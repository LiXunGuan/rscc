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
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.BatchToDepData;

/**
 * Created by yaoliceng on 2015/8/18.
 */
@Component
@Transactional
public class BatchToDepTransfer extends AbstractTransfer<BatchNode, DepNode, BatchToDepData> {

	@Override
	public TransferResult transfer(BatchNode fromNode, DepNode toNode, BatchToDepData transferData) {
		int count;
		
		if(transferData.getDatas() == null) {
			count = copy(fromNode.getTableName(), toNode.getTableName(), transferData.getTransferNum());
		} else {
			count = copyByDatas(fromNode.getTableName(), toNode.getTableName(), transferData.getDatas());
		}
    	
    	TransferResult tr = new TransferResult();
    	tr.setTransferCount(count);
    	
    	return tr;
	}

	//这里可能会有点问题，注意插入和更新的一定要一致
	private int copy(String sourceTable, String targetTable, int num) {
		if(num <= 0)
			return 0;
		//更新批次表，一级领用给部门--这里的intent_type is null有待考究，回收回来时是否清空intent_type？即own_department和own_user为空时，intent_type也为空
		//如果不清空的话，分配后可能会导致数据不同步，所以应该是要清空的，即intent_type is null和前边条件重复了，可以不要
		String updateSql = "update " + BatchNode.tablePrefix + sourceTable + " set own_department = ?,own_department_timestamp = NOW() "
				+ "where own_department is null and own_user is null and is_abandon != '1' "
				+ "and customer_uuid is null and is_blacklist != '1' and is_frozen != '1' and is_lock != '1' limit ?";
		//把数据插入该部门表
		String insertSql = "insert into " + DepNode.tablePrefix + targetTable 
				+ "(uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp) "
				+ "(select uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp from " + BatchNode.tablePrefix + sourceTable
				+ " where own_department = ? and own_user is null and customer_uuid is null and is_abandon != '1' "
				+ "and is_blacklist != '1' and is_frozen != '1' and is_lock != '1' " 
				+ "order by own_department_timestamp desc limit ? )";
		int updateCount = jdbcTemplate.update(updateSql, targetTable, num);
		if (updateCount == 0) {
			return updateCount;
		}
		jdbcTemplate.update(insertSql, targetTable, updateCount);
		synchronzineData(sourceTable, targetTable, null, updateCount);
		return updateCount;
    }
	
	private int copyByDatas(String sourceTable, String targetTable, Collection<String> datas) {
		List<Object> updateParam = new ArrayList<>();
		List<Object> insertParam = new ArrayList<>();
		updateParam.add(targetTable);
		String updateSql = "update " + BatchNode.tablePrefix + sourceTable + " set own_department = ?,own_department_timestamp = NOW() "
				+ "where own_department is null and own_user is null and customer_uuid is null and is_abandon != '1' "
				+ "and is_blacklist != '1' and is_frozen != '1' and is_lock != '1' and "
				+ QueryUtils.inString("uuid", updateParam, datas);
		
		//把数据插入该部门表
		insertParam.add(targetTable);
		String insertSql = "insert into " + DepNode.tablePrefix + targetTable 
				+ "(uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp) "
				+ "(select uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp from " + BatchNode.tablePrefix + sourceTable
				+ " where own_department = ? and own_user is null and customer_uuid is null and is_abandon != '1' "
				+ "and is_blacklist != '1' and is_frozen != '1' and is_lock != '1' and " 
				+ QueryUtils.inString("uuid", insertParam, datas);
		int updateCount = jdbcTemplate.update(updateSql, updateParam.toArray());
		if (updateCount == 0) {
			return updateCount;
		}
		jdbcTemplate.update(insertSql, insertParam.toArray());
		synchronzineData(sourceTable, targetTable, null, updateCount);
		return updateCount;
	}
    
	@Override
	public void synchronzineData(String batchUuid, String depUuid, String userUuid, Integer count) {
		addData(null, batchUuid, depUuid, null, count);
		addOwn(batchUuid, null, null, count);
		
	}

}
