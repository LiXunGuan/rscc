package com.ruishengtech.rscc.crm.ui.mw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.rscc.crm.ui.mw.condition.CallInTimerCondition;
import com.ruishengtech.rscc.crm.ui.mw.manager.CallRouteManager;
import com.ruishengtech.rscc.crm.ui.mw.model.AccessNumber;
import com.ruishengtech.rscc.crm.ui.mw.model.MWAccessnumberRoute;

/**
 * Created by yaoliceng on 2014/10/27.
 */
@Service
@Transactional
public class MWAccessnumberRputeService extends BaseConfigService<MWAccessnumberRoute> {

	private String database;
	
	public MWAccessnumberRputeService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}
	
    @Autowired
    private CallRouteManager callRouteManager;


    @Override
    protected Class getClazz() {
        return MWAccessnumberRoute.class;
    }


    @Override
    public void deploy() {
        callRouteManager.reloadCallin();
    }

    @Override
    protected ISolution getSolution() {
        return new ISolution() {
            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
            	 CallInTimerCondition cond =(CallInTimerCondition)condition;
            	 whereSql.append(" from "+database+"mw_accessnumber_route where 1=1 ");
        		 QueryUtil.queryData(whereSql, params, cond.getNumbers(), " and  number = ? ");
            }

            @Override
            public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
                countSql.append(" select count(*) ").append(whereSql);
            }

            @Override
            public void getPageSql(StringBuilder pageSql, StringBuilder whereSql, ICondition condition, List<Object> params) {
                pageSql.append("select *  ").append(whereSql);
                pageSql.append(" order by rank asc ");
            }

			@Override
			public List<String[]> getOrderBy(ICondition condition) {
				return null;
			}
            
        };
    }
    
    
    
    @SuppressWarnings("unchecked")
    public List<AccessNumber> getAccessNumber(final String number){
    	List<AccessNumber> anls=queryBean(new BeanHandler(){
    		
    		@Override
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append("select * from "+database+"fs_accessnumber where canin=?");
    			params.add(number);
    		}
    	},AccessNumber.class);
    	 
    	if (anls.size()>0) {
			return anls;
		}
        	return null;
    }
    
    
    @SuppressWarnings("unchecked")
    public MWAccessnumberRoute getMWInCalltimer(final String number){
    	List<MWAccessnumberRoute> ictls=queryBean(new BeanHandler(){
    		
    		@Override
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append("select * from "+database+"mw_accessnumber_route where number=?");
    			params.add(number);
    		}
    	},MWAccessnumberRoute.class);
    	 
    	if (ictls.size()>0) {
			return ictls.get(0);
		}
        	return null;
    }
    
    @SuppressWarnings("unchecked")
    public List<MWAccessnumberRoute> getAccessNumberRouteByExtenId(final Long extenid){
    	List<MWAccessnumberRoute> ictls=queryBean(new BeanHandler(){
    		
    		@Override
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append("select * from "+database+"mw_accessnumber_route where extenrouteid = ? ");
    			params.add(extenid);
    		}
    	},MWAccessnumberRoute.class);
    	 
    	if (ictls.size()>0) {
			return ictls;
		}
    	return null;
    }
    
}
