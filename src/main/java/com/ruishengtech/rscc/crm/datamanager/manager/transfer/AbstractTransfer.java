package com.ruishengtech.rscc.crm.datamanager.manager.transfer;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.datamanager.manager.TransferResult;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatch;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchDepartmentLink;
import com.ruishengtech.rscc.crm.datamanager.model.DepartmentData;
import com.ruishengtech.rscc.crm.datamanager.model.DepartmentTable;
import com.ruishengtech.rscc.crm.datamanager.model.UserData;
import com.ruishengtech.rscc.crm.datamanager.model.UserTask;

/**
 * Created by yaoliceng on 2015/8/18.
 */
public abstract class AbstractTransfer <T,V,Z> extends BaseService {

	public abstract TransferResult transfer(T fromNode, V toNode, Z transferData);
	
    public abstract void synchronzineData(String batchUuid, String depUuid, String userUuid, Integer count);
    
    //更新数据量，暂不考虑为批次追加数据，为部门追加数据时，要有批次id和部门id
    protected void addData(String batchUuid, String linkBatchUuid, String depUuid, String userUuid, Integer count) {
    	String updateSql;
    	if(batchUuid != null) {
    		updateSql = "update " + DataBatch.tableName + " set data_count=data_count+? where uuid=?"; 
    		jdbcTemplate.update(updateSql, count, batchUuid);
    	}
    	if(linkBatchUuid != null && StringUtils.isNotBlank(depUuid)) {
    		updateSql = "update " + DataBatchDepartmentLink.tableName + " set data_count=data_count+? where data_batch_uuid=? and department_uuid=?"; 
    		jdbcTemplate.update(updateSql, count, linkBatchUuid, depUuid);
    	}
    	if(StringUtils.isNotBlank(userUuid)) {
    		updateSql = "update " + UserTask.tableName + " set data_count=data_count+? where uuid=?"; 
    		jdbcTemplate.update(updateSql, count, userUuid);
    	}
    }
    
    protected void addOwn(String batchUuid, String linkBatchUuid, String depUuid, Integer count) {
    	String updateSql;
    	if(batchUuid != null) {
    		updateSql = "update " + DataBatch.tableName + " set own_count=own_count+? where uuid=?"; 
    		jdbcTemplate.update(updateSql, count, batchUuid);
    	}
    	if(linkBatchUuid != null && StringUtils.isNotBlank(depUuid)) {
    		updateSql = "update " + DataBatchDepartmentLink.tableName + " set own_count=own_count+? where data_batch_uuid=? and department_uuid=?"; 
    		jdbcTemplate.update(updateSql, count, linkBatchUuid, depUuid);
    	}
    }
    
    protected void addIntent(String batchUuid, String linkBatchUuid, String depUuid, String userUuid, Integer count) {
    	String updateSql;
    	if(batchUuid != null) {
    		updateSql = "update " + DataBatch.tableName + " set intent_count=intent_count+? where uuid=?"; 
    		jdbcTemplate.update(updateSql, count, batchUuid);
    	}
    	if(linkBatchUuid != null && StringUtils.isNotBlank(depUuid)) {
    		updateSql = "update " + DataBatchDepartmentLink.tableName + " set intent_count=intent_count+? where data_batch_uuid=? and department_uuid=?"; 
    		jdbcTemplate.update(updateSql, count, linkBatchUuid, depUuid);
    	}
    	if(StringUtils.isNotBlank(userUuid)) {
    		updateSql = "update " + UserTask.tableName + " set intent_count=intent_count+? where uuid=?"; 
    		jdbcTemplate.update(updateSql, count, userUuid);
    	}
    }
    
    //冻结的只用管批次表
    protected void addFrozen(String batchUuid, Integer count) {
    	String updateSql;
    	if(batchUuid != null) {
    		updateSql = "update " + DataBatch.tableName + " set own_count=own_count+? where uuid=?"; 
    		jdbcTemplate.update(updateSql, count, batchUuid);
    	}
    }
    
    protected void addBlackList(String batchUuid, String linkBatchUuid, String depUuid, String userUuid, Integer count) {
    	String updateSql;
    	if(batchUuid != null) {
    		updateSql = "update " + DataBatch.tableName + " set blacklist_count=blacklist_count+? where uuid=?"; 
    		jdbcTemplate.update(updateSql, count, batchUuid);
    	}
    	if(linkBatchUuid != null && StringUtils.isNotBlank(depUuid)) {
    		updateSql = "update " + DataBatchDepartmentLink.tableName + " set blacklist_count=blacklist_count+? where data_batch_uuid=? and department_uuid=?"; 
    		jdbcTemplate.update(updateSql, count, linkBatchUuid, depUuid);
    	}
    	if(StringUtils.isNotBlank(userUuid)) {
	    	updateSql = "update " + UserTask.tableName + " set blacklist_count=blacklist_count+? where uuid=?"; 
	    	jdbcTemplate.update(updateSql, count, userUuid);
    	}
    }
    
    protected void addCustomer(String batchUuid, String linkBatchUuid, String depUuid, String userUuid, Integer count) {
    	String updateSql;
    	if(batchUuid != null) {
    		updateSql = "update " + DataBatch.tableName + " set customer_count=customer_count+? where uuid=?"; 
    		jdbcTemplate.update(updateSql, count, batchUuid);
    	}
    	if(linkBatchUuid != null && StringUtils.isNotBlank(depUuid)) {
    		updateSql = "update " + DataBatchDepartmentLink.tableName + " set customer_count=customer_count+? where data_batch_uuid=? and department_uuid=?"; 
    		jdbcTemplate.update(updateSql, count, linkBatchUuid, depUuid);
    	}
    	if(StringUtils.isNotBlank(userUuid)) {
    		updateSql = "update " + UserTask.tableName + " set customer_count=customer_count+? where uuid=?"; 
    		jdbcTemplate.update(updateSql, count, userUuid);
    	}
    }

    
    protected void addAbandon(String batchUuid, String linkBatchUuid, String depUuid, String userUuid, Integer count) {
    	String updateSql;
    	if(batchUuid != null) {
    		updateSql = "update " + DataBatch.tableName + " set abandon_count=abandon_count+? where uuid=?"; 
    		jdbcTemplate.update(updateSql, count, batchUuid);
    	}
    	if(linkBatchUuid != null && StringUtils.isNotBlank(depUuid)) {
    		updateSql = "update " + DataBatchDepartmentLink.tableName + " set abandon_count=abandon_count+? where data_batch_uuid=? and department_uuid=?"; 
    		jdbcTemplate.update(updateSql, count, linkBatchUuid, depUuid);
    	}
    	if(StringUtils.isNotBlank(userUuid)) {
    		updateSql = "update " + UserTask.tableName + " set abandon_count=abandon_count+? where uuid=?"; 
    		jdbcTemplate.update(updateSql, count, userUuid);
    	}
    }
    
    protected void addShare(String batchUuid, String linkBatchUuid, String depUuid, String userUuid, Integer count) {
    	String updateSql;
    	if(batchUuid != null) {
    		updateSql = "update " + DataBatch.tableName + " set share_count=share_count+? where uuid=?"; 
    		jdbcTemplate.update(updateSql, count, batchUuid);
    	}
    	if(linkBatchUuid != null && StringUtils.isNotBlank(depUuid)) {
    		updateSql = "update " + DepartmentTable.tableName + " set share_count=share_count+? where uuid=?"; 
    		jdbcTemplate.update(updateSql, count, depUuid);
    	}
    	if(StringUtils.isNotBlank(userUuid)) {
    		updateSql = "update " + UserTask.tableName + " set share_count=share_count+? where uuid=?"; 
    		jdbcTemplate.update(updateSql, count, userUuid);
    	}
    }
    
    
    //一下三个方法暂时没有使用

    //根据数据表的情况，更新统计表的六个字段
    protected void synchronizeDataBatch(String tableName) {
    	
    	String statisticsSql = "SELECT SUM( CASE WHEN (own_department is not null or own_user is not null) THEN 1 ELSE 0 END ) own_count, "
    			+ "SUM( CASE WHEN intent_type is not null THEN 1 ELSE 0 END ) intent_count, "
    			+ "SUM( CASE WHEN is_abandon = '1' THEN 1 ELSE 0 END ) abandon_count, "
    			+ "SUM( CASE WHEN is_blacklist = '1' THEN 1 ELSE 0 END ) blacklist_count, "
    			+ "SUM( CASE WHEN is_frozen = '1' THEN 1 ELSE 0 END ) frozen_count, "
    			+ "SUM( CASE WHEN customer_uuid is not null THEN 1 ELSE 0 END ) customer_count from " + DataBatchData.tableName + tableName;
    	Map<String, Object> map = jdbcTemplate.queryForMap(statisticsSql);
    	String updateSourceSql = "update " + DataBatch.tableName + " set own_count=?,intent_count=?,customer_count=?,"
    			+ "frozen_count=?,abandon_count=?,blacklist_count=? where data_table = ?";
    	jdbcTemplate.update(updateSourceSql, map.get("own_count"),map.get("intent_count"),map.get("customer_count"),map.get("frozen_count")
    			,map.get("abandon_count"),map.get("blacklist_count"),tableName);
    }
    
    //根据人员表和部门数据表的情况，更新统计表的三个字段，customer_count、abandon_count和blacklist_count根据现有情况无法统计，只能通过一次加1来实现
    protected void synchronizeDepartmentTable(String tableName) {
    	
    	String statisticsSql = "SELECT SUM( CASE WHEN own_department =? THEN 1 ELSE 0 END) own_count, "
    			+ "SUM( CASE WHEN intent_type is not null THEN 1 ELSE 0 END) intent_count from " + UserData.tableName + tableName; 
    	Map<String, Object> map = jdbcTemplate.queryForMap(statisticsSql, tableName);
    	statisticsSql = "SELECT count(*) from " + DepartmentData.tableName + tableName;
    	int tableCount = jdbcTemplate.queryForObject(statisticsSql, Integer.class);
    	String updateSql = "update " + DepartmentTable.tableName + " set data_count=?,own_count=?,intent_count=? where data_table = ?";
    	jdbcTemplate.update(updateSql, tableCount + Integer.parseInt((String)map.get("own_count")), 
    			Integer.parseInt((String)map.get("own_count")),map.get("intent_count"));
    }
    
    //只统计一个用户的两个字段，customer_count、abandon_count和blacklist_count根据现有情况无法统计，只能通过一次加1来实现
    protected void synchronizeUserTask(String tableName, String userUuid) {
    	String statisticsSql = "SELECT count(*) data_count,SUM( CASE WHEN intent_type is not null THEN 1 ELSE 0 END) intent_count from " 
				+ UserData.tableName + tableName + " where own_user=?"; 
    	Map<String, Object> map = jdbcTemplate.queryForMap(statisticsSql, userUuid);
    	String updateSql = "update " + UserTask.tableName + " set data_count=?,intent_count=? where uuid = ?";
    	jdbcTemplate.update(updateSql, Integer.parseInt((String)map.get("data_count")), 
    			Integer.parseInt((String)map.get("intent_count")), userUuid);
    }
    
}
