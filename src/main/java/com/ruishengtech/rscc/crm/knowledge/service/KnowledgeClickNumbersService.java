package com.ruishengtech.rscc.crm.knowledge.service;

import com.ruishengtech.rscc.crm.knowledge.model.Knowledge;
import com.ruishengtech.rscc.crm.knowledge.model.KnowledgeClickNumbers;

public interface KnowledgeClickNumbersService {

	/**
	 * 保存或修改一个点击量对象
	 * @param knowledgeClickNumbers
	 */
	public abstract void saveOrUpdate(KnowledgeClickNumbers knowledgeClickNumbers);
	
	/**
	 * 查询知识的点击量
	 * @param knowledge
	 * @return
	 */
	public abstract Long getCountByKnowledge(Knowledge knowledge);
	
}
