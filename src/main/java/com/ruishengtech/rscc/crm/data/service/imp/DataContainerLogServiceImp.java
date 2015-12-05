package com.ruishengtech.rscc.crm.data.service.imp;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.data.model.DataContainerLog;

@Service
@Transactional
public class DataContainerLogServiceImp extends BaseService {
	
	public void save(DataContainerLog d) {
		d.setOperateTime(new Date());
		super.save(d);
	}
	
}
