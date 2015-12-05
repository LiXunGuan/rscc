package com.ruishengtech.rscc.crm.ui.mw.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.rscc.crm.ui.mw.command.ForAgentCommand;
import com.ruishengtech.rscc.crm.ui.mw.condition.FSXmlCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.FSXml;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;

/**
 * Created by yaoliceng on 2014/10/27.
 */
@Service
@Transactional
public class FSXmlService extends BaseConfigService<FSXml> {

	private String database;
	
	public FSXmlService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}
	
    @Autowired
    private FsServerManager fsServerManager;

    @Override
    protected Class getClazz() {
        return FSXml.class;
    }

    @Override
    public void deploy() {
         /*
         	通知节点部署
         */
        fsServerManager.sendAgentCommond(ForAgentCommand.refresh, "cata="+FSXml.CATA_XML_IVR,null);
        fsServerManager.sendAgentCommond(ForAgentCommand.refresh, "cata="+FSXml.CATA_XML_DIALPLAN,null);
        fsServerManager.sendAsynFsCommond("reloadxml ", null);

    }

    @Override
    protected ISolution getSolution() {
        return new ISolution() {
            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
            	FSXmlCondition cond =(FSXmlCondition)condition;
            	whereSql.append(" from "+database+"fs_xml where 1=1   ");
                if(StringUtils.isNotBlank(cond.getType())) {
                    QueryUtil.queryData(whereSql, params, cond.getType(), " and cata=? ");
                }else{
                    whereSql.append("and cata='CATA_XML-IVR' or cata='CATA_XML-DIALPLAN'");
                }
            }

            @Override
            public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
                countSql.append(" select count(*) ").append(whereSql);
            }

            @Override
            public void getPageSql(StringBuilder pageSql, StringBuilder whereSql, ICondition condition, List<Object> params) {
                pageSql.append(" select *  ").append(whereSql);
                pageSql.append(" order by id asc ");
            }

			@Override
			public List<String[]> getOrderBy(ICondition condition) {
				return null;
			}
            
        };
    }
    
//    @SuppressWarnings("unchecked")
//    public FSXml getExtenRoute(final String name){
//    	List<FSXml> xmlls=queryBean(new BeanHandler(){
//    		
//    		@Override
//    		public void doSql(StringBuilder sql, List<Object> params) {
//    			sql.append("select * from fs_xml where name=?");
//    			params.add(name);
//    		}
//    	},FSXml.class);
//    	 
//    	if (xmlls.size()>0) {
//			return xmlls.get(0);
//		}
//        	return null;
//    }
//    
    @SuppressWarnings("unchecked")
    public List<FSXml> getXmlls(final String cata){
    	List<FSXml> xmlls=queryBean(new BeanHandler(){
    		
    		@Override
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append("select * from "+database+"fs_xml where cata=?");
    			params.add(cata);
    		}
    	},FSXml.class);
    	 
    	if (xmlls.size()>0) {
			return xmlls;
		}
        	return null;
    }
    
    @SuppressWarnings("unchecked")
    public FSXml getXml(final String name){
    	List<FSXml> xmlls=queryBean(new BeanHandler(){
    		
    		@Override
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append("select * from "+database+"fs_xml where name=?");
    			params.add(name);
    		}
    	},FSXml.class);
    	 
    	if (xmlls.size()>0) {
			return xmlls.get(0);
		}
    	return null;
    }


}
