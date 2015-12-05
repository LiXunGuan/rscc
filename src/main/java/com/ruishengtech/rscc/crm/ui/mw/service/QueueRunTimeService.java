package com.ruishengtech.rscc.crm.ui.mw.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.ui.mw.model.RtQueueStatus;
import com.ruishengtech.rscc.crm.ui.mw.solution.RtQueueStatusSolution;

/**
 * 
 * @author chengxin
 *
 */
@Service
@Transactional
public class QueueRunTimeService extends BaseService{
	
	public PageResult<RtQueueStatus> queryPage(ICondition cond) {
		return super.queryPage(new RtQueueStatusSolution(), cond, RtQueueStatus.class);
	}
	
}
