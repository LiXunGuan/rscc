package com.ruishengtech.rscc.crm.ui.sysConfig.service.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.ui.sysConfig.SysConfigManager;
import com.ruishengtech.rscc.crm.ui.sysConfig.model.SysConfig;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;

@Service
@Transactional
public class SysConfigServiceImp extends BaseService implements SysConfigService{

	@Override
	public SysConfig getSysConfigByKey(String key) {
		return SysConfigManager.getInstance().getDataMap().get(key);
	}

	@Override
	public void saveOrUpdate(SysConfig sys) {
		if(sys == null)
			return;
		if(StringUtils.isBlank(sys.getUid())){
			super.save(sys);
		}else{
			sys.setUuid(UUID.UUIDFromString(sys.getUid()));
			super.update(sys);
		}
		
		SysConfigManager.getInstance().init();
	}

	@Override
	public SysConfig getSysConfigByUid(String uid) {
		return super.getByUuid(SysConfig.class, UUID.UUIDFromString(uid));
	}

	@Override
	public void updatSysConfig(String val, String key) {
		jdbcTemplate.update(" update sys_config set sysval = ? where syskey = ? ", val, key);
		SysConfigManager.getInstance().init();
	}
	
	@Override
	public void updatSysConfigByUUID(String val, String key,String uuid) {
		jdbcTemplate.update(" update sys_config set sysval = ? , syskey = ?  WHERE uuid = ? ", val, key,uuid);
		SysConfigManager.getInstance().init();
	}
	

    /**
     * 根据名字找到对应values值
     * @param name
     * @return
     */
    @SuppressWarnings("unchecked")
    public com.ruishengtech.rscc.crm.ui.mw.model.SysConfig getSysConfig(final String name){
    	List<com.ruishengtech.rscc.crm.ui.mw.model.SysConfig> configls=queryBean(new BeanHandler(){
    		
    		@Override
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append(" select * from "+ SpringPropertiesUtil.getProperty("sys.record.database") +"sys_config where name = ? ");
    			params.add(name);
    		}
    	},com.ruishengtech.rscc.crm.ui.mw.model.SysConfig.class);
    	 
    	if (configls.size()>0) {
			return configls.get(0);
		}
        	return null;
    }
	
    
    @Override
	public void saveOrUpdate(com.ruishengtech.rscc.crm.ui.mw.model.SysConfig sys) {
		if(sys == null)
			return;
		if(sys.getId()==null){
			super.save(sys);
		}else{
			super.update(sys);
		}
	}
    
    @Override
    public void reload(){
    	SysConfigManager.getInstance().init();
    }
    
}

