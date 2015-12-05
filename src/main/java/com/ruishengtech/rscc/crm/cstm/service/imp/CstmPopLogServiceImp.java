package com.ruishengtech.rscc.crm.cstm.service.imp;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.cstm.condition.CstmPopLogCondition;
import com.ruishengtech.rscc.crm.cstm.model.CstmPopLog;
import com.ruishengtech.rscc.crm.cstm.solution.CstmPopLogSolution;

/**
 * @author Frank
 *
 */
@Service
@Transactional
public class CstmPopLogServiceImp extends BaseService implements CstmPopLogService{
	
	/**
	 * 分页查询数据
	 * @param condition
	 * @return
	 */
	@Override
	public PageResult<CstmPopLog> queryPage(CstmPopLogCondition condition) {

		return super.queryPage(new CstmPopLogSolution(), condition, CstmPopLog.class);
		
	}

	/**
	 * 保存数据
	 * @param cstmPopLog
	 */
	@Override
	public void save(CstmPopLog cstmPopLog) {

		super.save(cstmPopLog);
	}
	
}

	 
