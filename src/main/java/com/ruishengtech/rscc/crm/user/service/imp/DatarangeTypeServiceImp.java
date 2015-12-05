package com.ruishengtech.rscc.crm.user.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.user.model.DatarangeType;
import com.ruishengtech.rscc.crm.user.service.DatarangeTypeService;
@Service
@Transactional
public class DatarangeTypeServiceImp extends BaseService implements DatarangeTypeService{
	
	public List<DatarangeType> getTypes() {
		List<DatarangeType> list = getBeanList(DatarangeType.class,"");
		return list;
	}

	public DatarangeType getByUuid(UUID uuid) {
		return super.getByUuid(DatarangeType.class, uuid);
	}
	
	public DatarangeType getDatarangeTypeByName(String datarangeName) {
		
		List<DatarangeType> list = getBeanList(DatarangeType.class, "and type_name = ?", datarangeName);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}

}
