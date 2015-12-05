package com.ruishengtech.rscc.crm.datamanager.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.datamanager.manager.DataManagers;
import com.ruishengtech.rscc.crm.datamanager.manager.TransferResult;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BatchNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.UserNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.BatchToUserData;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;
import com.ruishengtech.rscc.crm.datamanager.model.UserTask;

@Service
@Transactional
public class DepartmentDataServiceImp extends BaseService {
	
	@Autowired
	private DataManagers dataManager;
	
	@Autowired
	private DataBatchDepartmentLinkServiceImp dataBatchDepartmentLinkService;
	
	//分配数据到部门(条数)
	public int getData(String source, String[] target, int num) {
		//首先根据员工id得到部门id获取到部门员工数据表名new_data_department_user_deptid
		BatchNode from = new BatchNode(source);
		UserNode to = new UserNode();
		BatchToUserData data = new BatchToUserData();
		int count = 0;
		
		for (String s : target) {
			UserTask ut = getByUuid(UserTask.class, UUID.UUIDFromString(s));
			if(ut == null){
				return 0;
			}
			to.setTableName(ut.getDepartmentUuid());
			
			int todayLimit = ut.getDayLimit();
			data.setTransferUser(s);
			data.setTransferNum(num);
			TransferResult tr = dataManager.transfer(from, to, data);
			count += tr.getTransferCount();
		}
		return count;
	}
	
	public int getData(String source, String target, String[] datas) {
		//首先根据员工id得到部门id获取到部门员工数据表名new_data_department_user_deptid
		BatchNode from = new BatchNode(source);
		UserNode to = new UserNode();
		
		BatchToUserData data = new BatchToUserData();
		int count = 0;
		UserTask ut = getByUuid(UserTask.class, UUID.UUIDFromString(target));
		if(ut == null){
			return 0;
		}
		to.setTableName(ut.getDepartmentUuid());
		
		int todayLimit = ut.getDayLimit();
		data.setTransferUser(target);
		if(datas.length!=0)
		data.setDatas(datas);
		
		TransferResult tr = dataManager.transfer(from, to, data);
		count += tr.getTransferCount();
		return count;
		
	}
	
	private int getTodayData(String source, String target) {
		int total = 0;
		String selectSql = "select count(*) from " + DataBatchData.tableName + source + " where own_department=? and "
				+ "year(own_department_timestamp)=year(now()) and month(own_department_timestamp)=month(now()) and day(own_department_timestamp)=day(now())";
		total += jdbcTemplate.queryForObject(selectSql, Integer.class);
		return total;
	}
	
}
