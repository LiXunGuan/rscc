package com.ruishengtech.rscc.crm.ui.orderinfo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.framework.core.util.RandomUtil;

/**
 * 客户、客服公共的标准编号序列化实现类
 * 其他客户可实现自己的序列化类，重写里面的方法即可
 * @author Frank
 *
 */
@Service
@Transactional
public class OrderInfoSerialize extends BaseService implements OrderInfoSerializeService{
		
	public Integer getMin() {
		
		return Integer.valueOf(SpringPropertiesUtil.getProperty("sys.cstm.serialize.min"));
	
	}

	public Integer getCount() {
		
		return Integer.valueOf(SpringPropertiesUtil.getProperty("sys.cstm.serialize.count"));
	
	}

	public Integer getMax() {
		
		return Integer.valueOf(SpringPropertiesUtil.getProperty("sys.cstm.serialize.max"));
	
	}

	@Override
	public String getSerializeId() {
		int[] num = RandomUtil.randomCommon(getMin(), getMax(), getCount());
		
		int a = jdbcTemplate.queryForObject( " SELECT count(*) FROM  order_info  WHERE order_id = ? ", Integer.class, num[0]);
		
		if (a > 0) {
			return getSerializeId();
		}
		
		return String.valueOf(num[0]);
	}

	@Override
	public String getNewSerializeId() {
		
		int[] num = RandomUtil.randomCommon(getMin(), getMax(), getCount());
		
		int a = jdbcTemplate.queryForObject( " SELECT count(*) FROM  new_order_info  WHERE order_id = ? ", Integer.class, num[0]);
		
		if (a > 0) {
			return getNewSerializeId();
		}
		
		return String.valueOf(num[0]);
	}
}
