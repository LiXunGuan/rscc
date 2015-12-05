package com.ruishengtech.rscc.crm.ui.mw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.rscc.crm.ui.mw.model.AccessNumber;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;

/**
 * Created by yaoliceng on 2014/10/27.
 */
@Service
@Transactional
public class FSGateWayNumberService extends BaseConfigService<AccessNumber> {

	private String database;
	
	public FSGateWayNumberService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}
	
	@Autowired
    private FsServerManager fsServerManager;

    @Override
    protected Class getClazz() {
        return AccessNumber.class;
    }



    @Override
    public void deploy() {
       
    }

    @Override
	protected ISolution getSolution() {
		return null;
	}
    
  
//    /**
//     * 查出是否能打入的外线号码
//     * @param number
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    public List<AccessNumber> getFSGatewayNumber(final String number){
//    	List<AccessNumber> gateWayNumberls=queryBean(new BeanHandler(){
//    		
//    		@Override
//    		public void doSql(StringBuilder sql, List<Object> params) {
//    			sql.append("select * from fs_gateway_number where canin=?");
//    			params.add(number);
//    		}
//    	},AccessNumber.class);
//    	 
//    	if (gateWayNumberls.size()>0) {
//			return gateWayNumberls;
//		}
//        	return null;
//    }
    
    /**
     * 查出是否能打出的外线号码
     * @param number
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<AccessNumber> getAccessNumber(final String number){
    	List<AccessNumber> accessNumberls=queryBean(new BeanHandler(){
    		
    		@Override
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append("select * from "+database+"fs_accessnumber where canout=?");
    			params.add(number);
    		}
    	},AccessNumber.class);
    	 
    	if (accessNumberls.size()>0) {
			return accessNumberls;
		}
        	return null;
    }
   
    /**
     * 根据外线id删除关联表信息
     * @param id
     */
    public void removeGateWayNumberls(Long id) {
        jdbcTemplate.update("delete from "+database+"fs_accessnumber_gateway where gateway_id=? ",id);
    }



	
}
