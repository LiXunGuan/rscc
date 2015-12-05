package com.ruishengtech.rscc.crm.knowledge.service.imp;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.knowledge.model.KnowledgeLabel;
import com.ruishengtech.rscc.crm.knowledge.model.KnowledgeLabelLink;
import com.ruishengtech.rscc.crm.knowledge.service.KnowledgeLabelLinkService;
import com.ruishengtech.rscc.crm.knowledge.service.KnowledgeLabelService;
import com.ruishengtech.rscc.crm.knowledge.solution.KnowledgeLabelLinkSolution;

@Service
@Transactional
public class KnowledgeLabelLinkServiceImp extends BaseService implements KnowledgeLabelLinkService{

	@Autowired
	private KnowledgeLabelService knowledgeLabelService;
	
	@SuppressWarnings("unchecked")
	public  Map<String, String> getTags(){
		
		Map<String, String> allTags = new LinkedHashMap<String, String>();
		
		List<KnowledgeLabel> labels = queryBean(new BeanHandler() {
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" select * from knowledge_label ");
			}
		}, KnowledgeLabel.class);
		
		if(labels.size() > 0){
			for (int i = 0; i < labels.size(); i++) {
				allTags.put(labels.get(i).getLabelName(), labels.get(i).getUid());
			}
			return allTags;
		}
		
		return null;
	}
	
	@Override
	public void saveKnowledgeLabel(String kuuid, String kluuid) {
		jdbcTemplate.update(" insert into `crm`.`knowledge_label_link` (`knowledge_uuid`, `knowledge_label_uuid`) values (?, ?)", kuuid, kluuid);
	}

	@Override
	public PageResult<KnowledgeLabelLink> queryPage(ICondition cond) {
		return super.queryPage(new KnowledgeLabelLinkSolution(), cond, KnowledgeLabelLink.class);
	}

	@Override
	public KnowledgeLabelLink getKnowledgesByLinkUUid(String uuid) {
		UUID uid = UUID.UUIDFromString(uuid);
		return super.getByUuid(KnowledgeLabelLink.class, uid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public KnowledgeLabelLink getKnowledgesByKUUid(final String kuuid) {
		if (StringUtils.isNotBlank(kuuid)) {
			
			List<KnowledgeLabelLink> knowledges = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT * FROM knowledge_label_link WHERE knowledge_uuid = ?  ");
					params.add(kuuid);
				}
			}, KnowledgeLabelLink.class);
			if (knowledges.size() > 0) {
				return knowledges.get(0);
			}
		}
		return null;
	}

	@Override
	public void bindTagsToKnowledge(String kuuid, List<String> tags) {
		//先删除之前绑定的全部的标签，在重新绑定
		unBindTagsToKnowledge(kuuid);
		//查询所有标签
		Map<String, String> allTags = getTags();
		
		if (null != kuuid && tags.size() > 0) {
			for (String str : tags) {
				if(allTags != null && allTags.containsKey(str.trim())){
					jdbcTemplate.update(" insert into `crm`.`knowledge_label_link` (`knowledge_uuid`, `knowledge_label_uuid`) values (?, ?) ", kuuid, getTags().get(str.trim()));
				}else{
					KnowledgeLabel label = new KnowledgeLabel();
					label.setLabelName(str.trim());
					knowledgeLabelService.saveOrUpdate(label);
					jdbcTemplate.update(" insert into `crm`.`knowledge_label_link` (`knowledge_uuid`, `knowledge_label_uuid`) values (?, ?) ", kuuid, label.getUid());
				}
			}
		}
	}

	@Override
	public void unBindTagsToKnowledge(String kuuid) {
		jdbcTemplate.update(" delete from knowledge_label_link WHERE knowledge_uuid  = ? ", kuuid);
	}

	
}
