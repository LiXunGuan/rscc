package com.ruishengtech.rscc.crm.datamanager.service.imp;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.datamanager.model.DeptToUserTransgerLog;
import com.ruishengtech.rscc.crm.datamanager.service.DeptToUserTransgerLogService;
@Service
@Transactional
public class DeptToUserTransgerLogServiceImp extends BaseService implements DeptToUserTransgerLogService {

	@Override
	public void save(DeptToUserTransgerLog deptToUserTransgerLog) {
		super.save(deptToUserTransgerLog);
	}

	@Override
	public void getLogsByDate(Date date) {
		
	}

	@Override
	public Integer getLogCount(Date date, String userUuid) {
		
		if(null != date && StringUtils.isNotBlank(userUuid)){
			
			String sql = "SELECT SUM(user_real_count) as user_real_count FROM `transfer_log` WHERE gotdate = ? AND user_uuid = ?";
			return jdbcTemplate.queryForObject(sql, Integer.class,date,userUuid);
		}
		
		return 0;
	}

}
