package com.ruishengtech.rscc.crm.knowledge.service;

import java.util.List;

import com.ruishengtech.rscc.crm.knowledge.model.KnowledgeSearchNumbers;

public interface KnowledgeSearchNumbersService {

	/**
	 * 保存或修改一个关键字搜索量对象
	 * @param knowledgeSearchNumbers
	 */
	public abstract void saveOrUpdate(KnowledgeSearchNumbers knowledgeSearchNumbers);
	
	/**
	 * 查询关键字的搜索量
	 * @param keyword
	 * @return
	 */
	public abstract Long getCountByKeyword(String keyword);
	
	
	/**
	 * 查询最新的10条标签不重复数据
	 * @return
	 */
	public abstract List<KnowledgeSearchNumbers> selectTopTenData();
	
}
