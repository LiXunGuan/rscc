package com.ruishengtech.rscc.crm.ui.sysConfig.service.imp;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.ui.sysConfig.model.UserConfig;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.UserConfigService;

@Service
@Transactional
public class UserConfigServiceImp extends BaseService implements UserConfigService {

	public void save(UserConfig userConfig) {
		jdbcTemplate.update("replace into user_config(uuid, config) values(?,?)", userConfig.getUid(), userConfig.getConfig());
	}
	
	public UserConfig getByUuid(String uuid) {
		return super.getByUuid(UserConfig.class, UUID.UUIDFromString(uuid));
	}
	
}
