package com.ruishengtech.rscc.crm.issue.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.rscc.crm.issue.condition.CstmIssueCondition;
import com.ruishengtech.rscc.crm.issue.model.CstmIssue;
import com.ruishengtech.rscc.crm.issue.model.CstmIssueComments;
import com.ruishengtech.rscc.crm.issue.solution.CstmIssueSolution;
import com.ruishengtech.rscc.crm.report.condition.ReportBillCondition;

/**
 * @author Frank
 *
 */
public interface CstmIssueService {
	
	
	/**
	 * 分页查询数据
	 * @param cstmIssueCondition
	 * @return
	 */
	public abstract PageResult<CstmIssue> queryPage(CstmIssueCondition cstmIssueCondition);
	
	/**
	 * 根据uuid获取cstmservice对象
	 * @param uuid
	 * @return
	 */
	public abstract CstmIssue getCstmserviceByUUID(UUID uuid);
	
	/**
	 * 获取该case的所有评论
	 * @param uuid
	 * @return
	 */
	public abstract List<CstmIssueComments> getCstmserviceComments(UUID uuid);
	
	/**
	 * 修改或者是保存对象
	 * @param cstmIssue
	 */
	public abstract void saveOrUpdate(CstmIssue cstmIssue);
	
	
	/**
	 * 修改或者是保存对象
	 * @param cstmIssue
	 */
	public void saveOrUpdate(CstmIssue cstmIssue, String[] str);
	
	
	/**
	 * 根据callSessionUuid查询单个对象
	 * @param CallsessionUid
	 * @return
	 */
	public CstmIssue getBySessionUuid(final String CallsessionUuid);
	
	/**
	 * 导出数据
	 */
	public void getExcelCreated(HttpServletRequest request,HttpServletResponse response, CstmIssueCondition cicondition,CstmIssueSolution issueSolution) throws Exception;
}
