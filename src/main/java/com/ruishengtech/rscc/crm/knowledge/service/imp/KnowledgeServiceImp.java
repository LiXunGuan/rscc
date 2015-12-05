package com.ruishengtech.rscc.crm.knowledge.service.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.QueryUtil;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.knowledge.model.Knowledge;
import com.ruishengtech.rscc.crm.knowledge.model.KnowledgeLabelLink;
import com.ruishengtech.rscc.crm.knowledge.service.KnowledgeLabelLinkService;
import com.ruishengtech.rscc.crm.knowledge.service.KnowledgeService;
import com.ruishengtech.rscc.crm.knowledge.solution.KnowledgeSolution;

@Service
@Transactional
public class KnowledgeServiceImp extends BaseService implements KnowledgeService {

	@Autowired
	private KnowledgeLabelLinkService knowledgeLabelLinkService;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Knowledge> getKnowledgesByTitle(final String title, final String checktype,  final Integer pageSize, final Integer pageNum) {
		if (StringUtils.isNotBlank(title)) {
			List<Knowledge> knowledges = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					if("tags".equals(checktype)){
						//查询标签名或知识信息
						sql.append(" SELECT k.*,group_concat(la.label_name) as label_name "
								+ " FROM knowledge_info k "
								+ " LEFT JOIN knowledge_label_link li ON k.uuid = li.knowledge_uuid "
								+ " LEFT JOIN knowledge_label la ON la.uuid = li.knowledge_label_uuid "
								+ " WHERE 1=1 ");
						QueryUtil.like(sql, params, title, " AND la.label_name like ?  ");
						QueryUtil.like(sql, params, title, " OR k.knowledge_title like ?  ");
						QueryUtil.like(sql, params, title, " OR k.knowledge_content like ? GROUP BY k.uuid");
					}else if("dirs".equals(checktype)){
						//查询目录或知识信息
						sql.append("SELECT k.* FROM knowledge_info k "
								+ "LEFT JOIN knowledge_directory kd ON kd.uuid = k.directory_uuid "
								+ "WHERE 1=1 ");
						QueryUtil.like(sql, params, title, " AND kd.directory_name like ? ");
						QueryUtil.like(sql, params, title, " OR k.knowledge_title like ?  ");
						QueryUtil.like(sql, params, title, " OR k.knowledge_content like ? ");
					}else{
						QueryUtil.like(sql, params, title, "SELECT * FROM knowledge_info " );
					}
					sql.append("  LIMIT ? offset ? ");
					params.add(pageSize);
					params.add(pageNum);
				}
			}, Knowledge.class);
			if (knowledges.size() > 0) {
				return knowledges;
			}
		}else{
			List<Knowledge> knowledges = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT * FROM knowledge_info ");
					sql.append("  LIMIT ? offset ? ");
					params.add(pageSize);
					params.add(pageNum);
				}
			}, Knowledge.class);
			if (knowledges.size() > 0) {
				return knowledges;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer getKnowledgesCountBytitle(final String title, final String checktype) {
		if (StringUtils.isNotBlank(title)) {
			List<Knowledge> knowledges = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					if("tags".equals(checktype)){
						//查询标签和知识
						sql.append(" SELECT k.*,group_concat(la.label_name) as label_name "
								+ " FROM knowledge_info k "
								+ " LEFT JOIN knowledge_label_link li ON k.uuid = li.knowledge_uuid "
								+ " LEFT JOIN knowledge_label la ON la.uuid = li.knowledge_label_uuid "
								+ " WHERE 1=1 ");
						QueryUtil.like(sql, params, title, " AND la.label_name like ?  ");
						QueryUtil.like(sql, params, title, " OR k.knowledge_title like ?  ");
						QueryUtil.like(sql, params, title, " OR k.knowledge_content like ? GROUP BY k.uuid");
					}else if("dirs".equals(checktype)){
						//查询目录和知识
						sql.append("SELECT k.* FROM knowledge_info k "
								+ "LEFT JOIN knowledge_directory kd ON kd.uuid = k.directory_uuid "
								+ "WHERE 1=1 ");
						QueryUtil.like(sql, params, title, " AND kd.directory_name like ? ");
						QueryUtil.like(sql, params, title, " OR k.knowledge_title like ?  ");
						QueryUtil.like(sql, params, title, " OR k.knowledge_content like ? ");
					}else{
						QueryUtil.like(sql, params, title, "SELECT * FROM knowledge_info " );
					}
				}
			}, Knowledge.class);
			if (knowledges.size() > 0) {
				return knowledges.size();
			}
		}else{
			List<Knowledge> knowledges = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT * FROM knowledge_info ");
				}
			}, Knowledge.class);
			if (knowledges.size() > 0) {
				return knowledges.size();
			}
		}
		return 0;
	}
	
	
	

	@Override
	@SuppressWarnings("unchecked")
	public Knowledge getKnowledgesByContent(final String content) {
		if (StringUtils.isNotBlank(content)) {
			
			List<Knowledge> knowledges = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT * FROM knowledge_info WHERE knowledge_content like ?  ");
					params.add(content);
				}
			}, Knowledge.class);
			if (knowledges.size() > 0) {
				return knowledges.get(0);
			}
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Knowledge> getKnowledgesByDirectoryUUid(final String uuid) {
		if (StringUtils.isNotBlank(uuid)) {
			
			List<Knowledge> knowledges = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT * FROM knowledge_info WHERE directory_uuid = ?  ");
					params.add(uuid);
				}
			}, Knowledge.class);
			if (knowledges.size() > 0) {
				return knowledges;
			}
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Knowledge getKnowledgesByUUid(final String uuid) {
		if (StringUtils.isNotBlank(uuid)) {
			
			List<Knowledge> knowledges = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT * FROM knowledge_info WHERE uuid = ?  ");
					params.add(uuid);
				}
			}, Knowledge.class);
			if (knowledges.size() > 0) {
				return knowledges.get(0);
			}
		}
		return null;
	}

	@Override
	public PageResult<Knowledge> queryPage(ICondition cond) {
		return super.queryPage(new KnowledgeSolution(), cond, Knowledge.class);
	}

	@Override
	public void deleteKnowledgeByUUid(UUID uuid) {
		super.deleteById(Knowledge.class, uuid);
	}

	@Override
	public void saveOrUpdate(Knowledge knowledge) {
		if(knowledge == null)
			return;
		if(StringUtils.isBlank(knowledge.getUid())){
			super.save(knowledge);
		}else{
			knowledge.setUuid(UUID.UUIDFromString(knowledge.getUid()));
			super.update(knowledge);
		}
	}

	@Override
	public void delete(String uuid) {
		//删除知识标签中间表的信息
		KnowledgeLabelLink kll = knowledgeLabelLinkService.getKnowledgesByKUUid(uuid);
		if(kll != null){
			knowledgeLabelLinkService.unBindTagsToKnowledge(uuid);
		}
		//删除知识信息
		jdbcTemplate.update(" delete from knowledge_info WHERE uuid  = ? ", uuid);
	}

	@Override
	public List<Knowledge> getAllKnowledges() {
		return super.getAll(Knowledge.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Knowledge> getKnowledgesByParams(final String param) {
		if (StringUtils.isNotBlank(param)) {
			
			List<Knowledge> knowledges = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT * FROM knowledge_info WHERE 1=1 ");
					QueryUtil.like(sql, params, param, " AND knowledge_title like ?  ");
					QueryUtil.like(sql, params, param, " OR knowledge_content like ? ");
				}
			}, Knowledge.class);
			if (knowledges.size() > 0) {
				return knowledges;
			}
		}
		return null;
	}
}
