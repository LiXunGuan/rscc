package com.ruishengtech.rscc.crm.datamanager.service;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.datamanager.model.DataGlobalShareRecord;

public interface DataGlobalShareRecordService {

	public PageResult<DataGlobalShareRecord> queryPage(ICondition condition);
	
}
