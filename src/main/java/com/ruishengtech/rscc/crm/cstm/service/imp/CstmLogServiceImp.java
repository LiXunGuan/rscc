package com.ruishengtech.rscc.crm.cstm.service.imp;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.cstm.model.CstmLog;
import com.ruishengtech.rscc.crm.cstm.service.CstmLogService;
import com.ruishengtech.rscc.crm.ui.SessionUtil;

@Service
@Transactional
public class CstmLogServiceImp extends BaseService implements CstmLogService{

	/**
	 * 保存日志
	 * @param log
	 */
	@Override
	public void saveLog(CstmLog log) {
		super.save(log);
	}
	
	/**
	 * 查询指定客户池编号的所有操作日志
	 * @param uuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CstmLog> getCstmLogsByUUID(final String poolId){
		
		if(StringUtils.isNotBlank(poolId)){
			
			List<CstmLog> cstmLogs = queryBean(new BeanHandler() {
				
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append("SELECT l.opt_user, l.opt_date, l.opt_obj_uuid, l.opt_action, l.opt_obj, l.obj_pool FROM cstm_log l WHERE obj_pool = ? ORDER BY opt_date DESC;");
					params.add(poolId);
				}
			}, CstmLog.class);
			
			if(cstmLogs.size() > 0){
				
				return cstmLogs;
			}
		}
		return null;
	}
	
	/**
	 * 保存日志
	 * @param request
	 * @param source
	 * @param cstm_name
	 * @param pool_id
	 */
	@Override
	public void saveCustomerLogs(HttpServletRequest request, String source,
			String cstm_name, String pool_id) {
		CstmLog cstmLog = new CstmLog();
		cstmLog.setOptUser(SessionUtil.getCurrentUser(request).getLoginName());
		cstmLog.setOptUserUUID(SessionUtil.getCurrentUser(request).getUid());
		cstmLog.setOptDate(new Date());
		cstmLog.setOptObj(cstm_name);
		cstmLog.setOptAction("添加了一个名为 <b>" + cstm_name + "</b> 的客户信息");
		cstmLog.setObjPool(pool_id);
		cstmLog.setOptCount(1);
		cstmLog.setOptType(CstmLog.OPT1);
		
		if(StringUtils.isNotBlank(source)){
			
			cstmLog.setOptSource("");
		}else{
			cstmLog.setOptSource("");
		}
		
		// 保存日志
		saveLog(cstmLog);
		
	}

	
	
	
}
