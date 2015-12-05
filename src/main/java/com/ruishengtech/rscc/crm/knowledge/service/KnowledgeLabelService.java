package com.ruishengtech.rscc.crm.knowledge.service;

import java.util.List;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.knowledge.model.Knowledge;
import com.ruishengtech.rscc.crm.knowledge.model.KnowledgeLabel;

public interface KnowledgeLabelService {
	
	/**
	 * 保存或修改一个知识标签
	 * @param knowledgeLabel
	 */
	public abstract void saveOrUpdate(KnowledgeLabel knowledgeLabel);
	
	/**
	 * 查询符合条件的知识目录
	 * @param cond
	 * @return
	 */
	public abstract PageResult<KnowledgeLabel> queryPage(ICondition cond);
	
	/**
	 * 根据标签uuid查询标签信息
	 * @param uuid
	 * @return
	 */
	public abstract KnowledgeLabel getKnowledgesByLabelUUid(String uuid);
	
	/**
	 * 查询知识的所有标签
	 * @param kOnowledge
	 * @return
	 */
	public abstract List<String> getTagsByKnowledge(Knowledge knowledge);
	
}
