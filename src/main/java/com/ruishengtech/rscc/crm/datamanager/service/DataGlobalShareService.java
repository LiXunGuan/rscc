package com.ruishengtech.rscc.crm.datamanager.service;

import java.util.List;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.model.DataGlobalShare;
import com.ruishengtech.rscc.crm.datamanager.model.DataGlobalShareRecord;

public interface DataGlobalShareService {

	public PageResult<DataGlobalShare> queryPage(ICondition condition);
	
	public PageResult<DataGlobalShare> queryPagedata(ICondition condition);
	
	public List<DataGlobalShare> getAllDataGlobalShare();
	
	public void updateGlobalShareData(DataGlobalShare dgs);
	
	public void saveGlobalShareRecord(DataGlobalShareRecord dg);
	
	public List<DataGlobalShareRecord> getGlobalShareRecordByUserUuid(String useruuid, String marksave);
	
	public Integer getGlobalShareRecordByUserUuid(String useruuid);
	
	public List<DataGlobalShare> getGlobalShareByNumLimit(Integer numlimit, String userUuid);
	
	public Integer getGlobalShareByOwnUserUuid(String useruuid);
	
	public int getGlobalShare(String department, String useruuid, Integer numlimit);

	void changeGlobalShareStat(String dataUuid, boolean stat);
	
}
