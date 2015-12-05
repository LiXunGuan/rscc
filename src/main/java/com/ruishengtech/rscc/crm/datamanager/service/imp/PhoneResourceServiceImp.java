package com.ruishengtech.rscc.crm.datamanager.service.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.datamanager.service.PhoneResourceService;

/**
 * @author Frank
 *
 */
@Service
@Transactional
public class PhoneResourceServiceImp extends BaseService implements PhoneResourceService{

	/**
	 * 根据号码查询资源表中是否存在该对应数据
	 * @param phone
	 * @return true 存在 false不存在
	 */
	@Override
	public boolean getPhoneResourceByPhone(String phone) {

		if(StringUtils.isNotBlank(phone)){
			
			List<String> st = jdbcTemplate.queryForList("SELECT phone_number FROM `new_data_phone_resource` WHERE phone_number = ? ", String.class, phone);
			if(st.size() > 0)
				return true;
			
			return false;
		}
		
		return false;
	}

}
