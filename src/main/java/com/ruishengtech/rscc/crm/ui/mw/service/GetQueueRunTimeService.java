package com.ruishengtech.rscc.crm.ui.mw.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.ui.mw.model.RtAgentStatus;
import com.ruishengtech.rscc.crm.ui.mw.solution.RtAgentStatusSolution;

/**
 * 
 * @author chengxin
 *
 */
@Service
@Transactional
public class GetQueueRunTimeService extends BaseService{

	public PageResult<RtAgentStatus> queryPage(ICondition cond) {
		return super.queryPage(new RtAgentStatusSolution(), cond, RtAgentStatus.class);
	}
   
}
