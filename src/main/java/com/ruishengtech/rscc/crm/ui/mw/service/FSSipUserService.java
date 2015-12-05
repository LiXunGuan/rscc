package com.ruishengtech.rscc.crm.ui.mw.service;

import java.util.List;

import com.ruishengtech.rscc.crm.ui.mw.command.ForAgentCommand;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.rscc.crm.ui.mw.condition.SipUserCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.FSUser;
import com.ruishengtech.rscc.crm.ui.mw.model.FSXml;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;

/**
 * Created by yaoliceng on 2014/10/25.
 */
@Service
@Transactional
public class FSSipUserService extends BaseConfigService<FSUser> {

    @Autowired
    private FsServerManager fsServerManager;

    private String database;
    
    public FSSipUserService(){
    	database = SpringPropertiesUtil.getProperty("sys.record.database");
    }

    @Override
    protected Class getClazz() {
        return FSUser.class;
    }

    @Override
    public void deploy() {
         /*
         	通知节点部署
         */
        fsServerManager.sendAgentCommond(ForAgentCommand.refresh, "cata=" + FSXml.CATALOG_SIPUSER, null);
        fsServerManager.sendAsynFsCommond("reloadxml ", null);

    }

    public void toFSXMl() {

        /**
         * 写表更新
         */
        List<FSUser> fsUsers = getAll(FSUser.class);
        JSONObject jsonObject = new JSONObject();

        jdbcTemplate.update("delete from " + database + "fs_xml where cata=?", FSXml.CATALOG_SIPUSER);
        for (FSUser fsUser : fsUsers) {
            jsonObject.put(String.valueOf(fsUser.getSipId()), fsUser.toXml());
        }

        FSXml fsXml = new FSXml();
        fsXml.setName("user");
        fsXml.setStatus("0");
        fsXml.setCata(FSXml.CATALOG_SIPUSER);
        fsXml.setContent(jsonObject.toString());
        fsXml.setHostname("*");
        fsXml.setDest("/usr/local/freeswitch/conf/directory/default/");
        fsXml.setType(FSXml.MULTIPLE);
        save(fsXml);
    }

    @Override
    protected ISolution getSolution() {
        return new ISolution() {
            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
                SipUserCondition cond = (SipUserCondition) condition;
                whereSql.append(" from " + database + "fs_user where 1=1  ");
                QueryUtil.like(whereSql, params, cond.getUidname(), " and  sip_id like ? ");
            }

            @Override
            public void getCountSql(StringBuilder countSql, StringBuilder whereSql) {

                countSql.append(" select count(*) ").append(whereSql);
            }

            @Override
            public void getPageSql(StringBuilder pageSql, StringBuilder whereSql, ICondition condition, List<Object> params) {

                pageSql.append("select * ").append(whereSql);
                pageSql.append(" order by id desc ");
            }

			@Override
			public List<String[]> getOrderBy(ICondition condition) {
				return null;
			}
        };
    }

    /**
     * 根据sip_id查询分机
     * @param sipId
     * @return
     */
    @SuppressWarnings("unchecked")
    public FSUser getFSUser(final String sipId) {
        List<FSUser> fsls = queryBean(new BeanHandler() {

            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from " + database + "fs_user where sip_id=? ");
                params.add(sipId);
            }
        }, FSUser.class);

        if (fsls.size() > 0) {
            return fsls.get(0);
        }
        return null;
    }
    
    /**
     * 根据id查询分机
     * @param sipId
     * @return
     */
    @SuppressWarnings("unchecked")
    public FSUser getFSUserByid(final String id) {
        List<FSUser> fsls = queryBean(new BeanHandler() {

            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from " + database + "fs_user where id=? ");
                params.add(id);
            }
        }, FSUser.class);

        if (fsls.size() > 0) {
            return fsls.get(0);
        }
        return null;
    }
    
    /**
     * 查询范围内分机
     * @param sipId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<FSUser> getFSUserls(final String min,final String max) {
        List<FSUser> fsls = queryBean(new BeanHandler() {

            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from " + database + "fs_user where sip_id >=? and  sip_id <=? ");
                params.add(min);
                params.add(max);
            }
        }, FSUser.class);

        if (fsls.size() > 0) {
            return fsls;
        }
        return null;
    }

    public String getSipUserPhoneContact(String destString) {
        List<String> list = jdbcTemplate.queryForList("select url from " + database + "registrations where reg_user=?", String.class, destString);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public Long getCount() {
        Long ret = jdbcTemplate.queryForObject("select count(*) from " + database + "fs_user", Long.class);
        return ret;
    }

    public void deeleteBySIpId(String sipid) {
        jdbcTemplate.update("DELETE  FROM "+database+"fs_user WHERE sip_id=?", sipid);
        jdbcTemplate.update("DELETE  FROM "+database+"mw_exten_route WHERE type='SIPUSER' and extension=?", sipid);

    }
}
