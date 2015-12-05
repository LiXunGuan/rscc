package com.ruishengtech.rscc.crm.cstm.service.imp;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.rscc.crm.cstm.condition.CstmPopLogCondition;
import com.ruishengtech.rscc.crm.cstm.model.CstmPopLog;

/**
 * @author Frank
 *
 */
public interface CstmPopLogService {

	/**
	 * 分页查询数据
	 * @param condition
	 * @return
	 */
	abstract PageResult<CstmPopLog> queryPage(CstmPopLogCondition condition);
	
	
	/**
	 * 保存数据
	 * @param cstmPopLog
	 */
	abstract void save(CstmPopLog cstmPopLog);
	
}
