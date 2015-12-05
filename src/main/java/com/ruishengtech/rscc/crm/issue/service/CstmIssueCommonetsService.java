package com.ruishengtech.rscc.crm.issue.service;

import java.util.List;

import com.ruishengtech.rscc.crm.issue.model.CstmIssueComments;

public interface CstmIssueCommonetsService {
	
	/**
	 * 
	 * 查询该编号的所有评论
	 * @param uid
	 * @return
	 */
	public abstract List<CstmIssueComments> getCommentsByUid(String uid);
	
	/**
	 * 添加一条评论
	 * @param comments
	 */
	public abstract void save(CstmIssueComments comments);

}
