package com.ruishengtech.rscc.crm.billing.service;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.billing.model.Billing;

public interface BillService {
	
	public PageResult<Billing> queryPage(ICondition condition);
}
