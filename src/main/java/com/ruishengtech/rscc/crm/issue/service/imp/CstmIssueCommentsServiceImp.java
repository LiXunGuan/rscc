package com.ruishengtech.rscc.crm.issue.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.issue.model.CstmIssueComments;
import com.ruishengtech.rscc.crm.issue.service.CstmIssueCommonetsService;


@Service
@Transactional
public class CstmIssueCommentsServiceImp extends BaseService implements CstmIssueCommonetsService{

	
	/**
	 * 
	 * 查询该编号的所有评论
	 * @param uid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CstmIssueComments> getCommentsByUid(final String uid) {

		List<CstmIssueComments> comments = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" SELECT * FROM `cstmservice_comment` WHERE cstmservice_uuid = ? ");
				params.add(uid);
			}
		}, CstmIssueComments.class);
		
		if(comments.size() > 0){
			return comments;
		}
		
		return null;
	}

	/**
	 * 添加一条评论
	 * @param comments
	 */
	@Override
	public void save(CstmIssueComments comments) {

		super.save(comments);
	}
	
	

}
