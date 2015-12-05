package com.ruishengtech.rscc.crm.ui.mw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.rscc.crm.ui.mw.condition.UploadingVoiceCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.UploadingVoice;
import com.ruishengtech.rscc.crm.ui.mw.sys.FsServerManager;

/**
 * Created by yaoliceng on 2014/10/25.
 */
@Service
@Transactional
public class UploadingVoiceService extends BaseConfigService<UploadingVoice> {

	private String database;
	
	public UploadingVoiceService(){
		database = SpringPropertiesUtil.getProperty("sys.record.database");
	}
	
    @Autowired
    private FsServerManager fsServerManager;


    @Override
    protected Class getClazz() {
        return UploadingVoice.class;
    }

    @Override
    public void deploy() {
   
    }

    @Override
    protected ISolution getSolution() {
        return new ISolution() {
            @Override
            public void getWhere(ICondition condition, StringBuilder whereSql, List<Object> params) {
            	UploadingVoiceCondition cond = (UploadingVoiceCondition) condition;
                whereSql.append(" from "+database+"uploading_voice where 1=1  ");
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
     * 查询所有上传的语音信息
	 * @return
	 */
    @SuppressWarnings("unchecked")
    public List<UploadingVoice> getAllVoices(){
    	List<UploadingVoice> voices = queryBean(new BeanHandler() {
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append(" select * from "+database+"uploading_voice ");
    		}
        }, UploadingVoice.class);
        if (voices.size() > 0) {
            return voices;
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public UploadingVoice getVoicesByName(final String name){
    	List<UploadingVoice> voices = queryBean(new BeanHandler() {
    		public void doSql(StringBuilder sql, List<Object> params) {
    			sql.append(" select * from "+database+"uploading_voice where name = ? ");
    			params.add(name);
    		}
        }, UploadingVoice.class);
        if (voices.size() > 0) {
            return voices.get(0);
        }
        return null;
    }

//    /**
//     * 根据sip_id查询分机
//     * @param sipId
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    public FSUser getFSUser(final String sipId) {
//        List<FSUser> fsls = queryBean(new BeanHandler() {
//
//            @Override
//            public void doSql(StringBuilder sql, List<Object> params) {
//                sql.append("select * from fs_user where sip_id=? ");
//                params.add(sipId);
//            }
//        }, FSUser.class);
//
//        if (fsls.size() > 0) {
//            return fsls.get(0);
//        }
//        return null;
//    }
//    
//    /**
//     * 查询范围内分机
//     * @param sipId
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    public List<FSUser> getFSUserls(final String min,final String max) {
//        List<FSUser> fsls = queryBean(new BeanHandler() {
//
//            @Override
//            public void doSql(StringBuilder sql, List<Object> params) {
//                sql.append("select * from fs_user where sip_id >=? and  sip_id <=? ");
//                params.add(min);
//                params.add(max);
//            }
//        }, FSUser.class);
//
//        if (fsls.size() > 0) {
//            return fsls;
//        }
//        return null;
//    }
//
//
//    public String getSipUserPhoneContact(String destString) {
//        List<String> list = jdbcTemplate.queryForList("select url from registrations where reg_user=?", String.class, destString);
//        if(list.size()>0){
//            return list.get(0);
//        }
//        return null;
//    }
}
