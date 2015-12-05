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
import com.ruishengtech.rscc.crm.ui.mw.condition.ExtenRouterCondition;
import com.ruishengtech.rscc.crm.ui.mw.controller.AccessnumberRouteController;
import com.ruishengtech.rscc.crm.ui.mw.manager.CallRouteManager;
import com.ruishengtech.rscc.crm.ui.mw.model.MWExtenRoute;

/**
 * Created by yaoliceng on 2014/10/27.
 */
@Service
@Transactional
public class MWExtenRouteService extends BaseConfigService<MWExtenRoute> {
	
	private String database;
	
	public MWExtenRouteService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}

    @Override
    public void saveOrUpdate(MWExtenRoute mwExtenRoute) {
        super.saveOrUpdate(mwExtenRoute);
        jdbcTemplate.update(" UPDATE "+database+"mw_accessnumber_route SET extension=? WHERE extenrouteid=?",mwExtenRoute.getExtension(),mwExtenRoute.getId());
        AccessnumberRouteController.isApply = true;
    }


    @Autowired
    private CallRouteManager callRouteManager;

    @Override
    protected Class getClazz() {
        return MWExtenRoute.class;
    }

    @Override
    public void deploy() {
        callRouteManager.reloadRoute();
    }

    @Override
    protected ISolution getSolution() {
        return new ISolution() {
            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
            	 ExtenRouterCondition cond =(ExtenRouterCondition)condition;
            	
            	 whereSql.append(" from "+database+"mw_exten_route where 1=1  ");

                QueryUtil.like(whereSql, params, cond.getExtension(), " and extension like ? ");
                QueryUtil.like(whereSql, params, cond.getName(), " and name like ? ");
                QueryUtil.queryData(whereSql, params, cond.getType(), " and type = ? ");
            }

            @Override
            public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
                countSql.append(" select count(*) ").append(whereSql);
            }

            @Override
            public void getPageSql(StringBuilder pageSql, StringBuilder whereSql, ICondition condition, List<Object> params) {
                pageSql.append(" select *  ").append(whereSql);
                pageSql.append(" order by id desc ");
            }

			@Override
			public List<String[]> getOrderBy(ICondition condition) {
				return null;
			}
            
        };
    }
    
    
    
    @SuppressWarnings("unchecked")
    public MWExtenRoute getExten(final String extension){
    	List<MWExtenRoute> extls=queryBean(new BeanHandler(){
    		
    		@Override
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append("select * from "+database+"mw_exten_route where extension=?");
    			params.add(extension);
    		}
    	},MWExtenRoute.class);
    	 
    	if (extls.size()>0) {
			return extls.get(0);
		}
        	return null;
    }
    
    /**
     * 根据类型查询
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<MWExtenRoute> getExtenRoute(final String type){
    	List<MWExtenRoute> extls=queryBean(new BeanHandler(){
    		
    		@Override
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append("select * from "+database+"mw_exten_route where type=?");
    			params.add(type);
    		}
    	},MWExtenRoute.class);
    	 
    	if (extls.size()>0) {
			return extls;
		}
        	return null;
    }
    
    @SuppressWarnings("unchecked")
	public List<MWExtenRoute> getExtenRoute(final String type,final Long destid){
    	List<MWExtenRoute> extls=queryBean(new BeanHandler(){
    		
    		@Override
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append("select * from "+database+"mw_exten_route where type = ? and destid = ? ");
    			params.add(type);
    			params.add(destid);
    		}
    	},MWExtenRoute.class);
    	 
    	if (extls.size()>0) {
			return extls;
		}
        return null;
    }
    
    public void removExtenRoutes(final Long destId,String type){
        jdbcTemplate.update(" delete from "+database+"mw_exten_route where destid=? and type =?",destId,type);
    }

    @SuppressWarnings("unchecked")
	public MWExtenRoute getExtenById(final Long extenRouteId) {
        List<MWExtenRoute> extls=queryBean(new BeanHandler(){
            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from "+database+"mw_exten_route where id=?");
                params.add(extenRouteId);
            }
        },MWExtenRoute.class);
        if (extls.size()>0) {
            return extls.get(0);
        }
        return null;
    }
}
