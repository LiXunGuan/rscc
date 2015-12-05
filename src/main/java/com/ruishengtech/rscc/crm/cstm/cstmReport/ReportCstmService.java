package com.ruishengtech.rscc.crm.cstm.cstmReport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.service.BaseService;

/**
 * @author Frank
 *
 */
@Service
@Transactional
public class ReportCstmService extends BaseService{

	public PageResult<ReportCstm> queryPage(ReportCstmCondition cstmCondition) {
		
		return super.queryPage(new ReportCstmSolution(), cstmCondition, ReportCstm.class);
		
	}
}
