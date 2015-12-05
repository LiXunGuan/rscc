package com.ruishengtech.rscc.crm.ui.mw.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.rscc.crm.ui.mw.command.ForAgentCommand;
import com.ruishengtech.rscc.crm.ui.mw.condition.GateWayCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.AccessNumber;
import com.ruishengtech.rscc.crm.ui.mw.model.AccessNumberGateway;
import com.ruishengtech.rscc.crm.ui.mw.model.FSSipProfile;
import com.ruishengtech.rscc.crm.ui.mw.model.FSSipTrunk;
import com.ruishengtech.rscc.crm.ui.mw.model.FSXml;
import com.ruishengtech.rscc.crm.ui.mw.model.MWFsHost;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;

/**
 * Created by yaoliceng on 2014/10/27.
 */
@Service
@Transactional
public class FSGateWayService extends BaseConfigService<FSSipTrunk> {

	private String database;
	
	public FSGateWayService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}
	
    @Autowired
    private FsServerManager fsServerManager;

    @Override
    protected Class getClazz() {
        return FSSipTrunk.class;
    }


    @Override
    public void deploy() {
        fsServerManager.sendAgentCommond(ForAgentCommand.refresh,"cata="+FSXml.CATALOG_GATEWAY,null);
        fsServerManager.sendAsynFsCommond("sofia profile external restart", null);
    }


    public void toFSXMl() {

        List<FSSipTrunk> gws = getAll(FSSipTrunk.class);
        deleteAllOld();

        Map<String,JSONObject> map = new HashMap();
        for (FSSipTrunk gw : gws) {

            JSONObject jsonObject = map.get(getProfileString(gw));
            if(jsonObject==null){
                jsonObject =new JSONObject();
                map.put(getProfileString(gw),jsonObject);
            }
            jsonObject.put(String.valueOf(gw.getName()), gw.toXml());
        }

        for (String s : map.keySet()) {

            FSXml fsXml = new FSXml();
            fsXml.setName("gateway");
            fsXml.setStatus("0");
            fsXml.setCata(FSXml.CATALOG_GATEWAY);
            fsXml.setContent(map.get(s).toString());
            fsXml.setHostname(s.split("\\$\\$")[0]);
            fsXml.setDest("/usr/local/freeswitch/conf/sip_profiles/" +s.split("\\$\\$")[1] );
            fsXml.setType(FSXml.MULTIPLE);
            save(fsXml);
        }
    }

    private String getProfileString(FSSipTrunk gw){
        return getById(MWFsHost.class, gw.getFshost_id()).getName()+"$$" +getById(FSSipProfile.class, gw.getSipProfileId()).getName();
    }
    
    public void removeGatewayAccessNumber(final Long id) {
        jdbcTemplate.update(" delete from "+database+"fs_accessnumber_gateway where gateway_id=? ", id);
    }


    private void deleteAllOld() {
        jdbcTemplate.update("delete from "+database+"fs_xml where cata=? ",  FSXml.CATALOG_GATEWAY);
    }

    @Override
    protected ISolution getSolution() {
        return new ISolution() {
            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
            	GateWayCondition cond =(GateWayCondition)condition;
            	
            	 whereSql.append(" from "+database+"fs_gateway g ,"+database+"fs_sipprofile s , "+database+"mw_fshost m where g.sipprofileid=s.id and g.fshost_id=m.id ");
            		QueryUtil.queryData(whereSql, params, cond.getRegistration(), " and  g.registration = ? ");
            	
            }

            @Override
            public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
                countSql.append(" select count(*) ").append(whereSql);
            }

            @Override
            public void getPageSql(StringBuilder pageSql, StringBuilder whereSql, ICondition condition, List<Object> params) {
                pageSql.append("select g.*,s.name as sipprofilename ,m.name as servername  ").append(whereSql);
                pageSql.append(" order by id asc ");
            }

			@Override
			public List<String[]> getOrderBy(ICondition condition) {
				return null;
			}
            
        };
    }
    
    /**
     * 根据名字找对应外线
     * @param name
     * @return
     */
    @SuppressWarnings("unchecked")
    public FSSipTrunk getGateWay(final String name){
    	List<FSSipTrunk> fsls=queryBean(new BeanHandler(){
    		
    		@Override
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append("select * from "+database+"fs_gateway where name=?");
    			params.add(name);
    		}
    	},FSSipTrunk.class);
    	 
    	if (fsls.size()>0) {
			return fsls.get(0);
		}
        	return null;
    }
    
    /**
     * 获取接入号下的外线
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<FSSipTrunk> getGateWayls(final String name){
    	List<FSSipTrunk> fsls=queryBean(new BeanHandler(){
    		
    		@Override
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append("select g.id as id,g.name as name from "+database+"fs_gateway g,"+database+"fs_accessnumber_gateway ag,"+database+"fs_accessnumber a "
    					+ "where g.id=ag.gateway_id and ag.accessnumber_id=a.id and a.accessnumber=? ");
    			params.add(name);
    		}
    	},FSSipTrunk.class);
    	 
    	if (fsls.size()>0) {
			return fsls;
		}
        	return null;
    }
    
    /**
     * 查出是否能打出的外线
     * @param number
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<AccessNumber> getAccessNumberls(final Long id){
    	List<AccessNumber> accessNumberls=queryBean(new BeanHandler(){
    		
    		@Override
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append("select a.id as id,a.accessnumber as accessnumber from "+database+"fs_accessnumber a,"+database+"fs_accessnumber_gateway ag,"+database+"fs_gateway g where a.id=ag.accessnumber_id and ag.gateway_id=g.id and g.id=? ");
    			params.add(id);
    		}
    	},AccessNumber.class);
    	 
    	if (accessNumberls.size()>0) {
			return accessNumberls;
		}
        	return null;
    }
    
//    /**
//     * 根据名字获取可打出的外线
//     * @param name
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    public List<FSGateway> getGateWayls(final String name){
//    	List<FSGateway> fsls=queryBean(new BeanHandler(){
//    		
//    		@Override
//    		public void doSql(StringBuilder sql, List<Object> params) {
//    			sql.append("select distinct n.number from fs_gateway g,fs_gateway_number n where g.id=n.gatewayid and n.canout='1' and g.name=? ");
//    			params.add(name);
//    		}
//    	},FSGateway.class);
//    	 
//    	if (fsls.size()>0) {
//			return fsls;
//		}
//        	return null;
//    }
//    


    public void removeGateWays(final Long fshostId){
        jdbcTemplate.update(" delete from "+database+"fs_gateway where fshost_id=?",fshostId);
    }
    
//    /**
//     * 根据外线id找到所有的外线号码
//     * @param gatewayId
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    public List<AccessNumber> getGateWayNumbers(final Long gatewayId){
//    	List<AccessNumber> fsls=queryBean(new BeanHandler(){
//    		
//    		@Override
//    		public void doSql(StringBuilder sql, List<Object> params) {
//    			sql.append("select * from fs_gateway_number where gatewayid=? ");
//    			params.add(gatewayId);
//    		}
//    	},AccessNumber.class);
//    	 
//    	if (fsls.size()>0) {
//			return fsls;
//		}
//        	return null;
//    }
//   
    @SuppressWarnings("unchecked")
    public List<AccessNumberGateway> getGateWayAccessNumber(final Long id) {
        List<AccessNumberGateway> accessNumberGatewayls = queryBean(new BeanHandler() {
            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from "+database+"fs_accessnumber_gateway where gateway_id=?");
                params.add(id);
            }
        }, AccessNumberGateway.class);
        if (accessNumberGatewayls.size() > 0) {
            return accessNumberGatewayls;
        }
        return null;
    }
    
}
