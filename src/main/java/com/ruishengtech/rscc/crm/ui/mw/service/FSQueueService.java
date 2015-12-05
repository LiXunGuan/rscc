package com.ruishengtech.rscc.crm.ui.mw.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.util.Beans;
import com.ruishengtech.rscc.crm.ui.mw.command.ForAgentCommand;
import com.ruishengtech.rscc.crm.ui.mw.condition.QueueCondition;
import com.ruishengtech.rscc.crm.ui.mw.manager.CallRouteManager;
import com.ruishengtech.rscc.crm.ui.mw.model.FSQueue;
import com.ruishengtech.rscc.crm.ui.mw.model.FSXml;
import com.ruishengtech.rscc.crm.ui.mw.model.MWAgent;
import com.ruishengtech.rscc.crm.ui.mw.model.MWAgentQueue;
import com.ruishengtech.rscc.crm.ui.mw.model.MWExtenRoute;
import com.ruishengtech.rscc.crm.ui.mw.model.RtQueueStatus;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;

/**
 * Created by yaoliceng on 2014/10/27.
 */

@Service
@Transactional
public class FSQueueService extends BaseConfigService<FSQueue> {

    @Autowired
    private FsServerManager fsServerManager;

    @Autowired
    private AgentService agentService;

    @Autowired
    private MWAgentService mwAgentService;

    @Autowired
    private CallRouteManager callRouteManager;

    @Autowired
    private FSSipUserService fsSipUserService;
    
    private String database;
	
	public FSQueueService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}

    public void saveOrUpdate(FSQueue fsQueue) {

        if (Beans.getProperty(fsQueue, "id") == null) {
            save(fsQueue);

            RtQueueStatus rtQueueStatus = new RtQueueStatus();
            rtQueueStatus.setQueue_id(fsQueue.getId());
            rtQueueStatus.setQueue_name(fsQueue.getName());
            save(rtQueueStatus);

        } else {
            update(fsQueue);
        }
    }


    @Override
    protected Class getClazz() {
        return FSQueue.class;
    }

    @Override
    public void deploy() {

        fsServerManager.sendAgentCommond(ForAgentCommand.refresh, "cata=" + FSXml.CATALOG_QUEUE, null);
        //会有问题
        fsServerManager.sendAsynFsCommond("reload mod_callcenter", null);
    }

    public void toFSXML() {

        /**
         * 写表更新
         */

        jdbcTemplate.update("delete from "+database+"fs_xml where cata=?", FSXml.CATALOG_QUEUE);
        List<FSQueue> fsQueues = getAll(FSQueue.class);

        FSXml fsXml = new FSXml();
        fsXml.setName("callcenter.conf.xml");
        fsXml.setHostname("*");
        fsXml.setType(FSXml.SINGLE);
        fsXml.setCata(FSXml.CATALOG_QUEUE);
        fsXml.setContent(getXml(fsQueues));
        fsXml.setDest("/usr/local/freeswitch/conf/autoload_configs/callcenter.conf.xml");
        fsXml.setStatus("0");
        save(fsXml);
    }
    /**
     * 根据name将技能组的等待音乐替换为默认的音乐
     * @param queueName
     */
	public void updatemohSound(String queueName){
		if(StringUtils.isNotBlank(queueName)){
			FSQueue fsqueue = getFSQueue(queueName);
			fsqueue.setConfig(new JSONObject(fsqueue.getConfig()).put("moh-sound","/usr/local/freeswitch/sounds/moh/aw.wav").toString());
			saveOrUpdate(fsqueue);
		}else{
			List<FSQueue> fsqueues = getAllFSQueue();
			for(FSQueue fsqueue : fsqueues){
				fsqueue.setConfig(new JSONObject(fsqueue.getConfig()).put("moh-sound","/usr/local/freeswitch/sounds/moh/aw.wav").toString());
				saveOrUpdate(fsqueue);
			}
		}
		
    }
    @Override
    protected ISolution getSolution() {
        return new ISolution() {

            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql,
                                 List<Object> params) {
                QueueCondition cond = (QueueCondition) condition;
                whereSql.append(" from "+database+"fs_queue f, "+database+"mw_exten_route e where 1=1 and f.id = e.destid and e.type = 'CALLCENTER' ");
                QueryUtil.like(whereSql, params, cond.getName(), " and f.name like ? ");
                QueryUtil.queryData(whereSql, params, cond.getStaticqueue(), " and f.is_static = ? ");
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
                pageSql.append("select f.*,e.extension as extension ").append(whereSql);
                pageSql.append(" order by f.id desc ");
            }

			@Override
			public List<String[]> getOrderBy(ICondition condition) {
				return null;
			}

        };
    }

    public FSQueue getQueue(final String name) {

        return null;
    }

    @SuppressWarnings("unchecked")
    public List<MWAgentQueue> getmwAgentQueue(final Long queueId) {
        List<MWAgentQueue> aqls = queryBean(new BeanHandler() {
            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from "+database+"mw_agent_queue where queueid=? ");
                params.add(queueId);
            }
        }, MWAgentQueue.class);
        if (aqls.size() > 0) {
            return aqls;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<MWAgentQueue> getmwAgentQueueForCommand(final Long queueId) {
        List<MWAgentQueue> aqls = queryBean(new BeanHandler() {

            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from "+database+"mw_agent_queue q , "+database+"mw_agent a where a.uid=q.agentuid and queueid=? ");
                params.add(queueId);
            }
        }, MWAgentQueue.class);

        if (aqls.size() > 0) {
            return aqls;
        }

        return null;
    }


    @SuppressWarnings("unchecked")
    public FSQueue getFSQueue(final String name) {
        List<FSQueue> queuels = queryBean(new BeanHandler() {

            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from "+database+"fs_queue where name=?");
                params.add(name);
            }
        }, FSQueue.class);

        if (queuels.size() > 0) {
            return queuels.get(0);
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public FSQueue getFSQueueByid(final String id) {
        List<FSQueue> queuels = queryBean(new BeanHandler() {

            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from "+database+"fs_queue where id=?");
                params.add(id);
            }
        }, FSQueue.class);

        if (queuels.size() > 0) {
            return queuels.get(0);
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public List<FSQueue> getAllFSQueue() {
        List<FSQueue> queuels = queryBean(new BeanHandler() {

            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from "+database+"fs_queue");
            }
        }, FSQueue.class);

        if (queuels.size() > 0) {
            return queuels;
        }
        return null;
    }
   

    /**
     * 根据技能表id删除
     *
     * @param queueid
     */
    public void removMWAgentQueues(final Long queueid) {
        jdbcTemplate.update(" delete from "+database+"mw_agent_queue where queueid=? ", queueid);
    }


    public String getXml(List<FSQueue> fsQueues) {
        StringBuilder ret = new StringBuilder();

        ret.append("<configuration name=\"callcenter.conf\" description=\"CallCenter\">\n" +
                "        <settings>\n" +
                "        <param name=\"odbc-dsn\" value=\"" + "$${mwdb}" + "\"/>\n" +
                "        <!--<param name=\"dbname\" value=\"/dev/shm/callcenter.db\"/>-->\n" +
                "        </settings>\n" +
                "\n" +
                "        <queues>\n" +
                "\n");

        for (FSQueue fsQueue : fsQueues) {

            ret.append("    <queue name=\"" + fsQueue.getId() + "\">\n");
            JSONObject config = new JSONObject(fsQueue.getConfig());

            for (Object s : config.keySet()) {
                ret.append("          <param name=\"" + s.toString() + "\" value=\"" + config.optString(String.valueOf(s)) + "\"/>\n");
            }
            ret.append("    </queue>\n");
        }

        ret.append("     </queues>\n" +
                "\n" +
                "        <agents>\n" +
                "        </agents>\n" +
                "        <tiers>\n" +
                "        </tiers>\n" +
                "\n" +
                "        </configuration>");

        return ret.toString();
    }


    public void removeMWRT(Long id) {
        jdbcTemplate.update("DELETE  FROM  "+database+"rt_queue_status where queue_id=?", id);
    }

    public FSQueue exitsQueue(final String queue_exten) {

        List<FSQueue> list = queryBean(new BeanHandler() {
            @Override
            public void doSql(StringBuilder sql, List<Object> params) {

                sql.append(" select q.* from "+database+"mw_exten_route r ,fs_queue q \n" +
                        "    where r.destid = q.id and r.extension=? ");
                params.add(queue_exten);
            }
        }, FSQueue.class);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public Long getCount() {
        Long ret = jdbcTemplate.queryForObject("select count(*) from "+database+"fs_queue", Long.class);
        return ret;
    }


    public String deeleteByExten(final String extension) {

        List<MWExtenRoute> ret = queryBean(new BeanHandler() {
            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from "+database+"mw_exten_route where extension=? and type=? ");
                params.add(extension);
                params.add(MWExtenRoute.ROUTER_TYPE_CALLCENTER);
            }
        }, MWExtenRoute.class);

        if (ret.size() > 0) {
            MWExtenRoute mwExtenRoute = ret.get(0);
            deleteById(FSQueue.class, mwExtenRoute.getDestId());
            deleteById(MWExtenRoute.class, mwExtenRoute.getId());
            return String.valueOf(mwExtenRoute.getDestId());
        }
        return null;

    }

    public String redeployQueue(Long id) {

        fsServerManager.sendAgentCommond(ForAgentCommand.refresh, "cata=" + FSXml.CATALOG_QUEUE, null);
        fsServerManager.sendSynFsCommond("reloadxml ", null);
        return fsServerManager.sendSynFsCommond("callcenter_config queue reload " + String.valueOf(id), null);
    }

    public String deployQueue(Long id) {
        fsServerManager.sendAgentCommond(ForAgentCommand.refresh, "cata=" + FSXml.CATALOG_QUEUE, null);
        fsServerManager.sendSynFsCommond("reloadxml ", null);
        return fsServerManager.sendSynFsCommond("callcenter_config queue load " + String.valueOf(id), null);
    }

    public String undeploy(String id) {

        fsServerManager.sendAgentCommond(ForAgentCommand.refresh, "cata=" + FSXml.CATALOG_QUEUE, null);
        fsServerManager.sendSynFsCommond("reloadxml ", null);
        return fsServerManager.sendSynFsCommond("callcenter_config queue unload " + String.valueOf(id), null);
    }

    public boolean chckRoute(final String queue_exten) {


        List<FSQueue> list = queryBean(new BeanHandler() {
            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append(" select * from "+database+"mw_exten_route r where r.extension=?  and r.type!=?");
                params.add(queue_exten);
                params.add(MWExtenRoute.ROUTER_TYPE_CALLCENTER);
            }
        }, FSQueue.class);

        if (list.size() > 0) {
            return true;
        }
        return false;
    }

    public void removeBind(FSQueue fsqueue) {

        if ("1".equals(fsqueue.getIs_static())) {
            List<MWAgentQueue> ret = getmwAgentQueue(fsqueue.getId());

            if(ret!=null) {
                for (MWAgentQueue mwAgentQueue : ret) {
                    MWAgent agent = new MWAgent();
                    agent.setUid(mwAgentQueue.getAgentUid());
                    agentService.unBind(agent);
                }
            }
        }

    }

    public void bind(FSQueue fsqueue, MWAgentQueue mwAgentQueue) {

        if ("1".equals(fsqueue.getIs_static())) {

            MWAgent agent = mwAgentService.queryForAgent(mwAgentQueue.getAgentUid());

            String contactString = agentService.getContactString(agent);
            agentService.staticBind(agent, contactString, String.valueOf(mwAgentQueue.getQueueId()),agent.getStatic_exten());
        }
        return;
    }

    public void deleteAgentQueue(String i, Long id) {

        jdbcTemplate.update("DELETE FROM "+database+"mw_agent_queue WHERE queueid=? AND agentuid=?",id,i);
    }
}
