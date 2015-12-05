package com.ruishengtech.rscc.crm.knowledge.service;

import java.util.List;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.knowledge.model.Knowledge;

public interface KnowledgeService {
	
	/**
	 * 根据知识标题和类型查询知识信息
	 * @param title
	 * @param checktype
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public abstract List<Knowledge> getKnowledgesByTitle(String title, String checktype, Integer pageNum, Integer pageSize);
	
	/**
	 * 根据知识标题和类型查询知识数量
	 * @param title
	 * @param checktype
	 * @return
	 */
	public abstract Integer getKnowledgesCountBytitle(String title, String checktype);
	
	/**
	 * 根据知识内容查询知识信息
	 * @param content(内容)
	 * @return
	 */
	public abstract Knowledge getKnowledgesByContent(String content);
	
	/**
	 * 根据目录的UUid查询知识信息
	 * @param uuid
	 * @return
	 */
	public abstract List<Knowledge> getKnowledgesByDirectoryUUid(String uuid);
	
	/**
	 * 根据参数查询知识或内容信息
	 * @param params
	 * @return
	 */
	public abstract List<Knowledge> getKnowledgesByParams(String params);
	
	/**
	 * 根据UUid得到知识信息
	 * @param uuid
	 * @return
	 */
	public abstract Knowledge getKnowledgesByUUid(String uuid);
	
	/**
	 * 查询符合条件的所有知识信息
	 * @param cond
	 * @return
	 */
	public abstract PageResult<Knowledge> queryPage(ICondition cond);
	
	/**
	 * 根据UUid删除知识对象
	 * @param uuid
	 */
	public abstract void deleteKnowledgeByUUid(UUID uuid);
	
	/**
	 * 报存或修改一个知识对象
	 * @param knowledge
	 */
	public abstract void saveOrUpdate(Knowledge knowledge);
	
	/**
	 * 根据uuid删除信息
	 * @param uuid
	 */
	public abstract void delete(String uuid);
	
	/**
	 * 得到所有知识信息
	 * @return
	 */
	public List<Knowledge> getAllKnowledges();
	
}
