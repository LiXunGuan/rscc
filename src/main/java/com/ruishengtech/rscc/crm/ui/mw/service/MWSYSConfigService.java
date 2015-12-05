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
import com.ruishengtech.rscc.crm.ui.mw.condition.QueueCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.AccessNumber;
import com.ruishengtech.rscc.crm.ui.mw.model.FSQueue;
import com.ruishengtech.rscc.crm.ui.mw.model.SysConfig;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;

/**
 * 
 * @author chengxin
 *
 */
@Service
@Transactional
public class MWSYSConfigService extends BaseConfigService<SysConfig> {

	private String database;
	
	public MWSYSConfigService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}
	
	@Autowired
    private FsServerManager fsServerManager;

    @Override
    protected Class getClazz() {
        return SysConfig.class;
    }

    @Override
    public void deploy() {
    }

    public void toFSXML() {

    }

    @Override
    protected ISolution getSolution() {
        return new ISolution() {

            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql,
                                 List<Object> params) {
                QueueCondition cond = (QueueCondition) condition;
                whereSql.append(" from "+database+"sys_config where 1=1  ");
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

    /**
     * 根据名字找到对应values值
     * @param name
     * @return
     */
    @SuppressWarnings("unchecked")
    public SysConfig getSysConfig(final String name){
    	List<SysConfig> configls=queryBean(new BeanHandler(){
    		
    		@Override
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append("select * from "+database+"sys_config where name=?");
    			params.add(name);
    		}
    	},SysConfig.class);
    	 
    	if (configls.size()>0) {
			return configls.get(0);
		}
        	return null;
    }
  
    
    /**
     * 查出能打出的所有接入号
     * @param number
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<AccessNumber> getAccessNumberls(){
    	List<AccessNumber> accessNumberls=queryBean(new BeanHandler(){
    		
    		@Override
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append("select * from "+database+"fs_accessnumber where canout=1");
    		}
    	},AccessNumber.class);
    	 
    	if (accessNumberls.size()>0) {
			return accessNumberls;
		}
        	return null;
    }


    /**
     * 不知道写这个干吗，待研究
     * @param queueId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SysConfig> getmwAgentQueue(final Long queueId) {
    	List<SysConfig> aqls = queryBean(new BeanHandler() {

            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from "+database+"sys_config where queueid=? ");
                params.add(queueId);
            }
        }, SysConfig.class);

        if (aqls.size() > 0) {
            return aqls;
        }

        return null;
    }
    
    
    public void removMWAgentQueues(final Long queueid){
        jdbcTemplate.update(" delete from "+database+"mw_agent_queue where queueid=? ",queueid);
    }

    

    public String getXml(List<FSQueue> fsQueues) {
        StringBuilder ret = new StringBuilder();

        ret.append("<configuration name=\"callcenter.conf\" description=\"CallCenter\">\n" +
                "        <settings>\n" +
                "        <param name=\"odbc-dsn\" value=\""+"$${postgresdb}"+"\"/>\n" +
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
    
}
