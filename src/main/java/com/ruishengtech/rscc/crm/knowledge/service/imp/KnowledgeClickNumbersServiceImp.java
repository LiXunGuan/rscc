package com.ruishengtech.rscc.crm.knowledge.service.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.knowledge.model.Knowledge;
import com.ruishengtech.rscc.crm.knowledge.model.KnowledgeClickNumbers;
import com.ruishengtech.rscc.crm.knowledge.service.KnowledgeClickNumbersService;

@Service
@Transactional
public class KnowledgeClickNumbersServiceImp extends BaseService implements KnowledgeClickNumbersService{

	@Override
	public void saveOrUpdate(KnowledgeClickNumbers knowledgeClickNumbers) {
		if(knowledgeClickNumbers == null)
			return;
		else
			super.save(knowledgeClickNumbers);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getCountByKnowledge(final Knowledge knowledge) {
		if (StringUtils.isNotBlank(knowledge.getUid())) {
			
			List<Long> count = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" select count(*) from knowledge_clicknumbers where knowledge_uuid= ?  ");
					params.add(knowledge.getUid());
				}
			}, Long.class);
			if (count.size() > 0) {
				return count.get(0);
			}
		}
		return null;
	}
}
