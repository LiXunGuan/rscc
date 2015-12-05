package com.ruishengtech.rscc.crm.ui.mw.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.ui.mw.manager.CallRouteManager;
import com.ruishengtech.rscc.crm.ui.mw.model.Domains;
import com.ruishengtech.rscc.crm.ui.mw.model.LogAgentBinding;
import com.ruishengtech.rscc.crm.ui.mw.model.MWAgent;
import com.ruishengtech.rscc.crm.ui.mw.model.MWAgentUser;
import com.ruishengtech.rscc.crm.ui.mw.model.SysConfig;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;

/**
 * Created by yaoliceng on 2014/11/22.
 */
@Service
public class AgentService extends BaseService {

    @Autowired
    private AgentStatusManagerService agentStatusManagerService;

    @Autowired
    private FSSipUserService fsSipUserService;

    @Autowired
    private FsServerManager fsServerManager;

    @Autowired
    private SYSConfigService sysConfigService;

    @Autowired
    private CallRouteManager callRouteManager;
    
    private String database;
	
	public AgentService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}

    public String staticBind(MWAgent agent, String contact, String queueId,String extension) {

        SysConfig sysConfig = sysConfigService.getSysConfig("template");
        JSONObject json = new JSONObject(sysConfig.getVal());

        if(StringUtils.isNotBlank(extension)) {

            agent.setState(Domains.EXTEN_STATE_DOWN);
            agent.setStatus(json.optString("status"));
            agent.setExtension(extension);

            agentStatusManagerService.bind(agent);

            /** 动态绑定分机号*/
            MWAgentUser mwAgentUser = new MWAgentUser();
            mwAgentUser.setAgentUId(agent.getUid());
            mwAgentUser.setExtension(extension);
            save(mwAgentUser);
        }

        /** 动态添加agent **/
        jdbcTemplate.update("INSERT INTO "+database+"agents (name, system, uuid, type, contact, status, state, max_no_answer, wrap_up_time, " +
                        "reject_delay_time, busy_delay_time, no_answer_delay_time, last_bridge_start, last_bridge_end, last_offered_call, last_status_change, no_answer_count, calls_answered, talk_time, ready_time) " +
                        "VALUES (?, 'single_box', null, 'CallBack', ?, ?, 'Waiting', ?, ?, ?, ?, ?, 0, 0, 0, 0, 0, 0, 0, 0);\n",
                agent.getUid(), contact, json.optString("status"),
                json.getInt("max-no-answer"),
                json.getInt("wrap-up-time"),
                json.getInt("reject-delay-time"),
                json.getInt("busy-delay-time"),
                json.getInt("no-answer-delay-time"));

        jdbcTemplate.update("INSERT INTO "+database+"tiers (queue, agent, state, level, position) " +
                "VALUES (?, ?, 'Ready', 1, 1);", queueId, agent.getUid());

        return null;
    }


    public long unBind(MWAgent agent) {

        //删tire
        jdbcTemplate.update("delete from "+database+"tiers where agent=?", agent.getUid());

        //删 agent
        jdbcTemplate.update("delete from "+database+"agents where name=?", agent.getUid());

        //删除关系
        jdbcTemplate.update("delete from "+database+"mw_agent_user where agentuid=?", agent.getUid());

        //更新缓存的关系 不在这里更新
        jdbcTemplate.update("UPDATE "+database+"rt_agent_status SET status=? where agent_uid=? ", "offline", agent.getUid());

        //log
        return logEndDate(agent.getUid());

    }

    private long logEndDate(String uid) {
    	ArrayList<Object> ob = new ArrayList<Object>();
        ob.add(uid);
        List<LogAgentBinding> list = getAllById(LogAgentBinding.class, "where agentuid=? order by id desc limit 1  ", ob);
        if (list.size() == 0) {

            LogAgentBinding logAgentBinding = new LogAgentBinding();
            logAgentBinding.setEndDate(new Date());
            logAgentBinding.setAgentuid(uid);
            save(logAgentBinding);
        } else {
            LogAgentBinding logAgentBinding = list.get(0);
            if (logAgentBinding.getEndDate() == null) {
                logAgentBinding.setEndDate(new Date());
                update(logAgentBinding);
                return logAgentBinding.getEndDate().getTime() - logAgentBinding.getBeginDate().getTime();

            } else {
                logAgentBinding = new LogAgentBinding();
                logAgentBinding.setEndDate(new Date());
                logAgentBinding.setAgentuid(uid);
                save(logAgentBinding);
            }

        }
        return 0;
	}


	public String getContactString(MWAgent agent) {
        List<String> contact = new ArrayList<>();
        if (StringUtils.isNotBlank(agent.getStatic_exten())) {

            String fs = getExtenConTact(agent.getStatic_exten());
            if (fs != null) {
                contact.add(fs);
            }
        }
        if (StringUtils.isNotBlank(agent.getPhone())) {
//            String fs = getPhoneContast(agent,agent.getPhone());
            String fs = agent.getPhone();
            if (fs != null) {
                contact.add(fs);
            }
        }

        /** simultaneously or sequentially **/
        String contactString = null;
        if (contact.size() > 1) {
            if ("s".equals(agent.getStatic_strategy())) {
                contactString=contact.get(0)+","+contact.get(1);
            } else {
                contactString=contact.get(0)+"|"+contact.get(1);
            }
        } else if (contact.size() == 1) {
            contactString = contact.get(0);
        }
        return contactString;
    }


	private String getExtenConTact(String static_exten) {
		 return  fsSipUserService.getSipUserPhoneContact(static_exten);
	}


}
