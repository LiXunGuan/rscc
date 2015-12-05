package com.ruishengtech.rscc.crm.datamanager.manager.transfer;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.rscc.crm.datamanager.manager.TransferResult;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BatchNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.DepNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.UserNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.CrossUserToDepData;

/**
 * Created by yaoliceng on 2015/8/18.
 */
@Component
@Transactional
public class CrossUserToDepTransfer extends AbstractTransfer<UserNode, DepNode, CrossUserToDepData> {

	//0-未呼叫,1-未呼通,2-已呼通
	private String[] sqls = new String[]{"or call_count = 0 ", "or (call_count > 0 and last_call_result is null) ", "or last_call_result = '1' "};
	
	@Override
	public TransferResult transfer(UserNode fromNode, DepNode toNode, CrossUserToDepData transferData) {
		
    	int count = cut(fromNode.getTableName(), transferData.getUserDept(), toNode.getTableName(), transferData.getBatchUuid(), transferData.getTransferMod());
    	
    	TransferResult tr = new TransferResult();
    	tr.setTransferCount(count);
    	
    	return tr;
	}
	
	//二级回收要选一个批次，userUuid为回收人员的UUID，deptUuid为将要回收到的部门，userDept为回收人员的部门UUID，batchUuid为回收某个批次的数据
    private int cut(String userUuid, String userDept, String deptUuid, String batchUuid, Integer[] num) {
    	
    	String modSql = getModSql(num);
    	
    	// 二级回收回部门，插入数据 
    	// 插入new_data_department_部门id;增加部门内数据量
		String insertSql = "insert into " + DepNode.tablePrefix + deptUuid 
				+ "(uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp) "
				+ "(select uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp from "
				+ UserNode.tablePrefix + userDept 
				+ " where own_department=? and own_user=? and batch_uuid=? and intent_type is null " + modSql + ")";
		
		// 删除数据
		// (删除部门人员数据new_data_department_user_部门id;并且不是意向客户)
		String deleteSql = "delete from " + UserNode.tablePrefix + userDept + " where own_user=? and own_department=? and batch_uuid=? and intent_type is null " + modSql;
		
		// 更新数据，数据同步原则：保证两个表中选出来的内容一致。时间有可能不一致，在外部定义变量是最好的方法。
		// ===更新修改批次表中的数据
		String updateSql = "update " + BatchNode.tablePrefix + batchUuid + " set own_user=null,own_user_timestamp=null "
				+ "where own_department=? and own_user=? and is_abandon != '1' and customer_uuid is null and is_blacklist != '1' "
				+ "and is_frozen != '1' and is_lock != '1' and intent_type is null " + modSql;
		
		// 更新new_data_department_部门UUID中的数据
		String updateDeptName = " UPDATE " + DepNode.tablePrefix + deptUuid + " SET own_department = ? WHERE own_department = ?  " ;
		
		String updateBatchDeptName = " UPDATE " + BatchNode.tablePrefix + batchUuid + " SET own_department = ? WHERE own_department = ? " ;
		
		int updateCount = jdbcTemplate.update(insertSql, userDept, userUuid, batchUuid);
		if (updateCount == 0) {
			return updateCount;
		}
		
		jdbcTemplate.update(deleteSql, userUuid, userDept, batchUuid);
		jdbcTemplate.update(updateSql, userDept, userUuid);
		
		jdbcTemplate.update(updateDeptName, deptUuid, userDept);
		jdbcTemplate.update(updateBatchDeptName, deptUuid, userDept);
		
		synchronzineData(batchUuid, userUuid, userDept, deptUuid, updateCount);
		
		return updateCount;
    }
    
	@Override
	public void synchronzineData(String batchUuid, String depUuid, String userUuid, Integer count) {
	
	}
	
	// 回收子部门时数据量的同步
    private void synchronzineData(String batchUuid, String userUuid, String depUuid, String newDeptUuid, int count) {
    	//增加当前部门的数据量
		addData(null, batchUuid, newDeptUuid, null, count);
		//为选择的用户的部门减少数据量且为选择的用户减少数据量
		addData(null, batchUuid, depUuid, userUuid, -count);
		//为悬着用户的部门减少被占用数据量
		addOwn(null, batchUuid, depUuid, -count);
	}
	
	private String getModSql(Integer[] mods) {
		StringBuilder modSql = new StringBuilder("");
		String result = "";
		if (mods != null) {
			for (Integer mod : mods) {
				modSql.append(sqls[mod]);
			}
			result = " and " + "(" + modSql.substring(2) + ")";
		}
		return result;
	}

}
