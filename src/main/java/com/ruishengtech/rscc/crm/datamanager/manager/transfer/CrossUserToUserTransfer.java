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
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.CrossUserToUserData;

/**
 * Created by yaoliceng on 2015/8/18.
 * 从一个人转移给另一个人，暂时只能同部门转
 */
@Component
@Transactional
public class CrossUserToUserTransfer extends AbstractTransfer<UserNode, UserNode, CrossUserToUserData> {

	@Override
	public TransferResult transfer(UserNode fromNode, UserNode toNode, CrossUserToUserData transferData) {
		int count;
		if(transferData.getDatas() == null) {
			count = 0;
		} else {
			count = cutByDatas(transferData.getUserDept(), fromNode.getTableName(), toNode.getTableName(), transferData.getBatchUuid(), transferData.getDatas());
		}
    	
    	TransferResult tr = new TransferResult();
    	tr.setTransferCount(count);
    	
    	return tr;
	}
    
 	private int cutByDatas(String depUuid, String sourceUser, String targetUser, String batchUuid, Collection<String> datas) {
 		//二级领用到人，插入数据
 		List<Object> updateUserParam = new ArrayList<>();
		List<Object> updateParam = new ArrayList<>();
		//插入数据
		updateUserParam.add(targetUser);
		updateUserParam.add(batchUuid);
		updateUserParam.add(sourceUser);
		String updateUserSql = "update " + UserNode.tablePrefix + depUuid + 
				" set own_user=? where batch_uuid=? and own_user=? and  "
				+ QueryUtils.inString("uuid ", updateUserParam, datas);
		//更新数据，数据同步原则：保证两个表中选出来的内容一致。时间有可能不一致，在外部定义变量是最好的方法。
		updateParam.add(targetUser);
		updateParam.add(depUuid);
		updateParam.add(sourceUser);
		String updateSql = "update " + BatchNode.tablePrefix + batchUuid + " set own_user=?,own_user_timestamp=NOW() "
				+ "where own_department=? and own_user=? and is_abandon != '1' "
				+ "and customer_uuid is null and is_blacklist != '1' and is_frozen != '1' and is_lock != '1' and " 
				+ QueryUtils.inString("uuid ", updateParam, datas);
		int updateCount = jdbcTemplate.update(updateUserSql, updateUserParam.toArray());
		if (updateCount == 0) {
			return updateCount;
		}
		jdbcTemplate.update(updateSql, updateParam.toArray());
		synchronzineData(batchUuid, depUuid, sourceUser, -updateCount);
		synchronzineData(batchUuid, depUuid, targetUser, updateCount);
		return updateCount;
 	}

 	//这里需要添加数据量和意向客户量
	@Override
	public void synchronzineData(String batchUuid, String depUuid, String userUuid, Integer count) {
		addData(null, null, null, userUuid, count);
		addIntent(null, null, null, userUuid, count);
	}

}
