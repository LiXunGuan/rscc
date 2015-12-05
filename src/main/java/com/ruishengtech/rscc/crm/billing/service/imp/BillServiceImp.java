package com.ruishengtech.rscc.crm.billing.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.billing.model.Billing;
import com.ruishengtech.rscc.crm.billing.solution.BillingSolution;

@Service
@Transactional
//表用id，保存时没问题
//更新时没问题
//删除时，有重载方法
public class BillServiceImp extends BaseService {
	
	public PageResult<Billing> queryPage(ICondition condition) {
		return super.queryPage(new BillingSolution(), condition, Billing.class);
	}
	
	public Map<String, Object> queryResult(ICondition condition) {
		BillingSolution b = new BillingSolution();
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<>();
		b.getStatisticsSql(condition, sb, params);
		Map<String, Object> map = jdbcTemplate.queryForMap(sb.toString(), params.toArray());
		return map;
	}
	
}
