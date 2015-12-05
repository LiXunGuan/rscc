package com.ruishengtech.rscc.crm.ui.mw.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.ui.mw.model.Members;
import com.ruishengtech.rscc.crm.ui.mw.solution.MembersSolution;

/**
 * 
 * @author chengxin
 *
 */
@Service
@Transactional
public class GetMembersService extends BaseService{

	public PageResult<Members> queryPage(ICondition cond) {
		return super.queryPage(new MembersSolution(), cond, Members.class);
	}

}
