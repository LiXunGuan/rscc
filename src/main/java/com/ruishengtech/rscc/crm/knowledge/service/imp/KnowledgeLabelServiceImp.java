package com.ruishengtech.rscc.crm.knowledge.service.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.knowledge.model.Knowledge;
import com.ruishengtech.rscc.crm.knowledge.model.KnowledgeLabel;
import com.ruishengtech.rscc.crm.knowledge.service.KnowledgeLabelService;
import com.ruishengtech.rscc.crm.knowledge.solution.KnowledgeLabelSolution;

@Service
@Transactional
public class KnowledgeLabelServiceImp extends BaseService implements KnowledgeLabelService{

	@Override
	public void saveOrUpdate(KnowledgeLabel knowledgeLabel) {
		if(knowledgeLabel == null)
			return;
		if(StringUtils.isBlank(knowledgeLabel.getUid())){
			super.save(knowledgeLabel);
		}else{
			knowledgeLabel.setUuid(UUID.UUIDFromString(knowledgeLabel.getUid()));
			super.update(knowledgeLabel);
		}
	}

	@Override
	public PageResult<KnowledgeLabel> queryPage(ICondition cond) {
		return super.queryPage(new KnowledgeLabelSolution(), cond, KnowledgeLabel.class);
	}

	@Override
	public KnowledgeLabel getKnowledgesByLabelUUid(String uuid) {
		UUID uid = UUID.UUIDFromString(uuid);
		return super.getByUuid(KnowledgeLabel.class, uid);
	}

	@Override
	public List<String> getTagsByKnowledge(Knowledge knowledge) {
		if(null != knowledge && StringUtils.isNotBlank(knowledge.getUid())){
			return jdbcTemplate.queryForList(" select label_name from knowledge_label where uuid in( select knowledge_label_uuid from knowledge_label_link l where l.knowledge_uuid = ? ) ", String.class, knowledge.getUid());
		}
		return null;
	}
}
