package com.ruishengtech.rscc.crm.ui.mw.service;

import java.util.List;

import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.rscc.crm.ui.mw.command.Commands;
import com.ruishengtech.rscc.crm.ui.mw.command.ForAgentCommand;
import com.ruishengtech.rscc.crm.ui.mw.condition.ServerCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.MWFsHost;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;
import com.ruishengtech.rscc.crm.ui.mw.util.HttpRequest;

/**
 * Created by yaoliceng on 2014/10/25.
 */
@Service(value = "mwServerService")
@Transactional
public class MWFsHostService extends BaseConfigService<MWFsHost> {

	private String database;
	
	public MWFsHostService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}
	
    @Autowired
    private FsServerManager fsServerManager;

    @Override
    protected Class<MWFsHost> getClazz() {
        return MWFsHost.class;
    }

//    @Override
//    public void saveOrUpdate(MWServer mwServer){
//
//        FSXml fsXml = new FSXml();
//        fsXml.setContent(mwServer.getFsXml());
//        fsXml.setHostname(mwServer.getName());
//        fsXml.setName("event_socket.conf.xml");
//        fsXml.setDest("/usr/local/freeswitch/conf/autoload_configs/event_socket.conf.xml");
//        fsXml.setType(FSXml.SIMPLE);
//        fsXml.setStatus("0");
//        save(fsXml);
//
//        super.saveOrUpdate(mwServer);
//    }

    @Override
    public void deploy() {
//        fsServerManager.sendAgentCommond(ForAgentCommand.refresh, "name=event_socket.conf.xml");
//        fsServerManager.sendAsynFsCommond("reloadxml");
    }

    @Override
    protected ISolution getSolution() {
        return new ISolution() {

            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql,
                                 List<Object> params) {
                ServerCondition cond = (ServerCondition) condition;
                whereSql.append(" from "+database+"mw_fshost where 1=1  ");
                if (cond.getName() != null) {
                    QueryUtil.queryData(whereSql, params, cond.getName(), " and  name = ? ");
                }
            }

            @Override
            public void getCountSql(StringBuilder countSql,
                                    StringBuilder whereSql) {
                countSql.append(" select count(*) ").append(whereSql);

            }

            @Override
            public void getPageSql(StringBuilder pageSql,
                                   StringBuilder whereSql, ICondition condition,
                                   List<Object> params) {
                pageSql.append("select * ").append(whereSql);
                pageSql.append(" order by id asc ");

            }

			@Override
			public List<String[]> getOrderBy(ICondition condition) {
				return null;
			}

        };
    }


    @SuppressWarnings("unchecked")
    public MWFsHost getServer(final String name) {
        List<MWFsHost> mwls = queryBean(new BeanHandler() {

            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from "+database+"mw_fshost where name=?");
                params.add(name);
            }
        }, MWFsHost.class);

        if (mwls.size() > 0) {
            return mwls.get(0);
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public MWFsHost getServerIp(final String aghost) {
        List<MWFsHost> mwls = queryBean(new BeanHandler() {

            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from "+database+"mw_fshost where ag_ip=?");
                params.add(aghost);
            }
        }, MWFsHost.class);

        if (mwls.size() > 0) {
            return mwls.get(0);
        }
        return null;
    }
    
    
    
    

    public Boolean checkHost(MWFsHost mwFsHost, StringBuilder sb) {

        try {
            String ret = HttpRequest.sendPost1(Commands.build(ForAgentCommand.initServer, mwFsHost.getAgip(), mwFsHost.getAgport()), "identification="+mwFsHost.getName());
            sb.append(ret);
            return true;
        } catch (Exception e) {
            sb.append(e.getMessage());
            return false;
        }
    }

    public boolean addServer(MWFsHost mwServer) {

        try {
            fsServerManager.addServer(mwServer);
            return true;
        } catch (InboundConnectionFailure inboundConnectionFailure) {
            inboundConnectionFailure.printStackTrace();
            return false;
        }
    }


    public boolean editServer(MWFsHost mwServer) {

        try {
            fsServerManager.editServer(mwServer);
            return true;
        } catch (InboundConnectionFailure inboundConnectionFailure) {
            inboundConnectionFailure.printStackTrace();
            return false;
        }

    }
}
