package com.ruishengtech.rscc.crm.user.service;

import java.util.List;

import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.rscc.crm.user.model.DatarangeType;

public interface DatarangeTypeService {

	public List<DatarangeType> getTypes();

	public DatarangeType getByUuid(UUID uuid);

	public DatarangeType getDatarangeTypeByName(String datarangeName);

}