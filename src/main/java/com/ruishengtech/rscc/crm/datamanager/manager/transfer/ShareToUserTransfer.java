package com.ruishengtech.rscc.crm.datamanager.manager.transfer;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.rscc.crm.datamanager.manager.TransferResult;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BatchNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.ShareNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.UserNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.ShareToUserData;

/**
 * Created by yaoliceng on 2015/8/18.
 */
@Component
@Transactional
public class ShareToUserTransfer extends AbstractTransfer<ShareNode, UserNode, ShareToUserData> {

	//
	@Override
	public TransferResult transfer(ShareNode fromNode, UserNode toNode, ShareToUserData transferData) {
		
    	final int count = cut(toNode.getTableName(), transferData.getBatchUuid(), transferData.getTransferUser(), transferData.getTransferData());
    	
    	TransferResult tr = new TransferResult();
    	tr.setTransferCount(count);
    	
    	return tr;
	}
	   
	//从共享池分配给人
    private int cut(String targetTable, String batchUuid, String userUuid, String dataUuid) {
    	
		//二级领用到人，插入数据	--new_data_department_user_部门ID
		String insertSql = "insert into " + UserNode.tablePrefix + targetTable 
				+ "(uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp,own_user,own_user_timestamp,"
				+ "call_count,last_call_result,last_call_time,intent_type,intent_timestamp) "
				+ "(select uuid,batch_uuid,phone_number,json,? own_department,NOW() own_department_timestamp,? own_user,NOW() own_user_timestamp,"
				+ "call_count,last_call_result,last_call_time,intent_type,intent_timestamp from " + ShareNode.tableName
				+ " where batch_uuid=? and uuid=? )";
		
		//删除数据 --new_data_global_share
		String deleteSql = "delete from " + ShareNode.tableName + " where batch_uuid=? and uuid=?";
		
		//更新数据，数据同步原则：保证两个表中选出来的内容一致。时间有可能不一致，在外部定义变量是最好的方法。 new_data_batch_批次ID
		String updateSql = "update " + BatchNode.tablePrefix + batchUuid + " set own_user=?,own_user_timestamp=NOW() "
				+ "where own_department=? and own_user is null and is_abandon != '1' "
				+ "and customer_uuid is null and is_blacklist != '1' and is_frozen != '1' and is_lock != '1' and uuid=?";
		int updateCount = jdbcTemplate.update(insertSql, ShareNode.name, userUuid, batchUuid, dataUuid);
		if (updateCount == 0) {
			return updateCount;
		}
		jdbcTemplate.update(deleteSql, batchUuid, dataUuid);
		jdbcTemplate.update(updateSql, userUuid, ShareNode.name, dataUuid);
		synchronzineData(batchUuid, targetTable, userUuid, updateCount);
		return updateCount;
    }
//    //从共享池分配给人
//    private int cut(String targetTable, String batchUuid, String userUuid, String dataUuid) {
//    	//二级领用到人，插入数据
//    	String insertSql = "insert into " + UserNode.tablePrefix + targetTable 
//    			+ "(uuid,batch_uuid,phone_number,json,own_department,own_department_timestamp,own_user,own_user_timestamp,"
//    			+ "call_count,last_call_result,last_call_time,intent_type,intent_timestamp) "
//    			+ "(select uuid,batch_uuid,phone_number,json,? own_department,NOW() own_department_timestamp,? own_user,NOW() own_user_timestamp,"
//    			+ "call_count,last_call_result,last_call_time,intent_type,intent_timestamp "
//    			+ "where batch_uuid=? order by uuid limit ? )";
//    	//删除数据
//    	String deleteSql = "delete from " + ShareNode.tableName + " where batch_uuid=? order by uuid limit ?";
//    	//更新数据，数据同步原则：保证两个表中选出来的内容一致。时间有可能不一致，在外部定义变量是最好的方法。
//    	String updateSql = "update " + BatchNode.tablePrefix + batchUuid + " set own_user=?,own_user_timestamp=NOW() "
//    			+ "where own_department=? and own_user is null and is_abandon != '1' "
//    			+ "and customer_uuid is null and is_blacklist != '1' and is_frozen != '1' and is_lock != '1' order by uuid limit ?";
//    	int updateCount = jdbcTemplate.update(insertSql, ShareNode.name, userUuid, batchUuid, num);
//    	if (updateCount == 0) {
//    		return updateCount;
//    	}
//    	jdbcTemplate.update(deleteSql, batchUuid, updateCount);
//    	jdbcTemplate.update(updateSql, userUuid, ShareNode.name, updateCount);
//    	synchronzineData(batchUuid, targetTable, userUuid, updateCount);
//    	return updateCount;
//    }
    
    //释放数据，从哪个部门哪个人释放哪个批次的数据
    private void release(String targetTable, String batchUuid, String userUuid) {
    	
    }

    //这边如果不判断移动过去的数据有多少个意向的话，会导致用户和部门统计意向数量错乱（部门的统计数据如果是统计本部门的人的话，是会错乱的
    //如果只是统计部门的数据，则不会错乱）
    
    //问题在于从这里拿的数据到底算不算到此人所在的部门呢，暂时先不算吧，因为获取标记的own_department就是全局共享池
	@Override
	public void synchronzineData(String batchUuid, String depUuid, String userUuid, Integer count) {
		addShare(batchUuid, batchUuid, depUuid, userUuid, -count);
		addData(null, null, null, userUuid, count);
	}
    
}
