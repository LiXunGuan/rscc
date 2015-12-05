package com.ruishengtech.rscc.crm.ui.mw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.ui.mw.model.SysConfig;

/**
 * 
 * @author chengxin
 *
 */
@Service
@Transactional
public class SYSConfigService extends BaseService {

    /**
     * 根据名字找到对应values值
     * @param name
     * @return
     */
    @SuppressWarnings("unchecked")
    public SysConfig getSysConfig(final String name){
    	List<SysConfig> configls=queryBean(new BeanHandler(){
    		@Override
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append(" select * from "+ SpringPropertiesUtil.getProperty("sys.record.database") +"sys_config where name = ? ");
    			params.add(name);
    		}
    	},SysConfig.class);
    	if (configls.size()>0) {
			return configls.get(0);
		}
    	return null;
    }
    
}
