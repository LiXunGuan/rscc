package com.ruishengtech.rscc.crm.ui.mw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.rscc.crm.ui.mw.condition.GateWayCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.FSSipProfile;
import com.ruishengtech.rscc.crm.ui.mw.model.FSXml;
import com.ruishengtech.rscc.crm.ui.mw.model.MWFsHost;

/**
 * Created by yaoliceng on 2014/10/27.
 */
@Service
@Transactional
public class FSSipProfileService extends BaseConfigService<FSSipProfile> {

	private String database;
	
	public FSSipProfileService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}

    @Override
    protected Class getClazz() {
        return FSSipProfile.class;
    }

    @Override
    public void deploy() {

        /**
         * 写表更新
         */
        List<FSSipProfile> fsSipProfiles = getAll(FSSipProfile.class);
        for (FSSipProfile fsSipProfile : fsSipProfiles) {
            try {
                FSXml fsXml = new FSXml();
                fsXml.setName(fsSipProfile.getName() + ".xml");
                fsXml.setHostname(getById(MWFsHost.class, fsSipProfile.getFshostid()).getName());
                fsXml.setType(FSXml.SINGLE);
                fsXml.setCata(FSXml.CATALOG_SIPPROFILE);
                fsXml.setContent(fsSipProfile.toXml());
                fsXml.setDest("/usr/local/freeswitch/conf/sip_profiles/" + fsSipProfile.getName() + ".xml");
                fsXml.setStatus("0");
                save(fsXml);
            }catch (Exception e){
                e.printStackTrace();
            }
    }

    }

    @Override
    protected ISolution getSolution() {
        return new ISolution() {
            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
            	GateWayCondition cond = (GateWayCondition) condition;
                whereSql.append(" from " + database + "fs_sipprofile s , " + database + "mw_fshost m where s.fshost_id=m.id ");
            }

            @Override
            public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {
                countSql.append(" select count(*) ").append(whereSql);
            }

            @Override
            public void getPageSql(StringBuilder pageSql, StringBuilder whereSql, ICondition condition, List<Object> params) {
                pageSql.append("select s.*,m.name as servername ").append(whereSql);
                pageSql.append(" order by id asc ");
            }

			@Override
			public List<String[]> getOrderBy(ICondition condition) {
				return null;
			}

        };
    }

    @SuppressWarnings("unchecked")

    public FSSipProfile getSipProfile(final String name) {
        List<FSSipProfile> fsls = queryBean(new BeanHandler() {

            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from " + database + "fs_sipprofile where name=?");
                params.add(name);
            }
        }, FSSipProfile.class);

        if (fsls.size() > 0) {
            return fsls.get(0);
        }

        return null;
    }

    public void initSipProfile(MWFsHost mwFsHost) {

        FSSipProfile internal = new FSSipProfile();
        internal.setName("internal");
        internal.setSipIp(mwFsHost.getEslip());
        internal.setType(FSSipProfile.INTERNAL);
        internal.setFshostid(mwFsHost.getId());
        internal.setCodecString("G729,PCMU,PCMA,GSM");
        internal.setSipPort("5060");
        save(internal);

        FSSipProfile external = new FSSipProfile();
        external.setName("external");
        external.setSipIp(mwFsHost.getEslip());
        external.setType(FSSipProfile.EXTERNAL);
        external.setCodecString("G729,PCMU,PCMA,GSM");
        external.setFshostid(mwFsHost.getId());
        external.setSipPort("5080");
        save(external);
    }

    @SuppressWarnings("unchecked")
    public List<FSSipProfile> getSipPro(final Long fshostId){
    	List<FSSipProfile> sipls=queryBean(new BeanHandler(){

    		@Override
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append("select * from " + database + "fs_sipprofile where fshost_id=?");
    			params.add(fshostId);
    		}
    	},FSSipProfile.class);

    	if (sipls.size()>0) {
			return sipls;
		}
        	return null;
    }
}
