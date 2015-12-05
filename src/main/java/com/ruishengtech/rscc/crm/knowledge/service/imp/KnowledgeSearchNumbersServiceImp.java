package com.ruishengtech.rscc.crm.knowledge.service.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.knowledge.model.KnowledgeSearchNumbers;
import com.ruishengtech.rscc.crm.knowledge.service.KnowledgeSearchNumbersService;

@Service
@Transactional
public class KnowledgeSearchNumbersServiceImp extends BaseService implements KnowledgeSearchNumbersService{

	@Override
	public void saveOrUpdate(KnowledgeSearchNumbers knowledgeSearchNumbers) {
		if(knowledgeSearchNumbers == null)
			return;
		else
			super.save(knowledgeSearchNumbers);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getCountByKeyword(final String keyword) {
		if (StringUtils.isNotBlank(keyword)) {
			
			List<Long> count = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" select count(*) from knowledge_searchnumbers where keyword like ?  ");
					params.add(keyword);
				}
			}, Long.class);
			if (count.size() > 0) {
				return count.get(0);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KnowledgeSearchNumbers> selectTopTenData() {
		List<KnowledgeSearchNumbers> ksn = queryBean(new BeanHandler() {
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" select keyword from (select DISTINCT keyword from knowledge_searchnumbers ORDER BY search_time desc) a LIMIT 10 ");
			}
		}, KnowledgeSearchNumbers.class);
		if (ksn.size() > 0) {
			return ksn;
		}
		return null;
	}

}
