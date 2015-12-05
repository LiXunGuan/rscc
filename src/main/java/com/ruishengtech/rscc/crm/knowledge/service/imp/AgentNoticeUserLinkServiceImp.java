package com.ruishengtech.rscc.crm.knowledge.service.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.knowledge.model.AgentNotice;
import com.ruishengtech.rscc.crm.knowledge.model.AgentNoticeUserLink;
import com.ruishengtech.rscc.crm.knowledge.service.AgentNoticeUserLinkService;

@Service
@Transactional
public class AgentNoticeUserLinkServiceImp extends BaseService implements AgentNoticeUserLinkService{

	@Override
	public void saveOrUpdate(AgentNoticeUserLink au) {
		if(au == null){
			return;
		}
		if(StringUtils.isBlank(au.getUid())){
			super.save(au);
		}else{
			super.update(au);
		}
		
		//jdbcTemplate.update(" INSERT INTO agent_notice_user_link (`agent_notice_uuid`, `user_uuid`, `start_time`, `end_time`) VALUES (?, ?, ?, ?)", au.getAgentNoticeUUID(), au.getUserUUID(), au.getStartTime(), au.getEndTime());
	}

	@Override
	public void deleteByAgentNotice(AgentNotice agentNotice) {
		jdbcTemplate.update(" DELETE FROM agent_notice_user_link WHERE agent_notice_uuid  = ? ", agentNotice.getUid());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AgentNoticeUserLink> getAgentNoticeUserByNotice(final AgentNotice agentNotice) {
		if (StringUtils.isNotBlank(agentNotice.getUid())) {
			
			List<AgentNoticeUserLink> klls = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT anu.* FROM agent_notice_user_link anu WHERE agent_notice_uuid = ?  ");
					params.add(agentNotice.getUid());
				}
			}, AgentNoticeUserLink.class);
			if (klls.size() > 0) {
				return klls;
			}
		}
		return null;
	}

}
