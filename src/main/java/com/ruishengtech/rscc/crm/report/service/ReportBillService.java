package com.ruishengtech.rscc.crm.report.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.billing.model.Billing;
import com.ruishengtech.rscc.crm.report.condition.ReportBillCondition;
import com.ruishengtech.rscc.crm.report.model.ReportBill;

public interface ReportBillService {
	

	/**
	 * 分页查询所有符合条件的数据
	 * @param condition
	 * @return
	 */
	public abstract PageResult<ReportBill> queryPage(ReportBillCondition billCondition);
	
	public boolean insertReportBill(ReportBill rb);
	
	public List<Billing> getBill(String stime,String etime);
	
	public void removeReportBill();
	
	public List<ReportBill> getAllReportBill();
	
	public void insertRBillByBill();
	
	public void deleteReportBillByDate();
	
	public void getExcelCreated(HttpServletRequest request,HttpServletResponse response, ReportBillCondition billCondition) throws Exception;
	/*获取当天0点到当前时间所有数据*/
	public List<ReportBill> getTodayData(ReportBillCondition rbCondition);
	
	public Map<String, Object> queryResultForHistory(ICondition condition);

}
