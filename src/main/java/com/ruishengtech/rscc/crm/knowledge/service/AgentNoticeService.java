package com.ruishengtech.rscc.crm.knowledge.service;

import java.util.List;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.knowledge.model.AgentNotice;
import com.ruishengtech.rscc.crm.user.model.User;

public interface AgentNoticeService {

	/**
	 * 保存或修改一个坐席公告对象
	 * @param agentNotice
	 */
	public abstract void saveOrUpdate(AgentNotice agentNotice);
	
	/**
	 * 删除坐席公告信息
	 * @param agentNotice
	 */
	public abstract void delete(AgentNotice agentNotice);
	
	/**
	 * 查询符合条件的所有公告信息
	 * @param cond
	 * @return
	 */
	public abstract PageResult<AgentNotice> queryPage(ICondition cond);
	
	/**
	 * 通过UUID查询坐席公告信息
	 * @param uuid
	 * @return
	 */
	public abstract AgentNotice getAgentNoticeByUUid(String uuid);
	
	/**
	 * 根据用户UUID得到接收用户的信息
	 * @param agentNotice
	 * @return
	 */
	public abstract List<AgentNotice> getAgentNoticeUser(String userUUid);
	
	/**
	 * 得到当前用户最新的5条公告信息
	 * @param user
	 * @return
	 */
	public abstract List<AgentNotice> getAgentNoticesLimitFive(String userUUid);
	
	/**
	 * 根据标题查询坐席公告信息
	 * @param title
	 * @return
	 */
	public abstract AgentNotice getAgentNoticeByTitle(String title);
		
}
