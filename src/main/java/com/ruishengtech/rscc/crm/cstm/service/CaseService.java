package com.ruishengtech.rscc.crm.cstm.service;//package com.ruishengtech.rscc.crm.cstm.service;
//
//import java.util.List;
//
//import com.ruishengtech.framework.core.db.PageResult;
//
//
///**
// * @author Frank
// *
// */
//public interface CaseService {
//	
//	/**
//	 * 根据条件查询
//	 * @param c要查询的条件
//	 * @return 符合条件的所有数据
//	 */
//	public abstract List<Case> getCasesByConditions(Case c);
//	
//	/**
//	 * 保存一个case对象
//	 * @param c
//	 */
//	public abstract void save(Case c);
//	
//	/**
//	 * 修改
//	 * @param c
//	 */
//	public abstract void update(Case c);
//	
//	/**
//	 * 修改
//	 * @param c
//	 * @param excludeFieldName
//	 */
//	public abstract void update(Case c,String[] excludeFieldName);
//	
//	
//	/**
//	 * 分页查询
//	 * @return
//	 */
//	public abstract PageResult<Case> queryPage(CaseCondition caseCondition);
//	
//	/**
//	 * 保存修改删除
//	 * @param case1
//	 */
//	public abstract void saveOrUpdate(Case case1);
//	
//	
////
////	/**
////	 * 查询指派人对应的所有case
////	 * 
////	 * @param assignee
////	 *            指派人
////	 * @return 指派人的所有case
////	 */
////	public abstract List<Case> getCasesByAssignee(String assignee);
////
////	/**
////	 * 查询指派人对应状态的所有case
////	 * 
////	 * @param assignee
////	 *            指派人
////	 * @param status
////	 *            状态
////	 * @return 指派人指定状态的所有case
////	 */
////	public abstract List<Case> getCasesByAssignee(String assignee, String status);
////
////	
////	/** 查询指定时间之后的所有case
////	 * @param startTime 查询的开始时间
////	 * @return 返回符合条件的所有case
////	 */
////	public abstract List<Case> getCasesByStartTime(Date startTime);
////	
////	/** 查询report提交的所有case
////	 * @param reporter case提交者
////	 * @return 
////	 */
////	public abstract List<Case> getCasesByReport(String reporter);
////	
////	/**
////	 * 查询指定状态的case
////	 * @param status case状态
////	 * @return
////	 */
////	public abstract List<Case> getCasesByStatus(String status);
//
//}