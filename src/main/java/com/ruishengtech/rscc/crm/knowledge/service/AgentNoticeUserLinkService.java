package com.ruishengtech.rscc.crm.knowledge.service;

import java.util.List;

import com.ruishengtech.rscc.crm.knowledge.model.AgentNotice;
import com.ruishengtech.rscc.crm.knowledge.model.AgentNoticeUserLink;

public interface AgentNoticeUserLinkService {

	/**
	 * 修改或保存一个AgentNoticeUserLink对象
	 * @param agentNoticeUserLink
	 */
	public abstract void saveOrUpdate(AgentNoticeUserLink agentNoticeUserLink);
	
	/**
	 * 根据坐席公告删除中间表信息
	 * @param agentnotice
	 */
	public abstract void deleteByAgentNotice(AgentNotice agentNotice);
	
	/**
	 * 根据坐席公告查询公告用户中间表信息
	 * @param agentNotice
	 * @return
	 */
	public abstract List<AgentNoticeUserLink> getAgentNoticeUserByNotice(AgentNotice agentNotice);
}
