package com.ruishengtech.rscc.crm.cstm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ruishengtech.rscc.crm.cstm.model.CstmLog;

public interface CstmLogService {

	
	/**
	 * 保存日志
	 * @param log
	 */
	public abstract void saveLog(CstmLog log);
	
	
	/**
	 * 查询指定客户池编号的所有操作日志
	 * @param uuid
	 * @return
	 */
	public abstract List<CstmLog> getCstmLogsByUUID(String poolId);
	
	
	/**
	 * 保存客户日志
	 * @param request
	 * @param source
	 * @param cstm_name
	 * @param pool_id
	 */
	public void saveCustomerLogs(HttpServletRequest request, String source,
			String cstm_name, String pool_id);
	
}
