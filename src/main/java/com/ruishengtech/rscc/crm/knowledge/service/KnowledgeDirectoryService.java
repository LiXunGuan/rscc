package com.ruishengtech.rscc.crm.knowledge.service;

import java.util.List;

import org.json.JSONArray;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.knowledge.model.KnowledgeDirectory;

public interface KnowledgeDirectoryService {

	/**
	 * 保存或修改一个知识目录
	 * @param knowledgeDirectory
	 */
	public abstract void saveOrUpdate(KnowledgeDirectory knowledgeDirectory);
	
	/**
	 * 查询符合条件的所有知识目录信息
	 * @param cond
	 * @return
	 */
	public abstract PageResult<KnowledgeDirectory> queryPage(ICondition cond);
	
	/**
	 * 根据目录的UUid查询知识信息
	 * @param uuid
	 * @return
	 */
	public abstract KnowledgeDirectory getKnowledgesByDirectoryUUid(String uuid);
	
	/**
	 * 根据父目录的uuid查询知识信息
	 * @param uuid
	 * @return
	 */
	public abstract KnowledgeDirectory getKnowledgesByPDirectoryUUid(String uuid);
	
	/**
	 * 根据目录的名称查询单条目录信息
	 * @param name
	 * @return
	 */
	public abstract KnowledgeDirectory getKnowledgesByDirectoryName(String name);
	
	/**
	 * 根据目录的名称查询多条目录信息
	 * @param name
	 * @return
	 */
	public abstract List<KnowledgeDirectory> getKnowledgesByDirNames(String name);
	
	/**
	 * 得到所有的目录信息
	 * @return
	 */
	public abstract List<KnowledgeDirectory> getAllKnowledgeDirectorys();
	
	public JSONArray getDirectorys();
	
	public JSONArray getDirectoryTree();
	
	/**
	 * 根据uuid删除信息
	 * @param uuid
	 */
	public abstract void delete(String uuid);
	
	/**
	 * 得到所有目录信息
	 * @return
	 */
	public List<KnowledgeDirectory> getAllDirectorys();
	
}
