package com.ruishengtech.rscc.crm.datamanager.manager.transfer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.rscc.crm.datamanager.manager.TransferResult;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BatchNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.DepNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.UserNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToDepData;

/**
 * Created by yaoliceng on 2015/8/18.
 */
@Component
@Transactional
public class UserToDepTransfer extends AbstractTransfer<UserNode, DepNode, UserToDepData> {

	//0-未呼叫,1-未呼通,2-已呼通
	private String[] sqls = new String[]{"or call_count = 0 ", "or (call_count > 0 and (last_call_result is null OR last_call_result = '')) ", "or last_call_result = '1' "};
	
	@Override
	public TransferResult transfer(UserNode fromNode, DepNode toNode, UserToDepData transferData) {
		
		//回收数据时应该是不按数量，按条件的。回收意向，回收非意向，回收全部。这里先只回收非意向数据，意向的是会自动回收到共享池的，不去管。
    	int count;
    	if (transferData.getDatas() == null) {
//    		transferData.setTransferMod(0);
    		count = cut(fromNode.getTableName(), toNode.getTableName(), transferData.getBatchUuid(), transferData.getTransferMod());
    	} else {
    		count = cutByDatas(fromNode.getTableName(), toNode.getTableName(), transferData.getBatchUuid(), transferData.getDatas());
    	}
    	
    	TransferResult tr = new TransferResult();
    	tr.setTransferCount(count);
    	
    	return tr;
	}
	   
	//二级回收要选一个批次，source为回收人id，target为回收到的部门，batchUuid为回收某个批次的数据
    private int cut(String sourceTable, String targetTable, String batchUuid, Integer[] num) {
    	
    	String modSql = getModSql(num);
    	
    	//二级回收回部门，插入数据
		String insertSql = "insert into " + DepNode.tablePrefix + targetTable 
				+ "(uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp) "
				+ "(select uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp from "
				+ UserNode.tablePrefix + targetTable 
				+ " where own_department=? and own_user=? and batch_uuid=? and intent_type is null " + modSql + ")";
		//删除数据
		String deleteSql = "delete from " + UserNode.tablePrefix + targetTable + " where own_user=? and own_department=? and batch_uuid=? and intent_type is null " + modSql;
		//更新数据，数据同步原则：保证两个表中选出来的内容一致。时间有可能不一致，在外部定义变量是最好的方法。
		String updateSql = "update " + BatchNode.tablePrefix + batchUuid + " set own_user=null,own_user_timestamp=null "
				+ "where own_department=? and own_user=? and is_abandon != '1' and customer_uuid is null and is_blacklist != '1' "
				+ "and is_frozen != '1' and is_lock != '1' and intent_type is null " + modSql;
		int updateCount = jdbcTemplate.update(insertSql, targetTable, sourceTable, batchUuid);
		if (updateCount == 0) {
			return updateCount;
		}
		jdbcTemplate.update(deleteSql, sourceTable, targetTable, batchUuid);
		jdbcTemplate.update(updateSql, targetTable, sourceTable);
		synchronzineData(batchUuid, targetTable, sourceTable, updateCount);
		return updateCount;
    }
    
    private int cutByDatas(String sourceTable, String targetTable, String batchUuid, Collection<String> datas) {
    	
    	List<Object> selectParam = new ArrayList<>();
    	selectParam.add(batchUuid);
    	String selectSql = "select own_user,count(own_user) count from " + UserNode.tablePrefix + targetTable + " where batch_uuid=? and intent_type is null and " 
    			+ QueryUtils.inString("uuid", selectParam, datas) + " group by own_user";
    	List<Map<String, Object>> list = jdbcTemplate.queryForList(selectSql, selectParam.toArray());
    	
    	
    	//二级回收回部门，插入数据
    	List<Object> insertParam = new ArrayList<>();
 		List<Object> deleteParam = new ArrayList<>();
 		List<Object> updateParam = new ArrayList<>();
 		
 		insertParam.add(targetTable);
 		insertParam.add(batchUuid);
    	String insertSql = "insert into " + DepNode.tablePrefix + targetTable 
    			+ "(uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp) "
    			+ "(select uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp from "
    			+ UserNode.tablePrefix + targetTable 
    			+ " where own_department=? and batch_uuid=? and intent_type is null and " 
    			+ QueryUtils.inString("uuid ", insertParam, datas) + ")";
    	
    	//删除数据
    	deleteParam.add(targetTable);
    	deleteParam.add(batchUuid);
    	String deleteSql = "delete from " + UserNode.tablePrefix + targetTable + " where own_department=? and batch_uuid=? "
    			+ "and intent_type is null and " + QueryUtils.inString("uuid ", deleteParam, datas);
    	
    	
    	//更新数据，数据同步原则：保证两个表中选出来的内容一致。时间有可能不一致，在外部定义变量是最好的方法。
    	updateParam.add(targetTable);
    	String updateSql = "update " + BatchNode.tablePrefix + batchUuid + " set own_user=null,own_user_timestamp=null "
    			+ "where own_department=? and is_abandon != '1' and customer_uuid is null and is_blacklist != '1' "
    			+ "and is_frozen != '1' and is_lock != '1' and intent_type is null and " + QueryUtils.inString("uuid ", updateParam, datas);
    	int updateCount = jdbcTemplate.update(insertSql, insertParam.toArray());
    	if (updateCount == 0) {
			return updateCount;
		}
    	jdbcTemplate.update(deleteSql, deleteParam.toArray());
    	jdbcTemplate.update(updateSql, updateParam.toArray());
    	synchronzineData(batchUuid, targetTable, sourceTable, updateCount);
    	
    	for (Map<String, Object> m : list) {
			addData(null, null, null, m.get("own_user").toString(), -Integer.parseInt(m.get("count").toString()));
		}
    	
    	return updateCount;
    }
    
    
    
    private int cutByDatasOld(String sourceTable, String targetTable, String batchUuid, Collection<String> datas) {
    	//二级回收回部门，插入数据
    	List<Object> insertParam = new ArrayList<>();
 		List<Object> deleteParam = new ArrayList<>();
 		List<Object> updateParam = new ArrayList<>();
 		
 		insertParam.add(targetTable);
 		insertParam.add(sourceTable);
 		insertParam.add(batchUuid);
    	String insertSql = "insert into " + DepNode.tablePrefix + targetTable 
    			+ "(uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp) "
    			+ "(select uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp from "
    			+ UserNode.tablePrefix + targetTable 
    			+ "where own_department=? and own_user=? and batch_uuid=? and intent_type is null and " 
    			+ QueryUtils.inString("uuid ", insertParam, datas) + ")";
    	
    	//删除数据
    	deleteParam.add(targetTable);
    	deleteParam.add(sourceTable);
    	deleteParam.add(batchUuid);
    	String deleteSql = "delete from " + UserNode.tablePrefix + targetTable + " where own_user=? own_department=? and batch_uuid=? "
    			+ "and intent_type is null and " + QueryUtils.inString("uuid ", deleteParam, datas);
    	
    	
    	//更新数据，数据同步原则：保证两个表中选出来的内容一致。时间有可能不一致，在外部定义变量是最好的方法。
    	updateParam.add(targetTable);
    	updateParam.add(sourceTable);
    	String updateSql = "update " + BatchNode.tablePrefix + batchUuid + " set own_user=null,own_user_timestamp=null "
    			+ "where own_department=? and own_user=? and is_abandon != '1' and customer_uuid is null and is_blacklist != '1' "
    			+ "and is_frozen != '1' and is_lock != '1' and intent_type is null and " + QueryUtils.inString("uuid ", updateParam, datas);
    	int updateCount = jdbcTemplate.update(insertSql, insertParam.toArray());
    	if (updateCount == 0) {
			return updateCount;
		}
    	jdbcTemplate.update(deleteSql, deleteParam.toArray());
    	jdbcTemplate.update(updateSql, updateParam.toArray());
    	synchronzineData(batchUuid, targetTable, sourceTable, updateCount);
    	return updateCount;
    }
    
	@Override
	public void synchronzineData(String batchUuid, String depUuid, String userUuid, Integer count) {
		addData(null, null, null, userUuid, -count);
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
