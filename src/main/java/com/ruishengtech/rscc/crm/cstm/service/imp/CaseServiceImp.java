package com.ruishengtech.rscc.crm.cstm.service.imp;//package com.ruishengtech.rscc.crm.cstm.service.imp;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.ruishengtech.framework.core.db.PageResult;
//import com.ruishengtech.framework.core.db.QueryUtil;
//import com.ruishengtech.framework.core.db.UUID;
//import com.ruishengtech.framework.core.db.service.BaseService;
//import com.ruishengtech.rscc.crm.cstm.condition.CaseCondition;
//import com.ruishengtech.rscc.crm.cstm.model.Case;
//import com.ruishengtech.rscc.crm.cstm.service.CaseService;
//import com.ruishengtech.rscc.crm.cstm.solution.CaseSolution;
//
///**
// * @author Frank case module
// */
//@Service
//@Transactional
//public class CaseServiceImp extends BaseService implements CaseService {
//
//	
//	/* (non-Javadoc)
//	 * @see com.ruishengtech.rscc.crm.cstm.service.CaseService#getCasesByConditions(com.ruishengtech.rscc.crm.cstm.model.Case)
//	 */
//	@Override
//	public List<Case> getCasesByConditions(Case c) {
//
//		StringBuilder builder = new StringBuilder();
//		List<Object> param = new ArrayList<Object>();
//		
//		QueryUtil.like(builder, param, c.getCaseName(), " AND case_name like ? "); //case名字
//		QueryUtil.like(builder, param, c.getCaseAssignee(), " AND case_assignee like ? ");//case指派人
//		QueryUtil.queryData(builder, param, c.getCaseStatus(), " AND status = ? ");//case状态
//		QueryUtil.like(builder, param, c.getCaseReporter(), " AND case_reporter like ? "); //case发起者
//		QueryUtil.like(builder, param, c.getCaseWatchers(), " AND case_watchers like ? "); //case关注着
//		
//		List<Case> cases = getBeanList(Case.class, builder.toString(),param.toArray());
//		if (cases.size() > 0) {
//			return cases;
//		}
//		
//		return null;
//
//	}
//
//	/* (non-Javadoc)
//	 * @see com.ruishengtech.rscc.crm.cstm.service.CaseService#caseSave(com.ruishengtech.rscc.crm.cstm.model.Case)
//	 */
//	public void save(Case c) {
//		super.save(c);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.ruishengtech.rscc.crm.cstm.service.CaseService#update(com.ruishengtech.rscc.crm.cstm.model.Case)
//	 */
//	@Override
//	public void update(Case c) {
//		super.update(c);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.ruishengtech.rscc.crm.cstm.service.CaseService#update(com.ruishengtech.rscc.crm.cstm.model.Case, java.lang.String[])
//	 */
//	@Override
//	public void update(Case c, String[] excludeFieldName) {
//		super.update(c, excludeFieldName);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.ruishengtech.rscc.crm.cstm.service.CaseService#queryPage(com.ruishengtech.rscc.crm.cstm.condition.CaseCondition)
//	 */
//	@Override
//	public PageResult<Case> queryPage(CaseCondition caseCondition) {
//		
//		super.queryPage(new CaseSolution(), caseCondition, Case.class);
//
//		return null;
//	}
//
//	/* (non-Javadoc)
//	 * @see com.ruishengtech.rscc.crm.cstm.service.CaseService#saveOrUpdate(com.ruishengtech.rscc.crm.cstm.model.Case)
//	 */
//	@Override
//	public void saveOrUpdate(Case case1) {
//		
//	}
//	
//	/**
//	 * 
//	 * 根据UUID删除编号
//	 * @param uuid
//	 * @return
//	 */
//	public void deleteCaseByUUid(UUID uuid){
//		
//		super.deleteById(Case.class, uuid);
//	}
//	
//	
//	
//	
//	
//	// /**
//	// * 查询指派人对应的所有case
//	// *
//	// * @param assignee
//	// * 指派人
//	// * @return 指派人的所有case
//	// */
//	// @SuppressWarnings("unchecked")
//	// public List<Case> getCasesByReport(String assignee) {
//	// if (StringUtils.isNotBlank(assignee)) {
//	// List<Case> cases = queryBean(new BeanHandler() {
//	//
//	// @Override
//	// public void doSql(StringBuilder sql, List<Object> params) {
//	// sql.append("  ");
//	// }
//	// }, Case.class);
//	//
//	// if (cases.size() > 0) {
//	// return cases;
//	// }
//	// }
//	//
//	// return null;
//	// }
//	//
//	// /**
//	// * 查询指派人对应的所有case
//	// *
//	// * @param assignee
//	// * 指派人
//	// * @param status
//	// * 状态
//	// * @return 指派人指定状态的所有case
//	// */
//	// public List<Case> getCasesByReport(String assignee, String status) {
//	//
//	// List<Case> cases = getBeanList(Case.class, "", assignee, status);
//	// if (cases.size() > 0) {
//	// return cases;
//	// }
//	// return null;
//	//
//	// }
//	//
//	// @Override
//	// public List<Case> getCasesByAssignee(String assignee) {
//	//
//	// List<Case> cases = getBeanList(Case.class, " ", assignee);
//	// if (cases.size() > 0) {
//	// return cases;
//	// }
//	// return null;
//	//
//	// }
//	//
//	// @Override
//	// public List<Case> getCasesByAssignee(String assignee, String status) {
//	//
//	// List<Case> cases = getBeanList(Case.class, " ", assignee);
//	// if (cases.size() > 0) {
//	// return cases;
//	// }
//	// return null;
//	// // if (StringUtils.isNotBlank(assignee)) {
//	// // List<Case> cases = queryBean(new BeanHandler() {
//	// //
//	// // @Override
//	// // public void doSql(StringBuilder sql, List<Object> params) {
//	// //
//	// // }
//	// //
//	// // }, Case.class);
//	// // if (cases.size() > 0) {
//	// // return cases;
//	// // }
//	// // }
//	// // return null;
//	// }
//	//
//	// @Override
//	// public List<Case> getCasesByStartTime(Date startTime) {
//	// List<Case> cases = getBeanList(Case.class, " ", startTime);
//	// if (cases.size() > 0) {
//	// return cases;
//	// }
//	// return null;
//	// }
//	//
//	// @Override
//	// public List<Case> getCasesByStatus(String status) {
//	// List<Case> cases = getBeanList(Case.class, " ", status);
//	// if (cases.size() > 0) {
//	// return cases;
//	// }
//	// return null;
//	// }
//}
