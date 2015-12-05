package com.ruishengtech.rscc.crm.ui.mw.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.rscc.crm.ui.mw.command.ForAgentCommand;
import com.ruishengtech.rscc.crm.ui.mw.model.FSXml;
import com.ruishengtech.rscc.crm.ui.mw.model.IVRGen;
import com.ruishengtech.rscc.crm.ui.mw.model.MWExtenRoute;
import com.ruishengtech.rscc.crm.ui.mw.model.MWIVR;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;

/**
 * Created by yaoliceng on 2014/10/27.
 */
@Service
@Transactional
public class FSIvrService extends BaseConfigService<MWIVR> {

	private String database;
	
	public FSIvrService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}

    @Autowired
    private FsServerManager fsServerManager;


    @Override
    protected Class getClazz() {
        return MWIVR.class;
    }

    @Override
    public void deploy() {

        fsServerManager.sendAgentCommond(ForAgentCommand.refresh, "cata="+ FSXml.CATALOG_IVR,null);
        fsServerManager.sendAsynFsCommond("reloadxml ", null);

    }

    @Override
    protected ISolution getSolution() {
        return new ISolution() {
            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
            	whereSql.append(" from "+database+"mw_ivr where 1=1 ");
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

    public void toFSXMl() {

        /**
         * 写表更新
         */
        jdbcTemplate.update("delete from "+database+"fs_xml where cata=?", FSXml.CATALOG_IVR);
        List<MWIVR> mwivrList = getAll(MWIVR.class);
        for (MWIVR mwivr : mwivrList) {

            FSXml fsXml = new FSXml();
            fsXml.setName("ivr");
            fsXml.setStatus("0");
            fsXml.setCata(FSXml.CATALOG_IVR);
            fsXml.setContent(IVRGen.gen(mwivr, new JSONObject(mwivr.getContent())));
            fsXml.setHostname("*");
            fsXml.setDest("/usr/local/freeswitch/conf/ivr_menus/" + mwivr.getName() + ".xml");
            fsXml.setType(FSXml.SINGLE);
            save(fsXml);
        }

    }

    @SuppressWarnings("unchecked")
	public MWExtenRoute getExtenByDestId(final Long id) {

       List<MWExtenRoute> mwExtenRoutes = queryBean(new BeanHandler() {
            @Override
            public void doSql(StringBuilder sql, List<Object> params) {

                sql.append(" select * from "+database+"mw_exten_route where destid=? and type=?");
                params.add(id);
                params.add(MWExtenRoute.ROUTER_TYPE_IVR_EXT);
            }
        }, MWExtenRoute.class);

        if(mwExtenRoutes.size()>0){
            return mwExtenRoutes.get(0);
        }
        return null;
    }

    public void deleteExtenByDestId(Long id) {
        jdbcTemplate.update("DELETE FROM "+database+"mw_exten_route WHERE destid=? and type=?",id,MWExtenRoute.ROUTER_TYPE_IVR_EXT);
    }
}
