package com.ruishengtech.rscc.crm.knowledge.service.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.knowledge.model.AgentNotice;
import com.ruishengtech.rscc.crm.knowledge.model.AgentNoticeUserLink;
import com.ruishengtech.rscc.crm.knowledge.service.AgentNoticeService;
import com.ruishengtech.rscc.crm.knowledge.service.AgentNoticeUserLinkService;
import com.ruishengtech.rscc.crm.knowledge.solution.AgentNoticeSolution;
import com.ruishengtech.rscc.crm.user.model.User;

@Service
@Transactional
public class AgentNoticeServiceImp extends BaseService implements AgentNoticeService{

	@Autowired
	private AgentNoticeUserLinkService agentNoticeUserLinkService;
	
	@Override
	public void saveOrUpdate(AgentNotice agentNotice) {
		if(agentNotice == null)
			return;
		if(StringUtils.isBlank(agentNotice.getUid())){
			super.save(agentNotice);
		}else{
			agentNotice.setUuid(UUID.UUIDFromString(agentNotice.getUid()));
			super.update(agentNotice);
		}
	}

	@Override
	public void delete(AgentNotice agentNotice) {
		List<AgentNoticeUserLink> list = agentNoticeUserLinkService.getAgentNoticeUserByNotice(agentNotice);
		if(list != null && list.size() > 0){
			agentNoticeUserLinkService.deleteByAgentNotice(agentNotice);
		}
		jdbcTemplate.update(" DELETE FROM agent_notice WHERE uuid  = ? ", agentNotice.getUid());
	}

	@Override
	public PageResult<AgentNotice> queryPage(ICondition cond) {
		return super.queryPage(new AgentNoticeSolution(), cond, AgentNotice.class);
	}

	@Override
	public AgentNotice getAgentNoticeByUUid(String uuid) {
		UUID uid = UUID.UUIDFromString(uuid);
		return super.getByUuid(AgentNotice.class, uid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AgentNotice> getAgentNoticeUser(final String userUUid) {
		if (StringUtils.isNotBlank(userUUid)) {
			
			List<AgentNotice> agentNotices = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT an.*,anu.publishuser_uuid as publishUserUuid from agent_notice an "
							+ " LEFT JOIN agent_notice_user_link anu on an.uuid = anu.agent_notice_uuid and an.publish_status = '1' "
//							+ "LEFT JOIN user_user uu on uu.uuid = anu.user_uuid "
//							+ "LEFT JOIN user_user usu on usu.uuid = anu.publishuser_uuid "
							+ "WHERE anu.user_uuid = ? "
							+ "ORDER BY an.create_time DESC ");
					params.add(userUUid);
				}
			}, AgentNotice.class);
			if (agentNotices.size() > 0) {
				return agentNotices;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AgentNotice getAgentNoticeByTitle(final String title) {
		if (StringUtils.isNotBlank(title)) {
			List<AgentNotice> agentNotices = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT * FROM agent_notice WHERE notice_title = ? ");
					params.add(title);
				}
			}, AgentNotice.class);
			if (agentNotices.size() > 0) {
				return agentNotices.get(0);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AgentNotice> getAgentNoticesLimitFive(final String userUUid) {
		if (StringUtils.isNotBlank(userUUid)) {
			List<AgentNotice> agentNotices = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT an.*,ul.publish_time as publishTime FROM agent_notice an "
							+ "left join agent_notice_user_link ul on an.uuid = ul.agent_notice_uuid "
							+ "where ul.user_uuid = ? and an.publish_status = '1' order by ul.publish_time desc limit 5 ");
					params.add(userUUid);
				}
			}, AgentNotice.class);
			if (agentNotices.size() > 0) {
				return agentNotices;
			}
		}
		return null;
	}
	
}
