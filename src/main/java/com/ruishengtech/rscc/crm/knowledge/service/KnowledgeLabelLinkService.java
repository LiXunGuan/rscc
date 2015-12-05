package com.ruishengtech.rscc.crm.knowledge.service;

import java.util.List;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.knowledge.model.KnowledgeLabelLink;

public interface KnowledgeLabelLinkService {

	/**
	 * 保存一个知识标签对象
	 * @param knowledgeLabelLink
	 */
	public abstract void saveKnowledgeLabel(String kuuid, String kluuid);
	
	/**
	 * 查询符合条件的所有知识标签信息
	 * @param cond
	 * @return
	 */
	public abstract PageResult<KnowledgeLabelLink> queryPage(ICondition cond);
	
	/**
	 * 根据知识标签uuid查询中间表信息
	 * @param uuid
	 * @return
	 */
	public abstract KnowledgeLabelLink getKnowledgesByLinkUUid(String uuid);
	
	/**
	 * 根据知识的uuid查询中间表信息
	 * @param kuuid
	 * @return
	 */
	public abstract KnowledgeLabelLink getKnowledgesByKUUid(String kuuid);
	
	/**
	 * 为知识绑定标签
	 * @param kuuid
	 * @param tags
	 */
	public abstract void bindTagsToKnowledge(String kuuid, List<String> tags);
	
	/**
	 * 去除知识的所有标签
	 * @param kuuid
	 */
	public abstract void unBindTagsToKnowledge(String kuuid);
	
}
