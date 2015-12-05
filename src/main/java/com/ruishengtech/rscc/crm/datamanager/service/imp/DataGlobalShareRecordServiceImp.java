package com.ruishengtech.rscc.crm.datamanager.service.imp;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.datamanager.model.DataGlobalShareRecord;
import com.ruishengtech.rscc.crm.datamanager.service.DataGlobalShareRecordService;
import com.ruishengtech.rscc.crm.datamanager.solution.DataGlobalShareRecordSolution;

@Service
@Transactional
public class DataGlobalShareRecordServiceImp extends BaseService implements DataGlobalShareRecordService{

	@Override
	public PageResult<DataGlobalShareRecord> queryPage(ICondition condition) {
		return super.queryPage(new DataGlobalShareRecordSolution(), condition, DataGlobalShareRecord.class);
	}
	
}
