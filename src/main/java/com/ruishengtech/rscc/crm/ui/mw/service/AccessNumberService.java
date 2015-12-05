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
import com.ruishengtech.rscc.crm.ui.mw.condition.AccessNumberCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.AccessNumber;
import com.ruishengtech.rscc.crm.ui.mw.model.AccessNumberGateway;
import com.ruishengtech.rscc.crm.ui.mw.model.FSSipTrunk;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;

/**
 * Created by yaoliceng on 2014/10/27.
 */

@Service
@Transactional
public class AccessNumberService extends BaseConfigService<AccessNumber> {

	private String database;
	
	public AccessNumberService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}
	
    @Autowired
    private FsServerManager fsServerManager;

    @Override
    protected Class getClazz() {
        return AccessNumber.class;
    }

    @Override
    public void deploy() {

    }

    @Override
    protected ISolution getSolution() {
        return new ISolution() {

            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql,List<Object> params) {
            	AccessNumberCondition cond = (AccessNumberCondition)condition;
                whereSql.append(" from "+database+"fs_accessnumber where 1=1  ");
                QueryUtil.like(whereSql, params, cond.getNumber(), " and accessnumber like ? ");
            }

            @Override
            public void getCountSql(StringBuilder countSql,StringBuilder whereSql) {
                countSql.append(" select count(*) ").append(whereSql);
            }

            @Override
            public void getPageSql(StringBuilder pageSql,
                                   StringBuilder whereSql, ICondition condition,
                                   List<Object> params) {
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
     * 查出是否能打入的接入号
     *
     * @param number
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<AccessNumber> getAccessNumberls(final Long id) {
        List<AccessNumber> accessNumberls = queryBean(new BeanHandler() {

            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select a.id as id,a.accessnumber as accessnumber from "+database+"fs_accessnumber a,"+database+"fs_accessnumber_gateway ag,"+database+"fs_gateway g where a.id=ag.accessnumber_id and ag.gateway_id=g.id and g.id=? ");
                params.add(id);
            }
        }, AccessNumber.class);

        if (accessNumberls.size() > 0) {
            return accessNumberls;
        }
        return null;
    }
    
    /**
     * 查询所有的接入号
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<AccessNumber> getAllAccessNumbers(){
    	List<AccessNumber> accessNumberls = queryBean(new BeanHandler() {

            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append(" SELECT * from "+database+"fs_accessnumber ");
            }
        }, AccessNumber.class);
        if (accessNumberls.size() > 0) {
            return accessNumberls;
        }
        return null;
    }
    
    /**
     * 分页查询分机号
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<AccessNumber> getAccessNumbersByPage(final Integer pageSize,final Integer pageNum){
    	List<AccessNumber> accessNumberls = queryBean(new BeanHandler() {
            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append(" SELECT * from "+database+"fs_accessnumber ");
                sql.append("  LIMIT ? offset ? ");
				params.add(pageSize);
				params.add(pageNum);
            }
        }, AccessNumber.class);
        if (accessNumberls.size() > 0) {
            return accessNumberls;
        }
        return null;
    }

    /**
     * 查出接入号配置的外线
     *
     * @param number
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<AccessNumberGateway> getAccessNumberGateway(final Long id) {
        List<AccessNumberGateway> accessNumberGatewayls = queryBean(new BeanHandler() {

            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from "+database+"fs_accessnumber_gateway where accessnumber_id=?");
                params.add(id);
            }
        }, AccessNumberGateway.class);

        if (accessNumberGatewayls.size() > 0) {
            return accessNumberGatewayls;
        }
        return null;
    }
    

    /**
     * 根据名字查询接入号
     *
     * @param number
     * @return
     */
    @SuppressWarnings("unchecked")
    public AccessNumber getAccessNumber(final String number) {
        List<AccessNumber> accessNumberls = queryBean(new BeanHandler() {

            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from "+database+"fs_accessnumber where accessnumber=?");
                params.add(number);
            }
        }, AccessNumber.class);

        if (accessNumberls.size() > 0) {
            return accessNumberls.get(0);
        }
        return null;
    }


    /**
     * 根据技能表id删除
     *
     * @param queueid
     */
    public void removAccessNumberGateway(final Long id) {
        jdbcTemplate.update(" delete from "+database+"fs_accessnumber_gateway where accessnumber_id=? ", id);
    }

    @SuppressWarnings("unchecked")
	public List<FSSipTrunk> getGwByAccess(final String accessnumber, final String ip) {

        List<FSSipTrunk> ret = queryBean(new BeanHandler() {
            @Override
            public void doSql(StringBuilder sql, List<Object> params) {

                sql.append("select g.* from  "+database+"fs_accessnumber a, "+database+"fs_accessnumber_gateway ag ,"+database+"fs_gateway g, "+database+"mw_fshost f\n" +
                        "where a.id=ag.accessnumber_id and ag.gateway_id=g.id and g.fshost_id =f.id and f.esl_ip=? and a.accessnumber=?");
                params.add(ip);
                params.add(accessnumber);
            }
        }, FSSipTrunk.class);

        if(ret.size()>0){
            return ret;
        }

        return queryBean(new BeanHandler() {
            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select g.* from  "+database+"fs_accessnumber a, "+database+"fs_accessnumber_gateway ag ,"+database+"fs_gateway g \n" +
                        "where a.id=ag.accessnumber_id and ag.gateway_id=g.id and a.accessnumber=?");
                params.add(accessnumber);
            }
        }, FSSipTrunk.class);

    }
    
    /*
     * 查询范围内的接入号
     */
    @SuppressWarnings("unchecked")
    public List<AccessNumber> getNumbersByNum(final String min, final String max) {
        List<AccessNumber> ans = queryBean(new BeanHandler() {
            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append("select * from "+database+"fs_accessnumber where accessnumber >=? and  accessnumber <=? ");
                params.add(min);
                params.add(max);
            }
        }, AccessNumber.class);
        if (ans.size() > 0) {
            return ans;
        }
        return null;
    }
    
}
